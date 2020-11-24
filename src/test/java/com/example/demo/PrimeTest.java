package com.example.demo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PrimeTest {
    private static final Integer max_number = 1_000_00;

    @Test
    public void test_prime() {
//        long fastest = Long.MAX_VALUE;
//        for (int i = 0; i < 10; i++) {
//            long start = System.nanoTime();
//            paritionPrimesWithCustomCollecor(max_number);
//            long duration = (System.nanoTime() - start) / 1_000_000;
//            if (duration < fastest) fastest = duration;
//        }
//        System.out.println("Time :" + fastest);
//        fastest = Long.MAX_VALUE;
//        for (int i = 0; i < 10; i++) {
//            long start = System.nanoTime();
//            paritiionPrimes(max_number);
//            long duration = (System.nanoTime() - start) / 1_000_000;
//            if (duration < fastest) fastest = duration;
//        }
//        System.out.println("Time :" + fastest);
        Map<Boolean, List<Integer>> ret = paritionPrimesWithCustomCollecor(max_number);
        System.out.println(ret);
        Map<Boolean, Long> mapret = IntStream.rangeClosed(1, max_number)
                .boxed().collect(Collectors.partitioningBy(candidate -> isPrime(candidate),
                        Collectors.counting()
                ));
        System.out.println(mapret);
        Predicate<Integer> p = (a) -> a > NumberType.LARGE.value;
        Map<Boolean, Map<NumberType, List<Integer>>> mapGrouping = IntStream.rangeClosed(1, max_number)
                .boxed().collect(Collectors.partitioningBy(candidate -> isPrime(candidate),
                        Collectors.groupingBy((candidate) -> {
                                    if (p.test(candidate)) return NumberType.LARGE;
                                    else return NumberType.SMALL;
                                }
                        )));
        System.out.println(mapGrouping);
    }

    public Map<Boolean, List<Integer>> paritiionPrimes(int n) {
        return IntStream.rangeClosed(1, n).boxed().collect(
                Collectors.partitioningBy(candidate -> isPrime(candidate))
        );
    }

    private boolean isPrime(int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return IntStream.rangeClosed(2, candidateRoot)
                .noneMatch(i -> (candidate % i) == 0);
    }

    public Map<Boolean, List<Integer>> paritionPrimesWithCustomCollecor(int n) {
        return IntStream.rangeClosed(2, n).boxed()
                .collect(() -> new HashMap<Boolean, List<Integer>>() {{
                            put(true, new ArrayList<>());
                            put(false, new ArrayList<>());
                        }},
                        (acc, candidate) -> {
                            acc.get(Prime.isPrime(acc.get(true), candidate)).add(candidate);
                        },
                        (map1, map2) -> {
                            map1.get(true).addAll(map2.get(true));
                            map1.get(false).addAll(map2.get(false));
                        }
                );
    }

    @Test
    public void test_Integer() {
        List<Integer> aa = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
        List<Integer> bb = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
        aa.stream().forEach(a -> {
            System.out.println(String.valueOf(a) + bb.stream().noneMatch(b -> b == a));
        });
    }
}
