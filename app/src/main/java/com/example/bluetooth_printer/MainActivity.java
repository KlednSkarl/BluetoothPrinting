package com.example.bluetooth_printer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_BLUETOOTH_PERMISSIONS = 1;
    private static final String PERMISSION_BT_CONNECT = "android.permission.BLUETOOTH_CONNECT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_printSample = findViewById(R.id.btn_printSample);
        EditText ET_printername = findViewById(R.id.ET_printername);




        checkBluetoothPermission();
        btn_printSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String PrinterName = ET_printername.getText().toString().trim(); // this is the string value that has the bluetooth printer's name
                BluetoothPrinter.printBluetoothPrinter(PrinterName);
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Bluetooth printing success connection", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Bluetooth permission is required to print", Toast.LENGTH_SHORT).show();
            }
        }
    } // needed for the permissions




    private boolean checkBluetoothPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (checkSelfPermission(PERMISSION_BT_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{PERMISSION_BT_CONNECT}, REQUEST_BLUETOOTH_PERMISSIONS);
                return false; // not granted yet
            }
        }
        return true; // granted or not required
    }
}