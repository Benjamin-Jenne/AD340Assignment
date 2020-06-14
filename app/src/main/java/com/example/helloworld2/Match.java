package com.example.helloworld2;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Match implements Parcelable {
    public String name;
    public String imageUrl;
    public String lat;
    public String longitude;
    public String uid;
    public boolean liked;

    public Match() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public String getName(){
        return name;
    }
    public String getImageUrl(){
        return imageUrl;
    }
    public String getLat() { return lat; }
    public String getLongitude() { return longitude; }
    public boolean getLiked(){
        return liked;
    }
    public void setName(String n){
        name = n;
    }
    public void setImageUrl(String i){
        imageUrl = i;
    }
    public void setLiked(boolean l){
        liked = l;
    }

    public Match(Parcel in) {
        name = in.readString();
        liked = in.readByte() != 0;
    }

    public static final Creator<Match> CREATOR = new Creator<Match>() {
        @Override
        public Match createFromParcel(Parcel in) {
            return new Match(in);
        }

        @Override
        public Match[] newArray(int size) {
            return new Match[size];
        }
    };

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("name", name);
        result.put("liked", liked);

        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeByte((byte) (liked ? 1 : 0));
    }
}
