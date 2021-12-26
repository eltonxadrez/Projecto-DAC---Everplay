package br.edu.ifpb.dac.livraria.modelo;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Usuario {

	//Dados de acesso à loja
	@Id
	@Column(length = 150)
	private String email;
	@Column(length = 255)
	private String senha;
	@Column(length = 700)
	private String saltoHash;
	
	//Dados pessoais
	private String nome;
	private String cpf;
	private Date dataNascimento;
	private String telefone;
	
	//Endereço
	@Embedded
	private Endereco endereco;
	
	@ElementCollection(targetClass = String.class,fetch = FetchType.EAGER)
	private List<String> papeis;
	
	public Usuario() {
		// TODO Auto-generated constructor stub
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}


	public List<String> getPapeis() {
		return papeis;
	}

	public void setPapeis(List<String> papeis) {
		this.papeis = papeis;
	}

	public Set<String> getPapeisString(){
		return new HashSet<String>(papeis);
	}

	public String getSaltoHash() {
		return saltoHash;
	}

	public void setSaltoHash(String saltoHash) {
		this.saltoHash = saltoHash;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
}
