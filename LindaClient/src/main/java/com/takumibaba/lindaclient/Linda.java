package com.takumibaba.lindaclient;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;

/**
 * Created by s09704tb on 2013/07/04.
 */
public class Linda {
    private String url = "ws://linda.masuilab.org:10010";
    public WebSocketIO io;
    public TupleSpace ts;
    public LindaCallback callback;

    public Linda(){
        io = new WebSocketIO(URI.create(url), this);
        ts = new TupleSpace("takumibaba");
    }

    public void connect(){
        io.connect();
    }

    public void reConnect(){
        io = new WebSocketIO(URI.create(url), this);
        io.connect();
    }

    public class TupleSpace{
        public String spacename;

        public TupleSpace(String spacename){
            this.spacename = spacename;
        }

        public String makeCallbackId(){
            return String.format("%d_%s", System.currentTimeMillis(), Math.floor(Math.random() * 1000000));
        }

        public void write(JSONArray tuple, JSONArray opts){
            Log.d("TupleSpace", tuple.getClass().toString());
            if(tuple == null || tuple.getClass().toString().equals("class org.json.JSONArray") != true) return;
            if(opts == null || opts.getClass().toString().equals("class org.json.JSONArray") != true) return;
            JSONArray attributes = new JSONArray();
            attributes.put(this.spacename);
            attributes.put(tuple);
            attributes.put(opts);
            Log.d("TupleSpace", attributes.toString());
            Log.d("TupleSpace", this.spacename);
            io.push("__linda_write", attributes);
        }
        public void read(JSONArray tuple, LindaCallback callback){
            if(tuple == null || tuple.getClass().toString().equals("class org.json.JSONArray") != true) return;
            if(callback == null) return;
            String callback_id = this.makeCallbackId();
            JSONArray attributes = new JSONArray();
            attributes.put(this.spacename);
            attributes.put(tuple);
            attributes.put(callback_id);
            io.push("__linda_read", attributes);
        }
        public void take(JSONArray tuple){
            Log.d("LINDA", "TAKE");
            if(tuple == null || tuple.getClass().toString().equals("class org.json.JSONArray") != true) return;
//            if(callback == null) return;
            String callback_id = this.makeCallbackId();
            JSONArray attributes = new JSONArray();
            attributes.put(this.spacename);
            attributes.put(tuple);
            attributes.put(callback_id);
            io.push("__linda_take", attributes);
        }
        public void watch(JSONArray tuple){
            if(tuple == null || tuple.getClass().toString().equals("class org.json.JSONArray") != true) return;
            String callback_id = this.makeCallbackId();
            JSONArray attributes = new JSONArray();
            attributes.put(this.spacename);
            attributes.put(tuple);
            attributes.put(callback_id);
            io.push("__linda_watch", attributes);
        }
    }

}
