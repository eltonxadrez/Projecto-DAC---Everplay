package br.edu.ifpb.dac.livraria.beans;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.edu.ifpb.dac.livraria.modelo.enums.Categoria;
import br.edu.ifpb.dac.livraria.modelo.enums.Tipo;
import br.edu.ifpb.dac.livraria.modelo.enums.TipoEquipamento;
import br.edu.ifpb.dac.livraria.modelo.enums.TipoInstrumento;

@FacesConverter(value = "tipoConverter", forClass = Tipo.class)
public class TipoConverter implements Converter<Tipo> {

	@Override
	public Tipo getAsObject(FacesContext context, UIComponent component, String value) {
		System.out.println("tentando converter");
		try {
			if (Categoria.valueOf(value) != null) {
				return Categoria.valueOf(value);
			}
		} 
		catch (Exception e) {}
		try {
			if (TipoEquipamento.valueOf(value) != null){
				return TipoEquipamento.valueOf(value);
			}
		} 
		catch (Exception e) {}
		try {
			if (TipoInstrumento.valueOf(value) != null) {
				return TipoInstrumento.valueOf(value);
			}
		} 
		catch (Exception e) {}		
		return null;		
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Tipo value) {
		System.out.println(value);
		System.out.println("tentando converter 222");
		if (value == null) {
			return "";
		}
		else {
			return value.toString();
		}
		
	}
	
}
