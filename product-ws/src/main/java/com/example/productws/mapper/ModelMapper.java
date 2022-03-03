package com.example.productws.mapper;

public interface ModelMapper<T,R> {

    R straightMapping(T source);

    T reverseMapping(R destination);

}
