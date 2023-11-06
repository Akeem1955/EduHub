package com.akeem.instructor;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.akeem.eduhub.R;
import com.akeem.eduhub.databinding.ActivityInsrtuctorHomeBinding;
import com.akeem.eduhub.databinding.CancelClassBinding;
import com.akeem.instructor.home.schedule_class.ScheduleModel;
import com.akeem.instructor.parser.Cancel;

import java.util.Calendar;
import java.util.List;

public class InstructorHandler {
    private Cancel cancel = new Cancel();
    private InstructorViewModel ivm;
    String location;
    private Context ctx;
    private ActivityInsrtuctorHomeBinding binding;
    private FragmentManager manager;
    private List<Fragment> fragments;


    public InstructorHandler(){}


    public void cancelClass(View view){
        cancel = new Cancel();
        Dialog dialog = new Dialog(ctx);
        CancelClassBinding cancelBinding  = DataBindingUtil.inflate(LayoutInflater.from(ctx),R.layout.cancel_class,null,false);
        cancelBinding.setCancel(cancel);
        cancelBinding.setHandler(this);
        dialog.setContentView(cancelBinding.getRoot());
    }
    public void classCancelled(View view){
        ivm.notifyStudent(cancel.getReason(),location.toUpperCase() + " Class Cancelled");
    }











    public void initialize(){
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
        final int[] ids = {R.id.home_menu_instruct,R.id.resources_menu_instruct,R.id.analytic_menu_instruct};
        int id = item.getItemId();
        if(id == ids[0]){
            replaceFragment(fragments.get(0));
        }
        else if (id == ids[1]) {
            replaceFragment(fragments.get(1));
        }
        else if (id == ids[2]) {
            replaceFragment(fragments.get(2));
        }
    }
    private void replaceFragment(Fragment fragment){
        manager.beginTransaction().replace(R.id.frag_view,fragment).commitNow();
    }




    public void setIvm(InstructorViewModel ivm) {
        this.ivm = ivm;
    }


    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public void setBinding(ActivityInsrtuctorHomeBinding binding) {
        this.binding = binding;
    }

    public void setManager(FragmentManager manager) {
        this.manager = manager;
    }

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
