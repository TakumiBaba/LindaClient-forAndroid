package com.takumibaba.lindaclient;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;

/**
 * Created by s09704tb on 2013/07/04.
 */
public class WebSocketIO extends WebSocketClient {
    private Linda linda;
    private String session;
    public WebSocketIO(URI serverURI, Linda linda) {
        super(serverURI);
        this.linda = linda;
    }

    public void push(String type, JSONArray data){
        JSONObject json = new JSONObject();
        try {
            json.put("type", type);
            json.put("data", data);
            json.put("session", session);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.send(json.toString());
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        Log.d("IO", "connection open");
    }

    @Override
    public void onMessage(String s) {
        Log.d("IO-onMessage", s);
        JSONObject json = null;
        String type = "";
        try {
            json = new JSONObject(s);
            type = json.getString("type");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("IO-onMessage", json.toString());
        if(type.equals("__session_id")){
            try {
                session = json.getString("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            linda.callback.connect(json);
        }else if(type.matches("__linda_write_callback.*")){
            linda.callback.write(json);
        }else if(type.matches("__linda_watch_callback.*")){
            linda.callback.watch(json);
        }else if(type.matches("__linda_read_callback.*")){
            linda.callback.read(json);
        }else if(type.matches("__linda_take_callback.*")){
            linda.callback.take(json);
        }

    }

    @Override
    public void onClose(int i, String s, boolean b) {
        Log.d("IO-onClose", s);
        linda.reConnect();
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }
}
