package br.com.caelum.cadastro.activity.provas;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.model.Prova;

public class ProvasActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.provas);
		
		Log.i("provas_activity", "IsTablet? " + isTablet());
		
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

	public void selecionaProva(Prova prova) {
		Bundle argumentos = new Bundle();
		argumentos.putSerializable("prova", prova);
		
		DetalhesProvaFragment detalhesProva = new DetalhesProvaFragment();
		detalhesProva.setArguments(argumentos);
		
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				
		if(isTablet()) {
			transaction.replace(R.id.detalhe_provas_view, detalhesProva);
		} else {
			transaction.replace(R.id.provas_view, detalhesProva);
			transaction.addToBackStack(null);
		}
		
		transaction.commit();
	}
	
}
