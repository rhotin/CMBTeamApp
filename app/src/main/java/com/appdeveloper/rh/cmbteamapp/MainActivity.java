package com.appdeveloper.rh.cmbteamapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DownloadTeamJSON.Communicator {

    DownloadTeamJSON downloadTeamJSON;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ToggleButton toggleListGrid;

    TeamListAdapter teamListAdapter;
    ArrayList<TeamObject> teamArraylist = new ArrayList<>();

    int numberOfColumns = 2;
    RecyclerView.LayoutManager listLayoutManager;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        toggleListGrid = (ToggleButton) findViewById(R.id.toggleButton);
        teamArraylist.clear();

        prefs = this.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        editor = prefs.edit();


        toggleListGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggleListGrid.isChecked()) {
                    listLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(listLayoutManager);
                } else {
                    listLayoutManager = new GridLayoutManager(getApplicationContext(), numberOfColumns);
                    recyclerView.setLayoutManager(listLayoutManager);
                }
                editor.putBoolean("toggle", toggleListGrid.isChecked());
                editor.commit();
            }
        });

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        RecyclerView.ItemDecoration itemDecorationH = new
                DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL);
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.addItemDecoration(itemDecorationH);
        recyclerView.setLayoutManager(listLayoutManager);

        updateListView();
        progressBar.setVisibility(View.VISIBLE);
        if (prefs.getBoolean("toggle", true)) {
            toggleListGrid.performClick();
        } else {
            toggleListGrid.setChecked(false);
            listLayoutManager = new GridLayoutManager(getApplicationContext(), numberOfColumns);
            recyclerView.setLayoutManager(listLayoutManager);
        }
    }

    private void updateListView() {
        downloadTeamJSON = new DownloadTeamJSON(this);
        downloadTeamJSON.execute();
    }

    @Override
    public void updateProgressTo(int progress) {
        progressBar.setProgress(progress);
    }

    @Override
    public void updateUI(ArrayList<TeamObject> objArrayList) {
        teamArraylist.addAll(objArrayList);
        progressBar.setVisibility(View.GONE);
        teamListAdapter = new TeamListAdapter(this, objArrayList);
        recyclerView.setAdapter(teamListAdapter);
        teamListAdapter.notifyItemInserted(teamArraylist.size() - objArrayList.size() - 1);

        teamListAdapter.setOnItemClickListener(new TeamListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                TeamObject objectToPass = teamArraylist.get(position);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("theObject", objectToPass);
                startActivity(intent);
            }
        });

    }
}
