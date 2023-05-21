package it.eng.tz.puglia.autpae.civilia.migration.dto.utils;

import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.autpae.entity.AllegatoDTO;

public class AllegatiWithMultipart extends AllegatoDTO
{
	private static final long serialVersionUID = 1L;
	
	private MultipartFile file;
	private Long idCivilia;
	
	public AllegatiWithMultipart() {}
	public AllegatiWithMultipart(MultipartFile file) { this.setFile(file); }
	
	public MultipartFile getFile()
	{
		return file;
	}
	public void setFile(MultipartFile file)
	{
		this.file = file;
	}
	public Long getIdCivilia() {
		return idCivilia;
	}
	public void setIdCivilia(Long idCivilia) {
		this.idCivilia = idCivilia;
	}
	
	
	
}
