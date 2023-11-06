package com.akeem.instructor.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.akeem.eduhub.R;
import com.akeem.eduhub.databinding.FragmentInstructorHomeBinding;
import com.akeem.eduhub.databinding.RecyclerHomeItemBinding;
import com.bumptech.glide.Glide;

import java.util.List;

public class HAdapter extends RecyclerView.Adapter<HAdapter.ViewHolder> {
    private List<HModel> data;

    public HAdapter( List<HModel> data){
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.recycler_home_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.getRoot().setOnClickListener(view -> data.get(position).getListener().onClick(view));
        Glide.with(holder.binding.getRoot())
                .load(data.get(position).getDrawable())
                .into(holder.binding.icon);
        holder.binding.header.setText(data.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final RecyclerHomeItemBinding binding;
        public ViewHolder(@NonNull  RecyclerHomeItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    public void setData(List<HModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
