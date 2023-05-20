package com.work.springcloud.feignService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * ClassName:OrderService
 * Package:com.work.springcloud.feignService
 * Description: 描述信息
 *
 * @date:2023/5/17 21:42
 * @author:yueyue
 */
@FeignClient(name = "07-seata-order")
public interface OrderService {
    @GetMapping("/order/create/{userId}/{commodityCode}/{orderCount}")
    void create(@PathVariable String userId, @PathVariable String commodityCode, @PathVariable int orderCount);
}
