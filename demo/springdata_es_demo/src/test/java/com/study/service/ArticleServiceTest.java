package com.study.service;

import com.study.pojo.Article;
import com.study.repository.ArticleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Iterator;

/**
 * 使用spring data 中的repository操作索引库
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleRepository articleRepository;

    /**
     * 增
     */
    @Test
    public void testSave(){
        Article a = new Article();
        a.setId(100);
        a.setTitle("100.荣耀 V10全网通 8GB+128GB 极光蓝 移动联通电信4G全面屏手机 双卡双待");
        a.setContent("100.限时优惠3099，领券立减300，成交价2799！5.99英寸全面屏，2000万AI变焦双摄！");
        articleService.saveArticleToEs(a);
    }

    /**
     * 删
     */
    @Test
    public void testDelete(){
        articleService.deleteArticleFromEs(100);
    }

    /**
     * 查询全部
     */
    @Test
    public void testFindAll(){
        Iterator<Article> it = articleService.findAll().iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    /**
     * 分页
     */
    @Test
    public void testFindPage(){
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(3, 10,sort);
        Page<Article> page = articleService.findPage(pageable);
        System.out.println(page.getTotalElements());
        System.out.println(page.getTotalPages());
        for (Article article : page.getContent()) {
            System.out.println(article);
        }
    }

    @Test
    public void testLike(){
        Iterable<Article> it = articleRepository.findArticleByTitleIsLike("荣耀");
        for (Article article : it) {
            System.out.println(article);
        }
    }

    @Test
    public void testFindOne(){
        System.out.println(articleRepository.findByIdEquals(20));
    }
}