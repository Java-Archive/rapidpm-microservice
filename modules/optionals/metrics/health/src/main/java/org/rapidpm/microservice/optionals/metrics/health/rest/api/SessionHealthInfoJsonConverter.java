package org.rapidpm.microservice.optionals.metrics.health.rest.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Sven Ruppert on 10.11.15.
 */
public class SessionHealthInfoJsonConverter {

    private static final Type LIST_TYPE = new TypeToken<List<SessionHealthInfo>>() {
    }.getType();

    public List<SessionHealthInfo> fromJsonList(String json) {
        return new Gson().fromJson(json, LIST_TYPE);
    }

    public SessionHealthInfo fromJsonElement(String json) {
        return new Gson().fromJson(json, SessionHealthInfo.class);
    }


    public String toJson(List<SessionHealthInfo> sessionHealthInfos) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(sessionHealthInfos, LIST_TYPE);
    }

    public String toJson(SessionHealthInfo sessionHealthInfos) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(sessionHealthInfos, SessionHealthInfo.class);
    }

}
