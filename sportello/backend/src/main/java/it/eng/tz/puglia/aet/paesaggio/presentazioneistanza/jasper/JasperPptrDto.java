package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper;

public class JasperPptrDto {
	
	private String ambitoPaesaggistico;
    private String figura;
    private Boolean art103;
    private Boolean art142;
    private String descrizione;
    private String vincoloArt38;
    
	public String getVincoloArt38() {
		return vincoloArt38;
	}

	public void setVincoloArt38(String vincoloArt38) {
		this.vincoloArt38 = vincoloArt38;
	}

	public JasperPptrDto() {
		super();
	}
	
	public String getAmbitoPaesaggistico() {
		return ambitoPaesaggistico;
	}
	public void setAmbitoPaesaggistico(String ambitoPaesaggistico) {
		this.ambitoPaesaggistico = ambitoPaesaggistico;
	}
	public String getFigura() {
		return figura;
	}
	public void setFigura(String figura) {
		this.figura = figura;
	}
	public Boolean getArt103() {
		return art103;
	}
	public void setArt103(Boolean art103) {
		this.art103 = art103;
	}
	public Boolean getArt142() {
		return art142;
	}
	public void setArt142(Boolean art142) {
		this.art142 = art142;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}  
    
}
