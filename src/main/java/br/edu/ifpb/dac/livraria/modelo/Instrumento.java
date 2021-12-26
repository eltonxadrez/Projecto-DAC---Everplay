package br.edu.ifpb.dac.livraria.modelo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import br.edu.ifpb.dac.livraria.modelo.enums.TipoInstrumento;

@Entity
@DiscriminatorValue("I")
public class Instrumento extends Produto {	
	
	private TipoInstrumento tipoInstrumento;
	
	public Instrumento() {
		// TODO Auto-generated constructor stub
	}

	public TipoInstrumento getTipoInstrumento() {
		return tipoInstrumento;
	}

	public void setTipoInstrumento(TipoInstrumento tipoInstrumento) {
		this.tipoInstrumento = tipoInstrumento;
	}

}
