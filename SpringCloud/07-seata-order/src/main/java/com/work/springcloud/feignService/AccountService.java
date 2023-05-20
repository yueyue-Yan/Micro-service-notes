package com.work.springcloud.feignService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * ClassName:AccountService
 * Package:com.work.springcloud.feignService
 * Description: 描述信息
 *
 * @date:2023/5/17 21:58
 * @author:yueyue
 */
@FeignClient("07-seata-account")
public interface AccountService {
    @GetMapping("/account/debit/{userId}/{orderMoney}")
    void debit(@PathVariable String userId, @PathVariable int orderMoney);
}
