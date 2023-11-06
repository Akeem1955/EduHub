package com.akeem.student;

import android.content.Context;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.akeem.eduhub.R;
import com.akeem.eduhub.databinding.ActivityStudentHomeBinding;

import java.util.List;

public class HomeHandler {
    private final Context ctx;
    private final FragmentManager manager;
    private final List<Fragment> fragments;
    private final ActivityStudentHomeBinding binding;


    public HomeHandler(Context ctx, FragmentManager manager, List<Fragment> fragments, ActivityStudentHomeBinding binding) {
        this.ctx = ctx;
        this.manager = manager;
        this.fragments = fragments;
        this.binding = binding;
        initialize();
    }

    private void initialize(){
        binding.navView.setOnItemSelectedListener(item -> {
            handleNavItemClick(item);
            return true;
        });
    }
    private void handleNavItemClick(MenuItem item){
        handleNavItemClickHelper(item);
    }



    //Helper private method
    private void handleNavItemClickHelper(MenuItem item){
        final int[] ids = {R.id.home_student,R.id.resources_student,R.id.result_student,R.id.analytic_student};
        int id = item.getItemId();
        if(id == ids[0]){
            replaceFragment(fragments.get(0));
        }
        else if (id == ids[1]) {
            replaceFragment(fragments.get(1));
        }
        else if (id == ids[2]) {
            replaceFragment(fragments.get(2));
        } else if (id == ids[3]) {
            replaceFragment(fragments.get(3));

        }
    }
    private void replaceFragment(Fragment fragment){
        manager.beginTransaction().replace(R.id.frag_view,fragment).commitNow();
    }
}
