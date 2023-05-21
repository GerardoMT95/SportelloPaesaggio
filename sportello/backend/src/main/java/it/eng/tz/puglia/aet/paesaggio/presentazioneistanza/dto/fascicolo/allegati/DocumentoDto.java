package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.allegati;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;

public class DocumentoDto implements Serializable
{
	private static final long serialVersionUID = -1838563891494684558L;
	
	private UUID id;
	private String type;
	private String name;
	private Date uploadDate;
	private String path;
	private Long size;
	private String formatoFile;
	private String checksum;
	private Boolean isSigned;
	
	public DocumentoDto() { super(); }
	public DocumentoDto(AllegatiDTO dto)
	{
		id = dto.getId();
		type = dto.getTipiContenuto().get(0);
		name = dto.getNomeFile();
		uploadDate = dto.getDataCaricamento();
		path = dto.getPathCms();
		size = dto.getSize();
		setFormatoFile(dto.getFormatoFile());
		checksum = dto.getChecksum();
		isSigned = dto.isSigned();
	}
	
	public UUID getId()
	{
		return id;
	}
	public void setId(UUID id)
	{
		this.id = id;
	}
	
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
	public Date getUploadDate()
	{
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate)
	{
		this.uploadDate = uploadDate;
	}
	
	public String getPath()
	{
		return path;
	}
	public void setPath(String path)
	{
		this.path = path;
	}
	
	public Long getSize()
	{
		return size;
	}
	public void setSize(Long size)
	{
		this.size = size;
	}
	
		
	public String getChecksum() {
		return checksum;
	}
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
	
	public String getFormatoFile() {
		return formatoFile;
	}
	public void setFormatoFile(String formatoFile) {
		this.formatoFile = formatoFile;
	}
	public Boolean getIsSigned() {
		return isSigned;
	}
	public void setIsSigned(Boolean isSigned) {
		this.isSigned = isSigned;
	}
	@Override
	public int hashCode() {
		return Objects.hash(checksum, formatoFile, id, isSigned, name, path, size, type, uploadDate);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocumentoDto other = (DocumentoDto) obj;
		return Objects.equals(checksum, other.checksum) && Objects.equals(formatoFile, other.formatoFile)
				&& Objects.equals(id, other.id) && Objects.equals(isSigned, other.isSigned)
				&& Objects.equals(name, other.name) && Objects.equals(path, other.path)
				&& Objects.equals(size, other.size) && Objects.equals(type, other.type)
				&& Objects.equals(uploadDate, other.uploadDate);
	}
	@Override
	public String toString() {
		return "DocumentoDto [id=" + id + ", type=" + type + ", name=" + name + ", uploadDate=" + uploadDate + ", path="
				+ path + ", size=" + size + ", formatoFile=" + formatoFile + ", checksum=" + checksum + ", isSigned="
				+ isSigned + "]";
	}
	
}

