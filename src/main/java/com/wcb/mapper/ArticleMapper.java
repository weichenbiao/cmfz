package com.wcb.mapper;

import com.wcb.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper {
    public List<Article> queryAll(@Param("start") Integer start, @Param("rows") Integer rows);

    public Integer count();

    public void add(Article article);

    public void update(Article article);

    public void delete(String[] id);
}
