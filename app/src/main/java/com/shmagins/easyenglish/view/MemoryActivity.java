package com.shmagins.easyenglish.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.easyenglish.AnimalImages;
import com.shmagins.easyenglish.AppViewModel;
import com.shmagins.easyenglish.ImageAdapter;
import com.shmagins.easyenglish.MatrixManager;
import com.shmagins.easyenglish.R;
import com.shmagins.easyenglish.databinding.ActivityMemoryBinding;

import java.util.List;

public class MemoryActivity extends AppCompatActivity {
    AppViewModel viewModel;

    public static final String WIDTH = "WIDTH";
    public static final String HEIGHT = "HEIGHT";
    public static final String ELEMENTS = "ELEMENTS";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMemoryBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_memory);
        Intent intent = getIntent();
        int width = 0;
        int height = 0;
        ImageAdapter adapter = new ImageAdapter();
            if (intent != null) {
                width = intent.getIntExtra(WIDTH, 0);
                height = intent.getIntExtra(HEIGHT, 0);
            }

        AnimalImages ads = new AnimalImages();
        int[] drawables = ads.getIds();
        List<Integer> elements = MatrixManager.generateImageIndexMatrix(width, height, drawables.length);
        adapter.setImages(
                    MatrixManager.generateImageStates(elements, drawables)
            );
        binding.memoryRecycler.setAdapter(adapter);
        binding.memoryRecycler.setLayoutManager(new GridLayoutManager(this, width, RecyclerView.VERTICAL, false));

    }

    public static Intent getStartIntent(Context context, int width, int height){
        Intent intent = new Intent(context, MemoryActivity.class);
        intent.putExtra(WIDTH, width);
        intent.putExtra(HEIGHT, height);
        return intent;
    }
}
