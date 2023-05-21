package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istanza;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDelegatoDTO;

public class IstanzaDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3046038980322674977L;
	@NotNull
	private RichiedenteDto richiedente;
	@NotNull
	private TecnicoIncaricatoDto tecnicoIncaricato;
	private List<AltroTitolareDto> altriTitolari;
	@NotNull
	private DichiarazioniDto dichiarazioni;
	@NotNull
	@Valid
	private LocalizazioneDto localizzazione;
	private List<PraticaDelegatoDTO> delegatoPratica;

	
	public LocalizazioneDto getLocalizzazione() {
		return localizzazione;
	}

	public void setLocalizzazione(LocalizazioneDto localizazione) {
		this.localizzazione = localizazione;
	}

	public RichiedenteDto getRichiedente() {
		return richiedente;
	}

	public TecnicoIncaricatoDto getTecnicoIncaricato() {
		return tecnicoIncaricato;
	}

	public void setTecnicoIncaricato(TecnicoIncaricatoDto tecnicoIncaricato) {
		this.tecnicoIncaricato = tecnicoIncaricato;
	}

	public List<AltroTitolareDto> getAltriTitolari() {
		return altriTitolari;
	}

	public void setAltriTitolari(List<AltroTitolareDto> altriTitolari) {
		this.altriTitolari = altriTitolari;
	}

	public void setRichiedente(RichiedenteDto richiedente) {
		this.richiedente = richiedente;
	}

	public DichiarazioniDto getDichiarazioni() {
		return dichiarazioni;
	}

	public void setDichiarazioni(DichiarazioniDto dichiarazioni) {
		this.dichiarazioni = dichiarazioni;
	}

	public List<PraticaDelegatoDTO> getDelegatoPratica()
	{
	    return delegatoPratica;
	}
	public void setDelegatoPratica(List<PraticaDelegatoDTO> delegatoPratica)
	{
	    this.delegatoPratica = delegatoPratica;
	}

	
	
}
