package com.shmagins.superbrain.calcgame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.superbrain.BrainApplication;
import com.shmagins.superbrain.sound.MusicService;
import com.shmagins.superbrain.R;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CalcGameLevelsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_recycler);
        RecyclerView recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new GridLayoutManager(this, 5, RecyclerView.VERTICAL, false));
        adapter = new CalcGameLevelsAdapter();
        recycler.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MusicService.resumeMusic(this);
        loadLevels();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MusicService.pauseMusic(this);
        if (disposable != null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }

    public static Intent getStartIntent(Context context){
        return new Intent(context, CalcGameLevelsActivity.class);
    }

    public void loadLevels(){
        BrainApplication app = (BrainApplication) getApplication();
        disposable = app.getDatabaseComponent()
                .getGameRepository()
                .getGameLevels(0)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stats -> {
                    adapter.setLevelStats(stats);
                    adapter.notifyDataSetChanged();
                });
    }

    private CalcGameLevelsAdapter adapter;
    private Disposable disposable;
}
