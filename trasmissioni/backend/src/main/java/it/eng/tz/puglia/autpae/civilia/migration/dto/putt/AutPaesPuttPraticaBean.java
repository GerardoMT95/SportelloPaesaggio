package it.eng.tz.puglia.autpae.civilia.migration.dto.putt;

public class AutPaesPuttPraticaBean
{
	private Long id;
	private Long jbpId;
	private String jbpUname;
	private String codicePraticaApputp;
	private String codicePraticaEntedelegato;
	private String tPraticaDescrizione;
	private String enteDelegato;
	private String ufficio;
	private Long tnoPutpTipoprocedimentoId;
	//0 attivo, 1 non attivo
	private Short active;
	private String note;
	private Integer prog;
	//'0' se non checkato, '1' se checkato
	private String provvedimentoSanatoria;
	//0 procedimento completo, 1 solo procedimento finale
	private Short soloTrasmissione;
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public Long getJbpId()
	{
		return jbpId;
	}
	public void setJbpId(Long jbpId)
	{
		this.jbpId = jbpId;
	}
	
	public String getJbpUname()
	{
		return jbpUname;
	}
	public void setJbpUname(String jbpUname)
	{
		this.jbpUname = jbpUname;
	}
	
	public String getCodicePraticaApputp()
	{
		return codicePraticaApputp;
	}
	public void setCodicePraticaApputp(String codicePraticaApputp)
	{
		this.codicePraticaApputp = codicePraticaApputp;
	}
	
	public String getCodicePraticaEntedelegato()
	{
		return codicePraticaEntedelegato;
	}
	public void setCodicePraticaEntedelegato(String codicePraticaEntedelegato)
	{
		this.codicePraticaEntedelegato = codicePraticaEntedelegato;
	}
	
	public String gettPraticaDescrizione()
	{
		return tPraticaDescrizione;
	}
	public void settPraticaDescrizione(String tPraticaDescrizione)
	{
		this.tPraticaDescrizione = tPraticaDescrizione;
	}
	
	public String getEnteDelegato()
	{
		return enteDelegato;
	}
	public void setEnteDelegato(String enteDelegato)
	{
		this.enteDelegato = enteDelegato;
	}
	
	public String getUfficio()
	{
		return ufficio;
	}
	public void setUfficio(String ufficio)
	{
		this.ufficio = ufficio;
	}
	
	public Long getTnoPutpTipoprocedimentoId()
	{
		return tnoPutpTipoprocedimentoId;
	}
	public void setTnoPutpTipoprocedimentoId(Long tnoPutpTipoprocedimentoId)
	{
		this.tnoPutpTipoprocedimentoId = tnoPutpTipoprocedimentoId;
	}
	
	public Short getActive()
	{
		return active;
	}
	public void setActive(Short active)
	{
		this.active = active;
	}
	
	public String getNote()
	{
		return note;
	}
	public void setNote(String note)
	{
		this.note = note;
	}
	
	public Integer getProg()
	{
		return prog;
	}
	public void setProg(Integer prog)
	{
		this.prog = prog;
	}
	
	public String getProvvedimentoSanatoria()
	{
		return provvedimentoSanatoria;
	}
	public void setProvvedimentoSanatoria(String provvedimentoSanatoria)
	{
		this.provvedimentoSanatoria = provvedimentoSanatoria;
	}
	
	public Short getSoloTrasmissione()
	{
		return soloTrasmissione;
	}
	public void setSoloTrasmissione(Short soloTrasmissione)
	{
		this.soloTrasmissione = soloTrasmissione;
	}
	
	
}
