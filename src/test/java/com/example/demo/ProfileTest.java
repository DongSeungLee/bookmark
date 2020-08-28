package com.example.demo;
import com.example.demo.junit.*;
import org.junit.*;
import org.junit.Before;
import org.junit.experimental.categories.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class ProfileTest {

    private Profile profile;
    private Criteria criteria;

    private Question questionReimbursesTuition;
    private Answer answerReimbursesTuition;
    private Answer answerDoesNotReimburseTuition;

    private Question questionIsThereRelocation;
    private Answer answerThereIsRelocation;
    private Answer answerThereIsNoRelocation;

    private Question questionOnsiteDaycare;
    private Answer answerNoOnsiteDaycare;
    private Answer answerHasOnsiteDaycare;

    @Before
    public void createProfile() {
        profile = new Profile("Bull Hockey, Inc.");
    }

    @Before
    public void createCriteria() {
        criteria = new Criteria();
    }

    @Before
    public void createQuestionsAndAnswers() {
        questionIsThereRelocation =
                new BooleanQuestion(1, "Relocation package?");
        answerThereIsRelocation =
                new Answer(questionIsThereRelocation, Bool.TRUE);
        answerThereIsNoRelocation =
                new Answer(questionIsThereRelocation, Bool.FALSE);

        questionReimbursesTuition =
                new BooleanQuestion(1, "Reimburses tuition?");
        answerReimbursesTuition =
                new Answer(questionReimbursesTuition, Bool.TRUE);
        answerDoesNotReimburseTuition =
                new Answer(questionReimbursesTuition, Bool.FALSE);

        questionOnsiteDaycare =
                new BooleanQuestion(1, "Onsite daycare?");
        answerHasOnsiteDaycare =
                new Answer(questionOnsiteDaycare, Bool.TRUE);
        answerNoOnsiteDaycare =
                new Answer(questionOnsiteDaycare, Bool.FALSE);
    }
    private long run(int times, Runnable func) {
        long start = System.nanoTime();
        for (int i = 0; i < times; i++)
            func.run();
        long stop = System.nanoTime();
        return (stop - start) / 1000000;
    }

    @Test
    public void findAnswers() {
        int dataSize = 5000;
        for (int i = 0; i < dataSize; i++)
            profile.add(new Answer(
                    new BooleanQuestion(i, String.valueOf(i)), Bool.FALSE));
        profile.add(
                new Answer(
                        new PercentileQuestion(
                                dataSize, String.valueOf(dataSize), new String[] {}), 0));

        int numberOfTimes = 1000;
        long elapsedMs = run(numberOfTimes,
                () -> profile.find(
                        a -> a.getQuestion().getClass() == PercentileQuestion.class));
        System.out.println(elapsedMs);
        assertTrue(elapsedMs < 1000);
    }
}
