package com.shmagins.superbrain.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.superbrain.BrainApplication;
import com.shmagins.superbrain.R;
import com.shmagins.superbrain.adapters.MemoryGameLevelsAdapter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MemoryGameLevelsActivity extends AppCompatActivity {
    MemoryGameLevelsAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_recycler);
        RecyclerView recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new GridLayoutManager(this, 5, RecyclerView.VERTICAL, false));
        adapter = new MemoryGameLevelsAdapter();
        recycler.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadLevels();
    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MemoryGameLevelsActivity.class);
    }

    public void loadLevels() {
        BrainApplication app = (BrainApplication) getApplication();
        app.getDatabaseComponent()
                .getGameRepository()
                .getGameLevels(2)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stats -> {
                    adapter.setLevels(stats);
                    adapter.notifyDataSetChanged();
                });
    }
}
