package com.akeem.student.home;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akeem.eduhub.R;
import com.akeem.eduhub.databinding.FragmentHomeBinding;
import com.akeem.instructor.home.schedule_class.ScheduleModel;
import com.akeem.student.StudentViewModel;
import com.akeem.student.home.schedule.ScheduleAdapter;

import java.util.List;

public class HomeFragment extends Fragment {
    private Dialog loading;
    private ScheduleAdapter adapter;
    private StudentViewModel svm;
    private List<ScheduleModel> models;

    public HomeFragment(StudentViewModel svm) {
        adapter = new ScheduleAdapter(null);
        initializeAdapter();
        this.svm = svm;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        loading = new Dialog(context);
        loading.setContentView(R.layout.load_lay);
        loading.setCancelable(false);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentHomeBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        binding.scheduledRecycler.setHasFixedSize(true);
        binding.scheduledRecycler.setLayoutManager(new GridLayoutManager(requireContext(),1, RecyclerView.HORIZONTAL,false));
        binding.scheduledRecycler.setAdapter(adapter);
        return binding.getRoot();
    }
    private void initializeAdapter(){
        svm.getSchedules().observe(this, scheduleModels -> {
            if (scheduleModels != null){
                models = scheduleModels;
                adapter.setData(models);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (models == null){
            loading.show();
            svm.getSchedules().observe(this, scheduleModels -> {
                if (scheduleModels != null){
                    loading.dismiss();
                    models = scheduleModels;
                    adapter.setData(models);
                }
            });
        }
    }
}