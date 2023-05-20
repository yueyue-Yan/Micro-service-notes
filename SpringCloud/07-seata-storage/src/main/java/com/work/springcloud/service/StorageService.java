package com.work.springcloud.service;

/**
 * ClassName:StorageService
 * Package:com.work.springcloud.service
 * Description: 描述信息
 *
 * @date:2023/5/17 22:11
 * @author:yueyue
 */
public interface StorageService {

    /**
     * 扣除存储数量
     */
    void deduct(String commodityCode, int count);
}
