package com.example.appgui;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.*;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.util.Log;

public class Main extends Thread {
	private MainActivity ref;
   private BluetoothAdapter mBluetoothAdapter;  //the local device for conecting and pairing with bluetooth
   private BluetoothDevice mmDevice; //a bluetooth device..
   private BluetoothSocket mmSocket; // socket used to maintain serialconnection
   private OutputStream mmOutputStream; //outputstream for bluetooth
   private InputStream mmInputStream; //inputstream for bluetooth
   
   
	public Main(MainActivity ref){
		this.ref = ref;
	}
	
	@Override
	public void run(){
	
       mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); //find the local bluetooth adapter
       
       
       //find bonded devices
       Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
       
       //check for devicename "Joaquin", our bluetooth module
       if(pairedDevices.size() > 0)
       {
           for(BluetoothDevice device : pairedDevices)
           {
               if(device.getName().equals("ArduinoRemote")) //Note, you will need to change this to match the name of your device
               {
                   mmDevice = device;
                   break;
               }//end if
           }//end for
       } // end pairedDevicing
       
       
       UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard SerialPortService ID
       //
       
       //try to setup connection
       try {
      BluetoothDevice actual = mBluetoothAdapter.getRemoteDevice(mmDevice.getAddress());
        
   mmSocket = actual.createInsecureRfcommSocketToServiceRecord(uuid);
   
   mBluetoothAdapter.cancelDiscovery();

   mmSocket.connect();

   mmOutputStream = mmSocket.getOutputStream();

   mmInputStream = mmSocket.getInputStream();
   
   mmOutputStream.write("kake!".getBytes());
     } catch (IOException e) {
      // TODO Auto-generated catch block

      
 }
	}
}

