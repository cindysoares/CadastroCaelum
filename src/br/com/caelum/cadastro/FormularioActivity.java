package br.com.caelum.cadastro;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class FormularioActivity extends Activity {
	
	Button botaoInserir;
	Aluno aluno;
	Estado estadoAtual;
	
	enum Estado {INSERIR, ALTERAR};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formulario);
		
		botaoInserir = (Button) findViewById(R.id.botaoInserir);
		aluno = (Aluno) getIntent().getSerializableExtra("alunoSelecionado");
		if(aluno == null) {
			estadoAtual = Estado.INSERIR;
		} else {
			estadoAtual = Estado.ALTERAR;
			botaoInserir.setText(R.string.alterar);
			new FormularioHelper(this).colocaNoFormulario(aluno);
		}
		
		configurarBotaoInserir();
		
	}

	private void configurarBotaoInserir() {
		botaoInserir.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				inserirAluno();
			}

		});
	}

	private void inserirAluno() {		
		Aluno novoAluno = new FormularioHelper(this).pegaAlunoDoFormulario();
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
	
}
