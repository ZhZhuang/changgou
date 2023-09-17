package com.changgou.goods.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于测试
 * PDF文件中没有
 * 浏览器输入:http://172.18.19.60:9011/demo/test
 */

@RequestMapping("/demo")
@RestController  // =  @Controller + @RreponseBody
public class DemoController {

    @GetMapping("/test")
    public String demo(){
        return "demo message!";
    }
}
