package br.edu.ifpb.dac.livraria.servico;


import java.util.List;

import javax.annotation.PostConstruct;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.mindrot.jbcrypt.BCrypt;

import br.edu.ifpb.dac.livraria.modelo.Usuario;

@Stateless
public class ServicoUsuarios {
	
	@PersistenceContext
	private EntityManager manager;
	
	@PostConstruct
	void aposCriacao() {
	    System.out.println("[INFO] UsuarioDao foi criado.");
	}
	
	public void salva(Usuario usuario) {
		
		usuario.setSenha(transformaSenhaEmHash(usuario.getSenha()));
		
		manager.persist(usuario);
		System.out.println("[INFO] Salvou o Usuario " + usuario.getNome());
	}
	
	public void altera(Usuario usuario) {
		manager.merge(usuario);
		System.out.println("[INFO] Alterou o Usuario " + usuario.getNome());
	}
	
	private String transformaSenhaEmHash(String senhaBruta) {
		System.out.println("Gerando Hash usando Bcrypt");
		String senhaHashed = null;
		String salto = null;
		
		salto = BCrypt.gensalt();
	    senhaHashed = BCrypt.hashpw(senhaBruta, salto);
	    
	    System.out.println("Bcrypt - senhaHash: "+senhaHashed);
		
		return senhaHashed;
	}
	
	public void remove(Usuario usuario) {
		Usuario usuarioProcurado = buscaPeloEmailESenha(usuario.getEmail(), usuario.getSenha());
		manager.remove(usuarioProcurado);
	}
	
	public Usuario buscaPeloEmailESenha(String email, String senha) {
	    System.out.println("[INFO] Consultando o usuario pelo e-mail: "+email);
	    
	    Usuario usuarioRecuperado = null; 
	    try {
	    	
	    
	    usuarioRecuperado = manager.createQuery("select u from Usuario u where u.email = :email",Usuario.class)
		.setParameter("email", email)
		.getSingleResult();
	    
	    }catch (NoResultException nre){
	    	return null;
	    }
	    
	    if (usuarioRecuperado!=null) {
			if(verificaSenhaHash(senha, usuarioRecuperado.getSenha())) {
				return usuarioRecuperado;
			}
			
		}
	    
		return null; 
	}

	private boolean verificaSenhaHash(String senha, String senhaRecuperada) {
		// Programação defensiva contra NPE
		if(senha == null || senhaRecuperada == null) {
			return false;
		}
		return BCrypt.checkpw(senha, senhaRecuperada);
	}

    
	public Usuario buscaPelaId(String email) {
	    System.out.println("[INFO] Consultando Usuario pelo email: "+email);

		Usuario usuario = manager.find(Usuario.class,email);
		
		return usuario;
	}

	public List<Usuario> todosUsuarios() {
	    System.out.println("[INFO] Consultando todos os usuarios ");
		return manager.createQuery("select u from Usuario u",Usuario.class).getResultList();
	}
	
}
