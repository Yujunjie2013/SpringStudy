package com.example.esdemo;

import cn.hutool.json.JSONUtil;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;

import java.io.IOException;

/**
 * 索引删除
 */
public class ESTest_Index_Delete {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient esClient = new RestHighLevelClient(RestClient
                .builder(new HttpHost("localhost", 9200)));


        DeleteIndexRequest user = new DeleteIndexRequest("user");
        AcknowledgedResponse response = esClient.indices().delete(user, RequestOptions.DEFAULT);

        System.out.println("操作状态：" + JSONUtil.toJsonStr(response));
        esClient.close();
    }
}
