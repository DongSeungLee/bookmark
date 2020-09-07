package com.example.demo;

import com.example.demo.member.MemberService;
import com.example.demo.member.model.MemberEntity;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MemberService memberService;
    @Test
    public void redisTest(){
        String key = "key:springboot";
        redisTemplate.opsForValue().set(key,"hoho");
        String value = (String)redisTemplate.opsForValue().get(key);
        assertThat(value, equalTo("hoho"));
    }
    @Test
    public void getAllMembersCacheTest(){
        List<MemberEntity> ret = memberService.getAllMembers();
        System.out.println("-------------------------------");
        List<MemberEntity> ret1 = memberService.getAllMembers();
        List<MemberEntity> ret2 = memberService.getAllMembers();
        assertThat(ret,equalTo(ret1));
    }
    @After
    public void evictCache(){
        memberService.deleteAllMembers();
    }
}
