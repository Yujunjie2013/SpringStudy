package com.example.esdemo;

import cn.hutool.json.JSONUtil;
import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * 文档删除
 */
public class ESTest_Doc_Delete {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient esClient = new RestHighLevelClient(RestClient
                .builder(new HttpHost("localhost", 9200)));
        DeleteRequest deleteRequest = new DeleteRequest("user", "1001");
        DeleteResponse index = esClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println("文档删除结果： " + JSONUtil.toJsonStr(index));
        esClient.close();
    }
}
