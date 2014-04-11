package br.com.caelum.cadastro;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

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
	
	private SortedSet<Double> latitudes = new TreeSet<Double>();
	private SortedSet<Double> longitudes = new TreeSet<Double>();
	
	@Override
	public void onResume() {
		super.onResume();
		
		AlunoDAO dao = new AlunoDAO(getActivity());
		List<Aluno> alunos = dao.getLista();
		for (Aluno aluno : alunos) {
			LatLng coordenada = new Localizador(getActivity()).getCoordenada(aluno.getEndereco());
			if(coordenada==null) continue;
			
			latitudes.add(coordenada.latitude);
			longitudes.add(coordenada.longitude);
			
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
		
		double latitude = (latitudes.first() + latitudes.last()) / 2;
		double longitude = (longitudes.first() + longitudes.last()) / 2;
		
		double diferencaLatitude = latitudes.last() - latitudes.first();
		double diferencaLongitude = longitudes.last() -longitudes.first();

		float zoomLatitude = (float) (22*diferencaLatitude) / 18.0f;
		Log.i("mapa", String.valueOf(zoomLatitude));
		
		getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), zoomLatitude));
	}
	
	private BitmapDescriptor getAlunoBitMapDescription(Aluno aluno) {
		Bitmap image = ImageUtil.decodeSampledBitmapFromFilePath(aluno.getCaminhoFoto(), 100, 100);
		return BitmapDescriptorFactory.fromBitmap(image);
	}

}
