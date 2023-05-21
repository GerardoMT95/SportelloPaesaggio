package it.eng.tz.puglia.autpae.enumeratori;

public enum TipoLocalizzazione {
	
//	R("Regione"),
//	P("Provincia"),
//	CO("Comune"),
	SHPD("Editing shape"),
	SHPF("Shape File"),
	PTC("Particella");
	
	private String value;

	private TipoLocalizzazione(String text) {
		this.value = text;
	}

	public String getTextValue() {
		return value;
	}

	/**
	 * codifica da sportello ambiente
	 * @autor Adriano Colaianni
	 * @date 26 lug 2022
	 * @param tipoSelezioneLocalizzazione
	 * @return
	 */
	public static TipoLocalizzazione fromCodificaProcedimentiAmbiente(String tipoSelezioneLocalizzazione) {
		if(tipoSelezioneLocalizzazione.equals("E")) {
			return SHPD;
		}else if(tipoSelezioneLocalizzazione.equals("S")) {
			return SHPF;
		}else  if(tipoSelezioneLocalizzazione.equals("P")) {
			return PTC;	
		}
		return null;
	}

}
