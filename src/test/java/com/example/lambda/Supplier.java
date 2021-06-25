package com.example.lambda;

/**
 * @author caohaifengx@163.com 2021-06-23 14:35
 */
@FunctionalInterface
public interface Supplier<T> {
    T get();
}

class Car{
    public static Car create(Supplier<Car> supplier){
        return supplier.get();
    }
}
