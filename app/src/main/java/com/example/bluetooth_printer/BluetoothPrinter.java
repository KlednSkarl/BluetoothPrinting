package com.example.bluetooth_printer;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class BluetoothPrinter {

    @SuppressLint("MissingPermission")
    public static void printBluetoothPrinter(String printerName)

    {

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Log.d(printerName+"", "Bluetooth is not enabled"); // checks if bluetooth is enabled
            return;
        }

        BluetoothDevice printerDevice = null;
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : pairedDevices) {
            if (device.getName().equalsIgnoreCase(printerName)) {
                printerDevice = device;
                break;
            }
        }

        if (printerDevice == null) {
            Log.e(printerName+"", "Printer not found or not paired");
            return;
        }

        final BluetoothDevice printerDeviceFinal = printerDevice;

        new Thread(() -> {
            try {
                UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
                BluetoothSocket socket = printerDeviceFinal.createRfcommSocketToServiceRecord(uuid);
                socket.connect();
                OutputStream outputStream = socket.getOutputStream();


                // === Start Printing ===

                outputStream.write(new byte[]{0x1B, 0x61, 0x01}); // CENTER
                outputStream.write(new byte[]{0x1B, 0x45, 0x01}); // Bold
                outputStream.write(("Hello World from printer " +printerName +"\n").getBytes());
                outputStream.flush();

                // Header
//                outputStream.write(new byte[]{0x1B, 0x61, 0x01}); // CENTER
//                outputStream.write(new byte[]{0x1B, 0x45, 0x01}); // Bold
//                outputStream.write((companyLine1 + " " + companyLine2 + " " + companyLine3 + "\n").getBytes());
//                outputStream.write(new byte[]{0x1B, 0x45, 0x00}); // Bold off
//                outputStream.write((companyAddress + "\n").getBytes());
//                outputStream.write(("Smart: " + companySmart + " | Globe: " + companyGlobe + " | Tel: " + companyTel + "\n").getBytes());
//                outputStream.write(("Email: " + companyEmail + "\n").getBytes());
//                outputStream.write(("Facebook: " + companyFacebook + "\n").getBytes());
//
//                outputStream.write(new byte[]{0x1B, 0x45, 0x01});
//                outputStream.write("WATER BILL\n".getBytes());
//                outputStream.write(new byte[]{0x1B, 0x45, 0x00});
//
//                // Service Info
//                outputStream.write(new byte[]{0x1B, 0x61, 0x00}); // LEFT
//                outputStream.write(("Bill No.: " + billNo + "\n").getBytes());
//                outputStream.write(("Account Name: " + accountName + "\n").getBytes());
//                outputStream.write(("Address: " + address + "\n").getBytes());
//                outputStream.write(("Account Number: " + accountNumber + "\n").getBytes());
//                outputStream.write(("Meter Number: " + meterNumber + "\n").getBytes());
//                outputStream.write(("Type: " + type + "\n").getBytes());
//                outputStream.write(("Usage for Month Of " + usageMonth + "\n\n").getBytes());
//
//                // Readings
//                outputStream.write(("PREVIOUS: " + prevReading + " cubic meters\n").getBytes());
//                outputStream.write(("PRESENT: " + currReading + " cubic meters\n").getBytes());
//                outputStream.write(("CONSUMPTION: " + consumption + " cubic meters\n\n").getBytes());
//
//                // Billing
//                outputStream.write(("Bill Amount: " + billAmount + "\n").getBytes());
//                outputStream.write(("Previous Balance: " + prevBalance + "\n").getBytes());
//                outputStream.write(("Reconnection Fee: " + reconnectionFee + "\n").getBytes());
//                outputStream.write(("Violation Fee: " + violationFee + "\n").getBytes());
//                outputStream.write(("Others: " + othersFee + "\n\n").getBytes());
//                outputStream.write(("TOTAL DUE: " + totalDue + "\n").getBytes());
//                outputStream.write(("AFTER DUE DATE: " + totalDueWithPenalty + "\n\n").getBytes());
//
//                // Dates
//                outputStream.write(("Period From: " + periodFrom + "\n").getBytes());
//                outputStream.write(("Period To: " + periodTo + "\n").getBytes());
//                outputStream.write(("Due Date: " + dueDate + "\n").getBytes());
//                outputStream.write(("Disconnection Date: " + disconnectionDate + "\n\n").getBytes());
//
//                // Footer
//                outputStream.write(("Meter Reader: " + meterReader + "\n\n").getBytes());
//                outputStream.write("Please pay on time to avoid disconnection.\n".getBytes());
//                outputStream.write("Payments via GCash, Maya, or BDO Online will be posted within 5 days.\n".getBytes());
//                outputStream.write("For billing concerns, contact support.\n".getBytes());
//
//                // Cut
//                outputStream.write(new byte[]{0x1D, 0x56, 0x41, 0x10});
//
//                outputStream.flush();
//                outputStream.close();
//                socket.close();

            } catch (IOException e) {
                Log.e(printerName+"", "Error printing", e);
            }
        }).start();
    }
}

