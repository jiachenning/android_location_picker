package com.zeonic.icity.location_picker.entity;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by ninja on 5/10/17.
 */
public class Location implements Serializable {
    long id;
    String competition_id;
    double time;
    double lat;
    double lng;
    String remark;
    int level;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCompetition_id() {
        return competition_id;
    }

    public void setCompetition_id(String competition_id) {
        this.competition_id = competition_id;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return ((Long) getId()).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof  Location){
            if(getId() == ((Location)obj).getId()){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
