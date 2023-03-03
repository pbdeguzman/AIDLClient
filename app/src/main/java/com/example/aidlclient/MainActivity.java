package com.example.aidlclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.aidlserver.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {
    IMyAidlInterface iMyAidlInterface;
    private static final String TAG = "MainActivity";

    private final ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(iBinder);
            Log.d(TAG, "Remote config Service Connected!");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent("MyAIDLService");
        intent.setPackage("com.example.aidlserver");
        bindService(intent, mConnection, BIND_AUTO_CREATE);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(v-> {
            try {
                int color = iMyAidlInterface.getColor();
                button.setBackgroundColor(color);
            } catch (RemoteException | NullPointerException e) {
                e.printStackTrace();
            }
        });

        Button add = findViewById(R.id.add);
        add.setOnClickListener(v-> {
            try {
                int value = iMyAidlInterface.increment();
                add.setText(String.format("Click to Increment\n%s", value));
            } catch (RemoteException | NullPointerException e) {
                e.printStackTrace();
            }
        });

        Button message = findViewById(R.id.message);
        message.setOnClickListener(v-> {
            try {
                String value = iMyAidlInterface.getMessage();
                Toast.makeText(MainActivity.this, value, Toast.LENGTH_SHORT).show();

            } catch (RemoteException | NullPointerException e) {
                e.printStackTrace();
            }
        });
    }
}