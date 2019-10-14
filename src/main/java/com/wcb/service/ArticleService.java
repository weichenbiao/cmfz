package com.wcb.service;

import com.wcb.entity.Article;

import java.util.Map;

public interface ArticleService {
    public Map<String, Object> queryAll(Integer page, Integer rows);

    public void add(Article article);

    public void update(Article article);

    public void edit(String[] id, String oper);
}
