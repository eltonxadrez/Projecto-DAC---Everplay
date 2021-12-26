package br.edu.ifpb.dac.livraria.servico.identitystore;

import javax.security.enterprise.CallerPrincipal;

import br.edu.ifpb.dac.livraria.modelo.Usuario;

public class UsuarioPrincipal extends CallerPrincipal {
	
	private final Usuario usuario;

	public UsuarioPrincipal(Usuario usuario) {
		super(usuario.getEmail());
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	
}
