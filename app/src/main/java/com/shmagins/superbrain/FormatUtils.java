package com.shmagins.superbrain;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FormatUtils {
    public static List<String> convertCalculationsToStringList(Context context, Collection<Integer> indexes, CalcGame game) {
        List<String> ret = new ArrayList<>();
        for (int i : indexes) {
            Calculation c = game.getCalculation(i);
            String calcString = context.getString(
                    R.string.result_card_text, c.first, c.operation, c.second, c.result, c.answer
            );
            ret.add(calcString);
        }
        return ret;
    }
}
