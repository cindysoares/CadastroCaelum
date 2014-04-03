package br.com.caelum.cadastro;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class FormularioActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formulario);
		
		configurarBotaoInserir();
		
	}

	private void configurarBotaoInserir() {
		Button botaoInserir = (Button) findViewById(R.id.botaoInserir);
		botaoInserir.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				inserirAluno();
			}

		});
	}

	private void inserirAluno() {
		Toast.makeText(this, getString(R.string.alunoIncluido), Toast.LENGTH_LONG).show();
		Aluno novoAluno = new FormularioHelper(this).pegaAlunoDoFormulario();
		AlunoDAO dao = new AlunoDAO(this);
		dao.insere(novoAluno);
		dao.close();
		finish();
	}

}
