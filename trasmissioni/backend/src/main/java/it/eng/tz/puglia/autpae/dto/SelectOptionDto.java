package it.eng.tz.puglia.autpae.dto;

public class SelectOptionDto {

	private String value;
	private String description;
	private String linked;
	
	
	public SelectOptionDto() { }
	
	public SelectOptionDto(TipologicaDTO tipologica) { 
		this.value = tipologica.getCodice();
		this.description = tipologica.getLabel();
	}
	
	public SelectOptionDto(String value,String descr) { 
		this.value = value;
		this.description = descr;
	}
	
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLinked() {
		return linked;
	}
	public void setLinked(String linked) {
		this.linked = linked;
	}

	@Override
	public String toString() {
		return "SelectOptionDto [value=" + value + ", description=" + description + ", linked=" + linked + "]";
	}
	
}