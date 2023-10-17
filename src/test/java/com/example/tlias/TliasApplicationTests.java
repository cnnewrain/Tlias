package com.example.tlias;


import com.example.tlias.Controller.DeptController;
import com.example.tlias.Controller.EmpController;
import com.example.tlias.Controller.UploadController;
import com.example.tlias.mapper.DeptMapper;
import com.example.tlias.pojo.Dept;
import com.example.tlias.service.DeptService;
import com.example.tlias.service.impl.DeptServiceImpl;
import org.apache.ibatis.annotations.Select;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.*;
import org.springframework.context.ApplicationContext;

import java.util.Base64;
import javax.crypto.SecretKey;
import javax.xml.crypto.Data;

@SpringBootTest
class TliasApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test//这是使用java类解码Base64类型的数据
    public void testBase64(){
        String encodedString = "eyJuYW1lIjoia2lzc3Nob3QiLCJpZCI6MSwiZXhwIjoxNjkwMjE3NjQ5fQ";
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        String decodedString = new String(decodedBytes);
        System.out.println(decodedString);
    }


    @Test
    public void testJwT(){//将数据封装成JWT令牌
//        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        Map<String, Object> claims=new HashMap<>();
        claims.put("id",1);
        claims.put("name","kissshot");
        String Jwt=Jwts.builder()//接收生成的JWT令牌
                .signWith(SignatureAlgorithm.HS256,"kissshot")//JWT签名算法
                .setClaims(claims)//JWT载荷部分
                .setExpiration(new Date(System.currentTimeMillis()+3600*1000))//设置JWT令牌的有效期为1个小时
                .compact();
        System.out.println(Jwt);
    }


    @Test
    public void testParseJWT(){
        Jws<Claims> jws=Jwts.parser()
                .setSigningKey("kissshot")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoia2lzc3Nob3QiLCJpZCI6MSwiZXhwIjoxNjkwMjc2OTQ0fQ.BH1Zgcehq4lGKVk1seJujEJduUOR1eSIQXK2mmIq73c");
        Claims claims = jws.getBody();
        System.out.println(claims);
    }


    @Autowired
    private ApplicationContext applicationContext;
    @Test
    public void testGetBean(){
        //根据名称获取bean对象
        DeptController bean1 = (DeptController) applicationContext.getBean("deptController");
        System.out.println(bean1);

        //根据类型获取bean对象
        DeptController bean2 = applicationContext.getBean(DeptController.class);
        System.out.println(bean2);

        //根据名称和类型获取
        DeptController bean3 = applicationContext.getBean("deptController", DeptController.class);
        System.out.println(bean3);

        //这三次输出的bean地址都是一样的，说明调用的是一个bean对象。因为这是单列模式(可以通过更改bean的作用域改变单列模式)

        /**
         * 上述所说的【Spring项目启动时，会把其中的bean都创建好】还会受到作用域及延迟初始化影响，这里主要针对于默认的单例非延迟加载的bean而言。
         *
         *
         *bean的作用域---关键字加在相关类上面--要使用到注解@Scope("作用域关键字")
         * singleton:容器内同名称的bean只有一个实例（单例)（默认)
         * prototype:每次使用该bean时会创建新的实例（非单例)
         * request:每个请求范围内会创建新的实例(web环境中，了解)
         * session:每个会话范围内会创建新的实例(web环境中，了解)
         * application:每个应用范围内会创建新的实例( web环境中，了解)
         */

        for(int i=0;i<5;i++){
            UploadController bean = applicationContext.getBean(UploadController.class);
            System.out.println("bean"+i+":"+bean);
        }
/**
 * 前三个是单列模式下的，地址都一样调用的是用一个bean对象，后面五个是将bean的作用域改为了prototype，每次使用对象都要重新创建一个bean，所以地址各不相同
 * com.example.tlias.Controller.DeptController@51b59d58
 * com.example.tlias.Controller.DeptController@51b59d58
 * com.example.tlias.Controller.DeptController@51b59d58
 * bean0:com.example.tlias.Controller.UploadController@6c8d8b60
 * bean1:com.example.tlias.Controller.UploadController@4fa6fb7f
 * bean2:com.example.tlias.Controller.UploadController@2520010e
 * bean3:com.example.tlias.Controller.UploadController@163fbbc9
 * bean4:com.example.tlias.Controller.UploadController@1669f4e5
 * 
 * 注意事项；
 * 默认singleton的bean，在容器启动时被创建，可以使用@Lazy注解来延迟初始化（延迟到第一次使用时)。
 * prototype的bean，每一次使用该bean的时候都会创建一个新的实例。
 * 实际开发当中，绝大部分的Bean是单例的，也就是说绝大部分Bean不需要配置scope属性。
 */
    }

}


/**
下面是一个简单的示例代码，它演示了如何使用Base64类来对字符串进行Base64编码：
import java.util.Base64;

public class Base64EncodeExample {
    public static void main(String[] args) {
        String originalString = "Hello, world!";
        byte[] encodedBytes = Base64.getEncoder().encode(originalString.getBytes());
        String encodedString = new String(encodedBytes);
        System.out.println(encodedString);
    }
}


 您可以使用以下代码来解码Base64编码的字符串并在控制台中输出解码后的字符串：
 import java.util.Base64;

 public class Base64DecodeExample {
 public static void main(String[] args) {
 String encodedString = "eyJuYW1lIjoia2lzc3Nob3QiLCJpZCI6MSwiZXhwIjoxNjkwMjE3NjQ5fQ";
 byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
 String decodedString = new String(decodedBytes);
 System.out.println(decodedString);
 }
 }


 它演示了如何使用org.json:json库来解析JSON字符串并获取其中的数据：
 import org.json.JSONObject;

 public class JSONExample {
 public static void main(String[] args) {
 String jsonString = "{\"name\":\"kissshot\",\"id\":1,\"exp\":1690217649}";
 JSONObject jsonObject = new JSONObject(jsonString);
 String name = jsonObject.getString("name");
 int id = jsonObject.getInt("id");
 long exp = jsonObject.getLong("exp");
 System.out.println("name: " + name);
 System.out.println("id: " + id);
 System.out.println("exp: " + exp);
 }
 }

 **/