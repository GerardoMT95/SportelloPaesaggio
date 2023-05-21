package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto;

import java.io.Serializable;

public class GruppoBean implements Serializable {
		/**
	 * 
	 */
	private static final long serialVersionUID = -5285302811229490075L;
		String codiceGruppo;
		String nomeGruppo;
		public String getCodiceGruppo() {
			return codiceGruppo;
		}
		public void setCodiceGruppo(String codiceGruppo) {
			this.codiceGruppo = codiceGruppo;
		}
		public String getNomeGruppo() {
			return nomeGruppo;
		}
		public void setNomeGruppo(String nomeGruppo) {
			this.nomeGruppo = nomeGruppo;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((codiceGruppo == null) ? 0 : codiceGruppo.hashCode());
			result = prime * result + ((nomeGruppo == null) ? 0 : nomeGruppo.hashCode());
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
			GruppoBean other = (GruppoBean) obj;
			if (codiceGruppo == null) {
				if (other.codiceGruppo != null)
					return false;
			} else if (!codiceGruppo.equals(other.codiceGruppo))
				return false;
			if (nomeGruppo == null) {
				if (other.nomeGruppo != null)
					return false;
			} else if (!nomeGruppo.equals(other.nomeGruppo))
				return false;
			return true;
		}
		

}
