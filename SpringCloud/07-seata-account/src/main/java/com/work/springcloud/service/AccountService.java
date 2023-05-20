package com.work.springcloud.service;

/**
 * ClassName:AccountService
 * Package:com.work.springcloud.service
 * Description: 描述信息
 *
 * @date:2023/5/17 21:14
 * @author:yueyue
 */
public interface AccountService {
    /**
     * 从用户账户中借出
     */
    void debit(String userId, int money);
}
