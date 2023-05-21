package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
DROP VIEW CIVILIA_CS.VTNO_ATTIVITA_PPTR;

-- Formatted on 10/02/2016 11:46:51 (QP5 v5.136.908.31019) 
CREATE OR REPLACE FORCE VIEW CIVILIA_CS.VTNO_ATTIVITA_PPTR
(
   COMUNI,
   FTNO_SHKASSIGNMENTSTABLE,
   T_PRATICA_ID,
   T_PRATICA_CODICE,
   T_PRATICA_DESCRIZIONE,
   PROCESSES_RESOURCEREQUESTERID,
   PROCESSSTATES_NAME,
   ACTIVITIES_ID,
   ACTIVITIES_ACT_DEFINITIONID,
   ACTIVITIES_NAME,
   ACTIVITIES_PROCESS,
   ACTIVITIES_RESOURCEID,
   ACTIVITIES_STATE,
   ACTIVITYSTATES_KEYVALUE,
   RESOURCESTABLE_USERNAME,
   JBP_UNAME,
   PPTR_FASE_ISTANZA_CODICE,
   SOLOTRASMISSIONE,
   CODICE_PRATICA_ENTEDELEGATO,
   UFFICIO,
   TIPOPROCEDIMENTO,
   ENTEDELEGATO,
   DESCPROCEDIMENTO,
   OBJECTIDPUBBLICAZIONE
)
AS
     SELECT -- Copiata dalla vista vtno_attivita  con l'aggiunta della tabelle  tno_pptr_pratica, tno_pptr_pratica_fase_istanza, tno_pptr_fase_istanza restituisce in piu i campi jbp_uname,pptr_fase_istanza_codice,solotrasmissione,codice_pratica_entedelegato,ufficio
           ftno_elenco_comuni_pptr (t_pratica.t_pratica_id),
            ftno_shkassignmentstable (shkactivities.OID),
            t_pratica.t_pratica_id,
            t_pratica.codice,
            t_pratica_descrizione,
            shkprocesses.resourcerequesterid,
            shkprocessstates.NAME,
            shkactivities.ID,
            shkactivities.activitydefinitionid,
            shkactivities.NAME AS attivita,
            shkactivities.process,
            shkactivities.resourceid,
            shkactivities.state,
            shkactivitystates.keyvalue,
            SUBSTR (shkresourcestable.username,
                    1,
                    INSTR (shkresourcestable.username, '@') - 1),
            tno_pptr_pratica.jbp_uname,
            tno_pptr_fase_istanza.codice,
            solotrasmissione,
            tno_pptr_pratica.codice_pratica_entedelegato,
            tno_pptr_pratica.ufficio,
            tno_pptr_pratica.tno_pptr_tipoprocedimento_id tipoprocedimento,
            tno_pptr_pratica.entedelegato,
            tno_pptr_tipoprocedimento.descrizione descprocedimento,
            info_aut_paes_pptr_alfa.objectid
       --,tno_pptr_pratica.PROG
       FROM t_pratica,
            shkprocesses,
            shkprocessstates,
            shkactivities,
            (  SELECT MAX (TO_NUMBER (SUBSTR (ID, 1, INSTR (ID, '_') - 1)))
                         AS mid,
                      process
                 FROM shkactivities
             GROUP BY process) ultimo,
            shkactivitystates,
            shkresourcestable,
            tno_pptr_pratica,
            tno_pptr_pratica_fase_istanza,
            tno_pptr_fase_istanza,
            info_aut_paes_pptr_alfa,
            tno_pptr_tipoprocedimento
      WHERE     1 = 1
            AND t_pratica.pinstancewmsid = shkprocesses.ID
            AND shkprocesses.state = shkprocessstates.OID
            AND shkprocesses.OID = shkactivities.process
            AND TO_NUMBER(SUBSTR (shkactivities.ID,
                                  1,
                                  INSTR (shkactivities.ID, '_') - 1)) =
                  TO_NUMBER (ultimo.mid)
            AND shkactivities.state = shkactivitystates.OID
            AND shkactivities.theresource = shkresourcestable.OID(+)
            AND t_pratica.codice = tno_pptr_pratica.codice_pratica_appptr
            AND tno_pptr_pratica.codice_pratica_appptr =
                  tno_pptr_pratica_fase_istanza.codice_pratica_appptr
            AND tno_pptr_pratica_fase_istanza.fase_istanza_id =
                  tno_pptr_fase_istanza.fase_istanza_id
            AND tno_pptr_pratica.active = '0'
            AND info_aut_paes_pptr_alfa.codice_pratica_appptr(+) =
                  tno_pptr_pratica.codice_pratica_appptr
            -- questa condizione indica che la pratica non e' stata cancellata
            AND tno_pptr_tipoprocedimento.tno_pptr_tipoprocedimento_id =
                  tno_pptr_pratica.tno_pptr_tipoprocedimento_id
   -- questa condizione indica che la pratica non e' stata cancellata
   --    AND TNO_PPTR_PRATICA.PROG = TNO_PPTR_PRATICA_FASE_ISTANZA.PROG
   --   AND TNO_PPTR_PRATICA.PROG = info_aut_paes_pptr_alfa.PROG(+)
   ORDER BY shkactivitystates.OID;



*/

//@Entity
//@Table(name="VTNO_ATTIVITA_PPTR")
public class VtnoAttivitaPptr  implements Serializable  {

    private static final long serialVersionUID = 1L;
    
    static Logger _logger = LoggerFactory.getLogger(VtnoAttivitaPptr.class);
    
    //@Id
	//@Column(name="t_pratica_id")
	private long tPraticaId;
    
    //@Column(name="COMUNI")
    private String comuni;
	
	//@Column(name="ftno_shkassignmentstable")
	private String ftnoShkassignmentstable;
	
	//@Column(name="t_pratica_codice")
	private String tPraticaCodice;
	
	//@Column(name="t_pratica_descrizione")
	private String tPraticaDescrizione;
    
	//@Column(name="processes_resourcerequesterid")
	private String processesResourcerequesterId;	

	//@Column(name="processstates_name")
	private String processstatesName;	

	//@Column(name="activities_id")
	private String activitiesId;	
    
	//@Column(name="activities_act_definitionid")
	private String activitiesActDefinitionid;	
	
	//@Column(name="activities_name")
	private String activitiesName;
    
	//@Column(name="activities_process")
	private String activitiesProcess;
	
	//@Column(name="activities_resourceid")
	private String activitiesResourceid;	
	
	//@Column(name="activities_state")
	private String activitiesState;
	
	//@Column(name="activitystates_keyvalue")
	private String activitystatesKeyvalue;
        
	//@Column(name="resourcestable_username")
	private String resourcestableUsername;
	
	//@Column(name="JBP_UNAME")
	private String jbpUname;	
	
	//@Column(name="PPTR_FASE_ISTANZA_CODICE")
	private String pptrFaseIstanzaCodice;
	
	//@Column(name="SOLOTRASMISSIONE")
	private long soloTrasmissione;
	
	//@Column(name="CODICE_PRATICA_ENTEDELEGATO")
	private String codicePraticaEnteDelegato;
	
	//@Column(name="UFFICIO")
	private String ufficio;
	
	//@Column( name="DESCPROCEDIMENTO")
	private String descprocedimento;
	
	// e' l'ente delegato selezionato dal cittadino nella forma "00016!REGIONE PUGLIA"
	//@Column( name="entedelegato")
	private String entedelegato;		
	
	// e' il codice dell'ente delegato selezionato dal cittadino, un number, chiave esterna per 
	// TNO_PPTR_ABILITAZ_ENTEDELEG
	//@Column( name="TNO_PPTR_ABILITAZ_ENTEDELEG_ID")
	private long idEnteDelegato;
	
	//@Column( name="TIPOPROCEDIMENTO")
	private Long tipoProcedimento;
	
	public Long getTipoProcedimento() {
		return tipoProcedimento;
	}

	public void setTipoProcedimento(Long tipoProcedimento) {
		this.tipoProcedimento = tipoProcedimento;
	}

	/*
	 * Indica se la pratica è da evidenziare, nell'elenco pratiche    
	 */
	//@Transient
	private boolean evidenziaPratica;
	
	//@Transient
	private long prog;
	
	/**
	 *  Messaggio per lo stato della pratica che appare nei elenchi .
	 * 
	 * 	In lavorazione	in lavorazione->	solo per il procedimento 91
 	 *	In lavorazione	in attesa di trasmissione Relazione alla Soprintendenza	->per gli altri tipi di procedimento
 	 *	In lavorazione	in attesa di ricezione parere dalla Soprintendenza	->dopo che è stata trasmessa la Relazione in Soprintendenza
 	 *	In lavorazione	Parere Soprintendenza ricevuto	->Quando è stato ricevuto il parer. Questo messaggio di stato va scritto in rosso
 	 */
	//@Transient
	private String statusPratica;
	
	//OBJECTIDPUBBLICAZIONE
	private Long objectIdPubblicazione;
		
	public Long getObjectIdPubblicazione() {
		return objectIdPubblicazione;
	}

	public void setObjectIdPubblicazione(Long objectIdPubblicazione) {
		this.objectIdPubblicazione = objectIdPubblicazione;
	}

	public String getStatusPratica()
	{
		return statusPratica;
	}

	public void setStatusPratica( String statusPratica )
	{
		this.statusPratica = statusPratica;
	}

	public boolean getEvidenziaPratica()
	{
		return evidenziaPratica;
	}

	public void setEvidenziaPratica( boolean evidenzia )
	{
		this.evidenziaPratica = evidenzia;
	}
	
	public long getIdEnteDelegato()
	{
		return this.idEnteDelegato;
	}

	public void setIdEntedelegato( long id )
	{
		this.idEnteDelegato = id;
	}

	public long getProg()
	{
		return prog;
	}

	public void setProg( long prog )
	{
		this.prog = prog;
	}


	/*
	 * Costruttore senza parametri
	 */
	public VtnoAttivitaPptr( ){}
	
	
		
	
	/**
	 * Costruttore con parametri
	 *
	 * tno_pptr_pratica.CODICE_PRATICA_ENTEDELEGATO, 
	 * tno_pptr_pratica.T_PRATICA_DESCRIZIONE,  
	 * tno_pptr_pratica.ID ,
	 * tno_pptr_pratica.CODICE_PRATICA_APPPTR ,
	 * tno_pptr_pratica.PROG 
	 * 
	 * in alcuni casi anche DESCPROCEDIMENTO
     * 
	 */
	public VtnoAttivitaPptr( Object[] obj ) 
	{
		try
		{
			if( obj != null )
			{
				String tmp = "";
				// CODICE_PRATICA_ENTEDELEGATO
				if( obj[0] != null )
				{
					tmp = obj[0].toString();
					this.setCodicePraticaEnteDelegato( tmp );
				}
				
				// T_PRATICA_DESCRIZIONE
				if( obj[1] != null )
				{
					tmp = obj[1].toString();
					this.settPraticaDescrizione( tmp );
				}
				
				// ID
				if( obj[2] != null  )
				{
					tmp = obj[2].toString();
					if( !tmp.isEmpty() )
					{
						this.settPraticaId( Long.valueOf( tmp ).longValue() );
					}
					
				}
				
				// CODICE_PRATICA_APPPTR
				if( obj[3] != null )
				{
					tmp = obj[3].toString();
					this.settPraticaCodice( tmp );
				}
				
				// PROG
				if( obj[4] != null )
				{
					tmp = obj[4].toString();
					if( !tmp.isEmpty() )
					{
						this.setProg( Long.valueOf( tmp ).longValue() );
					}
					
				}
				
				// mi aspetto il tipo di procedimento
				if( obj.length > 5 )
				{
					// DESCPROCEDIMENTO
					if( obj[5] != null )
					{
						tmp = obj[5].toString();
						if( !tmp.isEmpty() )
						{
							this.setDescprocedimento( tmp );
						}
					}
				}
			}
			else
			{
				_logger.error( "VtnoAttivitaPptr obj is Null" );
			}
		}
		catch( Exception e )
		{
			_logger.error( " VtnoAttivitaPptr catch errore " + e.getMessage() );
		}
	}
	
	public String getComuni()
	{
		return comuni;
	}

	public void setComuni( String comuni )
	{
		this.comuni = comuni;
	}
	
	public String getEntedelegato()
	{
		return entedelegato;
	}
	
	public void setEntedelegato( String entedelegato )
	{
		this.entedelegato = entedelegato;
	}
	
	public String getCodicePraticaEnteDelegato()
	{
		return codicePraticaEnteDelegato;
	}
	
	public void setCodicePraticaEnteDelegato( String codicePraticaEnteDelegato )
	{
		this.codicePraticaEnteDelegato = codicePraticaEnteDelegato;
	}
	
	public String getUfficio()
	{
		return ufficio;
	}
	
	public void setUfficio( String ufficio )
	{
		this.ufficio = ufficio;
	}
	
	public long getSoloTrasmissione()
	{
		return soloTrasmissione;
	}
	
	public void setSoloTrasmissione( long soloTrasmissione )
	{
		this.soloTrasmissione = soloTrasmissione;
	}
	
	public long gettPraticaId()
	{
		return tPraticaId;
	}
	
	public void settPraticaId( long tPraticaId )
	{
		this.tPraticaId = tPraticaId;
	}
	
	public String getFtnoShkassignmentstable()
	{
		return ftnoShkassignmentstable;
	}
	
	public void setFtnoShkassignmentstable( String ftnoShkassignmentstable )
	{
		this.ftnoShkassignmentstable = ftnoShkassignmentstable;
	}
	
	public String gettPraticaCodice()
	{
		return tPraticaCodice;
	}
	
	public void settPraticaCodice( String tPraticaCodice )
	{
		this.tPraticaCodice = tPraticaCodice;
	}
	
	public String gettPraticaDescrizione()
	{
		return tPraticaDescrizione;
	}
	
	public void settPraticaDescrizione( String tPraticaDescrizione )
	{
		this.tPraticaDescrizione = tPraticaDescrizione;
	}
	
	public String getProcessesResourcerequesterId()
	{
		return processesResourcerequesterId;
	}
	
	public void setProcessesResourcerequesterId( String processesResourcerequesterId )
	{
		this.processesResourcerequesterId = processesResourcerequesterId;
	}
	
	public String getProcessstatesName()
	{
		return processstatesName;
	}
	
	public void setProcessstatesName( String processstatesName )
	{
		this.processstatesName = processstatesName;
	}
	
	public String getActivitiesId()
	{
		return activitiesId;
	}
	
	public void setActivitiesId( String activitiesId )
	{
		this.activitiesId = activitiesId;
	}
	
	public String getActivitiesActDefinitionid()
	{
		return activitiesActDefinitionid;
	}
	
	public void setActivitiesActDefinitionid( String activitiesActDefinitionid )
	{
		this.activitiesActDefinitionid = activitiesActDefinitionid;
	}
	
	public String getActivitiesName()
	{
		return activitiesName;
	}
	
	public void setActivitiesName( String activitiesName )
	{
		this.activitiesName = activitiesName;
	}
	
	public String getActivitiesProcess()
	{
		return activitiesProcess;
	}
	
	public void setActivitiesProcess( String activitiesProcess )
	{
		this.activitiesProcess = activitiesProcess;
	}
	
	public String getActivitiesResourceid()
	{
		return activitiesResourceid;
	}
	
	public void setActivitiesResourceid( String activitiesResourceid )
	{
		this.activitiesResourceid = activitiesResourceid;
	}
	
	public String getActivitiesState()
	{
		return activitiesState;
	}
	
	public void setActivitiesState( String activitiesState )
	{
		this.activitiesState = activitiesState;
	}
	
	public String getActivitystatesKeyvalue()
	{
		return activitystatesKeyvalue;
	}
	
	public void setActivitystatesKeyvalue( String activitystatesKeyvalue )
	{
		this.activitystatesKeyvalue = activitystatesKeyvalue;
	}
	
	public String getResourcestableUsername()
	{
		return resourcestableUsername;
	}
	
	public void setResourcestableUsername( String resourcestableUsername )
	{
		this.resourcestableUsername = resourcestableUsername;
	}
	
	public String getJbpUname()
	{
		return jbpUname;
	}
	
	public void setJbpUname( String jbpUname )
	{
		this.jbpUname = jbpUname;
	}
	
	public String getPptrFaseIstanzaCodice()
	{
		return pptrFaseIstanzaCodice;
	}
	
	public void setPptrFaseIstanzaCodice( String pptrFaseIstanzaCodice )
	{
		this.pptrFaseIstanzaCodice = pptrFaseIstanzaCodice;
	}
	
	public String getDescprocedimento()
	{
		return descprocedimento;
	}
	
	public void setDescprocedimento( String descprocedimento )
	{
		this.descprocedimento = descprocedimento;
	}
	
//	public JSONObject toJSONObject() throws JSONException
//	{
//		JSONObject jo = new JSONObject();
//		jo.put( "t_pratica_id" , this.tPraticaId );
//		jo.put( "ftno_shkassignmentstable" , this.ftnoShkassignmentstable );
//		jo.put( "t_pratica_codice" , this.tPraticaCodice );
//		jo.put( "t_pratica_descrizione" , this.tPraticaDescrizione );
//		jo.put( "processes_resourcerequesterid" , this.processesResourcerequesterId );
//		jo.put( "processstates_name" , this.processstatesName );
//		jo.put( "activities_id" , this.activitiesId );
//		jo.put( "activities_act_definitionid" , this.activitiesActDefinitionid );
//		jo.put( "activities_name" , this.activitiesName );
//		jo.put( "activities_process" , this.activitiesProcess );
//		jo.put( "activities_resourceid" , this.activitiesResourceid );
//		jo.put( "activities_state" , this.activitiesState );
//		jo.put( "activitystates_keyvalue" , this.activitystatesKeyvalue );
//		jo.put( "resourcestable_username" , this.resourcestableUsername );
//		jo.put( "jbpUname" , this.jbpUname );
//		jo.put( "pptrFaseIstanzaCodice" , this.pptrFaseIstanzaCodice );
//		
//		return jo;
//	}
	
}
