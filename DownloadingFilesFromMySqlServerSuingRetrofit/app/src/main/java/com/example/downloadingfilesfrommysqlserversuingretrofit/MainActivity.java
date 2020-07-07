package com.example.downloadingfilesfrommysqlserversuingretrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private EditText edtSn;
    private Button btnDownload, btnSave;

    private byte[] pdfInBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtSn = findViewById(R.id.edtSN);
        btnDownload = findViewById(R.id.btnDownload);
        btnSave = findViewById(R.id.btnSaveFile);

        btnSave.setVisibility(View.GONE);

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadDocument();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isStoragePermissionGranted()){
                    try {
                        save();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }

    private void save() throws IOException {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/TestDocument");

        if( !myDir.exists()){
            myDir.mkdir();
        }

        try {

            File fileLocation = new File(myDir, "myPDF.pdf");

            FileOutputStream fos = new FileOutputStream(fileLocation);
            fos.write(pdfInBytes);
            fos.flush();
            fos.close();

            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();


        }catch (Exception e){e.printStackTrace();}


    }

    public boolean isStoragePermissionGranted() {
        String TAG = "Storage Permission";
        if (Build.VERSION.SDK_INT >= 23) {
            if (this.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {
                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    private void downloadDocument() {


        String sn = edtSn.getText().toString().trim();

        Call<DocumentPOJO> call = RetrofitClient.getInstance().getAPI().downloadDocs(Integer.parseInt(sn));
        call.enqueue(new Callback<DocumentPOJO>() {
            @Override
            public void onResponse(Call<DocumentPOJO> call, Response<DocumentPOJO> response) {

                if(response.body() != null){

                    String encodedPdf = response.body().getEncodedPDF();
                    pdfInBytes = Base64.decode(encodedPdf, Base64.DEFAULT);

                    btnSave.setVisibility(View.VISIBLE);

                    Toast.makeText(MainActivity.this, "File Can be Saved", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(MainActivity.this, "Invalid SN", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<DocumentPOJO> call, Throwable t) {

            }
        });

    }

}