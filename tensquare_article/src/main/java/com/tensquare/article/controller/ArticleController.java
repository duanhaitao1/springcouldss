package com.tensquare.article.controller;

import com.tensquare.article.pojo.Article;
import com.tensquare.article.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import util.IdWorker;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by asus on 2019/9/8.
 * dgggkjakdkjadka1
 */
@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    //kdkkdkkd
    @Autowired
    private RedisTemplate redisTemplate;

    //查询全部数据
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",articleService.findAll());
    }
    //根据id查询
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable String id){
       return new Result(true,StatusCode.OK,"查询成功",articleService.findById(id));

    }
    //添加
    @RequestMapping(method=RequestMethod.POST)
    public  Result add(@RequestBody  Article article){
        articleService.add(article);
        return new Result(true,StatusCode.OK,"增加成功");
    }
    //修改
    @RequestMapping(value="/{id}",method= RequestMethod.PUT)
    public Result update(@RequestBody Article article,@PathVariable String id){
        article.setId(id);
        articleService.update(article);
        return new Result(true,StatusCode.OK,"修改成功");
    }
    //删除
    @RequestMapping(value = "/{id}",method =RequestMethod.DELETE)
    public Result delete(@PathVariable String id){
        articleService.deldteById(id);
        return new Result(true,StatusCode.OK,"删除成功");

    }
    // 条件查询+分页 SpringData 分页
    @RequestMapping(value = "/search/{page}/{size}",method =RequestMethod.POST)
    public  Result findSearch(@PathVariable Map searchMap, @PathVariable  int page, @PathVariable  int size){
        Page<Article>pageList=articleService.findSearch(searchMap,page,size);
        //Specification<Article> specification = createSpecification(whereMap);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<Article>(pageList.getTotalElements(),pageList.getContent())) ;
    }
    //根据条件查询
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",articleService.findSearch(searchMap));
    }
    //文章审核
    @RequestMapping(method = RequestMethod.PUT,value = "/examine/{id}")
    public Result examine(@PathVariable("id")String id){
        articleService.examine(id);
        return new Result(true,StatusCode.OK,"审核成功");
    }
    //文章审核
    @RequestMapping(method = RequestMethod.PUT,value = "/thumbup/{id}")
    public Result  updateThumbup(@PathVariable("id") String id){
        //判断用户是否点赞过
        String userId="1111";
        Object value=redisTemplate.opsForValue().get("userid_"+userId+"articleid"+id);
        if(value!=null){
          throw  new IllegalStateException  ("请不要重复点赞！");
        }
        articleService.updateThumbup(id);
        //3.把点赞信息存入redis
       // redisTemplate.opsForValue().set("userid_"+userid+"_articleid_"+id,"thumpup",30, TimeUnit.SECONDS);
        return new Result(true,StatusCode.OK,"点赞成功");
    }


}
