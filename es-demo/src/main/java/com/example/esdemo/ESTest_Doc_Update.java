package com.example.esdemo;

import cn.hutool.json.JSONUtil;
import com.example.esdemo.model.User;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * 文档更新
 */
public class ESTest_Doc_Update {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient esClient = new RestHighLevelClient(RestClient
                .builder(new HttpHost("localhost", 9200)));
        UpdateRequest updateRequest = new UpdateRequest("user", "1001");
        updateRequest.doc(XContentType.JSON, "sex", "女");
        UpdateResponse user = esClient.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println("文档更新结果：" + JSONUtil.toJsonStr(user));
        esClient.close();
    }
}
