/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.dto;

import java.sql.Blob;
import java.util.List;

/**
 * costruito da join con T_MAIL_ALLEGATI,T_KE_DOC_ST
 * SELECT mall.T_MAIL_INVIATE_ALLEGATI_ID ,mall.T_KE_DOC_ID,mall.T_MAIL_INVIATE_ID ,mall.NOME_FILE,mall.CC_TIME_STAMP ,bin.BIN_CONTENT 
	FROM T_MAIL_INVIATE_ALLEGATI mall,T_KE_DOC_ST bin WHERE mall.T_KE_DOC_ID =bin.T_KE_DOC_ID;
 * @author Adriano Colaianni
 * @date 23 apr 2021
 */
public class CiviliaMailAllegati {

	//@Id
	//@Column(name="T_MAIL_INVIATE_ALLEGATI_ID")
	private long tMailInviateAllegatiId;
		
	//@Column(name="T_MAIL_INVIATE_ID")
	private long tMailInviateId;
		
	//@Column(name="CC_TIME_STAMP")
	private long ccTimeStamp;
	
	//@Column(name="NOME_FILE")
	private String nomeFile;	
	
	//@Column(name="BIN_CONTENT")
	private Blob binContent;

	//@Column(name="T_KE_DOC_ID")
	private long tKeDocId;
	
	//set manuale
	private List<TkeDocAttr> attributi;

	public List<TkeDocAttr> getAttributi() {
		return attributi;
	}

	public void setAttributi(List<TkeDocAttr> attributi) {
		this.attributi = attributi;
	}

	public long gettMailInviateAllegatiId() {
		return tMailInviateAllegatiId;
	}

	public void settMailInviateAllegatiId(long tMailInviateAllegatiId) {
		this.tMailInviateAllegatiId = tMailInviateAllegatiId;
	}

	public long gettMailInviateId() {
		return tMailInviateId;
	}

	public void settMailInviateId(long tMailInviateId) {
		this.tMailInviateId = tMailInviateId;
	}

	public long getCcTimeStamp() {
		return ccTimeStamp;
	}

	public void setCcTimeStamp(long ccTimeStamp) {
		this.ccTimeStamp = ccTimeStamp;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public Blob getBinContent() {
		return binContent;
	}

	public void setBinContent(Blob binContent) {
		this.binContent = binContent;
	}

	public long gettKeDocId() {
		return tKeDocId;
	}

	public void settKeDocId(long tKeDocId) {
		this.tKeDocId = tKeDocId;
	}
	
}
