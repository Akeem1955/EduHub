package com.akeem.student.home.schedule;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.akeem.eduhub.R;
import com.akeem.eduhub.databinding.RecyclerStudentItemScheduleBinding;
import com.akeem.instructor.home.schedule_class.ScheduleModel;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
    private List<ScheduleModel> data;

    public ScheduleAdapter(List<ScheduleModel> data) {
        this.data = data;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recycler_student_item_schedule,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setModel(data.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private RecyclerStudentItemScheduleBinding binding;
        public ViewHolder(@NonNull RecyclerStudentItemScheduleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public List<ScheduleModel> getData() {
        return data;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<ScheduleModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
