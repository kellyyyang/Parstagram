package com.codepath.parstagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Register your parse models
        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("fwzyKUF4ArKe7CRi1B5OdISOnjP3GlONaDs1zy7W")
                .clientKey("yBgE2CcPH7i8nEvtH1s7V8A6OyKcH7VoJ6gOtNlQ")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
