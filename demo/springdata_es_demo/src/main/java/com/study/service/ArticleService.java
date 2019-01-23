package com.study.service;

import com.study.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleService {
    void saveArticleToEs(Article article);

    void deleteArticleFromEs(Integer id);

    Iterable<Article> findAll();

    Page<Article> findPage(Pageable pageable);
}
