package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.adapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.ListenerUtils;
import org.springframework.stereotype.Component;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.FascicoloDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.config.SportelloConfigBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.AllegatoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.allegati.DocumentazioneAmministrativaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istanza.AltroTitolareDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istanza.DichiarazioniDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.SezioneAllegati;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperAltroTitolareDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperCheckBox;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperClausoleEsoneriDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperDichiarazioniArticoliDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperDichiarazioniDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperDomandaIstanzaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperTipoProcedimentoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.StringWrapper;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatoDelegatoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DisclaimerDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDelegatoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipoProcedimentoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.AllegatiRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.AllegatoDelegatoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.DisclaimerRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.TipoProcedimentoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.AllegatoDelegatoSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IConfigurazioneService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl.RuoloReferenteService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl.TipoRuoloDittaService;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.string.StringUtil;

@Component
public class JasperDichiarazioneIstanzaAdapter extends AbstractJasperDichiarazioneAdapter<JasperDomandaIstanzaDto>
{
	@Autowired
	TipoRuoloDittaService tipoRuoloDittaSvc;
	
	@Autowired
	RuoloReferenteService ruoloReferenteSvc;
	
	@Autowired
	DisclaimerRepository disclaimerDao;
	
	@Autowired
	TipoProcedimentoRepository tipoProcedimentoDao;
	
	@Autowired
	AllegatoDelegatoRepository allegatoDelegatoDao;
	
	@Autowired
	AllegatiRepository allegatoDao;
	
	@Autowired
	IConfigurazioneService configSvc;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JasperDichiarazioneIstanzaAdapter.class);
	
	

	@Override
	public JasperDomandaIstanzaDto adapt(final FascicoloDto fascicolo)
	{
		final JasperDomandaIstanzaDto jasperDto = new JasperDomandaIstanzaDto();
		super.adaptBaseData(jasperDto, fascicolo);
		
		TipoProcedimentoDTO tipoProcDto = tipoProcedimentoDao.findById(fascicolo.getTipoProcedimento());
		JasperTipoProcedimentoDto jaspTipoProcedimento = new JasperTipoProcedimentoDto(tipoProcDto);
		jasperDto.setListTipoProcedimento(List.of(jaspTipoProcedimento));
		JasperDichiarazioniDto jaspDichiarazioneDto = this.adaptDichiarazione(fascicolo.getIstanza().getDichiarazioni(), fascicolo.getTipoProcedimento());
		jaspDichiarazioneDto.setDescrstampatitolo(jaspTipoProcedimento.getDescrstampatitolo());
		jasperDto.setDichiarazioni(Collections.singletonList(jaspDichiarazioneDto));
		jasperDto.setAltriTitolari(this.adaptAltriTitolari(fascicolo.getIstanza().getAltriTitolari()));
		if(jasperDto.getDocumentazioneAmministrativa()==null) {
			jasperDto.setDocumentazioneAmministrativa(new ArrayList<>());
		}
		char letteraStampaLabel='A';
		letteraStampaLabel=(char)(letteraStampaLabel-1);
		try{
			if(fascicolo.getIstanza().getRichiedente().getDocumento().getDocAllegato().getId()!=null)
			{
				letteraStampaLabel++;
				jasperDto.getDocumentazioneAmministrativa()
				.add(this.buildDocumentoAmministrativo(letteraStampaLabel, 
						"Copia del documento di identità del richiedente",
						fascicolo.getIstanza().getRichiedente().getDocumento().getDocAllegato()));	
			}
			if(fascicolo.getIstanza().getDichiarazioni()!=null && 
			   fascicolo.getIstanza().getDichiarazioni().getAllegato()!=null && 
				fascicolo.getIstanza().getDichiarazioni().getAllegato().getId()!=null) {
				letteraStampaLabel++;
				jasperDto.getDocumentazioneAmministrativa().add(
						this.buildDocumentoAmministrativo(letteraStampaLabel, 
										"Dichiarazione di assenso di terzi titolari di altri diritti reali o obbligatori con relativa copia del documento di identità",
										fascicolo.getIstanza().getDichiarazioni().getAllegato()));	
			}
			if(fascicolo.getIstanza().getTecnicoIncaricato().getDocumento()!=null && 
			   fascicolo.getIstanza().getTecnicoIncaricato().getDocumento().getDocAllegato()!=null && 
			   fascicolo.getIstanza().getTecnicoIncaricato().getDocumento().getDocAllegato().getId()!=null)
			{
				letteraStampaLabel++;
				jasperDto.getDocumentazioneAmministrativa().add(
						this.buildDocumentoAmministrativo(letteraStampaLabel, 
										"Copia del documento di identità del tecnico incaricato",
										fascicolo.getIstanza().getTecnicoIncaricato().getDocumento().getDocAllegato()));
			}
			if(ListUtil.isNotEmpty(fascicolo.getIstanza().getAltriTitolari())){
				for(AltroTitolareDto altroTit:fascicolo.getIstanza().getAltriTitolari()) {
					if(altroTit.getDocumento()!=null && altroTit.getDocumento().getIdAllegato()!=null) {
						letteraStampaLabel++;
						String tipo="Copia del documento di identità altro titolare: "+altroTit.getNome() + " " + altroTit.getCognome();
						jasperDto.getDocumentazioneAmministrativa().add(
								this.buildDocumentoAmministrativo(letteraStampaLabel,
												tipo,
												altroTit.getDocumento().getDocAllegato()));
					}
					
				}
			}
			if(fascicolo.getDelegatoCorrente()!=null) {
				AllegatiDTO allegatoDelega = this.getFileDelega(fascicolo.getDelegatoCorrente());
				if(allegatoDelega!=null && allegatoDelega.getChecksum()!=null) {
					letteraStampaLabel++;
					String tipo="Documento di delega richiedente";
					jasperDto.getDocumentazioneAmministrativa().add(
							this.buildDocumentoAmministrativo(letteraStampaLabel, 
											tipo,
											allegatoDelega));
				}
			}
		}catch( final Exception e) {
			LOGGER.error("Errore nella generazione del bean jasper per l'istanza nella seizone allegati documentazione amministrativa, l'elaborazione continua. ",e);
		}
		if(fascicolo.getAllegati().getDocumentazioneAmministrativa()!=null &&
				fascicolo.getAllegati().getDocumentazioneAmministrativa().getGrigliaPagamentoAllegati()!=null &&
						fascicolo.getAllegati().getDocumentazioneAmministrativa().getGrigliaPagamentoAllegati().size()>0) {
			for(AllegatiDTO allPag:fascicolo.getAllegati().getDocumentazioneAmministrativa().getGrigliaPagamentoAllegati()) {
				letteraStampaLabel++;
				jasperDto.getDocumentazioneAmministrativa().add(
						this.buildDocumentoAmministrativo(letteraStampaLabel, 
										allPag.getDescrizione(),
										allPag));
			}
		}
		if(fascicolo.getTipoProcedimento() == 2)
			jasperDto.setDichiarazioniArticoli(Collections.singletonList(this.adaptDichiarazioniArticoli(fascicolo.getIstanza().getDichiarazioni())));
		addClausoleEsoneri(jasperDto, fascicolo);
		
		jasperDto.setIdFascicolo(fascicolo.getId().toString());
		
		return jasperDto;
	}
	
	
	private AllegatiDTO getFileDelega(PraticaDelegatoDTO delegatoCorrente) {
		AllegatiDTO ret=null;
		AllegatoDelegatoDTO allDelDto=this.allegatoDelegatoDao.findAllegatoBySezione(delegatoCorrente.getId(),delegatoCorrente.getIndice(), SezioneAllegati.ALLEGATO_DELEGA);
		AllegatiDTO pk=new AllegatiDTO();
		pk.setId(allDelDto.getIdAllegato());
		ret=allegatoDao.find(pk);
		return ret;
	}

	private void addClausoleEsoneri(JasperDomandaIstanzaDto jasperDto,FascicoloDto fascicolo) {
		try {
			SportelloConfigBean confSportello = 
					configSvc.findConfigurazioneCorrente(fascicolo.getDataCreazione(), SportelloConfigBean.class);
			JasperClausoleEsoneriDto clausoleEsoneri=new JasperClausoleEsoneriDto();
			List<JasperCheckBox> checkBoxs=new ArrayList<JasperCheckBox>();
			clausoleEsoneri.setClausole(checkBoxs);
			jasperDto.setClausoleEsoneri(Collections.singletonList(clausoleEsoneri));
			JasperCheckBox esoneroBollo=new JasperCheckBox();
			JasperCheckBox esoneroOneri=new JasperCheckBox();
			esoneroBollo.setIsChecked(fascicolo.getEsoneroBollo()!=null && fascicolo.getEsoneroBollo()==true?true:false);
			esoneroBollo.setLabel(confSportello.getEsoneroBolloLabel());
			esoneroOneri.setIsChecked(fascicolo.getEsoneroOneri()!=null && fascicolo.getEsoneroOneri()==true?true:false);
			esoneroOneri.setLabel(confSportello.getEsoneroOneriLabel());
			checkBoxs.add(esoneroOneri);
			checkBoxs.add(esoneroBollo);
			
		} catch (Exception e) {
			LOGGER.error("Errore nella fetch della configurazione sportello");
		}
	}
	
	private JasperDichiarazioniDto adaptDichiarazione(final DichiarazioniDto dichiarazione, final Integer tipoProcedimento)
	{
		final JasperDichiarazioniDto entity = new JasperDichiarazioniDto();
		entity.setTitolarita(dichiarazione.getTitolarita());
		entity.setTitoloAltroSpec(dichiarazione.getDiAvereTitoloAltroSpec());
		entity.setTitoloRappSpec(dichiarazione.getDiAvereTitoloRappSpec());
		entity.setImmobile(dichiarazione.getTitolaritaEsclusivaIntervento());
		entity.setTipoProcedimento(tipoProcedimento);
		final List<StringWrapper> disclaimer = new ArrayList<StringWrapper>();
		final List<DisclaimerDTO> allDisclaimer = this.disclaimerDao.selectByIds(dichiarazione.getAltreOpzioni());
		for(final DisclaimerDTO discl:allDisclaimer) {
			disclaimer.add(new StringWrapper(discl.getTesto()));
		}
		entity.setDisclaimer(disclaimer);
		return entity;
	}
	
	private List<JasperAltroTitolareDto> adaptAltriTitolari(final List<AltroTitolareDto> altriTitolari)
	{
		final List<JasperAltroTitolareDto> entities = new ArrayList<JasperAltroTitolareDto>();
		for(final AltroTitolareDto at: altriTitolari)
		{
			final JasperAltroTitolareDto entity = new JasperAltroTitolareDto();
			super.setBaseInfo(entity, at);
			entity.setIndirizzoEmail(at.getIndirizzoEmail());
			entity.setPec(at.getPec());
			entity.setRecapitoTelefonico(at.getRecapitoTelefonico());
			entity.setNelCaso(at.isNelCaso() != null && at.isNelCaso() == true);
			entity.setResidenteIn(Collections.singletonList(super.adaptIndirizzoCompleto(at.getResidenteIn())));
			if (entity.getNelCaso())
			{
				entity.setDittaInQualitaDiDescr(this.getDescDittaInQualitaDi(at.getInQualitaDi(), at.getDescAltroDitta()));
				entity.setInQualitaDi(at.getInQualitaDi());
				entity.setPartitaIva(at.getPartitaIva());
				entity.setSocieta(at.getSocieta());
				entity.setSocietaCodiceFiscale(at.getSocietaCodiceFiscale());
				entity.setDescAltroDitta(at.getDescAltroDitta());
				entity.setCodiceIpa(at.getCodiceIpa());
				entity.setTipoAzienda(at.getTipoAzienda());
			}
			entity.setTitolaritaInQualitaDi(this.getTitolaritaInQualitaDi(at.getTitolaritaInQualitaDi(), at.getDescrizioneAltro()));
			entities.add(entity);
		}
		return entities;
	}
	
	private JasperDichiarazioniArticoliDto adaptDichiarazioniArticoli(final DichiarazioniDto dichiarazioni)
	{
		final JasperDichiarazioniArticoliDto entity = new JasperDichiarazioniArticoliDto();
		if(dichiarazioni.getArt136() != null && !dichiarazioni.getArt136().isEmpty())
		{
			entity.setArt136(true);
			for(final String art136: dichiarazioni.getArt136())
			{
				switch (art136.toLowerCase())
				{
				case "a":
					entity.setArt136a(true);
					break;
				case "b":
					entity.setArt136b(true);
					break;
				case "c":
					entity.setArt136c(true);
					break;
				case "d":
					entity.setArt136d(true);
					break;
				}
			}
		}
		if(dichiarazioni.getArt142() != null && !dichiarazioni.getArt142().isEmpty())
		{
			entity.setArt142(true);
			for(final String art142: dichiarazioni.getArt142())
			{
				switch (art142.toUpperCase())
				{
				case "TERRITORI_COSTIERI":
					entity.setTerritoriCostieri(true);
					break;
				case "TERRITORI_LAGHI":
					entity.setTerritoriContermini(true);
					break;
				case "ACQUE_PUBBLICHE":
					entity.setFiumiTorrenti(true);
					break;
				case "BOSCHI":
					entity.setBoschi(true);
					break;
				case "ZONE_UMIDE_RAMSAR":
					entity.setZoneUmide(true);
					break;
				case "ZONE_USI_CIVICI":
					entity.setUsiCivici(true);
					break;
				case "ZONE_INT_ARCH":
					entity.setInteresseArcheologico(true);
					break;
				case "PARCHI_E_RISERVE":
					entity.setParchiRiserve(true);
					break;
				}
			}
		}
		entity.setArt134(dichiarazioni.getArt134());
		entity.setInCasoDiVariante(dichiarazioni.getInCasoDiVariante());
		entity.setDa(dichiarazioni.getDa());
		entity.setInData(dichiarazioni.getInData());
		entity.setNumero(dichiarazioni.getNumero());
		return entity;
	}
	
	@Override
	protected String getDescDittaInQualitaDi(final int inQualitaDi, final String descrAltroDitta) {
		
		try {
			if(inQualitaDi==3) {
				return descrAltroDitta;
					
			}else {
				Optional<String> descr=null;
				descr = this.tipoRuoloDittaSvc.select().stream().filter(el->el.getId()==inQualitaDi).map(el->el.getNome()).findAny();
				return descr.orElse("");
			}
		} catch (final Exception e) {
			LOGGER.error("Errore nel retrieval del tipo ruolo ditta",e);
		}
		return "";
	}
	
	private String getTitolaritaInQualitaDi(final int inQualitaDi, final String descrAltro) {
		try {
			final String ret = this.ruoloReferenteSvc.select().stream().filter(el->el.getId()==inQualitaDi).map(el->{
				if(el.getAttivaSpecifica()) {
					return descrAltro;
				}else {
					return el.getDescrizione(); 
				} 	
			}).findAny().orElse("");
			return ret;
		} catch (final Exception e) {
			LOGGER.error("Errore nel retrieval della titolarità in qualità di");
			
		}
		return "";
	}
	
	/**
	 * genera doppia riga, es:
	 *  A. tipologiadocumento
	 *  Nome file: nomedelfile.pdf
	 *  Checksum (SHA256): 4595f69b1b9920052dcc5dd68701c8f5bb5dedd156a2b70b0ad83c863c0f46ba
	 *  
	 * @author acolaianni
	 *
	 * @param lettera
	 * @param denominazione
	 * @param checksum
	 * @return
	 */
	private StringWrapper buildDocumentoAmministrativo(char lettera, String denominazione,
			AllegatiDTO allegato) {
		StringBuilder sb = new StringBuilder(
				StringUtil.concateneString("<b>",lettera, ". ", denominazione,"</b>"));
		if(allegato!=null) {
			if ( StringUtil.isNotEmpty(allegato.getNomeFile())) {
				sb.append("<br>")
				.append("<u>")
				.append("Nome file: ")
				.append("</u>")
				.append(allegato.getNomeFile())
				;
			}
			if (allegato.getSize()!=null) {
				sb.append("<br>")
				.append("<u>")
				.append("Dimensione : ")
				.append("</u>")
				.append(StringUtil.humanReadableFileSize(allegato.getSize()))
				;
			}
			if (StringUtil.isNotEmpty(allegato.getChecksum())) {
				sb.append("<br>")
				.append("<u>")
				.append("Checksum (SHA256): ")
				.append("</u>")
				.append(allegato.getChecksum())
				;
			}	
		}
		return new StringWrapper(sb.toString());
	}
	
}
