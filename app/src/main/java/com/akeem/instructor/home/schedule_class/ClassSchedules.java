package com.akeem.instructor.home.schedule_class;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.akeem.eduhub.R;
import com.akeem.eduhub.databinding.ActivityClassSchedulesBinding;
import com.akeem.instructor.home.interfaces.Finished;

import java.util.ArrayList;
import java.util.List;

public class ClassSchedules extends AppCompatActivity {
    private ActivityClassSchedulesBinding binding;
    private ClassHandler handler;
    private ClassViewModel viewModel;
    private ScheduleAdapter adapter;
    private List<ScheduleModel> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_schedules);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_class_schedules);
        handler = new ClassHandler(this, this::finish);
        binding.setHandler(handler);
        viewModel = new ViewModelProvider(this).get(ClassViewModel.class);
        adapter = new ScheduleAdapter(null);
        viewModel.getClassSchedule("phy102").observe(this, strings -> adapter.setMod(strings));

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapter);

    }

}