package br.com.caelum.cadastro;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;

public class FormularioHelper {
	
	private EditText nome;
	private EditText telefone;
	private EditText endereco;
	private EditText site;
	private ImageView caminhoFoto;
	private SeekBar nota;
	
	public FormularioHelper(FormularioActivity activity) {
		nome = (EditText) activity.findViewById(R.id.textNome);
		telefone = (EditText) activity.findViewById(R.id.textTelefone);
		endereco = (EditText) activity.findViewById(R.id.textEndereco);
		site = (EditText) activity.findViewById(R.id.textSite);
		caminhoFoto = (ImageView) activity.findViewById(R.id.caminhoFoto);
		nota = (SeekBar) activity.findViewById(R.id.valorNota);
	}
	
	public Aluno pegaAlunoDoFormulario() {
		Aluno novoAluno = new Aluno();
		novoAluno.setNome(nome.getText().toString());
		novoAluno.setTelefone(telefone.getText().toString());
		novoAluno.setEndereco(endereco.getText().toString());
		novoAluno.setSite(site.getText().toString());
		//novoAluno.setCaminhoFoto();
		novoAluno.setNota(Integer.valueOf(nota.getProgress()).doubleValue());
		return novoAluno;
	}

}
