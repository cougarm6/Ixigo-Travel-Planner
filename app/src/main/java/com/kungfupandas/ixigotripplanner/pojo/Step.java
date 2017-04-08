package com.kungfupandas.ixigotripplanner.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tushar on 09/04/17.
 */
public class Step implements Parcelable {
    private String origin, destination, modePretty, fastestCarrier, minPrice, fastestCarrierDuration, carrierName;



    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getModePretty() {
        return modePretty;
    }

    public void setModePretty(String modePretty) {
        this.modePretty = modePretty;
    }

    public String getFastestCarrier() {
        return fastestCarrier;
    }

    public void setFastestCarrier(String fastestCarrier) {
        this.fastestCarrier = fastestCarrier;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getFastestCarrierDuration() {
        return fastestCarrierDuration;
    }

    public void setFastestCarrierDuration(String fastestCarrierDuration) {
        this.fastestCarrierDuration = fastestCarrierDuration;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.origin);
        dest.writeString(this.destination);
        dest.writeString(this.modePretty);
        dest.writeString(this.fastestCarrier);
        dest.writeString(this.minPrice);
        dest.writeString(this.fastestCarrierDuration);
        dest.writeString(this.carrierName);
    }

    public Step() {
    }

    protected Step(Parcel in) {
        this.origin = in.readString();
        this.destination = in.readString();
        this.modePretty = in.readString();
        this.fastestCarrier = in.readString();
        this.minPrice = in.readString();
        this.fastestCarrierDuration = in.readString();
        this.carrierName = in.readString();
    }

    public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel source) {
            return new Step(source);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };
}
