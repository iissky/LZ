package com.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.api.json.User;

@Controller
public class TestRest {
	@Autowired
	RestTemplate rest;

	@RequestMapping("/testRest")
	public @ResponseBody String tesy() {
		Object obj = null;
		try {
			User user1 = new User();
			user1.setUsername("15274953596");
			user1.setPassword("15274953596");
			obj = rest.postForEntity("http://a1.easemob.com/1113190815243420/lzapp/users", user1, Object.class);
			System.out.println(obj);
		} catch (Exception e) {
			System.out.println("400错误==============="+e.getMessage());
			e.printStackTrace();
			return "shibai";
		}
		return obj.toString();
	}
	
	public static void main(String[] args) {
		 
        List<Integer> integers = randomRedeEnvelope(10, 10);
        System.out.println(integers);
        int sum = 0;
        for (Integer integer : integers) {
            BigDecimal divide = new BigDecimal(integer).divide(new BigDecimal(100));
            System.out.println(divide);
            sum+=integer;
        }
        System.out.println(sum+"====");
    }
 
    public static  List<Integer> randomRedeEnvelope(int m,int n){
        Random random=new Random();
        List<Integer> list=new ArrayList<Integer>();
        int sumNum=n;
        for(int i=0;i<sumNum-1;i++){
            int num=m/n*2;
            //产生1-m/n*2-1的随机数
            int j = 0;
            j=random.nextInt(num-1)+1;
            //第一次产生的红包数
            list.add(j);
            m-=j;
            n--;
        }
        list.add(m);
        return list;
    }
	@Test
    public void tt(){
    	System.out.println(UUID.randomUUID().toString().replace("-", "").substring(0, 20).length());
    }
}
