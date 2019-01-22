package com.study.test;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author lj
 * date : 2019/1/21 17:41
 */
public class ESCurd {
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
     * 创建索引
     */
    @Test
    public void testCreatedIndex(){
        client.admin().indices().prepareCreate("mobile").get();
    }

    /**
     * 删除索引
     */
    @Test
    public void testDeleIndex(){
        client.admin().indices().prepareDelete("mobile").get();
    }


    /**
     * 添加映射
     * @throws Exception
     */
    @Test
    public void testCreateMapping() throws Exception {
        //1.先创建索引
        client.admin().indices().prepareCreate("blog").get();
        //2.添加映射
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                    .startObject("article")
                        .startObject("properties")
                            .startObject("id")
                                .field("store", true)
                                .field("type", "long")
                            .endObject()
                            .startObject("title")
                                .field("analyzer", "ik_smart")
                                .field("store", true)
                                .field("type", "text")
                            .endObject()
                            .startObject("content")
                                .field("analyzer", "ik_smart")
                                .field("store", true)
                                .field("type", "text")
                            .endObject()
                        .endObject()
                    .endObject()
                .endObject();
        //3.创建映射
        PutMappingRequest mappingRequest = Requests.putMappingRequest("blog").type("article").source(builder);
        client.admin().indices().putMapping(mappingRequest).get();
    }
}
