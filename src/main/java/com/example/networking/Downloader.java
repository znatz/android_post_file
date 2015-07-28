package com.example.networking;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Downloader implements Runnable {
    /* Location of downloaded file is

    "c:\Program Files (x86)\Android\android-sdk\platform-tools\adb.exe" pull /data/data/com.example.testSqlite/files/Master.sqlite

    or confirm with "adb.exe shell"

    * */
    public Downloader(Activity activity) {
        try {
            URL url = new URL("http://posco-cloud.sakura.ne.jp/TEST/IOS/OrderSystem/app/database/Master.sqlite");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            FileOutputStream fileOutputStream = activity.getApplicationContext().openFileOutput("Master.sqlite", Context.MODE_PRIVATE);
            Log.i("FILE", "HI");
            byte data[] = new byte[1024];
            int count;
            while ((count = inputStream.read(data)) != -1 ) {
                fileOutputStream.write(data,0,count);
            }
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void run() {
    }
}
