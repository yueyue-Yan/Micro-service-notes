package com.work.springcloud.controller;

import com.work.springcloud.utils.JwtUtils;
import com.work.springcloud.utils.Result;
import com.work.springcloud.beans.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

//import com.work.springcloud.utils.JwtUtils;

/**
 * ClassName:UserController
 * Package:com.work.springcloud.controller
 * Description: 描述信息
 *
 * @date:2023/5/12 16:00
 * @author:yueyue
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/login")
    public Result login(String username, String password, HttpSession session){

        if(username.equals("Alice") && password.equals("0601")){
            //将用户存入到Session中
            User user = new User()
                    .setId(UUID.randomUUID().toString())
                    .setUsername(username)
                    .setPassword(password)
                    .setApplicationName("Consumer8001");
            session.setAttribute("user",user);
            //登录成功
            return Result.success(0,"登录成功",user);
        }
        //登陆失败
        return Result.error(1,"登录失败，用户名或密码错误...Consumer8002");

    }

    @RequestMapping("/getUserByJwtToken")
    public Result getUser(HttpServletRequest request){
        String token = request.getHeader("token");

        //if(token == null){ return Result.error(1,"当前用户未登录-------token...");}
        //解析token数据
        String data = JwtUtils.vaildToken(token);
        String t = "{ \"token\" :"+token+", \"data\" : "+data +" }";
        return Result.success(0,"查询成功",t);
    }


    @RequestMapping("/getUserBySession")
    public Result getUser(HttpSession session){
        //通过SpringSession获取登录对象
        User user = ((User)session.getAttribute("user"));
        if(user == null){ return Result.error(1,"当前用户未登录-------springSession...");}
        return Result.success(0,"查询成功",user);
    }

}