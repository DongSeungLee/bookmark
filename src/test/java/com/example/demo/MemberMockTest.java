package com.example.demo;

import com.example.demo.member.MemberService;
import com.example.demo.member.model.MemberEntity;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.request.CreateMemberRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static com.example.demo.junit.MemeberEntityMatcher.MemeberEntityMatcher;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MemberMockTest {
    @Mock
    private MemberRepository memberRepository;
    private List<Integer> ids;
    private List<MemberEntity> members;
    @InjectMocks
    private MemberService memberService;
    private CreateMemberRequest request;

    @Before
    public void setUp() {
        //  memberService = new MemberService(factory,memberRepository,teamRepository);
        ids = Arrays.asList(1, 2, 3);
        members = Arrays.asList(MemberEntity.builder().memberId(1).name("hoho").build());
        request = CreateMemberRequest.builder()
                .memberId(1)
                .name("hoho")
                .sex("male")
                .phoneNumber("111")
                .build();
    }
    @Test
    public void test() {
        when(memberRepository.findAllById(ids)).thenReturn(members);
        memberService.findMembers(ids);
        // List의 equals는 모든 element를 iteration하면서 비교한다.
        verify(memberRepository).findAllById(ids);
    }
    @Test
    public void createMemberEntityTest() {
        MemberEntity entity = CreateMemberRequest.createMemberEntity(request);
        when(memberRepository.save(entity)).thenReturn(entity);
        MemberEntity expected = memberService.createMember(request);
        assertThat(entity, MemeberEntityMatcher(expected));
        verify(memberRepository).save(entity);
    }
    @Test(expected = IOException.class)
    public void testInputStreamIOException() throws IOException {
        InputStream stream = createStreamThrowingErrorWhenRead();
        stream.read();
    }

    private InputStream createStreamThrowingErrorWhenRead(){
        return new InputStream() {
            @Override
            public int read() throws IOException {
                throw new IOException();
            }
        };
    }
}
