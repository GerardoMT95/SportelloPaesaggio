package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import java.util.List;
import java.util.UUID;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.allegati
 */
public class AllegatiSearch extends BaseSearchRequestBean
{

	private static final long serialVersionUID = 2437744709L;
	// COLUMN id
	private String id;
	// COLUMN nome_file
	private String nomeFile;
	// COLUMN formato_file
	private String formatoFile;
	// COLUMN data_caricamento
	private String dataCaricamento;
	// COLUMN path_cms
	private String pathCms;
	// COLUMN id_cms
	private String idCms;
	// COLUMN pratica_id
	private String praticaId;
	// COLUMN descrizione
	private String descrizione;
	//COLUMN integrazione_id
	private Integer integrazioneId;
	//COLUMN deleted
	private Boolean deleted;

	private Integer tipoFile;
	
	private String visibilita;
	
	private List<Integer> tipologie;
	
	private Long idSeduta;
	
	//COLUMN id_allegato_pre_protocollazione
	private UUID idAllegatoPreProtocollazione;
	
		
	public UUID getIdAllegatoPreProtocollazione() {
		return idAllegatoPreProtocollazione;
	}

	public void setIdAllegatoPreProtocollazione(UUID idAllegatoPreProtocollazione) {
		this.idAllegatoPreProtocollazione = idAllegatoPreProtocollazione;
	}

	public AllegatiSearch()
	{
		deleted = false;
	}

	public String getId()
	{
		return this.id;
	}
	public void setId(String id)
	{
		this.id = id;
	}

	public String getNomeFile()
	{
		return this.nomeFile;
	}
	public void setNomeFile(String nomeFile)
	{
		this.nomeFile = nomeFile;
	}

	public String getFormatoFile()
	{
		return this.formatoFile;
	}
	public void setFormatoFile(String formatoFile)
	{
		this.formatoFile = formatoFile;
	}

	public String getDataCaricamento()
	{
		return this.dataCaricamento;
	}
	public void setDataCaricamento(String dataCaricamento)
	{
		this.dataCaricamento = dataCaricamento;
	}

	public String getPathCms()
	{
		return this.pathCms;
	}
	public void setPathCms(String pathCms)
	{
		this.pathCms = pathCms;
	}

	public String getIdCms()
	{
		return this.idCms;
	}
	public void setIdCms(String idCms)
	{
		this.idCms = idCms;
	}

	public String getPraticaId()
	{
		return this.praticaId;
	}
	public void setPraticaId(String praticaId)
	{
		this.praticaId = praticaId;
	}

	public String getDescrizione()
	{
		return this.descrizione;
	}
	public void setDescrizione(String descrizione)
	{
		this.descrizione = descrizione;
	}

	public Integer getTipoFile()
	{
		return tipoFile;
	}
	public void setTipoFile(Integer tipoFile)
	{
		this.tipoFile = tipoFile;
	}
	
	public List<Integer> getTipologie()
	{
		return tipologie;
	}
	public void setTipologie(List<Integer> tipologie)
	{
		this.tipologie = tipologie;
	}
	
	public Integer getIntegrazioneId()
	{
		return integrazioneId;
	}
	public void setIntegrazioneId(Integer integrazioneId)
	{
		this.integrazioneId = integrazioneId;
	}
	
	public Boolean getDeleted()
	{
		return deleted;
	}
	public void setDeleted(Boolean deleted)
	{
		this.deleted = deleted;
	}

	public Long getIdSeduta()
	{
		return idSeduta;
	}
	public void setIdSeduta(Long idSeduta)
	{
		this.idSeduta = idSeduta;
	}

	public String getVisibilita() {
		return visibilita;
	}

	public void setVisibilita(String visibilita) {
		this.visibilita = visibilita;
	}

}
