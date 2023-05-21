package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.util.string.StringUtil;

public class JasperAllegatoDto
{

	private String nomeAllegato; //nome del contenuto oppure in caso di tipologie singole coincide con il tipo del file
	private String nomeArchivio;//nome del file
	private String tipoDocumento; // "descrizione contenuto" nel caso di documentazione tecnica
	private String checksum;
	private String strDimensione; //es 11,3 Kb
	
	public JasperAllegatoDto()
	{
		super();
	}
	public JasperAllegatoDto(AllegatiDTO dto)
	{
		nomeAllegato = dto.getDescrizione();
		tipoDocumento = dto.getDescrizione();
		nomeArchivio = dto.getNomeFile();
		checksum = dto.getChecksum();
		strDimensione = StringUtil.humanReadableFileSize(dto.getSize());
	}
	
	public String getNomeAllegato()
	{
		return nomeAllegato;
	}
	public void setNomeAllegato(String nomeAllegato)
	{
		this.nomeAllegato = nomeAllegato;
	}
	
	public String getNomeArchivio()
	{
		return nomeArchivio;
	}
	public void setNomeArchivio(String nomeArchivio)
	{
		this.nomeArchivio = nomeArchivio;
	}

	public String getTipoDocumento()
	{
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento)
	{
		this.tipoDocumento = tipoDocumento;
	}
	public String getStrDimensione() {
		return strDimensione;
	}
	public void setStrDimensione(String strDimensione) {
		this.strDimensione = strDimensione;
	}

	/**
	 * @return the checksum
	 */
	public String getChecksum() {
		return checksum;
	}
	/**
	 * @param checksum the checksum to set
	 */
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
	

}
