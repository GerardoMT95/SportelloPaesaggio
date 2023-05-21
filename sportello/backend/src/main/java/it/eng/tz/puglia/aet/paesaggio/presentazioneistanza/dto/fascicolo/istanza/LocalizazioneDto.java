package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istanza;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.LocalizzazioneInterventoDTO;

public class LocalizazioneDto implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3458509810375884829L;

	@NotNull(message="Deve essere presente almeno un comune nel tab localizzazione")
	@Size(min=1, message="Deve essere presente almeno un comune nel tab localizzazione")
	private List<LocalizzazioneInterventoDTO> localizzazioneComuni;
	
	private List<AllegatiDTO> allegaShapeFile;

	public List<LocalizzazioneInterventoDTO> getLocalizzazioneComuni()
	{
		return localizzazioneComuni;
	}
	public void setLocalizzazioneComuni(List<LocalizzazioneInterventoDTO> localizzazioneComuni)
	{
		this.localizzazioneComuni = localizzazioneComuni;
	}
	public List<AllegatiDTO> getAllegaShapeFile() {
		return allegaShapeFile;
	}
	public void setAllegaShapeFile(List<AllegatiDTO> allegaShapeFile) {
		this.allegaShapeFile = allegaShapeFile;
	}

}
