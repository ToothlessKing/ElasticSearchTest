package com.es.service;

import javafx.beans.binding.ObjectExpression;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by liqijin on 2018/4/4.
 */
@Service
public class EsService {

    @Autowired
    TransportClient client;

    /**
     * 创建索引类型
     * @param index
     * @param type
     * @param params
     * @return
     * @throws IOException
     */
    public Object createIndex(String index, String type, Map<String,Object> params) throws IOException {
        IndexResponse response = null;
        if (params!=null){
           IndexRequestBuilder requestBuilder =
                   client.prepareIndex(index,type, UUID.randomUUID().toString().replaceAll("-",""));
            XContentBuilder builder =  XContentFactory.jsonBuilder().startObject();
            for (String key : params.keySet()) {
                builder.field(key,params.get(key));
            }
            builder.endObject();
            requestBuilder.setSource(builder);
            response = requestBuilder.get();
        }else {
            response = client.prepareIndex(index,type).get();
        }
        return response.getResult();
    }

    /**
     * add
     * @param title
     * @return
     */
    public Object insert(String title){
        try {
           XContentBuilder builder =  XContentFactory.jsonBuilder()
                    .startObject()
                    .field("title",title)
                    .endObject();
           IndexResponse response = client.prepareIndex("book","novel")
                   .setSource(builder)
                   .get();
           return response.getResult();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * get by id
     * @param id
     * @return
     */
    public Object get(String id){
        GetResponse response = client.prepareGet("book","novel",id).get();
        return response.getSource();
    }

    /**
     * delete by id
     * @param id
     * @return
     */
    public Object delete(String id){
        DeleteResponse response = client.prepareDelete("book","novel",id).get();
        return response.getResult();
    }

    public Object update(String id,String title){
        UpdateRequest request = new UpdateRequest("book","novel",id);
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject();
            if (title!=null){
                builder.field("title",title);
            }
            builder.endObject();
            request.doc(builder);
            UpdateResponse result = client.update(request).get();
            return result.getResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object query(String title,int limit,int size){
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if (title!=null){
            boolQuery.must(QueryBuilders.matchQuery("title",title));
        }
        SearchResponse searchResponse = client.prepareSearch("book")
                .setTypes("novel")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(boolQuery)
                .setFrom(limit)
                .setSize(size)
                .get();
        List result = new ArrayList();
        for (SearchHit searchHitFields : searchResponse.getHits()) {
            result.add(searchHitFields.getSource());
        }
        return result;
    }
}
