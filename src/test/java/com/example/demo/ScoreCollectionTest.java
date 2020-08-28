package com.example.demo;

import com.example.demo.junit.ScoreCollection;


import java.util.stream.IntStream;
import static org.hamcrest.CoreMatchers.equalTo;


import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import sun.invoke.empty.Empty;

public class ScoreCollectionTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void test(){
        fail("yet not implemented");
        IntStream intStream = IntStream.rangeClosed(1,10);
        int ret = intStream.reduce(0,Integer::sum);
        assertThat(ret,equalTo(55));
    }
    @Test
    public void answerArithmeticMeanOfTowNumbers(){
        ScoreCollection scores = new ScoreCollection();
        scores.add(()->5);
        scores.add(()->7);
        int actualResult = scores.arithmeticMean();
        assertThat(actualResult,is(notNullValue()));
        assertThat(actualResult,equalTo(6));
    }
    @Test
    public void emptyListTest(){
        thrown.expect(EmptyException.class);
        thrown.expectMessage("Empty List!");
        ScoreCollection scores = new ScoreCollection();
        int actualResult = scores.arithmeticMean();
        assertThat(actualResult,is(notNullValue()));
        assertThat(actualResult,equalTo(6));

    }
}
