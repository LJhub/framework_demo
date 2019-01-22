package com.study.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.pojo.Article;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author lj
 * date : 2019/1/21 20:26
 */
public class QueryForObject {
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
     * 词条查询封装到list<Object>
     */
    @Test
    public void testTermToObject() throws Exception {
        //          -->     词条搜索
        //SearchResponse searchResponse = client.prepareSearch("blog").setTypes("article").setQuery(QueryBuilders.termQuery("title", "服务器")).get();
        //          -->     字符串搜索
        //SearchResponse searchResponse = client.prepareSearch("blog").setTypes("article").setQuery(QueryBuilders.queryStringQuery("服务器")).get();
        //          -->     查询所有
        //SearchResponse searchResponse = client.prepareSearch("blog").setTypes("article").setQuery(QueryBuilders.matchAllQuery()).get();
        //          -->     根据id范围进行查询
        SearchResponse searchResponse = client.prepareSearch("blog").setTypes("article").setQuery(QueryBuilders.rangeQuery("id").from(1).to(2)).get();


        Iterator<SearchHit> it = searchResponse.getHits().iterator();
        List<Article> list = null;
        ObjectMapper objectMapper = null;
        while (it.hasNext()) {
            if (list == null){
                list = new ArrayList<>();
            }
            String source = it.next().getSourceAsString();
            objectMapper = new ObjectMapper();
            Article article = objectMapper.readValue(source, Article.class);
            list.add(article);
        }
        for (Article article : list) {
            System.out.println(article);
        }
    }

}
