package br.com.caelum.cadastro;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class MostraAlunosProximosActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.map_layout);
		
		MapaFragment mapaFragment = new MapaFragment();
		
		FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
		tx.replace(R.id.mapa, mapaFragment);
		tx.commit();
	}

}
