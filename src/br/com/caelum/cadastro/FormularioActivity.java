package br.com.caelum.cadastro;

import java.io.File;
import java.io.IOException;

import br.com.caelum.cadastro.model.Aluno;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class FormularioActivity extends Activity {
	
	private Aluno aluno;
	private Estado estadoAtual;
	
	private Button botaoInserir;
	private ImageView imageView;
	
	enum Estado {INSERIR, ALTERAR};
	private static final int TIRAR_FOTO = 123;
	
	private String localFoto;
	FormularioHelper helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formulario);
		
		helper = new FormularioHelper(this);
		
		botaoInserir = (Button) findViewById(R.id.botaoInserir);
		imageView = (ImageView) findViewById(R.id.caminhoFoto);
		
		configurarEstadoDoFormulario();		
		configurarListeners();
		
	}

	private void configurarEstadoDoFormulario() {
		aluno = (Aluno) getIntent().getSerializableExtra("alunoSelecionado");
		if(aluno == null) {
			estadoAtual = Estado.INSERIR;
		} else {
			estadoAtual = Estado.ALTERAR;
			botaoInserir.setText(R.string.alterar);
			new FormularioHelper(this).colocaNoFormulario(aluno);
		}
	}

	private void configurarListeners() {
		botaoInserir.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				inserirAluno();
			}

		});
		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				tirarFoto();
			}
		});
	}

	private void inserirAluno() {
		Aluno novoAluno = helper.pegaAlunoDoFormulario();
		AlunoDAO dao = new AlunoDAO(this);
		if(Estado.INSERIR.equals(estadoAtual)) {
			dao.insere(novoAluno);
			Toast.makeText(this, getString(R.string.alunoIncluido), Toast.LENGTH_LONG).show();
		} else {
			novoAluno.setId(aluno.getId());
			dao.alterar(novoAluno);
			Toast.makeText(this, getString(R.string.alunoAlterado), Toast.LENGTH_LONG).show();
		}
		
		dao.close();
		
		finish();
	}
	
	private void tirarFoto() {
//		localFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
		//localFoto = Environment.getExternalStorageDirectory() + "/cadastroCaelum/" + System.currentTimeMillis() + ".jpg";
		//localFoto = getFilesDir() + "/cadastroCaelum/" + System.currentTimeMillis() + ".jpg";
		localFoto = getApplicationContext().getFilesDir() + "/cadastroCaelum/" + System.currentTimeMillis() + ".jpg";
		Intent tirarFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		Log.i(null, "Local da foto:" + localFoto);
		File file = new File(localFoto);
		try {
			file.createNewFile();
		} catch (IOException e) {
			Log.e("formularioActivity", "Erro ao criar arquivo " + localFoto, e);
		}
		tirarFoto.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(localFoto)));
		startActivityForResult(tirarFoto, TIRAR_FOTO);		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.i(null, "requestCode = " + requestCode + ", resultCode = " + resultCode);
		if(requestCode == TIRAR_FOTO) {
			if(resultCode == RESULT_OK) {
				helper.carregaFoto(localFoto);
			} else {
				Log.e(null, "Erro ao tirar a foto");
			}
		}
	}
	
}
