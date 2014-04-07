package br.com.caelum.cadastro;

import java.util.List;

import org.json.JSONException;
import org.json.JSONStringer;

public class AlunoConverter {
	
	public String toJson(List<Aluno> alunos) throws JSONException {
		JSONStringer jsonStringer =  new JSONStringer();
		jsonStringer.object().key("list").array().object().key("alunos").array();
		
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
		
		
		return jsonStringer.toString();		
	}

}
