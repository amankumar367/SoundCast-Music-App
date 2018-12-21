package com.dev.aman.soundcast.API;

import com.dev.aman.soundcast.model.Results;
import com.dev.aman.soundcast.model.SongList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface SongListEndPoints {

    @Headers({
            "X-Parse-Application-Id: VSPdpDKRMND382hqIRFIaiVLgbkhM0E1rL32l1SQ",
            "X-Parse-REST-API-Key: E4ZeObhQv3XoHaQ3Q6baHGgbDPOkuO9jPlY9gzgA"
    })
    @GET("/classes/songs_library")
    Call<SongList> getSongList();
}
