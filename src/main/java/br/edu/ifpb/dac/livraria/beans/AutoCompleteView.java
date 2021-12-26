package br.edu.ifpb.dac.livraria.beans;

import java.util.List;
import java.util.stream.Collectors;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.ifpb.dac.livraria.modelo.Produto;
import br.edu.ifpb.dac.livraria.servico.ServicoProduto;
import java.io.Serializable;

@Named
@ViewScoped
public class AutoCompleteView implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Produto produtoSelecionado;
	
	@Inject
	private ServicoProduto servicoProduto;
	
	 public List<Produto> completeNome(String query) {
	        String queryLowerCase = query.toLowerCase();
	        List<Produto> allThemes = this.servicoProduto.todosProdutos();
	        return allThemes.stream().filter(t -> t.getNome().toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
	    }	
	
	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}
	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}
	
}
