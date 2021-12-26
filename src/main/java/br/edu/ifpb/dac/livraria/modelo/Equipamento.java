package br.edu.ifpb.dac.livraria.modelo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import br.edu.ifpb.dac.livraria.modelo.enums.TipoEquipamento;

@Entity
@DiscriminatorValue("E")
public class Equipamento extends Produto{
	
	private TipoEquipamento tipoEquipamento;
	
	public Equipamento() {
		// TODO Auto-generated constructor stub
	}

	public TipoEquipamento getTipoEquipamento() {
		return tipoEquipamento;
	}

	public void setTipoEquipamento(TipoEquipamento tipoEquipamento) {
		this.tipoEquipamento = tipoEquipamento;
	}
	
}