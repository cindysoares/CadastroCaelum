package br.com.caelum.cadastro.activity.provas;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import br.com.caelum.cadastro.R;

public class ProvasActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.provas);
		
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		
		transaction.replace(R.id.provas_view, new ListaProvasFragment());
		
		if(isTablet()) {
			transaction.replace(R.id.detalhe_provas_view, new DetalhesProvaFragment());
		}
		
		transaction.commit();
	}
	
	private boolean isTablet() {
		return getResources().getBoolean(R.bool.isTablet);
	}
	
}
