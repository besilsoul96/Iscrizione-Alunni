package com.ismonnet.openjm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.ismonnet.openjm.R;

public class MainActivity extends Activity {

	private EditText txtUsername;
	private EditText txtPassword;
	private TextView lblResult;
	private Button btnAccedi;
	private Button btnCancella;
	private Button btnRegistrati;
	private Intent intentSecretActivity;
	private Intent intentRegistrationActivity;
			
	private DatiCondivisi dc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		txtUsername = (EditText) findViewById(R.id.txtUsername);
		txtPassword = (EditText) findViewById(R.id.txtPassword);
		
		lblResult = (TextView) findViewById(R.id.lblResult);
		
		btnAccedi = (Button) findViewById(R.id.btnAccedi);
		btnCancella = (Button) findViewById(R.id.btnCancella);
		btnRegistrati = (Button) findViewById(R.id.btnRegistrati);
		
		intentSecretActivity = new Intent(MainActivity.this, SecretActivity.class);
		intentRegistrationActivity = new Intent(MainActivity.this, RegistrationActivity.class);

		
		dc = new DatiCondivisi();
			
		// listener
		btnAccedi.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!txtUsername.getText().toString().isEmpty() 
						&& !txtPassword.getText().toString().isEmpty()) {
					// creo un utente
					Utente u = new Utente();
					u.setUser(txtUsername.getText().toString());
					u.setPass(txtPassword.getText().toString());	
					
					// lo salvo in dati condivisi
					dc.setUtente(u);
					dc.setScelta("A");
					
					ThreadSocket ts = new ThreadSocket(dc);
					ts.start();
					try {
						ts.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						
					if (dc.isAutenticato()) {
						lblResult.setText(R.string.benvenuto);
						
						// faccio partire la nuova activity
						startActivity(intentSecretActivity);
					}
					else {
						lblResult.setText(R.string.userpasserrati);
					}
				} // fine if input utente vuoto
				else if (!txtUsername.getText().toString().isEmpty()) {
					lblResult.setText(R.string.inserireUsername);
				}
				else if (!txtPassword.getText().toString().isEmpty()) {
					lblResult.setText(R.string.inserirePassword);
				}
			}
		}); // fine listener btnAccedi
		
		btnCancella.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				txtUsername.setText("");
				txtPassword.setText("");
				txtUsername.requestFocus();
			}
		});
		
		btnRegistrati.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// passo i dati condivisi attraverso l'uso di Parcelable
				//intentRegistrationActivity.putExtra("DatiCondivisi", dc);
				
				
				Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
				Bundle bundle = new Bundle();
				bundle.putParcelable("DatiCondivisi", dc);
				intent.putExtras(bundle);

				// faccio partire la nuova activity
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
