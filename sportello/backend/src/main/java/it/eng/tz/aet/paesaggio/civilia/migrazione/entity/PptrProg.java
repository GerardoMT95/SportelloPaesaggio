package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;

import java.io.Serializable;

/**
 * The persistent class for the TNO_PPTR_PROG database table.
 * CREATE TABLE TNO_PPTR_PROG
(
  ID                            NUMBER          NOT NULL,
  CODICE_PRATICA_APPPTR         VARCHAR2(255 BYTE),
  T_PRATICA_APPTR_ID            NUMBER,
  CODICE_PRATICA_ENTEDELEGATO   VARCHAR2(255 BYTE),
  PROG_CITTADINO                NUMBER,
  PROG_ENTEDELEGATO             NUMBER,
  PROG_PUBBLICAZIONE            NUMBER     
)
 */
//@Entity
//@Table(name="TNO_PPTR_PROG")
public class PptrProg implements Serializable {
	private static final long serialVersionUID = 1L;

	//@Id
	//@Column(name="ID")
	private long id;
	
	//@Column(name="CODICE_PRATICA_APPPTR")
	private String codicePraticaAppptr;

	//@Column(name="CODICE_PRATICA_ENTEDELEGATO")
	private String codicePraticaEntedelegato;

	//@Column(name="PROG_CITTADINO")
	private long progCittadino;

	//@Column(name="PROG_ENTEDELEGATO")
	private long progEntedelegato;

	//@Column(name="PROG_PUBBLICAZIONE")
	private long progPubblicazione;

	//@Column(name="T_PRATICA_APPTR_ID")
	private long tPraticaApptrId;

	public PptrProg(){ }

	public String getCodicePraticaAppptr()
	{
		return this.codicePraticaAppptr;
	}

	public void setCodicePraticaAppptr( String codicePraticaAppptr )
	{
		this.codicePraticaAppptr = codicePraticaAppptr;
	}

	public String getCodicePraticaEntedelegato()
	{
		return this.codicePraticaEntedelegato;
	}

	public void setCodicePraticaEntedelegato( String codicePraticaEntedelegato )
	{
		this.codicePraticaEntedelegato = codicePraticaEntedelegato;
	}

	public long getId()
	{
		return this.id;
	}

	public void setId( long id )
	{
		this.id = id;
	}

	public long getProgCittadino()
	{
		return this.progCittadino;
	}

	public void setProgCittadino( long progCittadino )
	{
		this.progCittadino = progCittadino;
	}

	public long getProgEntedelegato()
	{
		return this.progEntedelegato;
	}

	public void setProgEntedelegato( long progEntedelegato )
	{
		this.progEntedelegato = progEntedelegato;
	}

	public long getProgPubblicazione()
	{
		return this.progPubblicazione;
	}

	public void setProgPubblicazione( long progPubblicazione )
	{
		this.progPubblicazione = progPubblicazione;
	}

	public long getTPraticaApptrId()
	{
		return this.tPraticaApptrId;
	}

	public void setTPraticaApptrId( long tPraticaApptrId )
	{
		this.tPraticaApptrId = tPraticaApptrId;
	}

}