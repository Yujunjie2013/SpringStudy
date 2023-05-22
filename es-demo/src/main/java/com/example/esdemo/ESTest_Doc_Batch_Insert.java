package com.example.esdemo;

import cn.hutool.json.JSONUtil;
import com.example.esdemo.model.User;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * 批量新增数据
 */
public class ESTest_Doc_Batch_Insert {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient esClient = new RestHighLevelClient(RestClient
                .builder(new HttpHost("localhost", 9200)));

        BulkRequest bulkRequest = new BulkRequest();
        User user = new User();
        user.setAge(20);
        user.setSex("女");
        user.setName("萌萌");
        User user1 = new User();
        user1.setAge(22);
        user1.setSex("男");
        user1.setName("壮壮");
        User user2 = new User();
        user2.setAge(18);
        user2.setSex("女");
        user2.setName("西西");
        User user3 = new User();
        user3.setAge(30);
        user3.setSex("男");
        user3.setName("射手");
        User user4 = new User();
        user4.setAge(36);
        user4.setSex("女");
        user4.setName("栈帧");
        bulkRequest.add(new IndexRequest("user").id("1001").source(JSONUtil.toJsonStr(user), XContentType.JSON));
        bulkRequest.add(new IndexRequest("user").id("1002").source(JSONUtil.toJsonStr(user1), XContentType.JSON));
        bulkRequest.add(new IndexRequest("user").id("1003").source(JSONUtil.toJsonStr(user2), XContentType.JSON));
        bulkRequest.add(new IndexRequest("user").id("1004").source(JSONUtil.toJsonStr(user3), XContentType.JSON));
        bulkRequest.add(new IndexRequest("user").id("1005").source(JSONUtil.toJsonStr(user4), XContentType.JSON));
        BulkResponse bulk = esClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println("===================");
        System.out.println(JSONUtil.toJsonStr(bulk));
        esClient.close();
    }
}
