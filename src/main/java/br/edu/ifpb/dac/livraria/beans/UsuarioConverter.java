package br.edu.ifpb.dac.livraria.beans;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.edu.ifpb.dac.livraria.modelo.Usuario;
import br.edu.ifpb.dac.livraria.servico.ServicoUsuarios;

@FacesConverter(managed = true, forClass = Usuario.class)
public class UsuarioConverter implements Converter<Usuario> {
	
	@Inject
	private ServicoUsuarios servico;

	@Override
	public Usuario getAsObject(FacesContext context, UIComponent component, String email) {
		if (email == null || email.isEmpty()) {
			return null;
		}
		
		try {
			Usuario u = servico.buscaPelaId(email);
			return u;
		} catch (NumberFormatException e) {
			throw new ConverterException(new FacesMessage("Produto com ID inválido."),e);
		}
		
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Usuario usuario) {
		if (usuario == null) {
			return "";
		}
		if (usuario.getEmail() != null) {
			return usuario.getEmail().toString();
		}else {
			throw new ConverterException(new FacesMessage("Produto com ID inválido."),null);
		}
		
	}

}
