package com.example.testSqlite;

/*
* @see http://www.techotopia.com/index.php/An_Android_SQLite_Database_Tutorial
*/

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import com.example.db.DBHelper;
import com.example.model.Person;
import com.example.networking.NetManager;

public class Home extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        DBHelper dbHelper = new DBHelper(this, null, null, 1);
        dbHelper.addPerson(new Person("ZNATZ", 30));

/*
    To take a look at the generated file
    "C:\Program Files (x86)\Android\android-sdk\platform-tools\adb.exe" pull /data/data/com.example.testSqlite/databases/person.db
*/

        String path = this.getDatabasePath(dbHelper.getDatabaseName()).getAbsolutePath();
        Log.i("DB_PATH", path);

        /* Upload File */
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        /*
        String ssid = "1063F3F0E6A3C_G";
        String pass = "3y4k7658x3sp3";
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.SSID = "\"" + ssid + "\"";
        wifiConfiguration.preSharedKey = "\"" + pass + "\"";
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifiManager.addNetwork(wifiConfiguration);
        wifiManager.enableNetwork(wifiConfiguration.networkId, true);
        wifiManager.reconnect();
        */

        NetManager netManager = new NetManager(this, dbHelper);
        new Thread(netManager).run();
    }
}
