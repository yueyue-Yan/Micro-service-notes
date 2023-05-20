package com.work.springcloud.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:Result
 * Package:com.work.springcloud.utils
 * Description: 描述信息
 *
 * @date:2023/5/7 22:22
 * @author:yueyue
 */
public class Result extends HashMap<String, Object> {
    public static Result success(){
        Result result = new Result();
        result.put("code",0);
        result.put("msg","操作成功！");
        result.put("consumer",8005);
        return result;
    }
    public static Result success(Integer code){
        Result result = new Result();
        result.put("code",code);
        result.put("msg","操作成功！");
        return result;
    }
    public static Result success(Integer code,String msg){
        Result result = new Result();
        result.put("code",code);
        result.put("msg",msg);
        return result;
    }
    public static <T> Result success(Integer code,String msg,T data){
        Result result = new Result();
        result.put("code",code);
        result.put("msg",msg);
        result.put("data",data);
        return result;
    }
    public static Result success(Map<String,Object> map){
        Result result = new Result();
        result.putAll(map);
        return result;
    }

    public static Result error(String msg){
        Result result = new Result();
        result.put("code",1);
        result.put("msg",msg);
        return result;
    }
    public static Result error(Integer code,String msg){
        Result result = new Result();
        result.put("code",code);
        result.put("msg",msg);
        return result;
    }



}
