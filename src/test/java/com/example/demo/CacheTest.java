package com.example.demo;


import com.example.demo.member.MemberService;
import com.example.demo.member.model.MemberEntity;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.member.repository.TeamRepository;
import com.example.demo.swift.SwiftApiCallFactory;
import com.example.demo.swift.SwiftApiCaller;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {TestConfig.class})
@SpringBootTest
@RunWith(SpringRunner.class)
public class CacheTest {

    @InjectMocks
    private MemberService memberService;
    @Mock
    private MemberRepository memberRepository;

    @Autowired
    private CacheManager cacheManager;

    @Mock
    private MyRedisTemplate redisTemplate;

    @Mock
    private TeamRepository teamRepository;
    @Mock
    private SwiftApiCallFactory factory;
    // mock으로 채워서 memberService를 만들어야 하는데
    // mock이 부족하니 애초에 IoC에 있었던 Bean을 가지고 와서 mock 객체가 아니였다.
    List<MemberEntity> list;

    @Before
    public void setUp() {
        MemberEntity m1 = MemberEntity.builder()
                .name("name1")
                .memberId(1)
                .build();
        MemberEntity m2 = MemberEntity.builder()
                .name("name2")
                .memberId(2)
                .build();
        list = Arrays.asList(m1, m2);
    }

    @Test
    public void testCache() {
        // given
        when(memberRepository.findAllEntity()).thenReturn(list);
        doNothing().when(redisTemplate).setValue("members",list,30L,TimeUnit.SECONDS);
        // 아무래도 이렇게 부르면 cache가 안되는 것으로 보인다.
        memberService.getAllMembers();
        memberService.getAllMembers();
        memberService.getAllMembers();
        memberService.getAllMembers();
        memberService.getAllMembers();
        // to use verify function , we need to use Mock obejct!
        // verify(memberRepository,times(1)).findAllEntity();
        // System.out.println(cacheManager.getCache("getAllMembers"));
        // 차라리 cacheManager에 cache가 되었는가로 판단하는 것이 더 바람직 해보인다.
        assertThat(cacheManager.getCache("getAllMembers")).isNotNull();

    }
}
