package br.com.caelum.cadastro.task;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import br.com.caelum.cadastro.AlunoConverter;
import br.com.caelum.cadastro.AlunoDAO;
import br.com.caelum.cadastro.model.Aluno;
import br.com.caelum.cadastro.support.WebClient;

public class EnviaContatosTask extends AsyncTask<Object, Object, String> {

	private final Context context;
	private final String endereco = "http://www.caelum.com.br/mobile";

	private ProgressDialog progress;

	public EnviaContatosTask(Context context) {
		this.context = context;
	}

	protected void onPreExecute() {
		progress = ProgressDialog.show(context, "Aguarde...",
				"Envio de dados para a web", true, true);
	}

	protected String doInBackground(Object... params) {
		AlunoDAO dao = new AlunoDAO(context);
		List<Aluno> lista = dao.getLista();
		dao.close();
		String listaJSON = new AlunoConverter().toJson(lista);
		String jsonDeResposta = new WebClient(endereco).post(listaJSON);
		return jsonDeResposta;
	}

	protected void onPostExecute(String result) {
		progress.dismiss();
		Toast.makeText(context, result, Toast.LENGTH_LONG).show();
	}

}