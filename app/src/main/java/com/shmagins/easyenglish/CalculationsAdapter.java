package com.shmagins.easyenglish;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.shmagins.easyenglish.databinding.CalculationCardBinding;
import com.shmagins.easyenglish.db.Calculation;

import java.util.ArrayList;
import java.util.List;

public class CalculationsAdapter extends RecyclerView.Adapter {
    private List<Calculation> calculations;
    private RecyclerView connectedRecycler;

    {
        calculations = new ArrayList<>();
    }

    public void setCalculations(List<Calculation> calculations) {
        this.calculations = calculations;
        notifyDataSetChanged();
    }

    public void addCalculation(Calculation calculation) {
        calculations.add(calculation);
        notifyDataSetChanged();
    }

    public void setRecyclerLink(RecyclerView recycler) {
        connectedRecycler = recycler;
    }

    class CalculationsViewHolder extends RecyclerView.ViewHolder {
        CalculationCardBinding binding;

        CalculationsViewHolder(@NonNull CalculationCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("WrongConstant")
        public void bind(Calculation calculation){
            binding.setCalculation(calculation);
            binding.executePendingBindings();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CalculationCardBinding binding = CalculationCardBinding.inflate(inflater, parent, false);
        return new CalculationsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CalculationsViewHolder h = (CalculationsViewHolder) holder;
        h.bind(calculations.get(position));
//        h.binding.answer.setOnEditorActionListener((textView, i, keyEvent) -> {
//
//            if (i == EditorInfo.IME_ACTION_DONE) {
//                if (connectedRecycler != null) {
//                    try {
//                        Context application = connectedRecycler
//                                .getContext()
//                                .getApplicationContext();
//                        InputMethodManager imm = (InputMethodManager)
//                                application
//                                .getSystemService(Context.INPUT_METHOD_SERVICE);
//                        imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
//                    CalculationsViewModel viewModel =
//                            ViewModelProviders.of(connectedRecycler.getContext(),
//                                    new CalculationViewModelFactory(application))
//                                    .get(CalculationsViewModel.class);
//                        if (position != calculations.size() - 1) {
//                            connectedRecycler.scrollToPosition(position + 1);
//                        }
//                    } catch (NullPointerException e) {
//                        Log.d("happy", "onEditorAction: NPE");
//                    }
//                }
//
//                return true;
//            }
//            return false;
//        });
    }

    @Override
    public int getItemCount() {
        return calculations.size();
    }
}