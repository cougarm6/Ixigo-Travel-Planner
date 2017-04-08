package com.kungfupandas.ixigotripplanner.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tushar on 08/04/17.
 */

public class Trip implements Parcelable {
    private String originName;
    private String destinationName;
    private boolean noModesPossible;
    private boolean multiModes;
    private boolean directFlight;
    private boolean directBus;
    private boolean directCar;
    private boolean direct;
    private String directIndirectSentence;
    private String distance;
    private List<Route> routes;
    private Route fastestRoute;
    private Route cheapestRoute;

    public Route getFastestRoute() {
        return fastestRoute;
    }

    public void setFastestRoute(Route fastestRoute) {
        this.fastestRoute = fastestRoute;
    }

    public Route getCheapestRoute() {
        return cheapestRoute;
    }

    public void setCheapestRoute(Route cheapestRoute) {
        this.cheapestRoute = cheapestRoute;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public boolean isNoModesPossible() {
        return noModesPossible;
    }

    public void setNoModesPossible(boolean noModesPossible) {
        this.noModesPossible = noModesPossible;
    }

    public boolean isMultiModes() {
        return multiModes;
    }

    public void setMultiModes(boolean multiModes) {
        this.multiModes = multiModes;
    }

    public boolean isDirectFlight() {
        return directFlight;
    }

    public void setDirectFlight(boolean directFlight) {
        this.directFlight = directFlight;
    }

    public boolean isDirectBus() {
        return directBus;
    }

    public void setDirectBus(boolean directBus) {
        this.directBus = directBus;
    }

    public boolean isDirectCar() {
        return directCar;
    }

    public void setDirectCar(boolean directCar) {
        this.directCar = directCar;
    }

    public boolean isDirect() {
        return direct;
    }

    public void setDirect(boolean direct) {
        this.direct = direct;
    }

    public String getDirectIndirectSentence() {
        return directIndirectSentence;
    }

    public void setDirectIndirectSentence(String directIndirectSentence) {
        this.directIndirectSentence = directIndirectSentence;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.originName);
        dest.writeString(this.destinationName);
        dest.writeByte(this.noModesPossible ? (byte) 1 : (byte) 0);
        dest.writeByte(this.multiModes ? (byte) 1 : (byte) 0);
        dest.writeByte(this.directFlight ? (byte) 1 : (byte) 0);
        dest.writeByte(this.directBus ? (byte) 1 : (byte) 0);
        dest.writeByte(this.directCar ? (byte) 1 : (byte) 0);
        dest.writeByte(this.direct ? (byte) 1 : (byte) 0);
        dest.writeString(this.directIndirectSentence);
        dest.writeString(this.distance);
        dest.writeList(this.routes);
        dest.writeParcelable(this.fastestRoute, flags);
        dest.writeParcelable(this.cheapestRoute, flags);
    }

    public Trip() {
    }

    protected Trip(Parcel in) {
        this.originName = in.readString();
        this.destinationName = in.readString();
        this.noModesPossible = in.readByte() != 0;
        this.multiModes = in.readByte() != 0;
        this.directFlight = in.readByte() != 0;
        this.directBus = in.readByte() != 0;
        this.directCar = in.readByte() != 0;
        this.direct = in.readByte() != 0;
        this.directIndirectSentence = in.readString();
        this.distance = in.readString();
        this.routes = new ArrayList<Route>();
        in.readList(this.routes, Route.class.getClassLoader());
        this.fastestRoute = in.readParcelable(Route.class.getClassLoader());
        this.cheapestRoute = in.readParcelable(Route.class.getClassLoader());
    }

    public static final Parcelable.Creator<Trip> CREATOR = new Parcelable.Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel source) {
            return new Trip(source);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };
}
