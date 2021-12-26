package br.edu.ifpb.dac.livraria.beans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.ifpb.dac.livraria.modelo.Endereco;
import br.edu.ifpb.dac.livraria.modelo.Usuario;
import br.edu.ifpb.dac.livraria.servico.ServicoUsuarios;

import java.io.Serializable;

@Named
@ViewScoped
public class UsuarioBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario = new Usuario();
	private List<Usuario> usuarios;
	private String papel;
	private List<String> papeisUsuario;
	
	@EJB
	private ServicoUsuarios dao;
	
	public UsuarioBean() {
		
	}
	
	@PostConstruct
	public void init() {
		this.usuario.setEndereco(new Endereco());
		usuarios = this.dao.todosUsuarios();
		papeisUsuario = new ArrayList<String>();
	}
	
	public String cadastra() {
		System.out.println("Cadastra - Usuario: "+usuario.getNome());
		
		//Verifica se tem algum usuário no BD, caso não tenha nenhum, o primeiro usuário registrado será administrador.
		checkUsuario();
		
		//Zerando o time da data de nascimento do usuario.
		removeTimeFromDate();
		
		this.dao.salva(usuario);
		
		usuarios.add(usuario);
		
		this.usuario = new Usuario();
		
		papeisUsuario = new ArrayList<String>();		
		
		reportarMensagemDeSucesso("CADASTRO REALIZADO");
		
		return "login.xhtml?faces-redirect=true";
	}
	
	public String altera() {
		this.dao.altera(this.usuario);
		reportarMensagemDeSucesso("ALTERAÇÃO REALIZADA");
		return "index.xhtml?faces-redirect=true";
	}
	
	protected void reportarMensagemDeErro(String detalhe) {
		reportarMensagem(true, detalhe);

	}

	protected void reportarMensagemDeSucesso(String detalhe) {
		reportarMensagem(false, detalhe);
	}

	private void reportarMensagem(boolean isErro, String detalhe) {
		String tipo = "SUCESSO!";
		Severity severity = FacesMessage.SEVERITY_INFO;
		if (isErro) {
			tipo = "ERRO!";
			severity = FacesMessage.SEVERITY_ERROR;
			FacesContext.getCurrentInstance().validationFailed();
		}

		FacesMessage msg = new FacesMessage(severity, tipo + " " + detalhe, null);

		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.setKeepMessages(true);
		flash.setRedirect(true);
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}
	
	//Zerando o time da data de nascimento do usuario.
    private void removeTimeFromDate() {
    	 
    	Date date = this.usuario.getDataNascimento();
    	if (date == null) {
            return;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        
        this.usuario.setDataNascimento(date);
    }
    
    //Verifica se tem algum usuário no BD, caso não tenha nenhum, o primeiro usuário registrado será administrador.
	private void checkUsuario(){
		List<Usuario> usuarios = this.dao.todosUsuarios();
		this.papeisUsuario = new ArrayList<String>();
		if(usuarios.isEmpty()) {
			papeisUsuario.add("ADMIN_GERENTE");
			this.usuario.setPapeis(papeisUsuario);
		}
		else {
			papeisUsuario.add("CLIENTE");
			this.usuario.setPapeis(papeisUsuario);
		}
	}
	
	public boolean estaVazio() {
		if(this.usuarios.isEmpty()) {
			return true;
		}
		return false;
	}
	
	public void adicionaPapel() {
		papeisUsuario.add(papel);
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public String getPapel() {
		return papel;
	}

	public void setPapel(String papel) {
		this.papel = papel;
	}

	public List<String> getPapeisUsuario() {
		return papeisUsuario;
	}

	public void setPapeisUsuario(List<String> papeisUsuario) {
		this.papeisUsuario = papeisUsuario;
	}
	
	public boolean getTrue() {
		return true;
	}
	
	public boolean getFalse() {
		return false;
	}
	
}
