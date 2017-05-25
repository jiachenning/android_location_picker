package com.zeonic.icity.location_picker.apis;

import com.zeonic.icity.location_picker.ZeonicUtils;
import com.zeonic.icity.location_picker.entity.Location;
import com.zeonic.icity.location_picker.entity.ZeonicMarkersResponse;
import com.zeonic.icity.location_picker.entity.ZeonicResponse;

import retrofit.RestAdapter;

/**
 * Created by ninja on 5/10/17.
 */

public class BootstrapService {
    public BootstrapService(){}

    private LocationService getZeonicService() {
        return new RestAdapter.Builder()
                .setEndpoint(LocationService.endpoint)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build().create(LocationService.class);
    }

    public ZeonicResponse locationPicker(Location location) {
        int competitionId = 1;
        return getZeonicService().locationPicker(ZeonicUtils.getIdentification(),
                location.getLat(),
                location.getLng(),
                location.getRemark(),
                location.getLevel(),
                competitionId);
    }

    public ZeonicMarkersResponse queryMarkers() {
        return getZeonicService().queryMarkers(ZeonicUtils.getIdentification());
    }
    public ZeonicMarkersResponse deleteMarkers(long id) {
        return getZeonicService().deleteMarkers(ZeonicUtils.getIdentification(), id);
    }
}
