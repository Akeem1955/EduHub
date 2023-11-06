package com.akeem.student;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.ProgressBar;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.akeem.background.EduHubProcessor;
import com.akeem.eduhub.R;
import com.akeem.student.parser.Student;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class AttendanceService extends Service {
    private NotificationManagerCompat managerCompat;
    private NotificationChannelCompat channelCompat;
    private WifiManager manager;
    private Runnable runnable;
    private Handler handler;
    private int attendance_count;
    private String ssid;
    private ValueEventListener listener;
    private ValueEventListener lec_listen;
    private Student student;
    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Students");
    private final DatabaseReference lectureId = FirebaseDatabase.getInstance().getReference("LectureId");

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler(Looper.getMainLooper());
        managerCompat = NotificationManagerCompat.from(getApplicationContext());
        channelCompat =
                new NotificationChannelCompat.Builder(getApplicationContext()
                        .getResources()
                        .getString(R.string.attendance_id),
                        NotificationManagerCompat.IMPORTANCE_HIGH)
                        .setName("Attendance").build();
        managerCompat.createNotificationChannel(channelCompat);
        manager = getSystemService(WifiManager.class);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null && intent.getAction()!= null && intent.getAction().equals("Attendance_stop")){
            managerCompat.cancel(10023);
            stopForeground(true);
            stopSelf();
            return START_NOT_STICKY;
        }
        startForeground(10023,creatNotification(10));
        processs(intent.getStringExtra("matric_no"), intent.getStringExtra("lecture_id"),intent.getStringExtra("subject"));
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }


    private void processs(String matric_no, String lecture_id, String subject){
        runnable = () -> {
            @SuppressLint("MissingPermission") List<ScanResult> scans = manager.getScanResults();
            for (ScanResult result:scans) {
                if (result.SSID.equals(this.ssid)){
                    if(attendance_count > 2){
                        this.handler.postAtFrontOfQueue(() -> {
                            student.getAttendance().replace(subject, student.getAttendance().get(subject) + 1);
                            reference.setValue(student).addOnSuccessListener(unused -> {
                                stopForeground(true);
                                stopSelf();
                            });
                        });
                        return;
                    }
                    attendance_count++;
                    this.handler.postAtFrontOfQueue(() -> startForeground(10023,creatNotification(50 * attendance_count)));
                    return;
                }
            }
        };
        manager.startScan();
        lec_listen = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ssid = snapshot.getValue(String.class);
                for (int i = 0; i < 5; i++) {
                    long milis = 1000 * 60 * 5 * i;
                    EduHubProcessor.getInstance().getHandler().postDelayed(runnable,1000*60*5);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                student = snapshot.getValue(Student.class);
                lectureId.child(lecture_id).addValueEventListener(lec_listen);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        reference.child(matric_no).addValueEventListener(listener);


    }

    @NonNull
    private Notification creatNotification(int progress){
        RemoteViews view = new RemoteViews(getPackageName(),R.layout.progress_attendance);
        view.setProgressBar(R.id.attend_progress,100,progress,false);

        String title = "Attendance Track";
        Intent intent_stop = new Intent(this,AttendanceService.class);
        intent_stop.setAction("Attendance_stop");
        PendingIntent intent = PendingIntent.getService(this,1955,intent_stop,PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Builder(getApplicationContext(),getApplicationContext().getResources().getString(R.string.attendance_id))
                .setContentTitle(title)
                .setContentText("Note! if You cancel the notification Your attendance will be zero :)")
                .setTicker(title)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setCustomContentView(view)
                .setOngoing(true)
                // Add the cancel action to the notification which can
                // be used to cancel the worker
                .addAction(android.R.drawable.ic_delete, "Cancel", intent).build();
    }
}