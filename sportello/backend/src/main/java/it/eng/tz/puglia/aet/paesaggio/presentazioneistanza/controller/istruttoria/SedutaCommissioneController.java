package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.istruttoria;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.tika.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.AllegatiCommissioneLocaleDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.CLContainerDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.CLEntiCommissioniDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.CommissioneLocaleDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.SedutaCommissioneDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.SedutaCommissioneExtendedDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.SedutaCommissioneInputDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.SimpleAllegatiSedutaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.InavlidDateCLException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PraticaSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.SedutaCommissioneSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.PaesaggioOrganizzazioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.search.CommissioneLocaleSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.AllegatoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.GruppiRuoliService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.IstrPraticaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.SedutaCommissioneService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.Logging;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.bean.CodiceEsitoEnum;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.service.http.bean.CmsDownloadResponseBean;

@RestController
@RequestMapping("istruttoria/sedutaCommissione")
public class SedutaCommissioneController extends RoleAwareController
{	
	private final String FIND 		= "/find";
	private final String SEARCH 	= "/search";
	private final String INSERT 	= "/insert";
	private final String UPDATE 	= "/update";
	private final String DELETE 	= "/delete";
	private final String UPLOAD 	= "/remove";
	private final String REMOVE 	= "/remove";
	private final String DOWNLOAD 	= "/download";
	private final String PUBLIC 	= "/pubblica";
	private final String PRATICHE	= "/searchPraticheAssociate";
	private final String SEARCH_ALL = "/searchAttachments";
	private final String SEARCH_PRT = "/searchPraticheEsaminabili";
	
	private final String FIND_CL	= "/findCL";
	private final String SEARCH_CL  = "/searchCL";
	private final String POPULATE   = "/populate";
	private final String INSERT_CL  = "/insertCL";
	private final String UPDATE_CL  = "/updateCL";
	private final String ADD_USER	= "/addUser";
	private final String DEL_USER   = "/removeUser";
	private final String FIND_USERS = "/findUsers";
	private final String CHECK_DATE = "/checkDate";
	
	@Autowired private SedutaCommissioneService service;
	@Autowired private GruppiRuoliService grService;
	@Qualifier("istrPraticaService")  @Autowired private IstrPraticaService praticaService;
	@Qualifier("allegatiIstruttoria") @Autowired private AllegatoService allegatoService;
	
	@Autowired UserUtil userUtil;
	
	@Logging
	@GetMapping(value=FIND, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<SedutaCommissioneExtendedDTO>> find(@RequestParam("idSeduta")Long id) throws Exception
	{
		checkAbilitazioneFor(Gruppi.CL_, Gruppi.ED_);
		if(!service.checkOrganizzazione(id))
		{
			logger.error("La tua organizzazione non è abilitata per la seduta con id {}", id);
			throw new Exception("La tua organizzazione non è abilitata per la seduta con id " + id);
		}
		return okResponse(service.find(id));
	}
	
	@Logging
	@GetMapping(value=SEARCH, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<PaginatedList<SedutaCommissioneDto>>> search(SedutaCommissioneSearch search) throws Exception
	{
		return okResponse(service.search(search));
	}
	
	@Logging
	@PostMapping(value=INSERT, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<SedutaCommissioneInputDTO>> insert(@Valid @RequestBody SedutaCommissioneInputDTO entity) throws Exception
	{
		checkAbilitazioneFor(Gruppi.ED_);
		if(!grService.isValidRup())
		{
			logger.error("Errore: non puoi effettuare questa operazione in quanto non risulti essere RUP");
			throw new Exception("Errore: non puoi effettuare questa operazione in quanto non risulti essere RUP");
		}
		return okResponse(service.insert(entity));
	}
	
	@Logging
	@PutMapping(value=UPDATE, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<SedutaCommissioneInputDTO>> update(@Valid @RequestBody SedutaCommissioneInputDTO entity) throws Exception
	{
		checkAbilitazioneFor(Gruppi.ED_);
		if(!grService.isValidRup())
		{
			logger.error("Errore: non puoi effettuare questa operazione in quanto non risulti essere RUP");
			throw new Exception("Errore: non puoi effettuare questa operazione in quanto non risulti essere RUP");
		}
		if(!service.checkOrganizzazione(entity.getId()))
		{
			logger.error("La tua organizzazione non è abilitata per la seduta con id {}", entity.getId());
			throw new Exception("La tua organizzazione non è abilitata per la seduta con id " + entity.getId());
		}
		return okResponse(service.update(entity));
	}
	
	@Logging
	@DeleteMapping(value=DELETE, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<Integer>> delete(@RequestParam("idSeduta")Long id) throws Exception
	{
		checkAbilitazioneFor(Gruppi.ED_);
		if(!grService.isValidRup())
		{
			logger.error("Errore: non puoi effettuare questa operazione in quanto non risulti essere RUP");
			throw new Exception("Errore: non puoi effettuare questa operazione in quanto non risulti essere RUP");
		}
		return okResponse(service.delete(id));
	}
	
	@Logging
	@PostMapping(value=UPLOAD, produces=MEDIA_TYPE) 
	public ResponseEntity<BaseRestResponse<AllegatiCommissioneLocaleDTO>> upload(@RequestPart("file")MultipartFile file, @RequestPart("body")AllegatiCommissioneLocaleDTO body) throws Exception
	{
		checkAbilitazioneFor(Gruppi.CL_, Gruppi.ED_);
		return okResponse(service.upload(file, body.getTipoAllegato().getValue(), body.getPraticheAssociate(), body.getIdSeduta()));
	}
	
	@Logging
	@GetMapping(DOWNLOAD)
	public void download(@RequestParam("idAllegato")UUID idAllegato, 
						 @RequestParam("idSeduta")Long idSeduta,
						 HttpServletResponse response) throws Exception
	{
		CmsDownloadResponseBean bean = allegatoService.downloadAllegato(idAllegato, idSeduta);
		File f = new File(bean.getFileName());
		try 
		{
			MediaType mediaType = MediaType.parseMediaType(bean.getContentType());
			response.setContentType(mediaType.toString());
		} catch (Exception e) 
		{
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
		}
		response.setHeader("content-disposition", "inline");
		response.setContentLength((int)f.length());
		try(InputStream  is = new FileInputStream(f);
			OutputStream os = response.getOutputStream())
		{
			IOUtils.copy(is, os);
			try
			{
				logger.info("Il file esiste su disco. Lo rimuovo...");
				is.close();
				Path _path = Paths.get(f.getAbsolutePath());
				Files.deleteIfExists(_path);
			} catch (Exception e)
			{
				logger.error("Error in delete tmp file", e);
			}
		}
		finally
		{
			Path path = Paths.get(f.getAbsolutePath());
			Files.deleteIfExists(path);
		}
	}
	
	@Logging
	@DeleteMapping(value=REMOVE, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<Integer>> remove(@RequestParam("idAllegato")UUID idAllegato,
															@RequestParam("idSeduta")Long idSeduta) throws Exception
	{
		checkAbilitazioneFor(Gruppi.CL_, Gruppi.ED_);
		return okResponse(service.remove(idAllegato, idSeduta));
	}
	
	@Logging
	@PutMapping(value=PUBLIC, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<Boolean>> pubblicaSeduta(@RequestParam("idSeduta")Long idSeduta) throws Exception
	{
		checkAbilitazioneFor(Gruppi.CL_, Gruppi.ED_);
		if(!service.checkOrganizzazione(idSeduta))
		{
			logger.error("La tua organizzazione non è abilitata per la seduta con id {}", idSeduta);
			throw new Exception("La tua organizzazione non è abilitata per la seduta con id " + idSeduta);
		}
		service.pubblicaSeduta(idSeduta);
		return okResponse(true);
	}
	
	@Logging
	@GetMapping(value=PRATICHE, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<List<PraticaDTO>>> getPratiche(@RequestParam("idSeduta")Long idSeduta, @RequestParam("esaminate")Boolean esaminate) throws Exception
	{
		checkAbilitazioneFor(Gruppi.CL_, Gruppi.ED_);
		if(!service.checkOrganizzazione(idSeduta))
		{
			logger.error("La tua organizzazione non è abilitata per la seduta con id {}", idSeduta);
			throw new Exception("La tua organizzazione non è abilitata per la seduta con id " + idSeduta);
		}
		PraticaSearch search = new PraticaSearch();
		search.setIdSeduta(idSeduta);
		search.setEsaminato(esaminate);
		return okResponse(praticaService.searchAll(search));
	}
	
	@Logging
	@GetMapping(value=SEARCH_ALL, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<List<SimpleAllegatiSedutaDTO>>> searchAttachments(@RequestParam("idPratica")UUID idPratica) throws Exception
	{
		return okResponse(service.searchAttachments(idPratica));
	}
	
	@Logging
	@GetMapping(value=SEARCH_PRT, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<List<PraticaDTO>>> findPratiche(@RequestParam(value="idSeduta", required=false)Long idSeduta) throws Exception
	{
		CommissioneLocaleDTO commissione = null;
		PraticaSearch search = new PraticaSearch();
		if(idSeduta != null)
		{
			checkAbilitazioneFor(Gruppi.ADMIN, Gruppi.CL_, Gruppi.ED_);
			if(!service.checkOrganizzazione(idSeduta))
			{
				logger.error("La tua organizzazione non è abilitata per la seduta con id {}", idSeduta);
				throw new Exception("La tua organizzazione non è abilitata per la seduta con id " + idSeduta);
			}
			SedutaCommissioneExtendedDTO seduta = service.find(idSeduta);
			commissione = service.findCommissioneLocale((long)seduta.getIdOrganizzazione());
		}
		else
		{
			checkAbilitazioneFor(Gruppi.ED_);
			commissione = service.findCommissioneByEnte(userUtil.getLongId());
		}
		search.setEditable(true);
		search.setPraticheSeduta(true);
		search.setRangeRicercaDa(commissione.getDataCreazione());
		search.setRangeRicercaA(commissione.getDataTermine());
		return okResponse(praticaService.searchAll(search));
	}
	
	@Logging
	@GetMapping(value=FIND_CL, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<CommissioneLocaleDTO>> findCommissioneLocale(@RequestParam("idCommissione")Long idCommissione) throws Exception
	{
		checkAbilitazioneFor(Gruppi.ADMIN);
		return okResponse(service.findCommissioneLocale(idCommissione));
	}
	
	@Logging
	@GetMapping(value=SEARCH_CL, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<PaginatedList<PaesaggioOrganizzazioneDTO>>> searchCommissioneLocale(CommissioneLocaleSearch search) throws Exception
	{
		checkAbilitazioneFor(Gruppi.ADMIN);
		return okResponse(service.cercaCommissione(search));
	}
	
	@Logging
	@GetMapping(value=POPULATE, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<CLContainerDTO>> populate() throws Exception
	{
		checkAbilitazioneFor(Gruppi.ADMIN);
		CLContainerDTO container = new CLContainerDTO();
		container.setUsers(service.utentiAssociabili());
		container.setEnti(service.getEnti());
		return okResponse(container);
	}
	
	@Logging
	@PostMapping(value=INSERT_CL, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<CommissioneLocaleDTO>> insertCL(@RequestBody CommissioneLocaleDTO entity) throws Exception
	{
		checkAbilitazioneFor(Gruppi.ADMIN);
		try
		{
			return okResponse(service.creaCommissione(entity));
		} catch(InavlidDateCLException exc)
		{
			logger.error("Errore, range di date non valido! {}", exc.getMessage());
			BaseRestResponse<CommissioneLocaleDTO> response = new BaseRestResponse<CommissioneLocaleDTO>();
			response.setCodiceEsito(CodiceEsitoEnum.OK);
			response.setDescrizioneEsito("INVALID_DATE");
			return new ResponseEntity<BaseRestResponse<CommissioneLocaleDTO>>(response, HttpStatus.OK);
		}
	}
	
	@Logging
	@PutMapping(value=UPDATE_CL, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<CommissioneLocaleDTO>> updateCL(@RequestBody CommissioneLocaleDTO entity) throws Exception
	{
		checkAbilitazioneFor(Gruppi.ADMIN);
		try
		{
			return okResponse(service.aggiornaCommissione(entity));
		} catch(InavlidDateCLException exc)
		{
			logger.error("Errore, range di date non valido! {}", exc.getMessage());
			BaseRestResponse<CommissioneLocaleDTO> response = new BaseRestResponse<CommissioneLocaleDTO>();
			response.setCodiceEsito(CodiceEsitoEnum.OK);
			response.setDescrizioneEsito("INVALID_DATE");
			return new ResponseEntity<BaseRestResponse<CommissioneLocaleDTO>>(response, HttpStatus.OK);
		}
	}
	
	@Logging
	@PostMapping(value=ADD_USER, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<Boolean>> addUser(@RequestParam("username")String username, @RequestParam("idCommissione")Long idCommissione) throws Exception
	{
		checkAbilitazioneFor(Gruppi.ADMIN);
		service.aggiungiUtente(username, idCommissione);
		return okResponse(true);
	}
	
	@Logging
	@DeleteMapping(value=DEL_USER, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<Boolean>> removeUser(@RequestParam("username")String username, @RequestParam("idCommissione")Long idCommissione) throws Exception
	{
		checkAbilitazioneFor(Gruppi.ADMIN);
		service.eliminaUtente(username, idCommissione);
		return okResponse(true);
	}
	
	@Logging
	@GetMapping(value=FIND_USERS, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<List<PlainStringValueLabel>>> findUsersOfCL(@RequestParam("idCommissione") Long idCommissione) throws Exception
	{
		checkAbilitazioneFor(Gruppi.ADMIN);
		return okResponse(service.utentiCommissine(idCommissione));
	}
	
	@Logging
	@GetMapping(value=CHECK_DATE, produces=MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<List<CLEntiCommissioniDTO>>> checkDates(@RequestParam("enti")List<Long> enti,
																				   @RequestParam(value="idCommissione", required=false)Long idCommissione,
																				   @RequestParam("dataInizioVal") Date dataInizioVal,
																				   @RequestParam("dataFineVal") Date dataFineVal) throws Exception
	{
		checkAbilitazioneFor(Gruppi.ADMIN);
		return okResponse(service.evaluateRangeDate(idCommissione, enti, dataInizioVal, dataFineVal));
	}
}
