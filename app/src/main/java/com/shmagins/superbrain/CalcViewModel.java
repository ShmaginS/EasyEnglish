package com.shmagins.superbrain;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.shmagins.superbrain.adapters.CalcAdapter;
import com.shmagins.superbrain.db.HistoryRecord;

import java.util.Calendar;

import io.reactivex.schedulers.Schedulers;

public class CalcViewModel extends AndroidViewModel {
    Context context;
    private HistoryRepository repository;
    private CalcGame game;

    public CalcViewModel(@NonNull Application application) {
        super(application);
        BrainApplication app = (BrainApplication) application;
        context = application;
        game = app.getCalcGame();
        repository = app.getDatabaseComponent().getHistoryRepository();
    }

    public void writeHistoryRecord(int elapsedTime){
        HistoryRecord hr = new HistoryRecord();
        hr.date = Calendar.getInstance().getTimeInMillis();
        hr.elapsedTime = elapsedTime;
        hr.test = TestType.CALCULATION.tag;
    }

    public void onDigitPressed(RecyclerView recycler, CharSequence digit) {
        View child = recycler.findChildViewUnder(10, 10);
        if (child != null) {
            int position = recycler.getChildLayoutPosition(child);
            CalcAdapter.CalculationsViewHolder holder =
                    (CalcAdapter.CalculationsViewHolder) recycler.findViewHolderForLayoutPosition(position);
            if (holder != null) {
                EditText answer = holder.binding.answer;
                answer.getText().append(digit);
            }
        }
    }

    public void onBackspacePressed(RecyclerView recycler) {
        View child = recycler.findChildViewUnder(10, 10);
        if (child != null) {
            int position = recycler.getChildLayoutPosition(child);
            CalcAdapter.CalculationsViewHolder holder =
                    (CalcAdapter.CalculationsViewHolder) recycler.findViewHolderForLayoutPosition(position);
            if (holder != null) {
                EditText answer = holder.itemView.findViewById(R.id.answer);
                Editable text = answer.getText();
                int length = text.length() - 1;
                answer.setText(text.subSequence(0, Math.max(length, 0)));
            }
        }
    }

    public void onClearPressed(RecyclerView recycler) {
        View child = recycler.findChildViewUnder(10, 10);
        if (child != null) {
            int position = recycler.getChildLayoutPosition(child);
            CalcAdapter.CalculationsViewHolder holder =
                    (CalcAdapter.CalculationsViewHolder) recycler.findViewHolderForLayoutPosition(position);
            if (holder != null) {
                EditText answer = holder.itemView.findViewById(R.id.answer);
                answer.setText("");
            }
        }
    }

    public void onNextPressed(RecyclerView recycler) {
        View child = recycler.findChildViewUnder(10, 10);
        if (child != null) {
            int position = recycler.getChildLayoutPosition(child);
            CalcAdapter.CalculationsViewHolder holder =
                    (CalcAdapter.CalculationsViewHolder) recycler.findViewHolderForLayoutPosition(position);
            if (holder != null) {
                if (holder.binding.answer.getText().length() != 0) {
                    game.answer(position, Integer.parseInt(holder.binding.answer.getText().toString()));
                    if (!recycler.isComputingLayout()) {
                        RecyclerView.Adapter adapter = recycler.getAdapter();
                        if (adapter != null) {
                            adapter.notifyItemChanged(position);
                        }
                    }
                    RecyclerView.LayoutManager manager = recycler.getLayoutManager();
                    if (manager != null) {
                        if (position < manager.getItemCount() - 1) {
                            Log.d("happy", "onNextPressed: " + position);
                            recycler.getLayoutManager().scrollToPosition(position + 1);
                        }
                    }
                } else {
                    Snackbar.make(recycler, "Введите ответ", BaseTransientBottomBar.LENGTH_SHORT).setTextColor(Color.WHITE).show();
                }
            }

        }
    }

    public void onPreviousPressed(RecyclerView recycler) {
        View child = recycler.findChildViewUnder(10, 10);
        if (child != null) {
            int position = recycler.getChildLayoutPosition(child);
            CalcAdapter.CalculationsViewHolder holder =
                    (CalcAdapter.CalculationsViewHolder) recycler.findViewHolderForLayoutPosition(position);
            if (holder != null) {
                RecyclerView.LayoutManager manager = recycler.getLayoutManager();
                if (manager != null) {
                    if (position >= 1) {
                        manager.scrollToPosition(position - 1);
                    }
                }
            }
        }
    }

    public CalcGame createOrContinueCalcGame(int limit) {
        game = ((BrainApplication) getApplication()).createOrContinueCalcGame();
        CalcManager.createCalculations(limit)
                .subscribeOn(Schedulers.newThread())
                .subscribe(game::setCalculations);
        return game;
    }

}