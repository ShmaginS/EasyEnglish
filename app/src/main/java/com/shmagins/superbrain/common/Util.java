package com.shmagins.superbrain.common;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Util {

    public static <T> ArrayList<T> toArrayList(Object o, Class<T> type) {
        ArrayList<T> objects = new ArrayList<>();
        for (int i = 0; i < Array.getLength(o); i++) {
            objects.add((T) Array.get(o, i));
        }
        return objects;
    }

}
