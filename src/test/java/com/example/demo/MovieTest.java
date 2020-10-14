package com.example.demo;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

public class MovieTest {

    private Movie movie;

    @Before
    public void setUp() throws Exception {
        movie = new Movie();
    }

    @Test
    public void should_return_0_when_just_created() {
        assertThat(movie.averageRating()).isEqualTo(0);
    }

    @Test
    public void should_return_1_when_1_was_rated() {
        movie.rate(1);
        assertThat(movie.averageRating()).isEqualTo(1);
    }

    @Test
    public void should_return_4_when_3_and_5_were_rated() {
        movie.rate(3);
        movie.rate(5);
        assertThat(movie.averageRating()).isEqualTo(4);
        System.out.println(LocalDateTime.parse("2020-10-04T21:51:15.267"));
    }

    @Test
    public void testList() {
        List<Integer> list = IntStream.rangeClosed(1, 55).boxed().collect(Collectors.toList());
        List<Integer> first = new ArrayList<>();
        for (int i = 0; i < list.size(); i += 10) {
            List<Integer> temp = list.subList(i, Math.min(i + 10, list.size()));
            System.out.println(temp.stream().reduce(Integer::sum).orElseGet(() -> null) + " " + temp.size());
            first.addAll(temp);
        }
        System.out.println(first.size());
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 1);
        System.out.println(Optional.ofNullable(map.get(2)).map(entity -> {
            System.out.println(entity);
            return entity + 10;
        }).map(v -> v + 10).orElseGet(() -> null));


    }

    @Test
    public void test_optional_ifPresent() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 1);
        Optional.ofNullable(map.get(2)).ifPresent(
                entity -> System.out.println(entity)
        );
        System.out.println(Optional.ofNullable(map.get(1))
                .map(entity->entity+10)
                .orElseGet(()->null));
    }
}
