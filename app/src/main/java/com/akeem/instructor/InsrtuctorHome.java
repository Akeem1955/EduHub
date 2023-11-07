package com.akeem.instructor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.MenuItem;

import com.akeem.eduhub.R;
import com.akeem.eduhub.databinding.ActivityInsrtuctorHomeBinding;
import com.akeem.instructor.analytic.AnalysisReport;
import com.akeem.instructor.home.InstructorHome;
import com.akeem.instructor.resources.ResourcesDocs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InsrtuctorHome extends AppCompatActivity {
    private ActivityInsrtuctorHomeBinding binding;
    private FragmentManager manager;
    private List<Fragment> fragments;
    private InstructorViewModel model;
    private InstructorHandler handler = new InstructorHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insrtuctor_home);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_insrtuctor_home);
        manager = getSupportFragmentManager();
        model = new ViewModelProvider(this).get(InstructorViewModel.class);
        handler.setCtx(this);
        handler.setIvm(model);
        handler.setManager(manager);
        handler.setBinding(binding);
        handler.setLocation(getIntent().getStringExtra("id"));

        fragments = Arrays.asList(new InstructorHome(model,handler,getIntent().getStringExtra("id")),new ResourcesDocs(model,this),new AnalysisReport(model,"phy101"));
        manager.beginTransaction().replace(R.id.frag_view,fragments.get(0)).commitNow();
        handler.setFragments(fragments);
        handler.initialize();
        binding.setHandler(handler);
    }
}