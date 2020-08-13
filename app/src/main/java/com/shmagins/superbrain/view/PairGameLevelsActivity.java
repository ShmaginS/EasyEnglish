package com.shmagins.superbrain.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.superbrain.BrainApplication;
import com.shmagins.superbrain.GameRepository;
import com.shmagins.superbrain.MusicService;
import com.shmagins.superbrain.R;
import com.shmagins.superbrain.adapters.PairGameLevelsAdapter;
import com.shmagins.superbrain.db.PairGameLevel;
import com.shmagins.superbrain.db.Stats;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PairGameLevelsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_recycler);
        RecyclerView recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new GridLayoutManager(this, 5, RecyclerView.VERTICAL, false));
        adapter = new PairGameLevelsAdapter();
        recycler.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MusicService.resumeMusic(this);
        loadLevels();
    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, PairGameLevelsActivity.class);
    }

    public void loadLevels() {
        BrainApplication app = (BrainApplication) getApplication();
        GameRepository repository = app.getDatabaseComponent().getGameRepository();
        disposable = Observable.zip(repository.getGameLevels(1), repository.getPairGameLevels(), Pair::new)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pair -> {
                    adapter.setLevelStats(pair.first);
                    adapter.setLevels(pair.second);
                    adapter.notifyDataSetChanged();
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        MusicService.pauseMusic(this);
        if (disposable != null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }

    private PairGameLevelsAdapter adapter;
    private Disposable disposable;
}
