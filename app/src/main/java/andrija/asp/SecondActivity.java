package andrija.asp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;

import java.io.IOException;

public class SecondActivity extends AppCompatActivity {

    GridView gridView;
    String json;
    JSON j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Log.i(AppConstants.getTAG(), "onCreate");

        Intent intent = getIntent();
        json = intent.getStringExtra("json");

        if(json != null && !(json.equals(""))) {

            j = new JSON();

            gridView = (GridView) findViewById(R.id.gridView);
            gridView.setAdapter(new secondActivityGridAdapter(this, json));

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Intent lightSwitchIntent = new Intent(SecondActivity.this,  LightSwitch.class);

                    myLinearLayout layoutClicked;

                    try{
                        layoutClicked = (myLinearLayout) view;

                        JSONArray array = layoutClicked.getNodeMCU();

                        Log.i(AppConstants.getTAG(), array.toString());

                        lightSwitchIntent.putExtra("json", j.getLightsArrayFromNodes(array).toString());

                        startActivity(lightSwitchIntent);

                    }catch (Exception e){
                        Log.i(AppConstants.getTAG(), "Error: " + e.toString());
                    }

                }
            });

        }

        else{
            Log.i(AppConstants.getTAG(), "Error: JSON String is empty or null");
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(AppConstants.getTAG(), "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(AppConstants.getTAG(), "onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(AppConstants.getTAG(), "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(AppConstants.getTAG(), "onResume");
    }

    public class returnLightsForArea extends AsyncTask<String, Integer, String>{


        @Override
        protected String doInBackground(String... strings) {

            String path = strings[0];
            String json = strings[1];
            String url = AppConstants.getProtocol() + AppConstants.getIp() + path;

            try{
                return HttpPOST.httpPost(url, json);
            }catch (IOException error){
                Log.i(AppConstants.getTAG(), "Error in" + this.getClass().getSimpleName() + " : "  + error);
                error.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s != null){
                Intent intent = new Intent(SecondActivity.this, LightSwitch.class);
                intent.putExtra("json", s);
                startActivity(intent);
            }

        }
    }

}
