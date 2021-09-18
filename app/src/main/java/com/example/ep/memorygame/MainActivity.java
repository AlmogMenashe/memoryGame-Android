package com.example.ep.memorygame;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ArrayList<Integer> arr_image;
    ArrayList<ImageButtonClass> arr_object;
    int z=0,x,y,count=0,dismissCounter=12,row,column;
    ImageButton x_Button;

    LinearLayout.LayoutParams layoutParameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutParameters = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1.0f
        );

    }

    @Override
    protected void onStart() {
        super.onStart();

        z=0;
        count=0;

        String[] level = {"Easy","Medium","Hard"};

        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Set Level")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setSingleChoiceItems(level, 1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case 0:
                                startGame(4);
                              break;
                            case 1:
                                startGame(5);
                                break;
                            case 2:
                                startGame(7);
                                break;
                        }

                        dialog.dismiss();

                    }
                }).setCancelable(false).show();
    }


    @SuppressLint("NewApi")
    public void startGame(int level) {
        // level = 4 - easy (6), 5 - medium (8), 7 - hard (12)

        dismissCounter=(level-1)*2;

        arr_image = new ArrayList<>();
        arr_object = new ArrayList<>();

        for(int j=0 ; j < 2 ; j++)
            for(int i=1 ; i < level ; i++)
                arr_image.add(getResources().getIdentifier("ex"+i, "drawable", getPackageName()));

        Collections.shuffle(arr_image);

        List<LinearLayout> layouts = new ArrayList<>();
        layouts.add((LinearLayout) findViewById(R.id.row_1));
        layouts.add((LinearLayout) findViewById(R.id.row_2));
        layouts.add((LinearLayout) findViewById(R.id.row_3));
        layouts.add((LinearLayout) findViewById(R.id.row_4));

        for (LinearLayout l : layouts) {
            l.removeAllViews();
        }

        if (level == 7) {
            row = 4;
            column = 3;
        }else if (level == 5){
            row = 4;
            column = 2;
        }else if (level == 4) {
            row = 3;
            column = 2;
        }


        for (int i = 0 ; i < column ; i++) {
            for (int j = 0; j < row; j++) {
                ImageButton button = new ImageButton(this);

                button.setId(666+i*10+j);
                button.setImageResource(R.mipmap.imagefound);
                button.setLayoutParams(layoutParameters);
                button.setAdjustViewBounds(true);
                button.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        func_button(v);
                    }
                });

                layouts.get(j).addView(button,i);
            }
        }


    }

    public void func_button(View view) {

        int id_button;

        if(view instanceof ImageButton) {
            final ImageButton imageButton = (ImageButton) view;

            if((id_button = findIdButton(view.getId())) == -1 ) {
                arr_object.add(new ImageButtonClass(arr_image.get(z), view.getId()));

                imageButton.setImageResource(arr_image.get(z));
                z++;

            }
            else {

                imageButton.setImageResource(arr_object.get(id_button).getId());
            }

            if(count == 0) {
                x = arr_object.get(findIdButton(view.getId())).getId();
                x_Button = imageButton;
                count++;
            }
            else
            {
                y= arr_object.get(findIdButton(view.getId())).getId();
                count=0;
                if(x == y && x_Button != view) {
                    Toast.makeText(this,"yes",Toast.LENGTH_LONG).show();
                    view.setVisibility(view.INVISIBLE);
                    x_Button.setVisibility(view.INVISIBLE);

                    if ((dismissCounter -=2) ==0){
                        Intent intent = new Intent(this,MainActivity.class);
                        startActivity(intent);
                    }
                }
            }

            Timer buttonTimer = new Timer();
            buttonTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            imageButton.setImageResource(R.mipmap.imagefound);
                        }
                    });
                }
            }, 3000);
        }


    }

    private int findIdButton(int id) {

        int i=0;

        for(ImageButtonClass image : arr_object)
        {
            if(image.getArrId() == id)

                return i;
            i++;
        }
        return -1;
    }
}
