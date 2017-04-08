package com.kungfupandas.ixigotripplanner.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by tushar on 08/04/17.
 */

public class Route implements Parcelable {
    private String price;
    private String durationPretty;
    private String allStepModes;
    private String modeViaString;
    private List<Step> steps;

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDurationPretty() {
        return durationPretty;
    }

    public void setDurationPretty(String durationPretty) {
        this.durationPretty = durationPretty;
    }

    public String getAllStepModes() {
        return allStepModes;
    }

    public void setAllStepModes(String allStepModes) {
        this.allStepModes = allStepModes;
    }

    public String getModeViaString() {
        return modeViaString;
    }

    public void setModeViaString(String modeViaString) {
        this.modeViaString = modeViaString;
    }


    public Route() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.price);
        dest.writeString(this.durationPretty);
        dest.writeString(this.allStepModes);
        dest.writeString(this.modeViaString);
        dest.writeTypedList(this.steps);
    }

    protected Route(Parcel in) {
        this.price = in.readString();
        this.durationPretty = in.readString();
        this.allStepModes = in.readString();
        this.modeViaString = in.readString();
        this.steps = in.createTypedArrayList(Step.CREATOR);
    }

    public static final Creator<Route> CREATOR = new Creator<Route>() {
        @Override
        public Route createFromParcel(Parcel source) {
            return new Route(source);
        }

        @Override
        public Route[] newArray(int size) {
            return new Route[size];
        }
    };
}
