package br.com.caelum.cadastro;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListaAlunosActivity extends Activity {
	
	private ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listagem_alunos);         
        
		listView = (ListView) findViewById(R.id.lista_alunos);
		
		final List<Aluno> alunos = carregarAlunos(listView);
        configurarListView(alunos);
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	carregarAlunos(listView);
    }

	private List<Aluno> buscarAlunos() {
		List<Aluno> alunos = new AlunoDAO(this).getLista();
		return alunos;
	}

	private void configurarListView(final List<Aluno> alunos) {        
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        
        listView.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> adapter, View view, int index, long id) {
        		Toast toast = Toast.makeText(ListaAlunosActivity.this, 
        				"Posição selecionada: " + String.valueOf(index+1), 
        				Toast.LENGTH_SHORT);
        		toast.show();
        	}
        });
        
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {
        	@Override
        	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int index, long arg3) {
        		Toast toast = Toast.makeText(ListaAlunosActivity.this, 
        				alunos.get(index).getNome(), 
        				Toast.LENGTH_LONG);
        		toast.show();  
        		return false;
        	}
        });
	}

	private List<Aluno> carregarAlunos(ListView listView) {
		final List<Aluno> alunos = buscarAlunos();
        listView.setAdapter(new ArrayAdapter<Aluno>(
        		this, android.R.layout.simple_list_item_1, alunos));
		return alunos;
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	boolean retorno = super.onCreateOptionsMenu(menu);
    	getMenuInflater().inflate(R.menu.lista_alunos, menu);
    	return retorno;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
		case R.id.menuNovo:
			startActivity(new Intent(this, FormularioActivity.class));
			return false;
		default:
			return super.onOptionsItemSelected(item);
		}
    }

    
}
