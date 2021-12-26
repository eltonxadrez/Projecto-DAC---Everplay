package br.edu.ifpb.dac.livraria.beans;

import java.io.IOException;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.edu.ifpb.dac.livraria.modelo.Usuario;
import br.edu.ifpb.dac.livraria.servico.identitystore.UsuarioPrincipal;

@Named
@RequestScoped
public class LoginBean {

	private Usuario usuario;
		
	@Inject
	private FacesContext facesContext;
	
	@Inject
	private ExternalContext externalContext;
	
	@SuppressWarnings("cdi-ambiguous-dependency")
	@Inject
	private SecurityContext securityContext;
	
	public LoginBean() {
		usuario = new Usuario();
	}
	
	public String login() {
		
		System.out.println("Login: "+usuario.getEmail()+" e senha: "+usuario.getSenha());
		AuthenticationStatus status = executaAutenticacao();
		System.out.println("Status: "+status);
		switch (status) {
		case SEND_CONTINUE:
			System.out.println("Principal: "+securityContext.getCallerPrincipal());
			if (securityContext.getCallerPrincipal()!=null) {
				facesContext.responseComplete();
			}else {
				facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro no login",null));
				FacesContext.getCurrentInstance()
			    .getExternalContext()
			    .getFlash().setKeepMessages(true);
			}
			break;
			
		case SEND_FAILURE:
			reportarMensagemDeErro("Erro no login");
			FacesContext.getCurrentInstance()
		    .getExternalContext()
		    .getFlash().setKeepMessages(true);
			break;
		case SUCCESS:
			reportarMensagemDeSucesso("Login Realizado");
			return "index.xhtml?faces-redirect=true";
		case NOT_DONE:
				//NADA
			break;
		}
		return null;
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
	
	public String logout() {
		HttpServletRequest request = (HttpServletRequest)externalContext.getRequest();
		try {
			request.logout();
		} catch (ServletException e) {
			e.printStackTrace();
		}
		request.getSession().invalidate();
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Logout realizado com sucesso",null));
		
		return "index.xhtml?faces-redirect=true";
	}
	public void processTimeOut() throws IOException {
		if(verificarUsuarioLogado()) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
		}
	}
	public void processTimeOutCliente() throws IOException {
		if(verificarUsuarioCliente()) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
		}
	}
	public void processTimeOutAdmin() throws IOException {
		if(verificarUsuarioAdiminstrador()) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
		}
	}
	
	private AuthenticationStatus executaAutenticacao() {
		return securityContext.authenticate((HttpServletRequest)externalContext.getRequest(), 
				(HttpServletResponse) externalContext.getResponse(), AuthenticationParameters
				.withParams().credential(new UsernamePasswordCredential(usuario.getEmail(), usuario.getSenha())));
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Boolean verificarUsuarioLogado() {
		if(getUsuarioLogado() == null) {
			return false;
		}
		return true;
	}
	
	public Boolean verificarUsuarioCliente() {
		if(getUsuarioLogado() == null) {
			return false;
		}
		else {
			if(getUsuarioLogado().getPapeis().contains("CLIENTE")) {
				return true;
			}
		}
		return false;
	}
	
	public Boolean verificarUsuarioAdiminstrador() {
		if(getUsuarioLogado() == null) {
			return false;
		}
		else {
			if(getUsuarioLogado().getPapeis().contains("ADMIN_GERENTE")) {
				return true;
			}
		}
		return false;
	}


	public Usuario getUsuarioLogado() {
		Usuario nomeUsuarioLogado = null;
		Optional<UsuarioPrincipal> usuarioLogado = securityContext.getPrincipalsByType(UsuarioPrincipal.class).stream().findFirst();
		if (usuarioLogado.isPresent()) {
			nomeUsuarioLogado = usuarioLogado.get().getUsuario();
		}
		
		return nomeUsuarioLogado;
	}
}
