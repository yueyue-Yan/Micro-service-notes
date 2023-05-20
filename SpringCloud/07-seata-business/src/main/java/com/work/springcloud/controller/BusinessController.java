package com.work.springcloud.controller;

import com.work.springcloud.service.BusinessService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:BusinessController
 * Package:com.work.springcloud.controller
 * Description: 描述信息
 *
 * @date:2023/5/17 21:41
 * @author:yueyue
 */
@RestController
@RequestMapping("/business")
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    @GetMapping("/purchase/{userId}/{commodityCode}/{orderCount}")
    @GlobalTransactional
    public String purchase(@PathVariable String userId, @PathVariable String commodityCode, @PathVariable int orderCount){
        businessService.purchase(userId,commodityCode,orderCount);
        return "采购完成...";
    }

}
