package com.shmagins.easyenglish;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.easyenglish.db.Calculation;
import com.shmagins.easyenglish.db.CalculationDatabase;
import com.shmagins.easyenglish.view.SmilesActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class AppViewModel extends AndroidViewModel {
    @Inject
    CalculationDatabase cdb;
    @Inject
    Context context;
    private CalcRepository repository;

    public AppViewModel(@NonNull Application application) {
        super(application);
        ((BrainApplication) application).getApplicationComponent()
                .inject(this);
        repository = new CalcRepository(cdb);
        repository.deleteAll();
        List<Calculation> l = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            l.add(CalcManager.createCalculation());
        }
        repository.insertCalculations(l);
    }

    public Observable<List<Calculation>> getAll(int limit) {
        return repository.getAllCalculations(limit);
    }

    public void updateCalculation(Calculation calculation) {
        repository.updateCalculation(calculation);
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
                updateCalculationFromHolder(holder);
            }
            if (!recycler.isComputingLayout()){
                recycler.getAdapter().notifyItemChanged(position);
            }
            if (position < recycler.getLayoutManager().getItemCount() - 1) {
                recycler.getLayoutManager().scrollToPosition(position + 1);
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
                updateCalculationFromHolder(holder);
            }
            if (!recycler.isComputingLayout()){
                recycler.getAdapter().notifyItemChanged(position);
            }
            if (position >= 1) {
                recycler.getLayoutManager().scrollToPosition(position - 1);
            }

        }
    }

    private void updateCalculationFromHolder(CalcAdapter.CalculationsViewHolder holder) {
        Calculation calculation = holder.binding.getCalculation();
        if (holder.binding.answer.getText().length() != 0) {
            calculation.answer = Integer.parseInt(holder.binding.answer.getText().toString());
        } else {
            calculation.answer = Integer.MIN_VALUE;
        }
        updateCalculation(calculation);
    }
}