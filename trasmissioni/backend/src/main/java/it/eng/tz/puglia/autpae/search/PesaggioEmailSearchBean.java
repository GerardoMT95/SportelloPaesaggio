package it.eng.tz.puglia.autpae.search;

import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.eng.tz.puglia.autpae.enumeratori.TipoOrganizzazioneSpecifica;
import it.eng.tz.puglia.autpae.enumeratori.TipoPaesaggioOrganizzazione;
import it.eng.tz.puglia.servizi_esterni.remote.dbMapping.Common_Ente;
import it.eng.tz.puglia.servizi_esterni.remote.dbMapping.Common_Paesaggio_Email;
import it.eng.tz.puglia.servizi_esterni.remote.dbMapping.Common_Paesaggio_Organizzazione;
import it.eng.tz.puglia.servizi_esterni.remote.search.PaesaggioEmailSearch;
import it.eng.tz.puglia.util.string.StringUtil;

public class PesaggioEmailSearchBean extends PaesaggioEmailSearch
{
	private static final long serialVersionUID = -2633692590925344152L;
	
	private java.util.List<TipoOrganizzazioneSpecifica> tipoOrganizzazione;
	private TipoPaesaggioOrganizzazione organizzazione;
	private String denominazioneEnte;
	private String denominazioneOrganizzazione;
	
	@JsonIgnore
	private boolean ricercaDestinatari = false;
	
	public java.util.List<TipoOrganizzazioneSpecifica> getTipoOrganizzazione()
	{
		return tipoOrganizzazione;
	}
	public void setTipoOrganizzazione(java.util.List<TipoOrganizzazioneSpecifica> tipoOrganizzazione)
	{
		this.tipoOrganizzazione = tipoOrganizzazione;
	}

	public TipoPaesaggioOrganizzazione getOrganizzazione()
	{
		return organizzazione;
	}
	public void setOrganizzazione(TipoPaesaggioOrganizzazione organizzazione)
	{
		this.organizzazione = organizzazione;
	}
	
	public String getDenominazioneEnte()
	{
		return denominazioneEnte;
	}
	public void setDenominazioneEnte(String denominazioneEnte)
	{
		this.denominazioneEnte = denominazioneEnte;
	}

	public String getDenominazioneOrganizzazione()
	{
		return denominazioneOrganizzazione;
	}
	public void setDenominazioneOrganizzazione(String denominazioneOrganizzazione)
	{
		this.denominazioneOrganizzazione = denominazioneOrganizzazione;
	}
	
	public boolean isRicercaDestinatari()
	{
	    return ricercaDestinatari;
	}
	public void setRicercaDestinatari(boolean ricercaDestinatari)
	{
	    this.ricercaDestinatari = ricercaDestinatari;
	}
	
	@Override
	protected void generateWhereCriteria() 
	{
		String separator = " where ";
		if(getId() != null)
		{
			sql.append(separator)
			   .append(Common_Paesaggio_Email.id.getCompleteName())
			   .append(" = :")
			   .append(Common_Paesaggio_Email.id.columnName());
			parameters.put(Common_Paesaggio_Email.id.columnName(), getId());
			separator = " and ";
		}
		if(!StringUtil.isEmpty(getCodiceApplicazione()))
		{
			sql.append(separator)
			   .append(Common_Paesaggio_Email.codice_applicazione.getCompleteName())
			   .append(" = :")
			   .append(Common_Paesaggio_Email.codice_applicazione.columnName());
			parameters.put(Common_Paesaggio_Email.codice_applicazione.columnName(), getCodiceApplicazione().toUpperCase());
			separator = " and ";
		}
		if(!StringUtil.isEmpty(getEmail()))
		{
			sql.append(separator)
			   .append(Common_Paesaggio_Email.email.getCompleteName())
			   .append(" ILIKE :")
			   .append(Common_Paesaggio_Email.email.columnName());
			parameters.put(Common_Paesaggio_Email.email.columnName(), StringUtil.convertLike(getEmail()));
			separator = " and ";
		}
		if(!StringUtil.isEmpty(getDenominazione()))
		{
			sql.append(separator)
			   .append(Common_Paesaggio_Email.denominazione.getCompleteName())
			   .append(" ILIKE :")
			   .append(Common_Paesaggio_Email.denominazione.columnName());
			parameters.put(Common_Paesaggio_Email.denominazione.columnName(), StringUtil.convertLike(getDenominazione()));
			separator = " and ";
		}
		if(getPec() != null)
		{
			sql.append(separator)
			   .append(Common_Paesaggio_Email.pec.getCompleteName())
			   .append(" = :")
			   .append(Common_Paesaggio_Email.pec.columnName());
			parameters.put(Common_Paesaggio_Email.pec.columnName(), getPec());
			separator = " and ";
		}
		if(!ricercaDestinatari)
		{
		    if(getOrganizzazioneId() != null && getEnteId() == null)
			{
				sql.append(separator)
				   .append(Common_Paesaggio_Email.organizzazione_id.getCompleteName())
				   .append(" = :")
				   .append(Common_Paesaggio_Email.organizzazione_id.columnName())
				   .append(" and ")
				   .append(Common_Paesaggio_Email.ente_id.getCompleteName())
				   .append(" IS NULL");
				parameters.put(Common_Paesaggio_Email.organizzazione_id.columnName(), getOrganizzazioneId());
				separator = " and ";
			}
			if(getEnteId() != null && getOrganizzazioneId() == null)
			{
				sql.append(separator)
				   .append(Common_Paesaggio_Email.ente_id.getCompleteName())
				   .append(" = :")
				   .append(Common_Paesaggio_Email.ente_id.columnName())
				   .append(" and ")
				   .append(Common_Paesaggio_Email.organizzazione_id.getCompleteName())
				   .append(" IS NULL");
				parameters.put(Common_Paesaggio_Email.ente_id.columnName(), getEnteId());
				separator = " and ";
			}
			if(getOrganizzazioneId() != null && getEnteId() != null)
			{
				sql.append(separator)
				   .append(Common_Paesaggio_Email.organizzazione_id.getCompleteName())
				   .append(" = :")
				   .append(Common_Paesaggio_Email.organizzazione_id.columnName())
				   .append(" and ")
				   .append(Common_Paesaggio_Email.ente_id.getCompleteName())
				   .append(" = :")
				   .append(Common_Paesaggio_Email.ente_id.columnName());
				parameters.put(Common_Paesaggio_Email.organizzazione_id.columnName(), getOrganizzazioneId());
				parameters.put(Common_Paesaggio_Email.ente_id.columnName(), getEnteId());
				separator = " and ";
			}
		}
		else
		{
		    if(getOrganizzazioneId() != null)
		    {
        			sql.append(separator)
        			   .append(Common_Paesaggio_Email.organizzazione_id.getCompleteName())
        			   .append(" = :")
        			   .append(Common_Paesaggio_Email.organizzazione_id.columnName());
        			parameters.put(Common_Paesaggio_Email.organizzazione_id.columnName(), getOrganizzazioneId());
        			separator = " and ";
		    }
		    if(getEnteId() != null)
		    {
        			sql.append(separator)
        			   .append(Common_Paesaggio_Email.ente_id.getCompleteName())
        			   .append(" = :")
        			   .append(Common_Paesaggio_Email.ente_id.columnName());
        			parameters.put(Common_Paesaggio_Email.ente_id.columnName(), getEnteId());
        			separator = " and ";
		    }
			
		}
		if(StringUtil.isNotBlank(denominazioneEnte))
		{
			sql.append(separator)
			   .append(Common_Ente.descrizione.getCompleteName())
			   .append(" like :")
			   .append(Common_Ente.descrizione.columnName());
			parameters.put(Common_Ente.descrizione.columnName(), StringUtil.convertFullLike(denominazioneEnte));
			separator = " and ";
		}
		if(StringUtil.isNotBlank(denominazioneOrganizzazione))
		{
			sql.append(separator)
			   .append(Common_Paesaggio_Organizzazione.denominazione.getCompleteName())
			   .append(" like :")
			   .append(Common_Paesaggio_Organizzazione.denominazione.columnName());
			parameters.put(Common_Paesaggio_Organizzazione.denominazione.columnName(), StringUtil.convertFullLike(denominazioneOrganizzazione));
			separator = " and ";
		}
		if(tipoOrganizzazione != null && !tipoOrganizzazione.isEmpty())
		{
			sql.append(separator)
			   .append(Common_Paesaggio_Organizzazione.tipo_organizzazione_specifica.getCompleteName())
			   .append(" in(:")
			   .append(Common_Paesaggio_Organizzazione.tipo_organizzazione_specifica.columnName())
			   .append(")");
			parameters.put(Common_Paesaggio_Organizzazione.tipo_organizzazione_specifica.columnName(), tipoOrganizzazione.stream().map(TipoOrganizzazioneSpecifica::getValue).collect(Collectors.toList()));
			separator = " and ";
		}
		if(organizzazione != null)
		{
			sql.append(separator)
			   .append(Common_Paesaggio_Organizzazione.tipo_organizzazione.getCompleteName())
			   .append(" = :")
			   .append(Common_Paesaggio_Organizzazione.tipo_organizzazione.columnName());
			parameters.put(Common_Paesaggio_Organizzazione.tipo_organizzazione.columnName(), organizzazione.toString());
			separator = " and ";
		}

	}

}
