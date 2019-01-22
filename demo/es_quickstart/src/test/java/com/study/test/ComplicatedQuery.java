package com.study.test;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Iterator;

/**
 * @author lj
 * date : 2019/1/21 21:20
 */
public class ComplicatedQuery {
    private PreBuiltTransportClient client;
    @Before
    public void before() throws UnknownHostException {
        client = new PreBuiltTransportClient(Settings.EMPTY);
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
    }
    @After
    public void after(){
        client.close();
    }


    /**
     * 复杂条件查询
     * must(QueryBuilders) : AND，求交集    -->必须满足的条件
     * mustNot(QueryBuilders): NOT (除去中间的)
     * should(QueryBuilders):OR ，求并集
     */
    @Test
    public void testComplicatedQuery(){
        SearchResponse searchResponse = client.prepareSearch("blog").setTypes("article").setQuery(QueryBuilders.boolQuery()
                .should(QueryBuilders.wildcardQuery("title", "*6*"))
                .should(QueryBuilders.rangeQuery("id").from(1).to(50))
        ).setFrom(0).setSize(20).addSort("id", SortOrder.DESC).get();
        SearchHits hits = searchResponse.getHits();
        System.out.println(hits.getTotalHits());
        Iterator<SearchHit> it = searchResponse.getHits().iterator();
        while (it.hasNext()) {
            System.out.println(it.next().getSourceAsString());
        }
    }

    /**
     * 分词查询加上range限制查询(多条件查询)
     */
    @Test
    public void testManyQuery(){
        SearchResponse searchResponse = client.prepareSearch("blog").setTypes("article")
                .setQuery(QueryBuilders.termQuery("title", "荣耀"))
                .setQuery(QueryBuilders.rangeQuery("id").from(5).to(55)).get();
        SearchHits hits = searchResponse.getHits();
        System.out.println(hits.getTotalHits());
        Iterator<SearchHit> it = searchResponse.getHits().iterator();
        while (it.hasNext()) {
            System.out.println(it.next().getSourceAsString());
        }
    }

    /**
     * 高亮搜索数据
     */
    @Test
    public void testHighlightQuery(){
        HighlightBuilder hb = new HighlightBuilder();
        hb.preTags("<font color='red'>");
        hb.postTags("</font>");
        hb.field("title");
        SearchResponse searchResponse = client.prepareSearch("blog").setTypes("article")
                .setQuery(QueryBuilders.termQuery("title","荣耀")).highlighter(hb).get();
        Iterator<SearchHit> it = searchResponse.getHits().iterator();
        while (it.hasNext()) {
            System.out.println(Arrays.toString(it.next().getHighlightFields().get("title").getFragments()));
        }
    }

}
