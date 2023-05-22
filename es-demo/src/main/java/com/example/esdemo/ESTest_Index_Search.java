package com.example.esdemo;

import cn.hutool.json.JSONUtil;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;

import java.io.IOException;

/**
 * 索引查询
 */
public class ESTest_Index_Search {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient esClient = new RestHighLevelClient(RestClient
                .builder(new HttpHost("localhost", 9200)));


        GetIndexRequest user = new GetIndexRequest("user");
        GetIndexResponse response = esClient.indices().get(user, RequestOptions.DEFAULT);

        System.out.println("操作状态：" + JSONUtil.toJsonStr(response));
        esClient.close();
    }
}
