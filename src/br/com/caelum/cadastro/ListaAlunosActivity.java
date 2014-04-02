package br.com.caelum.cadastro;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listagem_alunos);
        
        final String[] alunos = {"Anderson", "Filipe", "Guilherme"};
        
        ListView listView = (ListView) findViewById(R.id.lista_alunos);
        listView.setAdapter(new ArrayAdapter<String>(
        		this, android.R.layout.simple_list_item_checked, alunos));
        
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
        				alunos[index], 
        				Toast.LENGTH_LONG);
        		toast.show();  
        		return false;
        	}
        });
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
