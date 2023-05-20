package com.work.springcloud.service;

import com.work.springcloud.feignService.OrderService;
import com.work.springcloud.feignService.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName:BusinessServiceImpl
 * Package:com.work.springcloud.service
 * Description: 描述信息
 *
 * @date:2023/5/17 21:45
 * @author:yueyue
 */
@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private StorageService storageService;

    @Autowired
    private OrderService orderService;

    @Override
    public void purchase(String userId, String commodityCode, int orderCount) {
        storageService.deduct(commodityCode, orderCount);

        orderService.create(userId, commodityCode, orderCount);
    }
}
