package com.example.esdemo;

import cn.hutool.json.JSONUtil;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;

/**
 * 复杂查询演示
 */
public class ESTest_Doc_Query {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient esClient = new RestHighLevelClient(RestClient
                .builder(new HttpHost("localhost", 9200)));

        //查询全部
        queryAll(esClient);
        //条件查询
        queryCondition(esClient);
        //分页查询
        queryByPage(esClient);
        //查询排序
        queryBySort(esClient);
        //字段过滤查询
        queryFetch(esClient);
        //组合查询
        queryComposite(esClient);
        //范围查询
        queryByRange(esClient);
        //模糊查询
        fuzzyQuery(esClient);
        //高亮查询
        hightQuery(esClient);
        //聚合查询
        sumQuery(esClient);
        //分组查询
        queryGroup(esClient);

        esClient.close();
    }

    private static void queryGroup(RestHighLevelClient esClient) throws IOException {
        SearchRequest user = new SearchRequest("user");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //分组的字段不能是text类型
        searchSourceBuilder.aggregation(AggregationBuilders.terms("ageGroup").field("age"));
        user.source(searchSourceBuilder);
        SearchResponse index = esClient.search(user, RequestOptions.DEFAULT);
        System.out.println("getHits： " + index.getHits().getTotalHits());
        System.out.println("getTook： " + index.getTook());
        String jsonStr = JSONUtil.toJsonStr(index.getAggregations());
        for (SearchHit hit : index.getHits()) {
            System.out.println("分组查询：" + hit.getSourceAsString() + "分组结果：" + jsonStr);
        }
    }

    private static void sumQuery(RestHighLevelClient esClient) throws IOException {
        SearchRequest user = new SearchRequest("user");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.aggregation(AggregationBuilders.max("maxAge").field("age"));
        user.source(searchSourceBuilder);
        SearchResponse index = esClient.search(user, RequestOptions.DEFAULT);
        System.out.println("getHits： " + index.getHits().getTotalHits());
        System.out.println("getTook： " + index.getTook());
        String jsonStr = JSONUtil.toJsonStr(index.getAggregations());
        for (SearchHit hit : index.getHits()) {
            System.out.println("聚合查询：" + hit.getSourceAsString() + "聚合结果：" + jsonStr);
        }
    }


    private static void hightQuery(RestHighLevelClient esClient) throws IOException {
        SearchRequest user = new SearchRequest("user");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.highlighter(new HighlightBuilder()
                .field("name")
                .preTags("<font color='red'>").postTags("</font>"));
        user.source(searchSourceBuilder.query(QueryBuilders.termQuery("age", 36)));
        SearchResponse index = esClient.search(user, RequestOptions.DEFAULT);
        System.out.println("getHits： " + index.getHits().getTotalHits());
        System.out.println("getTook： " + index.getTook());
        for (SearchHit hit : index.getHits()) {
            System.out.println("高亮查询：" + hit.getSourceAsString() + "高亮：" + JSONUtil.toJsonStr(hit.getHighlightFields()));
        }
    }

    private static void fuzzyQuery(RestHighLevelClient esClient) throws IOException {
        SearchRequest user = new SearchRequest("user");

        FuzzyQueryBuilder fuzziness = QueryBuilders
                .fuzzyQuery("name", "萌萌")
                .fuzziness(Fuzziness.ONE);
        user.source(new SearchSourceBuilder().query(fuzziness));
        SearchResponse index = esClient.search(user, RequestOptions.DEFAULT);
        System.out.println("getHits： " + index.getHits().getTotalHits());
        System.out.println("getTook： " + index.getTook());
        for (SearchHit hit : index.getHits()) {
            System.out.println("模糊查询：" + hit.toString());
        }
    }

    private static void queryByRange(RestHighLevelClient esClient) throws IOException {
        SearchRequest user = new SearchRequest("user");
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("age");
        rangeQueryBuilder.gte(20).lte(36);
        user.source(new SearchSourceBuilder().query(rangeQueryBuilder));
        SearchResponse index = esClient.search(user, RequestOptions.DEFAULT);
        System.out.println("getHits： " + index.getHits().getTotalHits());
        System.out.println("getTook： " + index.getTook());
        for (SearchHit hit : index.getHits()) {
            System.out.println("范围查询：" + hit.toString());
        }
    }

    private static void queryComposite(RestHighLevelClient esClient) throws IOException {
        SearchRequest user = new SearchRequest("user");
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//        boolQueryBuilder.must(QueryBuilders.matchQuery("age", 36));
//        boolQueryBuilder.must(QueryBuilders.matchQuery("sex", "女"));
        boolQueryBuilder.should(QueryBuilders.matchQuery("name", "壮壮"));
        user.source(new SearchSourceBuilder().query(boolQueryBuilder));
        SearchResponse index = esClient.search(user, RequestOptions.DEFAULT);
        System.out.println("getHits： " + index.getHits().getTotalHits());
        System.out.println("getTook： " + index.getTook());
        for (SearchHit hit : index.getHits()) {
            System.out.println("组合查询：" + hit.toString());
        }
    }

    private static void queryFetch(RestHighLevelClient esClient) throws IOException {
        String[] includes = {};
        String[] excludes = {"age"};//排除年龄
        SearchRequest user = new SearchRequest("user");
        user.source(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery())
                .fetchSource(includes, excludes));
        SearchResponse index = esClient.search(user, RequestOptions.DEFAULT);
        System.out.println("getHits： " + index.getHits().getTotalHits());
        System.out.println("getTook： " + index.getTook());
        for (SearchHit hit : index.getHits()) {
            System.out.println("字段过滤查询结果：" + hit.toString());
        }
    }

    private static void queryBySort(RestHighLevelClient esClient) throws IOException {
        SearchRequest user = new SearchRequest("user");
        user.source(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery())
                .sort("age", SortOrder.DESC));
        SearchResponse index = esClient.search(user, RequestOptions.DEFAULT);
        System.out.println("getHits： " + index.getHits().getTotalHits());
        System.out.println("getTook： " + index.getTook());
        for (SearchHit hit : index.getHits()) {
            System.out.println("查询排序结果：" + hit.toString());
        }
    }

    private static void queryByPage(RestHighLevelClient esClient) throws IOException {
        SearchRequest user = new SearchRequest("user");
        user.source(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery())
                .from(0)//（当前页面-1）*每页显示条数
                .size(2));
        SearchResponse index = esClient.search(user, RequestOptions.DEFAULT);
        System.out.println("getHits： " + index.getHits().getTotalHits());
        System.out.println("getTook： " + index.getTook());
        for (SearchHit hit : index.getHits()) {
            System.out.println("分页查询结果：" + hit.toString());
        }
    }

    private static void queryCondition(RestHighLevelClient esClient) throws IOException {
        SearchRequest user = new SearchRequest("user");
        user.source(new SearchSourceBuilder().query(QueryBuilders.termQuery("age", "18")));
        SearchResponse search = esClient.search(user, RequestOptions.DEFAULT);
        System.out.println("getHits： " + search.getHits().getTotalHits());
        System.out.println("getTook： " + search.getTook());
        for (SearchHit hit : search.getHits()) {
            System.out.println(hit.toString());
        }
    }

    //查询全部
    private static void queryAll(RestHighLevelClient esClient) throws IOException {
        SearchRequest user = new SearchRequest("user");
        user.source(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()));
        SearchResponse index = esClient.search(user, RequestOptions.DEFAULT);
        System.out.println("getHits： " + index.getHits().getTotalHits());
        System.out.println("getTook： " + index.getTook());
        for (SearchHit hit : index.getHits()) {
            System.out.println(hit.toString());
        }
    }
}
