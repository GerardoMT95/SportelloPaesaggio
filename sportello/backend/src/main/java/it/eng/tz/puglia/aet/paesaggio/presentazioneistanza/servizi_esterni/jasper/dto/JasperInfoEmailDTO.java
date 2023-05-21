package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.jasper.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JasperInfoEmailDTO implements Serializable {

	private static final long serialVersionUID = 4982170498229217272L;

	private Date data;				// facoltativo
	private String protocollo;		// facoltativo
	private String mittenteEmail;	// facoltativo
	private String mittenteNome;	// facoltativo
	private String oggetto;
	private String corpo;
	private List<JasperInfoEmailAllegatoDTO> listaAllegati;		// facoltativo
	private List<JasperInfoEmailDestinatarioDTO> listaDestinatari;
	
	
	public JasperInfoEmailDTO() {
		this.listaDestinatari = new ArrayList<>();
		this.listaAllegati    = new ArrayList<>();
	}


	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getProtocollo() {
		return protocollo;
	}
	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}
	public String getMittenteEmail() {
		return mittenteEmail;
	}
	public void setMittenteEmail(String mittenteEmail) {
		this.mittenteEmail = mittenteEmail;
	}
	public String getMittenteNome() {
		return mittenteNome;
	}
	public void setMittenteNome(String mittenteNome) {
		this.mittenteNome = mittenteNome;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public String getCorpo() {
		return corpo;
	}
	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}
	public List<JasperInfoEmailAllegatoDTO> getListaAllegati() {
		return listaAllegati;
	}
	public void setListaAllegati(List<JasperInfoEmailAllegatoDTO> listaAllegati) {
		this.listaAllegati = listaAllegati;
	}
	public List<JasperInfoEmailDestinatarioDTO> getListaDestinatari() {
		return listaDestinatari;
	}
	public void setListaDestinatari(List<JasperInfoEmailDestinatarioDTO> listaDestinatari) {
		this.listaDestinatari = listaDestinatari;
	}
	
	@Override
	public String toString() {
		return "JasperInfoEmailDTO [data=" + data + ", protocollo=" + protocollo + ", mittenteEmail=" + mittenteEmail
				+ ", mittenteNome=" + mittenteNome + ", oggetto=" + oggetto + ", corpo=" + corpo + ", listaAllegati="
				+ listaAllegati + ", listaDestinatari=" + listaDestinatari + "]";
	}
	
}