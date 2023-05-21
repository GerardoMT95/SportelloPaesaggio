package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.istruttoria;

import java.util.Collections;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.RoleAwareController;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.DettaglioCorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.ParereSoprintendenzaDetailsDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ParereSoprintendenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.ParereSoprintendenzaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.Logging;
import it.eng.tz.puglia.bean.BaseRestResponse;

@RestController
@RequestMapping("/istruttoria/parere")
public class ParereSoprintendenzaController extends RoleAwareController
{
	@Autowired private ParereSoprintendenzaService service;
	
	private final static String URL_FIND   	   = "/find";
	private final static String URL_INSERT 	   = "/insert";
	private final static String URL_UPDATE 	   = "/update";
	private final static String URL_DELETE	   = "/delete";
	private final static String URL_UPLOAD 	   = "/upload";
	private final static String URL_REMOVE	   = "/remove";
	private final static String URL_ALLEGA     = "/allega";
	private final static String URL_CREATE_COM = "/createComunication";
	
	@Logging
	@GetMapping(value=URL_FIND, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<ParereSoprintendenzaDetailsDTO>> find(@RequestParam("idPratica")UUID idPratica) throws Exception
	{
		this.checkDiMiaCompetenza(idPratica);
		return okResponse(service.find(idPratica));
	}
	
	@Logging
	@PostMapping(value=URL_INSERT, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<ParereSoprintendenzaDTO>> insert(@RequestBody ParereSoprintendenzaDTO entity) throws Exception
	{
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.REG_, Gruppi.SBAP_);
		return okResponse(service.insert(entity));
	}
	
	@Logging
	@PutMapping(value=URL_UPDATE, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<ParereSoprintendenzaDTO>> update(@RequestBody ParereSoprintendenzaDTO entity) throws Exception
	{
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.REG_, Gruppi.SBAP_);
		return okResponse(service.update(entity));
	}
	
	@Logging
	@DeleteMapping(value=URL_DELETE, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<Boolean>> delete(
			@RequestParam("idParere")Long idParere,
			@RequestParam("idPratica")UUID idPratica
			) throws Exception
	{
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.REG_, Gruppi.SBAP_);
		return okResponse(service.delete(idParere) == 1);
	}
	
	@Logging
	@PostMapping(value=URL_UPLOAD, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<AllegatiDTO>> upload(
			@RequestPart("file")MultipartFile file, 
			@RequestParam("type")Integer type,
			@RequestParam("idPratica")UUID idPratica,
			@RequestParam("idParere")Long idParere) throws Exception
	{
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.REG_, Gruppi.SBAP_);
		return okResponse(service.uploadAllegato(file, Collections.singletonList(type), idParere));
	}
	
	@Logging
	@DeleteMapping(value=URL_REMOVE, produces=MEDIA_TYPE) 
	public ResponseEntity<BaseRestResponse<Boolean>> remove(
			@RequestParam("idAllegato")UUID idAllegato,
			@RequestParam("idPratica")UUID idPratica,
			@RequestParam("idParere")Long idParere) throws Exception
	{
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.REG_, Gruppi.SBAP_);
		service.deleteAllegato(idAllegato, idParere);
		return okResponse(true);
	}
	
	@Logging
	@PutMapping(value=URL_ALLEGA, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<ParereSoprintendenzaDTO>> allegaParere(@RequestBody ParereSoprintendenzaDTO entity) throws Exception
	{
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.REG_, Gruppi.SBAP_);
		return okResponse(service.allegaParere(entity));
	}
	
	@Logging
	@PostMapping(value=URL_CREATE_COM, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<DettaglioCorrispondenzaDTO>> createComunication(
			@RequestParam("idParere")Long idParere,
			@RequestParam("idPratica")UUID idPratica
			) throws Exception
	{
		checkAbilitazioneFor(Gruppi.SBAP_);
		return okResponse(service.creaComunicazione(idParere));
	}
}
