package com.akeem.instructor.resources;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.akeem.eduhub.R;
import com.github.barteksc.pdfviewer.PDFView;

public class DocumentViewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_viewer);
        Uri uri = getIntent().getData();
        PDFView view = findViewById(R.id.pdf_view);
        view.fromUri(uri)
                .enableAntialiasing(true)
                .load();
    }
}