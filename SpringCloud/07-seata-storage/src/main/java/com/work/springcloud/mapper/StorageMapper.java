package com.work.springcloud.mapper;

/**
 * ClassName:StorageMapper
 * Package:com.work.springcloud.mapper
 * Description: 描述信息
 *
 * @date:2023/5/17 22:12
 * @author:yueyue
 */
public interface StorageMapper {
    int updateByCommodityCode(String commodityCode, int count);
}
