package com.study.service;

import com.study.pojo.Article;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.internal.LoadingCache;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author lj
 * date : 2019/1/23 14:49
 * 使用ElasticsearchTemplate操作索引库
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class ElasticsearchTemplateTest {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 创建索引
     */
    @Test
    public void testCreateIndex(){
        elasticsearchTemplate.createIndex(Article.class);
    }

    /**
     * 创建和更新文档
     */
    @Test
    public void testAddDocument(){
        Article a = new Article();
        a.setId(99);
        a.setTitle("99.荣耀 V10全网通 8GB+128GB 极光蓝 移动联通电信4G全面屏手机 双卡双待");
        a.setContent("99.限时优惠3099，领券立减300，成交价2799！5.99英寸全面屏，2000万AI变焦双摄！");
        IndexQuery indexQuery = new IndexQuery();
        indexQuery.setObject(a);
        elasticsearchTemplate.index(indexQuery);
    }

    /**
     * 查询全部或者多个使用QueryForList
     */
    @Test
    public void testQueryAll(){
        CriteriaQuery searchQuery = new CriteriaQuery(new Criteria());
        List<Article> articles = elasticsearchTemplate.queryForList(searchQuery, Article.class);
        for (Article article : articles) {
            System.out.println(article);
        }
    }

    /**
     * 查询一个使用QueryForObject
     */
    @Test
    public void testFindOne(){
        CriteriaQuery getQuery = new CriteriaQuery(Criteria.where("id").is(5));
        Article article = elasticsearchTemplate.queryForObject(getQuery, Article.class);
        System.out.println(article);
    }

    /**
     * 分页+排序+条件
     */
    @Test
    public void testQueryPage(){
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageble = new PageRequest(2, 5,sort);
        CriteriaQuery searchQuery = new CriteriaQuery(Criteria.where("id").between(20, 40), pageble);
        Page<Article> page = elasticsearchTemplate.queryForPage(searchQuery, Article.class);
        System.out.println(page.getTotalElements());
        System.out.println(page.getTotalPages());
        for (Article article : page.getContent()) {
            System.out.println(article);
        }
    }

    /**
     *  词条查询    --> termQuery
     *  通配符查询  --> wildcardQuery
     *  通配符查询注意  -->和mysql不同
     *                --> * 多个字符
     *                --> ? 一个字符
     */
    @Test
    public void testTermQuery(){
        SearchQuery searchQuery = new NativeSearchQuery(QueryBuilders.wildcardQuery("title", "*耀*"));
        List<Article> articles = elasticsearchTemplate.queryForList(searchQuery, Article.class);
        for (Article article : articles) {
            System.out.println(article);
        }
    }

    @Test
    public void testComplexQuery(){
        SearchQuery searchQuery = new NativeSearchQuery(QueryBuilders
                .boolQuery()
                .must(QueryBuilders.rangeQuery("id").from(0).to(50))
                .must(QueryBuilders.wildcardQuery("title", "*6*"))
        );
        List<Article> articles = elasticsearchTemplate.queryForList(searchQuery, Article.class);
        for (Article article : articles) {
            System.out.println(article);
        }
    }

}
