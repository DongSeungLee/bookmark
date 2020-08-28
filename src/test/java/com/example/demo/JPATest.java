package com.example.demo;

import com.example.demo.member.model.MemberEntity;
import com.example.demo.member.model.QMemberEntity;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.model.NotFoundException;
import com.example.demo.model.ResultCode;
import com.querydsl.jpa.impl.JPAQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
//@ActiveProfiles("test")
public class JPATest {
    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager entityManager;
    @Test
    @Transactional
    public void testSave() {
        memberRepository.save(MemberEntity.builder()
                .name("DS")
                .build());
        List<MemberEntity> list = memberRepository.findAll();
        assertThat(list.size(), equalTo(513));
    }

    @Test
    @Transactional
    public void saveAndGetId() {
        MemberEntity entity = memberRepository.save(MemberEntity.builder()
                .name("DS123")
                .build());
//        MemberEntity getEntity = memberRepository.findById(entity.getMemberId())
//                .orElseThrow(()->new NotFoundException("hoho", ResultCode.NOT_FOUND));
        QMemberEntity ME = new QMemberEntity("ME");
        MemberEntity after = new JPAQuery<MemberEntity>(entityManager)
                .select(ME)
                .from(ME)
                .where(ME.memberId.eq(entity.getMemberId()))
                .fetchOne();
        assertThat(entity.getMemberId(),equalTo(after.getMemberId()));
    }
}
