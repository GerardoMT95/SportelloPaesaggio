package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper;

import java.util.List;

public class JasperPptrContenutoTabellaDto {
	
	private String nome;
	private String art1;
	private String definizione;
	private String disposizioniNormative;
	private String art2;
	// solo per la parte sotto le scritte in grassetto -->
		private List<JasperPptrContenutoTabellaDto> figli;
		private String specificare;
	// <--
	// mi servono solo per lo smistamento alla creazione del report -->
		private String codice;
		private String gruppo;
	// <--
		private int ordine;
		private int livello;
		
	public int getOrdine() {
			return ordine;
		}

		public void setOrdine(int ordine) {
			this.ordine = ordine;
		}

	public JasperPptrContenutoTabellaDto() {
		super();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getArt1() {
		return art1;
	}

	public void setArt1(String art1) {
		this.art1 = art1;
	}

	public String getDefinizione() {
		return definizione;
	}

	public void setDefinizione(String definizione) {
		this.definizione = definizione;
	}

	public String getDisposizioniNormative() {
		return disposizioniNormative;
	}

	public void setDisposizioniNormative(String disposizioniNormative) {
		this.disposizioniNormative = disposizioniNormative;
	}

	public String getArt2() {
		return art2;
	}

	public void setArt2(String art2) {
		this.art2 = art2;
	}

	public List<JasperPptrContenutoTabellaDto> getFigli() {
		return figli;
	}

	public void setFigli(List<JasperPptrContenutoTabellaDto> figli) {
		this.figli = figli;
	}

	public String getSpecificare() {
		return specificare;
	}

	public void setSpecificare(String specificare) {
		this.specificare = specificare;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getGruppo() {
		return gruppo;
	}

	public void setGruppo(String gruppo) {
		this.gruppo = gruppo;
	}

	public int getLivello() {
		return livello;
	}

	public void setLivello(int livello) {
		this.livello = livello;
	}

	
	
}
