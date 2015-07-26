package com.example.networking;
import android.app.Activity;
import android.util.Log;
import com.example.db.DBHelper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

// http://posco-cloud.sakura.ne.jp/TEST/IOS/OrderSystem/public/iosReceiver
public class NetManager implements Runnable {
    private String lineEnd = "\r\n";
    private String url_string = "http://posco-cloud.sakura.ne.jp/TEST/IOS/OrderSystem/public/iosReceiver2.php";

    public NetManager(Activity activity, DBHelper dbHelper) {

        File file = activity.getDatabasePath(dbHelper.getDatabaseName());
//        byte[] buffer = new byte[1024];

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url_string);

        /*
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("tanto", "1"));

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            // execute HTTP post request
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                String responseStr = EntityUtils.toString(resEntity).trim();
                Log.v("RESPONSE", "Response: " +  responseStr);
                // you can add an if statement here and do other actions based on the response
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */


        /* ----------- POST FILE -----------*/
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("tanto", "1"));

        FileBody fileBody = new FileBody(file);

        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        multipartEntityBuilder.setCharset(Charset.forName("UTF-8"));
        multipartEntityBuilder.addPart("file", fileBody);
        multipartEntityBuilder.addTextBody("tanto", "1");
        HttpEntity reqEntity = multipartEntityBuilder.build();

        httpPost.setEntity(reqEntity);

        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();

            if (resEntity != null) {

                String responseStr = EntityUtils.toString(resEntity).trim();
                Log.v("RESPONSE", "Response: " + responseStr);

                // you can add an if statement here and do other actions based on the response
            }
        } catch (Exception e) {
            Log.e("EXCEPTION", e.getMessage());
        }

    }

    public void run() {
    }
}
