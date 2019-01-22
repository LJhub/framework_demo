package com.study.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.pojo.Article;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ArticleTest {
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
     * Object保存到es索引库
     * @throws Exception
     */
    @Test
    public void testSaveObject() throws Exception {
        Article article = new Article();
        article.setId(2);
        article.setTitle("2.ElasticSearch是一个基于Lucene的搜索服务器");
        article.setContent("2.它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口。");

        ObjectMapper objectMapper = new ObjectMapper();
        String articleStr = objectMapper.writeValueAsString(article);

        client.prepareIndex("blog", "article", article.getId().toString()).setSource(articleStr.getBytes(), XContentType.JSON).get();
    }

    /**
     * 修改文档
     * 两种方式修改索引库中的一条document
     */
    @Test
    public void testUpdate() throws Exception{
        Article article = new Article();
        article.setId(2);
        article.setTitle("2.1 ElasticSearch是一个基于Lucene的搜索服务器");
        article.setContent("2.1 它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口。");

        ObjectMapper objectMapper = new ObjectMapper();
        String articleStr = objectMapper.writeValueAsString(article);

        //第一种修改方式client.prepareUpdate()
        //client.prepareUpdate("blog", "article", article.getId().toString()).setDoc(articleStr.getBytes(), XContentType.JSON).get();

        // 第二种修改方式
        client.update(new UpdateRequest("blog", "article", article.getId().toString()).doc(articleStr.getBytes(), XContentType.JSON)).get();
    }

    /**
     * 删除文档
     */
    @Test
    public void testDeleDocument() throws Exception{
        client.delete(new DeleteRequest("blog", "article", "2")).get();
    }



}