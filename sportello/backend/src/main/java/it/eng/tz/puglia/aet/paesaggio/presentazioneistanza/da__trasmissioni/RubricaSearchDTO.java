package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni;

import java.util.Objects;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.template.Trasmissioni_Paging;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.RubricaEnteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.RubricaIstituzionaleDTO;

public class RubricaSearchDTO extends Trasmissioni_Paging {

	private static final long serialVersionUID = 1L;

	private Long    id;
	private String  nome;
	private String  email;
	private boolean pec;
//	private int		page;		// per la Search
//	private int		limit;		// per la Search
	private String  direzione;  // per la Search
	private Boolean mailDefault;
	
	
	public RubricaSearchDTO() { }
	
	public RubricaSearchDTO(RubricaEnteDTO rubricaEnte) { 
		this.id    =  rubricaEnte.getId();
		this.nome  =  rubricaEnte.getNome();
		this.pec   = (rubricaEnte.getPec()==null) ? false : true;
		this.email = (this.pec==true) ? rubricaEnte.getPec() : rubricaEnte.getMail();
	}
	
	public RubricaSearchDTO(RubricaIstituzionaleDTO rubricaIstituzionale) { 
		this.id    =  null;
		this.nome  =  rubricaIstituzionale.getNome();
		this.pec   = (rubricaIstituzionale.getPec()==null) ? false : true;
		this.email = (this.pec==true) ? rubricaIstituzionale.getPec() : rubricaIstituzionale.getMail();
	}
	
	
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isPec() {
		return pec;
	}

	public void setPec(boolean pec) {
		this.pec = pec;
	}

//	public int getPage() {
//		return page;
//	}
//
//	public void setPage(int page) {
//		this.page = page;
//	}
//
//	public int getLimit() {
//		return limit;
//	}
//
//	public void setLimit(int limit) {
//		this.limit = limit;
//	}

	public String getDirezione() {
		return direzione;
	}

	public void setDirezione(String direzione) {
		this.direzione = direzione;
	}
	
	public Boolean getMailDefault() {
		return mailDefault;
	}

	public void setMailDefault(Boolean mailDefault) {
		this.mailDefault = mailDefault;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(direzione, email, id, mailDefault, nome, pec);
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
		RubricaSearchDTO other = (RubricaSearchDTO) obj;
		return Objects.equals(direzione, other.direzione) && Objects.equals(email, other.email)
				&& Objects.equals(id, other.id) && Objects.equals(mailDefault, other.mailDefault)
				&& Objects.equals(nome, other.nome) && pec == other.pec;
	}

	@Override
	public String toString() {
		return "RubricaSearchDTO [id=" + id + ", nome=" + nome + ", email=" + email + ", pec=" + pec + ", direzione="
				+ direzione + ", mailDefault=" + mailDefault + "]";
	}

	public void clean() {
		if (this.nome	  !=null && this.nome	  .trim().equals("")) this.nome		=null;
		if (this.email	  !=null && this.email	  .trim().equals("")) this.email	=null;
		if (this.direzione!=null && this.direzione.trim().equals("")) this.direzione=null;
	}
	
}