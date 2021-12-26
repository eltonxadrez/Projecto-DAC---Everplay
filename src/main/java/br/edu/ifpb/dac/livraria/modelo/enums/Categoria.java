package br.edu.ifpb.dac.livraria.modelo.enums;

public enum Categoria implements Tipo {
	
	//Categorias para o menu principal
	INSTRUMENTOS_DE_CORDAS("Instrumentos de Cordas"),
	BATERIA_E_PERCUSSAO("Bateria e Percussão"),
	AUDIO_E_TECNOLOGIA("Áudio e Tecnologia"),
	ARCOS_E_SOPROS("Arcos e Sopros"),
	INSTRUMENTOS_DE_TECLAS("Intrumentos de Teclas");
	
	private final String text;
	
	private Categoria(final String text) {
		this.text = text;
	}
	
    @Override
    public String toString() {
        return text;
    }

}
