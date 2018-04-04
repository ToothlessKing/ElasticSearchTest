package com.es.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * elastic 配置
 * Created by liqijin on 2018/4/4.
 */
@Configuration
public class ElasticSearchConfig {

    @Value("${es.host}")
    private String host;
    @Value("${es.port}")
    private int port;
    @Value("${es.cluster.name}")
    private String clusterName;

    @Bean
    public TransportClient client() throws UnknownHostException {
        //一个节点，可配置多个节点
        InetSocketTransportAddress node = new InetSocketTransportAddress(
                InetAddress.getByName(host),port
        );
        //节点名
        Settings settings = Settings.builder()
                .put("cluster.name",clusterName)
                .build();

        TransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(node);
        return client;
    }
}
