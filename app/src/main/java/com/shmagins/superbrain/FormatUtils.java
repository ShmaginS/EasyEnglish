package com.shmagins.superbrain;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FormatUtils {
    public static List<String> convertExpressionToStringList(Context context, Collection<Integer> indexes, CalcGame game) {
        List<String> ret = new ArrayList<>();
        for (int i : indexes) {
            Expression e = game.getExpression(i);
            ret.add(e.toString());
        }
        return ret;
    }

}
