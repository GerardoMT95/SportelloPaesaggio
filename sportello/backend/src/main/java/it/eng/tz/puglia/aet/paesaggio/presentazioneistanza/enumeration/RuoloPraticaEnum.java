package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration;

/**
 * Enum per ruolo_pratica
*/
public enum RuoloPraticaEnum {
	
	 PROPONENTE("PR")
	,DELEGATO("DE")
	;

	private final String codice;
	
	private RuoloPraticaEnum(final String codice) {
		this.codice = codice;
	}

	/**
	 * @return the codice
	 */
	public String getCodice() {
		return this.codice;
	}
	
	
	public static RuoloPraticaEnum fromCodice(final String codice) {
		for(final RuoloPraticaEnum item: RuoloPraticaEnum.values()) {
			if(item.getCodice().equals(codice))
				return item;
		}
		throw new IllegalArgumentException("Codice non valido");
	}
}
