package com.appdeveloper.rh.cmbteamapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    ImageView avatarImageView;
    TextView nameTextView;
    TextView titleTextView;
    TextView bioTextView;

    TeamObject obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        obj = getIntent().getParcelableExtra("theObject");

        avatarImageView = (ImageView) findViewById(R.id.teamImageView);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        bioTextView = (TextView) findViewById(R.id.bioTextView);

        Glide.with(this).load(obj.avatar).into(avatarImageView);
        nameTextView.setText(String.format("%s %s", obj.firstName, obj.lastName));
        titleTextView.setText(obj.title);
        bioTextView.setText(obj.bio);
    }
}
