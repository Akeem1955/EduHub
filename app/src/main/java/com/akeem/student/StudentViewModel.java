package com.akeem.student;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.akeem.background.EduHubProcessor;
import com.akeem.instructor.home.assignment.Assign;
import com.akeem.instructor.home.schedule_class.ScheduleModel;
import com.akeem.instructor.home.test.Test;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentViewModel extends ViewModel {

    //schedule variable
    private final List<ScheduleModel> scheduleModels = new ArrayList<>();
    private final List<Integer> day_of_week = new ArrayList<>();
    private final List<String> content = new ArrayList<>();
    private final List<String> time = new ArrayList<>();
    private final List<String> venue = new ArrayList<>();
    private final Calendar calendar = Calendar.getInstance();
    private final int year =Integer.parseInt(calendar.toString().split(",")[13].split("=")[1]);
    private final int week =Integer.parseInt(calendar.toString().split(",")[15].split("=")[1]);
    private final int current = Integer.parseInt(calendar.toString().split(",")[17].split("=")[1]);

    //ongoing class schedule models
    private final List<ScheduleModel> ongoingClass = new ArrayList<>();


    // other needed variables
    private final MutableLiveData<Student> student = new MutableLiveData<>();
    private final MutableLiveData<List<ResourcesModel>> uri_live = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<List<ScheduleModel>> schedule_live = new MutableLiveData<>(null);
    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Students");
    private final DatabaseReference schedule_reference = FirebaseDatabase.getInstance().getReference("Schedules");
    private final StorageReference sRefernce = FirebaseStorage.getInstance().getReference("resources");
    private ValueEventListener listener;
    private final ValueEventListener schedule_listen;
    private List<StorageReference> sReferences;
    private final List<ResourcesModel> uris = new ArrayList<>();
    private OnSuccessListener<ListResult> success;
    private OnFailureListener failure;
    private OnSuccessListener<Uri> uri_listener;
    private final EduHubProcessor processor = EduHubProcessor.getInstance();
    private Runnable runnable;
    private HashMap<String,List<String>> schedules = new HashMap<>();
    private DatabaseReference assReference = FirebaseDatabase.getInstance().getReference("Assignments");
    private DatabaseReference testReference = FirebaseDatabase.getInstance().getReference("Tests");
    private MutableLiveData<List<Assign>> assignment_live = new MutableLiveData<>();
    private MutableLiveData<List<Test>> test_live = new MutableLiveData<>();
    private OnSuccessListener<StorageMetadata> metadata_listener;
    private List<String> metadata = new ArrayList<>();
    private List<Uri> uriList = new ArrayList<>();
    private final DatabaseReference lectureId = FirebaseDatabase.getInstance().getReference("LectureId");
    private DatabaseReference current_class = FirebaseDatabase.getInstance().getReference("current");




    public StudentViewModel() {

        //listener for schwedules
        schedule_listen = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<HashMap<String, List<String>>>  type = new GenericTypeIndicator<HashMap<String, List<String>>>() {};
                schedules = snapshot.getValue(type);
                runnable = () -> handleScheduleProcesssing();
                processor.getHandler().post(runnable);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        //value event listener
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                student.postValue(snapshot.getValue(Student.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                student.postValue(null);

            }
        };


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

    public MutableLiveData<Student> getStudent(String matric_no){
        reference.child(matric_no).addValueEventListener(listener);
        return student;
    }
    public MutableLiveData<List<ResourcesModel>> getAllResources(){
        Task<ListResult> task=sRefernce.list(50);
        task.addOnFailureListener(failure);
        task.addOnSuccessListener(success);
        return uri_live;
    }
    public MutableLiveData<List<ScheduleModel>> getSchedules(){
        schedule_reference.addValueEventListener(schedule_listen);
        return schedule_live;
    }

    public void updateStudent(Student student){
        reference.child(student.getMatric_no()).setValue(student);
    }

    public MutableLiveData<List<Assign>> getAssignment(){
        assReference.orderByKey().get().addOnSuccessListener(dataSnapshot -> {
            GenericTypeIndicator<List<Assign>> type =new GenericTypeIndicator<List<Assign>>() {};
            assignment_live.postValue(dataSnapshot.getValue(type));
        });
        return assignment_live;
    };
    public MutableLiveData<List<Test>> getTest(){
        testReference.orderByKey().get().addOnSuccessListener(dataSnapshot -> {
            GenericTypeIndicator<List<Test>> type =new GenericTypeIndicator<List<Test>>() {};
            test_live.postValue(dataSnapshot.getValue(type));
        });
        return test_live;
    }

    public MutableLiveData<String> getCurrentClass(){
        MutableLiveData<String> current = new MutableLiveData<>();
        current_class.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                current.postValue(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return current;
    }




    @Override
    protected void onCleared() {
        super.onCleared();
        EduHubProcessor.getInstance().getHandler().removeCallbacks(runnable);
        listener = null;
        failure = null;
        success = null;
        uri_listener = null;
        runnable = null;
    }


    private void handleScheduleProcesssing(){

        for (Map.Entry<String, List<String>> s: schedules.entrySet()) {
            List<String> values = s.getValue();
            values.sort((o1, o2) -> {
                String val_a = o1.split(",")[2].split(" ")[0].replaceAll("[^0-9]+","");
                String val_b = o2.split(",")[2].split(" ")[0].replaceAll("[^0-9]+","");
                String val_a2 = o1.split(",")[2].split(" ")[0].replaceAll("[0-9]+","");
                String val_b2 = o2.split(",")[2].split(" ")[0].replaceAll("[0-9]+","");
                return (Integer.parseInt(val_a) - Integer.parseInt(val_b)) + (val_a2).compareTo(val_b2);
            });
            for (String value : values) {
                calendar.setWeekDate(year, week, Integer.parseInt(value.split(",")[0]));
                if (Integer.parseInt(calendar.toString().split(",")[17].split("=")[1]) < current || Integer.parseInt(calendar.toString().split(",")[17].split("=")[1]) > current ) {
                    continue;
                }
                day_of_week.add(Integer.parseInt(value.split(",")[0]));
                content.add(value.split(",")[1]);
                time.add(value.split(",")[2]);
                venue.add(value.split(",")[3]);
            }
        }
        populateData();
    }
    public MutableLiveData<HashMap<String, String>> getlecturessid(){
        MutableLiveData<HashMap<String,String>> map= new MutableLiveData<>();
        lectureId.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<HashMap<String,String>> indicator = new GenericTypeIndicator<HashMap<String, String>>() {};
                map.postValue( snapshot.getValue(indicator));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return map;
    }


    private void populateData() {

        scheduleModels.clear();
        for (int i = 0; i < 1; i++) {
            int j = 0;
            for (int k : day_of_week) {
                calendar.setWeekDate(year, week, k);
                System.out.println(calendar.getTime());
                String[] temp = calendar.getTime().toString().split(" ");
                scheduleModels.add(new ScheduleModel( temp[5], (temp[0] + " " + temp[1] + " " + temp[2]), content.get(j), time.get(j), venue.get(j)));
                j++;
            }
        }
        schedule_live.postValue(scheduleModels);
    }

}