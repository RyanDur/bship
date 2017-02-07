package com.bship.games.util;

@FunctionalInterface
public interface TriFunction<A, B, C, E> {
    E apply(A a, B b, C c);
}
