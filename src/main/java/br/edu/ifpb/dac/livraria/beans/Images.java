package br.edu.ifpb.dac.livraria.beans;

import javax.inject.Inject;

import org.omnifaces.cdi.GraphicImageBean;

import br.edu.ifpb.dac.livraria.servico.ServicoProduto;

@GraphicImageBean
public class Images {
	
	@Inject
	private ServicoProduto servicoProduto;
	
	ProdutoBean produtoBean;
	
	public byte[] get(Integer id) {
        return servicoProduto.buscaPelaId(id).getImagemProduto();
    }

}
