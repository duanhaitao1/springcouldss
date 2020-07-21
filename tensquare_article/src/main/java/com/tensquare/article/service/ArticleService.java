package com.tensquare.article.service;

import com.tensquare.article.pojo.Article;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by asus on 2019/9/8.
 */
public interface ArticleService {
    List<Article> findAll();

    Article findById(String id);

    void add(Article article);

    void update(Article article);

    void deldteById(String id);

    Page<Article> findSearch(Map searchMap, int page, int size);
    List<Article> findSearch(Map whereMap);

    void examine(String id);

    void updateThumbup(String id);
}
