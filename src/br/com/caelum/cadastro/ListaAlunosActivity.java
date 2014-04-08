package br.com.caelum.cadastro;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import br.com.caelum.cadastro.task.EnviaContatosTask;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
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
	
	private static final String URL_SERVIDOR = "http://www.caelum.com.br/mobile";
	private ListView listView;
	private Aluno alunoSelecionado;
	
	private static final String TAG_ACTIVITY = "aluno_activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listagem_alunos);         
        
		listView = (ListView) findViewById(R.id.lista_alunos);
		
		final List<Aluno> alunos = carregarAlunos(listView);
        configurarListView(alunos);
        
        registerForContextMenu(listView);
    }
    
    @Override
    protected void onResume() {
    	Log.i("resume", "onREsume()");
    	super.onResume();
    	carregarAlunos(listView);
    }

	private List<Aluno> buscarAlunos() {
		AlunoDAO alunoDAO = new AlunoDAO(this);
		List<Aluno> alunos = alunoDAO.getLista();
		alunoDAO.close();
		return alunos;
	}

	private void configurarListView(final List<Aluno> alunos) {
        
        listView.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> adapter, View view, int index, long id) {
        		Intent edicao = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
        		edicao.putExtra("alunoSelecionado", (Aluno)listView.getItemAtPosition(index));
        		startActivity(edicao);
        	}
        });
        
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {
        	@Override
        	public boolean onItemLongClick(AdapterView<?> adapter, View view, int index, long id) {
        		alunoSelecionado = (Aluno) adapter.getItemAtPosition(index);
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
		case R.id.menuSincronizar:
			try {
				int alunosSincronizados = sincronizarAlunos();
				Toast.makeText(this, alunosSincronizados + " alunos sincronizados.", Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				Log.e(TAG_ACTIVITY, e.getMessage(), e);
				Toast.makeText(this, "Náo foi possível sincronizar os alunos", Toast.LENGTH_LONG).show();
			}
			return false;
		default:
			return super.onOptionsItemSelected(item);
		}
    }
    
    private int sincronizarAlunos() throws InterruptedException, ExecutionException, JSONException {
    	AlunoDAO dao = new AlunoDAO(this);
    	List<Aluno> alunos = dao.getLista();
    	dao.close();
    	
    	String json = new AlunoConverter().toJson(alunos);
    	Log.i(TAG_ACTIVITY, json);
    	
    	EnviaContatosTask task = new EnviaContatosTask(this);
    	task.execute();

 /*   	new AsyncTask<String, Void, Boolean> () {
    		@Override
			protected Boolean doInBackground(String... args) {
    			try {
    				DefaultHttpClient httpClient = new DefaultHttpClient();
    				HttpPost post = new HttpPost(URL_SERVIDOR);
    				post.setEntity(new StringEntity(args[0]));
    				post.setHeader("Accept", "application/json");
    				post.setHeader("Content-type", "application/json");

    				HttpResponse response = httpClient.execute(post);
    				String jsonDeResposta = EntityUtils.toString(response.getEntity());
    				
    				Log.i(TAG_ACTIVITY, args[0]);
    				
    				DefaultHttpClient httpClient = new DefaultHttpClient();

    				HttpPost post = new HttpPost(URL_SERVIDOR);
    				post.setEntity(new StringEntity(args[0]));

    				post.setHeader("Accept", "application/json");
    				post.setHeader("Content-type", "application/json");

    				HttpResponse response = httpClient.execute(post);
    				String jsonDeResposta = EntityUtils.toString(response.getEntity());


    				Log.i(TAG_ACTIVITY, jsonDeResposta);
    			} catch(Exception e) {
    				Log.e(TAG_ACTIVITY, e.getMessage(), e);
    				return false;
    			}
				return true;
			};
    	}.execute(json);*/
    	
    	Boolean executouComSucesso = true;
    	
    	if(!executouComSucesso) {
    		throw new ExecutionException(null);
    	}
    	
    	return alunos.size();
		
	}

	@Override
    public void onCreateContextMenu(ContextMenu menu, View v,
    		ContextMenuInfo menuInfo) {
    	super.onCreateContextMenu(menu, v, menuInfo);
    	getMenuInflater().inflate(R.menu.item_lista_aluno, menu);    	
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.deletar:
    		deletarAluno();
    		break;
    	case R.id.ligar:
    		startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + alunoSelecionado.getTelefone())));
    		break;
    	case R.id.navegarNoSite:    		
    		navegarNoSite();
    		break;
    	case R.id.enviarEmail:
    		break;
    	case R.id.enviarSMS:
    		enviarSMS();
    		break;
    	case R.id.acharNoMapa:
    		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?z=14&q="+alunoSelecionado.getEndereco())));
    		break;
    	}
 
    	return super.onContextItemSelected(item);    	
    }

	private void enviarSMS() {
		Intent sms = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + alunoSelecionado.getTelefone()));
		sms.putExtra("sms_body", "Mensagem: ");
		startActivity(sms);
	}

	private void deletarAluno() {
		AlunoDAO alunoDAO = new AlunoDAO(this);
		alunoDAO.deletar(alunoSelecionado);
		alunoDAO.close();
		Toast.makeText(this, R.string.alunoDeletado, Toast.LENGTH_LONG).show();
		carregarAlunos(listView);
	}
	
	private void navegarNoSite() {
		String siteAluno = alunoSelecionado.getSite();
		if(siteAluno == null || siteAluno.trim().isEmpty()) {
			Toast.makeText(this, "O aluno não tem site cadastrado.", Toast.LENGTH_LONG).show();
			return;
		}
		if(!siteAluno.startsWith("http")) {
			siteAluno = "http:" + siteAluno;
		}
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(siteAluno)));
	}
    
}
