package com.example.admin.twitterintegration;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.io.File;
import java.net.URL;

import static android.R.attr.bitmap;
import static com.example.admin.twitterintegration.R.id.profileImage;
import static com.example.admin.twitterintegration.R.id.textViewUsername;

public class ProfileActivity extends AppCompatActivity {

    private ImageLoader imageLoader;

    //NetworkImageView Ojbect
    private ImageView profileImage;

    //TextView object
    private TextView textViewUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profileImage= (ImageView) findViewById(R.id.profileImage);
        textViewUsername= (TextView) findViewById(R.id.textViewUsername);


        //Getting the intent
        Intent intent = getIntent();

        //Getting values from intent
        String username = intent.getStringExtra(MainActivity.KEY_USERNAME);
        System.out.println("username"+username);
        String profileImageUrl = intent.getStringExtra(MainActivity.KEY_PROFILE_IMAGE_URL);
        System.out.println("profileImageUrl*****" + profileImageUrl);
        try {
            URL url = new URL(profileImageUrl);
            System.out.println("ImagePath*****" + url);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            System.out.println("bmp*****" + bmp);
            profileImage.setImageBitmap(bmp);


        }catch (Exception e){
            e.printStackTrace();
        }



        //Setting the username in textview
        textViewUsername.setText("@"+username);
    }



}



        //Initializing views

