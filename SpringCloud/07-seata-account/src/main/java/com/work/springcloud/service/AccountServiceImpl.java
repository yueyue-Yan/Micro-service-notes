package com.work.springcloud.service;

import com.work.springcloud.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ClassName:AccountServiceImpl
 * Package:com.work.springcloud.service
 * Description: 描述信息
 *
 * @date:2023/5/17 21:14
 * @author:yueyue
 */
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountMapper accountMapper;

    @Override
    @Transactional
    public void debit(String userId, int money) {
        accountMapper.updateByUserId(userId,money);
    }
}
