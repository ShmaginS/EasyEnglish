package com.shmagins.easyenglish;

import android.app.Application;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.easyenglish.db.Calculation;
import com.shmagins.easyenglish.db.CalculationDatabase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CalculationsViewModel extends AndroidViewModel {
    @Inject
    CalculationDatabase wdb;
    private WordsRepository repository;

    public CalculationsViewModel(@NonNull Application application) {
        super(application);
        ((CalculationApplication) application).getApplicationComponent()
                .inject(this);
        repository = new WordsRepository(wdb);


    }

    public Observable<List<Calculation>> getAll() {
        return repository.getAllCalculations();
    }

    public void updateCalculation(Calculation calculation) {
        repository.updateCalculation(calculation);
    }

    public void onDigitPressed(RecyclerView recycler, CharSequence digit) {
        View child = recycler.findChildViewUnder(10, 10);
        if (child != null) {
            int position = recycler.getChildLayoutPosition(child);
            CalculationsAdapter.CalculationsViewHolder holder =
                    (CalculationsAdapter.CalculationsViewHolder) recycler.findViewHolderForLayoutPosition(position);
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
            CalculationsAdapter.CalculationsViewHolder holder =
                    (CalculationsAdapter.CalculationsViewHolder) recycler.findViewHolderForLayoutPosition(position);
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
            CalculationsAdapter.CalculationsViewHolder holder =
                    (CalculationsAdapter.CalculationsViewHolder) recycler.findViewHolderForLayoutPosition(position);
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
            CalculationsAdapter.CalculationsViewHolder holder =
                    (CalculationsAdapter.CalculationsViewHolder) recycler.findViewHolderForLayoutPosition(position);
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
            CalculationsAdapter.CalculationsViewHolder holder =
                    (CalculationsAdapter.CalculationsViewHolder) recycler.findViewHolderForLayoutPosition(position);
            if (holder != null) {
                updateCalculationFromHolder(holder);
            }
            if (!recycler.isComputingLayout()){
                recycler.getAdapter().notifyItemChanged(position);
            }
            if (position < recycler.getLayoutManager().getItemCount() + 1) {
                recycler.getLayoutManager().scrollToPosition(position - 1);
            }

        }
    }

    void updateCalculationFromHolder(CalculationsAdapter.CalculationsViewHolder holder) {
        TextView id = holder.itemView.findViewById(R.id.id);
        TextView first = holder.itemView.findViewById(R.id.first);
        TextView second = holder.itemView.findViewById(R.id.second);
        TextView operation = holder.itemView.findViewById(R.id.operation);
        if (holder.binding.answer.getText().length() != 0) {
            Calculation calculation = new Calculation(
                    Integer.parseInt(id.getText().toString()),
                    Integer.parseInt(first.getText().toString()),
                    Integer.parseInt(second.getText().toString()),
                    operation.getText().toString(),
                    Integer.parseInt(holder.binding.answer.getText().toString())
            );
            updateCalculation(calculation);
        }
    }
}