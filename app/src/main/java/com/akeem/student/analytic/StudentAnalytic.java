package com.akeem.student.analytic;

import android.app.Dialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akeem.eduhub.R;
import com.akeem.eduhub.databinding.FragmentStudentAnalyticBinding;
import com.akeem.eduhub.databinding.FragmentStudentResultBinding;
import com.akeem.student.StudentHome;
import com.akeem.student.StudentViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StudentAnalytic extends Fragment {
    private StudentViewModel svm;
    private String student_id;
    private LineChart lineChart;
    private BarChart barChart;


    private FragmentStudentAnalyticBinding binding;
    private Dialog loading;
    public StudentAnalytic(StudentViewModel svm, String student_id) {
        this.svm = svm;
        this.student_id = student_id;
        if(StudentHome.Istudent.getMatric_no() == null){
            getStudent(student_id);
        }
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_student_analytic, container, false);
        lineChart = binding.lineChart;
        barChart = binding.barChart;
        if(StudentHome.Istudent.getMatric_no() != null){
            setChartData();
        }
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private void getStudent(String student_id){
        svm.getStudent(student_id).observe(this, student -> {
            if (student != null){
                StudentHome.Istudent=student;
                if(binding != null){
                    setChartData();
                }
            }
            else {
                if(loading != null){
                    loading.dismiss();
                    Snackbar.make(binding.getRoot(),"Network Error Unable to Connect the internet..",Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        loading.show();
        if(StudentHome.Istudent.getMatric_no() == null){
            getStudent(student_id);
        }

    }

    private void setChartData() {
        List<BarEntry> entriesBar = new ArrayList<>();
        entriesBar.add(new BarEntry(0f,22f));
        entriesBar.add(new BarEntry(1f,18f));
        entriesBar.add(new BarEntry(2f,15f));
        entriesBar.add(new BarEntry(3f,13f));
        entriesBar.add(new BarEntry(4f,10f));
        BarDataSet set = new BarDataSet(entriesBar, "BarDataSet");
        BarData dataA = new BarData(set);
        dataA.setBarWidth(0.9f); // set custom bar width
        barChart.setData(dataA);
        barChart.setFitBars(true); // make the x-axis fit exactly all bars
        barChart.invalidate(); // refresh

        //linechart

        List<Entry> entriesLine = new ArrayList<>();
//            HashMap<Float,Float> line = analytic_data.get("line");
//            for (Map.Entry<Float,Float> entry:bar.entrySet()) {
//                entriesLine.add(new Entry(entry.getValue(),entry.getKey()));
//            }
        LineDataSet dataSet = new LineDataSet(entriesLine,"Score Trend");
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        List<ILineDataSet> dataSets =new ArrayList<>();
        dataSets.add(dataSet);

        LineData data = new LineData(dataSets);

        lineChart.setData(data);
        XAxis x = lineChart.getXAxis();
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.invalidate();
    }
}