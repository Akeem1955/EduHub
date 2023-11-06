package com.ashiri.instuctor;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Instructor {

    //Variables

    @PrimaryKey
    private int id;
    private String password;
    private String name;
    @ColumnInfo(defaultValue = "")
    private String email;
    @ColumnInfo(defaultValue = "")
    private String phone;

    //Constructor

    public Instructor(){}

    //Getter ansd Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
