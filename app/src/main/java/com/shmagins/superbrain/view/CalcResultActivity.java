package com.shmagins.superbrain.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.shmagins.superbrain.BrainApplication;
import com.shmagins.superbrain.CalcGame;
import com.shmagins.superbrain.FormatUtils;
import com.shmagins.superbrain.R;
import com.shmagins.superbrain.adapters.ResultListAdapter;
import com.shmagins.superbrain.databinding.ActivityCalcResultBinding;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CalcResultActivity extends AppCompatActivity {
    public static final int REQUEST = 1;
    private static final String MILLISECONDS = "MILLISECONDS";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCalcResultBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_calc_result);
        CalcGame game = ((BrainApplication)getApplication()).getCalcGame();
        int milliseconds = getIntent().getIntExtra(MILLISECONDS, 0);
        int seconds = milliseconds / 1000;
        int minutes = seconds / 60;
        seconds = seconds % 60;
        List<String> groups = new LinkedList<>();
        int rightCount = game.rightCount();
        int wrongCount = game.wrongCount();
        int totalCount = rightCount + wrongCount;
        groups.add("Правильно (" + rightCount + ")");
        groups.add("Неправильно (" + wrongCount + ")");
        List<String> solved = FormatUtils.convertExpressionToStringList(this, game.getSolved(), game);
        List<String> failed = FormatUtils.convertExpressionToStringList(this, game.getFailed(), game);
        Map<Integer, List<String>> results = new HashMap<>();
        results.put(0, solved);
        results.put(1, failed);
        ResultListAdapter adapter = new ResultListAdapter(groups, results);
        binding.listView.setAdapter(adapter);
        int percent = rightCount * 100 / (totalCount);
        if (percent > 90) {
            binding.imageGameResult.setImageResource(R.drawable.smiles_shyly_smiling_face_emoji);
        } else if (percent > 70) {
            binding.imageGameResult.setImageResource(R.drawable.smiles_slightly_smiling_face_emoji);
        } else if (percent > 40) {
            binding.imageGameResult.setImageResource(R.drawable.smiles_neutral_face_emoji);
        } else if (percent > 20) {
            binding.imageGameResult.setImageResource(R.drawable.smiles_very_sad_emoji);
        } else {
            binding.imageGameResult.setImageResource(R.drawable.smiles_weary_face_emoji);
        }
        binding.gameSummary.setText(
                getString(R.string.game_summary, rightCount, totalCount, minutes, seconds)
        );
    }

    public void onPlayAgainClick(View view) {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
    }

    public void onCancelClick(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public static Intent getStartIntent(Context context, int milliseconds) {
        Intent intent = new Intent(context, CalcResultActivity.class);
        intent.putExtra(MILLISECONDS, milliseconds);
        return intent;
    }
}
