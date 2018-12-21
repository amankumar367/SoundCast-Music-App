package com.dev.aman.soundcast;

import com.parse.Parse;

public class Application extends android.app.Application {

    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("VSPdpDKRMND382hqIRFIaiVLgbkhM0E1rL32l1SQ")
                .clientKey("NnOwo2ejzrpQJD98uF9weupAo2AFH305DCOLVaBQ")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
