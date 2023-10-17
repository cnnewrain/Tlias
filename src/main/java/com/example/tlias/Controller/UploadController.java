package com.example.tlias.Controller;

import com.example.tlias.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

//加上这个注解那么这个类的bean就会延迟初始化，延迟到第一次使用的时候(正常情况是IOC容器创建bean就被创建了)
//@Lazy

//这个注解是和bean作用域有关,他把这个类的bean设置成为非单列模式的,即每次使用该类的对象都要重新创建一个该类的bean对象。测试在:TliasApplicationTests
//bean的作用域关键字在TliasApplicationTests里面
@Scope("prototype")
@Slf4j
@RestController
public class UploadController {

    /**
     * 文件上传
     * @param username
     * @param age
     * @param image
     * @return
     */
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public Result upload(String username, Integer age, MultipartFile image) throws Exception{
        log.info("文件上传:{},{},{}",username,age,image);

        /**
         * 注意:如果用户上传的文件名字相同，那么后者上传的文件就会覆盖前者上传的文件
         * 所以我们要构造唯一文件,方法：uuid(通用唯一识别码)
         *
         * UUID.randonUUID()生成一串随机的识别码,具有唯一性
         *
         * 在SpringBoot中，文件上传，默认单个文件允许最大大小为1M
         */

        String Filename=image.getOriginalFilename();//获取上传的文件名字

        int index = Filename.lastIndexOf(".");//如果文件名是0.00.0.jpg,那么就获取最后一个小数点的位置,类型是int
        String extName = Filename.substring(index);//然后截取文件名,位置就是从前面获得的index开始,也就是最后一个小数点开始一直到结尾,获得文件的扩展名

        String onlyFileName = UUID.randomUUID()+extName;
        log.info("用UUID生成的唯一标识码的文件名:{}",onlyFileName);
        image.transferTo(new File("D:\\win\\"+ onlyFileName)); //文件放置的路径,后面是识别码(唯一)和文件扩展名
        return Result.success();

        /**multiPartFile中提供的几种方法
         *String getOriginalFilename();//获取原始文件名
         * void transferTo(File dest);/将接收的文件转存到磁盘文件中long getSize();l/获取文件的大小，单位:字节
         * byte[] getBytes();l/获取文件内容的字节数组
         * InputStream getInputStream();//获取接收到的文件内容的输入流
         */


        /**
         * ConfigurationProperties与@Value
         * @Value注解只能一个一个的进行外部属性的注入。
         * @ConfigurationProperties可以批量的将外部的属性配置注入到bean对象的属性中
         * 具体用法百度
         */
    }
}
