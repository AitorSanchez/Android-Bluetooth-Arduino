package com.example.bluetooth;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.widget.Toast;

public class BluetoothUtils {
	private static final String UUID_CODE = "00001101-0000-1000-8000-00805F9B34FB";
	private static final String addres = "20:15:04:29:06:36";
	public ArrayList<BluetoothDevice> devices;
	public BluetoothAdapter adapter;
	public BluetoothDevice hc06;
	public BluetoothSocket socket;
	public boolean existe, connected;
	public OutputStream mOut;
	public InputStream mIn;
	private char DELIMITER = '#';
	
	public BluetoothUtils() {
		existe = false; connected = false;
		adapter = BluetoothAdapter.getDefaultAdapter();
		if (adapter == null) {
			LogError("\t\t[#]Phone does not support bluetooth!!");
			return;
		}
		if (!adapter.isEnabled()) {
			 LogError("[#]Bluetooth is not activated!!");
         }
		else {
			for (BluetoothDevice d : adapter.getBondedDevices())
				devices.add(d);
		}
		
	}
	
	public String[] getNames() {
		String names[] = new String[devices.size()];
		for (int i = 0; i < devices.size(); i++) {
			names[i] = devices.get(i).getName();
			if (names[i] == "HC-06") {
				//hc06 = devices.get(i);
				break;
			}
		}
		
		return names;
	}
	
	public boolean connect(int index) throws IOException {
		if (devices.get(index).getName() == "HC-06") {
			hc06 = adapter.getRemoteDevice(addres);
			try {
	            UUID uuid = UUID.fromString(UUID_CODE);
				socket = hc06.createRfcommSocketToServiceRecord(uuid);
				socket.connect();
				mOut = socket.getOutputStream();
	            mIn = socket.getInputStream();
	            //this.start();
	            //LogMessage("\t\t\t" + hc06.getName());
	            //LogMessage("\t\tOk!!");
	            connected = true;
	            return true;
			}catch (Exception e){
	            LogError("\t\t[#]Error while conecting: " + e.getMessage());
	            return false;
	        }
			
		}
		return false;
	}
	
	public String read(){

        while (true) {
            if(connected) {
                try {
                    byte ch, buffer[] = new byte[1024];
                    int i = 0;

                    String s = "";
                    while((ch=(byte)mIn.read()) != DELIMITER){
                        buffer[i++] = ch;
                    }
                    buffer[i] = '\0';

                    final String msg = new String(buffer);

                    /*MessageReceived(msg.trim());
                    LogMessage("[Blue]:" + msg);*/
                    return msg;

                } catch (IOException e) {
                    LogError("->[#]Failed to receive message: " + e.getMessage());
                }
            }
        }
    }
	
	public void disconnect() {
		
	}
	
	public boolean is_connected() {
		return connected;
	}
	
	public String recive() {
		return null;
	}
	
	private void LogError(String msg){
	      Log.e("Bluettoth", msg);
	    }
}
