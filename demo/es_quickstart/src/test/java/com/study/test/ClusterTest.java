package com.study.test;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lj
 * date : 2019/1/23 10:39
 */
public class ClusterTest {
    /**
     * 集群插入一条数据
     * @throws Exception
     */
    @Test
    public void testCreateIndex() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("cluster.name", "elasticsearch");
        Settings setting = Settings.builder().put(map).build();
        PreBuiltTransportClient client = new PreBuiltTransportClient(setting);
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

        Map sourceMap = new HashMap();
        sourceMap.put("id", 2);
        sourceMap.put("title", "ElasticSearch是一个基于Lucene的搜索服务器");
        sourceMap.put("content", "它提供了一个分布式多用户能力的全文搜索引擎，基于RESTfulweb接口。Elasticsearch是用Java开发的，并作为Apache许可条款下的开放源码发布，是当前流行的企业级搜索引擎。设计用于云计算中，能够时搜索，稳定，可靠，快速，安装使用方便。");
        client.prepareIndex("blog", "article", "2").setSource(sourceMap).get();
        client.close();
    }
}
