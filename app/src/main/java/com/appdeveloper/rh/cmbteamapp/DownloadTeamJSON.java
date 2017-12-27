package com.appdeveloper.rh.cmbteamapp;

import android.content.res.AssetManager;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Roman on 12/26/2017.
 */

public class DownloadTeamJSON extends AsyncTask<Void, Integer, Void> {

    private ArrayList<TeamObject> teamArrayList = new ArrayList<>();
    private Communicator comm;
    AssetManager mngr;

    DownloadTeamJSON(MainActivity c) {
        this.comm = c;
        this.mngr = c.getBaseContext().getAssets();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String jsonString = loadJSONFromAsset();
        try {
            JSONArray data_arr = new JSONArray(jsonString);
            int totalTeamMembers = data_arr.length();

            for (int i = 0; i < totalTeamMembers; i++) {
                String firstName = data_arr.getJSONObject(i).getString("firstName");
                String lastName = data_arr.getJSONObject(i).getString("lastName");
                String title = data_arr.getJSONObject(i).getString("title");
                String bio = data_arr.getJSONObject(i).getString("bio");
                String avatar = data_arr.getJSONObject(i).getString("avatar");

                TeamObject obj = new TeamObject(firstName, lastName, title, bio, avatar);
                teamArrayList.add(obj);
                publishProgress((int) (((i + 1.0) / totalTeamMembers) * 100.0));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = mngr.open("team.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        comm.updateProgressTo(values[0]);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        comm.updateUI(teamArrayList);
    }

    interface Communicator {
        void updateProgressTo(int progress);

        void updateUI(ArrayList<TeamObject> objArrayList);
    }
}
