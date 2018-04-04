package andrija.asp;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.json.JSONArray;

/**
 * Created by Andrija on 2/19/18.
 */

public class myLinearLayout extends LinearLayout {

    private JSONArray nodeMCU;

    public myLinearLayout(Context context, JSONArray nodeMCU) {
        super(context);

        this.nodeMCU = nodeMCU;
    }

    public JSONArray getNodeMCU() {
        return nodeMCU;
    }

    public void setNodeMCU(JSONArray nodeMCU) {
        this.nodeMCU = nodeMCU;
    }
}
