package br.com.caelum.cadastro;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AlunoDAO extends SQLiteOpenHelper {

	private static final int VERSAO = 1;
	private static final String DATABASE = "CadastroCaelum";

	public AlunoDAO(Context context) {
		super(context, DATABASE, null, VERSAO);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String ddl = "create table Aluno (id integer primary key, " +
				"nome text unique not null, telefone text, endereco text, " +
				"site text, nota real, caminhoFoto text);";		
		db.execSQL(ddl);		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "drop table if exists Aluno";
		db.execSQL(sql);
		onCreate(db);
	}
	
	public void insere(Aluno aluno) {
		ContentValues values = new ContentValues();
		values.put("nome", aluno.getNome());
		values.put("telefone", aluno.getTelefone());
		values.put("endereco", aluno.getEndereco());
		values.put("site", aluno.getSite());
		values.put("nota", aluno.getNota());
		values.put("caminhoFoto", aluno.getCaminhoFoto());
		
		getWritableDatabase().insert(Aluno.class.getSimpleName(), null, values);
	}

}
