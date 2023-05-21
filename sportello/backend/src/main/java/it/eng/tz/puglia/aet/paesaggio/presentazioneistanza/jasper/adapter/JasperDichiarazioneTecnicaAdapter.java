package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.FascicoloDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.SelectOptionDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.allegati.AllegatiDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.allegati.DocumentazioneAmministrativaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.allegati.DocumentazioneTecnicaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istanza.UlterioriInformazioniDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.CaratterizzazioneInterventoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.ConfigOptionValue;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.DestinazioneUrbanisticaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.EffettiMitigazioneDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.InquadramentoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.LegittimitaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.PareriAttiDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.PptrDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.ProcedimentoContenziosoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.ProcedureEdilizieDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.SchedaTecnicaDescrizioneDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.TipoInterventoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.TipologicaFE;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.VincolisticaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperAllegatoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperCaratterizzazioneInterventoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperDescrizioneDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperDestinazioneUrbanisticaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperDomandaDichiarazioneTecnicaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperEffettiMitigazioneDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperEventualiProcedimentiDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperInquadramentoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperLegittimitaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperPareriAttiDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperPptrContenutoTabellaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperPptrDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperPptrTabellaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperProcedureEdilizieDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperQualificazioneInterventoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperTipoProcedimentoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperTipologiaInterventoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperVincolisticaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.StringWrapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.enums.CaratterizzazioneIntervento;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.enums.QualificazioneIntervento;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.enums.TipologiaIntervento;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.LocalizzazioneInterventoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PptrSelezioniDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipoContenutoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipoProcedimentoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.UlterioriContestiPaesaggisticiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.TipoContenutoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.TipoProcedimentoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.UlterioriContestiPaesaggisticiRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.jasper.CreaReportService;
import it.eng.tz.puglia.util.string.StringUtil;

@Component
public class JasperDichiarazioneTecnicaAdapter extends AbstractJasperDichiarazioneAdapter<JasperDomandaDichiarazioneTecnicaDto> {

	@Autowired
	private UlterioriContestiPaesaggisticiRepository ucpRepository;
	
	@Autowired
	private TipoContenutoRepository tipoContDao;
	
	@Autowired
	TipoProcedimentoRepository tipoProcedimentoDao;

	@Override
	public JasperDomandaDichiarazioneTecnicaDto adapt(FascicoloDto fascicolo) {
		JasperDomandaDichiarazioneTecnicaDto jasperDto = new JasperDomandaDichiarazioneTecnicaDto();
		super.adaptBaseData(jasperDto, fascicolo);
		TipoProcedimentoDTO tipoProcDto = tipoProcedimentoDao.findById(fascicolo.getTipoProcedimento());
		JasperTipoProcedimentoDto jasperTipoProcedimento = new JasperTipoProcedimentoDto(tipoProcDto);
		jasperDto.setDescrstampasottotitolo(jasperTipoProcedimento.getDescrstampasottotitolo());
		jasperDto.setListTipoProcedimento(List.of(jasperTipoProcedimento));
		if(tipoProcDto.getId()==2) {
			jasperDto.setInquadramento(
					Collections.singletonList(adaptInquadramento(fascicolo.getSchedaTecnica().getInquadramento())));	
		}
		jasperDto.setDescrizione(Collections.singletonList(
				adaptDescrizione(fascicolo.getSchedaTecnica().getDescrizione(), fascicolo.getTipoProcedimento())));
		jasperDto.setEventualiProcedimenti(Collections
				.singletonList(adaptEventualiProcedimenti(fascicolo.getSchedaTecnica().getEventualiProcedimenti())));
		jasperDto
				.setDestinazioneUrbanistica(adaptDestinazioneUrbanistica(fascicolo.getSchedaTecnica().getDestinazione(),
						fascicolo.getIstanza().getLocalizzazione().getLocalizzazioneComuni()));
		jasperDto.setProcedureEdilizie(
				Collections.singletonList(adaptProcedureEdilizie(fascicolo.getSchedaTecnica().getProcedureEdilizie())));
		jasperDto.setLegittimita(
				Collections.singletonList(adaptLegittimita(fascicolo.getSchedaTecnica().getLegittimita())));
		jasperDto.setPareriAtti(
				Collections.singletonList(adaptPareriAtti(fascicolo.getSchedaTecnica().getParreriEAtti())));
		jasperDto.setPptr(Collections.singletonList(adaptPptr(fascicolo.getSchedaTecnica().getPptr(),fascicolo)));
		jasperDto.setVincolistica(
				Collections.singletonList(adaptVincolistica(fascicolo.getSchedaTecnica().getVincolistica())));
		if(tipoProcDto.getId()==2) {
			jasperDto.setEffettiMitigazione(Collections
				.singletonList(adaptEffettiMitigazione(fascicolo.getSchedaTecnica().getEffetiMitigazione())));
		}
		jasperDto.setElencoAllegati(JasperIntegrazioneDocumentaleAdapter.adaptElencoAllegati(fascicolo.getAllegati(),false));
		List<TipoContenutoDTO> tipiContenuto = tipoContDao.select();
		if(fascicolo.getAllegati().getDocumentazioneAmministrativa() != null)
		{
			jasperDto.setDocumentazioneAmministrativa(JasperIntegrazioneDocumentaleAdapter.adaptAmministrativa(fascicolo.getAllegati().getDocumentazioneAmministrativa().getGrigliaAllegatiCaricati()));
			//i pagamenti non vanno qui.... ma in istanza
		}
		if(fascicolo.getAllegati().getDocumentazioneTecnica() != null)
			jasperDto.setDocumentazioneTecnica(JasperIntegrazioneDocumentaleAdapter.adaptTecnica(fascicolo.getAllegati().getDocumentazioneTecnica().getGrigliaAllegatiCaricati(),tipiContenuto));
		jasperDto.setPptrTabella(adaptPptrTabella(fascicolo));
		jasperDto.setIdFascicolo(fascicolo.getId().toString());
		return jasperDto;
	}
	
	private void addFromUlterioriInformazioni(List<String> selezioni,List<SelectOptionDto> options,List<PptrSelezioniDTO> selezioniFascicolo,String codiceUcpBp) {
		if(options!=null && selezioni!=null) {
			//per tutte le opzioni
			options.forEach(opzioneSelezionabile->{
				//cerco l'eventuale selezione
				selezioni
				.stream()
				.filter(codice->codice.equals(opzioneSelezionabile.getValue()))
				.findAny()
				.ifPresent(code->{
					//se la becco cerco nelle selezioni della taeblla pptr (se magari esiste già)
					selezioniFascicolo.stream()
					.filter(el->el.getCodice().equals(codiceUcpBp)).findAny().ifPresentOrElse(
							(itemInFascicolo)->{
								//appendo la selezione i-esima nel codice già presente
								itemInFascicolo.setSpecifica(
										(itemInFascicolo.getSpecifica()==null?"":itemInFascicolo.getSpecifica())+
										" - (" +opzioneSelezionabile.getValue()+") " +opzioneSelezionabile.getDescription()+"\n"
										);
							}
							,
							()->{
								//non esiste, aggiungo item con codice e specifica
								PptrSelezioniDTO toAdd=new PptrSelezioniDTO();
								toAdd.setCodice(codiceUcpBp);
								toAdd.setSpecifica(" - (" +opzioneSelezionabile.getValue()+") " +opzioneSelezionabile.getDescription()+"\n");
								selezioniFascicolo.add(toAdd);				
							});
				});
			});
		}
	}

	private List<JasperPptrTabellaDto> adaptPptrTabella(FascicoloDto fascicolo) {
		//la clono perchè aggiungero' altro....
		List<PptrSelezioniDTO> listSelezioni=new ArrayList<>(fascicolo.getSchedaTecnica().getPptr().getUlteririContestiPaesaggistici());
		if(listSelezioni==null || listSelezioni.size()==0) return new ArrayList<>();
		//è una lista a 4 livelli
		// 6.1 STRUTTURA IDRO.....     				1
		// 6.1.1 - Componenti geomorfologiche		2
		//  	   UCP - Versanti 					3
		// .........
		// 6.3 - STRUTTURA ANTROPICA  												1
		// 6.3.1  - Componenti culturali e insediative 								2
		//        UCP - Testimonianze della Stratificazione Insediativa 			3
		//			  - segnalazioni architettoniche e segnalazioni archeologiche	4 
		// nel caso di 	
		//qui vanno aggiunte le selezioni delle tre item gestite in localizzazione
		//PARCHI_E_RISERVE  PAES_RUR AREE_NOTEVOLE_INSTERESSE_PUBB
		if(fascicolo.getIstanza().getLocalizzazione().getLocalizzazioneComuni()!=null) {
			fascicolo.getIstanza().getLocalizzazione().getLocalizzazioneComuni().forEach((comune)->{
				UlterioriInformazioniDto uif = comune.getUlterioriInformazioni();
				if(uif!=null) {
					if(uif.getBpParchiEReserve()!=null) {
						this.addFromUlterioriInformazioni(uif.getBpParchiEReserve(), uif.getBpParchiEReserveOptions(), listSelezioni,"PARCHI_E_RISERVE");
					}
					if(uif.getBpImmobileAreePubblico()!=null) {
						this.addFromUlterioriInformazioni(uif.getBpImmobileAreePubblico(), uif.getBpImmobileAreePubblicoOptions(), listSelezioni,"AREE_NOTEVOLE_INSTERESSE_PUBB");
					}
					if(uif.getUcpPaesaggioRurale()!=null) {
						this.addFromUlterioriInformazioni(uif.getUcpPaesaggioRurale(), uif.getUcpPaesaggioRuraleOptions(), listSelezioni,"PAES_RUR");
					}
				}
			});
		}
		// data una lista di codici prendi tutte le info di quegli elementi -->
		List<String> listaCodiciOpzioni = listSelezioni.stream().map(el->el.getCodice()).collect(Collectors.toList());
		List<UlterioriContestiPaesaggisticiDTO> listOpzioni = ucpRepository.selectAllWhereCodiceIn(listaCodiciOpzioni);
		List<JasperPptrContenutoTabellaDto> listaOpzioni = mapOpzioni(listOpzioni, listSelezioni);
		// a questo punto ho l'ultimo livello 4 
		//cerco i padri di livello 3
		List<String> listaPadri = listaOpzioni.stream().map(el->el.getGruppo()).collect(Collectors.toList());
		List<UlterioriContestiPaesaggisticiDTO> listaPadriEntity = ucpRepository.selectAllWhereCodiceIn(listaPadri);
		List<JasperPptrContenutoTabellaDto> listaPadriJasp = mapTipi(listaPadriEntity);
		//metto i corrispondenti figli nei padri
		for(JasperPptrContenutoTabellaDto padre:listaPadriJasp) {
			for(JasperPptrContenutoTabellaDto figlio:listaOpzioni) {
				if(figlio.getGruppo().equals(padre.getCodice())) {
					if(padre.getFigli()==null) {padre.setFigli(new ArrayList<>());}
					padre.getFigli().add(figlio);
				}
			}
		}
		// a questo punto ho tutti i flgli nei rispettivi padri
		//in questo livello se tra i membri c'è uno che ha come padre un elemento nella stessa lista,
		List<JasperPptrContenutoTabellaDto> figliIlleggittimi=new ArrayList<>();
		for(JasperPptrContenutoTabellaDto padre:listaPadriJasp) {
			listaPadriJasp
			.stream()
			.filter(pp->pp.getGruppo()!=null && pp.getGruppo().equals(padre.getCodice()))
			//.peek(el->System.out.println(el.getCodice()))
			.findAny().ifPresent((el)->
			{
				padre.getFigli().add(el);
				figliIlleggittimi.add(el);
				}
			);
			
		} 
		figliIlleggittimi.forEach(listaPadriJasp::remove);
		//cerco i padri di livello 2
		List<String> listaPadri2 = listaPadriJasp.stream().map(el->el.getGruppo()).collect(Collectors.toList());
		List<UlterioriContestiPaesaggisticiDTO> listaPadri2Entity = ucpRepository.selectAllWhereCodiceIn(listaPadri2);
		List<JasperPptrContenutoTabellaDto> listaPadri2Jasp = mapTipi(listaPadri2Entity);
		//metto i corrispondenti figli nei padri
		for(JasperPptrContenutoTabellaDto padre:listaPadri2Jasp) {
			for(JasperPptrContenutoTabellaDto figlio:listaPadriJasp) {
				if(figlio.getGruppo().equals(padre.getCodice())) {
					if(padre.getFigli()==null) {padre.setFigli(new ArrayList<>());}
					padre.getFigli().add(figlio);
				}
			}
		}
		//converto i padri all'oggetto corretto
		List<JasperPptrTabellaDto> retList = listaPadri2Jasp.stream().map(jaspPadre->{
			JasperPptrTabellaDto retObj = new JasperPptrTabellaDto();	
			retObj.setContenuto(jaspPadre.getFigli());
			retObj.setTitolo(jaspPadre.getNome());
			retObj.setCodice(jaspPadre.getCodice());
			return retObj;
		}).collect(Collectors.toList());
		//imposto il livello 2 per stampare in bold
		retList.forEach(gruppo->{
			gruppo.getContenuto().stream().forEach(cont->cont.setLivello(2));
		});
		//forse conviene da albero portarlo a lista di lista (ordinata)
		retList.stream().forEach(el->{
			//prendo il contenuto lo navigo e lo rimappo in una lista...
			List<JasperPptrContenutoTabellaDto> outList=new ArrayList<>();
			this.convertiAlista(el.getContenuto(),outList);
			outList.sort((el1,el2)->el1.getOrdine()-el2.getOrdine());
			el.setContenuto(outList);
		});
		
//		List<String> listaCodiciOpzioni = generaListaCodiciOpzioni(listSelezioni);
//		List<UlterioriContestiPaesaggisticiDTO> listOpzioni = ucpRepository.selectAllWhereCodiceIn(listaCodiciOpzioni);
//
//		List<String> listaCodiciTipi = generaListaCodiciTipi(listOpzioni);
//		List<UlterioriContestiPaesaggisticiDTO> listTipi = ucpRepository.selectAllWhereCodiceIn(listaCodiciTipi);
//
//		List<String> listaCodiciGruppi = generaListaCodiciTipi(listTipi); 
//		List<UlterioriContestiPaesaggisticiDTO> listGruppi = ucpRepository.selectAllWhereCodiceIn(listaCodiciGruppi);
//		
//		// <--
//		// 1) trasformo le tre liste in liste di oggetti che mi servono
//		// 2) mettere "specificare" nella lista delle opzioni
//		List<JasperPptrTabellaDto> listaGruppi = mapGruppi(listGruppi);
//		List<JasperPptrContenutoTabellaDto> listaTipi = mapTipi(listTipi);
//		List<JasperPptrContenutoTabellaDto> listaOpzioni = mapOpzioni(listOpzioni, listSelezioni);
//
//		// 3) smisto le opzioni nei tipi giusti
//		listaTipi = smistaOpzioniInTipi(listaOpzioni, listaTipi);
//
//		// 4) smisto i tipi nei gruppi giusti
//		listaGruppi = smistaTipiInGruppi(listaTipi, listaGruppi);
//		//se nei gruppi mi finisce COMP_CULT_INS allora devo sganciarlo dai gruppi ed inserirlo nei tipi in corrispondenza del contenuto con codice COMP_CULT_INS
//		if(listaGruppi!=null && listaGruppi.size()>0){
//			 Optional<JasperPptrTabellaDto> cci = listaGruppi.stream()
//			.filter(el-> el.getCodice().equals("COMP_CULT_INS"))
//			.findAny();
//			if(cci.isPresent()) {
//				//lo sposto nella STRUT_ANTR_STOR_CULT -> COMP_CULT_INS
//				listaGruppi.remove(cci.get());
//				Optional<JasperPptrTabellaDto> sac = listaGruppi.stream().filter(el->el.getCodice().equals("STRUT_ANTR_STOR_CULT")).findAny();
//				if(sac.isPresent()) {
//					//lo inserisco nel child con 
//					if(sac.get().getContenuto().
//				}
//			}
//			
//		}
//		return listaGruppi;
		return retList;
	}

	private void convertiAlista(List<JasperPptrContenutoTabellaDto> contenuto,
			List<JasperPptrContenutoTabellaDto> outList) {
		if(contenuto!=null) {
			contenuto.forEach(el->{
				outList.add(el);
				if(el.getFigli()!=null) {
					convertiAlista(el.getFigli(),outList);
				}
				el.setFigli(null);
			});
		}
		
	}

	private List<String> generaListaCodiciTipi(List<UlterioriContestiPaesaggisticiDTO> listOpzioni) {
		Set<String> setCodiciTipi = new HashSet<String>();
		for (UlterioriContestiPaesaggisticiDTO opzione : listOpzioni) {
			setCodiciTipi.add(opzione.getGruppo());
		}
		return new ArrayList<String>(setCodiciTipi);
	}

	private List<String> generaListaCodiciOpzioni(List<PptrSelezioniDTO> listSelezioni) {
		List<String> listaCodici = new ArrayList<String>();
		for (PptrSelezioniDTO opzione : listSelezioni) {
			listaCodici.add(opzione.getCodice());
		}
		return listaCodici;
	}

	private List<JasperPptrTabellaDto> smistaTipiInGruppi(List<JasperPptrContenutoTabellaDto> listaTipi,
			List<JasperPptrTabellaDto> listaGruppi) {
		for (JasperPptrContenutoTabellaDto tipo : listaTipi) {
			for (JasperPptrTabellaDto gruppo : listaGruppi) {
				if (gruppo.getCodice().equals(tipo.getGruppo())) {
					List<JasperPptrContenutoTabellaDto> contenuto = gruppo.getContenuto();
					if(contenuto != null) {
						contenuto.add(tipo);
						gruppo.setContenuto(contenuto);
					}
				}
			}
		}
		return listaGruppi;
	}

	private List<JasperPptrContenutoTabellaDto> smistaOpzioniInTipi(List<JasperPptrContenutoTabellaDto> listaOpzioni,
			List<JasperPptrContenutoTabellaDto> listaTipi) {
		for (JasperPptrContenutoTabellaDto opzione : listaOpzioni) {
			for (JasperPptrContenutoTabellaDto tipo : listaTipi) {
				if (tipo.getCodice().equals(opzione.getGruppo())) {
					if(tipo.getFigli()==null) {
						tipo.setFigli(new ArrayList<>());
					}
					tipo.getFigli().add(opzione);
//					List<JasperPptrContenutoTabellaDto> figli = tipo.getFigli();
//					if(figli!=null) {
//						figli.add(opzione);
//						tipo.setFigli(figli);	
//					}
					
				}
			}
		}
		return listaTipi;
	}

	private List<JasperPptrTabellaDto> mapGruppi(List<UlterioriContestiPaesaggisticiDTO> listGruppi) {
		List<JasperPptrTabellaDto> list = new ArrayList<JasperPptrTabellaDto>();
		for (UlterioriContestiPaesaggisticiDTO gruppo : listGruppi) {
			JasperPptrTabellaDto dto = new JasperPptrTabellaDto();
			dto.setTitolo(gruppo.getLabel());
			dto.setCodice(gruppo.getCodice());
			dto.setContenuto(new ArrayList<JasperPptrContenutoTabellaDto>());//estraiContenuto(gruppo)
			list.add(dto);
		}
		return list;
	}
	
	private List<JasperPptrContenutoTabellaDto> estraiContenuto(UlterioriContestiPaesaggisticiDTO elem)
	{
		JasperPptrContenutoTabellaDto dto = new JasperPptrContenutoTabellaDto();
		dto.setArt1(elem.getArt1());
		dto.setArt2(elem.getArt2());
		dto.setCodice(elem.getCodice());
		dto.setDefinizione(elem.getDefinizione());
		dto.setDisposizioniNormative(elem.getDisposizioni());
		dto.setGruppo(elem.getGruppo());
		dto.setNome(elem.getLabel());
		return Collections.singletonList(dto);
	}

	private List<JasperPptrContenutoTabellaDto> mapTipi(List<UlterioriContestiPaesaggisticiDTO> listTipi) {
		List<JasperPptrContenutoTabellaDto> list = new ArrayList<JasperPptrContenutoTabellaDto>();
		for (UlterioriContestiPaesaggisticiDTO tipo : listTipi) {
			JasperPptrContenutoTabellaDto dto = toJasper(tipo);
//			dto.setNome(tipo.getLabel());
//			dto.setArt1(tipo.getArt1());
//			dto.setDefinizione(tipo.getDefinizione());
//			dto.setDisposizioniNormative(tipo.getDisposizioni());
//			dto.setArt2(tipo.getArt2());
//			dto.setCodice(tipo.getCodice());
//			dto.setGruppo(tipo.getGruppo());
//			dto.setOrdine(tipo.getOrder());
			list.add(dto);
		}
		return list;
	}
	
	private JasperPptrContenutoTabellaDto toJasper(UlterioriContestiPaesaggisticiDTO opzione) {
		JasperPptrContenutoTabellaDto dto = new JasperPptrContenutoTabellaDto();
		dto.setNome(opzione.getLabel());
		dto.setArt1(opzione.getArt1());
		dto.setDefinizione(opzione.getDefinizione());
		dto.setDisposizioniNormative(opzione.getDisposizioni());
		dto.setArt2(opzione.getArt2());
		dto.setCodice(opzione.getCodice());
		dto.setGruppo(opzione.getGruppo());
		dto.setOrdine(opzione.getOrder());
		return dto;
	}

	private List<JasperPptrContenutoTabellaDto> mapOpzioni(List<UlterioriContestiPaesaggisticiDTO> listOpzioni,
			List<PptrSelezioniDTO> listSelezioni) {
		List<JasperPptrContenutoTabellaDto> list = new ArrayList<JasperPptrContenutoTabellaDto>();
		for (UlterioriContestiPaesaggisticiDTO opzione : listOpzioni) {
			JasperPptrContenutoTabellaDto dto =toJasper(opzione);
			dto.setSpecificare(getSpecificareFromCodice(opzione.getCodice(), listSelezioni));
			list.add(dto);
		}
		return list;
	}

	private String getSpecificareFromCodice(String codice, List<PptrSelezioniDTO> listSelezioni) {
		String specificare = null;
		for (PptrSelezioniDTO selezione : listSelezioni) {
			if (selezione.getCodice().equals(codice)) {
				specificare = selezione.getSpecifica();
			}
		}
		return specificare;
	}

	private List<JasperAllegatoDto> adaptDocumentazioneTecnica(DocumentazioneTecnicaDto documentazioneTecnica) {
		List<JasperAllegatoDto> list = new ArrayList<JasperAllegatoDto>();
		List<AllegatiDTO> grigliaAllegatiDocumentazioneTecnica = documentazioneTecnica.getGrigliaAllegatiCaricati();
		for (AllegatiDTO allegato : grigliaAllegatiDocumentazioneTecnica) {
			JasperAllegatoDto dto = new JasperAllegatoDto();
			dto.setNomeAllegato(allegato.getNomeFile());
			dto.setNomeArchivio(allegato.getNomeFile());
			dto.setTipoDocumento(allegato.getDescrizione());
			list.add(dto);
		}
		return list;
	}

	private List<JasperAllegatoDto> adaptDocumentazioneAmministrativa(
			DocumentazioneAmministrativaDto documentazioneAmministrativa) {
		List<JasperAllegatoDto> list = new ArrayList<JasperAllegatoDto>();
		List<AllegatiDTO> grigliaPagamentoAllegati = documentazioneAmministrativa.getGrigliaPagamentoAllegati();
		for (AllegatiDTO allegato : grigliaPagamentoAllegati) {
			JasperAllegatoDto dto = new JasperAllegatoDto();
			dto.setNomeAllegato(allegato.getNomeFile());
			dto.setNomeArchivio(allegato.getNomeFile());
			dto.setTipoDocumento(allegato.getDescrizione());
			list.add(dto);
		}
		List<AllegatiDTO> grigliaAllegatiCaricati = documentazioneAmministrativa.getGrigliaAllegatiCaricati();
		for (AllegatiDTO allegato : grigliaAllegatiCaricati) {
			if (allegato.getId() != null) {
				JasperAllegatoDto dto = new JasperAllegatoDto();
				dto.setNomeAllegato(allegato.getNomeFile());
				dto.setNomeArchivio(allegato.getNomeFile());
				dto.setTipoDocumento(allegato.getDescrizione());
				list.add(dto);
			}
		}
		return list;
	}

	
	private JasperEffettiMitigazioneDto adaptEffettiMitigazione(EffettiMitigazioneDto effettiMitigazione) {
		JasperEffettiMitigazioneDto dto = new JasperEffettiMitigazioneDto();
		dto.setDescrizione(CreaReportService.convertiATagJasper(effettiMitigazione.getDescrizione()));
		dto.setEffeti(CreaReportService.convertiATagJasper(effettiMitigazione.getEffeti()));
		dto.setMisure(CreaReportService.convertiATagJasper(effettiMitigazione.getMisure()));
		dto.setContenutiPercettivi(CreaReportService.convertiATagJasper(effettiMitigazione.getContenutiPercettivi()));
		return dto;
	}

	private JasperVincolisticaDto adaptVincolistica(VincolisticaDto vincolistica) {
		JasperVincolisticaDto dto = new JasperVincolisticaDto();
		dto.setSottopostoATutela(vincolistica.getSottopostoATutela());
		dto.setAltriVincolo(vincolistica.getAltriVincolo());
		List<TipologicaFE> listSpecificaVincolo = vincolistica.getSpecificaVincolo();
		List<StringWrapper> listString = new ArrayList<StringWrapper>();
		for (TipologicaFE specificaVincolo : listSpecificaVincolo) {
			StringWrapper string = new StringWrapper();
			string.setValue(getLabelSpecificaVincoloFromValue(specificaVincolo.getValue()));
			listString.add(string);
		}
		dto.setSpecificaVincolo(listString);
		return dto;
	}

	private String getLabelSpecificaVincoloFromValue(Integer value) {
		String label = "";
		switch (value) {
		case 1:
			label = "vincolo monumentale diretto (art. 10 del D.Lgs. n. 42/2004)";
			break;
		case 2:
			label = "vincolo monumentale indiretto (art. 45 del D.lgs. n. 42/2004)";
			break;
		case 3:
			label = "vincolo archeologico diretto (art. 10 del D.lgs. n. 42/2004)";
			break;
		case 4:
			label = "vincolo archeologico indiretto (art. 45 del D.lgs. n. 42/2004)";
			break;

		default:
			break;
		}
		return label;
	}

	private JasperPptrDto adaptPptr(PptrDto pptr,FascicoloDto fascicolo) {
		JasperPptrDto dto = new JasperPptrDto();
		dto.setAmbitoPaesaggistico(pptr.getAmbitoPaesaggistico());
		dto.setFigura(pptr.getFigura());
		dto.setArt103(pptr.getArt103());
		dto.setArt142(pptr.getArt142());
		dto.setDescrizione(pptr.getDescrizione());
		final StringBuilder vincoloArt38=new StringBuilder("");
		fascicolo.getIstanza().getLocalizzazione().getLocalizzazioneComuni().forEach(comune->{
		//lo cerco in comuni di competenza
		fascicolo.getComuniCompetenza().stream().filter(comComp->comComp.getEnteId()==comune.getComuneId()).findAny()
			.ifPresentOrElse(cc->vincoloArt38.append(" - ").append(cc.getVincoloArt38()).append("<br>"), ()->{});
		});
		dto.setVincoloArt38(vincoloArt38.toString());
		return dto;
	}

	private JasperPareriAttiDto adaptPareriAtti(PareriAttiDto pareriAtti) {
		JasperPareriAttiDto dto = new JasperPareriAttiDto();
		dto.setParreriInfo(pareriAtti.getParreriInfo());
		dto.setAttiInfo(pareriAtti.getAttiInfo());
		return dto;
	}

	private JasperLegittimitaDto adaptLegittimita(LegittimitaDto legittimita) {
		JasperLegittimitaDto dto = new JasperLegittimitaDto();
		dto.setTipoLegittimitaUrbanistica(legittimita.getTipoLegittimitaUrbanistica());
		dto.setLegittimitaUrbanstica(legittimita.getLegittimitaUrbanstica());
		dto.setLegittimitaInfo(legittimita.getLegittimitaInfo());
		dto.setTipoLegittimitaPaesaggistica(legittimita.getTipoLegittimitaPaesaggistica());
		dto.setTipologiaVincolo(legittimita.getDettaglioLegittimitaPaesaggistica().getTipologiaVincolo());
		dto.setDataIntervento(legittimita.getDettaglioLegittimitaPaesaggistica().getDataIntervento());
		dto.setDataImposizioneVincolo(legittimita.getDettaglioLegittimitaPaesaggistica().getDataImposizioneVincolo());
		dto.setAutorizzatoPaesaggisticamenteInfo(legittimita.getAutorizzatoPaesaggisticamenteInfo());
		return dto;
	}

	private JasperProcedureEdilizieDto adaptProcedureEdilizie(ProcedureEdilizieDto procedureEdilizie) {
		JasperProcedureEdilizieDto dto = new JasperProcedureEdilizieDto();
		List<TipologicaFE> listCheckbox = procedureEdilizie.getTipoStato();
		dto.setTipoIntervento(procedureEdilizie.getTipoIntervento());
		dto.setPrimaCheckbox(getCheckboxWithValue(1, listCheckbox));
		dto.setSecondaCheckbox(getCheckboxWithValue(2, listCheckbox));
		dto.setMotivazione(procedureEdilizie.getMotivazione());
		dto.setDetagglio(procedureEdilizie.getDetagglio());
		dto.setPressoData(procedureEdilizie.getPressoData());
		dto.setEspressoData(procedureEdilizie.getEspressoData());
		return dto;
	}

	private Boolean getCheckboxWithValue(Integer value, List<TipologicaFE> listCheckbox) {
		Boolean esito = false;
		for (TipologicaFE checkbox : listCheckbox) {
			if (checkbox.getValue().equals(value)) {
				esito = true;
			}
		}
		return esito;
	}

	private List<JasperDestinazioneUrbanisticaDto> adaptDestinazioneUrbanistica(
			List<DestinazioneUrbanisticaDto> listDestinazioneUrbanistica,
			List<LocalizzazioneInterventoDTO> listComuni) {
		List<JasperDestinazioneUrbanisticaDto> list = new ArrayList<JasperDestinazioneUrbanisticaDto>();
		for (DestinazioneUrbanisticaDto destinazioneUrbanistica : listDestinazioneUrbanistica) {
			JasperDestinazioneUrbanisticaDto dto = new JasperDestinazioneUrbanisticaDto();
			dto.setNomeComune(getNomeComuneFromComuneId(destinazioneUrbanistica.getComuneId(), listComuni));
			dto.setMostraCoerenza(destinazioneUrbanistica.isMostraCoerenza());
			dto.setCoerenzaData(destinazioneUrbanistica.getCoerenzaData());
			dto.setCoerenzaAtto(destinazioneUrbanistica.getCoerenzaAtto());
			dto.setStrumentoUrbanistico(destinazioneUrbanistica.getStrumentoUrbanistico());
			dto.setApprovatoData(destinazioneUrbanistica.getApprovatoData());
			dto.setApprovatoCon(destinazioneUrbanistica.getApprovatoCon());
			dto.setDestinazioneUrbanistica(destinazioneUrbanistica.getDestinazioneUrbanistica());
			dto.setUlterioriTutele(destinazioneUrbanistica.getUlterioriTutele());
			dto.setConfermaCoerenza(destinazioneUrbanistica.getConfermaCoerenza());
			dto.setStrumentoInAdozione(destinazioneUrbanistica.getStrumentoInAdozione());
			dto.setAdottatoData(destinazioneUrbanistica.getAdottatoData());
			dto.setAdottatoCon(destinazioneUrbanistica.getAdottatoCon());
			dto.setDestinazioneUrbanisticaAdottato(destinazioneUrbanistica.getDestinazioneUrbanisticaAdottato());
			dto.setUlterioriTuteleAdottato(destinazioneUrbanistica.getUlterioriTuteleAdottato());
			dto.setConformitaStrumentoUrbanistico(destinazioneUrbanistica.getConformitaStrumentoUrbanistico());
			list.add(dto);
		}
		return list;
	}

	private String getNomeComuneFromComuneId(Long comuneId, List<LocalizzazioneInterventoDTO> listComuni) {
		String nomeComune = "";
		for (LocalizzazioneInterventoDTO comune : listComuni) {
			if (comune.getComuneId() == comuneId) {
				nomeComune = comune.getComune();
			}
		}
		return nomeComune;
	}

	private JasperEventualiProcedimentiDto adaptEventualiProcedimenti(
			ProcedimentoContenziosoDto eventualiProcedimenti) {
		JasperEventualiProcedimentiDto dto = new JasperEventualiProcedimentiDto();
		dto.setPresenza(eventualiProcedimenti.getContenzisoAtto());
		dto.setDescrizione(eventualiProcedimenti.getDescrizione());
		return dto;
	}

	private JasperDescrizioneDto adaptDescrizione(SchedaTecnicaDescrizioneDto descrizione, Integer tipoProcedimento) {
		JasperDescrizioneDto dto = new JasperDescrizioneDto();
		dto.setDescrizione(descrizione.getDescrizione());
		dto.setCaratterizzazioneIntervento(Collections
				.singletonList(adaptCaratterizzazioneIntervento(descrizione.getCaratterizzazioneIntervento())));
		dto.setQualificazioneIntervento(
				adaptQualificazioneIntervento(descrizione.getQualificazioneIntervento(), tipoProcedimento));
		dto.setTipologiaIntervento(
				Collections.singletonList(adaptTipologiaIntervento(descrizione.getTipoIntervento())));
		return dto;
	}

	private JasperCaratterizzazioneInterventoDto adaptCaratterizzazioneIntervento(
			CaratterizzazioneInterventoDto caratterizzazioneIntervento) {
		JasperCaratterizzazioneInterventoDto dto = new JasperCaratterizzazioneInterventoDto();
		dto.setDurata(caratterizzazioneIntervento.getDurata());
		List<ConfigOptionValue> listCaratterizzazione = caratterizzazioneIntervento.getCaratterizzazione();
		List<ConfigOptionValue> listJasperCaratterizzazione = new ArrayList<ConfigOptionValue>();
		for (ConfigOptionValue caratterizzazione : listCaratterizzazione) {
			ConfigOptionValue jasperCaratterizzazione = new ConfigOptionValue();
			jasperCaratterizzazione
					.setValue(CaratterizzazioneIntervento.valueOf(caratterizzazione.getValue()).getLabel());
			jasperCaratterizzazione.setText(caratterizzazione.getText());
			listJasperCaratterizzazione.add(jasperCaratterizzazione);
		}
		dto.setCaratterizzazione(listJasperCaratterizzazione);
		return dto;
	}

	public static List<JasperQualificazioneInterventoDto> adaptQualificazioneIntervento(
			List<ConfigOptionValue> qualificazioneIntervento, Integer tipoProcedimento) {
		List<JasperQualificazioneInterventoDto> list = new ArrayList<JasperQualificazioneInterventoDto>();
		if (tipoProcedimento.equals(1)) {
			// mi aspetto un solo elemento nella lista perchè radiobutton
			String value = qualificazioneIntervento.get(0).getValue();
			JasperQualificazioneInterventoDto dto = new JasperQualificazioneInterventoDto();
			dto.setText(QualificazioneIntervento.valueOf(value).getLabel());
			list.add(dto);
		} else if (tipoProcedimento.equals(2)) {
			List<StringWrapper> listSubtext = new ArrayList<StringWrapper>();
			for (ConfigOptionValue option : qualificazioneIntervento) {
				JasperQualificazioneInterventoDto dto = new JasperQualificazioneInterventoDto();
				// divido le checkbox in due gruppi: le prime due e le figlie della seconda; se
				// le prime due allora le aggiungo subito alla lista...
				if (option.getValue().equals(QualificazioneIntervento.DPR_31_2017_1.name())
						|| option.getValue().equals(QualificazioneIntervento.DPR_31_2017_2.name())) {
					dto.setText(QualificazioneIntervento.valueOf(option.getValue()).getLabel());
					list.add(dto);
				} else {
					// ... altrimenti creo la lista delle figlie della seconda checkbox...
					StringWrapper subText = new StringWrapper();
					subText.setValue(QualificazioneIntervento.valueOf(option.getValue()).getLabel());
					listSubtext.add(subText);
				}
			}
			// ... e poi le riassegno alla seconda chekbox
			for (JasperQualificazioneInterventoDto elem : list) {
				if (elem.getText().equals(QualificazioneIntervento.DPR_31_2017_2.getLabel())) {
					elem.setSubtext(listSubtext);
				}
			}
		} else if (tipoProcedimento.equals(3)) {
			// faccio un semplice travaso
			for (ConfigOptionValue option : qualificazioneIntervento) {
				JasperQualificazioneInterventoDto dto = new JasperQualificazioneInterventoDto();
				dto.setText(QualificazioneIntervento.valueOf(option.getValue()).getLabel());
				list.add(dto);
			}
		} else {
			List<StringWrapper> listSubtext = new ArrayList<StringWrapper>();
			for (ConfigOptionValue option : qualificazioneIntervento) {
				JasperQualificazioneInterventoDto dto = new JasperQualificazioneInterventoDto();
				// divido tutto in due gruppi: i primi due radiobutton e i figli del secondo; se
				// i primi due allora li aggiungo subito alla lista...
				if (option.getValue().equals(QualificazioneIntervento.QUAL_1.name())
						|| option.getValue().equals(QualificazioneIntervento.QUAL_2.name())) {
					dto.setText(QualificazioneIntervento.valueOf(option.getValue()).getLabel());
					list.add(dto);
				} else {
					// ... altrimenti creo la lista dei figli del secondo radiobutton...
					StringWrapper subText = new StringWrapper();
					subText.setValue(QualificazioneIntervento.valueOf(option.getValue()).getLabel());
					listSubtext.add(subText);
				}
			}
			// ... e poi li riassegno al secondo radiobutton
			for (JasperQualificazioneInterventoDto elem : list) {
				if (elem.getText().equals(QualificazioneIntervento.QUAL_2.getLabel())) {
					elem.setSubtext(listSubtext);
				}
			}
		}
		return list;
	}

	private JasperTipologiaInterventoDto adaptTipologiaIntervento(TipoInterventoDto tipologiaIntervento) {
		JasperTipologiaInterventoDto dto = new JasperTipologiaInterventoDto();
		dto.setTipologiaDiIntervento(
				TipologiaIntervento.valueOf(tipologiaIntervento.getTipologiaDiIntervento()).getLabel());
		dto.setInParticolareAgliArtt(tipologiaIntervento.getInParticolareAgliArtt());
		dto.setDataApprovazione(tipologiaIntervento.getDataApprovazione());
		dto.setCon(tipologiaIntervento.getCon());
		return dto;
	}

	private JasperInquadramentoDto adaptInquadramento(InquadramentoDto inquadramento) {
		JasperInquadramentoDto dto = new JasperInquadramentoDto();
		dto.setDestinazioneUso(inquadramento.getDestinazioneUso());
		dto.setDestinazioneUsoSpecifica(inquadramento.getDestinazioneUsoSpecifica());
		dto.setContestoPaesaggInterv(inquadramento.getContestoPaesaggInterv());
		dto.setContestoPaesaggIntervSpecifica(inquadramento.getContestoPaesaggIntervSpecifica());
		dto.setMorfologiaConPaesag(inquadramento.getMorfologiaConPaesag());
		dto.setMorfologiaConPaesagSpecifica(inquadramento.getMorfologiaConPaesagSpecifica());
		return dto;
	}

	@Override
	protected String getDescDittaInQualitaDi(int inQualitaDi, String DescrAltroDitta) {
		// TODO Auto-generated method stub
		return null;
	}

}
