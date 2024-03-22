package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {
    //为实现个人主页和分页的功能，userId方便查找自己发过的帖子
    // offset是每一页起始行的行号，limit是每一页最多显示的数据量
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);
    //一共有多少页需要查询出表里一共有多少条数据
    int selectDiscussPostRows(@Param("userId") int userId);//注解的作用是给参数取一个别名，可以相同
    //当sql需要用到动态条件（<if>），并且条件（<if>）中会用到这个参数，这个参数必须有别名，否则会报错



}
