package br.com.caelum.cadastro;

import br.com.caelum.cadastro.model.Aluno;
import android.graphics.Bitmap;
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
	
	private Aluno aluno = new Aluno();;
	
	public FormularioHelper(FormularioActivity activity) {
		nome = (EditText) activity.findViewById(R.id.textNome);
		telefone = (EditText) activity.findViewById(R.id.textTelefone);
		endereco = (EditText) activity.findViewById(R.id.textEndereco);
		site = (EditText) activity.findViewById(R.id.textSite);
		caminhoFoto = (ImageView) activity.findViewById(R.id.caminhoFoto);
		nota = (SeekBar) activity.findViewById(R.id.valorNota);
	}
	
	public Aluno pegaAlunoDoFormulario() {
		aluno.setNome(nome.getText().toString());
		aluno.setTelefone(telefone.getText().toString());
		aluno.setEndereco(endereco.getText().toString());
		aluno.setSite(site.getText().toString());
		aluno.setNota(Integer.valueOf(nota.getProgress()).doubleValue());
		return aluno;
	}
	
	public void colocaNoFormulario(Aluno aluno) {
        nome.setText(aluno.getNome());
        telefone.setText(aluno.getTelefone());
        site.setText(aluno.getSite());
        nota.setProgress(Double.valueOf(aluno.getNota()).intValue());
        endereco.setText(aluno.getEndereco());
        this.aluno = aluno;
    }

	public void carregaFoto(String localFoto) {
		Bitmap bitmapImage = ImageUtil.decodeSampledBitmapFromFilePath(localFoto, 100, 100);
		aluno.setCaminhoFoto(localFoto);
		caminhoFoto.setImageBitmap(bitmapImage);
	}
	
}
