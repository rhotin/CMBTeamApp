package com.appdeveloper.rh.cmbteamapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Roman on 12/26/2017.
 */

public class TeamObject implements Parcelable {
    String firstName;
    String lastName;
    String title;
    String bio;
    String avatar;

    TeamObject(String firstName, String lastName, String title, String bio, String avatar) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.bio = bio;
        this.avatar = avatar;
    }

    private TeamObject(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        title = in.readString();
        bio = in.readString();
        avatar = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(title);
        dest.writeString(bio);
        dest.writeString(avatar);
    }

    public static final Creator<TeamObject> CREATOR = new Creator<TeamObject>() {
        @Override
        public TeamObject createFromParcel(Parcel in) {
            return new TeamObject(in);
        }

        @Override
        public TeamObject[] newArray(int size) {
            return new TeamObject[size];
        }
    };
}
