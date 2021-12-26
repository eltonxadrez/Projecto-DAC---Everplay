package br.edu.ifpb.dac.livraria.modelo.enums;

public  enum  TipoEquipamento implements Tipo  {
	
	//Instrumentos de corda
	AMPLIFICADOR_DE_GUITARRA("Amplificador de Guitarra"),
	PEDAL("Pedal"),
	ENCORDOAMENTO("Encordoamento"),
	
	//Bateria e percussão
	PRATO("Prato"),
	CAIXA("Caixa"),
	FERRAGEM("Ferragem"),
	
	//Áudio e tecnologia
	MIXER_E_MESA("Mixer e Mesa"),
	CAIXA_E_MONITOR("Caixa e Monitor"),
	GRAVACAO("Gravação"),
	FONE_DE_OUVIDO("Fone de Ouvido"),
	SEM_FIO("Sem Fio"),
	MICROFONE("Microfone"),
	
	//Instrumentos de teclas
	AMPLIFICADOR("Amplificador");
	
	private final String text;
	
	private TipoEquipamento(final String text) {
		this.text = text;
	}
	
    @Override
    public String toString() {
        return text;
    }

}
