package com.work.springcloud.beans;

import java.io.Serializable;

/**
 * ClassName:Order
 * Package:com.work.springcloud.beans
 * Description: 描述信息
 *
 * @date:2023/5/17 19:51
 * @author:yueyue
 */
public class Order implements Serializable {

    public String userId;
    public String commodityCode;
    public int    count;
    public int    money;
}

