package com.xiaour.spring.boot.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiaour.spring.boot.entity.UserInfo;
import com.xiaour.spring.boot.mapper.UserInfoMapper;
import com.xiaour.spring.boot.utils.JsonUtil;
import com.xiaour.spring.boot.utils.RedisUtil;
import com.xiaour.spring.boot.utils.aes.AuthHelper;

/**
 * Created by xiaour on 2017/4/19.
 */
@RestController
@RequestMapping(value="/test")
public class TestCtrl {
	
	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired  
    private UserInfoMapper userInfoMapper;  

    @RequestMapping(value="/index")
    public String index(){
        return "hello world";
    }
    
    /**
     * 向redis存储值
     * @param key
     * @param value
     * @return
     * @throws Exception
     */
    @RequestMapping("/set")  
    public String set(String key, String value) throws Exception{  
    	redisUtil.set(key, value);  
        return "success";  
    }  
    
    /**
     * 获取redis中的值
     * @param key
     * @return
     */
    @RequestMapping("/get")  
    public String get(String key){  
        try {
			return redisUtil.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";  
    }  
    
    /**
     * 获取数据库中的用户
     * @param id
     * @return
     */
    @RequestMapping("/getUser/{id}")  
    public String get(@PathVariable("id")int id){  
        try {
        	UserInfo user= userInfoMapper.selectByPrimaryKey(id);
			return JsonUtil.getJsonString(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";  
    }  
    
    /**
     * 获取数据库中的用户
     * @param id
     * @return
     */
    @RequestMapping("/getAccessToken")  
    public String getAccessToken(){  
        try {
        	Map<String,Object> data= new HashMap<>();
        	data.put("access_token", AuthHelper.getAccessToken());
			return JsonUtil.getJsonString(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";  
    } 
    
    /**
     * 获取数据库中的用户
     * @param id
     * @return
     */
    @RequestMapping("/ding")  
    public String ding(HttpServletRequest request){  
        try {
			return AuthHelper.getConfig(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";  
    } 
    
    @RequestMapping("/userinfo")  
    public String userinfo(String code){  
        try {
        	String jsonStr=AuthHelper.getUserinfo(code);
        	System.err.println(jsonStr);
			return jsonStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";  
    } 
    
    @RequestMapping("/snsUser")  
    public String snsUser(String code){  
        try {
        	String jsonStr=AuthHelper.snsTokenUser(code);
        	System.err.println(jsonStr);
			return jsonStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";  
    } 
}
