package com.example.esdemo;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;

import java.io.IOException;

/**
 * 创建user索引
 */
public class ESTest_Client {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient esClient = new RestHighLevelClient(RestClient
                .builder(new HttpHost("localhost", 9200)));


        CreateIndexRequest user = new CreateIndexRequest("user");
        CreateIndexResponse response = esClient.indices().create(user, RequestOptions.DEFAULT);
        boolean acknowledged = response.isAcknowledged();
        System.out.println("操作状态：" + acknowledged);
        System.out.println(response.index());
        System.out.println(response.isFragment());
        System.out.println(response.isShardsAcknowledged());
        esClient.close();
    }
}
