package com.nisith.covid19application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailedActivity extends AppCompatActivity {

    private ImageView flagImageView;
    private TextView countryNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        setUpLayout();

        Intent intent = getIntent();
        int flagId = intent.getIntExtra("FLAG_ID",-1);
        if (flagId != -1) {
            Picasso.get().load(flagId).fit().into(flagImageView);
        }else {
            flagImageView.setImageResource(R.drawable.ic_defalt_flag);
        }
    }

    private void setUpLayout(){
        Toolbar appToolbar = findViewById(R.id.app_toolbar);
        TextView toolbarTextView = appToolbar.findViewById(R.id.toolbar_text_view);
        toolbarTextView.setText("All Details");
        setSupportActionBar(appToolbar);
        setTitle("");
        appToolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        flagImageView = findViewById(R.id.flag_image_view);
        countryNameTextView = findViewById(R.id.country_name_text_view);
    }


}
