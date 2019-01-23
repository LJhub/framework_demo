package com.study.repository;

import com.study.pojo.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author lj
 * date : 2019/1/22 15:05
 */
@Repository
public interface ArticleRepository extends ElasticsearchRepository<Article,Integer> {

    public Iterable<Article> findArticleByTitleIsLike(String title);

    Article findByIdEquals(Integer id);
}
