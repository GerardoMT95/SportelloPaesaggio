package it.eng.tz.puglia.autpae.civilia.migration.dto.putt;

import java.sql.Blob;

import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.puglia.autpae.civilia.migration.dto.VtnoAllegatiPptr;

public class PuttDocBean
{
	private Long nTKeDocStID;
	private String tTipodocCodice;
	private String tTipodocDescrizione;
	private String nName;
	private String nomeFile; //nomefile comprensivo di prefisso F_xxxxxx
	private Blob abinContent;
	//tipo documento
	private String tKeDocAttrValue;
	private String idAlfresco;
	
	public String getIdAlfresco() {
		return idAlfresco;
	}
	public void setIdAlfresco(String idAlfresco) {
		this.idAlfresco = idAlfresco;
	}
	public Long getnTKeDocStID()
	{
		return nTKeDocStID;
	}
	public void setnTKeDocStID(Long nTKeDocStID)
	{
		this.nTKeDocStID = nTKeDocStID;
	}
	
	public String gettTipodocCodice()
	{
		return tTipodocCodice;
	}
	public void settTipodocCodice(String tTipodocCodice)
	{
		this.tTipodocCodice = tTipodocCodice;
	}
	
	public String gettTipodocDescrizione()
	{
		return tTipodocDescrizione;
	}
	public void settTipodocDescrizione(String tTipodocDescrizione)
	{
		this.tTipodocDescrizione = tTipodocDescrizione;
	}
	
	public String getnName()
	{
		return nName;
	}
	public void setnName(String nName)
	{
		this.nName = nName;
	}
	
	public Blob getAbinContent()
	{
		return abinContent;
	}
	public void setAbinContent(Blob abinContent)
	{
		this.abinContent = abinContent;
	}
	
	public String gettKeDocAttrValue()
	{
		return tKeDocAttrValue;
	}
	public void settKeDocAttrValue(String tKeDocAttrValue)
	{
		this.tKeDocAttrValue = tKeDocAttrValue;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	
	public String getIdToDownloadFromAlfresco() {
		if( StringUtil.isNotEmpty(idAlfresco) && ( idAlfresco.compareTo(VtnoAllegatiPptr.NOFILEINALFRESCO) != 0 ) )  
		{
			return this.idAlfresco;
		}
		return null;
	}
	
}

