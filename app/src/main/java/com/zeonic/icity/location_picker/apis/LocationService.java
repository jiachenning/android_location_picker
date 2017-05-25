package com.zeonic.icity.location_picker.apis;

import com.zeonic.icity.location_picker.entity.ZeonicMarkersResponse;
import com.zeonic.icity.location_picker.entity.ZeonicResponse;

import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by ninja on 5/10/17.
 */

public interface LocationService {
    public static final String endpoint = "https://www.wakfo.com:9998";
    @FormUrlEncoded
    @POST("/Competitions/LocationPicker/{identification}")
    ZeonicResponse locationPicker(@Path("identification") String identification,
                                  @Field("lat") Double lat,
                                  @Field("lng") Double lng,
                                  @Field("remark") String remark,
                                  @Field("level") int levelNum,
                                  @Field("competition_id") int competitionId);

    @GET("/Competitions/LocationPicker/{identification}")
    ZeonicMarkersResponse queryMarkers(@Path("identification") String identification);

    @FormUrlEncoded
    @DELETE("/Competitions/LocationPicker/{identification}")
    ZeonicMarkersResponse deleteMarkers(@Path("identification")String identification, @Field("id")long id);
}
