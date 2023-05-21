package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.jasper.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class JasperAllegatoDTO implements Serializable {

	private static final long serialVersionUID = 4982170498229217272L;

	private Date data;
	private String nome;
	private String listaTipi;
	
	
	public JasperAllegatoDTO() { }

	public JasperAllegatoDTO(Date data, String nome, List<String> listaTipi) {
		this.data = data;
		this.nome = nome;
		this.listaTipi = "";
		if (listaTipi!=null) {
			listaTipi.forEach(elem->{
				this.listaTipi = getListaTipi() + "- " +elem + "\n";
			});
			this.listaTipi = getListaTipi().substring(0, getListaTipi().length() - 1);
		}
	}

	
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getListaTipi() {
		return listaTipi;
	}

	public void setListaTipi(String listaTipi) {
		this.listaTipi = listaTipi;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((listaTipi == null) ? 0 : listaTipi.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		JasperAllegatoDTO other = (JasperAllegatoDTO) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (listaTipi == null) {
			if (other.listaTipi != null)
				return false;
		} else if (!listaTipi.equals(other.listaTipi))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JasperAllegatoDTO [data=" + data + ", nome=" + nome + ", listaTipi=" + listaTipi + "]";
	}

}