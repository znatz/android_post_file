package com.example.networking;
import android.app.Activity;
import android.util.Log;
import com.example.db.DBHelper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

// http://posco-cloud.sakura.ne.jp/TEST/IOS/OrderSystem/public/iosReceiver
public class Uploader implements Runnable {
    private String lineEnd = "\r\n";
    private String url_string = "http://posco-cloud.sakura.ne.jp/TEST/IOS/OrderSystem/public/iosReceiver2";

    public Uploader(Activity activity, DBHelper dbHelper) {

        File file = activity.getDatabasePath(dbHelper.getDatabaseName());

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url_string);


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
