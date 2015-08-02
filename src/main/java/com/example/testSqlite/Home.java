package com.example.testSqlite;

/*
* @see http://www.techotopia.com/index.php/An_Android_SQLite_Database_Tutorial
*/

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import com.example.db.DBHelper;
import com.example.model.Person;
import com.example.model.ReceiptLine;
import com.example.networking.Downloader;
import com.example.networking.Uploader;

public class Home extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        this.deleteDatabase("person.db");
        DBHelper dbHelper = new DBHelper(this, null, null, 1);
        dbHelper.addPerson(new Person("ZNATZ", 30));
        dbHelper.addReceiptLine(new ReceiptLine("1", "伊倉ヶ浜", "7", "20150803", "4", "2"));

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

        Uploader uploader = new Uploader(this, dbHelper);
        new Thread(uploader).run();

        Downloader downloader = new Downloader(this);
        new Thread(downloader).run();

    }

}
