package it.eng.tz.puglia.autpae.search;

import java.util.Date;

import it.eng.tz.puglia.autpae.dbMapping.Ricevuta;
import it.eng.tz.puglia.autpae.enumeratori.TipoErrore;
import it.eng.tz.puglia.autpae.enumeratori.TipoRicevuta;
import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;
import it.eng.tz.puglia.autpae.utility.DateUtil;

/**
 * Search for table public.ricevuta
 */
public class RicevutaSearch extends WhereClauseGenerator<Ricevuta> {

	private static final long serialVersionUID = 854634266887767564L;

	private Long id;
	private Long idCorrispondenza;
	private Long idDestinatario;
	private TipoRicevuta tipoRicevuta;
	private TipoErrore errore;
	private String descrizioneErrore;
	private String idCmsEml;			// inutile
	private String idCmsDatiCert;		// inutile
	private String idCmsSmime;			// inutile
	private Date data;					// inutile
	private Date dataDa;
	private Date dataA;
	
	
	public RicevutaSearch() { }
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdCorrispondenza() {
		return idCorrispondenza;
	}

	public void setIdCorrispondenza(Long idCorrispondenza) {
		this.idCorrispondenza = idCorrispondenza;
	}

	public Long getIdDestinatario() {
		return idDestinatario;
	}

	public void setIdDestinatario(Long idDestinatario) {
		this.idDestinatario = idDestinatario;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public TipoRicevuta getTipoRicevuta() {
		return tipoRicevuta;
	}

	public void setTipoRicevuta(TipoRicevuta tipoRicevuta) {
		this.tipoRicevuta = tipoRicevuta;
	}

	public TipoErrore getErrore() {
		return errore;
	}

	public void setErrore(TipoErrore errore) {
		this.errore = errore;
	}

	public String getDescrizioneErrore() {
		return descrizioneErrore;
	}

	public void setDescrizioneErrore(String descrizioneErrore) {
		this.descrizioneErrore = descrizioneErrore;
	}

	public String getIdCmsEml() {
		return idCmsEml;
	}

	public void setIdCmsEml(String idCmsEml) {
		this.idCmsEml = idCmsEml;
	}

	public String getIdCmsDatiCert() {
		return idCmsDatiCert;
	}

	public void setIdCmsDatiCert(String idCmsDatiCert) {
		this.idCmsDatiCert = idCmsDatiCert;
	}

	public String getIdCmsSmime() {
		return idCmsSmime;
	}

	public void setIdCmsSmime(String idCmsSmime) {
		this.idCmsSmime = idCmsSmime;
	}

	public Date getDataDa() {
		return dataDa;
	}

	public void setDataDa(Date dataDa) {
		this.dataDa = dataDa;
	}

	public Date getDataA() {
		return dataA;
	}

	public void setDataA(Date dataA) {
		this.dataA = dataA;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((dataA == null) ? 0 : dataA.hashCode());
		result = prime * result + ((dataDa == null) ? 0 : dataDa.hashCode());
		result = prime * result + ((descrizioneErrore == null) ? 0 : descrizioneErrore.hashCode());
		result = prime * result + ((errore == null) ? 0 : errore.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idCmsDatiCert == null) ? 0 : idCmsDatiCert.hashCode());
		result = prime * result + ((idCmsEml == null) ? 0 : idCmsEml.hashCode());
		result = prime * result + ((idCmsSmime == null) ? 0 : idCmsSmime.hashCode());
		result = prime * result + ((idCorrispondenza == null) ? 0 : idCorrispondenza.hashCode());
		result = prime * result + ((idDestinatario == null) ? 0 : idDestinatario.hashCode());
		result = prime * result + ((tipoRicevuta == null) ? 0 : tipoRicevuta.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		RicevutaSearch other = (RicevutaSearch) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (dataA == null) {
			if (other.dataA != null)
				return false;
		} else if (!dataA.equals(other.dataA))
			return false;
		if (dataDa == null) {
			if (other.dataDa != null)
				return false;
		} else if (!dataDa.equals(other.dataDa))
			return false;
		if (descrizioneErrore == null) {
			if (other.descrizioneErrore != null)
				return false;
		} else if (!descrizioneErrore.equals(other.descrizioneErrore))
			return false;
		if (errore != other.errore)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idCmsDatiCert == null) {
			if (other.idCmsDatiCert != null)
				return false;
		} else if (!idCmsDatiCert.equals(other.idCmsDatiCert))
			return false;
		if (idCmsEml == null) {
			if (other.idCmsEml != null)
				return false;
		} else if (!idCmsEml.equals(other.idCmsEml))
			return false;
		if (idCmsSmime == null) {
			if (other.idCmsSmime != null)
				return false;
		} else if (!idCmsSmime.equals(other.idCmsSmime))
			return false;
		if (idCorrispondenza == null) {
			if (other.idCorrispondenza != null)
				return false;
		} else if (!idCorrispondenza.equals(other.idCorrispondenza))
			return false;
		if (idDestinatario == null) {
			if (other.idDestinatario != null)
				return false;
		} else if (!idDestinatario.equals(other.idDestinatario))
			return false;
		if (tipoRicevuta != other.tipoRicevuta)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "RicevutaSearch [id=" + id + ", idCorrispondenza=" + idCorrispondenza + ", idDestinatario="
				+ idDestinatario + ", tipoRicevuta=" + tipoRicevuta + ", errore=" + errore + ", descrizioneErrore="
				+ descrizioneErrore + ", idCmsEml=" + idCmsEml + ", idCmsDatiCert=" + idCmsDatiCert + ", idCmsSmime="
				+ idCmsSmime + ", data=" + data + ", dataDa=" + dataDa + ", dataA=" + dataA + "]";
	}


	@Override
	protected void generateWhereCriteria() {
		String separator = " where ";
		if(id != null)
		{
			sql.append(separator)
			.append(Ricevuta.id.getCompleteName())
			.append(" = :")
			.append(Ricevuta.id.columnName());
			parameters.put(Ricevuta.id.columnName(), id);
			separator = " and ";
		}
		if(idCorrispondenza != null)
		{
			sql.append(separator)
			.append(Ricevuta.id_corrispondenza.getCompleteName())
			.append(" = :")
			.append(Ricevuta.id_corrispondenza.columnName());
			parameters.put(Ricevuta.id_corrispondenza.columnName(), idCorrispondenza);
			separator = " and ";
		}
		if(idDestinatario != null)
		{
			sql.append(separator)
			.append(Ricevuta.id_destinatario.getCompleteName())
			.append(" = :")
			.append(Ricevuta.id_destinatario.columnName());
			parameters.put(Ricevuta.id_destinatario.columnName(), idDestinatario);
			separator = " and ";
		}
		if(tipoRicevuta != null)
		{
			sql.append(separator)
			.append(Ricevuta.tipo_ricevuta.getCompleteName())
			.append(" = :")
			.append(Ricevuta.tipo_ricevuta.columnName());
			parameters.put(Ricevuta.tipo_ricevuta.columnName(), tipoRicevuta);
			separator = " and ";
		}
		if(errore != null)
		{
			sql.append(separator)
			.append(Ricevuta.errore.getCompleteName())
			.append(" = :")
			.append(Ricevuta.errore.columnName());
			parameters.put(Ricevuta.errore.columnName(), errore);
			separator = " and ";
		}
		if(descrizioneErrore != null)
		{
			sql.append(separator)
			.append(Ricevuta.descrizione_errore.getCompleteName())
			.append(" = :")
			.append(Ricevuta.descrizione_errore.columnName());
			parameters.put(Ricevuta.descrizione_errore.columnName(), descrizioneErrore);
			separator = " and ";
		}
//		if(idCmsEml != null)
//		{
//			sql.append(separator)
//			.append(Ricevuta.id_cms_eml.getCompleteName())
//			.append(" = :")
//			.append(Ricevuta.id_cms_eml.columnName());
//			parameters.put(Ricevuta.id_cms_eml.columnName(), idCmsEml);
//			separator = " and ";
//		}
		if(idCmsDatiCert != null)
		{
			sql.append(separator)
			.append(Ricevuta.id_cms_dati_cert.getCompleteName())
			.append(" = :")
			.append(Ricevuta.id_cms_dati_cert.columnName());
			parameters.put(Ricevuta.id_cms_dati_cert.columnName(), idCmsDatiCert);
			separator = " and ";
		}
//		if(idCmsSmime != null)
//		{
//			sql.append(separator)
//			.append(Ricevuta.id_cms_smime.getCompleteName())
//			.append(" = :")
//			.append(Ricevuta.id_cms_smime.columnName());
//			parameters.put(Ricevuta.id_cms_smime.columnName(), idCmsSmime);
//			separator = " and ";
//		}
//		if(data != null)
//		{
//			sql.append(separator)
//			.append(Ricevuta.data.getCompleteName())
//			.append(" = :")
//			.append(Ricevuta.data.columnName());
//			parameters.put(Ricevuta.data.columnName(), data);
//			separator = " and ";
//		}
		if(dataDa != null && dataA != null)
		{
			sql.append(separator)
			   .append(Ricevuta.data.getCompleteName())
			   .append(" between :dataDa and :dataA");
			parameters.put("dataDa", dataDa);
			parameters.put("dataA", DateUtil.endOfDay(dataA));
			separator = " and ";
		}
		else if(dataDa != null)
		{
			sql.append(separator)
			   .append(Ricevuta.data.getCompleteName())
			   .append(" >= :")
			   .append(Ricevuta.data.columnName());
			parameters.put(Ricevuta.data.columnName(), dataDa);
			separator = " and ";
		}
		else if(dataA != null)
		{
			sql.append(separator)
			   .append(Ricevuta.data.getCompleteName())
			   .append(" <= :")
			   .append(Ricevuta.data.columnName());
			parameters.put(Ricevuta.data.columnName(), DateUtil.endOfDay(dataA));
			separator = " and ";
		}
	}
}
