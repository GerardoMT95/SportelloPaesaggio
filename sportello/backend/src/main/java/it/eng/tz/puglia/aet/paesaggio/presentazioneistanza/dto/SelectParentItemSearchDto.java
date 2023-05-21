package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto;

import java.util.Objects;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

public class SelectParentItemSearchDto extends BaseSearchRequestBean {
	
	/**
	 * @author Alessio Bottalico
	 * @date 22 lug 2022
	 */
	private static final long serialVersionUID = 1L;
	private String parent;
	private String label;
	private String value;
	
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(label, parent, value);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SelectParentItemSearchDto other = (SelectParentItemSearchDto) obj;
		return Objects.equals(label, other.label) && Objects.equals(parent, other.parent)
				&& Objects.equals(value, other.value);
	}
	
}
