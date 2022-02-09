package com.example.loadimagefromurl;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.net.URL ;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {


    Button btn_fetch ;
    Button btn_clear ;
    EditText editTextUrl ;
    ImageView imageView ;
    Handler handler = new Handler();
    ProgressDialog progressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_fetch = findViewById(R.id.fetch);
        btn_clear = findViewById(R.id.clear);
        editTextUrl = findViewById(R.id.url);
        imageView = findViewById(R.id.image);

        btn_fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String URL = editTextUrl.getText().toString() ;
                new FetchImage(URL).start();
            }
        });


        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextUrl.setText("");
                imageView.setImageBitmap(null);
            }
        });


    }


    class FetchImage extends Thread{
        Bitmap bitmap ;
        String URl ;
        public FetchImage(String URL){
            this.URl = URL ;

        }

        @Override
        public void run() {

            handler.post(new Runnable() {
                @Override
                public void run() {
                    progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Getting Your Image Now");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }
            });

            InputStream inputStream = null;

            try {
                inputStream = new URL(this.URl).openStream() ;
                bitmap = BitmapFactory.decodeStream(inputStream);

            } catch (IOException e) {
                e.printStackTrace();
            }

            handler.post(new Runnable() {
                @Override
                public void run() {
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();

                    }

                    imageView.setImageBitmap(bitmap);
                }
            });

        }
    }
}