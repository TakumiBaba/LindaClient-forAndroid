package com.takumibaba.lindaclient;

import org.json.JSONObject;

/**
 * Created by s09704tb on 2013/07/04.
 */
public interface LindaCallback {

    void connect(JSONObject json);
    void watch(JSONObject json);
    void read(JSONObject json);
    void write(JSONObject json);
    void take(JSONObject json);
}
