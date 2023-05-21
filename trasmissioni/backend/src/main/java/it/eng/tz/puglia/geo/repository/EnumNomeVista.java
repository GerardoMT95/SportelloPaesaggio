package it.eng.tz.puglia.geo.repository;

public enum EnumNomeVista {

	EDITING("layer_view_editing"),//vista campi editing
	PUBLISHED("layer_view_published"); //vista campi published
	private String nomeVista;
	
	EnumNomeVista(String nomeVista){
		this.nomeVista=nomeVista;
	}

	public String getNomeVista() {
		return nomeVista;
	}
	
}
