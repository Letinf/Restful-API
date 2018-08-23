package com.example.demo.controller;




import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.MyException;


@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello() throws Exception {
        throw new Exception("发生错误/500");//统一异常处理
    }

    @RequestMapping("/json")
    public String json() throws MyException {
        throw new MyException("发生错误2");
    }

//    @RequestMapping("/")
//    public String index(ModelMap map) {
//        map.addAttribute("host", "/404");
//        return "index";
//    }

}