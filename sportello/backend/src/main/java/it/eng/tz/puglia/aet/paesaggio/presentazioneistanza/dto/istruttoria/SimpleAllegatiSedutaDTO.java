package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria;

import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.SimplePraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoAllegatoSeduta;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;

public class SimpleAllegatiSedutaDTO extends AllegatiDTO
{
	private static final long serialVersionUID = -1232182962250835005L;
	private List<SimplePraticaDTO> pratiche;
	private TipoAllegatoSeduta tipoAllegato;
	private Long idSeduta;

	public SimpleAllegatiSedutaDTO()
	{
		super();
	}
	public SimpleAllegatiSedutaDTO(AllegatiDTO other)
	{
		setId(other.getId());
		setChecksum(other.getChecksum());
		setDataCaricamento(other.getDataCaricamento());
		setDeleted(other.getDeleted());
		setDescrizione(other.getDescrizione());
		setFormatoFile(other.getFormatoFile());
		setIdCms(other.getIdCms());
		setPathCms(other.getPathCms());
		setNomeFile(other.getNomeFile());
		setSize(other.getSize());
		setTipiContenuto(other.getTipiContenuto());
	}
	
	public List<SimplePraticaDTO> getPratiche()
	{
		return pratiche;
	}
	public void setPratiche(List<SimplePraticaDTO> pratiche)
	{
		this.pratiche = pratiche;
	}
	
	public TipoAllegatoSeduta getTipoAllegato()
	{
		return tipoAllegato;
	}
	public void setTipoAllegato(TipoAllegatoSeduta tipoAllegato)
	{
		this.tipoAllegato = tipoAllegato;
	}
	
	public Long getIdSeduta()
	{
		return idSeduta;
	}
	public void setIdSeduta(Long idSeduta)
	{
		this.idSeduta = idSeduta;
	}
}
