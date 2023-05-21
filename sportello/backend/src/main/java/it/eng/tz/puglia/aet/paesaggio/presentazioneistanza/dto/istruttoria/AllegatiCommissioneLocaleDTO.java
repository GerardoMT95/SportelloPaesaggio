package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria;

import java.util.List;
import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoAllegatoSeduta;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;

public class AllegatiCommissioneLocaleDTO extends AllegatiDTO
{
	private static final long serialVersionUID = -4871269647629804601L;
	
	private Long idSeduta;
	private List<UUID> praticheAssociate;
	private TipoAllegatoSeduta tipoAllegato;
	
	public AllegatiCommissioneLocaleDTO()
	{
		super();
	}

	public AllegatiCommissioneLocaleDTO(AllegatiDTO other)
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
	
	public Long getIdSeduta()
	{
		return idSeduta;
	}
	public void setIdSeduta(Long idSeduta)
	{
		this.idSeduta = idSeduta;
	}

	public List<UUID> getPraticheAssociate()
	{
		return praticheAssociate;
	}
	public void setPraticheAssociate(List<UUID> praticheAssociate)
	{
		this.praticheAssociate = praticheAssociate;
	}

	public TipoAllegatoSeduta getTipoAllegato()
	{
		return tipoAllegato;
	}
	public void setTipoAllegato(TipoAllegatoSeduta tipoAllegato)
	{
		this.tipoAllegato = tipoAllegato;
	}
}
