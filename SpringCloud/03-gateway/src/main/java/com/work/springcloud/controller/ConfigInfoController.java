package com.work.springcloud.controller;


import com.work.springcloud.beans.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:ConfigInfoController
 * Package:com.work.springcloud.controller
 * Description: 描述信息
 *
 * @date:2023/5/12 22:21
 * @author:yueyue
 */
@RestController
@RefreshScope//自动更新配置内容的注解
public class ConfigInfoController {

    /*
     *  当网关启动时，会加载远程仓库中的配置信息
     *      配置文件中加载了application-dev.properties的config.info属性
     *      注入到本地中，返回到浏览器中加载
     */
    @Value("${config.info}")
    String configInfo;

    @GetMapping("/getConfigInfo")
    @ResponseBody
    public Result getConfigInfo(){
        return Result.success(0,"获取成功...",configInfo);
    }

}
