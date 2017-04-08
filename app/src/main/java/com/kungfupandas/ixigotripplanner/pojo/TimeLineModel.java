package com.kungfupandas.ixigotripplanner.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tushar on 08/04/17.
 */

public class TimeLineModel implements Parcelable {

    private String mMessage;
    private String mDate;

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mMessage);
        dest.writeString(this.mDate);
    }

    public TimeLineModel() {
    }

    protected TimeLineModel(Parcel in) {
        this.mMessage = in.readString();
        this.mDate = in.readString();
    }

    public static final Creator<TimeLineModel> CREATOR = new Creator<TimeLineModel>() {
        @Override
        public TimeLineModel createFromParcel(Parcel source) {
            return new TimeLineModel(source);
        }

        @Override
        public TimeLineModel[] newArray(int size) {
            return new TimeLineModel[size];
        }
    };
}