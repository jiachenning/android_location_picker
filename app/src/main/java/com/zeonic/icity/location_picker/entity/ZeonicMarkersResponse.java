package com.zeonic.icity.location_picker.entity;

import java.io.Serializable;

/**
 * Created by IrrElephant on 17/5/12.
 */

public class ZeonicMarkersResponse implements Serializable {
    int status;
    ZeonicMarkerResult result;
    String description;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ZeonicMarkerResult getResult() {
        return result;
    }

    public void setResult(ZeonicMarkerResult result) {
        this.result = result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
