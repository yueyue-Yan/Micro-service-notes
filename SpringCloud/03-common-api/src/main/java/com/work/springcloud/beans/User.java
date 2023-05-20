package com.work.springcloud.beans;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * ClassName:User
 * Package:com.work.springcloud.beans
 * Description: 描述信息
 *
 * @date:2023/5/12 18:02
 * @author:yueyue
 */
@Data
@Accessors(chain = true)
public class User implements Serializable {
    private String id;
    private String username;
    private String password;
    private String applicationName;
}