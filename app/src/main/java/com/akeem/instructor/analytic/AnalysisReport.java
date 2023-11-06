package com.akeem.instructor.analytic;

import android.app.Dialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akeem.eduhub.R;
import com.akeem.eduhub.databinding.FragmentAnalysisReportBinding;
import com.akeem.instructor.InstructorViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AnalysisReport extends Fragment {
    private InstructorViewModel ivm;
    private HashMap<String, HashMap<Float,Float>> analytic_data;
    private ScatterChart scatterChart;
    private PieChart pieChart;
    private BarChart barChart;
    private String lecturer_id;
    private Dialog loading;





    public AnalysisReport(InstructorViewModel ivm,String lecturer_id) {
        // Required empty public constructor
        this.ivm = ivm;
        this.lecturer_id = lecturer_id;
        ivm.getAnalyticdata(lecturer_id).observe(this, stringHashMapHashMap -> {
            analytic_data = stringHashMapHashMap;
        });
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentAnalysisReportBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_analysis_report,container,false);
        scatterChart = binding.scatterChart;
        pieChart = binding.pieChart;
        barChart = binding.barChart;
        if(analytic_data != null){

        }
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        loading = new Dialog(requireContext());
        loading.setContentView(R.layout.load_lay);
        loading.show();
        if(analytic_data == null){
            ivm.getAnalyticdata(lecturer_id).observe(this, stringHashMapHashMap -> {
                if(analytic_data != null && analytic_data.isEmpty()){
                    loading.dismiss();
                }
                if (analytic_data != null && !analytic_data.isEmpty()){
                    analytic_data = stringHashMapHashMap;
                    populateData();
                }
            });
        }
    }

    private void populateData(){
        List<BarEntry> entriesBar = new ArrayList<>();
        HashMap<Float,Float> bar = analytic_data.get("bar");
        for (Map.Entry<Float,Float> entry:bar.entrySet()) {
            entriesBar.add(new BarEntry(entry.getKey(),entry.getValue()));
        }
        BarDataSet set = new BarDataSet(entriesBar, "BarDataSet");
        BarData dataA = new BarData(set);
        dataA.setBarWidth(0.9f); // set custom bar width
        barChart.setData(dataA);
        barChart.setFitBars(true); // make the x-axis fit exactly all bars
        barChart.invalidate(); // refresh

        //Pie
        List<PieEntry> entriesPie = new ArrayList<>();
        String[] mapper = {"Male Above Average", "Male Below Average", "Female Above Average", "Female Below Average"};
        HashMap<Float,Float> pie = analytic_data.get("pie");
        for (Map.Entry<Float,Float> entry:bar.entrySet()) {
            entriesPie.add(new PieEntry(entry.getValue(),mapper[entry.getKey().intValue()]));
        }
        PieDataSet setB = new PieDataSet(entriesPie, "Gender Distribution");
        setB.setSliceSpace(2f);
        PieData dataB = new PieData(setB);
        pieChart.setUsePercentValues(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setDrawRoundedSlices(true);
        pieChart.setData(dataB);
        pieChart.invalidate();

        //line
        List<Entry> entriesLine = new ArrayList<>();
        HashMap<Float,Float> line = analytic_data.get("line");
        for (Map.Entry<Float,Float> entry:bar.entrySet()) {
            entriesLine.add(new Entry(entry.getValue(),entry.getKey()));
        }
        ScatterDataSet dataSet = new  ScatterDataSet(entriesLine,"Student Performance");
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSet.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        List<IScatterDataSet> dataSets =new ArrayList<>();
        dataSets.add(dataSet);

        ScatterData data = new ScatterData(dataSets);

        scatterChart.setData(data);
        XAxis x = scatterChart.getXAxis();
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        scatterChart.getAxisRight().setEnabled(false);
        scatterChart.invalidate();
    }
}