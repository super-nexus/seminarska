package andrija.asp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText password, username;
    Button logIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize text fields
        username = (EditText) findViewById(R.id.usernameText);
        password = (EditText) findViewById(R.id.passwordText);
        //Initialize button
        logIn = (Button) findViewById(R.id.logInButton);
        //Initialize constants

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               new setup().execute(AppConstants.getSetupPath(), "{}");
            }
        });

    }





    //Setting up first gridView

    public class setup extends AsyncTask<String, Integer, String>{


        @Override
        protected String doInBackground(String... strings) {

            String path = strings[0];
            String jsonToPost = strings[1];
            String urlString = AppConstants.getProtocol() + AppConstants.getIp() + path;
            String data = "";

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
                Log.i(AppConstants.getTAG(),"Error: " + e.toString());
                e.printStackTrace();
                return null;
            }
            Log.i(AppConstants.getTAG(),"Data recieved: " +  data);
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            intent.putExtra("json", s);
            startActivity(intent);

        }

    }


}
