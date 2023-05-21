package it.eng.tz.puglia.autpae.entity.composedKeys;

import java.io.Serializable;

import it.eng.tz.puglia.autpae.enumeratori.NomiTab;

public class ModificheTabPK implements Serializable {

	private static final long serialVersionUID = 7937074150880563920L;
	
	private int hashcode;
	private NomiTab tab;
	
	public ModificheTabPK() { }

	public ModificheTabPK(int hashcode, NomiTab tab) {
		this.hashcode = hashcode;
		this.tab = tab;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hashcode;
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
		ModificheTabPK other = (ModificheTabPK) obj;
		if (hashcode != other.hashcode)
			return false;
		if (tab != other.tab)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ModificheTabPK [hashcode=" + hashcode + ", tab=" + tab + "]";
	}

}