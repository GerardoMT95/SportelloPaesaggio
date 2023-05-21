package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.search;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.RubricaSearchDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.template.Trasmissioni_WhereClauseGenerator;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dbMapping.Common_Ente;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dbMapping.Common_Ente_Attribute;
import it.eng.tz.puglia.util.string.StringUtil;

public class RubricaIstituzionaleSearch extends Trasmissioni_WhereClauseGenerator<Common_Ente_Attribute>
{
	private static final long serialVersionUID = 1950139697991161830L;
	
	private Integer enteId;
	private Integer applicazioneId;
	private String  nome;			// non è in tabella (campo "descrizione" di ENTE)
	private String  pec;
	private String  mail;
	private String  pecORmail;		// cerca le corrispondenze nel campo PEC e nel campo MAIL
	
	
	public RubricaIstituzionaleSearch() { }
	
	public RubricaIstituzionaleSearch(RubricaSearchDTO rubrica) { 
		this.nome = rubrica.getNome();
		this.pecORmail = rubrica.getEmail();
		this.setPage(rubrica.getPage());
		this.setLimit(rubrica.getLimit());
		if (StringUtil.isNotEmpty(rubrica.getDirezione())) 
			this.setDirezione(Direction.valueOf(rubrica.getDirezione()));
	}

	
	public Integer getEnteId() {
		return enteId;
	}
	public void setEnteId(Integer enteId) {
		this.enteId = enteId;
	}
	public Integer getApplicazioneId() {
		return applicazioneId;
	}
	public void setApplicazioneId(Integer applicazioneId) {
		this.applicazioneId = applicazioneId;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getPec() {
		return pec;
	}
	public void setPec(String pec) {
		this.pec = pec;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPecORmail() {
		return pecORmail;
	}
	public void setPecORmail(String pecORmail) {
		this.pecORmail = pecORmail;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((applicazioneId == null) ? 0 : applicazioneId.hashCode());
		result = prime * result + ((enteId == null) ? 0 : enteId.hashCode());
		result = prime * result + ((mail == null) ? 0 : mail.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((pec == null) ? 0 : pec.hashCode());
		result = prime * result + ((pecORmail == null) ? 0 : pecORmail.hashCode());
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
		RubricaIstituzionaleSearch other = (RubricaIstituzionaleSearch) obj;
		if (applicazioneId == null) {
			if (other.applicazioneId != null)
				return false;
		} else if (!applicazioneId.equals(other.applicazioneId))
			return false;
		if (enteId == null) {
			if (other.enteId != null)
				return false;
		} else if (!enteId.equals(other.enteId))
			return false;
		if (mail == null) {
			if (other.mail != null)
				return false;
		} else if (!mail.equals(other.mail))
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
		if (pecORmail == null) {
			if (other.pecORmail != null)
				return false;
		} else if (!pecORmail.equals(other.pecORmail))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RubricaIstituzionaleSearch [enteId=" + enteId + ", applicazioneId=" + applicazioneId + ", nome=" + nome
				+ ", pec=" + pec + ", mail=" + mail + ", pecORmail=" + pecORmail + "]";
	}
	
	@Override
	protected void generateWhereCriteria() {

		String separator = " where ";
		if(enteId != null)
		{
			sql.append(separator)
			   .append(Common_Ente_Attribute.ente_id.getCompleteName())
			   .append(" = :")
			   .append(Common_Ente_Attribute.ente_id.columnName());
			parameters.put(Common_Ente_Attribute.ente_id.columnName(), enteId);
			separator = " and ";
		}
		if(applicazioneId != null)
		{
			sql.append(separator)
			   .append(Common_Ente_Attribute.applicazione_id.getCompleteName())
			   .append(" = :")
			   .append(Common_Ente_Attribute.applicazione_id.columnName());
			parameters.put(Common_Ente_Attribute.applicazione_id.columnName(), applicazioneId);
			separator = " and ";
		}
		if(!StringUtil.isEmpty(pec))
		{
			sql.append(separator)
			   .append(Common_Ente_Attribute.pec.getCompleteName())
			   .append(" ILIKE :")
			   .append(Common_Ente_Attribute.mail.columnName());	// sarebbe "pec" ma c'è il problema dei parameters che si resettano!
			parameters.put(Common_Ente_Attribute.mail.columnName(), StringUtil.convertLike(pec)); // sarebbe "pec" ma c'è il problema dei parameters che si resettano!
			separator = " and ";
		}
		if(!StringUtil.isEmpty(mail))
		{
			sql.append(separator)
			   .append(Common_Ente_Attribute.mail.getCompleteName())
			   .append(" ILIKE :")
			   .append(Common_Ente_Attribute.mail.columnName());
			parameters.put(Common_Ente_Attribute.mail.columnName(), StringUtil.convertLike(mail));
			separator = " and ";
		}
//		if(!StringUtil.isEmpty(pecORmail))
//		{
//			sql.append(separator)
//			   .append(" ( ")
//			   .append(Common_Ente_Attribute.pec .getCompleteName()).append(" ILIKE :pec_mail")
//			   .append(" or ")
//			   .append(Common_Ente_Attribute.mail.getCompleteName()).append(" ILIKE :pec_mail")
//			   .append(" ) ");
//			parameters.put("pec_mail", StringUtil.convertLike(pecORmail));
//			separator = " and ";
//		}
		if(!StringUtil.isEmpty(nome))
		{
			sql.append(separator)
			   .append(Common_Ente.descrizione.getCompleteName())
			   .append(" ILIKE :")
			   .append(Common_Ente.descrizione.columnName());
			parameters.put(Common_Ente.descrizione.columnName(), StringUtil.convertLike(nome));
			separator = " and ";
		}
	}
}