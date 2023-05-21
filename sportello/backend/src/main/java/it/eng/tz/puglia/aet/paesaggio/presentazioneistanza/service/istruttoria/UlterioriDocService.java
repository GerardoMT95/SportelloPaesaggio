package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria;

import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.UlterioreDocumentazioneMultiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.UlterioreDocumentazioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.UlterioreDocSearch;
import it.eng.tz.puglia.bean.PaginatedList;


public interface UlterioriDocService {

	public void insertUlterioreDoc(UlterioreDocumentazioneDTO data, MultipartFile[] file,String urlFile) throws Exception;
	void insertUlterioreDoc(UlterioreDocumentazioneMultiDTO data, MultipartFile[] file, String urlFile) throws Exception;
	public PaginatedList<AllegatiDTO> search(UlterioreDocSearch search) throws Exception;
}
