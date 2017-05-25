package com.zeonic.icity.location_picker.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ninja on 5/12/17.
 */

public class ZeonicMarkerResult implements Serializable{
    List<Location> locationPickers;

    public List<Location> getLocationPickers() {
        return locationPickers;
    }

    public void setLocationPickers(List<Location> locationPickers) {
        this.locationPickers = locationPickers;
    }
}
