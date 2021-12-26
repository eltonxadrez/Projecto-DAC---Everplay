package br.edu.ifpb.dac.livraria.beans;

import java.io.Serializable;
//import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
//import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

//import br.edu.ifpb.dac.livraria.modelo.Equipamento;
//import br.edu.ifpb.dac.livraria.modelo.Instrumento;
import br.edu.ifpb.dac.livraria.modelo.Produto;
import br.edu.ifpb.dac.livraria.modelo.enums.Categoria;
import br.edu.ifpb.dac.livraria.modelo.enums.Tipo;
import br.edu.ifpb.dac.livraria.modelo.enums.TipoEquipamento;
import br.edu.ifpb.dac.livraria.modelo.enums.TipoInstrumento;
import br.edu.ifpb.dac.livraria.servico.ServicoProduto;

@Named
@ViewScoped
public class CategoriaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Produto> produtos;
	private Tipo tipo;
	private Categoria tipoCategoria;
	private TipoEquipamento tipoEquipamento;
	private TipoInstrumento tipoInstrumento;
	
	@EJB
	private ServicoProduto servicoProduto;	
	
	@PostConstruct
	public void init() {
		System.out.println("[INFO] CategoriaBean criado ");
		this.produtos = servicoProduto.todosProdutos();
	}
	
	public void atualizar() {
		this.produtos = this.servicoProduto.todosProdutosTipo(this.tipo);
		for (Produto produto : produtos) {
			produto.getNome();
		}
	}
	
	public boolean checarProdutos() {
		if(produtos.isEmpty()) {
			return true;
		}
		return false;
	}
	
	public List<Produto> getProdutos() {
		return produtos;
	}
	
	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public Categoria getTipoCategoria() {
		return tipoCategoria;
	}

	public TipoEquipamento getTipoEquipamento() {
		return tipoEquipamento;
	}

	public TipoInstrumento getTipoInstrumento() {
		return tipoInstrumento;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
}
