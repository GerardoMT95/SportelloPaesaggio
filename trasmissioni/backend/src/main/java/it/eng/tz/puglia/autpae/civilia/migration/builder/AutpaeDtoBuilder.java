package it.eng.tz.puglia.autpae.civilia.migration.builder;

import java.lang.reflect.Field;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;


import it.eng.tz.puglia.autpae.civilia.migration.dto.AutPaesPptrLocalizInterv;
import it.eng.tz.puglia.autpae.civilia.migration.dto.AutPaesPptrPratica;
import it.eng.tz.puglia.autpae.civilia.migration.dto.CiviliaMailAllegati;
import it.eng.tz.puglia.autpae.civilia.migration.dto.EnteParcoAss;
import it.eng.tz.puglia.autpae.civilia.migration.dto.InfoAutPaesPptrAlfa;
import it.eng.tz.puglia.autpae.civilia.migration.dto.InteressePubblicoAss;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PaesaggioRuraleAss;
import it.eng.tz.puglia.autpae.civilia.migration.dto.ParticelleCatastaliBase;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrCaratterizzazioneIntervento;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrCodiceInterno;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrParticelleCatastali;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrProtocolloUscita;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrProvvedimento;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrQualificIntervento;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrTipoIntervento;
import it.eng.tz.puglia.autpae.civilia.migration.dto.ReferentiProgettoDto;
import it.eng.tz.puglia.autpae.civilia.migration.dto.VtnoAllegatiPptr;
import it.eng.tz.puglia.autpae.civilia.migration.dto.putt.InfoAutPaesAlfaBean;
import it.eng.tz.puglia.autpae.civilia.migration.dto.putt.LocalizzazionePuttBean;
import it.eng.tz.puglia.autpae.civilia.migration.dto.putt.PuttDocBean;
import it.eng.tz.puglia.autpae.civilia.migration.dto.utils.AllegatiWithMultipart;
import it.eng.tz.puglia.autpae.civilia.migration.exception.CaratterizzazioneInterventoException;
import it.eng.tz.puglia.autpae.civilia.migration.exception.ProvvedimentoException;
import it.eng.tz.puglia.autpae.civilia.migration.service.IMigratorService;
import it.eng.tz.puglia.autpae.entity.AllegatoDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloInterventoDTO;
import it.eng.tz.puglia.autpae.entity.LocalizzazioneInterventoDTO;
import it.eng.tz.puglia.autpae.entity.ParchiPaesaggiImmobiliDTO;
import it.eng.tz.puglia.autpae.entity.ParticelleCatastaliDTO;
import it.eng.tz.puglia.autpae.entity.RichiedenteDTO;
import it.eng.tz.puglia.autpae.enumeratori.Ditta;
import it.eng.tz.puglia.autpae.enumeratori.EsitoProvvedimento;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;
import it.eng.tz.puglia.autpae.enumeratori.TipoProcedimento;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoService;
import it.eng.tz.puglia.autpae.utility.MockMultipartFile;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Classe di utility per popolare i bean richiesti dai repository di autpae 
 * partenendo dai bean popolati dai dati del db da migrare
 * 
 * @author Michele Santoro
 *
 */
public class AutpaeDtoBuilder
{

	private static final Logger log = LoggerFactory.getLogger(AutpaeDtoBuilder.class);
	
	public final static String MIGRAZIONE_SUBPATH="MIGRAZIONE_AUTPAE";
	
	public static String localBasePath;
	
	/**
	 * Metodo che crea un {@link FascicoloDTO} e lo popola partendo da un oggetto 
	 * di tipo {@link InfoAutPaesPptrAlfa}
	 * @param bean
	 * @return oggetto popolato di tipo {@link FascicoloDTO}
	 */
	public static FascicoloDTO buildFascicolo(InfoAutPaesPptrAlfa bean)
	{
		FascicoloDTO myBean = new FascicoloDTO();
		buildFascicolo(myBean, bean);
		return myBean;
	}
	/**
	 * Metodo che popola un {@link FascicoloDTO} passato in input partendo da un oggetto 
	 * di tipo {@link InfoAutPaesPptrAlfa}
	 * @param myBean
	 * @param extBean
	 * @return oggetto popolato di tipo {@link FascicoloDTO}
	 */
	public static FascicoloDTO buildFascicolo(FascicoloDTO myBean, InfoAutPaesPptrAlfa extBean) 
	{
		myBean.setCodice(extBean.getCodicePraticaEnteDelegato());
		myBean.setOggettoIntervento(extBean.getOggetto());
		myBean.setRup(extBean.getResponsabileProvvedimento());
		myBean.setNumeroProvvedimento(extBean.getNumeroProvvedimento());
		myBean.setDataProtocollo(extBean.getDataProvvedimento());
		myBean.setEsito(EsitoProvvedimento.fromString(extBean.getEsitoProvvedimento()));
		myBean.setDataTrasmissione(extBean.getDataTrasmissione());
		//myBean.setNote(extBean.getNote());
		//...
		return myBean;
	}
	
	/**
	 * Metodo che crea un {@link FascicoloDTO} e lo popola partendo da un oggetto 
	 * di tipo {@link AutPaesPptrPratica}
	 * @param bean
	 * @return oggetto popolato di tipo {@link FascicoloDTO}
	 */
	public static FascicoloDTO buildFascicolo(AutPaesPptrPratica bean)
	{
		FascicoloDTO myBean = new FascicoloDTO();
		buildFascicolo(myBean, bean);
		return myBean;
	}
	/**
	 * Metodo che popola un {@link FascicoloDTO} passato in input partendo da un oggetto
	 * setta tipoProcedimento,DescrizioneIntervento,Note,sanatoria 
	 * di tipo {@link AutPaesPptrPratica}
	 * @param myBean
	 * @param extBean
	 * @return oggetto popolato di tipo {@link FascicoloDTO}
	 */
	public static FascicoloDTO buildFascicolo(FascicoloDTO myBean, AutPaesPptrPratica extBean)
	{
		myBean.setTipoProcedimento(fromLongToTipoProcediento(extBean.getTipoProcedimentoId()));
		myBean.setDescrizioneIntervento(extBean.gettPraticaDescrizione());
		myBean.setNote(extBean.getNote());
		myBean.setSanatoria(extBean.getProvvedimentoInSanatoria().equals("1"));
		return myBean;
	}
	
	/**
	 * Metodo che crea un {@link LocalizzazioneInterventoDTO} e lo popola partendo da un oggetto 
	 * di tipo {@link AutPaesPptrLocalizInterv}
	 * @param bean
	 * @return oggetto popolato di tipo {@link LocalizzazioneInterventoDTO}
	 */
	public static LocalizzazioneInterventoDTO buildLocalizzazione(AutPaesPptrLocalizInterv bean)
	{
		LocalizzazioneInterventoDTO myBean = new LocalizzazioneInterventoDTO();
		buildLocalizzazione(myBean, bean);
		return myBean;
	}
	/**
	 * Metodo che popola un {@link LocalizzazioneInterventoDTO} passato in input partendo da un oggetto 
	 * di tipo {@link AutPaesPptrLocalizInterv}
	 * @param myBean
	 * @param extBean
	 * @return oggetto popolato di tipo {@link LocalizzazioneInterventoDTO}
	 */
	public static LocalizzazioneInterventoDTO buildLocalizzazione(LocalizzazioneInterventoDTO myBean, AutPaesPptrLocalizInterv extBean)
	{
		//myBean.setComuneId(extBean.getLocalizIntervComuneId());//Da vedere
		myBean.setSiglaProvincia(extBean.getLocalizIntervProvincia());
		myBean.setComune(extBean.getComune());
		myBean.setDestUsoAtt(extBean.getLocalizIntervDestUsoAtt());
		myBean.setDestUsoProg(extBean.getLocalizIntervDestUsoProg());
		myBean.setIndirizzo(extBean.getLocalizIntervIndirizzo());
		myBean.setInterno(extBean.getLocalizIntervInterno());
		myBean.setNumCivico(extBean.getLocalizIntervNciv());
		myBean.setPiano(extBean.getLocalizIntervPiano());
		myBean.setDataRiferimentoCatastale(handleStringDate(extBean.getDataRiferimentoCatastale(), "dd/MM/yyyy"));
		myBean.setStrade(extBean.getStrade() != null && extBean.getStrade().equals("S"));
		return myBean;
	}
	
	/**
	 * Metodo che crea una lista di {@link FascicoloInterventoDTO} e lo popola partendo da un oggetto 
	 * di tipo {@link PptrCaratterizzazioneIntervento}
	 * 
	 * @param myBean
	 * @param bean
	 * @param idFascicolo
	 * @return Lista di {@link FascicoloInterventoDTO}
	 * @throws CaratterizzazioneInterventoException 
	 */
	public static java.util.List<FascicoloInterventoDTO> buildFascicoloIntervento(FascicoloDTO myBean, PptrCaratterizzazioneIntervento bean, long idFascicolo,InfoAutPaesPptrAlfa info) throws CaratterizzazioneInterventoException
	{
		java.util.List<FascicoloInterventoDTO> list = new java.util.ArrayList<>();
		if(bean.getRimessaInPristino() != null && bean.getRimessaInPristino().equals("1"))
			list.add(new FascicoloInterventoDTO(idFascicolo, 6l));
		if(bean.getDemolizione() != null && bean.getDemolizione().equals("1"))
			list.add(new FascicoloInterventoDTO(idFascicolo, 7l));
		if(bean.getNuoviInsedAreaUrb() != null && bean.getNuoviInsedAreaUrb().equals("1"))
			list.add(new FascicoloInterventoDTO(idFascicolo, 8l));
		if(bean.getNuoviInsedAreaRurali() != null && bean.getNuoviInsedAreaRurali().equals("1"))
			list.add(new FascicoloInterventoDTO(idFascicolo, 9l));
		if(bean.getRistrManufRuraliSecco() != null && bean.getRistrManufRuraliSecco().equals("1"))
			list.add(new FascicoloInterventoDTO(idFascicolo, 10l));
		if(bean.getRistrManufRuraliNoSecco() != null && bean.getRistrManufRuraliNoSecco().equals("1"))
			list.add(new FascicoloInterventoDTO(idFascicolo, 11l));
		if(bean.getNuoviInsedIndCommerc() != null && bean.getNuoviInsedIndCommerc().equals("1"))
			list.add(new FascicoloInterventoDTO(idFascicolo, 12l));
		if(bean.getRistrInsedIndCommerc() != null && bean.getRistrInsedIndCommerc().equals("1"))
			list.add(new FascicoloInterventoDTO(idFascicolo, 13l));
		if(bean.getRecinzioni() != null && bean.getRecinzioni().equals("1"))
			list.add(new FascicoloInterventoDTO(idFascicolo, 14l));
		if(bean.getImpiantiProduzioneEnergia() != null && bean.getImpiantiProduzioneEnergia().equals("1"))
			list.add(new FascicoloInterventoDTO(idFascicolo, 15l));
		if(bean.getLineeTelefElettriche() != null && bean.getLineeTelefElettriche().equals("1"))
			list.add(new FascicoloInterventoDTO(idFascicolo, 16l));
		if(bean.getInfrastrutturePrimarie() != null && bean.getInfrastrutturePrimarie().equals("1"))
			list.add(new FascicoloInterventoDTO(idFascicolo, 17l));
		if(bean.getMiglioramentiFondiari() != null && bean.getMiglioramentiFondiari().equals("1"))
			list.add(new FascicoloInterventoDTO(idFascicolo, 18l));
		if(bean.getAltro() != null && bean.getAltro().equals("1"))
			list.add(new FascicoloInterventoDTO(idFascicolo, 19l));
		myBean.setInterventoSpecifica(bean.getAltroSpecificare());
		myBean.setInterventoDettaglio(bean.getRimessaInPristinoDett());
		String tempPerm = bean.getTempPerm();
		if(StringUtil.isNotBlank(tempPerm)) {
			switch (tempPerm) {
			case "T":
			case "E":	
				list.add(new FascicoloInterventoDTO(idFascicolo, 113l));
				break;
			case "F":
			case "P":
				list.add(new FascicoloInterventoDTO(idFascicolo, 114l));
				break;
			case "R":
				list.add(new FascicoloInterventoDTO(idFascicolo, 227l));
				break;
			default:
				throw new CaratterizzazioneInterventoException(info, "Errore nella conversione del radio temporaneo/permanente, valore inatteso:"+bean.getTempPerm()+ " atteso (T,R,F,E,P)", null);
			}	
		}else {
			//sembra che su un set di 170 pratiche sia effettivamente a null, forse per un periodo non è stato obbligatorio...
			log.warn(IMigratorService.LOG_MIGRAZIONE_MARKER,"Valore di Temporaneo/permanente vuoto per la pratica " +myBean.getCodicePraticaAppptr() + " "+myBean.gettPraticaId());
		}
		return list;
	}
	
	/**
	 * Metodo che crea un oggetto di tipo {@link ParticelleCatastaliDTO} e lo popola partendo da un oggetto 
	 * di tipo {@link ParticelleCatastaliBase}
	 * 
	 * @param bean
	 * @param idFascicolo
	 * @param idComune
	 * @return
	 */
	public static ParticelleCatastaliDTO buildParticelleCatastali(ParticelleCatastaliBase bean, Long idFascicolo, Long idComune)
	{
		ParticelleCatastaliDTO myBean = new ParticelleCatastaliDTO();
		myBean.setFoglio(bean.getFoglio());
		myBean.setParticella(bean.getParticella());
		myBean.setLivello(bean.getLivello());
		myBean.setSezione(bean.getSezione());
		myBean.setSub(bean.getSub());
		myBean.setCodCat(bean.getCodCat());
		myBean.setPraticaId(idFascicolo);
		myBean.setComuneId(idComune);
		return myBean;
	}
	
	/**
	 * Metodo che crea una lista di {@link FascicoloInterventoDTO} e lo popola partendo da un oggetto 
	 * di tipo {@link PptrQualificIntervento}
	 * 
	 * @param bean
	 * @param tp
	 * @param idFascicolo
	 * @return lista di {@link FascicoloInterventoDTO}
	 */
	public static java.util.List<FascicoloInterventoDTO> buildQualificazioneIntervento(PptrQualificIntervento bean, TipoProcedimento tp, long idFascicolo)
	{
		java.util.List<FascicoloInterventoDTO> list = new java.util.ArrayList<>();
		java.util.List<Field> fields = Arrays.asList(PptrQualificIntervento.class.getDeclaredFields());
		long idTp = fromTipoProcedientoToLong(tp);
		if(bean.getdLgs42146Rb01() != null && idTp == 1l)
		{
			switch(bean.getdLgs42146Rb01())
			{
			case "1": list.add(new FascicoloInterventoDTO(idFascicolo, 20l)); break;
			case "2": list.add(new FascicoloInterventoDTO(idFascicolo, 21l)); break;
			case "3": list.add(new FascicoloInterventoDTO(idFascicolo, 22l)); break;
			}
		}
		
		if(bean.getdLgs4291RbLieveEntita() != null)
		{
			if(idTp == 4)
			{
				switch(bean.getdLgs4291RbLieveEntita())
				{
				case "1": list.add(new FascicoloInterventoDTO(idFascicolo, 25l)); break;
				case "2": list.add(new FascicoloInterventoDTO(idFascicolo, 26l)); break;
				}
			}
			else if(idTp == 6)
			{
				switch(bean.getdLgs4291RbLieveEntita())
				{
				case "1": list.add(new FascicoloInterventoDTO(idFascicolo, 23l)); break;
				case "2": list.add(new FascicoloInterventoDTO(idFascicolo, 24l)); break;
				}
			}
		}
		
		if(idTp == 2)
		{
			for(Field f: fields)
			{
				f.setAccessible(true);
				
				if(f.getName().startsWith("dpr13991Chk") && check(f, bean, "1"))
				{
					try 
					{ 
						int n = Integer.parseInt(f.getName().subSequence(11, f.getName().length()).toString());
						list.add(new FascicoloInterventoDTO(idFascicolo, 26l + n));
					} catch(NumberFormatException e) {}
				}
			}
		}
		
		if(idTp == 3)
		{
			if(bean.getdLgs42167ChkA() != null && bean.getdLgs42167ChkA().equals("1"))
				list.add(new FascicoloInterventoDTO(idFascicolo, 110l));
			if(bean.getdLgs42167ChkB() != null && bean.getdLgs42167ChkB().equals("1"))
				list.add(new FascicoloInterventoDTO(idFascicolo, 111l));
			if(bean.getdLgs42167ChkC() != null && bean.getdLgs42167ChkC().equals("1"))
				list.add(new FascicoloInterventoDTO(idFascicolo, 112l));
		}
		
		if(idTp == 4)
		{
			for(Field f: fields)
			{
				f.setAccessible(true);
				
				if(f.getName().startsWith("dpr13991Chk") && check(f, bean, "1"))
				{
					try 
					{ 
						int n = Integer.parseInt(f.getName().subSequence(11, f.getName().length()).toString());
						list.add(new FascicoloInterventoDTO(idFascicolo, 126l + n));
					} catch(NumberFormatException e) {}
				}
			}
		}
		
		if(idTp == 5 || idTp == 6)
		{
			if(idTp == 5)
			{
				if(bean.getDpr3190Chk1() != null && bean.getDpr3190Chk1().equals("1"))
					list.add(new FascicoloInterventoDTO(idFascicolo, 66l));
				if(bean.getDpr3190Chk2() != null && bean.getDpr3190Chk2().equals("1"))
					list.add(new FascicoloInterventoDTO(idFascicolo, 67l));
			}
			for(Field f: fields)
			{
				f.setAccessible(true);
				
				if(f.getName().startsWith("dpr3190Chk2_") && check(f, bean, "1"))
				{
					try 
					{ 
						int n = Integer.parseInt(f.getName().subSequence(12, f.getName().length()).toString());
						list.add(new FascicoloInterventoDTO(idFascicolo, 67l + n));
					} catch(NumberFormatException e) {}
				}
			}
		}
		
		return list;
	}
	
	/**
	 * Metodo che popola un oggetto {@link FascicoloDTO} e lo popola partendo da un oggetto 
	 * di tipo {@link PptrProvvedimento}
	 * 
	 * @param myBean
	 * @param extBean
	 * @return {@link FascicoloDTO}
	 */
	public static FascicoloDTO buildProvvedimento(FascicoloDTO myBean, PptrProvvedimento extBean)
	{
		myBean.setNumeroProvvedimento(extBean.getNumeroProvvedimento());
		myBean.setEsito(fromStringToEsito(extBean.getFlagEsito()));
		myBean.setDataRilascioAutorizzazione(extBean.getDataProvvedimento());
		myBean.setRup(extBean.getRup());
		return myBean;
	}
	
	/**
	 * Metodo che crea una lista di {@link ParchiPaesaggiImmobiliDTO} e lo popola partendo da lista di 
	 * {@link EnteParcoAss}, {@link InteressePubblicoAss} e {@link PaesaggioRuraleAss}
	 * 
	 * @param idFascicolo
	 * @param parchi
	 * @param interessi
	 * @param paesaggi
	 * @return lista di {@link ParchiPaesaggiImmobiliDTO}
	 */
	public static List<ParchiPaesaggiImmobiliDTO> buildPrachiPaesaggiImmobili(Long idFascicolo,Long comuneId,
																			  List<EnteParcoAss> parchi, 
																			  List<InteressePubblicoAss> interessi, 
																			  List<PaesaggioRuraleAss> paesaggi)
	{
		List<ParchiPaesaggiImmobiliDTO> list = new ArrayList<ParchiPaesaggiImmobiliDTO>();
		//aggiungo parchi
		if(parchi != null)
		{
			for(EnteParcoAss p: parchi)
			{
				ParchiPaesaggiImmobiliDTO entity = new ParchiPaesaggiImmobiliDTO();
				entity.setPraticaId(idFascicolo);
				entity.setDescrizione(p.getDescParco());
				entity.setSelezionato(p.getEnteParcoTipoAssId() == 1 ? "S" : null);
				entity.setCodice(p.getTnoEnteParcoMapId()+"");
				entity.setTipoVincolo("P");
				entity.setComuneId(comuneId);
				list.add(entity);
			}
		}
		//aggiungo immobili
		if(interessi != null)
		{
			for(InteressePubblicoAss i: interessi)
			{   
				ParchiPaesaggiImmobiliDTO entity = new ParchiPaesaggiImmobiliDTO();
				entity.setPraticaId(idFascicolo);
				entity.setDescrizione(i.getOggetto());
				entity.setSelezionato(i.getInteressePubbTipoAssId() == 1 ? "S" : null);
				entity.setCodice(i.getCodiceVincolo());
				entity.setTipoVincolo("I");
				entity.setComuneId(comuneId); 
				list.add(entity);
			}
		}
		//aggiungo paesaggi rurali
		if(paesaggi != null)
		{
			for(PaesaggioRuraleAss r: paesaggi)
			{
				ParchiPaesaggiImmobiliDTO entity = new ParchiPaesaggiImmobiliDTO();
				entity.setPraticaId(idFascicolo);
				entity.setDescrizione(r.getDescPaesaggioRurale());
				entity.setSelezionato(r.getPaesaggioRuraleTipoAssId() == 1 ? "S" : null);
				entity.setTipoVincolo("R");
				entity.setCodice(r.getTnoPaesaggiRuraliMapId()+"");
				entity.setComuneId(comuneId); 
				list.add(entity);
			}
		}
		
		return list;
	}
	
	public static AllegatiWithMultipart buildAllegato(VtnoAllegatiPptr bean, TipoAllegato tipo) throws Exception
	{
		MultipartFile m = new MockMultipartFile(bean.getNomeFile(), bean.getBinContent().getBinaryStream());
		AllegatiWithMultipart myBean = new AllegatiWithMultipart(m);
		myBean.setNome(bean.getNomeAllegato());
		myBean.setTipo(tipo);
		myBean.setMimeType(bean.gettKeDocAttrValue());
		myBean.setDimensione((int)m.getSize());
		myBean.setDescrizione(bean.getDescrizione());
		myBean.setDataProtocolloIn(bean.getDataProtocollo());//data protocollo out?
		myBean.setTitolo(bean.getName());
		myBean.setUsernameInserimento("MIGRAZIONE"); //Non è presente l'info...
		myBean.setDataCaricamento(new java.util.Date()); //FIXME bean.getDataArrivo() type mismatch
		return myBean;
	}
	
	private static java.util.Date handleStringDate(String strDate, String pattern)
	{
		java.util.Date d = null;
		if(StringUtil.isEmpty(strDate))
				return null;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try
		{
			d = sdf.parse(strDate);
		}
		catch(ParseException e)
		{
			try 
			{
				Integer year = Integer.parseInt(strDate);
				if(year != null && year > 0)
				{
					sdf.applyPattern("dd/MM/yyyy");
					String strDate2 = "01/01/"+year;
					d = sdf.parse(strDate2);
				}
			} catch(NumberFormatException|ParseException ex) {}
		}
		return d;
	}
	
	private static java.util.Date handleStringDate(String strDate, SimpleDateFormat sdf)
	{
		java.util.Date d = null;
		if(StringUtil.isEmpty(strDate))
				return null;
		try
		{
			d = sdf.parse(strDate);
		}
		catch(ParseException e)
		{
			try 
			{
				Integer year = Integer.parseInt(strDate);
				if(year != null && year > 0)
				{
					sdf.applyPattern("dd/MM/yyyy");
					String strDate2 = "01/01/"+year;
					d = sdf.parse(strDate2);
				}
			} catch(NumberFormatException|ParseException ex) {}
		}
		return d;
	}
	
	private static TipoProcedimento fromLongToTipoProcediento(Long tpId)
	{
		switch(tpId.intValue())
		{
		case 1: return TipoProcedimento.AUT_PAES_ORD;
		case 2: return TipoProcedimento.AUT_PAES_SEMPL_DPR_139_2010;
		case 3: return TipoProcedimento.ACCERT_COMPAT_PAES_DLGS_42_2004;
		case 4: return TipoProcedimento.ACCERT_COMPAT_PAES_DPR_139_2010;
		case 5: return TipoProcedimento.AUT_PAES_SEMPL_DPR_31_2017;
		case 6: return TipoProcedimento.ACCERT_COMPAT_PAES_DPR_31_2017;
		default: return null;
		}
	}
	
	private static Long fromTipoProcedientoToLong(TipoProcedimento tp)
	{
		switch(tp)
		{
		case AUT_PAES_ORD: return 1l;
		case AUT_PAES_SEMPL_DPR_139_2010: return 2l;
		case ACCERT_COMPAT_PAES_DLGS_42_2004: return 3l;
		case ACCERT_COMPAT_PAES_DPR_139_2010: return 4l;
		case AUT_PAES_SEMPL_DPR_31_2017: return 5l;
		case ACCERT_COMPAT_PAES_DPR_31_2017: return 6l;
		default: return null;
		}
	}
	
	private static boolean check(Field f, Object o, Object toCheck)
	{
		boolean checked = false;
		try { checked = f.get(o).equals(toCheck);}
		catch (Exception e) {}
		return checked;
	}
	
	private static EsitoProvvedimento fromStringToEsito(String esito)
	{
		if(esito == null) return null;
		switch(esito.toUpperCase())
		{
		case "A": return EsitoProvvedimento.AUTORIZZATO;
		case "N": return EsitoProvvedimento.NON_AUTORIZZATO;
		case "P": return EsitoProvvedimento.AUT_CON_PRESCRIZ;
		default: return null;
		}
	}
	
	public static void addProvvedimentoField(FascicoloDTO fascicolo,PptrProvvedimento extProvv,InfoAutPaesPptrAlfa infoPratica) throws ProvvedimentoException {
		fascicolo.setNumeroProvvedimento(extProvv.getNumeroProvvedimento());
		fascicolo.setDataRilascioAutorizzazione(extProvv.getDataProvvedimento());
		fascicolo.setRup(extProvv.getRup());
		fascicolo.setEsito(EsitoProvvedimento.fromCiviliaValue(extProvv.getFlagEsito()));
		if(fascicolo.getEsito()==null) {
			throw new ProvvedimentoException(infoPratica,"Esito provvedimento inatteso source:"+ extProvv.getFlagEsito(),null); 
		}
	}
	
	public static void addProvvedimentoAttachment(InfoAutPaesPptrAlfa infoPratica,
			FascicoloDTO fascicolo,List<VtnoAllegatiPptr> allegatiProvvedimento,
			AllegatoService allegatoService,boolean noAllegati,boolean inLavorazione) 
			throws ProvvedimentoException {
		boolean hasProvv=false;
		for(VtnoAllegatiPptr allegatoProvvedimento:allegatiProvvedimento) {
			TipoAllegato tipo=null;
			if(allegatoProvvedimento.getPptrTipoAllegatoDescrizione().equalsIgnoreCase("PARERE MIBAC")) {
				tipo=TipoAllegato.PARERE_MIBAC;
			}else if(allegatoProvvedimento.getPptrTipoAllegatoDescrizione().equalsIgnoreCase("PROVVEDIMENTO FINALE")) {
				tipo=TipoAllegato.PROVVEDIMENTO_FINALE;
				hasProvv=true;
			} 
			if(tipo!=null) {
				try {
					if(allegatoProvvedimento.getBinContent()==null || allegatoProvvedimento.getBinContent().length()<=0) {
						throw new ProvvedimentoException(infoPratica, "File a dimensione nulla " + tipo.name() + " " +allegatoProvvedimento.getNomeFile(),null);
					}
					allegatoService.inserisciAllegatoDaMigrazione(fascicolo, Collections.singletonList(tipo), allegatoProvvedimento.getBinContent(),
							allegatoProvvedimento.getNomeFile(), tipo.name(),allegatoProvvedimento.gettKeDocAttrValue(),
							MIGRAZIONE_SUBPATH, localBasePath,
							null, null, allegatoProvvedimento.getDataArrivo(),null,noAllegati);
				} catch (Exception e) {
					throw new ProvvedimentoException(infoPratica, "Errore durante l'elaborazione del file provvedimento " + tipo.name() + " " +allegatoProvvedimento.getNomeFile(), e);
				}	
			}
		//scrivo nei repo tutte le info del file (compreso path su alfresco) e metto nel campo dell'idalfresco un placeholder
		//nel frattempo scrivo i file in locale in una cartella che poi andro' ad uploadare su alfresco e tramite path
		// del file su alfresco, sostituisco placeholder con id reale.
		}
		if(!hasProvv && !inLavorazione) {
			throw new ProvvedimentoException(infoPratica, "Nessun file provvedimento ", null);
		}
	}
	/**
	 * popola codice_interno_ente ,	numero_interno_ente,protocollo ,data_protocollo 
	 * @autor Adriano Colaianni
	 * @date 23 apr 2021
	 * @param fascicoloDTO
	 * @param pptrEnte
	 * @return
	 */
	public static FascicoloDTO buildFascicolo(FascicoloDTO fascicoloDTO, PptrCodiceInterno pptrEnte) {
		fascicoloDTO.setCodiceInternoEnte(pptrEnte.getRifIntAlfanumerico());
		fascicoloDTO.setNumeroInternoEnte(pptrEnte.getRifNumero());
		fascicoloDTO.setProtocollo(pptrEnte.getRifProtocollo());
		fascicoloDTO.setDataProtocollo(pptrEnte.getRifData());
		return fascicoloDTO;
	}
	
	//
	
	public static java.util.List<FascicoloInterventoDTO> buildFascicoloTipoIntervento(FascicoloDTO myBean, PptrTipoIntervento bean, long idFascicolo,InfoAutPaesPptrAlfa info) throws CaratterizzazioneInterventoException
	{
		java.util.List<FascicoloInterventoDTO> list = new java.util.ArrayList<>();
		if(bean.getInterventiNoEdil()!=null && bean.getInterventiNoEdil().equals("1")) {
			list.add(new FascicoloInterventoDTO(idFascicolo, 1l));
		}
		if(bean.getManutenzione()!=null && bean.getManutenzione().equals("1")) {
			list.add(new FascicoloInterventoDTO(idFascicolo, 2l));
		}
		if(bean.getNuovaCostruzione()!=null && bean.getNuovaCostruzione().equals("1")) {
			list.add(new FascicoloInterventoDTO(idFascicolo, 3l));
		}
		if(bean.getRistEdilizia()!=null && bean.getRistEdilizia().equals("1")) {
			list.add(new FascicoloInterventoDTO(idFascicolo, 4l));
		}
		if(bean.getRistUrbanistica()!=null && bean.getRistUrbanistica().equals("1")) {
			list.add(new FascicoloInterventoDTO(idFascicolo, 5l));
		}
		if(list.size()==0) {
			throw new CaratterizzazioneInterventoException(info, "Errore nella conversione del radio Tipo intervento, nessun valore rilevato", null);
		}
		if(list.size()>1) {
			throw new CaratterizzazioneInterventoException(info, "Errore nella conversione del radio Tipo intervento, valore a selezione singola con più valori "+list, null);
		}	
		return list;
	}
	/**
	 * @autor Adriano Colaianni
	 * @date 26 apr 2021
	 * @param referentiProgettoDto
	 * @return
	 */
	public static RichiedenteDTO buildRichiedente(ReferentiProgettoDto referentiProgettoDto) {
		RichiedenteDTO ret=new RichiedenteDTO();
		ret.setNome(referentiProgettoDto.getRichiedenteNome());
		ret.setCognome(referentiProgettoDto.getRichiedenteCognome());
		ret.setCodiceFiscale(referentiProgettoDto.getRichiedenteCodiceFiscale());
		//ret.setSesso(); 
		ret.setStatoNascita(referentiProgettoDto.getStatoNascita());
		ret.setProvinciaNascita(referentiProgettoDto.getProvNascita());
		ret.setComuneNascita(referentiProgettoDto.getComuneNascita());
		ret.setDataNascita(referentiProgettoDto.getDataNascita());
		ret.setStatoResidenza(referentiProgettoDto.getStatoResidenza());
		ret.setProvinciaResidenza(referentiProgettoDto.getProvResidenza());
		ret.setComuneResidenza(referentiProgettoDto.getComuneResidenza());
		ret.setViaResidenza(referentiProgettoDto.getIndirizzo());
		ret.setNumeroResidenza(referentiProgettoDto.getNumCivico());
		ret.setCap(referentiProgettoDto.getCap());
		ret.setTelefono(referentiProgettoDto.getTelFisso());
		if(StringUtil.isNotEmpty(referentiProgettoDto.getDittaRuoloRich())){
			ret.setDitta(true);
			ret.setDittaCodiceFiscale(referentiProgettoDto.getDittaCodiceFiscale());
			ret.setDittaPartitaIva(referentiProgettoDto.getDittaPartitaIva());
			ret.setDittaSocieta(referentiProgettoDto.getDittaRagioneSociale());
			ret.setDittaInQualitaDi(Ditta.ALTRO.name());
			ret.setDittaQualitaAltro(referentiProgettoDto.getDittaRuoloRich());
		}
		ret.setEmail(referentiProgettoDto.getIndirizzoMail());
		ret.setPec(referentiProgettoDto.getIndirizzoPec());
		return ret;
	}
	
	/**
	 * mapping particelle
	 * @autor Adriano Colaianni
	 * @date 27 apr 2021
	 * @param listaOld
	 * @param locIntNew
	 * @return
	 */
	public static List<ParticelleCatastaliDTO> buildLocalizzazioneParticelle(List<PptrParticelleCatastali> listaOld,LocalizzazioneInterventoDTO locIntNew)
	{
		//myBean.setComuneId(extBean.getLocalizIntervComuneId());//Da vedere
		List<ParticelleCatastaliDTO> listaNew=new ArrayList<>();
		int counter=1;
		if(ListUtil.isNotEmpty(listaOld)) {
			for(PptrParticelleCatastali particellaOld:listaOld){
				ParticelleCatastaliDTO particellaNew=new ParticelleCatastaliDTO();
				particellaNew.setComuneId(locIntNew.getComuneId());
				particellaNew.setId(counter++);
				particellaNew.setPraticaId(locIntNew.getPraticaId());
				particellaNew.setSezione(particellaOld.getSezione());
				particellaNew.setLivello(particellaOld.getLivello());
				particellaNew.setFoglio(particellaOld.getFoglio());
				particellaNew.setParticella(particellaOld.getParticella());
				particellaNew.setSub(particellaOld.getSub());
				particellaNew.setDescrSezione(particellaOld.getNomeSezione());
				particellaNew.setCodCat(particellaOld.getCodCat());
				listaNew.add(particellaNew);
			}
		}
		return listaNew;
	}
	
	
	/**
	 * 
	 * @autor Adriano Colaianni
	 * @date 27 apr 2021
	 * @param infoPratica
	 * @param fascicolo
	 * @param allegati
	 * @param allegatoService
	 * @throws ProvvedimentoException
	 */
	public static void addAttachment(InfoAutPaesPptrAlfa infoPratica,
			FascicoloDTO fascicolo,List<VtnoAllegatiPptr> allegati,
			AllegatoService allegatoService,boolean noAllegati) 
			throws ProvvedimentoException {
		for(VtnoAllegatiPptr allegato:allegati) {
			Map<String, List<VtnoAllegatiPptr>> mappaContenuti = VtnoAllegatiPptr.getMapAllegatiFacoltativi_byKey_tnTKeDocStId(allegati);
			Set<TipoAllegato> tipi=new HashSet<>();
			//sono raggruppati per file
			Set<String> files = mappaContenuti.keySet();
			for(String file:files){
				List<VtnoAllegatiPptr> listaContenuti = mappaContenuti.get(file);
				listaContenuti.forEach(contenuto->{
					TipoAllegato tipo = TipoAllegato.fromCiviliaValue(contenuto.getPptrTipoAllegatoCodice());
					if(tipo==null) {
						log.error(IMigratorService.LOG_MIGRAZIONE_MARKER,"TipoAllegato non mappato: "+contenuto.getPptrTipoAllegatoDescrizione());
					}else {
						tipi.add(tipo);
					}
				});
			}
			if(ListUtil.isNotEmpty(tipi)) {
				try {
					if(allegato.getBinContent()==null || allegato.getBinContent().length()<=0) {
						throw new ProvvedimentoException(infoPratica, "File a dimensione nulla " + allegato.getName()+ " " +allegato.getNomeFile(),null);	
					}
					allegatoService.inserisciAllegatoDaMigrazione(fascicolo, new ArrayList<>(tipi), allegato.getBinContent(),
							allegato.getNomeFile(), ((TipoAllegato)(tipi.toArray()[0])).name(),allegato.gettKeDocAttrValue(),
							MIGRAZIONE_SUBPATH, localBasePath,
							null, null, allegato.getDataArrivo(),allegato.getnTKeDocStId(),noAllegati);
				} catch (Exception e) {
					throw new ProvvedimentoException(infoPratica, "Errore durante l'elaborazione del'allegato tipi "+tipi+" nomefile " + allegato.getNomeFile(), e);
				}	
			}
		}
		
	}
	
	private static Blob getBlobRicevuta(PptrProtocolloUscita ricevuta) throws SQLException {
		return ricevuta.getBinPdfContent().length()>ricevuta.getBinPdfProtContent().length()?
				ricevuta.getBinPdfContent():ricevuta.getBinPdfProtContent();
	}
	
	public static void inserisciRicevutaTrasmissione(PptrProtocolloUscita ricevuta,AllegatoService allegatoSvc,FascicoloDTO fascicoloDTO,boolean noAllegati, InfoAutPaesPptrAlfa infoPratica) throws SQLException, Exception {
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		final StringBuilder sb=new StringBuilder();
		sb
		.append(ricevuta.getTitolarioProtocollo())
		.append("/")
		.append(sdf.format(ricevuta.getDataprotocollo()))
		.append("/")
		.append(ricevuta.getNumeroProtocollo());
		if(getBlobRicevuta(ricevuta).length()<=0) {
			throw new ProvvedimentoException(infoPratica,"File ricevuta trasmissione autorizzazione a a dimensione nulla  "+fascicoloDTO.getCodice(),null);	
		}
		allegatoSvc.inserisciAllegatoDaMigrazione(fascicoloDTO, Collections.singletonList(TipoAllegato.RICEVUTA_TRASMISSIONE),
				getBlobRicevuta(ricevuta),
						TipoAllegato.RICEVUTA_TRASMISSIONE.getTextValue().replaceAll(" ", "_").concat(".pdf"),
						TipoAllegato.RICEVUTA_TRASMISSIONE.getTextValue(), 
						"application/pdf",
				MIGRAZIONE_SUBPATH, localBasePath,
				sb.toString(), ricevuta.getDataprotocollo(), ricevuta.getDataprotocollo(),ricevuta.getTnoPptrTrasmissioneId()+"",
				noAllegati);
		
		allegatoSvc.inserisciAllegatoDaMigrazione(fascicoloDTO, Collections.singletonList(TipoAllegato.ANTEPRIMA_TRASMISSIONE),
				ricevuta.getBinPdfContent(),
						"Segnatura_xml_protocollo.xml",
						TipoAllegato.ANTEPRIMA_TRASMISSIONE.getTextValue(), 
						"text/xml",
				MIGRAZIONE_SUBPATH, localBasePath,
				null, null, ricevuta.getDataprotocollo(),ricevuta.getTnoPptrTrasmissioneId()+"",noAllegati);
	}
	
	public static AllegatoDTO inserisciAllegatoMail(CiviliaMailAllegati allegato,AllegatoService allegatoSvc,FascicoloDTO fascicoloDTO,Date dataMail,TipoAllegato tipoAllegato,boolean noAllegati) throws SQLException, Exception {
		StringBuilder contentType=new StringBuilder();
		if(ListUtil.isNotEmpty(allegato.getAttributi())) {
			allegato.getAttributi().stream().filter(attr->attr.getName().equalsIgnoreCase("FORMATO")).findAny().ifPresent(attr->contentType.append(attr.getValue()));
		}
		if(contentType.length()==0) {
			contentType.append("application/binary"); //default generico...
		}
		if(allegato.getBinContent()==null || allegato.getBinContent().length()<=0) {
			throw new ProvvedimentoException(null,"File allegato mail a dimensione nulla  "+fascicoloDTO.getCodice(),null);	
		}
		
		AllegatoDTO ret = allegatoSvc.inserisciAllegatoDaMigrazione(fascicoloDTO, Collections.singletonList(tipoAllegato),
				allegato.getBinContent(),
						allegato.getNomeFile(), 
						tipoAllegato.getTextValue(),
						contentType.toString(),
				MIGRAZIONE_SUBPATH, localBasePath,
				null, null, dataMail,allegato.gettKeDocId()+"",noAllegati);
		return ret;
	}
	
	//--------------------------- PUTT -----------------------------------------------------
	
	/**
	 * Popola {@link FascicoloDTO} con i dati presi da {@link InfoAutPaesAlfaBean}
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static FascicoloDTO buildFascicoloPutt(InfoAutPaesAlfaBean bean, RichiedenteDTO responsabile) throws Exception
	{
		Date dAtt = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yy", Locale.ENGLISH);
		dAtt = handleStringDate(bean.getSuapDataAttivazione(), sdf);
		
		FascicoloDTO result = new FascicoloDTO();
		result.setCodice(bean.getCodicePratica());
		result.setCodiceInternoEnte(bean.getnCodiceinterno());
		result.setCodicePraticaAppptr(bean.getCodicePratica());
		result.setDataDelibera(dAtt);
		result.setDataCreazione(dAtt);
		result.setTipoProcedimento(toPuttTipoProc(bean.getTipologia()));
		result.setSanatoria(toSanatoria(bean.getSanatoria()));
		result.settPraticaId(bean.getPraticaId());
		result.setNote(bean.getnNote());
		result.setOggettoIntervento(bean.getSuapOggettoPratica());
		//provvedimento finale
		result.setNumeroProvvedimento(bean.getNumeroProvvedimento());
		result.setEsito(toEsito(bean.getEsitoProvvedimento()));
		result.setDataRilascioAutorizzazione(handleStringDate(bean.getDataPro(), "dd/MM/yyyy"));
		result.setRup(bean.getResponsab());
		//data trasmissione
		result.setDataTrasmissione(bean.getDataTrasmissione());
		if(result.getDataTrasmissione()==null) {
			result.setDataTrasmissione(result.getDataRilascioAutorizzazione());
		}
		if(result.getDataTrasmissione()==null) {
			result.setDataTrasmissione(new Date()); //poi al massimo la correggo....
		}
		if(result.getDataCreazione()==null) {//pezza se non riesco a rivavarla 
			//TODO
			result.setDataCreazione(result.getDataTrasmissione());
		}
		//responsabile
		responsabile.setCognome(bean.getRichiedente());
		return result;
	}
	
	/**
	 * Popola il tab descrizione intervento con le info disponibili
	 * @param bean
	 * @param fascicolo
	 * @param idFascicolo
	 * @return
	 */
	public static List<FascicoloInterventoDTO> buildFascicoloIntervento(InfoAutPaesAlfaBean bean, FascicoloDTO fascicolo, long idFascicolo)
	{
		List<FascicoloInterventoDTO> result = new ArrayList<>();
		if(StringUtil.isNotBlank(bean.getTipoIntervento()))
		{
			FascicoloInterventoDTO dto = new FascicoloInterventoDTO();
			dto.setIdFascicolo(idFascicolo);
			
			if(bean.getTipoIntervento().trim().equals("Altro-interventi e/o opere di grande impegno territoriale (DPCM 12/12/2005)"))
			{
				dto.setIdTipiQualificazioni(19l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(201l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Altro-interventi e/o opere non di edilizia"))
			{
				dto.setIdTipiQualificazioni(19l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(1l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Altro-manutenzione, restauro e risanamento conservativo (art. 3 DPR 380/01)"))
			{
				dto.setIdTipiQualificazioni(19l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(2l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Altro-nuova costruzione (art. 3 DPR 380/01)"))
			{
				dto.setIdTipiQualificazioni(19l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(3l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Altro-ristrutturazione edilizia (art. 3 DPR 380/01)"))
			{
				dto.setIdTipiQualificazioni(19l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(4l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Edifici rurali ex novo"))
			{
				dto.setIdTipiQualificazioni(221l);
			}
			else if(bean.getTipoIntervento().trim().equals("Edifici rurali: ampliamenti/ristrutturazioni"))
			{
				dto.setIdTipiQualificazioni(222l);
			}
			else if(bean.getTipoIntervento().trim().equals("Impianto produzione energia"))
			{
				dto.setIdTipiQualificazioni(224l);
			}
			else if(bean.getTipoIntervento().trim().equals("Infrastrutture per telefonia") || bean.getTipoIntervento().equals("Infrastrutture per telefonia-NON DEFINITO"))
			{
				dto.setIdTipiQualificazioni(225l);
			}
			else if(bean.getTipoIntervento().trim().equals("Infrastrutture per telefonia-interventi e/o opere di grande impegno territoriale (DPCM 12/12/2005)"))
			{
				dto.setIdTipiQualificazioni(225l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(201l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Infrastrutture per telefonia-interventi e/o opere non di edilizia"))
			{
				dto.setIdTipiQualificazioni(225l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(1l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Infrastrutture per telefonia-manutenzione, restauro e risanamento conservativo (art. 3 DPR 380/01)"))
			{
				dto.setIdTipiQualificazioni(225l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(2l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Infrastrutture per telefonia-nuova costruzione (art. 3 DPR 380/01)"))
			{
				dto.setIdTipiQualificazioni(225l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(3l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Infrastrutture per telefonia-ristrutturazione edilizia (art. 3 DPR 380/01)"))
			{
				dto.setIdTipiQualificazioni(225l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(4l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Infrastrutture primarie (viarie, acqua, gas, ecc.)") || bean.getTipoIntervento().equals("Infrastrutture primarie (viarie, acqua, gas, ecc.)-NON DEFINITO"))
			{
				dto.setIdTipiQualificazioni(17l);
			}
			else if(bean.getTipoIntervento().trim().equals("Infrastrutture primarie (viarie, acqua, gas, ecc.)-interventi e/o opere di grande impegno territoriale (DPCM 12/12/2005)"))
			{
				dto.setIdTipiQualificazioni(17l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(201l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Infrastrutture primarie (viarie, acqua, gas, ecc.)-interventi e/o opere non di edilizia"))
			{
				dto.setIdTipiQualificazioni(17l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(1l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Infrastrutture primarie (viarie, acqua, gas, ecc.)-manutenzione, restauro e risanamento conservativo (art. 3 DPR 380/01)"))
			{
				dto.setIdTipiQualificazioni(17l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(2l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Infrastrutture primarie (viarie, acqua, gas, ecc.)-nuova costruzione (art. 3 DPR 380/01)"))
			{
				dto.setIdTipiQualificazioni(17l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(3l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Infrastrutture primarie (viarie, acqua, gas, ecc.)-ristrutturazione edilizia (art. 3 DPR 380/01)"))
			{
				dto.setIdTipiQualificazioni(17l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(4l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Miglioramenti fondiarii") || bean.getTipoIntervento().equals("Miglioramenti fondiarii-NON DEFINITO"))
			{
				dto.setIdTipiQualificazioni(18l);
			}
			else if(bean.getTipoIntervento().trim().equals("Miglioramenti fondiarii-interventi e/o opere di grande impegno territoriale (DPCM 12/12/2005)"))
			{
				dto.setIdTipiQualificazioni(18l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(201l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Miglioramenti fondiarii-interventi e/o opere non di edilizia"))
			{
				dto.setIdTipiQualificazioni(18l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(1l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Miglioramenti fondiarii-manutenzione, restauro e risanamento conservativo (art. 3 DPR 380/01)"))
			{
				dto.setIdTipiQualificazioni(18l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(2l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Miglioramenti fondiarii-nuova costruzione (art. 3 DPR 380/01)"))
			{
				dto.setIdTipiQualificazioni(18l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(3l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Miglioramenti fondiarii-ristrutturazione edilizia (art. 3 DPR 380/01)"))
			{
				dto.setIdTipiQualificazioni(18l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(4l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Nuovi insediamenti civili e rurali") || bean.getTipoIntervento().equals("Nuovi insediamenti civili e rurali-NON DEFINITO"))
			{
				dto.setIdTipiQualificazioni(221l);
			}
			else if(bean.getTipoIntervento().trim().equals("Nuovi insediamenti civili e rurali-interventi e/o opere di grande impegno territoriale (DPCM 12/12/2005)"))
			{
				dto.setIdTipiQualificazioni(221l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(201l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Nuovi insediamenti civili e rurali-interventi e/o opere non di edilizia"))
			{
				dto.setIdTipiQualificazioni(221l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(1l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Nuovi insediamenti civili e rurali-manutenzione, restauro e risanamento conservativo (art. 3 DPR 380/01)"))
			{
				dto.setIdTipiQualificazioni(221l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(2l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Nuovi insediamenti civili e rurali-nuova costruzione (art. 3 DPR 380/01)"))
			{
				dto.setIdTipiQualificazioni(221l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(3l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Nuovi insediamenti civili e rurali-ristrutturazione edilizia (art. 3 DPR 380/01)"))
			{
				dto.setIdTipiQualificazioni(221l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(4l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Nuovi insediamenti industriali e commerciali") || bean.getTipoIntervento().equals("Nuovi insediamenti industriali e commerciali-NON DEFINITO"))
			{
				dto.setIdTipiQualificazioni(12l);
			}
			else if(bean.getTipoIntervento().trim().equals("Nuovi insediamenti industriali e commerciali-interventi e/o opere di grande impegno territoriale (DPCM 12/12/2005)"))
			{
				dto.setIdTipiQualificazioni(12l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(201l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Nuovi insediamenti industriali e commerciali-interventi e/o opere non di edilizia"))
			{
				dto.setIdTipiQualificazioni(12l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(1l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Nuovi insediamenti industriali e commerciali-manutenzione, restauro e risanamento conservativo (art. 3 DPR 380/01)"))
			{
				dto.setIdTipiQualificazioni(12l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(2l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Nuovi insediamenti industriali e commerciali-nuova costruzione (art. 3 DPR 380/01)"))
			{
				dto.setIdTipiQualificazioni(12l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(3l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Nuovi insediamenti industriali e commerciali-ristrutturazione edilizia (art. 3 DPR 380/01)"))
			{
				dto.setIdTipiQualificazioni(12l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(4l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Produzione e/o trasporto energia") || bean.getTipoIntervento().equals("Produzione e/o trasporto energia-NON DEFINITO"))
			{
				dto.setIdTipiQualificazioni(224l);
			}
			else if(bean.getTipoIntervento().trim().equals("Produzione e/o trasporto energia-interventi e/o opere di grande impegno territoriale (DPCM 12/12/2005)"))
			{
				dto.setIdTipiQualificazioni(224l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(201l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Produzione e/o trasporto energia-interventi e/o opere non di edilizia"))
			{
				dto.setIdTipiQualificazioni(224l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(1l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Produzione e/o trasporto energia-manutenzione, restauro e risanamento conservativo (art. 3 DPR 380/01)"))
			{
				dto.setIdTipiQualificazioni(224l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(2l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Produzione e/o trasporto energia-nuova costruzione (art. 3 DPR 380/01)"))
			{
				dto.setIdTipiQualificazioni(224l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(3l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Produzione e/o trasporto energia-ristrutturazione edilizia (art. 3 DPR 380/01)"))
			{
				dto.setIdTipiQualificazioni(224l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(4l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Recinzioni") || bean.getTipoIntervento().equals("Recinzioni-NON DEFINITO"))
			{
				dto.setIdTipiQualificazioni(14l);
			}
			else if(bean.getTipoIntervento().trim().equals("Recinzioni-interventi e/o opere non di edilizia"))
			{
				dto.setIdTipiQualificazioni(14l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(1l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Recinzioni-manutenzione, restauro e risanamento conservativo (art. 3 DPR 380/01)"))
			{
				dto.setIdTipiQualificazioni(14l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(2l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Recinzioni-nuova costruzione (art. 3 DPR 380/01)"))
			{
				dto.setIdTipiQualificazioni(14l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(3l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Recinzioni-ristrutturazione edilizia (art. 3 DPR 380/01)"))
			{
				dto.setIdTipiQualificazioni(14l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(4l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Ristrutturazione e ampliamenti insediamenti civili e rurali") || bean.getTipoIntervento().equals("Ristrutturazione e ampliamenti insediamenti civili e rurali-NON DEFINITO"))
			{
				dto.setIdTipiQualificazioni(222l);
			}
			else if(bean.getTipoIntervento().trim().equals("Ristrutturazione e ampliamenti insediamenti civili e rurali-interventi e/o opere di grande impegno territoriale (DPCM 12/12/2005)"))
			{
				dto.setIdTipiQualificazioni(222l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(201l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Ristrutturazione e ampliamenti insediamenti civili e rurali-interventi e/o opere non di edilizia"))
			{
				dto.setIdTipiQualificazioni(222l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(1l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Ristrutturazione e ampliamenti insediamenti civili e rurali-manutenzione, restauro e risanamento conservativo (art. 3 DPR 380/01)"))
			{
				dto.setIdTipiQualificazioni(222l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(2l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Ristrutturazione e ampliamenti insediamenti civili e rurali-nuova costruzione (art. 3 DPR 380/01)"))
			{
				dto.setIdTipiQualificazioni(222l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(3l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Ristrutturazione e ampliamenti insediamenti civili e rurali-ristrutturazione edilizia (art. 3 DPR 380/01)"))
			{
				dto.setIdTipiQualificazioni(222l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(4l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Ristrutturazione e ampliamenti insediamenti industriali e commerciali") || bean.getTipoIntervento().equals("Ristrutturazione e ampliamenti insediamenti industriali e commerciali-NON DEFINITO"))
			{
				dto.setIdTipiQualificazioni(223l);
			}
			else if(bean.getTipoIntervento().trim().equals("Ristrutturazione e ampliamenti insediamenti industriali e commerciali-interventi e/o opere di grande impegno territoriale (DPCM 12/12/2005)"))
			{
				dto.setIdTipiQualificazioni(223l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(201l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Ristrutturazione e ampliamenti insediamenti industriali e commerciali-interventi e/o opere non di edilizia"))
			{
				dto.setIdTipiQualificazioni(223l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(1l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Ristrutturazione e ampliamenti insediamenti industriali e commerciali-manutenzione, restauro e risanamento conservativo (art. 3 DPR 380/01)"))
			{
				dto.setIdTipiQualificazioni(223l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(2l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Ristrutturazione e ampliamenti insediamenti industriali e commerciali-nuova costruzione (art. 3 DPR 380/01)"))
			{
				dto.setIdTipiQualificazioni(223l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(3l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("Ristrutturazione e ampliamenti insediamenti industriali e commerciali-ristrutturazione edilizia (art. 3 DPR 380/01)"))
			{
				dto.setIdTipiQualificazioni(223l);
				FascicoloInterventoDTO dto2 = new FascicoloInterventoDTO();
				dto2.setIdFascicolo(idFascicolo);
				dto2.setIdTipiQualificazioni(4l);
				result.add(dto2);
			}
			else if(bean.getTipoIntervento().trim().equals("interventi e/o opere non di edilizia"))
			{
				dto.setIdTipiQualificazioni(1l);
			}
			else if(bean.getTipoIntervento().trim().equals("manutenzione, restauro e risanamento conservativo (art. 3 DPR 380/01)"))
			{
				dto.setIdTipiQualificazioni(2l);
			}
			else if(bean.getTipoIntervento().trim().equals("nuova costruzione (art. 3 DPR 380/01)"))
			{
				dto.setIdTipiQualificazioni(3l);
			}
			else
			{
				dto.setIdTipiQualificazioni(19l);
				if(!bean.getTipoIntervento().equals("Altro"))
					fascicolo.setInterventoSpecifica(bean.getTipoIntervento());
			}
			result.add(dto);
		}
		FascicoloInterventoDTO dto = new FascicoloInterventoDTO();
		dto.setIdFascicolo(idFascicolo);
		if(bean.getTipologia().equals("Parere ai sensi dell art 32 della L.47/85 per beni tutelati ai sensi delle NTA del PUTT/P (condono edilizio L.47/85, L.724/94, L.326/03);"))
			dto.setIdTipiQualificazioni(211l);
		else if(bean.getTipologia().equals("Autorizzazione paesaggistica ordinaria ex art. 5.01 NTA del PUTT/P"))
			dto.setIdTipiQualificazioni(212l);
		else if(bean.getTipologia().equals("Autorizzazione paesaggistica in sanatoria ex art. 5.01 NTA del PUTT/P"))
			dto.setIdTipiQualificazioni(213l);
		else if(bean.getTipologia().equals("Autorizzazione paesaggistica semplificata ex art. 5.01 NTA del PUTT/P"))
			dto.setIdTipiQualificazioni(214l);
		else if(bean.getTipologia().equals("Parere ai sensi dell art 32 della L.47/85 per beni tutelati ai sensi degli art 136 e 142 D.Lgs.  42/04 (condono edilizio L.47/85, L.724/94, L 326/03)"))
			dto.setIdTipiQualificazioni(215l);
		else if(bean.getTipologia().equals("Accertamento di compatibilità paesaggistica ex art. 167 D.Lgs. 42/04 (sanatoria ordinaria)"))
			dto.setIdTipiQualificazioni(216l);
		else if(bean.getTipologia().equals("Accertamento di compatibilit� paesaggistica ex art. 167 D.Lgs. 42/04 (sanatoria ordinaria)"))
			dto.setIdTipiQualificazioni(216l);
		else if(bean.getTipologia().equals("Autorizzazione paesaggistica ordinaria ex art.146 D.Lgs. 42/04"))
			dto.setIdTipiQualificazioni(217l);
		else if(bean.getTipologia().equals("Autorizzazione paesaggistica semplificata ex art. 146 c.9 D.Lgs. 42/04 e D.P.R. 139/10"))
			dto.setIdTipiQualificazioni(218l);
		else if(bean.getTipologia().equals("Autorizzazione Paesaggistica ai sensi dell'art. 5.01 delle Nta del P.U.T.T./p"))
			dto.setIdTipiQualificazioni(228l);
		else if(bean.getTipologia().equals("Autorizzazione Paesaggistica ai sensi dell'art. 146"))
			dto.setIdTipiQualificazioni(229l);
		else if(bean.getTipologia().equals("Autorizzazione Paesaggistica semplificata ai sensi dell'art. 146"))
			dto.setIdTipiQualificazioni(230l);
		result.add(dto);
		return result;
	}
	
	/**
	 * 
	 * @param bean
	 * @param comuneId
	 * @return
	 */
	public static List<ParticelleCatastaliDTO> buildParticelleCatastaliPutt(List<LocalizzazionePuttBean> bean, long comuneId)
	{
		List<ParticelleCatastaliDTO> result = new ArrayList<>();
		int counter=1;
		for(LocalizzazionePuttBean b: bean)
		{
			ParticelleCatastaliDTO dto = new ParticelleCatastaliDTO();
			dto.setId(counter++);
			dto.setComuneId(comuneId);
			dto.setCodCat(b.getCodCat());
			dto.setFoglio(b.getFoglio());
			dto.setLivello(b.getLivello());
			dto.setParticella(b.getParticella());
			dto.setSezione(b.getSezione());
			dto.setSub(b.getSub());
			result.add(dto);
		}
		return result;
	}
	
	/**
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static AllegatiWithMultipart buildAllegatoPutt(PuttDocBean bean) throws Exception
	{
		return buildAllegatoPutt(bean, false);
	}
	
	public static AllegatiWithMultipart buildAllegatoPutt(PuttDocBean bean,boolean checkOnly) throws Exception
	{
		MultipartFile file=null;
		if(!checkOnly) {
			file = new MockMultipartFile(bean.getnName(), bean.getAbinContent().getBinaryStream());
		} 
		AllegatiWithMultipart result = new AllegatiWithMultipart(file);
		result.setNome(bean.getnName());
		result.setTitolo(bean.getnName());
		result.setTipo(TipoAllegato.fromCiviliaPuttCodiceTipoAllegato(bean.gettTipodocCodice()));
		result.setMimeType(bean.gettKeDocAttrValue());
		result.setUsernameInserimento("MIGRAZIONE");
		if(!checkOnly) {
			result.setDimensione((int)file.getSize());	
		}
		result.setIdCivilia(bean.getnTKeDocStID());
		result.setDescrizione(bean.gettTipodocDescrizione());
		result.setDataCaricamento(new java.util.Date()); 
		//TODO: data protocollo 
		return result;
	}
	
	private static EsitoProvvedimento toEsito(String e)
	{
		if(e == null) return null;
		switch(e.toLowerCase())
		{
		case "autorizzato":
			return EsitoProvvedimento.AUTORIZZATO;
		case "non autorizzato":
			return EsitoProvvedimento.NON_AUTORIZZATO;
		case "autorizzato con prescrizioni":
			return EsitoProvvedimento.AUT_CON_PRESCRIZ;
		default: return null;
		}
	}
	
	private static TipoProcedimento toPuttTipoProc(String s) throws Exception
	{
		switch(s)
		{
		case "Parere ai sensi dell art 32 della L.47/85 per beni tutelati ai sensi delle NTA del PUTT/P (condono edilizio L.47/85, L.724/94, L.326/03);":
		case "Autorizzazione paesaggistica ordinaria ex art. 5.01 NTA del PUTT/P":
		case "Autorizzazione paesaggistica semplificata ex art. 5.01 NTA del PUTT/P":
		case "Autorizzazione paesaggistica in sanatoria ex art. 5.01 NTA del PUTT/P":
		case "Autorizzazione Paesaggistica ai sensi dell'art. 5.01 delle Nta del P.U.T.T./p":
			return TipoProcedimento.PUTT_X;
		case "Autorizzazione paesaggistica ordinaria ex art.146 D.Lgs. 42/04":
		case "Accertamento di compatibilità paesaggistica ex art. 167 D.Lgs. 42/04 (sanatoria ordinaria)":
		case "Accertamento di compatibilit� paesaggistica ex art. 167 D.Lgs. 42/04 (sanatoria ordinaria)":
		case "Parere ai sensi dell art 32 della L.47/85 per beni tutelati ai sensi degli art 136 e 142 D.Lgs.  42/04 (condono edilizio L.47/85, L.724/94, L 326/03)":
		case "Autorizzazione Paesaggistica ai sensi dell'art. 146":
		case "Autorizzazione Paesaggistica semplificata ai sensi dell'art. 146":
		case "Autorizzazione paesaggistica semplificata ex art. 146 c.9 D.Lgs. 42/04 e D.P.R. 139/10":
			return TipoProcedimento.PUTT_DLGS_42_2004;
		default: 
			log.error(IMigratorService.LOG_MIGRAZIONE_MARKER,"Errore, nessuna tipologia per settata per il codice \"{}\"", s);
			throw new Exception("Errore, nessuna tipologia per settata per il codice \"" + s + "\"");
		}
	}
	
	private static Boolean toSanatoria(String sanatoria)
	{
		switch(sanatoria)
		{
		case "vero":  return true;
		case "falso": return false;
		default: return null;
		}
	}
	
//	private String toddmmyyyy(String date, String sep)
//	{
//		String[] dpart = StringUtils.split(date, sep);
//		String d = dpart[0];
//		String y = dpart[2];
//		String m = "";
//		switch(dpart[1].toUpperCase())
//		{
//		case "GEN": return 1;
//		case "FEB"
//		}
//	}
}
