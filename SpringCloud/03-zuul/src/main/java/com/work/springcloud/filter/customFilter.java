package com.work.springcloud.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/**
 * ClassName:customFilter
 * Package:com.work.springcloud.filter
 * Description: 描述信息
 *
 * @date:2023/5/10 10:20
 * @author:yueyue
 */
@Component
public class customFilter extends ZuulFilter {
    //过滤器类型：pre post error
    @Override
    public String filterType() {
        return "pre";
    }
    //过滤器优先级：数字越小优先级越高，支持负数
    @Override
    public int filterOrder() {
        return 0;
    }
    //是否启用过滤器
    @Override
    public boolean shouldFilter() {
        return true;
    }
    //过滤器的业务逻辑
    //过滤器的返回值，是任意类型，代表继续向下转发请求操作
    @Override
    public Object run() throws ZuulException {
        System.out.println("过滤器启动----正在执行------");

          /**
            权限校验，要求必须传递Token参数，否则没有权限
                Token：令牌，令牌是被加密的，按照公司中规定的加密算法，进行加密。
                    可逆的加密算法：AES、DES、RSA，通过明文进行加密，可以通过指定的算法进行转换成明文
                    不可逆的加密算法：MD5，加密后的数据不能够转换为明文数据


            公司中的权限校验操作：
                1. 使用框架的方式，Shiro/SpringSecurity，都可以关联数据库进行操作
                    在权限校验时，会通过数据库将当前用户的角色信息和权限信息全部查询出来
                    角色信息：代表当前用户的属性标识，比如管理员、经理、销售员...
                    权限信息：代表当前用户具体的操作。
                        比如管理员，可以访问任意的请求。
                        比如销售员，只可以订单的查询、新增操作。
                        比如经理，可以对订单进行增删改查操作，可以对销售员进行新增、修改、查询或删除操作
                2. 通过令牌进行权限校验，只需要准备加密和解密、验证的算法即可，工具类。
                    例如P2P金融或电商，这些都是属于互联网项目，所有的用户的角色基本都相同
                    可以通过一些标识进行设置，加密一个字符串，可能是User对象
                        {
                            id=xxx,
                            username=xxx,
                            password='',
                            roles=[{roleName=xxx,roleDesc=xxx,id=xxx}...],
                            permissions=[{permissionName=xxx,permissionDesc=xxx,permissionUrl=xxx,id=xxx}...]
                        }
                        qwejklrjklqwheripouyhiow3h4jkl1blakjsdfhiouyhwe45kjl1hr
                3. 通过Session存储用户对象信息，每台服务器只能部署一个项目，尤其是分布式的项目，将一个项目拆分成多个
                    Crm,直接就可以
                    Crm,拆分成市场活动、用户模块、线索模块...每台服务器都有独立的JVM中的Session容器
                    需要将用户在每台Session中都能够存储吗？
                        此时如果使用Session进行存储时，会出现Session无法共享的问题
                        需要使用第三方的插件来解决Session共享的问题，SpringSession进行解决
                    SpringSession：
                        它通过Spring整合Redis，提供一个公共的容器进行存储，所有的服务器都可以向redis获取同一个用户对象
                        这样就解决了用户的session共享的问题了
         */

        //获取请求上下文对象
        RequestContext currentContext = RequestContext.getCurrentContext();
        //获取当前请求对象请求参数中的token数据（用户的身份令牌）
        String token = currentContext.getRequest().getParameter("token");

        //如果没有传递token或传递的是不合法的token规则
        if(token == null || token.length() == 0){
            //通过response响应数据

            String json = "{\"code\":1,\"msg\":\"当前请求异常token，请登录后重试...\"}";
            //设置响应体的数据
            currentContext.setResponseBody(json);
            //是否通过Zuul响应
            currentContext.setSendZuulResponse(false);
            //设置响应头信息：以html或文本响应编码格式为utf-8
            currentContext.addZuulResponseHeader("content-type", "text/html;charset=utf-8");
            //设置响应编码为401，表示权限不足
            currentContext.setResponseStatusCode(401);
        }
        else {
            System.out.println("用户携带了身份令牌,需要进一步验证这个身份是否真的合法");
        }
        //校验通过，进行转发操作
        return null;
        //http://localhost:5001/api/consumer/03?token=111  -->{"msg":"远程调用成功","code":0,"data":{"id":2002,"age":"22","username":"yueyue","desc":"我是yueyue"},"provider":7001,"consumer":8002}
        //http://localhost:5001/api/consumer/03 --> {"code":1,"msg":"当前请求异常token，请登录后重试..."}
    }
}
