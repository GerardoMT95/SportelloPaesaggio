package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.search;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.RubricaSearchDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.template.Trasmissioni_WhereClauseGenerator;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dbMapping.Common_Paesaggio_Organizzazione_Rubrica;
import it.eng.tz.puglia.util.string.StringUtil;

public class RubricaEnteSearch extends Trasmissioni_WhereClauseGenerator<Common_Paesaggio_Organizzazione_Rubrica>
{
	private static final long serialVersionUID = 1950139697991161830L;
	
	private Long id;
	private Integer paesaggioOrganizzazioneId;
	private String codiceApplicazione;
	private String nome;
	private String pec;
	private String mail;
	private String pecORmail;					// cerca le corrispondenze nel campo PEC e nel campo MAIL
	
	
	public RubricaEnteSearch() {}
	
	public RubricaEnteSearch(RubricaSearchDTO rubrica) { 
		this.nome = rubrica.getNome();
		this.pecORmail = rubrica.getEmail();
		this.setPage(rubrica.getPage());
		this.setLimit(rubrica.getLimit());
		if (StringUtil.isNotEmpty(rubrica.getDirezione())) 
			this.setDirezione(Direction.valueOf(rubrica.getDirezione()));
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getPaesaggioOrganizzazioneId() {
		return paesaggioOrganizzazioneId;
	}
	public void setPaesaggioOrganizzazioneId(Integer paesaggioOrganizzazioneId) {
		this.paesaggioOrganizzazioneId = paesaggioOrganizzazioneId;
	}
	public String getCodiceApplicazione() {
		return codiceApplicazione;
	}
	public void setCodiceApplicazione(String codiceApplicazione) {
		this.codiceApplicazione = codiceApplicazione;
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
		result = prime * result + ((codiceApplicazione == null) ? 0 : codiceApplicazione.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((paesaggioOrganizzazioneId == null) ? 0 : paesaggioOrganizzazioneId.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((pec == null) ? 0 : pec.hashCode());
		result = prime * result + ((mail == null) ? 0 : mail.hashCode());
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
		RubricaEnteSearch other = (RubricaEnteSearch) obj;
		if (codiceApplicazione == null) {
			if (other.codiceApplicazione != null)
				return false;
		} else if (!codiceApplicazione.equals(other.codiceApplicazione))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		if (paesaggioOrganizzazioneId == null) {
			if (other.paesaggioOrganizzazioneId != null)
				return false;
		} else if (!paesaggioOrganizzazioneId.equals(other.paesaggioOrganizzazioneId))
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
		return "RubricaEnteSearch [id=" + id + ", paesaggioOrganizzazioneId=" + paesaggioOrganizzazioneId
				+ ", codiceApplicazione=" + codiceApplicazione + ", nome=" + nome + ", pec=" + pec + ", mail=" + mail
				+ ", pecORmail=" + pecORmail + "]";
	}


	@Override
	protected void generateWhereCriteria() {

		String separator = " where ";
		if(id != null)
		{
			sql.append(separator)
			   .append(Common_Paesaggio_Organizzazione_Rubrica.id.getCompleteName())
			   .append(" = :")
			   .append(Common_Paesaggio_Organizzazione_Rubrica.id.columnName());
			parameters.put(Common_Paesaggio_Organizzazione_Rubrica.id.columnName(), id);
			separator = " and ";
		}
		if(paesaggioOrganizzazioneId != null)
		{
			sql.append(separator)
			   .append(Common_Paesaggio_Organizzazione_Rubrica.paesaggio_organizzazione_id.getCompleteName())
			   .append(" = :")
			   .append(Common_Paesaggio_Organizzazione_Rubrica.paesaggio_organizzazione_id.columnName());
			parameters.put(Common_Paesaggio_Organizzazione_Rubrica.paesaggio_organizzazione_id.columnName(), paesaggioOrganizzazioneId);
			separator = " and ";
		}
		if(!StringUtil.isEmpty(codiceApplicazione))
		{
			sql.append(separator)
			   .append(Common_Paesaggio_Organizzazione_Rubrica.codice_applicazione.getCompleteName())
			   .append(" = :")
			   .append(Common_Paesaggio_Organizzazione_Rubrica.codice_applicazione.columnName());
			parameters.put(Common_Paesaggio_Organizzazione_Rubrica.codice_applicazione.columnName(), codiceApplicazione);
			separator = " and ";
		}
		if(!StringUtil.isEmpty(nome))
		{
			sql.append(separator)
			   .append(Common_Paesaggio_Organizzazione_Rubrica.nome.getCompleteName())
			   .append(" ILIKE :")
			   .append(Common_Paesaggio_Organizzazione_Rubrica.nome.columnName());
			parameters.put(Common_Paesaggio_Organizzazione_Rubrica.nome.columnName(), StringUtil.convertLike(nome));
			separator = " and ";
		}
		if(!StringUtil.isEmpty(pec))
		{
			sql.append(separator)
			   .append(Common_Paesaggio_Organizzazione_Rubrica.pec.getCompleteName())
			   .append(" ILIKE :")
			   .append(Common_Paesaggio_Organizzazione_Rubrica.mail.columnName());	// sarebbe "pec" ma c'è il problema dei parameters che si resettano!
			parameters.put(Common_Paesaggio_Organizzazione_Rubrica.mail.columnName(), StringUtil.convertLike(pec)); // sarebbe "pec" ma c'è il problema dei parameters che si resettano!
			separator = " and ";
		}
		if(!StringUtil.isEmpty(mail))
		{
			sql.append(separator)
			   .append(Common_Paesaggio_Organizzazione_Rubrica.mail.getCompleteName())
			   .append(" ILIKE :")
			   .append(Common_Paesaggio_Organizzazione_Rubrica.mail.columnName());
			parameters.put(Common_Paesaggio_Organizzazione_Rubrica.mail.columnName(), StringUtil.convertLike(mail));
			separator = " and ";
		}
//		if(!StringUtil.isEmpty(pecORmail))
//		{
//			sql.append(separator)
//			   .append(" ( ")
//			   .append(Common_Paesaggio_Organizzazione_Rubrica.pec .getCompleteName()).append(" ILIKE :pec_mail")
//			   .append(" or ")
//			   .append(Common_Paesaggio_Organizzazione_Rubrica.mail.getCompleteName()).append(" ILIKE :pec_mail")
//			   .append(" ) ");
//			parameters.put("pec_mail", StringUtil.convertLike(pecORmail));
//			separator = " and ";
//		}
	}
}