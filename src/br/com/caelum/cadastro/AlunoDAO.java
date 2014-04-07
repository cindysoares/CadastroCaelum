package br.com.caelum.cadastro;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AlunoDAO extends SQLiteOpenHelper {

	private static final int VERSAO = 1;
	private static final String DATABASE = "CadastroCaelum";
	private static final String TABELA = "Aluno";

	public AlunoDAO(Context context) {
		super(context, DATABASE, null, VERSAO);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String ddl = "create table "+TABELA+" (id integer primary key, " +
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
		
		getWritableDatabase().insert(TABELA, null, values);
	}
	
	public List<Aluno> getLista() {
		List<Aluno> alunos = new ArrayList<Aluno>();
		Cursor c = getReadableDatabase().rawQuery("select * from "+TABELA+";", null);
		
		while (c.moveToNext()) {
			Aluno aluno = new Aluno();
			aluno.setId(c.getLong(c.getColumnIndex("id")));
			aluno.setNome(c.getString(c.getColumnIndex("nome")));
			aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
			aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
			aluno.setSite(c.getString(c.getColumnIndex("site")));
			aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
			aluno.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));
			alunos.add(aluno);
		}
		
		c.close();
		
		return alunos;
	}
	
	public void deletar(Aluno aluno) {
		String[] args = { aluno.getId().toString() };
		getWritableDatabase().delete(TABELA, "id=?", args);
	}

	public void alterar(Aluno aluno) {
		ContentValues values = new ContentValues();
		values.put("nome", aluno.getNome());
		values.put("telefone", aluno.getTelefone());
		values.put("endereco", aluno.getEndereco());
		values.put("site", aluno.getSite());
		values.put("nota", aluno.getNota());
		values.put("caminhoFoto", aluno.getCaminhoFoto());
		
		String[] args = { aluno.getId().toString() };
		getWritableDatabase().update(TABELA, values, "id=?", args);
	}
	
	public boolean isAluno(String telefone) {
		String[] param = {telefone};
		Cursor rawQuery = getReadableDatabase()
				.rawQuery("select telefone from " + TABELA + " where telefone= ? ", param);
		int total = rawQuery.getCount();
		rawQuery.close();
		return total > 0;
	}

}
