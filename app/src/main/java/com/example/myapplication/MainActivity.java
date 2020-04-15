package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.ActionMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    View view1;

    boolean success = false;

    ListView myListView;
    String[] countries;
    String[] capitals;
    String[] language;

    SeekBar seekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//-------------------------- Background
        view1 = this.getWindow().getDecorView();
        view1.setBackgroundColor(Color.WHITE);
//-------------------------- Seekbar for brightness
        seekbar = findViewById(R.id.seekBar);
        seekbar.setMax(255);
        seekbar.setProgress(getBrightness());

        getPermission();

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser && success){
                    setBrightness(progress);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(!success){
                    Toast.makeText(MainActivity.this, "Permission not granted!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // ----------------------------------------------------------------
                // take values from String.xml for our arraylist
        Resources res = getResources();
        myListView = findViewById(R.id.myListView);
        countries = res.getStringArray(R.array.countries);
        capitals = res.getStringArray(R.array.capitals);
        language = res.getStringArray(R.array.Language);


        //
        itemAdapter itemAdapter= new itemAdapter(this, countries, language, capitals);
        myListView.setAdapter(itemAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent showDetailActivity =
                        new Intent(getApplicationContext(), Main2Activity.class);
                showDetailActivity.putExtra("com.example.ITEM_INDEX", position);
                startActivity(showDetailActivity);
            }
        });
            }
// **************************BaCKGROUND COLOR CHANGE '************

                public void goGreen(View view){
                view1.setBackgroundColor(Color.GREEN);
            }

                public void goRed(View view){
                    view1.setBackgroundColor(Color.RED);
               }

                public void goYellow(View view){
                   view1.setBackgroundColor(Color.YELLOW);
               }

 //*******************************************************
             // ändra och sätta värde på brightness
               private void setBrightness(int brightness) {
        if(brightness < 0) {
                brightness = 0;
        }
            else if (brightness>255){
                brightness=255;
                   }
                   ContentResolver contentResolver = getApplicationContext().getContentResolver();
                   Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, brightness);

         }
         // ------------------------------------------
         // ta värde
         private int getBrightness(){
            int brightness = 100;
            try{
                ContentResolver contentResolver = getApplicationContext().getContentResolver();
                brightness = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS);
            }
            catch (Settings.SettingNotFoundException e){
                e.printStackTrace();
            }
            return brightness;
         }

         //--------------------- PERMISSION METHOD for brightness, check if we have permission, if no? then get permission
            private  void getPermission(){
            boolean value;
            if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                value = Settings.System.canWrite(getApplicationContext());
                if (value){
                    success = true;
                }
                else{
                    Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
                    startActivityForResult(intent, 1000);

                }
            }
            }

            //--------------------- PERMISSION METHOD

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                boolean value = Settings.System.canWrite(getApplicationContext());
                if (value) {
                    success = true;
                } else {
                    Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}  ///*********************************
