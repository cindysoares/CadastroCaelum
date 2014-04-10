package br.com.caelum.cadastro;

import java.util.List;

import android.graphics.Bitmap;
import android.util.Log;
import br.com.caelum.cadastro.model.Aluno;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaFragment extends SupportMapFragment {
	
	@Override
	public void onResume() {
		super.onResume();
		
		AlunoDAO dao = new AlunoDAO(getActivity());
		List<Aluno> alunos = dao.getLista();
		for (Aluno aluno : alunos) {
			LatLng coordenada = new Localizador(getActivity()).getCoordenada(aluno.getEndereco());
			if(coordenada==null) continue;
			MarkerOptions markerOptions = new MarkerOptions();
			markerOptions.title(aluno.getNome())
				.snippet(aluno.getEndereco())
				.position(coordenada);
			if(aluno.getCaminhoFoto() != null && !aluno.getCaminhoFoto().trim().isEmpty()) {
				markerOptions.icon(getAlunoBitMapDescription(aluno));
			}
				
			getMap().addMarker(markerOptions);
		}
		
		Localizador localizador = new Localizador(getActivity());
		LatLng local = localizador.getCoordenada("Rua do Ouvidor, 50, Centro, Rio de Janeiro, Brasil");
		
		Log.i("mapa", "Coordenada da Caelum: " + local);
		this.centraliza(local);
	}

	private void centraliza(LatLng local) {
		getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(local, 13));
	}
	
	private BitmapDescriptor getAlunoBitMapDescription(Aluno aluno) {
		Bitmap image = ImageUtil.decodeSampledBitmapFromFilePath(aluno.getCaminhoFoto(), 100, 100);
		return BitmapDescriptorFactory.fromBitmap(image);
	}

}
