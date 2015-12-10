package com.ismonnet.openjm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationActivity extends Activity {
	private EditText txtUsernameReg;
	private EditText txtPasswordReg;
	private EditText txtNomeReg;
	private EditText txtCognomeReg;
	private EditText txtEmailReg;
	private Button btnConfermaReg;
	
	private DatiCondivisi dc;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		
		txtUsernameReg = (EditText) findViewById(R.id.txtUsernameReg);
		txtPasswordReg = (EditText) findViewById(R.id.txtPasswordReg);
		txtNomeReg = (EditText) findViewById(R.id.txtNomeReg);
		txtCognomeReg = (EditText) findViewById(R.id.txtCognomeReg);
		txtEmailReg = (EditText) findViewById(R.id.txtEmailReg);
		
		btnConfermaReg = (Button) findViewById(R.id.btnConfermaReg);
		
		// ottengo i dati condivisi attraverso l'uso di Parcelable
		//dc = getIntent().getExtras().getParcelable("DatiCondivisi");
		
		Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        dc = (DatiCondivisi)bundle.getParcelable("DatiCondivisi");
        
		btnConfermaReg.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!txtUsernameReg.getText().toString().isEmpty() 
						&& !txtPasswordReg.getText().toString().isEmpty()
						&& !txtNomeReg.getText().toString().isEmpty()
						&& !txtCognomeReg.getText().toString().isEmpty()
						&& !txtEmailReg.getText().toString().isEmpty()) {
							
					// creo un utente
					Utente u = new Utente();
					u.setUser(txtUsernameReg.getText().toString());
					u.setPass(txtPasswordReg.getText().toString());
					u.setUser(txtNomeReg.getText().toString());
					u.setPass(txtCognomeReg.getText().toString());	
					u.setUser(txtEmailReg.getText().toString());
					
					// lo salvo in dati condivisi
					dc.setUtente(u);
					dc.setScelta("R");
					
					ThreadSocket ts = new ThreadSocket(dc);
					ts.start();
					try {
						ts.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
						
					/*if (dc.isAutenticato()) {
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
				}*/
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration, menu);
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
