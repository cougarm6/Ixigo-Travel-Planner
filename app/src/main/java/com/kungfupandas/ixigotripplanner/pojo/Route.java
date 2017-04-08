package com.kungfupandas.ixigotripplanner.pojo;

/**
 * Created by tushar on 08/04/17.
 */

public class Route {
    private String price;
    private String durationPretty;
    private String allStepModes;
    private String modeViaString;

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
}
