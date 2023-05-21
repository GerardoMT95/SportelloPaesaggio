package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.template.Trasmissioni_WhereClauseGenerator;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dbMapping.Common_Ente;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dbMapping.Common_Ente_Attribute;
import it.eng.tz.puglia.util.string.StringUtil;

public class RubricaAdminSearchDTO extends Trasmissioni_WhereClauseGenerator<Common_Ente_Attribute> {

	private static final long serialVersionUID = 1L;

	private Integer applicazioneId;
	private Integer id;
	private String  nome;
	private String  email;
	private String  pec;
	private String  tipoEnte;
	
	
	public RubricaAdminSearchDTO() { }
	
	public void clean() {
		if (this.email!=null && this.email.trim().equals("")) this.email=null;
		if (this.pec  !=null && this.pec  .trim().equals("")) this.pec  =null;
	    this.setColonna(null);
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPec() {
		return pec;
	}
	public void setPec(String pec) {
		this.pec = pec;
	}
	public String getTipoEnte() {
		return tipoEnte;
	}
	public void setTipoEnte(String tipoEnte) {
		this.tipoEnte = tipoEnte;
	}
	public Integer getApplicazioneId() {
		return applicazioneId;
	}
	public void setApplicazioneId(Integer applicazioneId) {
		this.applicazioneId = applicazioneId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((applicazioneId == null) ? 0 : applicazioneId.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((pec == null) ? 0 : pec.hashCode());
		result = prime * result + ((tipoEnte == null) ? 0 : tipoEnte.hashCode());
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
		RubricaAdminSearchDTO other = (RubricaAdminSearchDTO) obj;
		if (applicazioneId == null) {
			if (other.applicazioneId != null)
				return false;
		} else if (!applicazioneId.equals(other.applicazioneId))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (pec == null) {
			if (other.pec != null)
				return false;
		} else if (!pec.equals(other.pec))
			return false;
		if (tipoEnte == null) {
			if (other.tipoEnte != null)
				return false;
		} else if (!tipoEnte.equals(other.tipoEnte))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RubricaAdminDTO [applicazioneId=" + applicazioneId + ", id=" + id + ", nome=" + nome 
				+ ", email=" + email + ", pec=" + pec + ", tipoEnte=" + tipoEnte + "]";
	}

	@Override
	protected void generateWhereCriteria() {
		String separatore = " where ";
		if(id != null)
		{
			sql.append(separatore)
			   .append(Common_Ente_Attribute.ente_id.getCompleteName())
			   .append(" = :")
			   .append(Common_Ente_Attribute.ente_id.columnName());
			parameters.put(Common_Ente_Attribute.ente_id.columnName(), id);
			separatore = " and ";
		}
		if(applicazioneId != null)
		{
			sql.append(separatore)
			   .append(Common_Ente_Attribute.applicazione_id.getCompleteName())
			   .append(" = :")
			   .append(Common_Ente_Attribute.applicazione_id.columnName());
			parameters.put(Common_Ente_Attribute.applicazione_id.columnName(), applicazioneId);
			separatore = " and ";
		}
		if(!StringUtil.isEmpty(email))
		{
			sql.append(separatore)
			   .append(Common_Ente_Attribute.mail.getCompleteName())
			   .append(" ILIKE :")
			   .append(Common_Ente_Attribute.mail.columnName());
			parameters.put(Common_Ente_Attribute.mail.columnName(), StringUtil.convertLike(email));
			separatore = " and ";
		}
		if(!StringUtil.isEmpty(pec))
		{
			sql.append(separatore)
			   .append(Common_Ente_Attribute.pec.getCompleteName())
			   .append(" ILIKE :")
			   .append(Common_Ente_Attribute.pec.columnName());
			parameters.put(Common_Ente_Attribute.pec.columnName(), StringUtil.convertLike(pec));
			separatore = " and ";
		}
		if(!StringUtil.isEmpty(nome))
		{
			sql.append(separatore)
			   .append(Common_Ente.descrizione.getCompleteName())
			   .append(" ILIKE :")
			   .append(Common_Ente.descrizione.columnName());
			parameters.put(Common_Ente.descrizione.columnName(), StringUtil.convertLike(nome));
			separatore = " and ";
		}
		if(!StringUtil.isEmpty(tipoEnte))
		{
			sql.append(separatore)
			   .append(Common_Ente.tipo.getCompleteName())
			   .append(" = :")
			   .append(Common_Ente.tipo.columnName());
			parameters.put(Common_Ente.tipo.columnName(), tipoEnte);
			separatore = " and ";
		}
	}
	
}