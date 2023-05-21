package it.eng.tz.puglia.autpae.dto.BE_to_FE;

import it.eng.tz.puglia.autpae.search.generic.Paging;
import it.eng.tz.puglia.servizi_esterni.remote.dto.RubricaEnteDTO;
import it.eng.tz.puglia.servizi_esterni.remote.dto.RubricaIstituzionaleDTO;

public class RubricaSearchDTO extends Paging {

	private static final long serialVersionUID = 1L;

	private Long    id;
	private String  nome;
	private String  email;
	private boolean pec;
//	private int		page;		// per la Search
//	private int		limit;		// per la Search
	private String  direzione;  // per la Search
	
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((direzione == null) ? 0 : direzione.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
//		result = prime * result + page;
//		result = prime * result + limit;
		result = prime * result + (pec ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RubricaSearchDTO other = (RubricaSearchDTO) obj;
		if (direzione == null) {
			if (other.direzione != null)
				return false;
		} else if (!direzione.equals(other.direzione))
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
//		if (page != other.page)
//			return false;
//		if (limit != other.limit)
//			return false;
		if (pec != other.pec)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RubricaSearchDTO [id=" + id + ", nome=" + nome + ", email=" + email 
				+ ", pec=" + pec + ", direzione=" + direzione + "]";
	}
	
	public void clean() {
		if (this.nome	  !=null && this.nome	  .trim().equals("")) this.nome		=null;
		if (this.email	  !=null && this.email	  .trim().equals("")) this.email	=null;
		if (this.direzione!=null && this.direzione.trim().equals("")) this.direzione=null;
	}
	
}