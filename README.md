## Interface View


<p align="center">
  <img width="200px"src="https://i.ibb.co/7tnt292/Screenshot-2022-02-09-17-31-01-701-com-example-loadimagefromurl.jpg" />
</p>



## Uses Permission

``` xml

<uses-permission android:name="android.permission.INTERNET"/>

``` 

## Java Code

``` java

package com.example.loadimagefromurl;

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

```

## XML

```xml

<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <TextView
        android:id="@+id/title"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Load Image from URL"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        android:textSize="20dp"
        android:textColor="@color/purple_700"
        android:layout_marginTop="20dp" />

        <ImageView
            android:id="@+id/image"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:src="@drawable/default_image"
            app:layout_constraintTop_toBottomOf="@id/title"
            app:layout_constraintBottom_toTopOf="@id/url"
            android:layout_margin="30dp"/>

    <EditText
        android:id="@+id/url"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="20dp"
        android:hint="URL"
        android:inputType="text"
        android:minHeight="48dp"
        app:layout_constraintBottom_toTopOf="@id/buttonSection"
        tools:layout_editor_absoluteX="20dp" />

    <LinearLayout
        android:id="@+id/buttonSection"
        android:gravity="center"
        app:layout_constraintBottom_toBottomOf="parent"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        android:padding="10dp"
        android:orientation="horizontal">
        <Button
            android:id="@+id/clear"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_margin="10dp"
            android:text="clear"/>

        <Button
            android:id="@+id/fetch"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_margin="10dp"
            android:text="fetch"/>
    </LinearLayout>

</androidx.constraintlayout.widget.ConstraintLayout>
```
