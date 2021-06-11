package com.dev.Pool;

import java.util.HashMap;

public interface Behavior {
    int PRODUCT_NEEDS = 10;

    void initialize(String[] set);

    void behave();

    void print();

    void setTime(long time);

    void setList(HashMap<Integer, HashMap<String, String>> list);

    HashMap<Integer, HashMap<String, String>> getList();

    default void call(String[] set) {
        long beforeTime = System.currentTimeMillis();

        initialize(set);
        behave();

        long afterTime = System.currentTimeMillis();

        setTime(afterTime - beforeTime);
    }
}
