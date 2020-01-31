package com.shmagins.easyenglish;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.easyenglish.databinding.WordCardBinding;
import com.shmagins.easyenglish.db.Word;
import com.shmagins.easyenglish.db.WordDatabase;
import com.shmagins.easyenglish.view.WordsFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;
import io.reactivex.Observable;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        DatabaseModule.class
})
public interface ApplicationComponent {
    Context getContext();

    Application getApplication();

    void inject(WordsApplication wordsApplication);

    void inject(WordsViewModel wordsViewModel);

    void inject(WordsFragment wordsFragment);
}
