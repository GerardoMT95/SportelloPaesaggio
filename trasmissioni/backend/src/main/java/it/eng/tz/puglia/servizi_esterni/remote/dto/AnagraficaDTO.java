package it.eng.tz.puglia.servizi_esterni.remote.dto;

import java.io.Serializable;

public class AnagraficaDTO implements Serializable {

	private static final long serialVersionUID = -8208200099325617529L;

	private Integer id;
	private Integer idRegione;
	private Integer idProvincia;
	private String nome;
	private String numericIstatCode;
	private Long shapeLeng;
	private Long shapeArea;
	private String istatCode;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdRegione() {
		return idRegione;
	}

	public void setIdRegione(Integer idRegione) {
		this.idRegione = idRegione;
	}

	public Integer getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(Integer idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNumericIstatCode() {
		return numericIstatCode;
	}

	public void setNumericIstatCode(String numericIstatCode) {
		this.numericIstatCode = numericIstatCode;
	}

	public Long getShapeLeng() {
		return shapeLeng;
	}

	public void setShapeLeng(Long shapeLeng) {
		this.shapeLeng = shapeLeng;
	}

	public Long getShapeArea() {
		return shapeArea;
	}

	public void setShapeArea(Long shapeArea) {
		this.shapeArea = shapeArea;
	}

	public String getIstatCode() {
		return istatCode;
	}

	public void setIstatCode(String istatCode) {
		this.istatCode = istatCode;
	}

}
