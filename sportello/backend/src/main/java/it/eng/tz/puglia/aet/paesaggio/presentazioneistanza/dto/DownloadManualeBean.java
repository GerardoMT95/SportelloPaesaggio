package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto;

import java.io.Serializable;
import java.util.Objects;

public class DownloadManualeBean implements Serializable{

	private static final long serialVersionUID = 1376033433454759479L;
	private String label;
	private String url;
	
	public DownloadManualeBean(final String label, final String url) {
		this.label = label;
		this.url = url;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(label, url);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DownloadManualeBean other = (DownloadManualeBean) obj;
		return Objects.equals(label, other.label) && Objects.equals(url, other.url);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DownloadManualeBean [label=").append(label).append(", url=").append(url).append("]");
		return builder.toString();
	}
}
