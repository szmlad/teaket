package util;

import java.util.function.Function;
import java.util.function.Predicate;

public class Func {
    public static <T> Predicate<T> not(Predicate<T> p) {
        return t -> !p.test(t);
    }

    public static <T> Predicate<T> and(Predicate<T> p, Predicate<T> q) {
        return t -> p.test(t) && q.test(t);
    }

    public static <T, U> Predicate<U> equality(Function<U, T> f, T value) {
        return x -> f.apply(x).equals(value);
    }
}
