package com.spacelife;

import java.util.HashMap;
import java.util.Map;

public final class Utils {
    private Utils() {
    }

    public static <K, V> Map<K, V> mapOf(Object... keyValues) {
        Map<K, V> map = new HashMap<>();

        for (int index = 0; index < keyValues.length / 2; index++) {
            map.put((K)keyValues[index * 2], (V)keyValues[index * 2 + 1]);
        }

        return map;
    }
}
