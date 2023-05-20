package com.work.springcloud.controller;

import com.work.springcloud.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:OrderController
 * Package:com.work.springcloud.controller
 * Description: 描述信息
 *
 * @date:2023/5/17 21:55
 * @author:yueyue
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/create/{userId}/{commodityCode}/{orderCount}")
    void create(@PathVariable String userId, @PathVariable String commodityCode, @PathVariable int orderCount){
        orderService.create(userId,commodityCode,orderCount);
    }


}
