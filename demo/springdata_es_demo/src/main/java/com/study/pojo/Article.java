package com.study.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author lj
 * date : 2019/1/21 19:54
 */
@Document(indexName = "blog",type = "article")
public class Article {
    @Id
    private Integer id;
    @Field(store = true,analyzer = "ik_smart",searchAnalyzer = "ik_smart",type = FieldType.Text)
    private String title;
    @Field(store = true,analyzer = "ik_smart",searchAnalyzer = "ik_smart",type = FieldType.Text)
    private String content;

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
