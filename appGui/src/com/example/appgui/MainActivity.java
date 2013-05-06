package com.example.appgui;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends Activity {
	private Handler handler;
	private Main link;
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash);
		handler = new Handler();
		link = new Main(this);
		link.start();
		
		Handler x = new Handler();
		x.postDelayed(new SplashHandler(), 2000);
	}
	class SplashHandler implements Runnable{
		public void run(){
			startActivity(new Intent(getApplication(), Main.class));
			MainActivity.this.finish();
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
		


		public void enableBT() {
			handler.post(new Runnable() {
				@Override
				public void run() {
					Intent enableBluetooth = new Intent(
							BluetoothAdapter.ACTION_REQUEST_ENABLE);
					startActivityForResult(enableBluetooth, 0);
				}
			});
		}

		public void feilmelding(String feil) {
			handler.post(new Feilmelder(feil));
		}

		/*
		 * Denne klassen får en feilmeldingsdialog til å poppe opp
		 * 
		 */
		class Feilmelder implements Runnable { 
			private String feil;

			public Feilmelder(String x) {
				this.feil = x;
			}

			@Override
			public void run() {
				new AlertDialog.Builder(MainActivity.this).setTitle("Argh")
						.setMessage(feil).setNeutralButton("Close", null).show();
			}
		}
	}

