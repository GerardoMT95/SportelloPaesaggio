package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.allegati.AllegatiDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperAllegatoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperIntegrazioneDocumentaleDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperListAllegatoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipoContenutoDTO;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.string.StringUtil;


public class JasperIntegrazioneDocumentaleAdapter {
	public static JasperIntegrazioneDocumentaleDto adapt(String codiceFascicolo, AllegatiDto allegati,List<TipoContenutoDTO> allTipi)
			throws Exception {
		JasperIntegrazioneDocumentaleDto jasperDto = new JasperIntegrazioneDocumentaleDto();
		jasperDto.setCodiceFascicolo(codiceFascicolo);
		if (allegati.getDocumentazioneAmministrativa() != null) {
			List<JasperAllegatoDto> listAllAmm = new LinkedList<JasperAllegatoDto>();
			listAllAmm.addAll(JasperIntegrazioneDocumentaleAdapter
					.adaptAmministrativa(allegati.getDocumentazioneAmministrativa().getGrigliaPagamentoAllegati()));
			listAllAmm.addAll(JasperIntegrazioneDocumentaleAdapter
					.adaptAmministrativa(allegati.getDocumentazioneAmministrativa().getGrigliaAllegatiCaricati()));
			jasperDto.setDocumentazioneAmministrativa(listAllAmm);
		}
		if (allegati.getDocumentazioneTecnica() != null)
		{
			List<JasperAllegatoDto> listaAllTecnici = JasperIntegrazioneDocumentaleAdapter
			.adaptTecnica(allegati.getDocumentazioneTecnica().getGrigliaAllegatiCaricati(),allTipi);
			jasperDto.setDocumentazioneTecnica(listaAllTecnici);
		}
		List<JasperAllegatoDto> elencoAllegati = JasperIntegrazioneDocumentaleAdapter.adaptElencoAllegati(allegati,true);
		JasperListAllegatoDto listAllegato = new JasperListAllegatoDto();
		listAllegato.setElenco(elencoAllegati);
		jasperDto.setElencoAllegati(List.of(listAllegato));	
		return jasperDto;
	}

	public static List<JasperAllegatoDto> adaptAmministrativa(List<AllegatiDTO> list) {
		List<JasperAllegatoDto> returnList = null;
		if (list != null)
			returnList = list.stream().map(JasperAllegatoDto::new).map(jasperAll -> {
				jasperAll.setNomeAllegato(" -- ");
				return jasperAll;
			}).collect(Collectors.toList());
		else
			Collections.emptyList();
		return returnList;
	}

	public static List<JasperAllegatoDto> adaptTecnica(List<AllegatiDTO> list, List<TipoContenutoDTO> tipi) {
		List<JasperAllegatoDto> returnList = null;
		if (list != null)
			returnList = list.stream().map(all -> {
				JasperAllegatoDto jAll = new JasperAllegatoDto(all);
				final StringBuilder sb = new StringBuilder();
				if (all.getTipiContenuto() != null && all.getTipiContenuto().size() > 0) {
					all.getTipiContenuto().stream().forEach(tipoCode -> {
						tipi.stream().filter(tipoCont -> tipoCont.getDescrizione().equals(tipoCode)).findAny()
								.ifPresent(tt -> {
									sb.append(tt.getDescrizioneEstesa() + "<br><br>");
								});
					});
				}
				if (StringUtil.isNotBlank(sb.toString())) {
					jAll.setTipoDocumento(sb.toString());
				}
				return jAll;
			}).collect(Collectors.toList());
		else
			Collections.emptyList();
		return returnList;
	}

	/**
	 * sia per amministrativa che per tecnica crea 
	 * elenco unico utilizzato per elencate tutti i file con i relativi attributi (checksum etc..)
	 * @author acolaianni
	 *
	 * @param allegati
	 * @param aggiungiGrigliaPagamenti true se va considerata anche la sezione pagamenti (oneri e scansione marca da bollo)
	 * nell'integrazione vengono inseriti sempre nell'elenco allegati a differenza della presentazione istanza in cui vengono inserito in DomandaIstanza 
	 * e non in Schedatecnica.
	 * @return
	 */
	public static List<JasperAllegatoDto> adaptElencoAllegati(AllegatiDto allegati,boolean aggiungiGrigliaPagamenti) {
		List<JasperAllegatoDto> list = new ArrayList<JasperAllegatoDto>();
		if(aggiungiGrigliaPagamenti) {
			List<AllegatiDTO> grigliaPagamenti = allegati.getDocumentazioneAmministrativa()
					.getGrigliaPagamentoAllegati();
			addToLista(grigliaPagamenti,list);
		}
		List<AllegatiDTO> grigliaAllegatiCaricati = allegati.getDocumentazioneAmministrativa()
				.getGrigliaAllegatiCaricati();
		addToLista(grigliaAllegatiCaricati,list);
		List<AllegatiDTO> grigliaAllegatiDocumentazioneTecnica = allegati.getDocumentazioneTecnica()
				.getGrigliaAllegatiCaricati();
		addToLista(grigliaAllegatiDocumentazioneTecnica,list);
		return list;
	}
	
	private static void addToLista(List<AllegatiDTO> sourceList, List<JasperAllegatoDto> destList) {
		if(ListUtil.isNotEmpty(sourceList)) {
			for (AllegatiDTO allegato : sourceList) {
				if (allegato.getId() != null) {
					JasperAllegatoDto dto = new JasperAllegatoDto(allegato);
					destList.add(dto);
				}
			}	
		}
	}
	
}
