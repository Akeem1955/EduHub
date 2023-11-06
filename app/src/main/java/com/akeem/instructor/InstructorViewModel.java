package com.akeem.instructor;

import static com.akeem.background.EduHubHandler.ASSIGNMENT_UPDATE;

import android.net.Uri;
import android.os.Bundle;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.akeem.background.EduHubProcessor;
import com.akeem.instructor.home.schedule_class.ScheduleModel;
import com.akeem.instructor.resources.ResourcesModel;
import com.akeem.student.parser.Student;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstructorViewModel extends ViewModel {
    private ValueEventListener analytic_listen;
    private String lecturer_id;
    private int[] day_of_week;
    private String[] content;
    private List<ScheduleModel> data = new ArrayList<>();
    private String[] time;
    private String[] venue;
    private Runnable runnable;
    private HashMap<String, HashMap<Float,Float>> analytic = new HashMap<>();
    private MutableLiveData<HashMap<String, HashMap<Float,Float>>> analytic_live = new MutableLiveData<>(null);
    private final Calendar calendar = Calendar.getInstance();
    private final int year =Integer.parseInt(calendar.toString().split(",")[13].split("=")[1]);
    private int week =Integer.parseInt(calendar.toString().split(",")[15].split("=")[1]);
    private int current = Integer.parseInt(calendar.toString().split(",")[17].split("=")[1]);
    private final DatabaseReference reference   = FirebaseDatabase.getInstance().getReference("Schedules");
    private final DatabaseReference student_reference   = FirebaseDatabase.getInstance().getReference("Students");
    private DatabaseReference child;
    private ValueEventListener listener;
    private List<String> mod = new ArrayList<>();
    private final MutableLiveData<List<ScheduleModel>> schedule_live = new MutableLiveData<>(data);
    private final StorageReference sRefernce = FirebaseStorage.getInstance().getReference("resources");
    private OnSuccessListener<ListResult> success;
    private OnFailureListener failure;
    private final MutableLiveData<List<ResourcesModel>> uri_live = new MutableLiveData<>(new ArrayList<>());
    private OnSuccessListener<Uri> uri_listener;
    private List<StorageReference> sReferences;
    private final List<ResourcesModel> uris = new ArrayList<>();
    private OnSuccessListener<StorageMetadata> metadata_listener;
    private List<String> metadata = new ArrayList<>();
    private List<Uri> uriList = new ArrayList<>();
    //question to ask
    //will there be race
    //does firebase create multiple thread when when getting list of files uri
    //you know you add listener for each files to get the uri







    public InstructorViewModel(){
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<List<String>> indicator = new GenericTypeIndicator<List<String>>() {};
                mod = snapshot.getValue(indicator);
                System.out.println(mod.isEmpty() + " ");
                initializeDayOfweek();
                populateData();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        analytic_listen = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<HashMap<String, Student>> type = new GenericTypeIndicator<HashMap<String, Student>>() {};
                HashMap<String,Student> all_student = snapshot.getValue(type);
                runnable = () -> analytic_dataProcess(all_student);
                EduHubProcessor.getInstance().getHandler().post(runnable);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                analytic_live.postValue(new HashMap<>());
            }
        };
        //getting resources listener
        uri_listener = uri -> {
            uriList.add(uri);
            uris.clear();
            for (String s:metadata){
                for (Uri u:uriList) {
                    ResourcesModel model =new ResourcesModel();
                    model.setDescription(s.split("!@#%&")[0]);
                    model.setUri(u);
                    model.setMimetype(s.split("!@#%&")[1]);
                    uris.add(model);
                }
            }
            uri_live.postValue(uris);
        };

        metadata_listener = storageMetadata -> {
            metadata.add( storageMetadata.getCustomMetadata("description") + "!@#%&" +storageMetadata.getContentType());
            storageMetadata.getReference().getDownloadUrl().addOnSuccessListener(uri_listener);
        };
        success = listResult -> {
            sReferences = listResult.getItems();
            if(sReferences.isEmpty()){
                uri_live.postValue(new ArrayList<>());
                return;
            }
            for (StorageReference sr: sReferences) {
                sr.getMetadata().addOnSuccessListener(metadata_listener);
            }
        };
        failure = e -> uri_live.postValue(null);
    }






    public MutableLiveData<List<ScheduleModel>> getClassSchedule(String location){

        child = reference.child(location);
        child.addValueEventListener(listener);
        return schedule_live;
    }




    public void notifyStudent(String msg, String title){
        Message message = Message.obtain(EduHubProcessor.getInstance().getHandler());
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        bundle.putString("msg",msg);
        message.what=ASSIGNMENT_UPDATE;
        message.setData(bundle);
        message.sendToTarget();
    }





    @Override
    protected void onCleared() {
        super.onCleared();
        child.removeEventListener(listener);
        EduHubProcessor.getInstance().getHandler().removeCallbacksAndMessages(null);
        student_reference.removeEventListener(analytic_listen);
        listener=null;
        runnable = null;
        analytic_listen = null;
        failure=null;
        success=null;
        metadata_listener = null;
    }




    private void initializeDayOfweek() {
        day_of_week = new int[mod.size()];
        content = new String[mod.size()];
        time = new String[mod.size()];
        venue = new String[mod.size()];
        for (int i = 0; i < day_of_week.length; i++) {
            day_of_week[i] = Integer.parseInt(mod.get(i).split(",")[0]);
            content[i] = mod.get(i).split(",")[1];
            time[i] = mod.get(i).split(",")[2];
            venue[i] = mod.get(i).split(",")[3];
        }
    }




    private void populateData() {
        data.clear();
        for (int i = 0; i < 1; i++) {
            int j = 0;
            for (int k : day_of_week) {
                calendar.setWeekDate(year, week, k);
                if (Integer.parseInt(calendar.toString().split(",")[17].split("=")[1]) < current || Integer.parseInt(calendar.toString().split(",")[17].split("=")[1]) > current ) {
                    return;
                }
                System.out.println(calendar.getTime());
                String[] temp = calendar.getTime().toString().split(" ");
                data.add(new ScheduleModel( temp[5], (temp[0] + " " + temp[1] + " " + temp[2]),content[j],time[j],venue[j]));
                j++;
            }
        }
        schedule_live.postValue(data);

    }





    //Analytic data
    //student atttendance against student score
    //student male and female above average and below average
    //student size against a performance excellent good and poor
    //student have atteendance, score, gender,

    public MutableLiveData<HashMap<String, HashMap<Float,Float>>> getAnalyticdata(String lecturer){
        student_reference.addValueEventListener(analytic_listen);
        lecturer_id = lecturer;
        return analytic_live;
    }
    private void analytic_dataProcess(HashMap<String,Student> all_student ){
        if(all_student == null)return;
        analytic.clear();
        HashMap<Float, Float> a = new HashMap<>();
        HashMap<Float, Float> b = new HashMap<>();
        HashMap<Float, Float> c = new HashMap<>();
        c.put(0f,0f);
        c.put(1f,0f);
        c.put(2f,0f);
        c.put(3f,0f);
        c.put(4f,0f);
        b.put(0f,0f);
        b.put(1f,0f);
        b.put(2f,0f);
        b.put(3f,0f);
        for (Map.Entry<String,Student> entries:all_student.entrySet()) {
            //analytic for line chart
            a.put(entries.getValue().getAttendance().get(lecturer_id).floatValue(), entries.getValue().getTotal_score().get(lecturer_id).floatValue());

            if(entries.getValue().getGender().equalsIgnoreCase("male")
                    && entries.getValue().getTotal_score().get(lecturer_id) > 15)
            {
                b.put(0f, b.get(0f)+1);
            }
            else if (entries.getValue().getGender().equalsIgnoreCase("male")
                    && entries.getValue().getTotal_score().get(lecturer_id) <= 15)
            {
                b.put(1f, b.get(1f)+1);
            }
            else if (entries.getValue().getGender().equalsIgnoreCase("female")
                    && entries.getValue().getTotal_score().get(lecturer_id) > 15)
            {
                b.put(2f, b.get(2f)+1);
            }
            else if (entries.getValue().getGender().equalsIgnoreCase("male")
                    && entries.getValue().getTotal_score().get(lecturer_id) <= 15)
            {
                b.put(3f, b.get(3f)+1);
            }
            //analytic for barchart
            if((entries.getValue().getTotal_score().get(lecturer_id).floatValue()/30)*100  >= 70){
                c.put(0f,c.get(0f) + 1);
            } else if ((entries.getValue().getTotal_score().get(lecturer_id).floatValue()/30)*100  >= 60) {
                c.put(1f,c.get(0f) + 1);
            } else if ((entries.getValue().getTotal_score().get(lecturer_id).floatValue()/30)*100  >= 50) {
                c.put(2f,c.get(0f) + 1);
            } else if ((entries.getValue().getTotal_score().get(lecturer_id).floatValue()/30)*100  >= 40) {
                c.put(3f,c.get(0f) + 1);
            } else if ((entries.getValue().getTotal_score().get(lecturer_id).floatValue()/30)*100  < 40) {
                c.put(4f,c.get(0f) + 1);
            }
        }
        analytic.put("line", a);
        analytic.put("pie", b);
        analytic.put("bar", c);
        analytic_live.postValue(analytic);
    }


    public MutableLiveData<List<ResourcesModel>> getAllResources(){
        Task<ListResult> task=sRefernce.list(50);
        task.addOnFailureListener(failure);
        task.addOnSuccessListener(success);
        return uri_live;
    }

}
