package com.study.test;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author lj
 * date : 2019/1/21 9:34
 * 文档的查询
 */
public class ES {

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
     * 增加索引记录(map构建参数)
     * @throws Exception
     */
    @Test
    public void testForXbuilder() throws Exception {
        PreBuiltTransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        //批量插入100条
        for (Integer i = 4; i < 100; i++) {
            Map<String, Object> map = new HashMap();
            map.put("id", i);
            map.put("title", i.toString()+".荣耀 V10全网通 8GB+128GB 极光蓝 移动联通电信4G全面屏手机 双卡双待");
            map.put("content", i.toString()+".限时优惠3099，领券立减300，成交价2799！5.99英寸全面屏，2000万AI变焦双摄！");
            IndexResponse indexResponse = client.prepareIndex("blog", "article", i.toString()).setSource(map).get();
        }
        client.close();
    }

    /**
     * 新建记录(build构建参数)
     * @throws Exception
     */
    @Test
    public void test1() throws Exception {
        //创建客户端访问对象
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        XContentBuilder builder = XContentFactory.jsonBuilder().startObject()
                .field("id", 1)
                .field("title", "ElasticSearch是一个基于Lucene的搜索服务器。")
                .field("content", "它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口。" +
                        "Elasticsearch是用Java开发的，并作为Apache许可条款下的开放源码发布，" +
                        "是当前流行的企业级搜索引擎。设计用于云计算中，能够达到实时搜索，稳定，可靠，快速，安装使用方便。")
                .endObject();
        //创建索引、创建文档类型、设置唯一主键。同时创建文档
        client.prepareIndex("blog1","article","1").setSource(builder).get();//执行动作
        client.close();
    }

    /**
     * @throws Exception
     * 根据id查询,不需要索引
     */
    @Test
    public void testQueryForId() throws Exception{
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        GetResponse get = client.prepareGet().setIndex("手机").setType("info").setId("1").get();
        System.out.println("Map封装的结果"+get.getSource());
        System.out.println("json类型的结果:"+get.getSourceAsString());
        client.close();
    }

    /**
     * 查询前部,不通过索引-->QueryBuilders.matchAllQuery()
     * @throws UnknownHostException
     */
    @Test
    public void testFindAll() throws UnknownHostException {
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        SearchResponse searchResponse = client.prepareSearch("手机").setTypes("info").setQuery(QueryBuilders.matchAllQuery()).get();
        Iterator<SearchHit> it = searchResponse.getHits().iterator();
        while (it.hasNext()) {
            SearchHit sh = it.next();
            System.out.println(sh.getSourceAsString());
            System.out.println(sh.getSource().get("title"));

        }
        client.close();
    }

    /**
     * 字段查询-->QueryBuilders.queryStringQuery("荣耀")
     * 注意:如果是使用字符串查询,我们的参数会先分词再查询
     */
    @Test
    public void testQueryForOneField(){
        SearchResponse searchResponse = client.prepareSearch("手机").setTypes("info").setQuery(QueryBuilders.queryStringQuery("荣耀").field("title")).get();
        Iterator<SearchHit> it = searchResponse.getHits().iterator();
        while (it.hasNext()) {
            System.out.println(it.next().getSourceAsString());
        }
    }

    /**
     * 词条查询-->QueryBuilders.termQuery(k,v)
     */
    @Test
    public void testForTerm(){
        SearchResponse searchResponse = client.prepareSearch("blog").setTypes("article").setQuery(QueryBuilders.termQuery("title", "移动")).get();
        Iterator<SearchHit> it = searchResponse.getHits().iterator();
        while (it.hasNext()) {
            System.out.println(it.next().getSourceAsString());
        }
    }

    /**
     * 通配符搜索(模糊搜索)-->QueryBuilders.wildcardQuery(k,v)
     *      ?    --> 代表一个字符
     *      *    --> 代表多个字符
     */
    @Test
    public void testWildCard(){
        SearchResponse searchResponse = client.prepareSearch("手机").setTypes("info").setQuery(QueryBuilders.wildcardQuery("title", "*耀*")).get();
        Iterator<SearchHit> it = searchResponse.getHits().iterator();
        while (it.hasNext()) {
            System.out.println(it.next().getSourceAsString());
        }
    }
}
