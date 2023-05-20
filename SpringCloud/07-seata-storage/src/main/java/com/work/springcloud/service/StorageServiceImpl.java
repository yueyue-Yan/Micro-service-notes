package com.work.springcloud.service;

import com.work.springcloud.mapper.StorageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName:StorageServiceImpl
 * Package:com.work.springcloud.service
 * Description: 描述信息
 *
 * @date:2023/5/17 22:12
 * @author:yueyue
 */
@Service
public class StorageServiceImpl implements StorageService {
    @Autowired
    private StorageMapper storageMapper;

    @Override
    public void deduct(String commodityCode, int count) {
            storageMapper.updateByCommodityCode(commodityCode,count);
    }
}
