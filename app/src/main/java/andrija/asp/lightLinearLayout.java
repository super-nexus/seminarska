package andrija.asp;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Andrija on 2/20/18.
 */

public class lightLinearLayout extends LinearLayout {

    private String name, mac_address;
    private int state, id;
    private JSONObject light;

    public lightLinearLayout(Context context, JSONObject light) {
        super(context);

        try {
            name = light.getString("name");
            mac_address = light.getString("mac_address");
            state = light.getInt("state");
            id = light.getInt("id");
            this.light = light;
        }catch (JSONException e){
            Log.i(AppConstants.getTAG(), "Failed to set light values: " + e.toString());
        }
    }

    public String getName() {
        return name;
    }

    public String getMac_address() {
        return mac_address;
    }

    public int getState() {
        return state;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getLightJSONString()throws  JSONException{
        JSONObject object = new JSONObject();
        object.put("mac_address", mac_address);
        object.put("id", id);
        return object.toString();
    }
}
