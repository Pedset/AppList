package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Display;
import android.widget.ImageView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent in = getIntent();
        int index = in.getIntExtra("com.example.ITEM_INDEX", -1);

        if (index > -1) {
            int pic = getImg(index);
            ImageView img = findViewById(R.id.imageView);
            scale(img, pic);
        }
    }


    private int getImg(int index) {
        switch (index) {
            case 0: {
               return R.drawable.sweden;
            }
            case 1: {
                return R.drawable.denmark;
            }
            case 2: {
                return R.drawable.norway;
            }
            default: return -1;
        }
    }

    // just to resize the image if the images res is higher than the screens res.
    private void scale(ImageView img, int pic){
        Display screen = getWindowManager().getDefaultDisplay();
        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true; // turn on boundaries
        BitmapFactory.decodeResource(getResources(), pic, options);

        int imgWidth = options.outWidth;
        int screenWidth = screen.getWidth();

        if (imgWidth >  screenWidth){
            int ratio = Math.round( (float) imgWidth / (float) screenWidth);
            options.inSampleSize = ratio;
        }
        options.inJustDecodeBounds = false;
        Bitmap scaledImg = BitmapFactory.decodeResource(getResources(), pic, options);
        img.setImageBitmap(scaledImg);
    }
}
