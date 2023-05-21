package it.eng.tz.puglia.autpae.dto.BE_to_FE;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import it.eng.tz.puglia.autpae.dto.TipologicaDTO;

public class PlainTypeStringIdDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String nome;
	
	public PlainTypeStringIdDTO() { }

	public PlainTypeStringIdDTO(String id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public PlainTypeStringIdDTO(TipologicaDTO tipologicaDTO) {
		this.id = tipologicaDTO.getCodice();
		this.nome = tipologicaDTO.getLabel();
	}

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		PlainTypeStringIdDTO other = (PlainTypeStringIdDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		return "PlainTypeStringIdDto [id=" + id + ", nome=" + nome + "]";
	}

	
	public static Set<PlainTypeStringIdDTO> creaDaTipologicaDTO(Set<TipologicaDTO> setTipologicaDTO) {
		Set<PlainTypeStringIdDTO> setPlainTypeStringIdDTO = new HashSet<>(setTipologicaDTO.size());
		setTipologicaDTO.forEach(elem->{
			setPlainTypeStringIdDTO.add(new PlainTypeStringIdDTO(elem));
		});
		return setPlainTypeStringIdDTO;
	}
	
}