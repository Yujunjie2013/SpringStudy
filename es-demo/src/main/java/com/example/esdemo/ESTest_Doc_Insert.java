package com.example.esdemo;

import cn.hutool.json.JSONUtil;
import com.example.esdemo.model.User;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

public class ESTest_Doc_Insert {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient esClient = new RestHighLevelClient(RestClient
                .builder(new HttpHost("localhost", 9200)));
        IndexRequest indexRequest = new IndexRequest();
        User user = new User();
        user.setName("张三");
        user.setSex("男");
        user.setAge(30);
        indexRequest.index("user").id("1001").source(JSONUtil.toJsonStr(user), XContentType.JSON);
        IndexResponse index = esClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println("文档创建结果：" + JSONUtil.toJsonStr(index));
        esClient.close();
    }
}
