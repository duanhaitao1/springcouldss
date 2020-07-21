package com.tensquare.article.dao;

import com.tensquare.article.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by asus on 2019/9/8.
 */
public interface ArticleDao extends JpaRepository<Article,String>
,JpaSpecificationExecutor<Article>
{
    /*
    * 在@Query注解中编写JPQL实现DELETE和UPDATE操作的时候必须加上@modifying注解，
    * 以通知Spring Data 这是一个DELETE或UPDATE操作。*/
    @Modifying
    @Query("update Article a set a.state = '1' where a.id = ?1  ")
    void examine(String id);
    /**
     * 文章点赞
     * @param id
     */
    @Query("update Article set thumbup = thumbup + 1  where id = ?1 ")
    @Modifying
    void updateThumbup(String id);
}
