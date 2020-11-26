package com.example.demo;

import com.example.demo.Utiliy.OptionalUtility;
import com.example.demo.member.model.MemberEntity;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

public class GroupingTest {
    private MemberEntity m1;
    private MemberEntity m2;
    private MemberEntity m3;
    private MemberEntity m4;
    private Properties pros = new Properties();

    @Before
    public void setUp() {
        m1 = MemberEntity.builder()
                .memberId(1)
                .name("hoho1")
                .build();
        m2 = MemberEntity.builder()
                .memberId(2)
                .name("hoho2")
                .build();
        m3 = MemberEntity.builder()
                .memberId(3)
                .name("hoho3")
                .build();
        m4 = MemberEntity.builder()
                .memberId(1)
                .name("hoho4")
                .build();
    }

    @Test
    public void test_grouping() {

        List<MemberEntity> list = Arrays.asList(m1, m2, m3, m4);
        // element를 추가하려면 UnsupportedOperationException이 발생한다.
//        list.add(m3);
        Map<Integer, List<String>> map = list.stream().collect(Collectors.groupingBy(
                MemberEntity::getMemberId, Collectors.mapping(MemberEntity::getName, Collectors.toList())
        ));
        System.out.println(map);
    }

    @Test
    public void test_optional() {
        Optional<MemberEntity> o1 = Optional.ofNullable(m1);
        Optional<MemberEntity> o2 = Optional.empty();
        Optional<MemberEntity> o3 = Optional.empty();
        System.out.println(o1);
        System.out.println(o2.hashCode() + " " + o3.hashCode());
        String ret = o1.map(MemberEntity::getName).orElse("hoho");
    }

    @Test
    public void test_OptionalUtility() {

        pros.put("hoho1", "2");
        pros.put("hoho2", "23");
        pros.put("hoho3", "23aa");
        System.out.println(reduceProperties("hoho2"));
        System.out.println(reduceProperties("hoho3"));
    }

    private Integer reduceProperties(String name) {
        return Optional.ofNullable(pros.getProperty(name))
                .flatMap(OptionalUtility::stringToInt)
                .filter(i -> i > 0)
                .orElse(0);
    }
}
