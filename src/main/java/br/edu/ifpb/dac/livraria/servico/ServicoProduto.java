package br.edu.ifpb.dac.livraria.servico;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.edu.ifpb.dac.livraria.modelo.Equipamento;
import br.edu.ifpb.dac.livraria.modelo.Instrumento;
import br.edu.ifpb.dac.livraria.modelo.Produto;
import br.edu.ifpb.dac.livraria.modelo.enums.Categoria;
import br.edu.ifpb.dac.livraria.modelo.enums.Tipo;
import br.edu.ifpb.dac.livraria.modelo.enums.TipoEquipamento;
import br.edu.ifpb.dac.livraria.modelo.enums.TipoInstrumento;

@Stateless
public class ServicoProduto {
	
	@PersistenceContext
	private EntityManager manager;
	
	@PostConstruct
	void aposCriacao() {
	    System.out.println("[INFO] ServicoProduto foi criado.");
	}
	
	@RolesAllowed({"ADMIN_GERENTE"})
	public void salva(Produto produto) {		
		System.out.println("[INFO] Salvando o Produto " + produto.getNome());
		this.manager.persist(produto);
		System.out.println("[INFO] Salvou o Produto " + produto.getNome());
	}
	
	@PermitAll
	public List<Produto> todosProdutos() {
		return manager.createQuery("select l from Produto l",Produto.class).getResultList();
	}

	@PermitAll
	public Produto buscaPelaId(Integer produtoId) {
		Produto produto = manager.find(Produto.class, produtoId);		
		return produto;
	}
	
	@PermitAll
	public Equipamento buscaEquipPelaId(Integer produtoId) {
		Equipamento equipamento = manager.find(Equipamento.class, produtoId);
		return equipamento;
	}

	@PermitAll
	public void altera(Produto produto) {
		System.out.println("[INFO] Alterando o Produto " + produto.getNome());
	    manager.merge(produto);
	    System.out.println("[INFO] Produto " + produto.getNome()+" alterado com sucesso.");
	}
	
	@RolesAllowed({"ADMIN_GERENTE"})
	public void deleta(Integer produtoId) {
		Produto produtoParaRemocao = buscaPelaId(produtoId);
		System.out.println("[INFO] Deletando o Produto " + produtoParaRemocao.getNome());
	    manager.remove(produtoParaRemocao);
	    System.out.println("[INFO] Produto " + produtoParaRemocao.getNome()+" deletado com sucesso.");	
	}
	
	@PermitAll
	public List<Instrumento> todosInstrumentos() {
		return manager.createQuery("select l from Instrumento l" , Instrumento.class).getResultList();
	}
	
	@PermitAll
	public List<Equipamento> todosEquipamentos() {
		return manager.createQuery("select l from Equipamento l" , Equipamento.class).getResultList();
	}
	
	@PermitAll
	public List<Produto> todosProdutosTipo(Tipo tipo) {
		ArrayList<Produto> novaLista = new ArrayList<Produto>();
		if(tipo instanceof Categoria) {
			Categoria categoria = (Categoria) tipo;
			return manager.createQuery("select l from Produto l where l.categoria =" + categoria.ordinal() , Produto.class).getResultList();
		}
		else if(tipo instanceof TipoInstrumento) {
			TipoInstrumento tipoInstrumento = (TipoInstrumento) tipo;
			List<Instrumento> listaRecuperada = manager.createQuery("select l from Instrumento l where l.tipoInstrumento =" + tipoInstrumento.ordinal() , Instrumento.class).getResultList();
			novaLista.addAll(listaRecuperada);
			return novaLista;
		}
		else {
			TipoEquipamento tipoEquipamento = (TipoEquipamento) tipo;
			ArrayList<Equipamento> listaRecuperada = (ArrayList<Equipamento>) manager.createQuery("select l from Equipamento l where l.tipoEquipamento =" + tipoEquipamento.ordinal() , Equipamento.class).getResultList();
			novaLista.addAll(listaRecuperada);
			return novaLista;
		}
		 
	}

}
