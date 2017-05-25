package com.zeonic.icity.location_picker.entity;

import java.io.Serializable;

/**
 * Entity
 * Created by ninja on 5/10/17.
 */

public class ZeonicResponse implements Serializable{
    int status;
    String description;
    Object result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
