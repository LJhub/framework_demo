package com.study.service.impl;

import com.study.pojo.Article;
import com.study.repository.ArticleRepository;
import com.study.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author lj
 * date : 2019/1/22 15:14
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    /**
     * 增
     * @param article
     */
    @Override
    public void saveArticleToEs(Article article) {
        articleRepository.save(article);
    }

    /**
     * 删
     * @param id
     */
    @Override
    public void deleteArticleFromEs(Integer id) {
        articleRepository.deleteById(id);
    }

    /**
     * 查all
     * @return
     */
    @Override
    public Iterable<Article> findAll() {
        return articleRepository.findAll();
    }

    /**
     * 分页
     * @param pageable
     * @return
     */
    @Override
    public Page<Article> findPage(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }
}
