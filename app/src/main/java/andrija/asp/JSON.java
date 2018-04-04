package andrija.asp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Andrija on 2/19/18.
 */

public class JSON {


    public JSONArray getLightsArray(JSONObject nodeMCU) throws JSONException{

        return nodeMCU.getJSONArray("switches");

    }

    public JSONArray getLightsArrayFromNodes(JSONArray nodeMCUs) throws JSONException{

        JSONArray holder = new JSONArray();

        for(int i = 0; i < nodeMCUs.length(); i++){

            JSONObject currentNodeMCU = nodeMCUs.getJSONObject(i);

            JSONArray lights = currentNodeMCU.getJSONArray("switches");

            for(int m = 0; m < lights.length(); m++){

                JSONObject holdingObject = lights.getJSONObject(m);
                holdingObject.put("mac_address", currentNodeMCU.getString("mac_address"));
                holder.put(holdingObject);

            }

        }
        return holder;
    }

    public ArrayList<Area> getAreaOutOfJsonArray(JSONArray nodeMCUs) throws JSONException{

        Set<String> listOfAreaNameSet = new HashSet<>();
        ArrayList<Area> listOfAreas = new ArrayList<>();
        ArrayList<String> listOfAreaNames = new ArrayList<>();

        for(int i = 0; i < nodeMCUs.length(); i++){
            listOfAreaNameSet.add(nodeMCUs.getJSONObject(i).getString("area"));
        }

        listOfAreaNames.addAll(listOfAreaNameSet);

        for(int i = 0; i < listOfAreaNames.size(); i++){

            Log.i(AppConstants.getTAG(), "List of area names: " + listOfAreaNames.toString());

            String currentArea = listOfAreaNames.get(i);

            Area area = new Area(currentArea);

            for(int m = 0; m < nodeMCUs.length(); m++){

                JSONObject currentObject = nodeMCUs.getJSONObject(m);

                if(currentObject.getString("area").equals(currentArea)){
                    area.addNodeMCU(currentObject);
                }

            }

            Log.i(AppConstants.getTAG(), "Area: " + area.toString());

            listOfAreas.add(area);
        }


        return listOfAreas;

    }


}
