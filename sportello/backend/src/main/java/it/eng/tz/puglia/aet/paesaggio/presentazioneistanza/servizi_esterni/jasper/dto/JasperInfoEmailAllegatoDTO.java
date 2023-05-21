package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.jasper.dto;

import java.io.Serializable;

public class JasperInfoEmailAllegatoDTO implements Serializable {

	private static final long serialVersionUID = 4982170498229217272L;

	private String nome;
	private String checksum;
	private long dimensione;

	
	public JasperInfoEmailAllegatoDTO() { }
	
	public JasperInfoEmailAllegatoDTO(String nome, String checksum, long dimensione) {
		this.nome = nome;
		this.checksum = checksum;
		this.dimensione = dimensione;
	}
	
	
	public String getChecksum() {
		return checksum;
	}
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public long getDimensione() {
		return dimensione;
	}
	public void setDimensione(long dimensione) {
		this.dimensione = dimensione;
	}
	
	@Override
	public String toString() {
		return "JasperInfoEmailAllegatoDTO [checksum=" + checksum + ", nome=" + nome + ", dimensione=" + dimensione + "]";
	}
	
}