package com.amstolbov;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MainStream {

    public static void main(String[] args) {
        partMinValue();
        partOddOrEven();
    }

    public static void partMinValue() {
        System.out.println(minValue(new int[]{1, 2, 3, 3, 3, 3, 1}));
        System.out.println(minValue(new int[]{9, 8}));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce((s1, s2) -> s1 * 10 + s2)
                .orElse(0);
    }

    public static void partOddOrEven() {
        System.out.println(oddOrEven(Arrays.asList(new Integer[]{2, 4, 6, 8, 1, 3})));
        System.out.println(oddOrEven(Arrays.asList(new Integer[]{1, 3, 5, 6, 8})));
        System.out.println(oddOrEven(Arrays.asList(new Integer[]{2, 4, 6, 1, 1, 3})));

        System.out.println(oddOrEvenOneStream(Arrays.asList(new Integer[]{1, 3, 5, 6, 8})));
        System.out.println(oddOrEvenOneStream(Arrays.asList(new Integer[]{2, 4, 6, 1, 1, 3})));
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        Predicate<Integer> predicate = (s1 -> s1 % 2 != 0);
        int countOdd = (int) integers.stream().filter(predicate).count();
        if (predicate.test(countOdd)) {
            predicate = predicate.negate();
        }
        return integers.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    private static List<Integer> oddOrEvenOneStream(List<Integer> integers) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        return integers.stream().collect(
                Collector.of(
                        (Supplier<ArrayList<Integer>>) ArrayList::new,
                        (b, s) -> {
                            atomicInteger.addAndGet(s);
                            b.add(s);
                        },
                        (b1, b2) -> {
                            b1.addAll(b2);
                            return b1;
                        },
                        total -> total.stream().filter(s1 -> (atomicInteger.get() % 2 == 0) == (s1 % 2 != 0))
                                .collect(Collectors.toList())
                )
        );

    }

}
