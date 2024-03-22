package com.nowcoder.community.dao;

import org.springframework.stereotype.Repository;
//能够被容器扫描并装配的注解（对于访问数据库的Bean采用@Repository）
@Repository("alphaHibernate")
public class AlphaDaoHibernatelmpl implements AlphaDao{
    @Override
    public String select(){
        return "Hibernate";
    }
}
