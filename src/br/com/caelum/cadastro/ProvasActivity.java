package br.com.caelum.cadastro;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class ProvasActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.provas);
		
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		
		transaction.replace(R.id.provas_view, new ListaProvasFragment());
		
		transaction.commit();
	}
	
}
