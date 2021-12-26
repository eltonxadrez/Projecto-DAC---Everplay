package br.edu.ifpb.dac.livraria.beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBAccessException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.UploadedFile;

import br.edu.ifpb.dac.livraria.modelo.Equipamento;
import br.edu.ifpb.dac.livraria.modelo.Instrumento;
import br.edu.ifpb.dac.livraria.modelo.Produto;
import br.edu.ifpb.dac.livraria.modelo.enums.Categoria;
import br.edu.ifpb.dac.livraria.modelo.enums.Tipo;
import br.edu.ifpb.dac.livraria.modelo.enums.TipoEquipamento;
import br.edu.ifpb.dac.livraria.modelo.enums.TipoInstrumento;
import br.edu.ifpb.dac.livraria.servico.ServicoProduto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class ProdutoBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Produto produto = new Produto();
	private Instrumento instrumento = new Instrumento();
	private Equipamento equipamento = new Equipamento();
	private List<Produto> produtos;
	private List<Instrumento> instrumentos;
	private List<Equipamento> equipamentos;
	private List<String> tipoProdutos;
	private String tipoProduto;
	private String tipo;
	private Tipo[] lista;
	private List<String> nomeProdutos;
	private Double total;
	private Integer quantidade;
	@EJB
	private ServicoProduto servicoProduto;
	@Inject
	private FacesContext facesContext;
	
	private UploadedFile uploadedFile;
	
	public ProdutoBean() {
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	public void init() {		
		this.produtos = this.servicoProduto.todosProdutos();
		this.instrumentos = this.servicoProduto.todosInstrumentos();
		this.equipamentos = this.servicoProduto.todosEquipamentos();
		System.out.println("[INFO] ProdutoBean criado ");
		this.tipoProdutos = new ArrayList<String>();
		this.tipoProdutos.add("equipamento");
		this.tipoProdutos.add("instrumento");
		this.tipoProduto = "equipamento";
		this.tipo = "tipoEquipamento";
		this.lista = getTipoEquipamentos();
		this.nomeProdutos = gerarNomeProdutos();
	}
	
	public String compra() {
		if(this.produto.getQuantidade() < 1) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Produto em falta no estoque!", null));
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			return "/index.xhtml?faces-redirect=true";
		}		
		return "/compra.xhtml?faces-redirect=true";
	}
	
	public String finalizarCompra() {
		
		this.produto.setQuantidade(this.produto.getQuantidade() - this.quantidade);
		this.servicoProduto.altera(this.produto);
		
		facesContext.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Compra realizada com sucesso.", null));

		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		
		return "/index.xhtml?faces-redirect=true";
	}
	
	private Categoria checkCategoria(Tipo tipo) {
		if(tipo == null) {
			return null;
		}
		if (tipo.toString().matches("(?i)Amplificador de Guitarra|Pedal|Encordoamento|Violão|Guitarra|Baixo")) {
		     return Categoria.INSTRUMENTOS_DE_CORDAS;
		}
		else if (tipo.toString().matches("(?i)Prato|Caixa|Ferragem|Bateria Acústica|Bateria Eletrônica|Percussão")) {
			return Categoria.BATERIA_E_PERCUSSAO;
		}
		else if (tipo.toString().matches("(?i)Mixer e Mesa|Caixa e Monitor|Gravação|Fone de Ouvido|Sem Fio|Microfone")) {
			return Categoria.AUDIO_E_TECNOLOGIA;
		}
		else if (tipo.toString().matches("(?i)Gaita|Saxofone|Flauta|Trompete|Violino|Violoncelo")) {
			return Categoria.ARCOS_E_SOPROS;
		}
		else {
			return Categoria.INSTRUMENTOS_DE_TECLAS;
		}
	}
	
	private void setCategoria(Equipamento equipamento) {
		equipamento.setCategoria(checkCategoria(equipamento.getTipoEquipamento()));
	}
	
	private void setCategoria(Instrumento instrumento) {
		instrumento.setCategoria(checkCategoria(instrumento.getTipoInstrumento()));
	}
	
	public String cadastra() {
		
		Produto novoProduto = null;
		uploadImage();
		
		if(this.tipoProduto.equals("equipamento")) {
			setCategoria(this.equipamento);
			novoProduto = this.equipamento;			
		}
		if(this.tipoProduto.equals("instrumento")) {
			setCategoria(this.instrumento);
			novoProduto = this.instrumento;
		}		
		if(novoProduto == null) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Produto Inválido", null));
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			return null;
		}

		System.out.println("Cadastra - " + this.tipoProduto + ": " + novoProduto.getNome());

		try {			
			System.out.println("SALVAMENTO DE IMAGEM NO BD" + novoProduto.getImagemProduto());
			this.servicoProduto.salva(novoProduto);

		} catch (EJBAccessException e) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário sem permissão para alterar.", null));
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

			return null;
		}

		this.produtos.add(novoProduto);
		facesContext.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Produto gravado com sucesso.", null));

		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

		return "/admin/registra_produto.xhtml?faces-redirect=true";
	}
	
	public List<String> gerarNomeProdutos(){
		ArrayList<String> nomes = new ArrayList<String>();
		for (Produto produto : this.produtos) {
			nomes.add(produto.getNome());
		}
		return nomes;
	}
	
    public List<Produto> completeNome(String query) {
        String queryLowerCase = query.toLowerCase();
        List<Produto> allThemes = this.produtos;
        return allThemes.stream().filter(t -> t.getNome().toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
    }
	
	public String altera() {
		System.out.println("Altera - Produto: " + this.produto.getNome());
		try {
			this.servicoProduto.altera(this.produto);
		} catch (EJBAccessException e) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário sem permissão para alterar.", null));
			return null;
		}

		this.produto = new Produto();

		facesContext.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Produto alterado com sucesso.", null));

		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

		return "index?faces-redirect=true";
	}
	
	public String deletaProduto() {
		
		
		try {			
			this.servicoProduto.deleta(this.produto.getId());

		} catch (EJBAccessException e) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário sem permissão para alterar.", null));
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

		}
		this.produtos.remove(this.produto);
		
		facesContext.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Produto deletado com sucesso.", null));

		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		
		return "/admin/lista_produto.xhtml?faces-redirect=true";
		
	}
	
	public String deletaInstrumento() {		
		
		try {			
			this.servicoProduto.deleta(this.instrumento.getId());

		} catch (EJBAccessException e) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário sem permissão para alterar.", null));
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

		}
		this.produtos.remove(instrumento);
		this.instrumentos.remove(this.instrumento);
		
		facesContext.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Instrumento deletado com sucesso.", null));

		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		
		return "/admin/lista_Instrumento.xhtml?faces-redirect=true";
		
	}
	
	public String deletaEquipamento() {		
		
		try {			
			this.servicoProduto.deleta(this.equipamento.getId());

		} catch (EJBAccessException e) {
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário sem permissão para alterar.", null));
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

		}
		this.produtos.remove(equipamento);
		this.equipamentos.remove(this.equipamento);
		
		facesContext.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Equipamento deletado com sucesso.", null));

		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		
		return "/admin/lista_Equipamento.xhtml?faces-redirect=true";
		
	}
	
	public void ouvinte() {
		if(this.tipoProduto.equals("equipamento")) {
			
			this.tipo = "tipoEquipamento";
			this.lista = getTipoEquipamentos();
		}
		if(this.tipoProduto.equals("instrumento")) {
			
			this.tipo = "tipoInstrumento";
			this.lista = getTipoInstrumentos();
		}
	}
	
	public void uploadImage() {
	    byte[] contents = getUploadedFileInByte();
	    if(this.tipoProduto.equals("equipamento")) {
	    	if(this.equipamento == null) {
	    		this.equipamento = new Equipamento();
	    	}
	    	this.equipamento.setImagemProduto(contents);
		}
		if(this.tipoProduto.equals("instrumento")) {
			if(this.instrumento == null) {
				this.instrumento = new Instrumento();
			}
			this.instrumento.setImagemProduto(contents);
		}
	}
	public void calcular() {
		if(this.quantidade == null) {
			System.out.println("setar quantidade para 1");
			this.quantidade = 1;
		}
		Double preco = produto.getPreco().doubleValue();
		preco = preco * quantidade;
		this.total = preco;
		System.out.println("tudo calculado");
	}
	
	public Produto getProduto() {
		return produto;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public List<Produto> getProdutos() {
		return produtos;
	}
	
	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Instrumento getInstrumento() {
		return instrumento;
	}

	public void setInstrumento(Instrumento instrumento) {
		this.instrumento = instrumento;
	}

	public Equipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}
	
	public TipoEquipamento[] getTipoEquipamentos() {
		return TipoEquipamento.values();
	}
	
	public TipoInstrumento[] getTipoInstrumentos() {
		return TipoInstrumento.values();
	}

	public List<String> getTipoProdutos() {
		return tipoProdutos;
	}

	public void setTipoProdutos(List<String> tipoProdutos) {
		this.tipoProdutos = tipoProdutos;
	}

	public String getTipoProduto() {
		return tipoProduto;
	}

	public void setTipoProduto(String tipoProduto) {
		this.tipoProduto = tipoProduto;
	}

	public List<Tipo> getLista() {
		return (Arrays.asList(this.lista));
	}

	public void setLista(Tipo[] lista) {
		this.lista = lista;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}
	
	public byte[] getUploadedFileInByte() {
		return uploadedFile.getContents();
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public List<String> getNomeProdutos() {
		return nomeProdutos;
	}

	public void setNomeProdutos(List<String> nomeProdutos) {
		this.nomeProdutos = nomeProdutos;
	}

	public List<Instrumento> getInstrumentos() {
		return instrumentos;
	}

	public void setInstrumentos(List<Instrumento> instrumentos) {
		this.instrumentos = instrumentos;
	}

	public List<Equipamento> getEquipamentos() {
		return equipamentos;
	}

	public void setEquipamentos(List<Equipamento> equipamentos) {
		this.equipamentos = equipamentos;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

}
