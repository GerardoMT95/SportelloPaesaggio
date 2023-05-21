package it.eng.tz.puglia.autpae.search;

import it.eng.tz.puglia.autpae.dbMapping.ModificheTab;
import it.eng.tz.puglia.autpae.enumeratori.NomiTab;
import it.eng.tz.puglia.autpae.search.generic.WhereClauseGenerator;

/**
 * Search for table modifiche_tab
 */
public class ModificheTabSearch extends WhereClauseGenerator<ModificheTab> 
{
	private static final long serialVersionUID = 6693642738L;
	
	private Integer hashcode;
	private NomiTab tab;
//	private String info;
	
	
	public ModificheTabSearch() {}

	public ModificheTabSearch(Integer hashcode, NomiTab tab, String info) {
		this.hashcode = hashcode;
		this.tab = tab;
//		this.info = info;
	}

	
	public Integer getHashcode() {
		return hashcode;
	}

	public void setHashcode(Integer hashcode) {
		this.hashcode = hashcode;
	}

	public NomiTab getTab() {
		return tab;
	}

	public void setTab(NomiTab tab) {
		this.tab = tab;
	}

//	public String getInfo() {
//		return info;
//	}
//
//	public void setInfo(String info) {
//		this.info = info;
//	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((hashcode == null) ? 0 : hashcode.hashCode());
		result = prime * result + ((tab == null) ? 0 : tab.hashCode());
//		result = prime * result + ((info == null) ? 0 : info.hashCode());
		return result;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModificheTabSearch other = (ModificheTabSearch) obj;
		if (hashcode == null) {
			if (other.hashcode != null)
				return false;
		} else if (!hashcode.equals(other.hashcode))
			return false;
		if (tab != other.tab)
			return false;
//		if (info == null) {
//			if (other.info != null)
//				return false;
//		} else if (!info.equals(other.info))
//			return false;
		return true;
	}

	
	@Override
	public String toString() {
		return "ModificheTabSearch [hashcode=" + hashcode + ", tab=" + tab + "]";
	}

	
	@Override
	protected void generateWhereCriteria() {
		String separator = " where ";
		if(hashcode != null)
		{
			sql.append(separator)
			   .append(ModificheTab.hashcode.getCompleteName())
			   .append(" = :")
			   .append(ModificheTab.hashcode.columnName());
			parameters.put(ModificheTab.hashcode.columnName(), hashcode);
			separator = " and ";
		}
		if(tab != null)
		{
			sql.append(separator)
			   .append(ModificheTab.tab.getCompleteName())
			   .append(" = :")
			   .append(ModificheTab.tab.columnName());
			parameters.put(ModificheTab.tab.columnName(), tab);
			separator = " and ";
		}
	}

}