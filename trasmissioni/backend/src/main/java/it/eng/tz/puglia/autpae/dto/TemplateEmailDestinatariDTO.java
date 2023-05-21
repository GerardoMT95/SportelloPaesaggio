package it.eng.tz.puglia.autpae.dto;

import java.util.ArrayList;
import java.util.List;

import it.eng.tz.puglia.autpae.entity.TemplateEmailDTO;

/**
 * DTO for Template Email+Destinatari
 */
public class TemplateEmailDestinatariDTO extends TemplateEmailDTO {

	private static final long serialVersionUID = 6693642738L;
	
	private List<DestinatarioTemplateDTO> destinatari;
	
	public TemplateEmailDestinatariDTO() {
		this.destinatari = new ArrayList<DestinatarioTemplateDTO>();
	}
	
	public TemplateEmailDestinatariDTO(TemplateEmailDTO templateEmail) {
		
		this.codice=templateEmail.getCodice();
		this.oggetto=templateEmail.getOggetto();
		this.testo=templateEmail.getTesto();
		this.descrizione=templateEmail.getDescrizione();
		this.nome=templateEmail.getNome();
		this.destinatari = new ArrayList<DestinatarioTemplateDTO>();
	}
	

	public List<DestinatarioTemplateDTO> getDestinatari() {
		return destinatari;
	}

	public void setDestinatari(List<DestinatarioTemplateDTO> destinatari) {
		this.destinatari = destinatari;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((destinatari == null) ? 0 : destinatari.hashCode());
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
		TemplateEmailDestinatariDTO other = (TemplateEmailDestinatariDTO) obj;
		if (destinatari == null) {
			if (other.destinatari != null)
				return false;
		} else if (!destinatari.equals(other.destinatari))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TemplateEmailDestinatariDTO [destinatari=" + destinatari + "]";
	}
}
