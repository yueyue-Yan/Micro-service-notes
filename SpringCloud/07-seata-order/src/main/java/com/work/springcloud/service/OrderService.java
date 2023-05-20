package com.work.springcloud.service;

import com.work.springcloud.beans.Order;

/**
 * ClassName:OrderService
 * Package:com.work.springcloud.service
 * Description: 描述信息
 *
 * @date:2023/5/17 21:57
 * @author:yueyue
 */
public interface OrderService {
    /**创建订单*/
    Order create(String userId, String commodityCode, int orderCount);
}
