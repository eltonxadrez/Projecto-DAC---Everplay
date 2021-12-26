package br.edu.ifpb.dac.livraria.beans;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.edu.ifpb.dac.livraria.modelo.Produto;
import br.edu.ifpb.dac.livraria.servico.ServicoProduto;


@FacesConverter(managed = true, forClass = Produto.class)
public class ProdutoConverter implements Converter<Produto>{
	
	@Inject
	private ServicoProduto servico;

	@Override
	public Produto getAsObject(FacesContext context, UIComponent component, String idProduto) {
		if (idProduto == null || idProduto.isEmpty()) {
			return null;
		}
		
		try {
			Integer id = Integer.parseInt(idProduto);
			Produto l = servico.buscaPelaId(id);
			return l;
		} catch (NumberFormatException e) {
			throw new ConverterException(new FacesMessage("Produto com ID inválido."),e);
		}

	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Produto produto) {
		
		if (produto == null) {
			return "";
		}
		
		if (produto.getId() != null) {
			return produto.getId().toString();
		}else {
			throw new ConverterException(new FacesMessage("Produto com ID inválido."),null);
		}
		
	}

}
