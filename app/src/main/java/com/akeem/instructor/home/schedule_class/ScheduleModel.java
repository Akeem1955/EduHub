package com.akeem.instructor.home.schedule_class;

public class ScheduleModel {
    private String year;
    private String day_name;
    private String content;
    private String time;
    private String venue;




    public ScheduleModel(String year, String day_name, String content, String time, String venue) {
        this.year = year;
        this.day_name = day_name;
        this.content = content;
        this.time = time;
        this.venue = venue;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDay_name() {
        return day_name;
    }

    public void setDay_name(String day_name) {
        this.day_name = day_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }
}
