package com.example.demo;


import com.example.demo.member.MemberService;
import com.example.demo.member.model.MemberEntity;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.member.repository.TeamRepository;
import com.example.demo.swift.SwiftApiCallFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {TestConfig.class})
@SpringBootTest
@RunWith(SpringRunner.class)
public class CacheTest {
    // 실제 IoC에 있는 Bean객체(왜냐하면 SpringBootTest로 돌리기 때문에) 대신에 이를 Mock으로 만든다.
    // 왜냐하면 verify(class,times()).method()를 활용하기 위해서!
    // spyBean은 특정 객체가 사용되었는지, 그렇다면 몇번 사용되었는지를 판단하기 위해서 주로 사용된다.
    @SpyBean
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private MyRedisTemplate redisTemplate;

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private SwiftApiCallFactory factory;
    // mock으로 채워서 memberService를 만들어야 하는데
    // mokck객체는 이렇게 우리가 구현하는데 필요하지만 실제로 준비하기엔 여러가지 어령무이 따르는 대상을
    // 필요한 부분만큼 채워서 만들어진 객체를 뜻한다.
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
        //  when(memberRepository.findAllEntity()).thenReturn(list);
        //  doNothing().when(redisTemplate).setValue("members",list,30L,TimeUnit.SECONDS);
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
        verify(memberService, times(1)).getAllMembers();
    }
}
