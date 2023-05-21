package it.eng.tz.puglia.autpae.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.autpae.civilia.migration.util.MockBlob;
import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.dbMapping.Allegato;
import it.eng.tz.puglia.autpae.dto.AllegatoCustomDTO;
import it.eng.tz.puglia.autpae.dto.SelectOptionDto;
import it.eng.tz.puglia.autpae.dto.TipologicaNumeriDTO;
import it.eng.tz.puglia.autpae.entity.AllegatoDTO;
import it.eng.tz.puglia.autpae.entity.AllegatoFascicoloDTO;
import it.eng.tz.puglia.autpae.entity.AllegatoObbligatorioDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloDTO;
import it.eng.tz.puglia.autpae.entity.RicevutaDTO;
import it.eng.tz.puglia.autpae.enumeratori.ErroriValidazioneBE;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;
import it.eng.tz.puglia.autpae.enumeratori.TipoProcedimento;
import it.eng.tz.puglia.autpae.repository.AllegatoFullRepository;
import it.eng.tz.puglia.autpae.search.AllegatoCorrispondenzaSearch;
import it.eng.tz.puglia.autpae.search.AllegatoEnteSearch;
import it.eng.tz.puglia.autpae.search.AllegatoFascicoloSearch;
import it.eng.tz.puglia.autpae.search.AllegatoObbligatorioSearch;
import it.eng.tz.puglia.autpae.search.AllegatoSearch;
import it.eng.tz.puglia.autpae.search.RicevutaSearch;
import it.eng.tz.puglia.autpae.search.UlterioreDocumentazioneSearch;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoCorrispondenzaService;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoEnteService;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoFascicoloService;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoObbligatorioService;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoService;
import it.eng.tz.puglia.autpae.service.interfacce.CorrispondenzaService;
import it.eng.tz.puglia.autpae.service.interfacce.FascicoloService;
import it.eng.tz.puglia.autpae.service.interfacce.ProtocollazioneService;
import it.eng.tz.puglia.autpae.service.interfacce.RicevutaService;
import it.eng.tz.puglia.autpae.utility.CheckSumUtil;
import it.eng.tz.puglia.autpae.utility.DateUtil;
import it.eng.tz.puglia.autpae.utility.FileWrapper;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.error.exception.CustomCmisException;
import it.eng.tz.puglia.error.exception.CustomOperationException;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.service.http.IHttpClientService;
import it.eng.tz.puglia.service.http.bean.ProtocolloRequestBean;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.UserUtil;
import it.eng.tz.puglia.servizi_esterni.protocollo.ProtocolNumberType;
import it.eng.tz.puglia.servizi_esterni.protocollo.model.SegnaturaProtocollo;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;								   

@Service
public class AllegatoServiceImpl implements AllegatoService {
	
	@Value("${cms.url.upload}")
	private String uploadUrl;

	@Value("${cms.url.download}")
	private String downloadUrl;

	@Value("${cms.url.delete}")
	private String deleteUrl;
	
	@Value("${cms.codice.applicazione}")
	private String nomeApplicazione;
	
//	@Value("${path.cms}")
//	private String pathTransito;
 
	@Value("${alfresco.path.base}")
    private String cmsBasePath;
    
//    @Value("${filesystem.temppath}")
//    private String tempPath;
    
	private static final Logger log = LoggerFactory.getLogger(AllegatoServiceImpl.class);
//	private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "User cannot access attachments for plan exclusion of this municipality";

	@Autowired private AllegatoFullRepository repository;
	
	@Autowired private IHttpClientService cmsService;										  
	@Autowired private ApplicationProperties props;
	
	@Autowired
	UserUtil userUtil;
	@Autowired MessageSource messageSource;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// BaseRepository
	
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )	public 		  List<AllegatoDTO>   select() 					    throws Exception { return repository.select(); 	     }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )	public long 					  count (AllegatoSearch filter) throws Exception { return repository.count (filter); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )	public 			   AllegatoDTO 	  find  (Long pk) 			    throws Exception { return repository.find  (pk); 	 }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false)	public Long 					  insert(AllegatoDTO entity) 	throws Exception { return repository.insert(entity); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false)	public int 						  update(AllegatoDTO entity)	throws Exception { return repository.update(entity); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false)	public int 						  delete(AllegatoSearch search) throws Exception { return repository.delete(search); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )	public PaginatedList<AllegatoDTO> search(AllegatoSearch filter) throws Exception { return repository.search(filter); }
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// FullRepository
	
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )	public String 				     findIdAlfrescoById		 	 (Long id) 													  throws Exception {
			  																													  	return 				  repository.findIdAlfrescoById		 	 (	   id);  																   }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )	public List<AllegatoDTO> 	     datiAllegato				 (Long idFascicolo, List<TipoAllegato> listaTipi) 			  throws Exception {
			  																													  	return 				  repository.datiAllegato				 (	   idFascicolo, 			 	   listaTipi);							   }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false)	public int 					     protocolla				 	 (long idAllegato, String protocolloIn, String protocolloOut) throws Exception {
			  																													  	return 				  repository.protocolla				  	 (	   idAllegato,		  protocolloIn,		   protocolloOut);	 			   }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )	public List<TipologicaNumeriDTO> nomeAllegatiCorrispondenza  (Long idCorrispondenza) 									  throws Exception {
//			  																													  	return 				  repository.nomeAllegatiCorrispondenza  (	   idCorrispondenza);  													   }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )	public List<AllegatoCustomDTO>   infoAllegatiCorrispondenza  (Long idCorrispondenza) 									  throws Exception {
																																  	return 			      repository.infoAllegatiCorrispondenza  (	   idCorrispondenza);	 												   }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )	public TipologicaNumeriDTO 	     cercaRecente			     (AllegatoSearch filter, List<Long> listaId) 				  throws Exception {
																																  	return 		  		  repository.cercaRecente			     (				 filter, 			listaId);								   }
/*  @Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )	public Long 				     findIdByIdAlfresco		     (String idAlfresco) 										  throws Exception {
			  																													  	return 		  		  repository.findIdByIdAlfresco		     (		 idAlfresco); 					   									   } */
/*	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false)	public int 					     aggiorna					 (long idAllegato, String descrizione, String titolo) 		  throws Exception {
			 																													  	return 		  		  repository.aggiorna					 (	   idAllegato, 		  descrizione, 		  titolo); 		  				   } */
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )	public PaginatedList<Long> 	     searchIdDocumentiUD		 (UlterioreDocumentazioneSearch filter) 					  throws Exception {
																																  	return 				  repository.searchIdDocumentiUD		 (								filter);									   }
	
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )	public List<AllegatoDTO> 	     infoAllegatiRichiestaPostTrasmissione(Long idFascicolo, Long idRichiestaPostTrasmissione) 			  throws Exception {
		  	return 				  repository.datiAllegatoRichiestaPostTrasmissione(idFascicolo, idRichiestaPostTrasmissione);							   }
	
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )	public List<AllegatoDTO> 	     infoAllegatiAnnotazioniInterne(Long idFascicolo, Long idAnnotazioneInterna) 			  throws Exception {
	  		return 				  repository.datiAllegatoAnnotazioniInterne(idFascicolo, idAnnotazioneInterna);							   }

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Altri Metodi
    
	@Autowired private AllegatoObbligatorioService allegatoObbligatorioService;
	
	@Autowired private AllegatoEnteService allegatoEnteService;
	
	@Autowired private AllegatoFascicoloService allegatoFascicoloService;
	
	@Autowired private AllegatoCorrispondenzaService allegatoCorrispondenzaService;
	
	@Autowired private FascicoloService fascicoloService;
	
	@Autowired private CorrispondenzaService corrispondenzaService;
	
	@Autowired private RicevutaService ricevutaService;
	
	@Autowired private ProtocollazioneService protocollazioneService;
	
	//@Autowired private IFeignAlfrescoService alfrescoService;
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public ArrayList<AllegatoCustomDTO> tabAllegati(TipoProcedimento tipoProcedimento) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		ArrayList<AllegatoCustomDTO> lista = new ArrayList<AllegatoCustomDTO>(8);
		
		lista.add(new AllegatoCustomDTO(TipoAllegato.ISTANZA));
		lista.add(new AllegatoCustomDTO(TipoAllegato.VERBALE));
		lista.add(new AllegatoCustomDTO(TipoAllegato.PARERE));
		lista.add(new AllegatoCustomDTO(TipoAllegato.PREAVVISO));
		lista.add(new AllegatoCustomDTO(TipoAllegato.ALTRI_PARERI));
		lista.add(new AllegatoCustomDTO(TipoAllegato.ELABORATI));
		lista.add(new AllegatoCustomDTO(TipoAllegato.PARERI_SCHEDA));
		lista.add(new AllegatoCustomDTO(TipoAllegato.SCHEDA_PROGETTO));
		
		List<AllegatoObbligatorioDTO> listaObbligatori = allegatoObbligatorioService.search(new AllegatoObbligatorioSearch(null,tipoProcedimento)).getList();
		
		if (!listaObbligatori.isEmpty()) {
			 listaObbligatori.forEach(elemExt -> {
				lista.forEach(elemInt -> {
					if (elemExt.getType().equals(elemInt.getTipo()))
						elemInt.setObbligatorio(true);
				});
			});
		}
		return lista;
	}
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, timeout=60000, readOnly=true, rollbackFor=Exception.class)
	public ArrayList<AllegatoCustomDTO> datiAllegati(long idFascicolo) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		List<TipoAllegato> listaTipi = null;
		
		if (props.isPareri()) {
			listaTipi = new ArrayList<>( List.of(TipoAllegato.ISTANZA, 
												 TipoAllegato.ELABORATI,
												 TipoAllegato.LOCALIZZAZIONE,
												 TipoAllegato.INTEGRAZIONI) );
		}
		else if (props.isAutPae()) {
			listaTipi = new ArrayList<>( List.of(TipoAllegato.ISTANZA, 
												 TipoAllegato.VERBALE, 
												 TipoAllegato.LOCALIZZAZIONE,
												 TipoAllegato.PARERE,
												 TipoAllegato.PREAVVISO, 
												 TipoAllegato.ALTRI_PARERI, 
												 TipoAllegato.ELABORATI,
												 TipoAllegato.PARERI_SCHEDA, 
												 TipoAllegato.SCHEDA_PROGETTO) );
		}
		else if(props.isPutt()) {
			listaTipi = new ArrayList<>( List.of(TipoAllegato.ATTESTAZIONE_CONFORMITA, 
												 TipoAllegato.PREAVVISO, 
												 TipoAllegato.RICHIESTA_SOPRINTENDENZA,
												 TipoAllegato.PROPOSTA_PROVVEDIMENTO, 
												 TipoAllegato.DOCUMENTAZIONE_FOTOGRAFICA, 
												 TipoAllegato.PARERE,
												 TipoAllegato.RETTIFICA_AUTORIZZAZIONE,
												 TipoAllegato.VERBALE, 
												 TipoAllegato.ISTANZA, 
												 TipoAllegato.ALTRI_PARERI, 
												 TipoAllegato.ELABORATI, 
												 TipoAllegato.ALTRO,
												 TipoAllegato.LOCALIZZAZIONE,
												 TipoAllegato.COMUNICAZIONE_SOPRINTENDENZA_ESITO_NON_COMPLETATO) );
		}
		
		List<AllegatoDTO> listaAllegati = this.datiAllegato(idFascicolo, listaTipi);
		
		// devo creare ora una lista che non abbia allegati duplicati, perchè "listaAllegati" duplica lo stesso allegato per ogni "tipo"
		
		List<AllegatoDTO> listaAllegatiDistinti = new ArrayList<AllegatoDTO>();
		
		for (AllegatoDTO allegato : listaAllegati) {
			boolean presente = false;
			for (AllegatoDTO distinct : listaAllegatiDistinti) {
				if (distinct.getId()==allegato.getId())
					presente = true;
			};
			if (presente==false)
				listaAllegatiDistinti.add(allegato);
		};
		
		ArrayList<AllegatoCustomDTO> lista = new ArrayList<AllegatoCustomDTO>(listaAllegatiDistinti.size());
		for (AllegatoDTO allegato : listaAllegatiDistinti) {
			AllegatoCustomDTO nuovoAllegato = new AllegatoCustomDTO(allegato);
			List<AllegatoFascicoloDTO> listaAF = allegatoFascicoloService.search(new AllegatoFascicoloSearch(allegato.getId(), null, idFascicolo)).getList();
			listaAF.forEach(af->{
				if(nuovoAllegato.getTipi()==null) {
					nuovoAllegato.setTipi(new ArrayList<>());
				}
				nuovoAllegato.getTipi().add(af.getType());
			});
			lista.add(nuovoAllegato);
		};
		
		AllegatoObbligatorioSearch search = new AllegatoObbligatorioSearch();
		search.setTipoProcedimento(fascicoloService.find(idFascicolo).getTipoProcedimento());
		List<AllegatoObbligatorioDTO> listaObbligatori = allegatoObbligatorioService.search(search).getList();
		if (!listaObbligatori.isEmpty()) {
			 lista.forEach(allegato->{
				listaObbligatori.forEach(obbligatorio->{
					if (allegato.getTipo().equals(obbligatorio.getType()))
						allegato.setObbligatorio(true);					
				});
			});
		}
		return lista;
	}
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public AllegatoCustomDTO infoAllegato(long idAllegato) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		return new AllegatoCustomDTO(repository.find(idAllegato));
	}
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public AllegatoCustomDTO inserisciAllegato(List<Long> idFascicoli, TipoAllegato tipoAllegato, MultipartFile file, AllegatoDTO informazioni) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		return this.inserisciAllegato(idFascicoli, Collections.singletonList(tipoAllegato), file, informazioni);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public AllegatoCustomDTO inserisciAllegato(List<Long> idFascicoli, List<TipoAllegato> tipiAllegato, MultipartFile file, AllegatoDTO informazioni) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		return this.inserisciAllegatoDef(idFascicoli, tipiAllegato, file, informazioni,null);
	}
	
	/**
	 * 
	 * @autor Adriano Colaianni
	 * @date 28 apr 2021
	 * @param idFascicoli
	 * @param tipiAllegato
	 * @return  es. /codicefascicolo/tipoallegato
	 */
	private String buildPathCms(List<Long> idFascicoli, List<TipoAllegato> tipiAllegato) {
		StringBuilder sb=new StringBuilder();
		if(idFascicoli.size()>1) {
			sb.append("/CONDIVISO_"+DateUtil.dataParse(new Date()));
		}else if(idFascicoli.size()==1) {
			FascicoloDTO fascicolo;
			try {
				fascicolo = fascicoloService.find(idFascicoli.get(0));
				sb.append("/"+fascicolo.getCodice());
			} catch (Exception e) {
				sb.append("/"+idFascicoli.get(0));
				log.error("Errore nella find del Fascicolo con id "+idFascicoli.get(0));
			}
		}
		//subpath legata al tipo di file
		if(tipiAllegato.size()>0 && StringUtil.isNotEmpty(tipiAllegato.get(0).getSubPathCms())){
			sb.append("/"+tipiAllegato.get(0).getSubPathCms());
		}
		return sb.toString(); 
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public AllegatoCustomDTO inserisciAllegato(List<Long> idFascicoli, List<TipoAllegato> tipiAllegato, MultipartFile file, AllegatoDTO informazioni,String pathCms) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		return this.inserisciAllegatoDef(idFascicoli, tipiAllegato, file, informazioni,pathCms);
	}
		
	private AllegatoCustomDTO inserisciAllegatoDef(List<Long> idFascicoli, List<TipoAllegato> tipiAllegato, MultipartFile file, AllegatoDTO informazioni,String pathCms) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		if (informazioni==null)
			informazioni = new AllegatoDTO();
		
//		controlloVincoliAllegato(tipoAllegato, file);
		
		String fileName = file.getOriginalFilename();
		
		   if (fileName==null || fileName.isEmpty())
			   fileName = file.getName();
		   if (fileName==null || fileName.isEmpty())
			   fileName = informazioni.getNome();
		
		String mimeType = file.getContentType();		// TODO: utilizzare SOLO Tika !!!
		
		   if (mimeType==null || mimeType.isEmpty())
			   mimeType = informazioni.getMimeType();
		
		if ((tipiAllegato.size()==1 && tipiAllegato.get(0).equals(TipoAllegato.PROVVEDIMENTO_FINALE_ESITO_LETTERA))) {
			fileName = informazioni.getNome();
			mimeType = informazioni.getMimeType();
		}
		
		// creo l'AllegatoDTO (senza l'ID Alfresco) // (devo prendere i dati dal MultipartFile prima dell'upload su Alfresco perchè cancella il file)
		AllegatoDTO allegatoDTO = new AllegatoDTO();
		
		allegatoDTO.setNome(fileName);
		//allegatoDTO.setDescrizione((tipiAllegato.size()==1 && tipiAllegato.get(0).equals(TipoAllegato.LOCALIZZAZIONE)) ? TipoAllegato.LOCALIZZAZIONE.getTextValue() : "");
		allegatoDTO.setDescrizione(tipiAllegato.size()==1 && tipiAllegato.get(0)!=null? tipiAllegato.get(0).getTextValue() : "");
		allegatoDTO.setMimeType(mimeType);
		allegatoDTO.setChecksum(CheckSumUtil.getCheckSum(file));
		allegatoDTO.setDimensione(Math.toIntExact(file.getSize()));
		allegatoDTO.setDeleted(false);
		if(informazioni.getDescrizione()!=null) {
			allegatoDTO.setDescrizione(informazioni.getDescrizione());	
		}
		allegatoDTO.setTitolo(informazioni.getTitolo());
		allegatoDTO.setDataProtocolloIn(informazioni.getDataProtocolloIn());
		allegatoDTO.setDataProtocolloOut(informazioni.getDataProtocolloOut());
		allegatoDTO.setNumeroProtocolloIn(informazioni.getNumeroProtocolloIn());
		allegatoDTO.setNumeroProtocolloOut(informazioni.getNumeroProtocolloOut());
		
		// allegatoDTO.setDataCaricamento(new Date());
		
		// upload del MultipartFile in Alfresco ----> recupero l'ID Alfresco ----> lo inserisco nell'AllegatoDTO
		if(StringUtil.isEmpty(pathCms)) {
			//costruisco il path cms
			String subPathCmsBuilded=this.buildPathCms(idFascicoli,tipiAllegato);
			//allegatoDTO.setContenuto(this.alfrescoUpload(file, idFascicoli.size()==1 ? String.valueOf(idFascicoli.get(0)) : "CONDIVISO_"+DateUtil.dataParse(new Date())));
			allegatoDTO.setPathCms(cmsBasePath+subPathCmsBuilded);
			allegatoDTO.setContenuto(this.doUploadWithCommonStarter(file, allegatoDTO.getPathCms()));
			
		}else {
			allegatoDTO.setPathCms(cmsBasePath+"/"+pathCms);
			allegatoDTO.setContenuto(this.doUploadWithCommonStarter(file,allegatoDTO.getPathCms()));
		}
		
		// inserisco l'AllegatoDTO nel DB ----> recupero l'ID Allegato
		Long idAllegato = repository.insert(allegatoDTO);
		allegatoDTO.setId(idAllegato);
		allegatoDTO.setDataCaricamento(new Date());
		// protocollo eventualmente il file
		if (informazioni.getNumeroProtocolloIn() != null
				&& informazioni.getNumeroProtocolloIn().equals("DA PROTOCOLLARE")) {
			String retProto = this.protocolla(file, allegatoDTO, ProtocolNumberType.I,null);
			allegatoDTO.setNumeroProtocolloIn(retProto);
			this.protocolla(idAllegato, allegatoDTO.getNumeroProtocolloIn(), null);
		}
		if (informazioni.getNumeroProtocolloOut() != null
				&& informazioni.getNumeroProtocolloOut().equals("DA PROTOCOLLARE")) {
			allegatoDTO.setNumeroProtocolloOut(this.protocolla(file, allegatoDTO, ProtocolNumberType.O,null));
			this.protocolla(idAllegato, null, allegatoDTO.getNumeroProtocolloOut());
		}

		if (idFascicoli != null && idFascicoli.size() > 0) {//se l'allegato non ha legami con fascicoli (es file di configurazione).....
			idFascicoli.forEach(idFascicolo -> {
				for (TipoAllegato tipoAllegato : tipiAllegato) {
					// creo l'AllegatoFascicoloDTO con i dati in ingresso al metodo
					AllegatoFascicoloDTO allegatoFascicolo = new AllegatoFascicoloDTO();
					allegatoFascicolo.setIdAllegato(idAllegato);
					allegatoFascicolo.setIdFascicolo(idFascicolo);
					allegatoFascicolo.setType(tipoAllegato);
					// inserisco il nuovo record nella tabella Allegato_Fascicolo
					try {
						allegatoFascicoloService.insert(allegatoFascicolo);
					} catch (Exception e) {
						log.error("Errore nell'inserimento del record nella tabella Allegato_Fascicolo",e);
					}
				}
			});
		}
		
		return new AllegatoCustomDTO(allegatoDTO);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public AllegatoCustomDTO inserisciPdfPreTimbratura(List<Long> idFascicoli,MultipartFile file, AllegatoDTO informazioni) throws Exception {
		List<TipoAllegato> tipi=new ArrayList<>();
		tipi.add(TipoAllegato.PDF_PRE_TIMBRATURA);
		return this.inserisciAllegatoDef(idFascicoli, tipi, file, informazioni, null);
		
	}
	
	@Override
	@Transactional(propagation=Propagation.MANDATORY, rollbackFor={Exception.class}, timeout=60000)
	public String protocolla(MultipartFile file, AllegatoDTO informazioni, ProtocolNumberType tipo,ProtocolloRequestBean beanProto) throws Exception {
		SegnaturaProtocollo segnaturaProtocollo = doProtocolla(file, informazioni, tipo, beanProto);
		return segnaturaProtocollo.toString();
	}
	private SegnaturaProtocollo doProtocolla(MultipartFile file, AllegatoDTO informazioni, ProtocolNumberType tipo,
			ProtocolloRequestBean beanProto) throws CustomOperationToAdviceException {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		SegnaturaProtocollo segnaturaProtocollo = null;
		try {
			String mittenteProtocollo = "";
			if (userUtil.hasUserLogged()) {
				mittenteProtocollo = userUtil.getMyProfile().getNome().concat(" ").concat(userUtil.getMyProfile().getCognome());
			} else {
				mittenteProtocollo = props.getBatchUsr();
			}
			ProtocolloRequestBean toSend=beanProto;
			if(toSend==null) {
				toSend=new ProtocolloRequestBean();
				toSend.setInOut(tipo.name());
				toSend.setTitoloDocumento(informazioni.getDescrizione());
				toSend.setOggetto(informazioni.getContenuto());
				toSend.setTipoRiferimento("MIME");
				toSend.setTipoDocumento(informazioni.getMimeType());
			}
			segnaturaProtocollo = protocollazioneService.ottieniNumeroProtocollo(file, informazioni, tipo,mittenteProtocollo,toSend);
			if (segnaturaProtocollo==null || StringUtil.isEmpty(segnaturaProtocollo.getNumeroProtocollo()))
				throw new Exception();
		} catch (Exception e) {
			log.error("Errore nella protocollazione del file. Impossibile comunicare con i server della Regione. Riprovare più tardi ",e);
			throw new CustomOperationToAdviceException("Errore nella protocollazione del file. Impossibile comunicare con i server della Regione. Riprovare più tardi");
		}
		LocalDate dd = LocalDate.parse(segnaturaProtocollo.getDataRegistrazione(), DateTimeFormatter.ofPattern("ddMMyyyy"));
		Date d=Date.from(dd.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		if (informazioni!=null && tipo==ProtocolNumberType.I)	informazioni.setDataProtocolloIn(d);
		if (informazioni!=null && tipo==ProtocolNumberType.O)	informazioni.setDataProtocolloOut(d);
		return segnaturaProtocollo;
	}
	
	
	@Override
	@Transactional(propagation=Propagation.MANDATORY, rollbackFor={Exception.class}, timeout=60000)
	public SegnaturaProtocollo protocollaEgetSegnatura(MultipartFile file, AllegatoDTO informazioni, ProtocolNumberType tipo,ProtocolloRequestBean beanProto) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		return doProtocolla(file, informazioni, tipo, beanProto);
	}

	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public TipologicaNumeriDTO findRicevutaTrasmissione(long idFascicolo) throws Exception {
		return this.doFindRicevutaTrasmissione(idFascicolo);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public TipologicaNumeriDTO getRicevutaTrasmissione(long idFascicolo) {
		TipologicaNumeriDTO ret=null;
		try {
			ret= this.doFindRicevutaTrasmissione(idFascicolo);
		} catch (Exception e) {
			
		}
		return ret;
	}
	
	private TipologicaNumeriDTO doFindRicevutaTrasmissione(long idFascicolo) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		AllegatoFascicoloSearch searchAF = new AllegatoFascicoloSearch();
		searchAF.setIdFascicolo(idFascicolo);
		searchAF.setType(TipoAllegato.RICEVUTA_TRASMISSIONE);
		List<AllegatoFascicoloDTO> listaAF = allegatoFascicoloService.search(searchAF).getList();
		TipologicaNumeriDTO infoRT = null;
		if (listaAF==null || listaAF.isEmpty()) {
			log.error("Nessuna Ricevuta di trasmissione trovata per il fascicolo con id="+idFascicolo);
			throw new Exception("Nessuna Ricevuta di trasmissione trovata per il fascicolo con id="+idFascicolo);
		}
		else if (listaAF.size()==1) {
			AllegatoDTO RT = repository.find(listaAF.get(0).getIdAllegato());
			infoRT = new TipologicaNumeriDTO(RT.getId(), RT.getNumeroProtocolloOut());
		}
		else {
			List<Long> listaId = new ArrayList<Long>(listaAF.size());
			listaAF.forEach(elem->{
				listaId.add(elem.getIdAllegato());
			});
			AllegatoSearch searchA = new AllegatoSearch();
			searchA.setDeleted(false);
			searchA.setUtenteInserimento(fascicoloService.find(idFascicolo).getUsernameUtenteTrasmissione());
			searchA.setColonna(Allegato.data_caricamento);
			searchA.setDirezione(AllegatoSearch.Direction.DESC);
			try {
				infoRT = this.cercaRecente(searchA, listaId);
			} catch (Exception e) {
				log.error("Errore nella ricerca dell'allegato più recente",e);
				throw e;
//				throw new Exception("Errore nella ricerca dell'allegato più recente");
			}
		}
		return infoRT;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public void eliminaAllegato(long idAllegato) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		AllegatoFascicoloSearch search1 = new AllegatoFascicoloSearch();
		search1.setIdAllegato(idAllegato);
		if (allegatoFascicoloService.delete(search1)<1)
			throw new Exception("Errore nella cancellazione dell'allegatoFascicolo con id="+idAllegato);
		
		this.alfrescoDelete(idAllegato);
		
		AllegatoEnteSearch searchAE = new AllegatoEnteSearch();
		searchAE.setIdAllegato(idAllegato);
		if (allegatoEnteService.count(searchAE)>0)
			if (allegatoEnteService.delete(searchAE)<1)
				throw new Exception("Errore nella cancellazione dell'allegatoEnte con idAllegato="+idAllegato);
		
		
		AllegatoCorrispondenzaSearch searchAC = new AllegatoCorrispondenzaSearch();
		searchAC.setIdAllegato(idAllegato);
		if (allegatoCorrispondenzaService.count(searchAC)>0)
			if (allegatoCorrispondenzaService.delete(searchAC)<1)
				throw new Exception("Errore nella cancellazione dell'allegatoCorrispondenza con idAllegato="+idAllegato);
		
		
		AllegatoSearch searchA = new AllegatoSearch();
		searchA.setId(idAllegato);
		if (repository.delete(searchA)!=1)
			throw new Exception("Errore nella cancellazione dell'allegato con id="+idAllegato);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public void eliminaAllegatoWithoutCheck(long idAllegato) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		AllegatoFascicoloSearch search1 = new AllegatoFascicoloSearch();
		search1.setIdAllegato(idAllegato);
		allegatoFascicoloService.delete(search1);
		this.alfrescoDelete(idAllegato);
		
		AllegatoEnteSearch searchAE = new AllegatoEnteSearch();
		searchAE.setIdAllegato(idAllegato);
		if (allegatoEnteService.count(searchAE)>0)
		{
			allegatoEnteService.delete(searchAE);
		}
		AllegatoCorrispondenzaSearch searchAC = new AllegatoCorrispondenzaSearch();
		searchAC.setIdAllegato(idAllegato);
		if (allegatoCorrispondenzaService.count(searchAC)>0)
		{
			allegatoCorrispondenzaService.delete(searchAC);
		}
		AllegatoSearch searchA = new AllegatoSearch();
		searchA.setId(idAllegato);
		if (repository.delete(searchA)!=1)
			throw new Exception("Errore nella cancellazione dell'allegato con id="+idAllegato);
	}

	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public void eliminaAllegati(List<Long> listaAllegati) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		if (listaAllegati!=null && !listaAllegati.isEmpty())
			listaAllegati.forEach(allegato-> {
				try {
					this.eliminaAllegato(allegato);
				} catch (Exception e) {
					log.error("Errore nell'eliminazione dell'allegato con id="+allegato,e);
				}
			});
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, timeout = 60000, readOnly = true, rollbackFor=Exception.class)
	public List<SelectOptionDto> validazione(long idFascicolo, TipoProcedimento tipoProcedimento) throws Exception {
		log.trace("Entrato nel service: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");
		List<SelectOptionDto> erroriValidazione = new ArrayList<>();
		AllegatoObbligatorioSearch searchAO = new AllegatoObbligatorioSearch(null, tipoProcedimento);
		List<AllegatoObbligatorioDTO> listaAO = allegatoObbligatorioService.search(searchAO).getList();
		if (listaAO != null && !listaAO.isEmpty()) {
			AllegatoFascicoloSearch searchAF = new AllegatoFascicoloSearch(null, null, idFascicolo);
			for (AllegatoObbligatorioDTO allegatoObbligatorio : listaAO) {
				searchAF.setType(allegatoObbligatorio.getType());
				if (allegatoFascicoloService.count(searchAF) < 1) {
					log.error("L'allegato obbligatorio '" + allegatoObbligatorio.getType().getTextValue()
							+ "' non è presente!");
					// return false;
					if (allegatoObbligatorio != null
							&& ! Arrays.asList(TipoAllegato.PROVVEDIMENTO_FINALE, TipoAllegato.PARERE_MIBAC)
									.contains(allegatoObbligatorio.getType())) // escludo quelli del pannello
																				// provvedimento....
					{
						erroriValidazione
								.add(new SelectOptionDto(ErroriValidazioneBE.ALLEGATO_OBBLIGATORIO_MANCANTE.name(),
										messageSource.getMessage(
												"validazioneFascicolo."
														+ ErroriValidazioneBE.ALLEGATO_OBBLIGATORIO_MANCANTE.name(),
												new Object[] { allegatoObbligatorio.getType().getTextValue() },
												Locale.getDefault())));
					}

				}
			}
		}
		return erroriValidazione;
	}
	

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public File downloadFile(TipoAllegato tipoAllegato, Long idAllegato, Long idFascicolo, Long idCorrispondenza) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		String idAlfresco = downloadAllegato(tipoAllegato, idAllegato, idFascicolo, idCorrispondenza);
		return this.downloadAlfresco(idAlfresco);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public File downloadRicevutaMail(TipoAllegato tipoAllegato, Long idRicevuta) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		String idAlfresco = getIdAlfrescoRicevutaMail(tipoAllegato,idRicevuta);
		return this.downloadAlfresco(idAlfresco);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public FileWrapper downloadAllegatoByTipoEidFascicolo(TipoAllegato tipoAllegato, Long idFascicolo) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		AllegatoDTO allegato=null;
		AllegatoFascicoloSearch searchAF = new AllegatoFascicoloSearch();
		searchAF.setIdFascicolo(idFascicolo);
		searchAF.setType(tipoAllegato);
		List<AllegatoFascicoloDTO> listaAF = allegatoFascicoloService.search(searchAF).getList();
		if (listaAF==null || listaAF.isEmpty()) {
			log.error("Nessun allegato trovato per i parametri in ingresso");
			throw new Exception("Nessun allegato trovato per i parametri in ingresso");
		}
		else if (listaAF.size()>1) {
			log.error("Errore. Trovati " + listaAF.size() + " allegati per i parametri in ingresso");
			//throw new Exception("Errore. Trovati " + listaAF.size() + " allegati per i parametri in ingresso");
			int i=0;
			boolean trovato = false;
			while(i<listaAF.size() && !trovato) {
				allegato= repository.find(listaAF.get(i).getIdAllegato());
				if(!allegato.getDeleted())
					trovato = true;
				i++;
			}
			if(!trovato) {
				log.error("Nessun allegato valido trovato");
				throw new Exception("Nessun allegato valido trovato");				
			}
		}
		else {
			 allegato= repository.find(listaAF.get(0).getIdAllegato());
		}
		File outFile=this.downloadAlfresco(allegato.getContenuto());
		FileWrapper mfOut=new FileWrapper(outFile ,allegato.getNome(), allegato.getMimeType());
		return mfOut;
	}
	
	
	private String getIdAlfrescoRicevutaMail(TipoAllegato tipoAllegato, Long idRicevuta) throws Exception {
		String idAlfresco = null;
		if (idRicevuta!=null) {
			RicevutaSearch searchR = new RicevutaSearch();
			searchR.setId(idRicevuta);
			RicevutaDTO ricevuta = ricevutaService.find(idRicevuta);

			if (tipoAllegato==TipoAllegato.RICEVUTA_EML) 
				idAlfresco = ricevuta.getIdCmsEml();
			else if (tipoAllegato==TipoAllegato.RICEVUTA_DATI_CERT)
				idAlfresco = ricevuta.getIdCmsDatiCert();
			else if (tipoAllegato==TipoAllegato.RICEVUTA_SMIME)
				idAlfresco = ricevuta.getIdCmsSmime();
			else {
				log.error("Errore. " + tipoAllegato.name() + " non è previsto per le ricevute");
				throw new Exception("Errore. " + tipoAllegato.name() + " non è previsto per le ricevute");
			}
		}
		return idAlfresco;

	} 
	
	private String downloadAllegato(TipoAllegato tipoAllegato, Long idAllegato, Long idFascicolo, Long idCorrispondenza) throws Exception {
		String idAlfresco = null;

		if (idAllegato!=null) {
			idAlfresco = repository.findIdAlfrescoById(idAllegato);
		}else if (idFascicolo!=null) {
			AllegatoFascicoloSearch searchAF = new AllegatoFascicoloSearch();
			searchAF.setIdFascicolo(idFascicolo);
			searchAF.setType(tipoAllegato);
			List<AllegatoFascicoloDTO> listaAF = allegatoFascicoloService.search(searchAF).getList();
			if (listaAF==null || listaAF.isEmpty()) {
				log.error("Nessun allegato trovato per i parametri in ingresso");
				throw new Exception("Nessun allegato trovato per i parametri in ingresso");
			}
			else if (listaAF.size()>1) {
				log.error("Errore. Trovati " + listaAF.size() + " allegati per i parametri in ingresso");
				throw new Exception("Errore. Trovati " + listaAF.size() + " allegati per i parametri in ingresso");
			}
			else {
				idAlfresco = repository.findIdAlfrescoById(listaAF.get(0).getIdAllegato());
			}
		}else if (idCorrispondenza!=null) {
			if (tipoAllegato==TipoAllegato.CORRISPONDENZA_EML) {
				idAlfresco = corrispondenzaService.find(idCorrispondenza).getIdEmlCmis();
			}
			else {
				RicevutaSearch searchR = new RicevutaSearch();
				searchR.setIdCorrispondenza(idCorrispondenza);
				List<RicevutaDTO> listaR = ricevutaService.getRicevuteCorrispondenza(searchR).getList();
				if (listaR==null || listaR.isEmpty()) {
					log.error("Nessuna ricevuta trovata per i parametri in ingresso");
					throw new Exception("Nessuna ricevuta trovata per i parametri in ingresso");
				}
				else if (listaR.size()>1) {
					log.error("Errore. Trovate " + listaR.size() + " ricevute per i parametri in ingresso");
					throw new Exception("Errore. Trovate " + listaR.size() + " ricevute per i parametri in ingresso");
				}
				else {
					if (tipoAllegato==TipoAllegato.RICEVUTA_EML) 
						idAlfresco = listaR.get(0).getIdCmsEml();
					else if (tipoAllegato==TipoAllegato.RICEVUTA_DATI_CERT)
						idAlfresco = listaR.get(0).getIdCmsDatiCert();
					else if (tipoAllegato==TipoAllegato.RICEVUTA_SMIME)
						idAlfresco = listaR.get(0).getIdCmsSmime();
					else {
						log.error("Errore. " + tipoAllegato.name() + " non è previsto per le ricevute");
						throw new Exception("Errore. " + tipoAllegato.name() + " non è previsto per le ricevute");
					}
				}
			}
		}
//		else if (idRicevuta!=null) {
//			RicevutaSearch searchR = new RicevutaSearch();
//			searchR.setId(idRicevuta);
//			RicevutaDTO ricevuta = ricevutaService.find(idRicevuta);
//
//			if (tipoAllegato==TipoAllegato.RICEVUTA_EML) 
//				idAlfresco = ricevuta.getIdCmsEml();
//			else if (tipoAllegato==TipoAllegato.RICEVUTA_DATI_CERT)
//				idAlfresco = ricevuta.getIdCmsDatiCert();
//			else if (tipoAllegato==TipoAllegato.RICEVUTA_SMIME)
//				idAlfresco = ricevuta.getIdCmsSmime();
//			else {
//				log.error("Errore. " + tipoAllegato.name() + " non è previsto per le ricevute");
//				throw new Exception("Errore. " + tipoAllegato.name() + " non è previsto per le ricevute");
//			}
//		}
		else if (tipoAllegato.equals(TipoAllegato.IMAGE_TEMPLATE_DOC)) {
			log.info("Download image logo for template");
		}
		else {
			log.error("Parametri in ingresso non corretti!");
			throw new Exception("Parametri in ingresso non corretti!");
		}

		if (idAlfresco==null || idAlfresco.isEmpty()) {
			log.error("Nessun file trovato per i parametri in ingresso. [idAlfresco="+idAlfresco+"]");
			throw new Exception("Nessun file trovato per i parametri in ingresso. [idAlfresco="+idAlfresco+"]");
		}
		return idAlfresco;
	}
	
	
	@SuppressWarnings("unused")
	private void controlloVincoliAllegato(MultipartFile file, TipoAllegato tipoAllegato) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		// TODO: implementarlo se necessario, altrimenti cancellare il metodo
		if (tipoAllegato.equals(TipoAllegato.LOCALIZZAZIONE)) {
			// Eccezione in caso di non congruità
		}
	}
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public void alfrescoDelete(long idAllegato) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		AllegatoDTO allegatoDTO = repository.find(idAllegato);
		if(allegatoDTO.getDeletable()!=null && allegatoDTO.getDeletable()==false) {
			log.info("Delete alfresco not permitted for file "+allegatoDTO.getNome()+" !!!");
		}else {
			this.deleteAlfresco(allegatoDTO.getContenuto());	
		}
	}
		
	private String doUploadWithCommonStarter(MultipartFile file, String pathCms) throws CustomCmisException {
		String ret=null;
		try {
			ret = cmsService.uploadCms(file, uploadUrl, pathCms, nomeApplicazione.toLowerCase(), StringUtil.isEmpty(LogUtil.getAuthorization()) || SecurityUtil.isApiUser());
		} catch (CustomCmisException e) {
			log.error("Errore durante il salvatggio del file {}. {}", file.getOriginalFilename(), e.getMessage(), e);
			throw e;
		}
		if (ret==null)	{
			log.error("Errore durante il salvatggio del file {}. IdFile di Alfresco nullo!", file.getOriginalFilename());
			throw new CustomCmisException("Errore durante il salvatggio del file " + file.getOriginalFilename() + ". IdFile di Alfresco nullo!");
		}
		return ret;
	}
	
	/**
	 * 
	 * @param multipartFile
	 * @param codice
	 * @param tipoAllegato
	 * @return
	 * @throws IOException
	 */
//	@SuppressWarnings("unused")
//	@Deprecated
//	private String doUploadWithCmsFeign(MultipartFile multipartFile, String codice, TipoAllegato tipoAllegato) throws IOException {
//		String idFileAlfresco=null;
//		log.info("scelgo un nome casuale per la copia temporanea del file");
//		String tmpFilename = UUID.randomUUID().toString();
//		String tmpName = tempPath + tmpFilename;
//		
//		log.info("creo un nuovo file sull'hard disk: " + tmpName);
//		File f = new File(tmpName);
//
//		log.info("popolo il DTO con i dati presenti nel multipartFile");
//		AlfrescoUploadInputDTO fileDTO = new AlfrescoUploadInputDTO();
//
//		fileDTO.setFileName      (multipartFile.getOriginalFilename());
//		fileDTO.setFileNameOnDisk(tmpFilename);
//		fileDTO.setPathCms		 (cmsBasePath+"/TEST/Fascicolo_Codice_"+codice+"/"+tipoAllegato.name()+"/");
//		fileDTO.setSize		  	 (multipartFile.getSize());
//		fileDTO.setContentType	 (multipartFile.getContentType());
//
//		log.info("riverso il contenuto del multipartFile nel nuovo file creato prima");
//		multipartFile.transferTo(f);
//
//		log.info("trasferisco i dati del file sul server di Alfresco richiamando l'apposito servizio Feign");
//		idFileAlfresco = alfrescoService.uploadAlfresco(fileDTO).getId();
//
//		if(f.exists()) {
//			log.info("Rimuovo il file creato in precedenza sull'hard disk");
//			f.delete();
//		}
//		return idFileAlfresco;
//	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void deleteAlfresco(String idAlfresco) throws CustomCmisException {
		cmsService.deleteCmisIdFromCms(deleteUrl, idAlfresco, nomeApplicazione.toLowerCase(),
				StringUtil.isEmpty(LogUtil.getAuthorization()) || SecurityUtil.isApiUser());
	}
	
	/**
	 * Cancellazione fisica del file dal server di Alfresco
	 *
	 * @param IdAlfresco {@link String} del file da cancellare
	 * @throws Exception in caso di errore durante l'eliminazione del file
	 */
//	@SuppressWarnings("unused")
//	@Deprecated
//	private void deleteAlfrescoOld(String idAlfresco) throws Exception {
//		String response = null;
//		try
//		{
//			log.info("cancello dal server il file con idAlfresco: {}", idAlfresco);
//			response = alfrescoService.deleteAlfresco(URLEncoder.encode(idAlfresco, "UTF-8"));
//			
//		} catch (Exception e)	{
//			log.error("Errore durante la cancellazione fisica del file con idAlfresco: {}. {}", idAlfresco, e.getMessage(), e);
//			throw new Exception("Errore durante la cancellazione fisica del file con idAlfresco: "+idAlfresco+". " + e.getMessage(), e);
//		}
//
//		if (response==null || !response.toUpperCase().contains("OK"))	{
//			log.error("Errore durante la cancellazione fisica del file con idAlfresco: {}. Response ERRATA!", idAlfresco);
//			throw new Exception("Errore durante la cancellazione fisica del file con idAlfresco: "+idAlfresco+". Response ERRATA!");
//		}
//	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	@Transactional(propagation=Propagation.SUPPORTS, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public File downloadAlfresco(String idAlfresco) throws Exception {
		File fileOut=null;
		try {
			Path pathTemp=Files.createTempFile(idAlfresco.replaceAll("[^a-zA-Z0-9\\.\\-]", "_").toString(), "");
			String pathFileLocale=pathTemp.toString();
			//String pathFileLocale = Path.of(pathTransito, idAlfresco.replaceAll("[^a-zA-Z0-9\\.\\-]", "_")).toString();
			cmsService.downloadFromCmsStream(idAlfresco, downloadUrl, nomeApplicazione.toLowerCase(), pathFileLocale,
					StringUtil.isEmpty(LogUtil.getAuthorization()) || SecurityUtil.isApiUser());
			if (Files.exists(Path.of(pathFileLocale))) {
				fileOut=new File(pathFileLocale);
			}
		} catch (Exception e) {
			log.error("errore durante il downloaad del file ",e);
			throw new CustomOperationException("Copy of downloaded file failed.");
		}
		return fileOut;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public AllegatoDTO inserisciAllegatoDaMigrazione(FascicoloDTO fascicolo, List<TipoAllegato> tipiAllegato, Blob file,
			String nomeFile,String nomeDocumento,String contentType,String migrazioneSubPath,String localBasePath,
			String protocolloOut,Date dataProtocolloOut,Date dataArrivo,String subPathCategoriaAllegato,
			boolean noAllegati) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		if(file==null && !noAllegati) {
			throw new Exception("Blob dell'allegato nullo codice pratica:" + fascicolo.getCodice() + " codiceAppptr: "+fascicolo.getCodicePraticaAppptr() +" nomeFile:"+nomeFile + "tipiAllegato:"+tipiAllegato);
		}
		
		String fileName = nomeFile;
		String mimeType = contentType;		
		// creo l'AllegatoDTO (senza l'ID Alfresco) // (devo prendere i dati dal MultipartFile prima dell'upload su Alfresco perchè cancella il file)
		AllegatoDTO allegatoDTO = new AllegatoDTO();
		String subPathCmsBuilded=this.buildPathCms(Collections.singletonList(fascicolo.getId()),tipiAllegato);
		//allegatoDTO.setContenuto(this.alfrescoUpload(file, idFascicoli.size()==1 ? String.valueOf(idFascicoli.get(0)) : "CONDIVISO_"+DateUtil.dataParse(new Date())));
		StringBuilder pathCms=new StringBuilder(cmsBasePath+"/"+migrazioneSubPath+subPathCmsBuilded);
		if(StringUtil.isNotEmpty(subPathCategoriaAllegato)) {
			pathCms.append("/").append(subPathCategoriaAllegato);
		}
		allegatoDTO.setPathCms(pathCms.toString());
		Path localPath=Paths.get(localBasePath,allegatoDTO.getPathCms(),nomeFile);
		if(!localPath.getParent().toFile().exists() && !noAllegati) {
			Files.createDirectories(localPath.getParent());
		}
		File outFile=localPath.toFile();
		if(!noAllegati) {
			if(outFile.exists() && outFile.length()==file.length()) {
				//già scritto.. evito di riscriverlo
			}else {
				if(outFile.exists()) {
					outFile.delete();
				}
				try(BufferedOutputStream os=new BufferedOutputStream(new FileOutputStream(outFile))){
					IOUtils.copy(file.getBinaryStream(), os);	
				}catch(Exception e) {
					log.error("Error writing file locally: " +outFile.getName(),e);
				}	
			}	
		}
		if(file instanceof MockBlob) {
			file.free();//libero il file temporaneo
		}
		allegatoDTO.setNome(fileName);
		allegatoDTO.setDescrizione(tipiAllegato.size()==1 && tipiAllegato.get(0)!=null? tipiAllegato.get(0).getTextValue() : "");
		allegatoDTO.setMimeType(mimeType);
		if(!noAllegati) {
			log.info("calculate checksum  {} ",outFile.getName());
			if(outFile.length()<200*1024*1024) {
				//con file >200MB si pianta
				allegatoDTO.setChecksum(CheckSumUtil.getCheckSum(outFile));	
			} else {
				allegatoDTO.setChecksum("");	
				log.info("skipping calculate checksum, file too large {} {}" ,outFile.getName(), outFile.length());
			}
			allegatoDTO.setDimensione(Math.toIntExact(outFile.length()));	
		}else {
			allegatoDTO.setChecksum("");
			allegatoDTO.setDimensione(0);
		}
		allegatoDTO.setDeleted(false);
		allegatoDTO.setTitolo(nomeDocumento);
		allegatoDTO.setDataProtocolloOut(dataProtocolloOut);
		allegatoDTO.setNumeroProtocolloOut(protocolloOut);
		allegatoDTO.setDataCaricamento(dataArrivo);
		allegatoDTO.setContenuto(AllegatoService.PLACEHOLDERTEMPIDCMS);
		// inserisco l'AllegatoDTO nel DB ----> recupero l'ID Allegato
		Long idAllegato = repository.insert(allegatoDTO);
		allegatoDTO.setId(idAllegato);
		for (TipoAllegato tipoAllegato : tipiAllegato) {
					// creo l'AllegatoFascicoloDTO con i dati in ingresso al metodo
					AllegatoFascicoloDTO allegatoFascicolo = new AllegatoFascicoloDTO();
					allegatoFascicolo.setIdAllegato(idAllegato);
					allegatoFascicolo.setIdFascicolo(fascicolo.getId());
					allegatoFascicolo.setType(tipoAllegato);
					// inserisco il nuovo record nella tabella Allegato_Fascicolo
					try {
						allegatoFascicoloService.insert(allegatoFascicolo);
					} catch (Exception e) {
						log.error("Errore nell'inserimento del record nella tabella Allegato_Fascicolo",e);
					}
				}
		return allegatoDTO;
	}
	
	@Override
	public void downloadFile(String idCmis, HttpServletResponse response) throws Exception {
		cmsService.downloadFromCmsStream(idCmis, downloadUrl, nomeApplicazione.toLowerCase(), response,
				StringUtil.isEmpty(LogUtil.getAuthorization()) || SecurityUtil.isApiUser());
	}
	
}