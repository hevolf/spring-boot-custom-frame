package com.example.lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author caohaifengx@163.com 2021-06-22 16:20
 */
public class Java8Tester {

    public static void main(String[] args) {
        MathOperation add = (a, b) -> {return a + b;};
        MathOperation multi = (a, b) -> a * b;

        Java8Tester tester = new Java8Tester();
        int operate = tester.operate(5, 10, add);
        System.out.println(operate);

        Comparator<String> comparator = Comparator.comparingInt(String::length);

        String[] players = {"Rafael Nadal", "Novak Djokovic",
                "Stanislas Wawrinka", "David Ferrer",
                "Roger Federer", "Andy Murray",
                "Tomas Berdych", "Juan Martin Del Potro",
                "Richard Gasquet", "John Isner"};
        Arrays.sort(players, comparator);
        System.out.println(Arrays.asList(players));
        List<String> list = Arrays.asList(players);

        Collections.sort(list, comparator);

        list.stream().collect(Collectors.toList()).sort(comparator);
        List<String> a = list.stream().sorted(Comparator.comparingInt(i -> Integer.valueOf(i))).collect(Collectors.toList());


        // supplier
        Supplier supplier = () -> new Car();
        supplier.get();
        Car car = Car.create(Car::new);
    }

    private int operate(int a, int b, MathOperation mathOperation){
       return mathOperation.operation(a, b);
    }


}
