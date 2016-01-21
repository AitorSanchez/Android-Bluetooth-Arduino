package com.example.bluetooth;

import java.io.IOException;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

	import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

	public class MainActivity extends Activity implements  OnItemClickListener{

		private ListView dispos;
		private BluetoothUtils bluetooth;
		TextView abc;
		
		
		
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
			 abc = (TextView)findViewById(R.id.textView1);
			bluetooth = new BluetoothUtils();
			
			//Obtenemos la lista de elementos
			dispos = (ListView) findViewById(R.id.list1);		
			//Obtenemos los nombres de los dispositivos
			//bluetooth vinculados
			if (bluetooth.is_connected()) {
				String[] nombres = bluetooth.getNames();
				int i;
				for (i = 0; i < nombres.length; i++) {
					if (nombres[i] == "hc06")break;
				}
				/*//Asignamos los nombres a la lista
				dispos.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nombres));
	
				//Leemos los "clicks" sobre los elementos de 
				//la lista
				dispos.setOnItemClickListener(this);*/
				
				try {
					if (bluetooth.connect(i)) {
						Toast.makeText(this, "Dispositivo conectado", Toast.LENGTH_SHORT).show();
						boolean cond = false;
						while (!cond){
							abc.setText("");
							String aux = bluetooth.read();
							abc.setText(aux);
							esperar(6000);
						}
					}
					else abc.setText("no se encuentra el dispositivo HC-06");

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
			else {
				abc.setText("Bluetooth desactivado");
			}
			
			
		}

		
		/**
		 * Cuando cerramos la app desconectamos
		 */
		protected void onPause() {
			super.onPause();
			bluetooth.disconnect();
		}
		
		
		
		/**
		 * Método que se ejecutará cuando se pulse sobre un elemento de la 
		 * lista. Index indicará el número del elemento pulsado.
		 */
		public void onItemClick(AdapterView<?> ag, View v, int index, long id) {
			//Conectamos con el elemento pulsado
			try {
				if (bluetooth.connect(index)) {
					Toast.makeText(this, "Dispositivo conectado", Toast.LENGTH_SHORT).show();
					boolean cond = false;
					while (!cond){
						abc.setText("");
						String aux = bluetooth.read();
						abc.setText(aux);
						esperar(6000);
					}
				}
				else Toast.makeText(this, "Error en la conexión", Toast.LENGTH_SHORT).show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		public void esperar(int milisegundos) {
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
				public void run() {
					//acciones despues de la espera
				}
			}, milisegundos);
		}

	}
