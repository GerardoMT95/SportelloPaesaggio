package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.dto;

import java.io.Serializable;
import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TemplateDestinatarioDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TemplateEmailDTO;

public class TemplateEmailDestinatariDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TemplateEmailDTO template;
	private List<TemplateDestinatarioDTO> destinatari;
	private List<PlainStringValueLabel> placeholders;
	

	public List<PlainStringValueLabel> getPlaceholders() {
		return placeholders;
	}

	public void setPlaceholders(List<PlainStringValueLabel> placeholders) {
		this.placeholders = placeholders;
	}

	public List<TemplateDestinatarioDTO> getDestinatari() {
		return destinatari;
	}

	public void setDestinatari(List<TemplateDestinatarioDTO> destinatari) {
		this.destinatari = destinatari;
	}

	public TemplateEmailDTO getTemplate() {
		return template;
	}

	public void setTemplate(TemplateEmailDTO template) {
		this.template = template;
	}

	public TemplateEmailDestinatariDto(TemplateEmailDTO template, List<TemplateDestinatarioDTO> destinatari) {
		super();
		this.template = template;
		this.destinatari = destinatari;
	}

	public TemplateEmailDestinatariDto() {}
		
	
	
}
