package com.tecode.g02.controller;

import com.tecode.bean.User;
import com.tecode.g02.service.G02ZXLUserService;
import com.tecode.util.hbase.table.HBaseUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.hadoop.hbase.client.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * 1.在接收html请求的java文件的类上添加@Controller
 */
@Controller
public class G02ZXLNameController {
    /**
     *需要调用业务层（Services)的方法时 声明的对象 类型为接口， 添加@Autowired，实现对该对象的实例化。
     */
    @Autowired
    private G02ZXLUserService userService;


    /**
     * 用户登录方法
     *
     * 1.其中 @RequestMapping(value = "/userLogin", method = RequestMethod.POST) 表示html页面请求到该方法的URL地址的映射
     *      value = "/userLogin" ： html的请求地址
     *      method = RequestMethod.POST：表示请求的方式
     * 2.@RequestBody:表示接收josn类型的参数
     *          注意User对象中的属性名称，必须和html页面传递的参数的名称完全相同，包括大小写。
     *
     * 3.@ResponseBody:：表示把返回的值封装成json进行返回
     */
    @ResponseBody
    @RequestMapping(value = "/selectperson", method = RequestMethod.POST)
    public Map<String,Object> getUserByName(User user){
        /**
         *1.验证参数的合法性
         * 2.调用业务逻辑层处理业务，并获得返回值
         * 3.判断返回的结果是否为空
         *          如果不为空 返回封装了user对象的list
         * 4.讲返回结果封装成map集合
         * 5.返回map
         *
         *
         *
         */
        //用一个变量来接收传入的姓名
        String name=user.getName();

        //创建一个map集合用来存放返回的结果
        Map<String,Object> map=new HashedMap();

        //对传入的姓名进行判断，如果为空则直接返回，不为空则调用Service层中查找用户的方法
        if(name!=null&&!name.matches("[ ]+")&&!name.equals("")){
            try {
                //获取从Service层返回的结果
                List<User> userList=userService.findUser(name);

                //如果结果为空，则返回false，不为空返回true和list集合
                if(userList.size()==0||userList==null){
                    map.put("success",false);
                    map.put("msg","查找不到此用户");
                }else{
                    map.put("success",true);
                    map.put("data",userList);
                }
                return map;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        //如果输入的参数不符合规则，返回false并加上错误提示
        map.put("success",false);
        map.put("msg","输入的名字不能为空");

        return map;
    }

}
