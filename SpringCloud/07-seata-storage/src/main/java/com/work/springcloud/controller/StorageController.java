package com.work.springcloud.controller;

import com.work.springcloud.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:StorageController
 * Package:com.work.springcloud.controller
 * Description: 描述信息
 *
 * @date:2023/5/17 22:11
 * @author:yueyue
 */
@RestController
@RequestMapping("/storage")
public class StorageController {

    @Autowired
    private StorageService storageService;

    @GetMapping("/deduct/{commodityCode}/{orderCount}")
    void deduct(@PathVariable String commodityCode, @PathVariable int orderCount){
        storageService.deduct(commodityCode,orderCount);
    }

}