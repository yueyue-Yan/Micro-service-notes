package com.work.springcloud.controller;

import com.work.springcloud.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:AccountController
 * Package:com.work.springcloud.controller
 * Description: 描述信息
 *
 * @date:2023/5/17 21:13
 * @author:yueyue
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/debit/{userId}/{orderMoney}")
    void debit(@PathVariable String userId, @PathVariable int orderMoney){

        accountService.debit(userId,orderMoney);

    }


}
