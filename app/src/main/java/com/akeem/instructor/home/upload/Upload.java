package com.akeem.instructor.home.upload;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class Upload extends BaseObservable {
    private String title;
    private String material_info;


    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Bindable
    public String getMaterial_info() {
        return material_info;
    }

    public void setMaterial_info(String material_info) {
        this.material_info = material_info;
    }
}
