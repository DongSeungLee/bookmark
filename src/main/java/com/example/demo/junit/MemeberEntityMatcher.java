package com.example.demo.junit;

import com.example.demo.member.model.MemberEntity;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Objects;

public class MemeberEntityMatcher extends TypeSafeMatcher<MemberEntity> {
    private MemberEntity entity;

    public MemeberEntityMatcher(MemberEntity entity) {
        this.entity = entity;
    }

    @Override
    protected boolean matchesSafely(MemberEntity item) {
        if (Objects.isNull(item))
            return false;
        return entity.getMemberId() == item.getMemberId()
                && entity.getName().equals(item.getName());

    }

    @Override
    public void describeTo(Description description) {
        description.appendText(String.format("MemberEntity id, name are %d, %s", entity.getMemberId(), entity.getName()));
    }

    public static <T> Matcher<MemberEntity> MemeberEntityMatcher(MemberEntity entity) {
        return new MemeberEntityMatcher(entity);
    }
}
