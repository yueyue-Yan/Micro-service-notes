package com.work.springcloud.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ClassName:AccountMapper
 * Package:com.work.springcloud.mapper
 * Description: 描述信息
 *
 * @date:2023/5/17 21:13
 * @author:yueyue
 */
//@ResponseBody
@Mapper
public class AccountMapper {
    public void updateByUserId(@Param("userId")String userId, @Param("money")int money) {
    }
}
