package br.edu.ifpb.dac.livraria.modelo.enums;

public enum TipoInstrumento implements Tipo {
	
	//Instrumentos de corda
	VIOLAO("Violão"),
	GUITARRA("Guitarra"),
	BAIXO("Baixo"),
	
	//Bateria e percussão
	BATERIA_ACUSTICA("Bateria Acústica"),
	BATERIA_ELETRONICA("Bateria Eletrônica"),
	PERCUSSAO("Percussão"),
	
	//Arcos e sopros
	GAITA("Gaita"),
	SAXOFONE("Saxofone"),
	FLAUTA("Flauta"),
	TROMPETE("Trompete"),
	VIOLINO("Violino"),
	VIOLONCELO("Violoncelo"),
	
	//Instrumentos de teclas
	PIANO("Piano"),
	WORKSTATION("Workstation"),
	SINTETIZADOR("Sintetizador"),
	ARRANJADOR("Arranjador"),
	ACORDEON("Acordeon");	
	
	private final String text;
	
	private TipoInstrumento(final String text) {
		this.text = text;
	}
	
    @Override
    public String toString() {
        return text;
    }

}
