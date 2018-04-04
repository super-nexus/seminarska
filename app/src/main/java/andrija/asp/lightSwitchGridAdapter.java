package andrija.asp;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Andrija on 2/20/18.
 */

public class lightSwitchGridAdapter extends BaseAdapter {

    Context c;
    JSONArray lightsArray;

    public lightSwitchGridAdapter(Context c, JSONArray lightsArray){

        this.c = c;
        this.lightsArray = lightsArray;

    }

    @Override
    public int getCount() {
        return lightsArray.length();
    }

    @Override
    public Object getItem(int i) {
        try {
            return lightsArray.get(i);
        } catch (JSONException e) {
            Log.i(AppConstants.getTAG(), "returning null..... Error getting json item: " + e.toString());
        }

        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        lightLinearLayout myLayout = null;


        if(view == null){
            try {
                JSONObject currentLight = lightsArray.getJSONObject(i);
                myLayout = new lightLinearLayout(c, currentLight);

                myLayout.setOrientation(LinearLayout.VERTICAL);

                ImageView image = new ImageView(c);
                image.setImageResource(R.drawable.light_on);

                TextView text = new TextView(c);
                text.setText(currentLight.getString("name"));

                myLayout.addView(image);
                myLayout.addView(text);

            }catch (JSONException e){
                Log.i(AppConstants.getTAG(), "Unable to fetch json object for light linear layout: " + e.toString());
            }

        }
        else{
            myLayout = (lightLinearLayout) view;
        }


        return myLayout;
    }
}
