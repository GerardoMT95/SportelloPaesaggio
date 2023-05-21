package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.ApplicationProperties;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.AssegnamentoFascicoloOldDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.repository.AssegnamentoFascicoloFullRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.search.AssegnamentoFascicoloSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.FasiAssegnazione;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ComuniCompetenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ReferentiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipoProcedimentoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.ComuniCompetenzaSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IComuniCompetenzaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IPraticaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IReferentiService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.ITipoProcedimentoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.EnteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.repository.CommonRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.ProvvedimentoFinaleService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.PlaceHolder;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.service.IResolveTemplateService;
import it.eng.tz.puglia.bean.PaginatedList;


@Service
public class ResolveTemplateService implements IResolveTemplateService {
	
	private static final Logger log = LoggerFactory.getLogger(ResolveTemplateService.class);
	
	@Autowired IPraticaService praticaService;
	@Autowired ITipoProcedimentoService tipoService;
	@Autowired IComuniCompetenzaService comuniService;
	@Autowired IReferentiService referenti;
	@Autowired ProvvedimentoFinaleService provvedimentoService;
	
	@Autowired CommonRepository commonDao;
	@Autowired AssegnamentoFascicoloFullRepository assegnamentoDao;
	
	@Autowired private ApplicationProperties props;
	
	@Override
	public String risolviTesto(String placeholder, String testo, UUID idPratica) {
		PraticaDTO pratica= new PraticaDTO();
		pratica.setId(idPratica);
		try {
			pratica=praticaService.find(pratica);
			return this.risolviTesto(placeholder, testo, pratica);
		} catch (Exception e) {
			log.error("Errore in risolviTesto",e);
		}
		return testo;
		
	}
	
	@Override
	public String risolviTesto(String placeholder, String testo, PraticaDTO pratica) {
		return this.risolviTesto(placeholder, testo, pratica,
				(ph)->{
					return ph;//nessuna sostituzione....
					});
	}
	
	@Override
	public String risolviTesto(String placeholder, String testo, PraticaDTO pratica, Function<String,String> customResolving) {
		
		try {
			String[] listPlaceholder = placeholder.split(",");
			TipoProcedimentoDTO tipoProc = new TipoProcedimentoDTO();
			tipoProc.setId(pratica.getTipoProcedimento());
			tipoProc=tipoService.find(tipoProc);
			ComuniCompetenzaSearch searchComuni = new ComuniCompetenzaSearch();
			searchComuni.setPraticaId(pratica.getId());
			searchComuni.setSoloComuniIntervento(true);
			PaginatedList<ComuniCompetenzaDTO> comuniCompetenza = comuniService.search(searchComuni);
			List<String> comuniList=new ArrayList<String>();
			for (ComuniCompetenzaDTO comune: comuniCompetenza.getList()) {
				comuniList.add(comune.getDescrizione());
			}
			String listaComuni=String.join(", ", comuniList);
			ReferentiDTO referento = referenti.selectRichiedente(pratica.getId(),"SD");
			RISOLUZIONE_PLACEHOLDERS:for (String placeholderStr : listPlaceholder) {
				if(!testo.contains("{"+placeholderStr.trim()+"}")) {
					//se non è stato usato il placeholder.. salto...
					continue RISOLUZIONE_PLACEHOLDERS;
				}
				PlaceHolder placehoder=null;
				try {
				   placehoder=PlaceHolder.valueOf(placeholderStr.trim());
				}catch(Exception e) {
					log.error("placeholder non ammesso in enum:"+placeholderStr);
				}
				if(placehoder==null) {
					continue;
				}
				if(customResolving!=null) {
					String ret = customResolving.apply(placehoder.name());
					if(!ret.equals(placehoder.name()) && ret!=null){
						//risolto dall'esterno....
						testo= testo.replace("{"+placeholderStr.trim()+"}", ret);
						continue RISOLUZIONE_PLACEHOLDERS;
					}
				}
				SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
				String traduzione=null;
				switch (placehoder) {
					case CODICE_PRATICA: //alias... CODICE_FASCICOLO
						traduzione = pratica.getCodicePraticaAppptr();
						break;
					case CODICE_TRASMISSIONE: 
						traduzione = pratica.getCodiceTrasmissione();
						break;	
					case CODICE_FASCICOLO:
						traduzione = pratica.getCodicePraticaAppptr();
						break;
					case TIPO_PROCEDIMENTO:
						traduzione = tipoProc.getDescrizione();
						break;
					case OGGETTO:
						traduzione = pratica.getOggetto();
						break;
					case DATA_PRESENTAZIONE:
						if(pratica.getDataPresentazione()!=null) {
							traduzione = sdfDate.format(pratica.getDataPresentazione());
						}
						break;
					case COMUNE:
						traduzione = listaComuni;
						break;
					case ENTE_PROCEDENTE:
						try {
						String ente = pratica.getEnteDelegato();
						Integer idEnte=Integer.parseInt(ente);
						EnteDTO enteDto = commonDao.findEnteByIdOrganizzazione(idEnte);
						traduzione=enteDto.getDescrizione();
						}catch(Exception e){
							log.error("Errore nel retrieval della descrizione dell'ente nella risoluzione del placeholder ENTE_PROCEDENTE enteId="+pratica.getEnteDelegato(),e);
							throw new CustomOperationToAdviceException("Errore nel retrieval della descrizione dell'ente nella risoluzione del placeholder ENTE_PROCEDENTE");
						}
						break;
					case NOME_COGNOME_RICHIEDENTE:
						traduzione = referento.getNome()+" "+referento.getCognome();
						break;
					case RUP:
						traduzione="";
						try {
							AssegnamentoFascicoloSearch filter = new AssegnamentoFascicoloSearch();
							filter.setFase(FasiAssegnazione.ISTRUTTORIA.name());
							filter.setIdFascicolo(pratica.getId());
							filter.setIdOrganizzazione(Integer.parseInt(pratica.getEnteDelegato()));
							filter.setRup(true);
							PaginatedList<AssegnamentoFascicoloOldDTO> list = this.assegnamentoDao.search(filter);
							if (list.getCount() > 0) {
								traduzione = list.getList().get(0).getDenominazioneUtente();
							} else {
							// 	può essere che non sia stato ancora assegnato....
							}
						} catch (Exception e) {
							log.error("Errore nella risoluzione del placeholder RUP ", e);
						}
						break;
					case NUM_PROTOCOLLO_ISTANZA:
						traduzione = pratica.getNumeroProtocollo();
						break;
					case DATA_PROTOCOLLO_ISTANZA:
						if(pratica.getDataProtocollo()!=null) {
							traduzione = sdfDate.format(pratica.getDataProtocollo());
						}
						break;
					case PROTOCOLLO:
						if((pratica.getNumeroProtocollo() != null && !pratica.getNumeroProtocollo().isEmpty()) 
								&& (pratica.getDataProtocollo() != null)) {
							traduzione = "acquisita con protocollo<br>" + pratica.getNumeroProtocollo() + " del " + sdfDate.format(pratica.getDataProtocollo()) +"<br><br>";
						} else {
							traduzione = "<br><br>";
						}
						break;
					case LINK_PROVVEDIMENTO:
						traduzione = "";
						List<AllegatiDTO> allegati = this.provvedimentoService.findAllegatiProvvedimenti(pratica.getId());
						if(allegati != null && allegati.size() > 0) {
							String context = "";
							Optional<AllegatiDTO> allegato = allegati.stream().filter(x -> x.getType().equals("950")).findAny();
							if(allegato.isPresent()) {
								context = this.commonDao.getConfigurationValue(ProvvedimentoFinaleService.KEY_URL_DOWNLOAD_PREFIX, props.getCodiceApplicazione());
								traduzione =  new StringBuilder(context)
										                            .append("/public/" + ProvvedimentoFinaleService.BASE_PUBLIC_DOWNLOAD)
										                            .append("/download-provvedimento-privato")
										                            .append("/").append(String.valueOf(pratica.getId())).toString();
							} else {
								allegato = allegati.stream().filter(x -> x.getType().equals("951")).findAny();
								if(allegato.isPresent()) {
									context = this.commonDao.getConfigurationValue(ProvvedimentoFinaleService.KEY_URL_DOWNLOAD_PREFIX, props.getCodiceApplicazione());
									traduzione =  new StringBuilder(context)
										                            .append("/public/" + ProvvedimentoFinaleService.BASE_PUBLIC_DOWNLOAD)
										                            .append("/download-provvedimento-pubblico")
										                            .append("/").append(String.valueOf(pratica.getId())).toString();
								}
							}
						}	
						break;
					default:
						break;
				}
				if(traduzione!=null) {
					testo= testo.replace("{"+placeholderStr.trim()+"}", traduzione);
				}
			}
			return testo;
		} catch (Exception e) {
			log.error("Errore in risolviTesto",e);
		}
		return testo;
	}

	

}
