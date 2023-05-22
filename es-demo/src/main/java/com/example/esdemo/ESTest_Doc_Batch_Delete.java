package com.example.esdemo;

import cn.hutool.json.JSONUtil;
import com.example.esdemo.model.User;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * 批量删除数据
 */
public class ESTest_Doc_Batch_Delete {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient esClient = new RestHighLevelClient(RestClient
                .builder(new HttpHost("localhost", 9200)));

        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.add(new DeleteRequest("user").id("1001"));
        bulkRequest.add(new DeleteRequest("user").id("1002"));
        bulkRequest.add(new DeleteRequest("user").id("1003"));
        bulkRequest.add(new DeleteRequest("user").id("1004"));
        bulkRequest.add(new DeleteRequest("user").id("1005"));
        BulkResponse bulk = esClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(JSONUtil.toJsonStr(bulk));
        esClient.close();
    }
}
