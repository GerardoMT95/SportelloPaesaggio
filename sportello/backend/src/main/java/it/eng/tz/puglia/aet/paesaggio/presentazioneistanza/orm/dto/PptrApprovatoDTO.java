package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.PptrDto;

/**
 * DTO for table presentazione_istanza.pptr_approvato
 */
public class PptrApprovatoDTO implements Serializable
{

	private static final long serialVersionUID = 8637288003L;
	// COLUMN pratica_id
	private UUID praticaId;
	// COLUMN ambito_paesaggistico
	private String ambitoPaesaggistico;
	// COLUMN figure_ambito
	private String figureAmbito;
	// COLUMN ricade_terr_art_1_03_co_5_6
	private Boolean ricadeTerrArt103Co56;
	// COLUMN ricade_terr_art_142_co_2
	private Boolean ricadeTerrArt142Co2;
	
	public PptrApprovatoDTO()
	{
		super();	
	}
	public PptrApprovatoDTO(PptrDto pptr)
	{
		this.praticaId = pptr.getIdPratica();
		this.ambitoPaesaggistico = pptr.getAmbitoPaesaggistico();
		this.figureAmbito = pptr.getFigura();
		this.ricadeTerrArt103Co56 = pptr.getArt103();
		this.ricadeTerrArt142Co2 = pptr.getArt142();
	}

	public UUID getPraticaId()
	{
		return this.praticaId;
	}
	public void setPraticaId(UUID praticaId)
	{
		this.praticaId = praticaId;
	}

	public String getAmbitoPaesaggistico()
	{
		return this.ambitoPaesaggistico;
	}
	public void setAmbitoPaesaggistico(String ambitoPaesaggistico)
	{
		this.ambitoPaesaggistico = ambitoPaesaggistico;
	}

	public String getFigureAmbito()
	{
		return this.figureAmbito;
	}
	public void setFigureAmbito(String figureAmbito)
	{
		this.figureAmbito = figureAmbito;
	}

	public Boolean getRicadeTerrArt103Co56()
	{
		return this.ricadeTerrArt103Co56;
	}
	public void setRicadeTerrArt103Co56(Boolean ricadeTerrArt103Co56)
	{
		this.ricadeTerrArt103Co56 = ricadeTerrArt103Co56;
	}

	public Boolean getRicadeTerrArt142Co2()
	{
		return this.ricadeTerrArt142Co2;
	}
	public void setRicadeTerrArt142Co2(Boolean ricadeTerrArt142Co2)
	{
		this.ricadeTerrArt142Co2 = ricadeTerrArt142Co2;
	}

}
