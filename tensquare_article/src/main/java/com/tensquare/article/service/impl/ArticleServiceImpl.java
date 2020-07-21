package com.tensquare.article.service.impl;

import com.tensquare.article.dao.ArticleDao;
import com.tensquare.article.pojo.Article;
import com.tensquare.article.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by asus on 2019/9/8.
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private IdWorker idWorker;
    @Override
    public List<Article> findAll() {
        return articleDao.findAll();
    }

    @Override
    public Article findById(String id) {
        return articleDao.findById(id).get();
    }

    @Override
    public void add(Article article) {
        article.setId(idWorker.nextId()+"");
        articleDao.save(article);
    }

    @Override
    public void update(Article article) {
        articleDao.save(article);
    }

    @Override
    public void deldteById(String id) {
        articleDao.deleteById(id);
    }

    @Override
    public Page<Article> findSearch(Map searchMap, int page, int size) {
        Specification<Article>specification=createSpecification(searchMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return articleDao.findAll(specification,pageRequest);

    }

    @Override
    public List<Article> findSearch(Map whereMap) {
        Specification<Article> specification = createSpecification(whereMap);
        return articleDao.findAll(specification);
    }
    @Transactional
    @Override
    public void examine(String id) {
        articleDao.examine(id);
    }
  @Transactional
   @Override
    public void updateThumbup(String id) {
        articleDao.updateThumbup(id);
    }

    //动态构建条件
    private Specification<Article>createSpecification(Map searchMap){
        return new Specification<Article>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                ArrayList<Predicate> predicates = new ArrayList<>();
                if(searchMap.get("id")!=null&&"".equals(searchMap.get("id"))){
                    predicates.add(cb.like(root.get("id").as(String.class),"%"+(String)searchMap.get("id")+"%"));
                }
                //专栏id
                if(searchMap.get("columnid")!=null && "".equals(searchMap.get("columnid"))){
                    predicates.add(cb.like(root.get("columnid").as(String.class),"%"+(String)searchMap.get("columnid")+"%"));
                }
                //用户id
                if(searchMap.get("userid")!=null && "".equals(searchMap.get("userid"))){
                    predicates.add(cb.like(root.get("userid").as(String.class),"%"+(String)searchMap.get("userid")+"%"));
                }
                //标题
                if(searchMap.get("title")!=null && "".equals(searchMap.get("title"))){
                    predicates.add(cb.like(root.get("title").as(String.class),"%"+(String)searchMap.get("title")+"%"));
                }
                // 文章正文
                if (searchMap.get("content")!=null && !"".equals(searchMap.get("content"))) {
                    predicates.add(cb.like(root.get("content").as(String.class), "%"+(String)searchMap.get("content")+"%"));
                }
                // 文章封面
                if (searchMap.get("image")!=null && !"".equals(searchMap.get("image"))) {
                    predicates.add(cb.like(root.get("image").as(String.class), "%"+(String)searchMap.get("image")+"%"));
                }
                // 是否公开
                if (searchMap.get("ispublic")!=null && !"".equals(searchMap.get("ispublic"))) {
                    predicates.add(cb.like(root.get("ispublic").as(String.class), "%"+(String)searchMap.get("ispublic")+"%"));
                }
                // 是否置顶
                if (searchMap.get("istop")!=null && !"".equals(searchMap.get("istop"))) {
                    predicates.add(cb.like(root.get("istop").as(String.class), "%"+(String)searchMap.get("istop")+"%"));
                }
                // 审核状态
                if (searchMap.get("state")!=null && !"".equals(searchMap.get("state"))) {
                    predicates.add(cb.like(root.get("state").as(String.class), "%"+(String)searchMap.get("state")+"%"));
                }
                // 所属频道
                if (searchMap.get("channelid")!=null && !"".equals(searchMap.get("channelid"))) {
                    predicates.add(cb.like(root.get("channelid").as(String.class), "%"+(String)searchMap.get("channelid")+"%"));
                }
                // URL
                if (searchMap.get("url")!=null && !"".equals(searchMap.get("url"))) {
                    predicates.add(cb.like(root.get("url").as(String.class), "%"+(String)searchMap.get("url")+"%"));
                }
                // 类型
                if (searchMap.get("type")!=null && !"".equals(searchMap.get("type"))) {
                    predicates.add(cb.like(root.get("type").as(String.class), "%"+(String)searchMap.get("type")+"%"));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

    }
}
