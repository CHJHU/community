package com.nowcoder.community.service;

import com.nowcoder.community.dao.AlphaDao;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
//能够被容器扫描并装配的注解（对于业务是Service）
@Service
//@Scope("prototype")
public class AlphaService {

    @Autowired
    private AlphaDao alphaDao;
    //构造方法（构造器）
    public AlphaService(){
        System.out.println("实例化AlphaService");
    }
    //在构造之后调用的注解
    @PostConstruct
    public void init(){
        System.out.println("初始化AlphaService");
    }
    //在销毁之前调用的注解
    @PreDestroy
    public void destory(){
        System.out.println("销毁AlphaService");
    }
    public String find(){
        return alphaDao.select();
    }
}
