package it.eng.tz.puglia.servizi_esterni.cms.bean;

import java.io.Serializable;

/**
 * Bean to map cms file in upload service
 */
public class CmsFileBean implements Serializable {

	private static final long serialVersionUID = 6911453160888476582L;

	private Long attachmentId;
	private String id;
	private String path;

	public CmsFileBean(String id) {
		this.id = id;
	}

	public CmsFileBean() {
	}

	public Long getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(Long attachmentId) {
		this.attachmentId = attachmentId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		CmsFileBean other = (CmsFileBean) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
