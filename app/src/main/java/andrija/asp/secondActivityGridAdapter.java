package andrija.asp;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Andrija on 2/19/18.
 */

//This activity is supposed to setThe area "buttons"

public class secondActivityGridAdapter extends BaseAdapter {

    private ArrayList<Area> areaNames;
    private Context c;
    private JSON j;



    public secondActivityGridAdapter(Context c, String json){

        this.c = c;
        j = new JSON();

        try {
            areaNames = j.getAreaOutOfJsonArray(new JSONArray(json));
        }catch (JSONException e){
            Log.i(AppConstants.getTAG(), "Error in adapter constructor: " + e.toString());
        }
    }


    @Override
    public int getCount() {
        return areaNames.size();
    }

    @Override
    public Object getItem(int i) {
        return areaNames.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        try {
            Log.i(AppConstants.getTAG(), "Creating view, position: " + String.valueOf(i) + ", View :  " + view.toString());
        }catch (NullPointerException e){
            Log.i(AppConstants.getTAG(), "Creating view, position: " + String.valueOf(i) + ", View :  ");
        }
        myLinearLayout myLayout;


        if(view == null){

            Log.i(AppConstants.getTAG(),"Setting up this area: " + areaNames.get(i).toString());

            myLayout = new myLinearLayout(c, areaNames.get(i).getNodeMCUs());

            myLayout.setOrientation(LinearLayout.VERTICAL);


            ImageView image = new ImageView(c);

            image.setImageResource(R.drawable.light_of);
            //image.getLayoutParams().height = 20;
            //image.getLayoutParams().width = 20;

            TextView text = new TextView(c);

            text.setText(areaNames.get(i).getNameOfArea());
            text.setTextSize(16);

            myLayout.addView(image);
            myLayout.addView(text);

        }
        else{
            myLayout = (myLinearLayout) view;
        }

        return myLayout;
    }
}
