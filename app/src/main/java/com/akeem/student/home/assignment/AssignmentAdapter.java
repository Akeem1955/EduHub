package com.akeem.student.home.assignment;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.akeem.eduhub.R;
import com.akeem.eduhub.databinding.RecyclerStudentItemAssignBinding;
import com.akeem.eduhub.databinding.RecyclerStudentItemTestBinding;
import com.akeem.instructor.home.assignment.Assign;
import com.akeem.instructor.home.test.Test;
import com.akeem.student.home.test.OnSetupScreen;
import com.akeem.student.home.test.TestAdapter;

import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {
    private List<Assign> data;
    private OnAssignmentScreen setupScreen;

    public AssignmentAdapter(List<Assign> data, OnAssignmentScreen screen) {
        this.data = data;
        this.setupScreen = screen;
    }

    public List<Assign> getData() {
        return data;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Assign> data) {
        this.data = data;
        notifyDataSetChanged();

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.recycler_student_item_assign,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setAssign(data.get(position));
        holder.binding.getRoot().setOnClickListener(v -> {
            setupScreen.onSetup(data.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private RecyclerStudentItemAssignBinding binding;
        public ViewHolder(@NonNull  RecyclerStudentItemAssignBinding binding) {
            super(binding.getRoot());
            this.binding= binding;
        }
    }
}
