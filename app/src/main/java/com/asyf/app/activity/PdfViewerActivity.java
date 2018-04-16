package com.asyf.app.activity;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.asyf.app.R;
import com.github.barteksc.pdfviewer.PDFView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

@ContentView(R.layout.activity_pdf_viewer)
public class PdfViewerActivity extends AppCompatActivity {

    @ViewInject(value = R.id.btn)
    Button btn;
    @ViewInject(value = R.id.pdfView)
    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_pdf_viewer);
        x.view().inject(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PdfViewerActivity.this, "diaji", Toast.LENGTH_SHORT).show();
                String path = Environment.getExternalStorageDirectory() + "/test.pdf";
                pdfView.fromFile(new File(path)).swipeHorizontal(true).load();
            }
        });
    }

}
