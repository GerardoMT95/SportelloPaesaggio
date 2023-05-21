/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.dto;

/**
 * CIVILIA_CS.T_KE_DOC_ATTR  per recuperare content-type file  
 * @author Adriano Colaianni
 * @date 28 apr 2021
 */
public class TkeDocAttr {
	private String name;
	private String value;
	private String tKeDocId;
	private String proprietario;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String gettKeDocId() {
		return tKeDocId;
	}
	public void settKeDocId(String tKeDocId) {
		this.tKeDocId = tKeDocId;
	}
	public String getProprietario() {
		return proprietario;
	}
	public void setProprietario(String proprietario) {
		this.proprietario = proprietario;
	}
	

}
