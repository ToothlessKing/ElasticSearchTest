package com.es.service;

import com.es.EsTestApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liqijin on 2018/4/4.
 */
public class EsServiceTest extends EsTestApplicationTests {

    @Autowired
    EsService esService;

    @Test
    public void insert() throws Exception {
        Object object = esService.insert("test");
        System.out.println(object);
    }

    @Test
    public void getById (){
        String id = "6Q6Sj2IBUY_hd3_Zwa8t";
        Object object = esService.get(id);
        System.out.println(object);
    }
    @Test
    public void delete(){
        String id = "6A6Ej2IBUY_hd3_Zg68-";
        Object object = esService.delete(id);
        System.out.println(object);
    }
    @Test
    public void update(){
        String id = "6Q6Sj2IBUY_hd3_Zwa8t";
        String title = "this is a test";
        Object object = esService.update(id,title);
        System.out.println(object);
    }
    @Test
    public void query(){
        String title = "test";
        Object object = esService.query(title,0,10);
        System.out.println(object);
    }
    @Test
    public void createIndex() throws IOException {
        String index = "school";
        String type = "student";
        Map param = new HashMap();
        param.put("name","li");
        param.put("sex","男");
        Object object = esService.createIndex(index,type,param);
        System.out.println(object);
    }
}