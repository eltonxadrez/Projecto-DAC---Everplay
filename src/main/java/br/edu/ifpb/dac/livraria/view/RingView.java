package br.edu.ifpb.dac.livraria.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class RingView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<String> marcas;

	@PostConstruct
	public void init() {
		this.marcas = new ArrayList<String>();
		for (int i = 1; i < 6; i++) {
			marcas.add("marca" + i);
		}
		;
	}

	public List<String> getMarcas() {
		return marcas;
	}

	public void setMarcas(List<String> marcas) {
		this.marcas = marcas;
	}

}
