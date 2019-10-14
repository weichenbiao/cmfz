package com.wcb.serviceimpl;

import com.wcb.entity.Article;
import com.wcb.mapper.ArticleMapper;
import com.wcb.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        Integer count = articleMapper.count();
        Integer start = (page - 1) * rows;
        List<Article> articles = articleMapper.queryAll(start, rows);
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("records", count);
        hashMap.put("page", page);
        Integer total = count % rows == 0 ? count / rows : count / rows + 1;
        hashMap.put("total", total);
        hashMap.put("rows", articles);
        return hashMap;
    }

    @Override
    public void add(Article article) {
        article.setId(UUID.randomUUID().toString().replace("-", ""));
        article.setCreateDate(new Date());
        articleMapper.add(article);
    }

    @Override
    public void update(Article article) {
        articleMapper.update(article);
    }

    @Override
    public void edit(String[] id, String oper) {
        if ("del".equals(oper)) {
            articleMapper.delete(id);
        }
    }
}
