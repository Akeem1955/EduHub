package com.akeem.student.home.test;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.akeem.eduhub.R;
import com.akeem.eduhub.databinding.RecyclerStudentItemTestBinding;
import com.akeem.instructor.home.assignment.Assign;
import com.akeem.instructor.home.test.Test;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {
    private List<Test> data;
    private OnSetupScreen setupScreen;

    public TestAdapter(List<Test> data,OnSetupScreen setupScreen) {
        this.data = data;
        this.setupScreen = setupScreen;
    }

    public List<Test> getData() {
        return data;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Test> data) {
        this.data = data;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recycler_student_item_test,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setTest(data.get(position));
        holder.binding.getRoot().setOnClickListener(v -> {
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private RecyclerStudentItemTestBinding binding;
        public ViewHolder(@NonNull  RecyclerStudentItemTestBinding binding) {
            super(binding.getRoot());
            this.binding= binding;
        }
    }
}
