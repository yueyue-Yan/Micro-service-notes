package com.work.springcloud.service;

import com.work.springcloud.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * ClassName:ProviderFeignHystrix
 * Package:com.work.springcloud.service
 * Description: 描述信息
 *
 * @date:2023/5/8 20:19
 * @author:yueyue
 */

@Component
@Slf4j
public class ProviderFeignHystrixImpl implements ProviderFeignService {
    @Override
    public Result provider01() {
        log.info("执行了provider01");
        return Result.error(1,"当前访问人数过多，请稍后再试=====provider01");
    }

    @Override
    public Result provider02(Integer id, String username) {
        return Result.error(1,"当前访问人数过多，请稍后再试=====provider02");
    }

    @Override
    public Result provider03(Integer id, String username, String age, String desc) {
        return Result.error(1,"当前访问人数过多，请稍后再试=====provider03");
    }

    @Override
    public Result provider04(Integer id, String username, String age, String desc, Map<String, Object> requestBody) {
        return Result.error(1,"当前访问人数过多，请稍后再试=====provider04");
    }

    @Override
    public Result provider05(Integer id, String username, String age, String desc, Map<String, Object> requestBody) {
        return Result.error(1,"当前访问人数过多，请稍后再试=====provider05");
    }

    @Override
    public Result provider06(Integer id, String username, String age, String desc, Map<String, Object> requestBody) {
        return Result.error(1,"当前访问人数过多，请稍后再试=====provider06");
    }

    @Override
    public Result provider07(Integer id, String username, String age, String desc) {
        return Result.error(1,"当前访问人数过多，请稍后再试=====provider07");
    }

    @Override
    public Result providerfh01() {
        return Result.error(1,"当前访问人数过多，请稍后再试=====providerfh01========fh========");
    }
}
