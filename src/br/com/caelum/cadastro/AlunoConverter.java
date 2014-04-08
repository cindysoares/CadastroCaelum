package br.com.caelum.cadastro;

import java.util.List;

import org.json.JSONException;
import org.json.JSONStringer;

import br.com.caelum.cadastro.model.Aluno;

public class AlunoConverter {
	
	public String toJson(List<Aluno> alunos) {
		JSONStringer jsonStringer =  new JSONStringer();
		try {
			jsonStringer.object().key("list").array().object().key("aluno").array();


			for (Aluno aluno : alunos) {
				jsonStringer.object()
				.key("id").value(aluno.getId())
				.key("nome").value(aluno.getNome())
				.key("telefone").value(aluno.getTelefone())
				.key("endereco").value(aluno.getEndereco())
				.key("site").value(aluno.getSite())
				.key("nota").value(aluno.getNota()).endObject();
			}


			jsonStringer.endArray().endObject().endArray().endObject();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jsonStringer.toString();		
	}

}
