package com.akeem.instructor.resources;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akeem.eduhub.R;
import com.akeem.eduhub.databinding.FragmentResourcesDocsBinding;
import com.akeem.instructor.InsrtuctorHome;
import com.akeem.instructor.InstructorViewModel;
import com.akeem.instructor.home.interfaces.OnResClick;
import com.ashiri.instuctor.Instructor;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ResourcesDocs extends Fragment {
    private  ResourcesAdapter adapter;
    private InstructorViewModel ivm;
    private Dialog loading;
    private Dialog failed;
    private FragmentResourcesDocsBinding binding;

    public ResourcesDocs(InstructorViewModel ivm, Context context) {
        this.ivm =ivm;
        adapter = new ResourcesAdapter(null, data -> {
            if(data.getMimetype().equals("application/pdf")){
                Intent intent = new Intent(context, DocumentViewer.class);
                intent.setData(data.getUri());
                startActivity(intent);
            }else{
                Intent intent = new Intent(context, ResourcesVideo.class);
                intent.setData(data.getUri());
                startActivity(intent);
            }

        });
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_resources_docs,container,false);
        binding.recycleView.setHasFixedSize(true);
        binding.searchView.setOnClickListener(v -> binding.searchView.setIconified(false));
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (adapter.getData() == null){
                    Snackbar.make(binding.getRoot(),"No Resources Available Try Uploading Some",Snackbar.LENGTH_SHORT).show();
                } else if (adapter.getData().isEmpty()) {
                    Snackbar.make(binding.getRoot(),"No Resources Available Try Uploading Some",Snackbar.LENGTH_SHORT).show();
                }else {
                    adapter.filter(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        binding.recycleView.setLayoutManager(new GridLayoutManager(requireContext(),2));
        binding.recycleView.setAdapter(adapter);
        return binding.getRoot();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        loading = new Dialog(context);
        loading.setContentView(R.layout.load_lay);
        loading.setCancelable(false);
        loading.show();
        failed = new Dialog(context);
        failed.setContentView(R.layout.failed_lay);
        TextView errMsg = failed.findViewById(R.id.failed_msg);
        errMsg.setText(R.string.unable_to_load_resources_check_your_internet_connection_and_try_again);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initializeAdapter(){
        ivm.getAllResources().observe(this, uris -> {
            if(uris != null){
                adapter.setData(uris);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResume() {
        super.onResume();
        if(adapter.getData() == null){
            ivm.getAllResources().observe(this, uris -> {
                if(uris != null && !uris.isEmpty()){
                    adapter.setData(uris);
                    adapter.notifyDataSetChanged();
                    loading.dismiss();
                    binding.emptyResource.setVisibility(View.GONE);
                } else if (uris != null && uris.isEmpty()) {
                    loading.dismiss();
                    binding.emptyResource.setVisibility(View.VISIBLE);
                } else if (uris == null) {
                    failed.show();
                }
            });

        } else if (adapter.getData().isEmpty()) {
            loading.dismiss();
            binding.emptyResource.setVisibility(View.VISIBLE);
        } else {
            binding.emptyResource.setVisibility(View.GONE);
            loading.dismiss();
        }
    }
}