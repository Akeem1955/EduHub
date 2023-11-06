package com.akeem.instructor.resources;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.akeem.eduhub.R;
import com.akeem.eduhub.databinding.RecyclerResourcesItemBinding;
import com.akeem.instructor.home.interfaces.OnResClick;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ResourcesAdapter extends RecyclerView.Adapter<ResourcesAdapter.ViewHolder> {
    private List<ResourcesModel> filter = new ArrayList<>();
    private List<ResourcesModel> data;
    private OnResClick resClick;

    public ResourcesAdapter(List<ResourcesModel> data,OnResClick resClick) {
        this.data = data;
        this.resClick = resClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recycler_resources_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.binding.vidContentDescription.setText(data.get(position).getDescription());
        holder.binding.imageButton3.setOnClickListener(v -> {
            resClick.handle(data.get(position));
        });
        if(data.get(position).getMimetype().equals("application/pdf")){
            holder.binding.imageView2.setVisibility(View.GONE);
            holder.binding.pdfView.fromUri(data.get(position).getUri())
                    .pages(0)
                    .enableSwipe(false)
                    .enableAntialiasing(true)
                    .load();
        }else if(data.get(position).getMimetype().equals("video/*")){
            holder.binding.pdfView.setVisibility(View.GONE);
            Glide.with(holder.binding.getRoot())
                    .load(data.get(position).getUri())
                    .into(holder.binding.imageView2);
        }

    }

    @Override
    public int getItemCount() {
        if(data == null){
            return 0;
        }
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private RecyclerResourcesItemBinding binding;
        public ViewHolder(@NonNull RecyclerResourcesItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public List<ResourcesModel> getData() {
        return data;
    }

    public void setData(List<ResourcesModel> data) {
        if(data == null)return;
        this.data = data;
        filter.addAll(data);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void  filter(String query){
        data = filter.stream().filter(resourcesModel -> resourcesModel.getDescription().contains(query)).collect(Collectors.toList());
        notifyDataSetChanged();
    }
}
