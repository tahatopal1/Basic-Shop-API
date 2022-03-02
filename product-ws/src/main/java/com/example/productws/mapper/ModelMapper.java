package com.example.productws.mapper;

public interface ModelMapper<T,R> {

    R straightMapping(T source, R destination);

    T reverseMapping(T source, R destination);

}
