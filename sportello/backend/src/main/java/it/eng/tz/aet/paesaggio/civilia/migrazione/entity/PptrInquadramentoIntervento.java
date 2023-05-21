package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;

import java.io.Serializable;
import java.util.EnumMap;

/**

CREATE TABLE TNO_PPTR_INQUADRAM_INTERV
(
  INQUADRAM_INTERV_ID        NUMBER,
  T_PRATICA_APPTR_ID         NUMBER,
  CODICE_PRATICA_APPPTR      VARCHAR2(255 BYTE),
  INQ_OPERA_CORRELATA_A      VARCHAR2(200 BYTE),
  INQ_CARATTERE_INTERV       VARCHAR2(200 BYTE),
  INQ_DEST_USO_INTERV        VARCHAR2(200 BYTE),
  INQ_DEST_USO_INTERV_ALTRO  VARCHAR2(400 BYTE),
  INQ_USO_SUOLO              VARCHAR2(200 BYTE),
  INQ_USO_SUOLO_ALTRO        VARCHAR2(400 BYTE),
  INQ_CONTESTO_PAESAG        VARCHAR2(200 BYTE),
  INQ_MORFOLOGIA_PAESAG      VARCHAR2(200 BYTE)
)

)*/

//@Entity
//@Table(name="TNO_PPTR_INQUADRAM_INTERV")
public class PptrInquadramentoIntervento  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
//	@Id
//	//@Column( name="INQUADRAM_INTERV_ID" )
	private long id;

	//@Column( name="CODICE_PRATICA_APPPTR" )
	private String codicePraticaAppptr;

	//@Column( name="INQ_CARATTERE_INTERV" )
	private String inqCarattereInterv;

	//@Column( name="INQ_CONTESTO_PAESAG" )
	private String inqContestoPaesag;

	//@Column( name="INQ_DEST_USO_INTERV" )
	private String inqDestUsoInterv;

	//@Column( name="INQ_DEST_USO_INTERV_ALTRO" )
	private String inqDestUsoIntervAltro;

	//@Column( name="INQ_MORFOLOGIA_PAESAG" )
	private String inqMorfologiaPaesag;

	//@Column( name="INQ_OPERA_CORRELATA_A" )
	private String inqOperaCorrelataA;

	//@Column( name="INQ_USO_SUOLO" )
	private String inqUsoSuolo;

	//@Column( name="INQ_USO_SUOLO_ALTRO" )
	private String inqUsoSuoloAltro;

	//@Column( name="T_PRATICA_APPTR_ID" )
	private long tPraticaApptrId;
	
	//@Column( name="PROG" )
	private long prog;

	//@Column( name="INQ_CONTESTO_PAESAG_ALTRO" )
	private String inqContestoPaesagAltro;
	
	//@Column( name="INQ_MORFOLOGIA_PAESAG_ALTRO" )
	private String inqMorfologiaPaesagAltro;
	
	public long getProg()
	{
		return prog;
	}

	public void setProg( long prog )
	{
		this.prog = prog;
	}


	public enum enum_inqOperaCorrelataA
	{
		edificio, 
		area_di_pertinenza_o_intorno,
		lotto_di_terreno,
		strade_corsi_d_acqua,
		territorio_aperto
	}
	
	public enum enum_inqCarattereInterv
	{
		temporaneo_o_stagionale,
		permanente_fisso,
		permanente_rimovibile
	}
	
	public enum enum_inqDestUsoInterv
	{
		residenziale,
		ricettiva_turistica,
		industriale_artigianale,
		agricolo,
		commerciale_direzionale,
		altro
	}
	
	public enum enum_inqUsoSuolo
	{
		urbano,
		agricolo,
		boscato,
		naturale_non_coltivato,
		altro
	}
	
	public enum enum_inqContestoPaesag
	{
		centro_storico,
		area_urbana,
		area_periurbana,
		territorio_agricolo,
		insediamento_sparso,
		insediamento_agricolo,
		area_naturale,
		//Aggiunte per il procedimento 31
		area_agricola,
		insediamento_rurale,
		area_boschiva,
		ambito_fluviale,
		ambito_lacustre,
		altro
	}
	
	public enum enum_inqMorfologiaPaesag
	{
		costa_bassa_alta,
		lacustre_vallivo,
		pianura,
		versante_collinare_montano,
		altopiano,
		promontorio,
		piana_valliva_montana_collinare,
		terrazzamento_crinale,
		//Aggiunte per il procedimento 31
        altopiano_promontorio,
		crinale,
		versante,
		altro
	}
	//@Transient
	private EnumMap<enum_inqOperaCorrelataA , String> mapvalues_inqOperaCorrelataA = new EnumMap<PptrInquadramentoIntervento.enum_inqOperaCorrelataA , String>(enum_inqOperaCorrelataA.class);
	//@Transient
	private EnumMap<enum_inqMorfologiaPaesag , String> mapvalues_inqMorfologiaPaesag = new EnumMap<PptrInquadramentoIntervento.enum_inqMorfologiaPaesag , String>(enum_inqMorfologiaPaesag.class);
	//@Transient
	private EnumMap<enum_inqContestoPaesag , String> mapvalues_inqContestoPaesag = new EnumMap<PptrInquadramentoIntervento.enum_inqContestoPaesag , String>(enum_inqContestoPaesag.class);
	//@Transient
	private EnumMap<enum_inqUsoSuolo , String> mapvalues_inqUsoSuolo = new EnumMap<PptrInquadramentoIntervento.enum_inqUsoSuolo , String>(enum_inqUsoSuolo.class);
	//@Transient
	private EnumMap<enum_inqCarattereInterv , String> mapvalues_inqCarattereInterv = new EnumMap<PptrInquadramentoIntervento.enum_inqCarattereInterv , String>(enum_inqCarattereInterv.class);
	//@Transient
	private EnumMap<enum_inqDestUsoInterv , String> mapvalues_inqDestUsoInterv = new EnumMap<PptrInquadramentoIntervento.enum_inqDestUsoInterv , String>(enum_inqDestUsoInterv.class);
	
	private void setMapStringValues_inqOperaCorrelataA()
	{
		mapvalues_inqOperaCorrelataA.put( enum_inqOperaCorrelataA.edificio , "edificio" );
		mapvalues_inqOperaCorrelataA.put( enum_inqOperaCorrelataA.area_di_pertinenza_o_intorno , "area di pertinenza o intorno dell\'edifio" );
		mapvalues_inqOperaCorrelataA.put( enum_inqOperaCorrelataA.lotto_di_terreno , "lotto di terreno" );
		mapvalues_inqOperaCorrelataA.put( enum_inqOperaCorrelataA.strade_corsi_d_acqua , "strade , corsi d\'acqua" );
		mapvalues_inqOperaCorrelataA.put( enum_inqOperaCorrelataA.territorio_aperto , "territorio aperto" );
	}
	
	private void setMapStringValues_inqMorfologiaPaesag()
	{
		mapvalues_inqMorfologiaPaesag.put(enum_inqMorfologiaPaesag.costa_bassa_alta, "costa (bassa/alta)" );
		mapvalues_inqMorfologiaPaesag.put(enum_inqMorfologiaPaesag.lacustre_vallivo, "lacustre/vallivo" );
		mapvalues_inqMorfologiaPaesag.put(enum_inqMorfologiaPaesag.pianura, "pianura" );
		mapvalues_inqMorfologiaPaesag.put(enum_inqMorfologiaPaesag.versante_collinare_montano, "versante (collinare/montano)" );
		mapvalues_inqMorfologiaPaesag.put(enum_inqMorfologiaPaesag.altopiano, "altopiano" );
		mapvalues_inqMorfologiaPaesag.put(enum_inqMorfologiaPaesag.promontorio, "promontorio" );
		mapvalues_inqMorfologiaPaesag.put(enum_inqMorfologiaPaesag.piana_valliva_montana_collinare, "piana valliva (montana/collinare)" );
		mapvalues_inqMorfologiaPaesag.put(enum_inqMorfologiaPaesag.terrazzamento_crinale, "terrazzamento crinale" );
		//Aggiunte per il procedimento 31
		mapvalues_inqMorfologiaPaesag.put(enum_inqMorfologiaPaesag.altopiano_promontorio, "altopiano/promontorio)" );
		mapvalues_inqMorfologiaPaesag.put(enum_inqMorfologiaPaesag.crinale, "crinale (collinare/montano)" );
		mapvalues_inqMorfologiaPaesag.put(enum_inqMorfologiaPaesag.versante, "versante" );
		mapvalues_inqMorfologiaPaesag.put(enum_inqMorfologiaPaesag.altro, "altro" );
	}
	
	private void setMapStringValues_mapvalues_inqContestoPaesag()
	{
		mapvalues_inqContestoPaesag.put(enum_inqContestoPaesag.centro_storico, "centro storico" );
		mapvalues_inqContestoPaesag.put(enum_inqContestoPaesag.area_urbana, "area urbana" );
		mapvalues_inqContestoPaesag.put(enum_inqContestoPaesag.area_periurbana, "area periurbana" );
		mapvalues_inqContestoPaesag.put(enum_inqContestoPaesag.territorio_agricolo, "territorio agricolo" );
		mapvalues_inqContestoPaesag.put(enum_inqContestoPaesag.insediamento_sparso, "insediamento sparso" );
		mapvalues_inqContestoPaesag.put(enum_inqContestoPaesag.insediamento_agricolo, "insediamento agricolo" );
		mapvalues_inqContestoPaesag.put(enum_inqContestoPaesag.area_naturale, "area naturale" );
		//Aggiunti per il procedimento 31
		mapvalues_inqContestoPaesag.put(enum_inqContestoPaesag.area_agricola, "area agricola" );
		mapvalues_inqContestoPaesag.put(enum_inqContestoPaesag.insediamento_rurale, "insediamento rurale" );
		mapvalues_inqContestoPaesag.put(enum_inqContestoPaesag.area_boschiva, "area boschiva" );
		mapvalues_inqContestoPaesag.put(enum_inqContestoPaesag.ambito_fluviale, "ambito fluviale" );
		mapvalues_inqContestoPaesag.put(enum_inqContestoPaesag.ambito_lacustre, "ambito lacustre" );
		mapvalues_inqContestoPaesag.put(enum_inqContestoPaesag.altro, "altro" );
	}
	
	private void setMapStringValues_mapvalues_inqUsoSuolo()
	{
		mapvalues_inqUsoSuolo.put(enum_inqUsoSuolo.urbano, "urbano" );
		mapvalues_inqUsoSuolo.put(enum_inqUsoSuolo.agricolo, "agricolo" );
		mapvalues_inqUsoSuolo.put(enum_inqUsoSuolo.boscato, "boscato" );
		mapvalues_inqUsoSuolo.put(enum_inqUsoSuolo.naturale_non_coltivato, "naturale non coltivato" );
		mapvalues_inqUsoSuolo.put(enum_inqUsoSuolo.altro, "altro" );
	}
	
	private void setMapStringValues_mapvalues_inqCarattereInterv()
	{
		mapvalues_inqCarattereInterv.put(enum_inqCarattereInterv.temporaneo_o_stagionale, "temporaneo o stagionale" );
		mapvalues_inqCarattereInterv.put(enum_inqCarattereInterv.permanente_fisso, "permanente / fisso" );
		mapvalues_inqCarattereInterv.put(enum_inqCarattereInterv.permanente_rimovibile, "permanente / rimovibile" );
	}
	
	private void setMapStringValues_mapvalues_inqDestUsoInterv()
	{
		mapvalues_inqDestUsoInterv.put(enum_inqDestUsoInterv.residenziale, "residenziale" );
		mapvalues_inqDestUsoInterv.put(enum_inqDestUsoInterv.ricettiva_turistica, "ricettiva/turistica" );
		mapvalues_inqDestUsoInterv.put(enum_inqDestUsoInterv.industriale_artigianale, "industriale/artigianale" );
		mapvalues_inqDestUsoInterv.put(enum_inqDestUsoInterv.agricolo, "agricolo" );
		mapvalues_inqDestUsoInterv.put(enum_inqDestUsoInterv.commerciale_direzionale, "commerciale direzionale" );
		mapvalues_inqDestUsoInterv.put(enum_inqDestUsoInterv.altro, "altro" );
	}
	
	public PptrInquadramentoIntervento(  ) 
	{
		this.setMapStringValues_inqOperaCorrelataA();
		this.setMapStringValues_inqMorfologiaPaesag();
		this.setMapStringValues_mapvalues_inqContestoPaesag();
		this.setMapStringValues_mapvalues_inqUsoSuolo();
		this.setMapStringValues_mapvalues_inqCarattereInterv();
		this.setMapStringValues_mapvalues_inqDestUsoInterv();
	}

	public long getId(  ) 
	{
		return this.id;
	}

	public void setId( long id ) 
	{
		this.id = id;
	}

	public String getCodicePraticaAppptr(  ) 
	{
		
		return this.codicePraticaAppptr;
	}

	public void setCodicePraticaAppptr( String codicePraticaAppptr ) 
	{
		this.codicePraticaAppptr = codicePraticaAppptr;
	}

	public String getInqCarattereInterv(  ) 
	{
		return this.inqCarattereInterv;
	}

	public void setInqCarattereInterv( String inqCarattereInterv ) 
	{
		this.inqCarattereInterv =inqCarattereInterv;
	}

	public String getInqContestoPaesag(  ) 
	{
		return this.inqContestoPaesag;
	}

	public void setInqContestoPaesag( String inqContestoPaesag ) 
	{
		this.inqContestoPaesag = inqContestoPaesag;
	}

	public String getInqDestUsoInterv(  ) 
	{
		return this.inqDestUsoInterv;
	}

	public void setInqDestUsoInterv( String inqDestUsoInterv ) 
	{
		this.inqDestUsoInterv = inqDestUsoInterv;
	}

	public String getInqDestUsoIntervAltro(  ) 
	{
		return this.inqDestUsoIntervAltro;
	}

	public void setInqDestUsoIntervAltro( String inqDestUsoIntervAltro ) 
	{
		this.inqDestUsoIntervAltro = inqDestUsoIntervAltro;
	}

	public String getInqMorfologiaPaesag(  ) 
	{
		return this.inqMorfologiaPaesag;
	}

	public void setInqMorfologiaPaesag( String inqMorfologiaPaesag ) 
	{
		this.inqMorfologiaPaesag = inqMorfologiaPaesag;
	}

	public String getInqOperaCorrelataA(  ) 
	{
		return this.inqOperaCorrelataA;
	}

	public void setInqOperaCorrelataA( String inqOperaCorrelataA ) 
	{
		this.inqOperaCorrelataA = inqOperaCorrelataA;
	}

	public String getInqUsoSuolo(  ) 
	{
		return this.inqUsoSuolo;
	}

	public void setInqUsoSuolo( String inqUsoSuolo ) 
	{
		this.inqUsoSuolo = inqUsoSuolo;
	}

	public String getInqUsoSuoloAltro(  ) 
	{
		return this.inqUsoSuoloAltro;
	}

	public void setInqUsoSuoloAltro( String inqUsoSuoloAltro ) 
	{
		this.inqUsoSuoloAltro = inqUsoSuoloAltro;
	}

	public String getInqContestoPaesagAltro(  ) 
	{
		return this.inqContestoPaesagAltro;
	}

	public void setInqContestoPaesagAltro( String inqContestoPaesagAltro ) 
	{
		this.inqContestoPaesagAltro = inqContestoPaesagAltro;
	}
	
	public String getInqMorfologiaPaesagAltro(  ) 
	{
		return this.inqMorfologiaPaesagAltro;
	}

	public void setInqMorfologiaPaesagAltro( String inqMorfologiaPaesagAltro ) 
	{
		this.inqMorfologiaPaesagAltro = inqMorfologiaPaesagAltro;
	}
	
	public long getTPraticaApptrId(  ) 
	{
		return this.tPraticaApptrId;
	}

	public void setTPraticaApptrId( long tPraticaApptrId ) 
	{
		this.tPraticaApptrId = tPraticaApptrId;
	}
	
}