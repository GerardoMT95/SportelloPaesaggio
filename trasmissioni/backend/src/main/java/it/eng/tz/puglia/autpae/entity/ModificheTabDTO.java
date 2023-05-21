package it.eng.tz.puglia.autpae.entity;

import java.io.Serializable;

import it.eng.tz.puglia.autpae.enumeratori.NomiTab;

/**
 * DTO for table modifiche_tab
 */
public class ModificheTabDTO implements Serializable {

	private static final long serialVersionUID = 6693642738L;
	
	private int hashcode;
	private NomiTab tab;
	private String info;
	
	public ModificheTabDTO() {}

	public ModificheTabDTO(int hashcode, NomiTab tab, String info) {
		this.hashcode = hashcode;
		this.tab = tab;
		this.info = info;
	}

	public int getHashcode() {
		return hashcode;
	}

	public void setHashcode(int hashcode) {
		this.hashcode = hashcode;
	}

	public NomiTab getTab() {
		return tab;
	}

	public void setTab(NomiTab tab) {
		this.tab = tab;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hashcode;
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		result = prime * result + ((tab == null) ? 0 : tab.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModificheTabDTO other = (ModificheTabDTO) obj;
		if (hashcode != other.hashcode)
			return false;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		if (tab != other.tab)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ModificheTabDTO [hashcode=" + hashcode + ", tab=" + tab + ", info=" + info + "]";
	}

}