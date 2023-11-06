package com.akeem.instructor.home.schedule_class;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.akeem.eduhub.R;
import com.akeem.eduhub.databinding.RecyclerScheduleItemBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
    private  List<String> mod;
    private  List<ScheduleModel> data=new ArrayList<>();
    private int start = 0;
    private boolean end = false;
    private boolean refresh = true;
    private int count = 356;
    private int[] day_of_week;
    private String[] content;
    private String[] time;
    private String[] venue;
    private final Calendar calendar = Calendar.getInstance();
    private final int year =Integer.parseInt(calendar.toString().split(",")[13].split("=")[1]);
    private int week =Integer.parseInt(calendar.toString().split(",")[15].split("=")[1]);
    private int current = Integer.parseInt(calendar.toString().split(",")[17].split("=")[1]);

    public ScheduleAdapter(List<String> data) {
        this.mod = data;
      if(this.mod != null){
          initializeDayOfweek();
          populateData();
      }
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recycler_schedule_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setModel(data.get(position));
    }

    @Override
    public int getItemCount() {
        if(mod == null){
            return 0;
        }
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final RecyclerScheduleItemBinding binding;
        public ViewHolder(@NonNull  RecyclerScheduleItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }




    //    Initialization
    private void initializeDayOfweek() {
        day_of_week = new int[mod.size()];
        content = new String[mod.size()];
        time = new String[mod.size()];
        venue = new String[mod.size()];
        for (int i = 0; i < day_of_week.length; i++) {
            day_of_week[i] = Integer.parseInt(mod.get(i).split(",")[0]);
            content[i] = mod.get(i).split(",")[1];
            time[i] = mod.get(i).split(",")[2];
            venue[i] = mod.get(i).split(",")[3];
        }
    }
    private void populateData() {
        for (int i = start; i < count; i++) {
            int j = 0;
            for (int k : day_of_week) {
                calendar.setWeekDate(year, week, k);
                if (Integer.parseInt(calendar.toString().split(",")[17].split("=")[1]) < current) {
                    week++;
                }
                calendar.setWeekDate(year, week, k);
                current = Integer.parseInt(calendar.toString().split(",")[17].split("=")[1]);
                if (Integer.parseInt(calendar.toString().split(",")[13].split("=")[1]) > year) {
                    end = true;
                    System.out.println("End Of The Year :)");
                    return;
                }
                System.out.println(calendar.getTime());
                String[] temp = calendar.getTime().toString().split(" ");
                data.add(new ScheduleModel( temp[5], (temp[0] + " " + temp[1] + " " + temp[2]),content[j],time[j],venue[j]));
                j++;
            }
        }
    }
//    @SuppressLint("NotifyDataSetChanged")
//    private void rePopulate(){
//        if(end){
//            System.out.println("Ended");
//            if(refresh){
//                notifyDataSetChanged();
//                refresh = false;
//            }
//            return;
//        }
//        start = count;
//        count += 6;
//        populateData();
//        if(end){
//            return;
//        }
//        notifyDataSetChanged();
//    }

    public void setMod(List<String> mod) {
        this.mod = mod;
        initializeDayOfweek();
        populateData();
        notifyDataSetChanged();

    }
}
