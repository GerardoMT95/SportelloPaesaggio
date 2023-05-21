package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class UtenteInsertDTO implements Serializable
{
	private static final long serialVersionUID = -6126374541014496621L;
	private String id;
	private String idApplicazione;
	private List<SimpleGruppiEntityDTO> gruppiInput;

	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getIdApplicazione()
	{
		return idApplicazione;
	}
	public void setIdApplicazione(String idApplicazione)
	{
		this.idApplicazione = idApplicazione;
	}
	
	public List<SimpleGruppiEntityDTO> getGruppiInput()
	{
		return gruppiInput;
	}
	public void setGruppiInput(List<SimpleGruppiEntityDTO> gruppiInput)
	{
		this.gruppiInput = gruppiInput;
	}
	
	public String getIdGruppoInput()
	{
		if(gruppiInput == null || gruppiInput.isEmpty())
			return null;
		return gruppiInput.get(0).getId();
	}
	public void setIdGruppoInput(String id)
	{
		SimpleGruppiEntityDTO t = new SimpleGruppiEntityDTO();
		t.setId(id);
		this.gruppiInput = Collections.singletonList(t);
	}
	

	private class SimpleGruppiEntityDTO implements Serializable
	{
		private static final long serialVersionUID = 5703503045346263937L;
		private String id;

		public String getId()
		{
			return id;
		}
		public void setId(String id)
		{
			this.id = id;
		}
	}
}
