package br.com.caelum.cadastro.activity.provas;

import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.model.Prova;

public class ListaProvasFragment extends Fragment {
	
	private ListView listViewProvas;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View layoutProvas = inflater.inflate(R.layout.lista_provas, container, false);
		
		this.listViewProvas = (ListView) layoutProvas.findViewById(R.id.lista_provas);
		
		Prova prova1 = new Prova("08/04/2014", "Matematica");
		prova1.setTopicos(Arrays.asList("Algebra linear","Diferencial"));
		Prova prova2 = new Prova("09/04/2014", "Portugues");
		prova2.setTopicos(Arrays.asList("Complemento nominal", "Oracoes subordinadas"));
		
		List<Prova> provas = Arrays.asList(prova1, prova2);
		
		this.listViewProvas.setAdapter(new ArrayAdapter<Prova>(getActivity(), android.R.layout.simple_list_item_1, provas));
		this.listViewProvas.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int index, long id) {
				Prova provaSelecionada = (Prova) adapter.getItemAtPosition(index);
				
				ProvasActivity calendarioProvas = (ProvasActivity) getActivity();
				calendarioProvas.selecionaProva(provaSelecionada);				
			}
			
		});
		
		
		return layoutProvas;
	}
	
	
	
}
