/**
 * Copyright © 2013 Sven Ruppert (sven.ruppert@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.rapidpm.microservice.optionals.metrics.health.rest.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

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
