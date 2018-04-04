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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LightSwitch extends AppCompatActivity {

    GridView gridView;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_switch);

        intent = getIntent();

        JSONArray lights = null;

        try {
            lights = new JSONArray(intent.getStringExtra("json"));
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i(AppConstants.getTAG(), "Error parsing json array of lights: " + e.toString());
        }

        if(lights == null || lights.toString().equals("")){
            Log.i(AppConstants.getTAG(), "Error light json array is equal to null or \"\"");
        }
        else{

            gridView = (GridView) findViewById(R.id.gridView);
            gridView.setAdapter(new lightSwitchGridAdapter(this, lights));

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    try{
                        lightLinearLayout clickedLayout = (lightLinearLayout) view;

                        new postLightSwitch().execute(AppConstants.getLightSwitchPath(), clickedLayout.getLightJSONString());

                    }catch (Exception e){

                        Log.i(AppConstants.getTAG(), "Error before sending post request to switch light:" + e.toString());

                    }



                }
            });

        }

    }


    public class postLightSwitch extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... strings) {

            String path = strings[0];
            String jsonToPost = strings[1];
            String data = "";

            String urlString = AppConstants.getProtocol() + AppConstants.getIp() + path;

            HttpURLConnection conn;

            try{

                URL url = new URL(urlString);
                conn = (HttpURLConnection) url.openConnection();
                conn.addRequestProperty("Content-Type", "application/JSON");
                conn.setRequestMethod("POST");

                conn.setDoOutput(true);

                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(jsonToPost);
                wr.flush();
                wr.close();

                InputStream in = conn.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);

                int inputStreamData = inputStreamReader.read();
                while (inputStreamData != -1) {
                    char current = (char) inputStreamData;
                    inputStreamData = inputStreamReader.read();
                    data += current;
                }


            }catch (Exception e){

                Log.i(AppConstants.getTAG(), "Error while sending post to switch light: " + e.toString());

            }

            Log.i(AppConstants.getTAG(), "Data recieved from switching light : " + data);

            return data;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);



        }
    }
}
