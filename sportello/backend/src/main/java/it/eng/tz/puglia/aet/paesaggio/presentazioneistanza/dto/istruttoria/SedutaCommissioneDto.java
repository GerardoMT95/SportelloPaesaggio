package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.SedutaCommissioneDTO;

public class SedutaCommissioneDto extends SedutaCommissioneDTO
{
	private static final long serialVersionUID = -174381835194772538L;
	private Integer nFascicoli;
	private Integer nFascicoliEsaminati;
	
	public SedutaCommissioneDto()
	{
		super();
	}
	
	public SedutaCommissioneDto(SedutaCommissioneDTO other)
	{
		setId(other.getId());
		setIdOrganizzazione(other.getIdOrganizzazione());
		setDataCreazione(other.getDataCreazione());
		setDataSeduta(other.getDataSeduta());
		setNomeSeduta(other.getNomeSeduta());
		setPubblica(other.getPubblica());
		setStato(other.getStato());
		setUsernameUtenteCreazione(other.getUsernameUtenteCreazione());
	}
	
	public Integer getnFascicoli()
	{
		return nFascicoli;
	}
	public void setnFascicoli(Integer nFascicoli)
	{
		this.nFascicoli = nFascicoli;
	}
	
	public Integer getnFascicoliEsaminati()
	{
		return nFascicoliEsaminati;
	}
	public void setnFascicoliEsaminati(Integer nFascicoliEsaminati)
	{
		this.nFascicoliEsaminati = nFascicoliEsaminati;
	}
	
	
}
