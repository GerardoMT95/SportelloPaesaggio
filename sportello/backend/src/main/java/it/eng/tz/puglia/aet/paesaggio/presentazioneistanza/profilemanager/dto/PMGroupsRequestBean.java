package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto;

import java.io.Serializable;
import java.util.List;

/**
 * DTO chiesto in input da groups/save e groups/update del PM
 * @author msantoro
 *
 */
public class PMGroupsRequestBean implements Serializable
{
	private static final long serialVersionUID = -6308261068266851680L;
	
	private String id;
	private String codiceGruppo;
	private String nomeGruppo;
	private String descrizioneGruppo;
	private String idApplicazione;
	private List<String> ruoli;
	
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getCodiceGruppo()
	{
		return codiceGruppo;
	}
	public void setCodiceGruppo(String codiceGruppo)
	{
		this.codiceGruppo = codiceGruppo;
	}
	
	public String getNomeGruppo()
	{
		return nomeGruppo;
	}
	public void setNomeGruppo(String nomeGruppo)
	{
		this.nomeGruppo = nomeGruppo;
	}
	
	public String getDescrizioneGruppo()
	{
		return descrizioneGruppo;
	}
	public void setDescrizioneGruppo(String descrizioneGruppo)
	{
		this.descrizioneGruppo = descrizioneGruppo;
	}
	
	public String getIdApplicazione()
	{
		return idApplicazione;
	}
	public void setIdApplicazione(String idApplicazione)
	{
		this.idApplicazione = idApplicazione;
	}
	
	public List<String> getRuoli()
	{
		return ruoli;
	}
	public void setRuoli(List<String> ruoli)
	{
		this.ruoli = ruoli;
	}
	
	
}
