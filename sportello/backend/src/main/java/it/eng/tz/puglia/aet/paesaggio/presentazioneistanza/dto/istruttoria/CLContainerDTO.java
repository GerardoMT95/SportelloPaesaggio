package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria;

import java.io.Serializable;
import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainNumberValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;

public class CLContainerDTO implements Serializable
{
	private static final long serialVersionUID = -5446310125607345706L;
	private List<PlainStringValueLabel> users;
	private List<PlainNumberValueLabel> enti;
	
	public List<PlainStringValueLabel> getUsers()
	{
		return users;
	}
	public void setUsers(List<PlainStringValueLabel> users)
	{
		this.users = users;
	}
	public List<PlainNumberValueLabel> getEnti()
	{
		return enti;
	}
	public void setEnti(List<PlainNumberValueLabel> enti)
	{
		this.enti = enti;
	}
	
	
}
