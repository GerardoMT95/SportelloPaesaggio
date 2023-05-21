package it.eng.tz.puglia.autpae.search;

import java.util.Date;

import it.eng.tz.puglia.autpae.dbMapping.Allegato;
import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;
import it.eng.tz.puglia.autpae.utility.DateUtil;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Search for table public.allegato
 */
public class AllegatoSearch extends WhereClauseGenerator<Allegato> {

	private static final long serialVersionUID = 6693642738L;
	
	private Long id;
	private String nome;
	private String titolo;
	private String descrizione;
	private String mimeType;		  // inutile
	private Date dataCaricamento;     // inutile
	private Date dataCaricamentoDa;
	private Date dataCaricamentoA;
	private String contenuto;		  // inutile
	private String checksum;		  // inutile
	private Boolean deleted;
	private Integer dimensione;		  // inutile
	private String numeroProtocollo;              // che comprende sia 'in' sia 'out'
//	private String dataProtocollo;    // inutile  // che comprende sia 'in' sia 'out'  
	private String utenteInserimento; 			  // che comprende sia 'nome+cognome' sia 'username'
	
	
	public AllegatoSearch() { }

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public Date getDataCaricamento() {
		return dataCaricamento;
	}

	public void setDataCaricamento(Date dataCaricamento) {
		this.dataCaricamento = dataCaricamento;
	}

	public Date getDataCaricamentoDa() {
		return dataCaricamentoDa;
	}

	public void setDataCaricamentoDa(Date dataCaricamentoDa) {
		this.dataCaricamentoDa = dataCaricamentoDa;
	}

	public Date getDataCaricamentoA() {
		return dataCaricamentoA;
	}

	public void setDataCaricamentoA(Date dataCaricamentoA) {
		this.dataCaricamentoA = dataCaricamentoA;
	}

	public String getContenuto() {
		return contenuto;
	}

	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Integer getDimensione() {
		return dimensione;
	}

	public void setDimensione(Integer dimensione) {
		this.dimensione = dimensione;
	}

	public String getUtenteInserimento() {
		return utenteInserimento;
	}

	public void setUtenteInserimento(String utenteInserimento) {
		this.utenteInserimento = utenteInserimento;
	}

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((checksum == null) ? 0 : checksum.hashCode());
		result = prime * result + ((contenuto == null) ? 0 : contenuto.hashCode());
		result = prime * result + ((dataCaricamento == null) ? 0 : dataCaricamento.hashCode());
		result = prime * result + ((dataCaricamentoA == null) ? 0 : dataCaricamentoA.hashCode());
		result = prime * result + ((dataCaricamentoDa == null) ? 0 : dataCaricamentoDa.hashCode());
		result = prime * result + ((deleted == null) ? 0 : deleted.hashCode());
		result = prime * result + ((descrizione == null) ? 0 : descrizione.hashCode());
		result = prime * result + ((dimensione == null) ? 0 : dimensione.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mimeType == null) ? 0 : mimeType.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((numeroProtocollo == null) ? 0 : numeroProtocollo.hashCode());
		result = prime * result + ((titolo == null) ? 0 : titolo.hashCode());
		result = prime * result + ((utenteInserimento == null) ? 0 : utenteInserimento.hashCode());
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
		AllegatoSearch other = (AllegatoSearch) obj;
		if (checksum == null) {
			if (other.checksum != null)
				return false;
		} else if (!checksum.equals(other.checksum))
			return false;
		if (contenuto == null) {
			if (other.contenuto != null)
				return false;
		} else if (!contenuto.equals(other.contenuto))
			return false;
		if (dataCaricamento == null) {
			if (other.dataCaricamento != null)
				return false;
		} else if (!dataCaricamento.equals(other.dataCaricamento))
			return false;
		if (dataCaricamentoA == null) {
			if (other.dataCaricamentoA != null)
				return false;
		} else if (!dataCaricamentoA.equals(other.dataCaricamentoA))
			return false;
		if (dataCaricamentoDa == null) {
			if (other.dataCaricamentoDa != null)
				return false;
		} else if (!dataCaricamentoDa.equals(other.dataCaricamentoDa))
			return false;
		if (deleted == null) {
			if (other.deleted != null)
				return false;
		} else if (!deleted.equals(other.deleted))
			return false;
		if (descrizione == null) {
			if (other.descrizione != null)
				return false;
		} else if (!descrizione.equals(other.descrizione))
			return false;
		if (dimensione == null) {
			if (other.dimensione != null)
				return false;
		} else if (!dimensione.equals(other.dimensione))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mimeType == null) {
			if (other.mimeType != null)
				return false;
		} else if (!mimeType.equals(other.mimeType))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (numeroProtocollo == null) {
			if (other.numeroProtocollo != null)
				return false;
		} else if (!numeroProtocollo.equals(other.numeroProtocollo))
			return false;
		if (titolo == null) {
			if (other.titolo != null)
				return false;
		} else if (!titolo.equals(other.titolo))
			return false;
		if (utenteInserimento == null) {
			if (other.utenteInserimento != null)
				return false;
		} else if (!utenteInserimento.equals(other.utenteInserimento))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AllegatoSearch [id=" + id + ", nome=" + nome + ", titolo=" + titolo + ", descrizione=" + descrizione
				+ ", mimeType=" + mimeType + ", dataCaricamento=" + dataCaricamento + ", dataCaricamentoDa="
				+ dataCaricamentoDa + ", dataCaricamentoA=" + dataCaricamentoA + ", contenuto=" + contenuto
				+ ", checksum=" + checksum + ", deleted=" + deleted + ", dimensione=" + dimensione
				+ ", numeroProtocollo=" + numeroProtocollo + ", utenteInserimento=" + utenteInserimento + "]";
	}

	@Override
	protected void generateWhereCriteria() {

		String separator = " where ";
		if(id != null)
		{
			sql.append(separator)
			   .append(Allegato.id.getCompleteName())
			   .append(" = :")
			   .append(Allegato.id.columnName());
			parameters.put(Allegato.id.columnName(), id);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(nome))
		{
			sql.append(separator)
			   .append(Allegato.nome.getCompleteName())
			   .append(" = :")
			   .append(Allegato.nome.columnName());
			parameters.put(Allegato.nome.columnName(), nome);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(titolo))
		{
			sql.append(separator)
			   .append(Allegato.titolo.getCompleteName())
			   .append(" = :")
			   .append(Allegato.titolo.columnName());
			parameters.put(Allegato.titolo.columnName(), titolo);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(descrizione))
		{
			sql.append(separator)
			   .append(Allegato.descrizione.getCompleteName())
			   .append(" = :")
			   .append(Allegato.descrizione.columnName());
			parameters.put(Allegato.descrizione.columnName(), descrizione);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(mimeType))
		{
			sql.append(separator)
			   .append(Allegato.mime_type.getCompleteName())
			   .append(" = :")
			   .append(Allegato.mime_type.columnName());
			parameters.put(Allegato.mime_type.columnName(), mimeType);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(numeroProtocollo))
		{
			sql.append(separator)
			   .append(" ( ")
			   .append(Allegato.numero_protocollo_in.getCompleteName())
			   .append(" = :")
			   .append(Allegato.numero_protocollo_in.columnName())
			   .append(" OR ")
			   .append(Allegato.numero_protocollo_out.getCompleteName())
			   .append(" = :")
			   .append(Allegato.numero_protocollo_in.columnName())
			   .append(" ) ");
			parameters.put(Allegato.numero_protocollo_in.columnName(), numeroProtocollo);
			separator = " and ";
		}
//		if(dataCaricamento != null)    // poco utile
//		{
//			sql.append(separator)
//			   .append(Allegato.data_caricamento.getCompleteName())
//			   .append(" = :")
//			   .append(Allegato.data_caricamento.columnName());
//			parameters.put(Allegato.data_caricamento.columnName(), dataCaricamento);
//			separator = " and ";
//		}
		if(dataCaricamentoDa != null && dataCaricamentoA != null)	
		{
			sql.append(separator)
				.append(Allegato.data_caricamento.getCompleteName())
				.append(" BETWEEN :")
				.append(Allegato.data_caricamento.columnName())
				.append("_DA")
				.append(" AND :")
				.append(Allegato.data_caricamento.columnName())
				.append("_A");
			parameters.put(Allegato.data_caricamento.columnName() + "_DA", dataCaricamentoDa);
			parameters.put(Allegato.data_caricamento.columnName() + "_A" , DateUtil.endOfDay(dataCaricamentoA));
			separator = " and ";
		}
		else if(dataCaricamentoDa != null)	
		{
			sql.append(separator)
				.append(Allegato.data_caricamento.getCompleteName())
				.append(" >= :")
				.append(Allegato.data_caricamento.columnName());
			parameters.put(Allegato.data_caricamento.columnName(), dataCaricamentoDa);
			separator = " and ";
		}
		else if(dataCaricamentoA != null)
		{
			sql.append(separator)
				.append(Allegato.data_caricamento.getCompleteName())
				.append(" <= :")
				.append(Allegato.data_caricamento.columnName());
			parameters.put(Allegato.data_caricamento.columnName(), DateUtil.endOfDay(dataCaricamentoA));
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(contenuto))
		{
			sql.append(separator)
			   .append(Allegato.contenuto.getCompleteName())
			   .append(" = :")
			   .append(Allegato.contenuto.columnName());
			parameters.put(Allegato.contenuto.columnName(), contenuto);
			separator = " and ";
		}
//		if(StringUtil.isNotEmpty(checksum))
//		{
//			sql.append(separator)
//			   .append(Allegato.checksum.getCompleteName())
//			   .append(" = :")
//			   .append(Allegato.checksum.columnName());
//			parameters.put(Allegato.checksum.columnName(), checksum);
//			separator = " and ";
//		}
		if(deleted != null)
		{
			sql.append(separator)
			   .append(Allegato.deleted.getCompleteName())
			   .append(" = :")
			   .append(Allegato.deleted.columnName());
			parameters.put(Allegato.deleted.columnName(), deleted);
			separator = " and ";
		}
		if(dimensione != null && dimensione==0)
		{
			sql.append(separator)
			   .append(Allegato.dimensione.getCompleteName())
			   .append(" = :")
			   .append(Allegato.dimensione.columnName());
			parameters.put(Allegato.dimensione.columnName(), dimensione);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(utenteInserimento))
		{
			sql.append(separator)
			   .append(" ( ")
			   .append(Allegato.utente_inserimento.getCompleteName())
			   .append(" = :")
			   .append(Allegato.utente_inserimento.columnName())
			   .append(" OR ")
			   .append(Allegato.username_inserimento.getCompleteName())
			   .append(" = :")
			   .append(Allegato.utente_inserimento.columnName())
			   .append(" ) ");
			parameters.put(Allegato.utente_inserimento.columnName(), utenteInserimento);
			separator = " and ";
		}
//		if(StringUtil.isNotEmpty(inputProtocolNumber))
//		{
//			sql.append(separator)
//			   .append(Allegato.input_protocol_number.getCompleteName())
//			   .append(" = :")
//			   .append(Allegato.input_protocol_number.columnName());
//			parameters.put(Allegato.input_protocol_number.columnName(), inputProtocolNumber);
//			separator = " and ";
//		}
//		if(inputProtocolDate != null)   // poco utile
//		{
//			sql.append(separator)
//			   .append(Allegato.input_protocol_date.getCompleteName())
//			   .append(" = :")
//			   .append(Allegato.input_protocol_date.columnName());
//			parameters.put(Allegato.input_protocol_date.columnName(), inputProtocolDate);
//			separator = " and ";
//		}
//		if(inputProtocolDateDa != null && inputProtocolDateA != null)	
//		{
//			sql.append(separator)
//				.append(Allegato.input_protocol_date.getCompleteName())
//				.append(" BETWEEN :")
//				.append(Allegato.input_protocol_date.columnName())
//				.append("_DA")
//				.append(" AND :")
//				.append(Allegato.input_protocol_date.columnName())
//				.append("_A");
//			parameters.put(Allegato.input_protocol_date.columnName() + "_DA", inputProtocolDateDa);
//			parameters.put(Allegato.input_protocol_date.columnName() + "_A" , inputProtocolDateA);
//			separator = " and ";
//		}
//		else if(inputProtocolDateDa != null)	
//		{
//			sql.append(separator)
//				.append(Allegato.input_protocol_date.getCompleteName())
//				.append(" >= :")
//				.append(Allegato.input_protocol_date.columnName());
//			parameters.put(Allegato.input_protocol_date.columnName(), inputProtocolDateDa);
//			separator = " and ";
//		}
//		else if(inputProtocolDateA != null)
//		{
//			sql.append(separator)
//				.append(Allegato.input_protocol_date.getCompleteName())
//				.append(" <= :")
//				.append(Allegato.input_protocol_date.columnName());
//			parameters.put(Allegato.input_protocol_date.columnName(), inputProtocolDateA);
//			separator = " and ";
//		}
//		if(StringUtil.isNotEmpty(outputProtocolNumber))
//		{
//			sql.append(separator)
//			   .append(Allegato.output_protocol_number.getCompleteName())
//			   .append(" = :")
//			   .append(Allegato.output_protocol_number.columnName());
//			parameters.put(Allegato.output_protocol_number.columnName(), outputProtocolNumber);
//			separator = " and ";
//		}
//		if(outputProtocolDate != null)  // poco utile
//		{
//			sql.append(separator)
//			   .append(Allegato.output_protocol_date.getCompleteName())
//			   .append(" = :")
//			   .append(Allegato.output_protocol_date.columnName());
//			parameters.put(Allegato.output_protocol_date.columnName(), outputProtocolDate);
//			separator = " and ";
//		}
//		if(outputProtocolDateDa != null && outputProtocolDateA != null)	
//		{
//			sql.append(separator)
//				.append(Allegato.output_protocol_date.getCompleteName())
//				.append(" BETWEEN :")
//				.append(Allegato.output_protocol_date.columnName())
//				.append("_DA")
//				.append(" AND :")
//				.append(Allegato.output_protocol_date.columnName())
//				.append("_A");
//			parameters.put(Allegato.output_protocol_date.columnName() + "_DA", outputProtocolDateDa);
//			parameters.put(Allegato.output_protocol_date.columnName() + "_A" , outputProtocolDateA);
//			separator = " and ";
//		}
//		else if(outputProtocolDateDa != null)	
//		{
//			sql.append(separator)
//				.append(Allegato.output_protocol_date.getCompleteName())
//				.append(" >= :")
//				.append(Allegato.output_protocol_date.columnName());
//			parameters.put(Allegato.output_protocol_date.columnName(), outputProtocolDateDa);
//			separator = " and ";
//		}
//		else if(outputProtocolDateA != null)
//		{
//			sql.append(separator)
//				.append(Allegato.output_protocol_date.getCompleteName())
//				.append(" <= :")
//				.append(Allegato.output_protocol_date.columnName());
//			parameters.put(Allegato.output_protocol_date.columnName(), outputProtocolDateA);
//			separator = " and ";
//		}
	}
	
}
