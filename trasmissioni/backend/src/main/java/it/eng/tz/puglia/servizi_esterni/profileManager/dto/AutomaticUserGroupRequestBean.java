package it.eng.tz.puglia.servizi_esterni.profileManager.dto;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * DTO chiesto in input da userGroups/automaticUserGroup
 * @author msantoro
 *
 */
@SuppressWarnings("unused")
public class AutomaticUserGroupRequestBean implements Serializable
{
	private static final long serialVersionUID = -6633227472858357716L;
	
	private String usernameUtente;
	private List<SimpleApp> applicazioni;
	private List<SimpleGroup> gruppi;
	private List<SimpleRole> ruoli;
	private boolean associaRuoliTotale = true;
	
	public String getUsernameUtente()
	{
		return usernameUtente;
	}
	public void setUsernameUtente(String usernameUtente)
	{
		this.usernameUtente = usernameUtente;
	}

	public List<SimpleApp> getApplicazioni()
	{
		return applicazioni;
	}
	public void setApplicazioni(List<SimpleApp> applicazioni)
	{
		this.applicazioni = applicazioni;
	}

	public List<SimpleGroup> getGruppi()
	{
		return gruppi;
	}
	public void setGruppi(List<SimpleGroup> gruppi)
	{
		this.gruppi = gruppi;
	}

	public List<SimpleRole> getRuoli()
	{
		return ruoli;
	}
	public void setRuoli(List<SimpleRole> ruoli)
	{
		this.ruoli = ruoli;
	}
	
	public boolean isAssociaRuoliTotale()
	{
		return associaRuoliTotale;
	}
	public void setAssociaRuoliTotale(boolean associaRuoliTotale)
	{
		this.associaRuoliTotale = associaRuoliTotale;
	}
	public void setApplicazioni(String codiceApplicazione)
	{
		SimpleApp x = new SimpleApp();
		x.setCodiceApplicazione(codiceApplicazione);
		setApplicazioni(Collections.singletonList(x));
	}
	
	public void setGruppi(String codiceGruppo)
	{
		SimpleGroup x = new SimpleGroup();
		x.setCodiceGruppo(codiceGruppo);
		setGruppi(Collections.singletonList(x));
	}
	
	public void  setRuoli(String codiceRuolo)
	{
		SimpleRole x = new SimpleRole();
		x.setCodiceRuolo(codiceRuolo);
		setRuoli(Collections.singletonList(x));
	}

	private class SimpleApp implements Serializable
	{
		private static final long serialVersionUID = 2479656705003725394L;
		private String codiceApplicazione;

		public String getCodiceApplicazione()
		{
			return codiceApplicazione;
		}
		public void setCodiceApplicazione(String codiceApplicazione)
		{
			this.codiceApplicazione = codiceApplicazione;
		}
	}
	
	private class SimpleGroup implements Serializable
	{
		private static final long serialVersionUID = 637338564674916814L;
		private String codiceGruppo;

		public String getCodiceGruppo()
		{
			return codiceGruppo;
		}
		public void setCodiceGruppo(String codiceGruppo)
		{
			this.codiceGruppo = codiceGruppo;
		}
	}
	
	private class SimpleRole implements Serializable
	{
		private static final long serialVersionUID = 8769553072807453956L;
		private String codiceRuolo;

		public String getCodiceRuolo()
		{
			return codiceRuolo;
		}
		public void setCodiceRuolo(String codiceRuolo)
		{
			this.codiceRuolo = codiceRuolo;
		}
	}
}
