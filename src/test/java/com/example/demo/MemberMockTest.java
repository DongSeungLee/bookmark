package com.example.demo;

import com.example.demo.member.MemberService;
import com.example.demo.member.model.MemberEntity;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.member.repository.TeamRepository;
import com.example.demo.request.CreateMemberRequest;
import com.example.demo.swift.SwiftApiCallFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
@SpringBootTest
@RunWith(SpringRunner.class)
public class MemberMockTest {
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private TeamRepository teamRepository;
    @Autowired
    private SwiftApiCallFactory factory;
    private List<Integer> ids;
    private List<MemberEntity> members;
    @InjectMocks
    private MemberService memberService;
    private CreateMemberRequest request;
    @Before
    public void setUp(){
      //  memberService = new MemberService(factory,memberRepository,teamRepository);
        ids = Arrays.asList(1,2,3);
        members = Arrays.asList(MemberEntity.builder().memberId(1).name("hoho").build());
        request = CreateMemberRequest.builder().memberId(1).name("hoho").sex("male").phoneNumber("111").build();
    }
    @Test
    public void test(){
        when(memberRepository.findAllById(ids)).thenReturn(members);
        memberService.findMembers(ids);

    }
}
