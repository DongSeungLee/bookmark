package com.example.demo.junit;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Arrays;
import java.util.Objects;

public class ContainsAllElement extends TypeSafeMatcher<SparseArray> {
    private SparseArray sparseArray;
    public ContainsAllElement(SparseArray sparseArray){
        this.sparseArray = sparseArray;
    }
    @Override
    public void describeTo(Description description) {
        description.appendText("The number of non values in the sparse array is not equal to size of sparse array");
    }

    @Override
    protected boolean matchesSafely(SparseArray sparseArray) {
        Object[] values = sparseArray.getValues();
        long nonvalues = Arrays.stream(values).filter(Objects::nonNull).count();
        return nonvalues == Long.valueOf(sparseArray.getSize());
    }

    public static <T> Matcher<SparseArray> ContainsAllElement(SparseArray sparseArray) {
        return new ContainsAllElement(sparseArray);
    }
}
