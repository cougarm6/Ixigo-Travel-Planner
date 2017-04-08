package com.kungfupandas.ixigotripplanner.pojo;

import java.util.List;

/**
 * Created by tushar on 08/04/17.
 */

public class Trip {
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
}
