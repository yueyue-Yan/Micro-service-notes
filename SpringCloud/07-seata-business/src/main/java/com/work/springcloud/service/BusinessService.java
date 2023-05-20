package com.work.springcloud.service;

/**
 * ClassName:BusinessService
 * Package:com.work.springcloud.service
 * Description: 描述信息
 *
 * @date:2023/5/17 21:43
 * @author:yueyue
 */
public interface BusinessService {
    /**采购*/
    public void purchase(String userId, String commodityCode, int orderCount) ;
}
