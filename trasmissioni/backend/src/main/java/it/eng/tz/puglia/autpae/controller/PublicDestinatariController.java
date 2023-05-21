package it.eng.tz.puglia.autpae.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.dto.TipologicaDestinatarioDTO;
import it.eng.tz.puglia.autpae.enumeratori.TipoDestinatario;
import it.eng.tz.puglia.autpae.service.interfacce.DestinatarioService;
import it.eng.tz.puglia.autpae.utility.LoggingAet;

/**
 * controller invocato dallo sportello paesaggio (servizio presentazione-istanza-be) 
 * per prelevare i destinatari della trasmissione finale del provvedimento.
 * 
 * @author Adriano Colaianni
 * @date 9 set 2022
 */
@RestController
@RequestMapping(value = "public/destinatari", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PublicDestinatariController extends _RestController 
{
	private final static Logger log = LoggerFactory.getLogger(PublicDestinatariController.class);
	private final static String FIND_DESTINATARI_ENTE_DELEGATO  = "/findDestinatariForEnteDelegato";
	private final static String FIND_DESTINATARI_REGIONE  = "/findDestinatariForRegione";
	private final static String FIND_DESTINATARI_SOPRINTENDENZE  = "/findDestinatariForSoprintendenze";
	private final static String FIND_DESTINATARI_PROVINCE  = "/findDestinatariForProvince";
	private final static String FIND_DESTINATARI_COMUNI  = "/findDestinatariForComuni";
	
	@Autowired private DestinatarioService destSvc;
	
	
	@GetMapping(value= {FIND_DESTINATARI_ENTE_DELEGATO}, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Operation(summary = "search destinatari ente delegato per id organizzazione")
	@LoggingAet
	public List<TipologicaDestinatarioDTO> findDestinatariForEnteDelegato(
			@Parameter(description="id organizzazione")
			@RequestParam Integer idOrganizzazione,
			@Parameter(description="id enti su cui filtrare")
			@RequestParam List<Integer> idEnti,
			@Parameter(description="tipo destinatario TO o CC")
			@RequestParam TipoDestinatario tipoDestinatario,
			HttpServletResponse response) throws Exception	{
		try {
			return destSvc.findDestinatariForEnteDelegato(idOrganizzazione,idEnti,tipoDestinatario);
		}catch(CustomOperationToAdviceException e) {
			response.setStatus(500);
			response.getOutputStream().print(e.getMessage());
			return null;
		}
	}
	
	@GetMapping(value= {FIND_DESTINATARI_REGIONE}, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Operation(summary = "search destinatari regione")
	@LoggingAet
	public List<TipologicaDestinatarioDTO> findDestinatariForRegione(
			@Parameter(description="id enti comuni su cui filtrare")
			@RequestParam List<Integer> idEnti,
			@Parameter(description="tipo destinatario TO o CC")
			@RequestParam TipoDestinatario tipoDestinatario,
			HttpServletResponse response) throws Exception	{
		try {
			return destSvc.findDestinatariForRegione(idEnti,tipoDestinatario);
		}catch(CustomOperationToAdviceException e) {
			response.setStatus(500);
			response.getOutputStream().print(e.getMessage());
			return null;
		}
		
	}
	
	@GetMapping(value= {FIND_DESTINATARI_SOPRINTENDENZE}, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Operation(summary = "search destinatari soprintendenze ")
	@LoggingAet
	public List<TipologicaDestinatarioDTO> findDestinatariForSoprintendenze(
			@Parameter(description="id enti comuni su cui filtrare")
			@RequestParam List<Integer> idEnti,
			@Parameter(description="tipo destinatario TO o CC")
			@RequestParam TipoDestinatario tipoDestinatario,
			HttpServletResponse response) throws Exception	{
		try {
			return destSvc.findDestinatariForSoprintendenze(idEnti,tipoDestinatario);
		}catch(CustomOperationToAdviceException e) {
			response.setStatus(500);
			response.getOutputStream().print(e.getMessage());
			return null;
		}
	}
	
	@GetMapping(value= {FIND_DESTINATARI_PROVINCE}, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Operation(summary = "search destinatari ente delegato per id organizzazione")
	@LoggingAet
	public List<TipologicaDestinatarioDTO> findDestinatariForProvince(
			@Parameter(description="id enti comuni su cui filtrare")
			@RequestParam List<Integer> idEnti,
			@Parameter(description="tipo destinatario TO o CC")
			@RequestParam TipoDestinatario tipoDestinatario,
			HttpServletResponse response) throws Exception	{
		try {
			return destSvc.findDestinatariForProvince(idEnti,tipoDestinatario);
		}catch(CustomOperationToAdviceException e) {
			response.setStatus(500);
			response.getOutputStream().print(e.getMessage());
			return null;
		}		
	}
	
	@GetMapping(value= {FIND_DESTINATARI_COMUNI}, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Operation(summary = "search destinatari ente delegato per id organizzazione")
	@LoggingAet
	public List<TipologicaDestinatarioDTO> findDestinatariForComuni(
			@Parameter(description="id enti comuni su cui filtrare")
			@RequestParam List<Integer> idEnti,
			@Parameter(description="tipo destinatario TO o CC")
			@RequestParam TipoDestinatario tipoDestinatario,
			HttpServletResponse response) throws Exception	{
		try {
			return destSvc.findDestinatariForComuni(idEnti,tipoDestinatario);
		}catch(CustomOperationToAdviceException e) {
			response.setStatus(500);
			response.getOutputStream().print(e.getMessage());
			return null;
		}		
	}
	
	
		
}