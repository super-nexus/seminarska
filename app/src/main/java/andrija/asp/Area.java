package andrija.asp;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Andrija on 2/19/18.
 */

public class Area {

    private JSONArray nodeMCUs;
    private String nameOfArea;

    public Area(JSONArray nodeMCUs, String nameOfArea){
        this.nameOfArea = nameOfArea;
        this.nodeMCUs = nodeMCUs;
    }

    public Area(String nameOfArea){
        this.nameOfArea = nameOfArea;
        nodeMCUs = new JSONArray();
    }

    public void addNodeMCU(JSONObject nodeMCU){

        nodeMCUs.put(nodeMCU);

    }

    public JSONArray getNodeMCUs() {
        return nodeMCUs;
    }

    public String toString(){
        return nameOfArea + ", with nodeMCU array: " + nodeMCUs.toString();
    }

    public String getNameOfArea() {
        return nameOfArea;
    }

    public void setNameOfArea(String nameOfArea) {
        this.nameOfArea = nameOfArea;
    }
}
