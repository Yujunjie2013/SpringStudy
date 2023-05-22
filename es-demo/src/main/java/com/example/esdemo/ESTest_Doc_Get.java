package com.example.esdemo;

import cn.hutool.json.JSONUtil;
import com.example.esdemo.model.User;
import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * 文档查询
 */
public class ESTest_Doc_Get {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient esClient = new RestHighLevelClient(RestClient
                .builder(new HttpHost("localhost", 9200)));
        GetRequest getRequest = new GetRequest("user","1001");
        GetResponse index = esClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println("文档查询结果： " + index.getSourceAsString());
        esClient.close();
    }
}
