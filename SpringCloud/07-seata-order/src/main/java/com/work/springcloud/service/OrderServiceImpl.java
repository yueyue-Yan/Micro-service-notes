package com.work.springcloud.service;

import com.work.springcloud.beans.Order;
import com.work.springcloud.feignService.AccountService;
import com.work.springcloud.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ClassName:OrderServiceImpl
 * Package:com.work.springcloud.service
 * Description: 描述信息
 *
 * @date:2023/5/17 21:57
 * @author:yueyue
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private AccountService accountService;

    @Override
    @Transactional
    public Order create(String userId, String commodityCode, int orderCount) {
        int orderMoney = 100;

        accountService.debit(userId, orderMoney);

        Order order = new Order();
        order.userId = userId;
        order.commodityCode = commodityCode;
        order.count = orderCount;
        order.money = orderMoney;

        int flag = orderMapper.insert(order);
        if(flag == 0){
            return null;
        }
        return order;


    }

}
