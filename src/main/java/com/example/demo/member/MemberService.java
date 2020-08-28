package com.example.demo.member;

import com.example.demo.swift.SwiftApiCallFactory;
import com.example.demo.member.model.MemberEntity;
import com.example.demo.member.model.TeamEntity;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.member.repository.TeamRepository;
import com.example.demo.request.CreateMemberRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final SwiftApiCallFactory factory;
    public MemberService(SwiftApiCallFactory factory,
                         MemberRepository memberRepository,
                         TeamRepository teamRepository){
        this.factory = factory;
        this.memberRepository = memberRepository;
        this.teamRepository = teamRepository;
    }
    @Transactional(value = "userTransactionManager")
    public TeamEntity initial(){
        MemberEntity m1 = MemberEntity.builder().name("DS1").build();
        MemberEntity m2 = MemberEntity.builder().name("DS2").build();
        MemberEntity m3 = MemberEntity.builder().name("DS3").build();
        MemberEntity m4 = MemberEntity.builder().name("DS4").build();
        TeamEntity t1 = TeamEntity.builder().name("T1").build();
        teamRepository.save(t1);
        m1.setTeamEntity(t1);
        m2.setTeamEntity(t1);
        m3.setTeamEntity(t1);
        m4.setTeamEntity(t1);
        log.info("MemberService initial : {}", TransactionSynchronizationManager.isActualTransactionActive());
        memberRepository.save(m1);
        memberRepository.save(m2);
        memberRepository.save(m3);
        memberRepository.save(m4);
        return t1;

    }
    public MemberEntity createMember(CreateMemberRequest request){
        MemberEntity entity =  CreateMemberRequest.createMemberEntity(request);
        memberRepository.save(entity);
        return entity;
    }
    public void findMembers(List<Integer> ids){
        List<MemberEntity>members = memberRepository.findAllById(ids);
        if(members.isEmpty()){
            System.out.println("NO");
            return;
        }
        return;
    }

    public void createFile(InputStream input,String originalName){
        String currentDate = DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDateTime.now());
        String path = "images/"+currentDate+"/"+originalName;
        // 요청이 있을 때마다 생성하기 때문에 concurrency issue가 없다.
        factory.create().files()
                .uploadWithTtlSetting("dev",path,input,true,true,"1200")
                .executeWithoutHandler();
    }
}
