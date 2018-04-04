package andrija.asp;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Andrija on 2/25/18.
 */

public class HttpPOST {


    public static String httpPost(String urlString, String jsonToPost)throws IOException{

        String data = "";

        HttpURLConnection conn;

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

        if(data.equals("")){
            throw new IOException("data is \"\"");
        }
        else{
            return data;
        }

    }


}



