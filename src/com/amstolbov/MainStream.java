package com.amstolbov;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MainStream {

    public static void main(String[] args) {
        partMinValue();
        partOddOrEven();
    }

    public static void partMinValue() {
        System.out.println(minValue(new int[]{1,2,3,3,3,3,1}));
        System.out.println(minValue(new int[]{9,8}));

        int MAX_ELEMENT = 1000000;
        int[] res2 = new int[MAX_ELEMENT];
        for (int i = 0; i < MAX_ELEMENT; i++) {
            res2[i] = ThreadLocalRandom.current().nextInt(1, 10);
        }
        System.out.println(minValue(res2));
    }

    private static int minValue(int[] values) {
        List<Integer> list = Arrays.stream(values).boxed().collect(Collectors.toList());
        return list.parallelStream().distinct().sorted().reduce((s1, s2) -> s1 * ((int)Math.pow(10,((int)Math.log10(s2)+1))) + s2).orElse(0);
    }

    public static void partOddOrEven() {
        System.out.println(oddOrEven(Arrays.asList(new Integer[]{2,4,6,8,1,3})));
        System.out.println(oddOrEven(Arrays.asList(new Integer[]{1,3,5,6,8})));
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        Predicate<Integer> predicate = (s1 -> s1 % 2 != 0);
        int countOdd = (int)integers.stream().filter(predicate).count();
        if (predicate.test(countOdd)) {
            predicate = (s1 -> s1 % 2 == 0);
        }
        return integers.stream().filter(predicate).collect(Collectors.toList());
    }

}
