package com.akeem.instructor.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akeem.eduhub.R;
import com.akeem.eduhub.databinding.CurrentClassBinding;
import com.akeem.eduhub.databinding.FragmentInstructorHomeBinding;
import com.akeem.instructor.InstructorHandler;
import com.akeem.instructor.InstructorViewModel;
import com.akeem.instructor.home.assignment.AssignmentStudent;
import com.akeem.instructor.home.schedule_class.ClassSchedules;
import com.akeem.instructor.home.test.TestStudents;
import com.akeem.instructor.home.upload.DocumentsUpload;
import com.akeem.instructor.home.upload.VideoUpload;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class InstructorHome extends Fragment {
    private InstructorViewModel viewModel;
    private FragmentInstructorHomeBinding binding;
    private HAdapter adapter;
    private final List<HModel> data = new ArrayList<>();
    private Intent intent;
    private InstructorHandler handler;
    private BottomSheetDialog dialog;
    private  CurrentClassBinding currentbinding;



    public InstructorHome(InstructorViewModel viewModel, InstructorHandler handler) {
        this.viewModel = viewModel;
        this.handler = handler;
        initializeAdapter();
        adapter = new HAdapter(data);
    }
    public InstructorHome(){
        initializeAdapter();
        adapter = new HAdapter(data);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dialog = new BottomSheetDialog(context);
        CurrentClassBinding currentbinding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.current_class,null,false);
        currentbinding.setHandler(handler);
        //currentbinding.setScheduleModel();
        dialog.setContentView(currentbinding.getRoot());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View view = inflater.inflate(R.layout.fragment_instructor_home, container, false);
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_instructor_home,container,false);
        binding.recycleView.setHasFixedSize(true);
        binding.recycleView.setLayoutManager(new GridLayoutManager(requireContext(),2));
        binding.recycleView.setAdapter(adapter);
        return binding.getRoot();
    }



    private void initializeAdapter(){
        data.add(new HModel("Scheduled Class", R.drawable.dule_class, v -> {
            System.out.println("Click event");
            intent = new Intent(requireContext(), ClassSchedules.class);
            startActivity(intent);

        }));
        data.add(new HModel("Current Class", R.drawable.arcticons_netease_open_class, v -> {
            dialog.show();
        }));
        data.add(new HModel("Set Assignment", R.drawable.ass, v -> {
            intent = new Intent(requireContext(), AssignmentStudent.class);
            startActivity(intent);
        }));
        data.add(new HModel("Set Test", R.drawable.test, v -> {
            intent = new Intent(requireContext(), TestStudents.class);
            startActivity(intent);
        }));
        data.add(new HModel("Upload Video", R.drawable.vid_upload, v -> {
            intent = new Intent(requireContext(), VideoUpload.class);
            startActivity(intent);
        }));
        data.add(new HModel("Upload Materials", R.drawable.doc_upload, v -> {
            intent = new Intent(requireContext(), DocumentsUpload.class);
            startActivity(intent);
        }));


    }
}