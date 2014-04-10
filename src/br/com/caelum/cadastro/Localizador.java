package br.com.caelum.cadastro;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class Localizador {
	
	private Geocoder geo;
	
	public Localizador(Context context) {
		geo = new Geocoder(context);
	}

	public LatLng getCoordenada(String endereco) {
		try {
			List<Address> enderecosEncontrados = geo.getFromLocationName(endereco, 1);
			if (enderecosEncontrados.isEmpty()) {
				return null;
			}
			Address address = enderecosEncontrados.get(0);
			return new LatLng(address.getLatitude(), address.getLongitude());
		} catch (IOException e) {
			Log.e("mapa", e.getMessage(), e);
		}
		
		return null;
	}
	
}
