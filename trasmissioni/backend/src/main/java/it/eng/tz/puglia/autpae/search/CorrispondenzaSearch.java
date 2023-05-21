package it.eng.tz.puglia.autpae.search;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.eng.tz.puglia.autpae.dbMapping.Corrispondenza;
import it.eng.tz.puglia.autpae.dbMapping.Destinatario;
import it.eng.tz.puglia.autpae.dbMapping.FascicoloCorrispondenza;
import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;
import it.eng.tz.puglia.autpae.utility.DateUtil;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Search for table public.corrispondenza
 */
public class CorrispondenzaSearch extends WhereClauseGenerator<Corrispondenza> {

	private static final long serialVersionUID = 6693642738L;
	
	private Long idFascicolo;
	private Long id; 		  		       // idCorrispondenza
	private String messageId;

	private String mittenteEnte;
	private String mittente; 			   // mittenteDenominazione  	// comprende sia il campo 'denominazione' sia il campo 'username' del 'Mittente'
	@JsonIgnore
	private String usernameForBozza;	 	//impostato per fare in modo che le bozze le vede solo l'utente che le ha create.
	private String mittenteEmail;          // inutile
	private Date dataInvio;    		       // inutile (data esatta)
	private Date dataInvioDa;
	private Date dataInvioA;
	private String text;
	private String oggetto;
	private String destinatario;           // comprende sia il campo 'denominazione' sia il campo 'email' del 'Destinatario'
	private Boolean stato;
	private Boolean comunicazione;
	private Boolean bozza;
	
	public CorrispondenzaSearch() { }

	public String getUsernameForBozza() {
		return usernameForBozza;
	}

	public void setUsernameForBozza(String usernameForBozza) {
		this.usernameForBozza = usernameForBozza;
	}

	public Long getIdFascicolo() {
		return idFascicolo;
	}
	public void setIdFascicolo(Long idFascicolo) {
		this.idFascicolo = idFascicolo;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDataInvio() {
		return dataInvio;
	}
	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getDataInvioDa() {
		return dataInvioDa;
	}
	public void setDataInvioDa(Date dataInvioDa) {
		this.dataInvioDa = dataInvioDa;
	}
	public Date getDataInvioA() {
		return dataInvioA;
	}
	public void setDataInvioA(Date dataInvioA) {
		this.dataInvioA = dataInvioA;
	}
	public String getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	public Boolean getBozza() {
		return bozza;
	}
	public void setBozza(Boolean bozza) {
		this.bozza = bozza;
	}
	public String getMittenteEnte() {
		return mittenteEnte;
	}
	public void setMittenteEnte(String mittenteEnte) {
		this.mittenteEnte = mittenteEnte;
	}
	public String getMittente() {
		return mittente;
	}
	public void setMittente(String mittente) {
		this.mittente = mittente;
	}
	public String getMittenteEmail() {
		return mittenteEmail;
	}
	public void setMittenteEmail(String mittenteEmail) {
		this.mittenteEmail = mittenteEmail;
	}
	public Boolean getStato() {
		return stato;
	}
	public void setStato(Boolean stato) {
		this.stato = stato;
	}
	public Boolean getComunicazione() {
		return comunicazione;
	}
	public void setComunicazione(Boolean comunicazione) {
		this.comunicazione = comunicazione;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}


	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bozza == null) ? 0 : bozza.hashCode());
		result = prime * result + ((comunicazione == null) ? 0 : comunicazione.hashCode());
		result = prime * result + ((dataInvio == null) ? 0 : dataInvio.hashCode());
		result = prime * result + ((dataInvioA == null) ? 0 : dataInvioA.hashCode());
		result = prime * result + ((dataInvioDa == null) ? 0 : dataInvioDa.hashCode());
		result = prime * result + ((destinatario == null) ? 0 : destinatario.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idFascicolo == null) ? 0 : idFascicolo.hashCode());
		result = prime * result + ((messageId == null) ? 0 : messageId.hashCode());
		result = prime * result + ((mittente == null) ? 0 : mittente.hashCode());
		result = prime * result + ((mittenteEmail == null) ? 0 : mittenteEmail.hashCode());
		result = prime * result + ((mittenteEnte == null) ? 0 : mittenteEnte.hashCode());
		result = prime * result + ((usernameForBozza == null) ? 0 : usernameForBozza.hashCode());
		result = prime * result + ((oggetto == null) ? 0 : oggetto.hashCode());
		result = prime * result + ((stato == null) ? 0 : stato.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		CorrispondenzaSearch other = (CorrispondenzaSearch) obj;
		if (bozza == null) {
			if (other.bozza != null)
				return false;
		} else if (!bozza.equals(other.bozza))
			return false;
		if (comunicazione == null) {
			if (other.comunicazione != null)
				return false;
		} else if (!comunicazione.equals(other.comunicazione))
			return false;
		if (dataInvio == null) {
			if (other.dataInvio != null)
				return false;
		} else if (!dataInvio.equals(other.dataInvio))
			return false;
		if (dataInvioA == null) {
			if (other.dataInvioA != null)
				return false;
		} else if (!dataInvioA.equals(other.dataInvioA))
			return false;
		if (dataInvioDa == null) {
			if (other.dataInvioDa != null)
				return false;
		} else if (!dataInvioDa.equals(other.dataInvioDa))
			return false;
		if (destinatario == null) {
			if (other.destinatario != null)
				return false;
		} else if (!destinatario.equals(other.destinatario))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idFascicolo == null) {
			if (other.idFascicolo != null)
				return false;
		} else if (!idFascicolo.equals(other.idFascicolo))
			return false;
		if (messageId == null) {
			if (other.messageId != null)
				return false;
		} else if (!messageId.equals(other.messageId))
			return false;
		if (mittente == null) {
			if (other.mittente != null)
				return false;
		} else if (!mittente.equals(other.mittente))
			return false;
		if (mittenteEmail == null) {
			if (other.mittenteEmail != null)
				return false;
		} else if (!mittenteEmail.equals(other.mittenteEmail))
			return false;
		if (mittenteEnte == null) {
			if (other.mittenteEnte != null)
				return false;
		} else if (!mittenteEnte.equals(other.mittenteEnte))
			return false;
		if (usernameForBozza == null) {
			if (other.usernameForBozza != null)
				return false;
		} else if (!usernameForBozza.equals(other.usernameForBozza))
			return false;
		if (oggetto == null) {
			if (other.oggetto != null)
				return false;
		} else if (!oggetto.equals(other.oggetto))
			return false;
		if (stato == null) {
			if (other.stato != null)
				return false;
		} else if (!stato.equals(other.stato))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "CorrispondenzaSearch [idFascicolo=" + idFascicolo + ", id=" + id + ", messageId=" + messageId
				+ ", mittenteEnte=" + mittenteEnte + ", mittente=" + mittente + ", usernameForBozza=" + usernameForBozza
				+ ", mittenteEmail=" + mittenteEmail + ", dataInvio=" + dataInvio + ", dataInvioDa=" + dataInvioDa
				+ ", dataInvioA=" + dataInvioA + ", text=" + text + ", oggetto=" + oggetto + ", destinatario="
				+ destinatario + ", stato=" + stato + ", comunicazione=" + comunicazione + ", bozza=" + bozza + "]";
	}


	@Override
	protected void generateWhereCriteria() {

		String separator = " WHERE ";
		if(id != null)
		{
			sql.append(separator)
			   .append(Corrispondenza.id.getCompleteName())
			   .append(" = :")
			   .append(Corrispondenza.id.columnName());
			parameters.put(Corrispondenza.id.columnName(), id);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(messageId))
		{
			sql.append(separator)
			   .append(Corrispondenza.message_id.getCompleteName())
			   .append(" = :")
			   .append(Corrispondenza.message_id.columnName());
			parameters.put(Corrispondenza.message_id.columnName(), messageId);
			separator = " and ";
		}
		if(idFascicolo != null)
		{
			sql.append(separator)
			   .append(FascicoloCorrispondenza.id_fascicolo.getCompleteName())
			   .append(" = :")
			   .append(FascicoloCorrispondenza.id_fascicolo.columnName());
			parameters.put(FascicoloCorrispondenza.id_fascicolo.columnName(), idFascicolo);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(mittenteEnte))
		{
			sql.append(separator)
			   .append(Corrispondenza.mittente_ente.getCompleteName())
			   .append(" = :")
			   .append(Corrispondenza.mittente_ente.columnName());
			parameters.put(Corrispondenza.mittente_ente.columnName(), mittenteEnte);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(mittente))
		{
			sql.append(separator)
			   .append(" ( ")
			   .append(Corrispondenza.mittente_denominazione.getCompleteName())
			   .append(" ILIKE :")
			   .append(Corrispondenza.mittente_denominazione.columnName())
			   .append(" OR ")
			   .append(Corrispondenza.mittente_username.getCompleteName())
			   .append(" ILIKE :")
			   .append(Corrispondenza.mittente_denominazione.columnName())
			   .append(" ) ");
			parameters.put(Corrispondenza.mittente_denominazione.columnName(), StringUtil.convertLike(mittente));
			separator = " and ";
		}
//		if(dataInvio != null)  // poco utile
//		{
//			sql.append(separator)
//			   .append(Corrispondenza.data_invio.getCompleteName())
//			   .append(" = :")
//			   .append(Corrispondenza.data_invio.columnName());
//			parameters.put(Corrispondenza.data_invio.columnName(), dataInvio);
//			separator = " and ";
//		}
		if(dataInvioDa != null && dataInvioA != null)	
		{
			sql.append(separator)
				.append(Corrispondenza.data_invio.getCompleteName())
				.append(" BETWEEN :")
				.append(Corrispondenza.data_invio.columnName())
				.append("_DA")
				.append(" AND :")
				.append(Corrispondenza.data_invio.columnName())
				.append("_A");
			parameters.put(Corrispondenza.data_invio.columnName() + "_DA", dataInvioDa);
			parameters.put(Corrispondenza.data_invio.columnName() + "_A" , DateUtil.endOfDay(dataInvioA));
			separator = " and ";
		}
		else if(dataInvioDa != null)	
		{
			sql.append(separator)
				.append(Corrispondenza.data_invio.getCompleteName())
				.append(" >= :")
				.append(Corrispondenza.data_invio.columnName());
			parameters.put(Corrispondenza.data_invio.columnName(), dataInvioDa);
			separator = " and ";
		}
		else if(dataInvioA != null)
		{
			sql.append(separator)
				.append(Corrispondenza.data_invio.getCompleteName())
				.append(" <= :")
				.append(Corrispondenza.data_invio.columnName());
			parameters.put(Corrispondenza.data_invio.columnName(), DateUtil.endOfDay(dataInvioA));
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(text))
		{
			sql.append(separator)
			   .append(Corrispondenza.text.getCompleteName())
			   .append(" = :")
			   .append(Corrispondenza.text.columnName());
			parameters.put(Corrispondenza.text.columnName(), text);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(oggetto))
		{
			sql.append(separator)
			   .append(Corrispondenza.oggetto.getCompleteName())
			   .append(" ILIKE :")
			   .append(Corrispondenza.oggetto.columnName());
			parameters.put(Corrispondenza.oggetto.columnName(), StringUtil.convertLike(oggetto));
			separator = " and ";
		}
		if(bozza!=null)
		{
			sql.append(separator)
			   .append(Corrispondenza.bozza.getCompleteName())
			   .append(" = :")
			   .append(Corrispondenza.bozza.columnName());
			parameters.put(Corrispondenza.bozza.columnName(), bozza);
			separator = " and ";
		}
		if(comunicazione!=null)
		{
			sql.append(separator)
			   .append(Corrispondenza.comunicazione.getCompleteName())
			   .append(" = :")
			   .append(Corrispondenza.comunicazione.columnName());
			parameters.put(Corrispondenza.comunicazione.columnName(), comunicazione);
			separator = " and ";
		}
		if(stato!=null)
		{
			sql.append(separator)
			   .append(Corrispondenza.stato.getCompleteName())
			   .append(" = :")
			   .append(Corrispondenza.stato.columnName());
			parameters.put(Corrispondenza.stato.columnName(), stato);
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(destinatario))
		{
			sql.append(separator)
			   .append(" ( ")   
			   .append(" select count(*) FROM ")
	           .append(Destinatario.getTableName())
	           .append(" where ") 
	           .append(Corrispondenza.id.getCompleteName())
	           .append(" = ")
	           .append(Destinatario.id_corrispondenza.getCompleteName());
			sql.append(separator)
			   .append(" ( ")
			   .append(Destinatario.denominazione.getCompleteName())
			   .append(" ILIKE :")
			   .append(Destinatario.getTableName())
			   .append(" OR ")
			   .append(Destinatario.email.getCompleteName())
			   .append(" ILIKE :")
			   .append(Destinatario.getTableName())
			   .append(" ) ")
			   .append(") > 0");
			parameters.put(Destinatario.getTableName(), StringUtil.convertLike(destinatario));
			separator = " and ";
		}
		if(StringUtil.isNotEmpty(usernameForBozza))
		{
			sql.append(separator)
				.append("(")
			   .append(Corrispondenza.mittente_username.getCompleteName())
			   .append(" = ")
			   .append(" :usernameForBozza ")
			   .append(" OR ")
			   .append(Corrispondenza.bozza.getCompleteName())
			   .append(" = false ")
			   .append(" OR ")
			   .append(Corrispondenza.comunicazione.getCompleteName())
			   .append(" = false ")
			   .append(" )");
			parameters.put("usernameForBozza", usernameForBozza);
			separator = " and ";
		}
	}
	
}
