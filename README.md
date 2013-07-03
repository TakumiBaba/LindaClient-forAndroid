LindaClient for android
===

[Sinatra::RocketIO::Linda](https://github.com/shokai/sinatra-rocketio-linda) Client for android


requirement
===
java_websocket (https://github.com/TooTallNate/Java-WebSocket)

example
===
```java
public class BabaScript implements LindaCallback {

    private Linda linda;
    private Linda.TupleSpace ts;

    public BabaScript(){
        linda = new Linda();
        ts = linda.ts;
        linda.callback = this;
        linda.connect();
    }

    @Override
    public void connect(JSONObject json) {
        JSONArray tuple = new JSONArray();
        tuple.put("babascript");
        ts.watch(tuple);
    }

    @Override
    public void watch(JSONObject json) {

    }

    @Override
    public void read(JSONObject json) {

    }

    @Override
    public void write(JSONObject json) {

    }

    @Override
    public void take(JSONObject json) {

    }
}
