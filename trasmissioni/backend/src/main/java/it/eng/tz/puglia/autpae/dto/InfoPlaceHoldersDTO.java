package it.eng.tz.puglia.autpae.dto;

import java.io.Serializable;

import it.eng.tz.puglia.autpae.entity.CampionamentoDTO;
import it.eng.tz.puglia.autpae.entity.RichiedenteDTO;
import it.eng.tz.puglia.autpae.generated.orm.dto.RichiestePostTrasmissioneDTO;

public class InfoPlaceHoldersDTO implements Serializable
{
	private static final long serialVersionUID = 7545873978918968471L;

	private Long idFascicolo;
	private FascicoloTabDTO fascicolo;
	private RichiedenteDTO richiedente;
	private String ulterDocTitolo;
	private String ulterDocDescrizione;
	private String ulterDocNomeFile;
	private String ulterDocLinkFile;
	private String infoAssegnatario;
	private CampionamentoDTO campionamento;
	private String urlRicevutaTrasmissione;
	private String urlProvvedimentoFinale;
	/**
	 * in caso di invio mail di richieste modifica/cancellazione post trasmissione
	 */
	private RichiestePostTrasmissioneDTO richiestaPostTrasmissione;
	
	private String ulterioreDocumentazione;
	
	
	
	public String getUlterDocLinkFile() {
		return ulterDocLinkFile;
	}


	public void setUlterDocLinkFile(String ulterDocLinkFile) {
		this.ulterDocLinkFile = ulterDocLinkFile;
	}


	public InfoPlaceHoldersDTO() { }

	
	public Long getIdFascicolo() {
		return idFascicolo;
	}

	public CampionamentoDTO getCampionamento() {
		return campionamento;
	}

	public void setCampionamento(CampionamentoDTO campionamento) {
		this.campionamento = campionamento;
	}

	public void setIdFascicolo(Long idFascicolo) {
		this.idFascicolo = idFascicolo;
	}

	public FascicoloTabDTO getFascicolo() {
		return fascicolo;
	}

	public void setFascicolo(FascicoloTabDTO fascicolo) {
		this.fascicolo = fascicolo;
	}

	public RichiedenteDTO getRichiedente() {
		return richiedente;
	}

	public void setRichiedente(RichiedenteDTO richiedente) {
		this.richiedente = richiedente;
	}

	public String getUlterDocTitolo() {
		return ulterDocTitolo;
	}

	public void setUlterDocTitolo(String ulterDocTitolo) {
		this.ulterDocTitolo = ulterDocTitolo;
	}

	public String getUlterDocDescrizione() {
		return ulterDocDescrizione;
	}

	public void setUlterDocDescrizione(String ulterDocDescrizione) {
		this.ulterDocDescrizione = ulterDocDescrizione;
	}

	public String getUlterDocNomeFile() {
		return ulterDocNomeFile;
	}

	public void setUlterDocNomeFile(String ulterDocNomeFile) {
		this.ulterDocNomeFile = ulterDocNomeFile;
	}

	public String getInfoAssegnatario() {
		return infoAssegnatario;
	}

	public void setInfoAssegnatario(String infoAssegnatario) {
		this.infoAssegnatario = infoAssegnatario;
	}


	public String getUrlRicevutaTrasmissione() {
		return urlRicevutaTrasmissione;
	}


	public void setUrlRicevutaTrasmissione(String urlRicevutaTrasmissione) {
		this.urlRicevutaTrasmissione = urlRicevutaTrasmissione;
	}


	public RichiestePostTrasmissioneDTO getRichiestaPostTrasmissione() {
		return richiestaPostTrasmissione;
	}


	public void setRichiestaPostTrasmissione(RichiestePostTrasmissioneDTO richiestaPostTrasmissione) {
		this.richiestaPostTrasmissione = richiestaPostTrasmissione;
	}

	public String getUlterioreDocumentazione()
	{
	    return ulterioreDocumentazione;
	}
	public void setUlterioreDocumentazione(String ulterioreDocumentazione)
	{
	    this.ulterioreDocumentazione = ulterioreDocumentazione;
	}


	public String getUrlProvvedimentoFinale() {
		return urlProvvedimentoFinale;
	}


	public void setUrlProvvedimentoFinale(String urlProvvedimentoFinale) {
		this.urlProvvedimentoFinale = urlProvvedimentoFinale;
	}
	
	
}