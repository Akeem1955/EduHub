package com.akeem.instructor.resources;

import android.net.Uri;

public class ResourcesModel {
    private Uri uri;
    private String description;
    private String mimetype;

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public ResourcesModel() {
    }

    public ResourcesModel(Uri uri, String description, String mime) {
        this.uri = uri;
        this.description = description;
        this.mimetype = mime;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
