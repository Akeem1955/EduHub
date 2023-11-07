package com.akeem.student.home;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akeem.eduhub.R;
import com.akeem.eduhub.databinding.FragmentHomeBinding;
import com.akeem.instructor.home.assignment.Assign;
import com.akeem.instructor.home.schedule_class.ScheduleModel;
import com.akeem.instructor.home.test.Test;
import com.akeem.student.StudentHome;
import com.akeem.student.StudentViewModel;
import com.akeem.student.home.assignment.AssignmentAdapter;
import com.akeem.student.home.assignment.AssignmentScreen;
import com.akeem.student.home.assignment.OnAssignmentScreen;
import com.akeem.student.home.schedule.ScheduleAdapter;
import com.akeem.student.home.test.OnSetupScreen;
import com.akeem.student.home.test.TestAdapter;
import com.akeem.student.home.test.setup.Setup;

import java.util.List;

public class HomeFragment extends Fragment {
    private Dialog loading;
    private ScheduleAdapter adapter;
    private TestAdapter t_adapter;
    private AssignmentAdapter A_adapter;

    private StudentViewModel svm;
    private List<ScheduleModel> models;

    public HomeFragment(StudentViewModel svm, Context context) {
        adapter = new ScheduleAdapter(null);
        t_adapter = new TestAdapter(null, new OnSetupScreen() {
            @Override
            public void setup(Test t) {
                //duration
                //topic
                //concentrate
                //no of question
                //score_per_question
                Intent intent = new Intent(context, Setup.class);
                intent.putExtra("duration",t.getDuration());
                intent.putExtra("topic",t.getTopic());
                intent.putExtra("concentrate",t.getConcentrate());
                intent.putExtra("no_of_question",t.getNo_of_question());
                intent.putExtra("score_per_question",t.getScore_per_question());
            }
        });
        A_adapter = new AssignmentAdapter(null, new OnAssignmentScreen() {
            //instruction
            //questions
            //topic
            @Override
            public void onSetup(Assign a) {
                Intent intent = new Intent(context, AssignmentScreen.class);
                intent.putExtra("question_a",a.getQuestion_a());
                intent.putExtra("question_b",a.getQuestion_b() != null ? a.getQuestion_b():"null");
                intent.putExtra("question_c",a.getQuestion_c() != null ? a.getQuestion_c():"null");
                intent.putExtra("instruction",a.getInstruction());
                intent.putExtra("topic",a.getTopic());

            }
        });
        initializeAdapter();
        initializeAdapterTest();
        initializeAdapterAssignment();
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

    private void initializeAdapterTest() {
        svm.getTest().observe(this, tests -> {
            if (tests != null){
                t_adapter.setData(tests);
            }
        });
    }
    private void initializeAdapterAssignment() {
        svm.getAssignment().observe(this, assigns -> {
          if (assigns != null){
              A_adapter.setData(assigns);
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