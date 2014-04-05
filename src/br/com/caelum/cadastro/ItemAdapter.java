package br.com.caelum.cadastro;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ItemAdapter extends BaseAdapter {

	private List<Aluno> alunos;
	private Context context;
	
	public ItemAdapter(List<Aluno> alunos, Context context) {
		this.alunos = alunos;
	}
	
	@Override
	public int getCount() {
		return alunos.size();
	}

	@Override
	public Object getItem(int index) {
		return alunos.get(index);
	}

	@Override
	public long getItemId(int index) {
		return alunos.get(index).hashCode();
	}

	@Override
	public View getView(int index, View view, ViewGroup viewGroup) {
		
		Aluno aluno = (Aluno) getItem(index);
		
		//LayoutInflater.from(context).inflate(, root)
		
		return null;
	}

}
