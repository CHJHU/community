package com.nowcoder.community.controller;

import com.nowcoder.community.service.AlphaService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

//能够被容器扫描并装配的注解（处理请求的组件）
@Controller
//访问路径注解
@RequestMapping("/alpha")
public class AlphaController {
    @Autowired
    private AlphaService alphaService;

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello() {
        return "Hello Spring Boot.";
    }

    @RequestMapping("/data")
    @ResponseBody
    public String getData() {
        return alphaService.find();
    }

    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response) {
        //获取请求数据
        //获取请求方式
        System.out.println(request.getMethod());
        //获取请求路径
        System.out.println(request.getServletPath());
        //获取请求行（键值对的结构）的key，得到一个迭代器，即消息头若干行数据
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();//key，即名字
            String value = request.getHeader(name);//value
            System.out.println(name + ": " + value);
        }

        //请求体数据
        System.out.println(request.getParameter("code"));
        //向浏览器返回响应数据
        response.setContentType("text/html;charset=utf-8");//设定返回的数据类型
        try {
            PrintWriter writer = response.getWriter();//通过response获取一个输出流
            writer.write("<h1>牛客网<h1>");//通过writer向浏览器打印一个网页
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //GET请求（从服务器获取数据时、默认发送的请求）如何处理
    //从服务器获取数据两种方式：
    //方式一：
    //假设查询所有学生
    // /students?current=1&limit=20
    @RequestMapping(path = "students", method = RequestMethod.GET)//强制这个方法必须是GET请求才能访问到
    @ResponseBody
    public String getStudents(
            //把“current”赋值给current，不传也可以，默认值为1
            @RequestParam(name = "current", required = false, defaultValue = "1") int current,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
        //只要参数名和传过来的参数名保持一致就可以
        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }

    //方式二：
    //根据学生的编号查询一个学生
    // /student/123 或者 /student?id=123
    @RequestMapping(path = "/student/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id) {//从路径当中得到这个变量并且赋值给这个参数
        System.out.println(id);
        return "a student";
    }

    //浏览器向服务器提交请求的时候一般采用POST请求（不用GET）
    //提交数据前，浏览器需要打开一个带有表单的网页，通过表单填写数据之后才能提交给服务器
    //创建一个静态网页：resources/static.html/student.html
    @RequestMapping(path = "student", method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name, int age) {
        //声明的参数的名字和表单中参数的名字一致，就会自动传过来
        System.out.println(name);
        System.out.println(age);
        return "success";
    }

    //如何向浏览器返回响应数据（动态html数据）
    //将老师相关数据响应给浏览器
    //第一种方式
    @RequestMapping(path = "/teacher",method = RequestMethod.GET)
    public ModelAndView getTeacher(){
        //ModelAndView对象封装的是给DispatcherServlet返回的Model和View两份数据
        ModelAndView mav =new ModelAndView();
        mav.addObject("name","张三");
        mav.addObject("age","30");
        //往对象中设置一个模板的路径和名字
        mav.setViewName("/demo/view");//templates/demo/view.html
        return mav;
    }
    //第二种方式（推荐）
    @RequestMapping(path = "/school",method = RequestMethod.GET)
    public String getSchool(Model model){
        model.addAttribute("name","北京大学");
        model.addAttribute("age","80");
        return "/demo/view";
    }

    //向浏览器响应JSON数据（异步请求）
    //（服务器）Java对象 -> JSON字符串 -> JS对象（浏览器）
    @RequestMapping(path = "/emp",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getEmp(){
        Map<String, Object> emp = new HashMap<>();
        emp.put("name","张三");
        emp.put("age",23);
        emp.put("salary",8000.00);
        return emp;
    }

    @RequestMapping(path = "/emps",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getEmps(){
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> emp = new HashMap<>();
        emp.put("name","张三");
        emp.put("age",23);
        emp.put("salary",8000.00);
        list.add(emp);

        emp.put("name","李四");
        emp.put("age",24);
        emp.put("salary",9000.00);
        list.add(emp);

        emp.put("name","王五");
        emp.put("age",25);
        emp.put("salary",10000.00);
        list.add(emp);
        return list;
    }


}
