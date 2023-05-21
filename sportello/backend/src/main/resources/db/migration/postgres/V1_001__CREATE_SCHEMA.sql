--
-- PostgreSQL database dump
--

-- Dumped from database version 10.10
-- Dumped by pg_dump version 10.10

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: presentazione_istanza; Type: SCHEMA; Schema: -; Owner: presentazione_istanza
--
CREATE SCHEMA IF NOT EXISTS presentazione_istanza;

ALTER SCHEMA presentazione_istanza OWNER TO presentazione_istanza;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: allegati; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.allegati (
    id uuid NOT NULL,
    nome_file character varying(255) NOT NULL,
    formato_file character varying(100),
    data_caricamento date,
    path_cms character varying(400),
    id_cms character varying(100),
    pratica_id uuid NOT NULL
);


ALTER TABLE presentazione_istanza.allegati OWNER TO presentazione_istanza;

--
-- Name: TABLE allegati; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON TABLE presentazione_istanza.allegati IS 'allegati alla pratica con classificazione';


--
-- Name: allegati_tipo_contenuto; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.allegati_tipo_contenuto (
    allegati_id uuid NOT NULL,
    tipo_contenuto_id character varying(100) NOT NULL
);


ALTER TABLE presentazione_istanza.allegati_tipo_contenuto OWNER TO presentazione_istanza;

--
-- Name: TABLE allegati_tipo_contenuto; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON TABLE presentazione_istanza.allegati_tipo_contenuto IS 'relazione n-m tra allegati e tipo_contenuto';


--
-- Name: altre_dichiarazioni_rich; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.altre_dichiarazioni_rich (
    pratica_id uuid NOT NULL,
    check_136 character varying(1),
    check_142 character varying(1),
    check_134 character varying(1),
    check_136a character varying(1),
    check_136b character varying(1),
    check_136c character varying(1),
    check_136d character varying(1),
    ente_rilascio character varying(255),
    descr_aut_rilasciata character varying(400),
    data_rilascio date,
    is_caso_variante boolean DEFAULT false,
    check_142_parchi character varying(1)
);


ALTER TABLE presentazione_istanza.altre_dichiarazioni_rich OWNER TO presentazione_istanza;

--
-- Name: TABLE altre_dichiarazioni_rich; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON TABLE presentazione_istanza.altre_dichiarazioni_rich IS 'corrisponde alla sezione aggiuntiva che compare nel pannello richiedente se tipo procedimento = AUTORIZZAZIONE SEMPLIFICATA';


--
-- Name: COLUMN altre_dichiarazioni_rich.check_142_parchi; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON COLUMN presentazione_istanza.altre_dichiarazioni_rich.check_142_parchi IS 'S se e selezionato il check PARCHI dell''articolo 142, N se non lo e. Serve per facilitare la verifica incrociata con la localizzazione, in fase di validazione (preso da CIVILIA_CS)';


--
-- Name: assogg_procedure_edilizie; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.assogg_procedure_edilizie (
    pratica_id uuid NOT NULL,
    flag_assoggettato character varying(1) DEFAULT 'N'::character varying NOT NULL,
    no_assogg_specificare character varying(400),
    assogg_flag_pratica_in_corso character varying(1),
    assogg_ente_pratica_in_corso character varying(400),
    assogg_datapr_pratica_in_corso date,
    assogg_flag_parere_urb_espr character varying(1),
    assogg_data_approv_pratica date
);


ALTER TABLE presentazione_istanza.assogg_procedure_edilizie OWNER TO presentazione_istanza;

--
-- Name: TABLE assogg_procedure_edilizie; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON TABLE presentazione_istanza.assogg_procedure_edilizie IS 'pannello scheda-tecnica-> procedure edilizie';


--
-- Name: caratterizzazione_intervento; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.caratterizzazione_intervento (
    pratica_id uuid NOT NULL,
    altro_specifica character varying(4000),
    rimessa_in_ripristino_specifica character varying(4000),
    temp_perm character varying(1)
);


ALTER TABLE presentazione_istanza.caratterizzazione_intervento OWNER TO presentazione_istanza;

--
-- Name: COLUMN caratterizzazione_intervento.temp_perm; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON COLUMN presentazione_istanza.caratterizzazione_intervento.temp_perm IS 'QUESTO FLAG DEVE ESSERE VALORIZZATO A "T" PER INTERVENTI A CARATTERE TEMPORANEO/STAGIONALE, A "F" PER INTERVENTI A CARATTERE PERMANENTE DI TIPO FISSO OPPURE "R" PER INTERVENTI A CARATTERE PERMANENTE DI TIPO RIMOVIBILE (vecchia codifica)';


--
-- Name: caratterizzazione_intervento_values; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.caratterizzazione_intervento_values (
    pratica_id uuid NOT NULL,
    codice character varying(50) NOT NULL
);


ALTER TABLE presentazione_istanza.caratterizzazione_intervento_values OWNER TO presentazione_istanza;

--
-- Name: TABLE caratterizzazione_intervento_values; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON TABLE presentazione_istanza.caratterizzazione_intervento_values IS 'selezione checkbox effettuata';


--
-- Name: comuni_competenza; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.comuni_competenza (
    pratica_id uuid NOT NULL,
    ente_id integer NOT NULL,
    data_inserimento timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE presentazione_istanza.comuni_competenza OWNER TO presentazione_istanza;

--
-- Name: TABLE comuni_competenza; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON TABLE presentazione_istanza.comuni_competenza IS 'qui si salva i comuni di competenza dell''ente delegato ricevente della pratica';


--
-- Name: COLUMN comuni_competenza.ente_id; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON COLUMN presentazione_istanza.comuni_competenza.ente_id IS 'riferimento nella tabella common.ente';


--
-- Name: destinazione_urbanistica_intervento; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.destinazione_urbanistica_intervento (
    id bigint NOT NULL,
    localizzazione_intervento_id bigint NOT NULL,
    strum_urb_approvato character varying(3),
    strum_urb_approvato_data date,
    strum_urb_approvato_atto character varying(4000),
    destin_area_str_vig character varying(4000),
    strum_approv_ult_tutele character varying(4000),
    strum_urb_adottato character varying(3),
    strum_urb_adottato_data date,
    strum_urb_adottato_atto character varying(4000),
    destin_area_str_adott character varying(4000),
    strum_adott_ult_tutele character varying(4000),
    conforme_discipl_urb_vigente character varying(1),
    check_presa_visione character varying(1),
    id_strum_urban_art38 bigint,
    id_strum_urban_art100 bigint
);


ALTER TABLE presentazione_istanza.destinazione_urbanistica_intervento OWNER TO presentazione_istanza;

--
-- Name: TABLE destinazione_urbanistica_intervento; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON TABLE presentazione_istanza.destinazione_urbanistica_intervento IS 'simile TNO_PPTR_DEST_URB_INTERV di CIVILIA_CS un record per ogni record di localizzazione_intervento
';


--
-- Name: COLUMN destinazione_urbanistica_intervento.strum_urb_approvato; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON COLUMN presentazione_istanza.destinazione_urbanistica_intervento.strum_urb_approvato IS 'PRG PUG Pdf';


--
-- Name: COLUMN destinazione_urbanistica_intervento.strum_urb_adottato; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON COLUMN presentazione_istanza.destinazione_urbanistica_intervento.strum_urb_adottato IS 'PUG VAR NO
';


--
-- Name: COLUMN destinazione_urbanistica_intervento.check_presa_visione; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON COLUMN presentazione_istanza.destinazione_urbanistica_intervento.check_presa_visione IS 'Si No';


--
-- Name: destinazione_urbanistica_intervento_id_seq; Type: SEQUENCE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE SEQUENCE presentazione_istanza.destinazione_urbanistica_intervento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE presentazione_istanza.destinazione_urbanistica_intervento_id_seq OWNER TO presentazione_istanza;

--
-- Name: destinazione_urbanistica_intervento_id_seq; Type: SEQUENCE OWNED BY; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER SEQUENCE presentazione_istanza.destinazione_urbanistica_intervento_id_seq OWNED BY presentazione_istanza.destinazione_urbanistica_intervento.id;


--
-- Name: disclaimer; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.disclaimer (
    id integer NOT NULL,
    testo character varying(4000) NOT NULL,
    tipo_procedimento integer NOT NULL,
    tipo_referente character varying(2)
);


ALTER TABLE presentazione_istanza.disclaimer OWNER TO presentazione_istanza;

--
-- Name: TABLE disclaimer; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON TABLE presentazione_istanza.disclaimer IS 'dichiarazioni da accettare in compilazione istanza';


--
-- Name: COLUMN disclaimer.tipo_referente; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON COLUMN presentazione_istanza.disclaimer.tipo_referente IS 'come tipo_referente in referenti SD TR ...';


--
-- Name: disclaimer_pratica; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.disclaimer_pratica (
    id bigint NOT NULL,
    disclaimer_id integer NOT NULL,
    flag character varying(1),
    pratica_id uuid NOT NULL
);


ALTER TABLE presentazione_istanza.disclaimer_pratica OWNER TO presentazione_istanza;

--
-- Name: disclaimer_pratica_id_seq; Type: SEQUENCE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE SEQUENCE presentazione_istanza.disclaimer_pratica_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE presentazione_istanza.disclaimer_pratica_id_seq OWNER TO presentazione_istanza;

--
-- Name: disclaimer_pratica_id_seq; Type: SEQUENCE OWNED BY; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER SEQUENCE presentazione_istanza.disclaimer_pratica_id_seq OWNED BY presentazione_istanza.disclaimer_pratica.id;


--
-- Name: effetti_mitigazione; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.effetti_mitigazione (
    pratica_id uuid NOT NULL,
    descr_stato_attuale character varying(4000),
    effetti_realiz_opera character varying(4000),
    mitigazione_imp_interv character varying(4000),
    indicaz_contenuti_percettivi character varying(4000)
);


ALTER TABLE presentazione_istanza.effetti_mitigazione OWNER TO presentazione_istanza;

--
-- Name: TABLE effetti_mitigazione; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON TABLE presentazione_istanza.effetti_mitigazione IS 'tabella CIVILIA_CS TNO_PPTR_STATO_EFF_MITIGAZ , riferita al pannello opzionale EFFETTI CONS. MITIGAZIONE';



--
-- Name: inquadramento; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.inquadramento (
    pratica_id uuid NOT NULL,
    inq_dest_uso_interv character varying(50),
    inq_dest_uso_interv_altro character varying(400),
    inq_dest_uso_suolo character varying(50),
    inq_dest_uso_suolo_altro character varying(400),
    inq_contesto_paesag character varying(50),
    inq_contesto_paesag_altro character varying(400),
    inq_morfologia_paesag character varying(50),
    inq_morfologia_paesag_altro character varying(400)
);


ALTER TABLE presentazione_istanza.inquadramento OWNER TO presentazione_istanza;

--
-- Name: TABLE inquadramento; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON TABLE presentazione_istanza.inquadramento IS 'pannello opzionale scheda tecnica->inquadramento';


--
-- Name: leggittimita; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.leggittimita (
    pratica_id uuid NOT NULL,
    leg_urb_tit_edilizio character varying(2),
    leg_urb_privo_specifica character varying(4000),
    leg_paesag_immobile character varying(2),
    leg_pae_tipo_vincolo character varying(400),
    leg_pae_data_intervento date,
    leg_pae_data_vincolo date
);


ALTER TABLE presentazione_istanza.leggittimita OWNER TO presentazione_istanza;

--
-- Name: TABLE leggittimita; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON TABLE presentazione_istanza.leggittimita IS 'pannello leggittimità urbanistica in scheda tecnica';


--
-- Name: COLUMN leggittimita.leg_urb_tit_edilizio; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON COLUMN presentazione_istanza.leggittimita.leg_urb_tit_edilizio IS 'PT=privo di titolo edilizio
DT=dotato di titolo edilizio
';


--
-- Name: COLUMN leggittimita.leg_paesag_immobile; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON COLUMN presentazione_istanza.leggittimita.leg_paesag_immobile IS 'AP=autorizzato paesaggisticamente
PV= privo di titolo paesaggistico';


--
-- Name: leggittimita_titoli; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.leggittimita_titoli (
    pratica_id uuid NOT NULL,
    id bigint NOT NULL,
    leg_tit_denominazione character varying(400),
    leg_tit_rilasciato_da character varying(400),
    leg_tit_num_protocollo character varying(400),
    leg_tit_data_rilascio date,
    leg_tit_intestatario character varying(400)
);


ALTER TABLE presentazione_istanza.leggittimita_titoli OWNER TO presentazione_istanza;

--
-- Name: TABLE leggittimita_titoli; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON TABLE presentazione_istanza.leggittimita_titoli IS 'titoli leggittimità urbanistica';


--
-- Name: leggittimita_titoli_id_seq; Type: SEQUENCE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE SEQUENCE presentazione_istanza.leggittimita_titoli_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE presentazione_istanza.leggittimita_titoli_id_seq OWNER TO presentazione_istanza;

--
-- Name: leggittimita_titoli_id_seq; Type: SEQUENCE OWNED BY; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER SEQUENCE presentazione_istanza.leggittimita_titoli_id_seq OWNED BY presentazione_istanza.leggittimita_titoli.id;


--
-- Name: leggittimita_provvedimenti; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.leggittimita_provvedimenti (
    pratica_id uuid NOT NULL,
    id bigint DEFAULT nextval('presentazione_istanza.leggittimita_titoli_id_seq'::regclass) NOT NULL,
    leg_provv_denominazione character varying(400),
    leg_provv_rilasciato_da character varying(400),
    leg_provv_num_protocollo character varying(400),
    leg_provv_data_rilascio date,
    leg_provv_intestatario character varying(400)
);


ALTER TABLE presentazione_istanza.leggittimita_provvedimenti OWNER TO presentazione_istanza;

--
-- Name: localizzazione_intervento; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.localizzazione_intervento (
    pratica_id uuid NOT NULL,
    comune_id bigint NOT NULL,
    indirizzo character varying(400),
    num_civico character varying(30),
    piano character varying(30),
    interno character varying(50),
    dest_uso_att character varying(400),
    dest_uso_prog character varying(400),
    comune character varying(100),
    sigla_provincia character varying(5),
    data_riferimento_catastale date,
    strade character varying(1) DEFAULT 'N'::character varying NOT NULL,
    id bigint NOT NULL
);


ALTER TABLE presentazione_istanza.localizzazione_intervento OWNER TO presentazione_istanza;

--
-- Name: COLUMN localizzazione_intervento.comune_id; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON COLUMN presentazione_istanza.localizzazione_intervento.comune_id IS 'codice istat del comune';


--
-- Name: COLUMN localizzazione_intervento.strade; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON COLUMN presentazione_istanza.localizzazione_intervento.strade IS 'S= se l''intervento rinteressa l''area stradale';


--
-- Name: localizzazione_intervento_id_seq; Type: SEQUENCE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE SEQUENCE presentazione_istanza.localizzazione_intervento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE presentazione_istanza.localizzazione_intervento_id_seq OWNER TO presentazione_istanza;

--
-- Name: localizzazione_intervento_id_seq; Type: SEQUENCE OWNED BY; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER SEQUENCE presentazione_istanza.localizzazione_intervento_id_seq OWNED BY presentazione_istanza.localizzazione_intervento.id;


--
-- Name: numero_pratica; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.numero_pratica (
    id integer NOT NULL,
    anno bigint,
    numero bigint
);


ALTER TABLE presentazione_istanza.numero_pratica OWNER TO presentazione_istanza;

--
-- Name: TABLE numero_pratica; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON TABLE presentazione_istanza.numero_pratica IS 'contiene gli anni e i numeri che servono per creare il numero pratica';


--
-- Name: numero_pratica_id_seq; Type: SEQUENCE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE SEQUENCE presentazione_istanza.numero_pratica_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE presentazione_istanza.numero_pratica_id_seq OWNER TO presentazione_istanza;

--
-- Name: numero_pratica_id_seq; Type: SEQUENCE OWNED BY; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER SEQUENCE presentazione_istanza.numero_pratica_id_seq OWNED BY presentazione_istanza.numero_pratica.id;


--
-- Name: pareri_e_atti_assenso; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.pareri_e_atti_assenso (
    pratica_id uuid NOT NULL,
    id bigint NOT NULL,
    tipologia_atto character varying(4000),
    autorita_rilascio character varying(4000),
    protocollo character varying(100),
    data_rilascio date,
    flag_atto_parere character varying(1),
    intestatario_atto character varying(400)
);


ALTER TABLE presentazione_istanza.pareri_e_atti_assenso OWNER TO presentazione_istanza;

--
-- Name: COLUMN pareri_e_atti_assenso.flag_atto_parere; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON COLUMN presentazione_istanza.pareri_e_atti_assenso.flag_atto_parere IS 'A=atto P=parere';


--
-- Name: pareri_e_atti_assenso_id_seq; Type: SEQUENCE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE SEQUENCE presentazione_istanza.pareri_e_atti_assenso_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE presentazione_istanza.pareri_e_atti_assenso_id_seq OWNER TO presentazione_istanza;

--
-- Name: pareri_e_atti_assenso_id_seq; Type: SEQUENCE OWNED BY; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER SEQUENCE presentazione_istanza.pareri_e_atti_assenso_id_seq OWNED BY presentazione_istanza.pareri_e_atti_assenso.id;


--
-- Name: particelle_catastali; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.particelle_catastali (
    localizzazione_intervento_id bigint NOT NULL,
    id bigint NOT NULL,
    livello character varying(100),
    sezione character varying(100),
    foglio character varying(100),
    particella character varying(100),
    sub character varying(100),
    cod_cat character varying(10)
);


ALTER TABLE presentazione_istanza.particelle_catastali OWNER TO presentazione_istanza;

--
-- Name: COLUMN particelle_catastali.cod_cat; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON COLUMN presentazione_istanza.particelle_catastali.cod_cat IS 'codice catastale';


--
-- Name: particelle_catastali_id_seq; Type: SEQUENCE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE SEQUENCE presentazione_istanza.particelle_catastali_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE presentazione_istanza.particelle_catastali_id_seq OWNER TO presentazione_istanza;

--
-- Name: particelle_catastali_id_seq; Type: SEQUENCE OWNED BY; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER SEQUENCE presentazione_istanza.particelle_catastali_id_seq OWNED BY presentazione_istanza.particelle_catastali.id;


--
-- Name: pptr_approvato; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.pptr_approvato (
    pratica_id uuid NOT NULL,
    ambito_paesaggistico character varying(4000),
    figure_ambito character varying(4000),
    ricade_terr_art_1_03_co_5_6 character varying(1),
    ricade_terr_art_142_co_2 character varying(1)
);


ALTER TABLE presentazione_istanza.pptr_approvato OWNER TO presentazione_istanza;

--
-- Name: TABLE pptr_approvato; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON TABLE presentazione_istanza.pptr_approvato IS 'selezioni di parte del pannello PPTR (scheda tecnica)
fa riferimento all''AS-IS su TNO_PPTR_APPROVATO';


--
-- Name: COLUMN pptr_approvato.ricade_terr_art_1_03_co_5_6; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON COLUMN presentazione_istanza.pptr_approvato.ricade_terr_art_1_03_co_5_6 IS 'Si No';


--
-- Name: COLUMN pptr_approvato.ricade_terr_art_142_co_2; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON COLUMN presentazione_istanza.pptr_approvato.ricade_terr_art_142_co_2 IS 'Si No';


--
-- Name: pptr_selezioni; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.pptr_selezioni (
    pratica_id uuid NOT NULL,
    cult_bp_interesse_pubb character varying(1),
    cult_bp_interesse_pubb_specif character varying(4000),
    cult_bp_usi_civici character varying(1),
    cult_bp_interesse_archeo character varying(1),
    cult_ucp_citta_consolidata character varying(1),
    cult_ucp_strat_insed_archeo character varying(1),
    cult_ucp_strat_insed_arc_spec character varying(4000),
    cult_ucp_strat_rete_tratturi character varying(1),
    cult_ucp_strat_tratturi_specif character varying(4000),
    cult_ucp_strat_insed_risk_arc character varying(1),
    cult_ucp_str_ins_risk_arc_spec character varying(4000),
    cult_ucp_risp_compon_insediat character varying(1),
    cult_ucp_paesag_rurali character varying(1),
    perc_ucp_strade_valenza_paesag character varying(1),
    cult_ucp_strade_panoramiche character varying(1),
    cult_ucp_punti_panoramici character varying(1),
    cult_ucp_coni_visuali character varying(1),
    bot_bp_boschi character varying(1),
    bot_bp_zone_umide_ramsar character varying(1),
    bot_bp_z_umide_ramsar_specif character varying(4000),
    bot_ucp_aree_umide character varying(1),
    bot_ucp_aree_umide_specif character varying(4000),
    bot_ucp_prati_pascoli character varying(1),
    bot_ucp_form_arbustive character varying(1),
    bot_ucp_aree_risp_boschi character varying(1),
    nat_ucp_area_risp_parchi character varying(1),
    nat_ucp_siti_ril_natural character varying(1),
    nat_ucp_siti_ril_natur_specif character varying(4000),
    geo_ucp_versanti character varying(1),
    geo_ucp_lame_gravine character varying(1),
    geo_ucp_doline character varying(1),
    geo_ucp_grotte character varying(1),
    geo_ucp_geositi character varying(1),
    geo_ucp_inghiottitoi character varying(1),
    geo_ucp_cordoni_dunari character varying(1),
    idro_bp_territ_costieri character varying(1),
    idro_bp_territ_conterm_laghi character varying(1),
    idro_bp_corsi_acqua character varying(1),
    idro_bp_corsi_acqua_specif character varying(4000),
    idro_ucp_reticolo_idrografico character varying(1),
    idro_ucp_sorgenti character varying(1),
    idro_ucp_aree_sogg_vinc character varying(1)
);


ALTER TABLE presentazione_istanza.pptr_selezioni OWNER TO presentazione_istanza;

--
-- Name: TABLE pptr_selezioni; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON TABLE presentazione_istanza.pptr_selezioni IS 'selezioni sulla griglia PPTR:
STRUTTURA GEOMOEFOLOGICA 
Componenti geomorfologiche 
Componenti idrologiche 
STRUTTURA ECOSISTEMICA – AMBIENTALE 
Componenti botanico-vegetazionali 
Componenti delle aree protette e dei siti naturalistici 
 
STRUTTURA ANTROPICA E STORICO-CULTURALE 
Componenti culturali e insediative 
Componenti dei valori percettivi 

Tabelle di riferimento su CIVILIA_CS:
TNO_PPTR_STRUT_ANTRO_STOR_CULT
TNO_PPTR_STRUT_ECOSISTEMICA
TNO_PPTR_STRUT_IDROGEOMORF';


--
-- Name: COLUMN pptr_selezioni.cult_bp_interesse_pubb; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON COLUMN presentazione_istanza.pptr_selezioni.cult_bp_interesse_pubb IS 'La definizione dei BP - Immobili e aree di notevole interesse pubblico viene gestita nella sezione ''Localizzazione dell''intervento''';


--
-- Name: COLUMN pptr_selezioni.cult_ucp_paesag_rurali; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON COLUMN presentazione_istanza.pptr_selezioni.cult_ucp_paesag_rurali IS 'La definizione dei UCP - Paesaggi rurali viene gestita nella sezione ''Localizzazione dell''intervento'' ';


--
-- Name: COLUMN pptr_selezioni.nat_ucp_area_risp_parchi; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON COLUMN presentazione_istanza.pptr_selezioni.nat_ucp_area_risp_parchi IS 'BP - Parchi e riserve  
Nota:La definizione dei Parchi e riserve viene gestita nella sezione ''Localizzazione dell''intervento''  ';


--
-- Name: pratica; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.pratica (
    id uuid NOT NULL,
    codice_pratica_appptr character varying(100) NOT NULL,
    ente_delegato character varying(20) NOT NULL,
    in_sanatoria boolean,
    tipo_procedimento integer NOT NULL,
    stato character varying(100) NOT NULL,
    data_creazione date NOT NULL,
    oggetto character varying(4000) NOT NULL,
    user_id character varying(100) NOT NULL,
    data_modifica date DEFAULT CURRENT_DATE NOT NULL
);


ALTER TABLE presentazione_istanza.pratica OWNER TO presentazione_istanza;

--
-- Name: TABLE pratica; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON TABLE presentazione_istanza.pratica IS 'tabella centrale istanza da presentare';


--
-- Name: COLUMN pratica.ente_delegato; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON COLUMN presentazione_istanza.pratica.ente_delegato IS 'puntare alla tabella degli enti delegati abilitati alla ricezione delle istanze da istruire';


--
-- Name: COLUMN pratica.stato; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON COLUMN presentazione_istanza.pratica.stato IS 'CompilaDomanda,   GeneraStampaDomanda ,
AllegaDocumentiSottoscritti ,     IstanzaPresentata , InAttesaDiProtocollazione 
';


--
-- Name: procedimenti_contenzioso; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.procedimenti_contenzioso (
    pratica_id uuid NOT NULL,
    flag_contenzioso_in_atto character varying(1),
    descrizione character varying(500)
);


ALTER TABLE presentazione_istanza.procedimenti_contenzioso OWNER TO presentazione_istanza;

--
-- Name: TABLE procedimenti_contenzioso; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON TABLE presentazione_istanza.procedimenti_contenzioso IS 'pannello procedimenti contenzioso, tabella riferimento su CIVILIA_CS TNO_PPTR_PROCED_CONTENZIOSO';


--
-- Name: COLUMN procedimenti_contenzioso.flag_contenzioso_in_atto; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON COLUMN presentazione_istanza.procedimenti_contenzioso.flag_contenzioso_in_atto IS 'S=Vi sono procedimenti di contenzioso in atto N=Non vi sono procedimenti di contenzioso in atto';


--
-- Name: qualificazione_31_2017_values; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.qualificazione_31_2017_values (
    pratica_id uuid NOT NULL,
    codice character varying NOT NULL
);


ALTER TABLE presentazione_istanza.qualificazione_31_2017_values OWNER TO presentazione_istanza;

--
-- Name: TABLE qualificazione_31_2017_values; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON TABLE presentazione_istanza.qualificazione_31_2017_values IS 'selezione sui 42 valori ammessi di:
le opere rientrano rientrano tra gli interventi di lieve entità di cui all’allegato B al d.P.R. 31/2017:
B1....B42';


--
-- Name: qualificazione_42_2004_values; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.qualificazione_42_2004_values (
    pratica_id uuid NOT NULL,
    codice character varying(50) NOT NULL
);


ALTER TABLE presentazione_istanza.qualificazione_42_2004_values OWNER TO presentazione_istanza;

--
-- Name: TABLE qualificazione_42_2004_values; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON TABLE presentazione_istanza.qualificazione_42_2004_values IS 'l’intervento ricade tra le seguenti categorie di cui al c.4 art. 167 D.Lgs. 42/2004:';


--
-- Name: qualificazione_intervento; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.qualificazione_intervento (
    pratica_id uuid NOT NULL,
    dpcm_12_2005 character varying(50),
    istanza_rinnovo character varying(50),
    lieve_entita_rientrano_31_2017 character varying(50),
    ricadono_31_2017 character varying(50),
    non_ricadono_31_2017 character varying(50)
);


ALTER TABLE presentazione_istanza.qualificazione_intervento OWNER TO presentazione_istanza;

--
-- Name: referenti; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.referenti (
    id bigint NOT NULL,
    tipo_referente character varying(4) NOT NULL,
    pratica_id uuid NOT NULL,
    cognome character varying(100),
    nome character varying(100),
    codice_fiscale character(16),
    comune_nascita integer,
    provincia_nascita integer,
    nazionalita_nascita integer,
    comune_nascita_estero character varying(100),
    provincia_nascita_estero character varying(100),
    data_nascita date,
    sesso character(1),
    indirizzo_residenza character varying(100),
    civico_residenza character varying(100),
    cap_residenza character(5),
    comune_residenza integer,
    provincia_residenza integer,
    nazionalita_residenza integer,
    comune_residenza_estero character varying(100),
    provincia_residenza_estero character varying(100),
    pec character varying(255),
    mail character varying(255),
    telefono character varying(100),
    cellulare character varying(100),
    fax character varying(100),
    ditta boolean DEFAULT false,
    ditta_ente character varying(100),
    ditta_cf character varying(100),
    ditta_partita_iva character varying(100),
    ditta_qualita_di integer,
    ditta_qualita_altro character varying(255),
    tecnico_studio_indirizzo character varying(100),
    tecnico_studio_civico character varying(100),
    tecnico_studio_cap character(5),
    tecnico_studio_comune integer,
    tecnico_studio_provincia integer,
    tecnico_studio_nazionalita integer,
    tecnico_studio_comune_estero character varying(100),
    tecnico_studio_provincia_estero character varying(100),
    tecnico_ord_collegio character varying(400),
    tecnico_collegio_sede character varying(100),
    tecnico_collegio_n_iscr character varying(100),
    nazionalita_residenza_name character varying(100),
    provincia_residenza_name character varying(100),
    comune_residenza_name character varying(100),
    nazionalita_nascita_name character varying(100),
    provincia_nascita_name character varying(100),
    comune_nascita_name character varying(100),
    tecnico_studio_nazionalita_name character varying(100),
    tecnico_studio_provincia_name character varying(100),
    tecnico_studio_comune_name character varying(100),
    titolarita_id integer,
    specifica_titolarita character varying(400),
    titolarita_esclusiva character varying(1)
);


ALTER TABLE presentazione_istanza.referenti OWNER TO presentazione_istanza;

--
-- Name: COLUMN referenti.tipo_referente; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON COLUMN presentazione_istanza.referenti.tipo_referente IS 'Questo e un flag ( TP=Titolare Principale AT=Altro Titolare SD=Soggetto Dichiarante TR=Tecnico Redattore della Relazione Tecnica )';


--
-- Name: COLUMN referenti.titolarita_id; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON COLUMN presentazione_istanza.referenti.titolarita_id IS 'ruolo di titolarità sull''intervento';


--
-- Name: COLUMN referenti.titolarita_esclusiva; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON COLUMN presentazione_istanza.referenti.titolarita_esclusiva IS 'S   o N ';


--
-- Name: referenti_documento; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.referenti_documento (
    referente_id bigint NOT NULL,
    id_tipo integer,
    numero character varying(100),
    data_rilascio date,
    ente_rilascio character varying(100),
    data_scadenza date,
    allegato_id uuid
);


ALTER TABLE presentazione_istanza.referenti_documento OWNER TO presentazione_istanza;

--
-- Name: TABLE referenti_documento; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON TABLE presentazione_istanza.referenti_documento IS 'Tabella dei documenti di riconoscimento dei referenti della pratica';


--
-- Name: referenti_id_seq; Type: SEQUENCE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE SEQUENCE presentazione_istanza.referenti_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE presentazione_istanza.referenti_id_seq OWNER TO presentazione_istanza;

--
-- Name: referenti_id_seq; Type: SEQUENCE OWNED BY; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER SEQUENCE presentazione_istanza.referenti_id_seq OWNED BY presentazione_istanza.referenti.id;


--
-- Name: richieste_abilitazione; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.richieste_abilitazione (
    id bigint NOT NULL,
    user_id character varying(255) NOT NULL,
    data_richiesta timestamp without time zone DEFAULT CURRENT_DATE NOT NULL,
    stato_residenza character varying(100) NOT NULL,
    comune_residenza character varying(100),
    comune_residenza_estero character varying(100),
    prov_residenza character varying(4),
    indirizzo character varying(255),
    num_civico character varying(30),
    cap character varying(10),
    indirizzo_email character varying(255),
    tipo_documento integer NOT NULL,
    nome_file character varying(255),
    formato_file character varying(100),
    data_caricamento timestamp without time zone DEFAULT CURRENT_DATE NOT NULL,
    num_doc_ident character varying(100),
    data_doc_ident date,
    ente_ril_doc_ident character varying(400),
    path_cms character varying(400),
    dimensione integer,
    id_cms character varying(100),
    privacy_accettata boolean DEFAULT false
);


ALTER TABLE presentazione_istanza.richieste_abilitazione OWNER TO presentazione_istanza;

--
-- Name: TABLE richieste_abilitazione; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON TABLE presentazione_istanza.richieste_abilitazione IS 'richieste di abilitazione utente richiedente';


--
-- Name: ruolo_referente; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.ruolo_referente (
    id integer NOT NULL,
    descrizione character varying(255) NOT NULL,
    titolarita boolean,
    altra_titolarita boolean,
    attiva_specifica boolean
);


ALTER TABLE presentazione_istanza.ruolo_referente OWNER TO presentazione_istanza;

--
-- Name: TABLE ruolo_referente; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON TABLE presentazione_istanza.ruolo_referente IS 'ruoli referente';


--
-- Name: COLUMN ruolo_referente.titolarita; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON COLUMN presentazione_istanza.ruolo_referente.titolarita IS 'indica se utilizzabile come opzione in titolarità';


--
-- Name: tipi_e_qualificazioni; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.tipi_e_qualificazioni (
    codice character varying(50) NOT NULL,
    stile character varying NOT NULL,
    raggruppamento character varying(50) NOT NULL,
    zona integer NOT NULL,
    label character varying(3000) NOT NULL,
    ordine integer NOT NULL
);


ALTER TABLE presentazione_istanza.tipi_e_qualificazioni OWNER TO presentazione_istanza;

--
-- Name: TABLE tipi_e_qualificazioni; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON TABLE presentazione_istanza.tipi_e_qualificazioni IS 'tipologiche per la descrizione dell''intervento come da decreti.';


--
-- Name: tipo_contenuto; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.tipo_contenuto (
    type character varying(255) NOT NULL,
    descrizione character varying(400) NOT NULL,
    sezione character varying(255)
);


ALTER TABLE presentazione_istanza.tipo_contenuto OWNER TO presentazione_istanza;

--
-- Name: TABLE tipo_contenuto; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON TABLE presentazione_istanza.tipo_contenuto IS 'tipi di contenuto previsto per l''istanza';


--
-- Name: COLUMN tipo_contenuto.sezione; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON COLUMN presentazione_istanza.tipo_contenuto.sezione IS 'AMMINISTRATIVI
TECNICI
....';


--
-- Name: tipo_documento_identita; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.tipo_documento_identita (
    id integer NOT NULL,
    nome character varying(100) NOT NULL,
    "order" smallint NOT NULL
);


ALTER TABLE presentazione_istanza.tipo_documento_identita OWNER TO presentazione_istanza;

--
-- Name: TABLE tipo_documento_identita; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON TABLE presentazione_istanza.tipo_documento_identita IS 'Tabella dei tipi documenti di riconoscimento';


--
-- Name: tipo_documento_identita_id_seq; Type: SEQUENCE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE SEQUENCE presentazione_istanza.tipo_documento_identita_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE presentazione_istanza.tipo_documento_identita_id_seq OWNER TO presentazione_istanza;

--
-- Name: tipo_documento_identita_id_seq; Type: SEQUENCE OWNED BY; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER SEQUENCE presentazione_istanza.tipo_documento_identita_id_seq OWNED BY presentazione_istanza.tipo_documento_identita.id;


--
-- Name: tipo_intervento; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.tipo_intervento (
    pratica_id uuid NOT NULL,
    descrizione_intervento character varying(4000),
    tipo_intervento character varying(50),
    conform_interv_artt_reg_edil character varying(400),
    conform_data_approv_reg_edil date,
    conform_atto_approv_reg_edil character varying(400)
);


ALTER TABLE presentazione_istanza.tipo_intervento OWNER TO presentazione_istanza;

--
-- Name: TABLE tipo_intervento; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON TABLE presentazione_istanza.tipo_intervento IS 'sezione descrizione in blocco istanza';


--
-- Name: tipo_procedimento; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.tipo_procedimento (
    id integer NOT NULL,
    codice character varying(50) NOT NULL,
    descrizione character varying(1000),
    invia_email boolean DEFAULT false NOT NULL,
    sanatoria boolean DEFAULT false NOT NULL,
    abilitato_presentazione boolean DEFAULT false
);


ALTER TABLE presentazione_istanza.tipo_procedimento OWNER TO presentazione_istanza;

--
-- Name: tipo_ruolo_ditta; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.tipo_ruolo_ditta (
    id integer NOT NULL,
    nome character varying(100) NOT NULL,
    "order" smallint NOT NULL
);


ALTER TABLE presentazione_istanza.tipo_ruolo_ditta OWNER TO presentazione_istanza;

--
-- Name: TABLE tipo_ruolo_ditta; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON TABLE presentazione_istanza.tipo_ruolo_ditta IS 'Tabella dei tipi ruoli in ditta';


--
-- Name: tipo_ruolo_ditta_id_seq; Type: SEQUENCE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE SEQUENCE presentazione_istanza.tipo_ruolo_ditta_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE presentazione_istanza.tipo_ruolo_ditta_id_seq OWNER TO presentazione_istanza;

--
-- Name: tipo_ruolo_ditta_id_seq; Type: SEQUENCE OWNED BY; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER SEQUENCE presentazione_istanza.tipo_ruolo_ditta_id_seq OWNED BY presentazione_istanza.tipo_ruolo_ditta.id;


--
-- Name: tno_pptr_strumenti_urbanistici; Type: TABLE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE TABLE presentazione_istanza.tno_pptr_strumenti_urbanistici (
    id bigint NOT NULL,
    istat_6_prov character varying(6),
    tipo_strumento integer,
    stato character varying(255),
    atto character varying(255),
    data_atto date,
    utente_modifica character varying(255),
    data_modifica date
);


ALTER TABLE presentazione_istanza.tno_pptr_strumenti_urbanistici OWNER TO presentazione_istanza;

--
-- Name: TABLE tno_pptr_strumenti_urbanistici; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON TABLE presentazione_istanza.tno_pptr_strumenti_urbanistici IS 'tabella popolata dall''esterno (forse da file excel) che contiene i dati aggiornati circa la conformità dei piani urbanistici comunali su PPTR art.100 e art.38';


--
-- Name: COLUMN tno_pptr_strumenti_urbanistici.tipo_strumento; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON COLUMN presentazione_istanza.tno_pptr_strumenti_urbanistici.tipo_strumento IS '1: art. 100 - 2: art. 38';


--
-- Name: COLUMN tno_pptr_strumenti_urbanistici.stato; Type: COMMENT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

COMMENT ON COLUMN presentazione_istanza.tno_pptr_strumenti_urbanistici.stato IS 'indica lo stato del comune rispetto al determinato strumento urbanistico';


--
-- Name: tno_pptr_strumenti_urbanistici_id_seq; Type: SEQUENCE; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE SEQUENCE presentazione_istanza.tno_pptr_strumenti_urbanistici_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE presentazione_istanza.tno_pptr_strumenti_urbanistici_id_seq OWNER TO presentazione_istanza;

--
-- Name: tno_pptr_strumenti_urbanistici_id_seq; Type: SEQUENCE OWNED BY; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER SEQUENCE presentazione_istanza.tno_pptr_strumenti_urbanistici_id_seq OWNED BY presentazione_istanza.tno_pptr_strumenti_urbanistici.id;


--
-- Name: destinazione_urbanistica_intervento id; Type: DEFAULT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.destinazione_urbanistica_intervento ALTER COLUMN id SET DEFAULT nextval('presentazione_istanza.destinazione_urbanistica_intervento_id_seq'::regclass);


--
-- Name: disclaimer_pratica id; Type: DEFAULT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.disclaimer_pratica ALTER COLUMN id SET DEFAULT nextval('presentazione_istanza.disclaimer_pratica_id_seq'::regclass);


--
-- Name: leggittimita_titoli id; Type: DEFAULT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.leggittimita_titoli ALTER COLUMN id SET DEFAULT nextval('presentazione_istanza.leggittimita_titoli_id_seq'::regclass);


--
-- Name: localizzazione_intervento id; Type: DEFAULT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.localizzazione_intervento ALTER COLUMN id SET DEFAULT nextval('presentazione_istanza.localizzazione_intervento_id_seq'::regclass);


--
-- Name: numero_pratica id; Type: DEFAULT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.numero_pratica ALTER COLUMN id SET DEFAULT nextval('presentazione_istanza.numero_pratica_id_seq'::regclass);


--
-- Name: pareri_e_atti_assenso id; Type: DEFAULT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.pareri_e_atti_assenso ALTER COLUMN id SET DEFAULT nextval('presentazione_istanza.pareri_e_atti_assenso_id_seq'::regclass);


--
-- Name: particelle_catastali id; Type: DEFAULT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.particelle_catastali ALTER COLUMN id SET DEFAULT nextval('presentazione_istanza.particelle_catastali_id_seq'::regclass);


--
-- Name: referenti id; Type: DEFAULT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.referenti ALTER COLUMN id SET DEFAULT nextval('presentazione_istanza.referenti_id_seq'::regclass);


--
-- Name: tipo_documento_identita id; Type: DEFAULT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.tipo_documento_identita ALTER COLUMN id SET DEFAULT nextval('presentazione_istanza.tipo_documento_identita_id_seq'::regclass);


--
-- Name: tipo_ruolo_ditta id; Type: DEFAULT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.tipo_ruolo_ditta ALTER COLUMN id SET DEFAULT nextval('presentazione_istanza.tipo_ruolo_ditta_id_seq'::regclass);


--
-- Name: tno_pptr_strumenti_urbanistici id; Type: DEFAULT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.tno_pptr_strumenti_urbanistici ALTER COLUMN id SET DEFAULT nextval('presentazione_istanza.tno_pptr_strumenti_urbanistici_id_seq'::regclass);



--
-- Data for Name: disclaimer; Type: TABLE DATA; Schema: presentazione_istanza; Owner: presentazione_istanza
--

INSERT INTO presentazione_istanza.disclaimer (id, testo, tipo_procedimento, tipo_referente) VALUES (1, 'che le opere/interventi in progetto non rientrano nei casi di esclusione previsti ai sensi dell''art 149 del D.Lgs 42/2004', 1, 'SD');
INSERT INTO presentazione_istanza.disclaimer (id, testo, tipo_procedimento, tipo_referente) VALUES (2, 'di astenersi dall''avviare i lavori fino a quando non ha ottenuto la prescritta autorizzazione paesaggistica', 1, 'SD');
INSERT INTO presentazione_istanza.disclaimer (id, testo, tipo_procedimento, tipo_referente) VALUES (3, 'di essere informato che l''autorizzazione paesaggistica non è atto che legittima l''esecuzione dei lavori', 1, 'SD');
INSERT INTO presentazione_istanza.disclaimer (id, testo, tipo_procedimento, tipo_referente) VALUES (4, 'di essere a conoscenza che l''autorizzazione paesaggistica ha valore esclusivamente per la valutazione ai fini della tutela paesaggistica e non sulla conformità delgli strumenti urbanistici adottati o approvati, ai regolamenti edilizi e di settore, per i quali il progetto deve rispettare le norme di riferimento vigenti', 1, 'SD');
INSERT INTO presentazione_istanza.disclaimer (id, testo, tipo_procedimento, tipo_referente) VALUES (5, 'di aver letto l''informativa sui trattamento dei dati personali posta al termine del presente modulo', 1, 'SD');
INSERT INTO presentazione_istanza.disclaimer (id, testo, tipo_procedimento, tipo_referente) VALUES (6, 'di astenersi dall''avviare i lavori fino a quando non ha ottenuto la prescritta autorizzazione paesaggistica', 2, 'SD');
INSERT INTO presentazione_istanza.disclaimer (id, testo, tipo_procedimento, tipo_referente) VALUES (7, 'di essere informato che l''autorizzazione paesaggistica non è atto che legittima l''esecuzione dei lavori', 2, 'SD');
INSERT INTO presentazione_istanza.disclaimer (id, testo, tipo_procedimento, tipo_referente) VALUES (8, 'di essere a conoscenza che l''autorizzazione paesaggistica ha valore esclusivamente per la valutazione ai fini della tutela paesaggistica e non sulla conformità delgli strumenti urbanistici adottati o approvati, ai regolamenti edilizi e di settore, per i quali il progetto deve rispettare le norme di riferimento vigenti', 2, 'SD');
INSERT INTO presentazione_istanza.disclaimer (id, testo, tipo_procedimento, tipo_referente) VALUES (9, 'di aver letto l''informativa sui trattamento dei dati personali posta al termine del presente modulo', 2, 'SD');
INSERT INTO presentazione_istanza.disclaimer (id, testo, tipo_procedimento, tipo_referente) VALUES (10, 'di aver letto l''informativa sui trattamento dei dati personali posta al termine del presente modulo', 3, 'SD');
INSERT INTO presentazione_istanza.disclaimer (id, testo, tipo_procedimento, tipo_referente) VALUES (11, 'di essere informato che l''autorizzazione paesaggistica non è atto che legittima l''esecuzione dei lavori', 3, 'SD');
INSERT INTO presentazione_istanza.disclaimer (id, testo, tipo_procedimento, tipo_referente) VALUES (12, 'di essere a conoscenza che l''autorizzazione paesaggistica ha valore esclusivamente per la valutazione ai fini della tutela paesaggistica e non sulla conformità delgli strumenti urbanistici adottati o approvati, ai regolamenti edilizi e di settore, per i quali il progetto deve rispettare le norme di riferimento vigenti', 3, 'SD');
INSERT INTO presentazione_istanza.disclaimer (id, testo, tipo_procedimento, tipo_referente) VALUES (13, 'di aver letto l''informativa sui trattamento dei dati personali posta al termine del presente modulo', 4, 'SD');
INSERT INTO presentazione_istanza.disclaimer (id, testo, tipo_procedimento, tipo_referente) VALUES (14, 'di essere informato che l''autorizzazione paesaggistica non è atto che legittima l''esecuzione dei lavori', 4, 'SD');
INSERT INTO presentazione_istanza.disclaimer (id, testo, tipo_procedimento, tipo_referente) VALUES (15, 'di essere a conoscenza che l''autorizzazione paesaggistica ha valore esclusivamente per la valutazione ai fini della tutela paesaggistica e non sulla conformità delgli strumenti urbanistici adottati o approvati, ai regolamenti edilizi e di settore, per i quali il progetto deve rispettare le norme di riferimento vigenti', 4, 'SD');
INSERT INTO presentazione_istanza.disclaimer (id, testo, tipo_procedimento, tipo_referente) VALUES (16, 'di essere informato che l''autorizzazione paesaggistica non è atto che legittima l''esecuzione dei lavori', 1, 'TR');




--
-- Data for Name: ruolo_referente; Type: TABLE DATA; Schema: presentazione_istanza; Owner: presentazione_istanza
--

INSERT INTO presentazione_istanza.ruolo_referente (id, descrizione, titolarita, altra_titolarita, attiva_specifica) VALUES (1, 'proprietario/comproprietario', false, true, false);
INSERT INTO presentazione_istanza.ruolo_referente (id, descrizione, titolarita, altra_titolarita, attiva_specifica) VALUES (2, 'nudo proprietario', false, true, false);
INSERT INTO presentazione_istanza.ruolo_referente (id, descrizione, titolarita, altra_titolarita, attiva_specifica) VALUES (3, 'usufruttuario', false, true, false);
INSERT INTO presentazione_istanza.ruolo_referente (id, descrizione, titolarita, altra_titolarita, attiva_specifica) VALUES (4, 'proprietario esclusivo', true, false, false);
INSERT INTO presentazione_istanza.ruolo_referente (id, descrizione, titolarita, altra_titolarita, attiva_specifica) VALUES (6, 'amministratore/delegato dal Condominio(solo per i lavori che interessano parti condominiali)', true, false, false);
INSERT INTO presentazione_istanza.ruolo_referente (id, descrizione, titolarita, altra_titolarita, attiva_specifica) VALUES (7, 'comproprietario con i soggetti elencati nella sezione "Altri richiedenti"', true, false, false);
INSERT INTO presentazione_istanza.ruolo_referente (id, descrizione, titolarita, altra_titolarita, attiva_specifica) VALUES (8, 'locatario/comodatario a tale scopo autorizzato dai proprietari nella sezione "Altri richiedenti"', true, false, false);
INSERT INTO presentazione_istanza.ruolo_referente (id, descrizione, titolarita, altra_titolarita, attiva_specifica) VALUES (9, 'altro (specificare)', true, true, true);
INSERT INTO presentazione_istanza.ruolo_referente (id, descrizione, titolarita, altra_titolarita, attiva_specifica) VALUES (5, 'rappresentante legale della Ditta, Società, Associazione o Ente Pubblico', true, false, true);


--
-- Data for Name: tipi_e_qualificazioni; Type: TABLE DATA; Schema: presentazione_istanza; Owner: presentazione_istanza
--



--
-- Data for Name: tipo_contenuto; Type: TABLE DATA; Schema: presentazione_istanza; Owner: presentazione_istanza
--



--
-- Data for Name: tipo_documento_identita; Type: TABLE DATA; Schema: presentazione_istanza; Owner: presentazione_istanza
--

INSERT INTO presentazione_istanza.tipo_documento_identita (id, nome, "order") VALUES (1, 'Carta d''identità', 1);
INSERT INTO presentazione_istanza.tipo_documento_identita (id, nome, "order") VALUES (2, 'Patente di guida', 2);
INSERT INTO presentazione_istanza.tipo_documento_identita (id, nome, "order") VALUES (4, 'Altro', 999);
INSERT INTO presentazione_istanza.tipo_documento_identita (id, nome, "order") VALUES (3, 'Passaporto', 3);


--
-- Data for Name: tipo_intervento; Type: TABLE DATA; Schema: presentazione_istanza; Owner: presentazione_istanza
--



--
-- Data for Name: tipo_procedimento; Type: TABLE DATA; Schema: presentazione_istanza; Owner: presentazione_istanza
--

INSERT INTO presentazione_istanza.tipo_procedimento (id, codice, descrizione, invia_email, sanatoria, abilitato_presentazione) VALUES (1, 'AUT_PAES_ORD', 'AUTORIZZAZIONE PAESAGGISTICA art. 146, D. Lgs. 42/2004 – art. 90, NTA PPTR (ORDINARIA)', false, false, true);
INSERT INTO presentazione_istanza.tipo_procedimento (id, codice, descrizione, invia_email, sanatoria, abilitato_presentazione) VALUES (2, 'AUT_PAES_SEMPL_DPR_31_2017', 'AUTORIZZAZIONE PAESAGGISTICA SEMPLIFICATA D.P.R.  31/2017 – art. 90, NTA PPTR (PER LE OPERE IL CUI IMPATTO PAESAGGISTICO E'' VALUTATO MEDIANTE UNA DOCUMENTAZIONE SEMPLIFICATA)', false, false, true);
INSERT INTO presentazione_istanza.tipo_procedimento (id, codice, descrizione, invia_email, sanatoria, abilitato_presentazione) VALUES (3, 'ACCERT_COMPAT_PAES_DLGS_42_2004', 'ACCERTAMENTO DI COMPATIBILITÀ PAESAGGISTICA Art. 167 e 181, D. Lgs. 42/2004 (PER OPERE IN DIFFORMITA’ O ASSENZA DI AUTORIZZAZIONE PAESAGGISTICA)', false, false, true);
INSERT INTO presentazione_istanza.tipo_procedimento (id, codice, descrizione, invia_email, sanatoria, abilitato_presentazione) VALUES (4, 'ACCERT_COMPAT_PAES_DPR_31_2017', 'ACCERTAMENTO DI COMPATIBILITÀ PAESAGGISTICA art. 91, NTA PPTR [IN VIGENZA D.P.R.  31/2017] (PER INTERVENTI CHE COMPORTINO MODIFICA DELLO STATO DEI LUOGHI NEGLI ULTERIORI CONTESTI COME INDIVIDUATI NELL’ART. 38 C. 3.1 NTA PPTR)', false, true, true);
INSERT INTO presentazione_istanza.tipo_procedimento (id, codice, descrizione, invia_email, sanatoria, abilitato_presentazione) VALUES (5, 'ACCERT_COMPAT_PAES_DPR_139_2010', 'ACCERTAMENTO DI COMPATIBILITÀ PAESAGGISTICA art. 91, NTA PPTR [IN VIGENZA D.P.R. 139/2010] (PER INTERVENTI CHE COMPORTINO MODIFICA DELLO STATO DEI LUOGHI NEGLI ULTERIORI CONTESTI COME INDIVIDUATI NELL’ART. 38 C. 3.1 NTA PPTR)', false, true, false);
INSERT INTO presentazione_istanza.tipo_procedimento (id, codice, descrizione, invia_email, sanatoria, abilitato_presentazione) VALUES (6, 'AUT_PAES_SEMPL_DPR_139_2010', 'AUTORIZZAZIONE PAESAGGISTICA SEMPLIFICATA D.P.R. 139/2010 – art. 90, NTA PPTR (PER LE OPERE IL CUI IMPATTO PAESAGGISTICO E'' VALUTATO MEDIANTE UNA DOCUMENTAZIONE SEMPLIFICATA)', false, false, false);


--
-- Data for Name: tipo_ruolo_ditta; Type: TABLE DATA; Schema: presentazione_istanza; Owner: presentazione_istanza
--

INSERT INTO presentazione_istanza.tipo_ruolo_ditta (id, nome, "order") VALUES (1, 'Rappresentante legale', 1);
INSERT INTO presentazione_istanza.tipo_ruolo_ditta (id, nome, "order") VALUES (3, 'Altro', 3);


--
-- Data for Name: tno_pptr_strumenti_urbanistici; Type: TABLE DATA; Schema: presentazione_istanza; Owner: presentazione_istanza
--



--
-- Name: destinazione_urbanistica_intervento_id_seq; Type: SEQUENCE SET; Schema: presentazione_istanza; Owner: presentazione_istanza
--

SELECT pg_catalog.setval('presentazione_istanza.destinazione_urbanistica_intervento_id_seq', 1, false);


--
-- Name: disclaimer_pratica_id_seq; Type: SEQUENCE SET; Schema: presentazione_istanza; Owner: presentazione_istanza
--

SELECT pg_catalog.setval('presentazione_istanza.disclaimer_pratica_id_seq', 40, true);


--
-- Name: leggittimita_titoli_id_seq; Type: SEQUENCE SET; Schema: presentazione_istanza; Owner: presentazione_istanza
--

SELECT pg_catalog.setval('presentazione_istanza.leggittimita_titoli_id_seq', 1, false);


--
-- Name: localizzazione_intervento_id_seq; Type: SEQUENCE SET; Schema: presentazione_istanza; Owner: presentazione_istanza
--

SELECT pg_catalog.setval('presentazione_istanza.localizzazione_intervento_id_seq', 1, false);


--
-- Name: numero_pratica_id_seq; Type: SEQUENCE SET; Schema: presentazione_istanza; Owner: presentazione_istanza
--

SELECT pg_catalog.setval('presentazione_istanza.numero_pratica_id_seq', 25, true);


--
-- Name: pareri_e_atti_assenso_id_seq; Type: SEQUENCE SET; Schema: presentazione_istanza; Owner: presentazione_istanza
--

SELECT pg_catalog.setval('presentazione_istanza.pareri_e_atti_assenso_id_seq', 1, false);


--
-- Name: particelle_catastali_id_seq; Type: SEQUENCE SET; Schema: presentazione_istanza; Owner: presentazione_istanza
--

SELECT pg_catalog.setval('presentazione_istanza.particelle_catastali_id_seq', 1, false);


--
-- Name: referenti_id_seq; Type: SEQUENCE SET; Schema: presentazione_istanza; Owner: presentazione_istanza
--

SELECT pg_catalog.setval('presentazione_istanza.referenti_id_seq', 65, true);


--
-- Name: tipo_documento_identita_id_seq; Type: SEQUENCE SET; Schema: presentazione_istanza; Owner: presentazione_istanza
--

SELECT pg_catalog.setval('presentazione_istanza.tipo_documento_identita_id_seq', 1, false);


--
-- Name: tipo_ruolo_ditta_id_seq; Type: SEQUENCE SET; Schema: presentazione_istanza; Owner: presentazione_istanza
--

SELECT pg_catalog.setval('presentazione_istanza.tipo_ruolo_ditta_id_seq', 1, false);


--
-- Name: tno_pptr_strumenti_urbanistici_id_seq; Type: SEQUENCE SET; Schema: presentazione_istanza; Owner: presentazione_istanza
--

SELECT pg_catalog.setval('presentazione_istanza.tno_pptr_strumenti_urbanistici_id_seq', 1, false);


--
-- Name: allegati allegati_pkey; Type: CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.allegati
    ADD CONSTRAINT allegati_pkey PRIMARY KEY (id);


--
-- Name: allegati_tipo_contenuto allegati_tipo_contenuto_pkey; Type: CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.allegati_tipo_contenuto
    ADD CONSTRAINT allegati_tipo_contenuto_pkey PRIMARY KEY (allegati_id, tipo_contenuto_id);


--
-- Name: altre_dichiarazioni_rich altre_dichiararazioni_rich_pkey; Type: CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.altre_dichiarazioni_rich
    ADD CONSTRAINT altre_dichiararazioni_rich_pkey PRIMARY KEY (pratica_id);


--
-- Name: assogg_procedure_edilizie assogg_procedure_edilizie_pkey; Type: CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.assogg_procedure_edilizie
    ADD CONSTRAINT assogg_procedure_edilizie_pkey PRIMARY KEY (pratica_id);


--
-- Name: caratterizzazione_intervento_values caratterizzazione_intervento_selected_pkey; Type: CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.caratterizzazione_intervento_values
    ADD CONSTRAINT caratterizzazione_intervento_selected_pkey PRIMARY KEY (pratica_id, codice);


--
-- Name: comuni_competenza comuni_competenza_pkey; Type: CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.comuni_competenza
    ADD CONSTRAINT comuni_competenza_pkey PRIMARY KEY (pratica_id, ente_id);


--
-- Name: destinazione_urbanistica_intervento destinazione_urbanistica_intervento_pkey; Type: CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.destinazione_urbanistica_intervento
    ADD CONSTRAINT destinazione_urbanistica_intervento_pkey PRIMARY KEY (id);


--
-- Name: disclaimer disclaimer_pkey; Type: CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.disclaimer
    ADD CONSTRAINT disclaimer_pkey PRIMARY KEY (id);


--
-- Name: disclaimer_pratica disclaimer_pratica_pkey; Type: CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.disclaimer_pratica
    ADD CONSTRAINT disclaimer_pratica_pkey PRIMARY KEY (id);


--
-- Name: effetti_mitigazione effetti_mitigazione_pkey; Type: CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.effetti_mitigazione
    ADD CONSTRAINT effetti_mitigazione_pkey PRIMARY KEY (pratica_id);



--
-- Name: inquadramento inquadramento_pkey; Type: CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.inquadramento
    ADD CONSTRAINT inquadramento_pkey PRIMARY KEY (pratica_id);


--
-- Name: leggittimita leggittimita_pkey; Type: CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.leggittimita
    ADD CONSTRAINT leggittimita_pkey PRIMARY KEY (pratica_id);


--
-- Name: leggittimita_provvedimenti leggittimita_provv_pkey; Type: CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.leggittimita_provvedimenti
    ADD CONSTRAINT leggittimita_provv_pkey PRIMARY KEY (id);


--
-- Name: leggittimita_titoli leggittimita_titoli_pkey; Type: CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.leggittimita_titoli
    ADD CONSTRAINT leggittimita_titoli_pkey PRIMARY KEY (id);


--
-- Name: localizzazione_intervento localizzazione_intervento_pkey; Type: CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.localizzazione_intervento
    ADD CONSTRAINT localizzazione_intervento_pkey PRIMARY KEY (id);


--
-- Name: numero_pratica numero_pratica_pkey; Type: CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.numero_pratica
    ADD CONSTRAINT numero_pratica_pkey PRIMARY KEY (id);


--
-- Name: pareri_e_atti_assenso pareri_e_atti_assenso_pkey; Type: CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.pareri_e_atti_assenso
    ADD CONSTRAINT pareri_e_atti_assenso_pkey PRIMARY KEY (id);


--
-- Name: particelle_catastali particelle_catastali_pkey; Type: CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.particelle_catastali
    ADD CONSTRAINT particelle_catastali_pkey PRIMARY KEY (id);


--
-- Name: pptr_approvato pptr_approvato_pkey; Type: CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.pptr_approvato
    ADD CONSTRAINT pptr_approvato_pkey PRIMARY KEY (pratica_id);


--
-- Name: pptr_selezioni pptr_selezioni_pkey; Type: CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.pptr_selezioni
    ADD CONSTRAINT pptr_selezioni_pkey PRIMARY KEY (pratica_id);


--
-- Name: pratica pratica_pkey; Type: CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.pratica
    ADD CONSTRAINT pratica_pkey PRIMARY KEY (id);


--
-- Name: procedimenti_contenzioso procedimenti_contenzioso_pkey; Type: CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.procedimenti_contenzioso
    ADD CONSTRAINT procedimenti_contenzioso_pkey PRIMARY KEY (pratica_id);


--
-- Name: qualificazione_31_2017_values qualificazione_31_2017_values_pkey; Type: CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.qualificazione_31_2017_values
    ADD CONSTRAINT qualificazione_31_2017_values_pkey PRIMARY KEY (pratica_id, codice);


--
-- Name: qualificazione_42_2004_values qualificazione_42_2004_values_pkey; Type: CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.qualificazione_42_2004_values
    ADD CONSTRAINT qualificazione_42_2004_values_pkey PRIMARY KEY (pratica_id, codice);


--
-- Name: qualificazione_intervento qualificazione_intervento_pkey; Type: CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.qualificazione_intervento
    ADD CONSTRAINT qualificazione_intervento_pkey PRIMARY KEY (pratica_id);


--
-- Name: referenti_documento referenti_documento_pkey; Type: CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.referenti_documento
    ADD CONSTRAINT referenti_documento_pkey PRIMARY KEY (referente_id);


--
-- Name: referenti referenti_pkey; Type: CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.referenti
    ADD CONSTRAINT referenti_pkey PRIMARY KEY (id);


--
-- Name: richieste_abilitazione richieste_abilitazione_pkey; Type: CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.richieste_abilitazione
    ADD CONSTRAINT richieste_abilitazione_pkey PRIMARY KEY (id);


--
-- Name: ruolo_referente ruolo_referente_pkey; Type: CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.ruolo_referente
    ADD CONSTRAINT ruolo_referente_pkey PRIMARY KEY (id);


--
-- Name: tipo_contenuto tipi_allegati_pkey; Type: CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.tipo_contenuto
    ADD CONSTRAINT tipi_allegati_pkey PRIMARY KEY (type);


--
-- Name: tipi_e_qualificazioni tipi_e_qualificazioni_pkey; Type: CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.tipi_e_qualificazioni
    ADD CONSTRAINT tipi_e_qualificazioni_pkey PRIMARY KEY (codice);


--
-- Name: tipo_documento_identita tipo_documento_identita_pkey; Type: CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.tipo_documento_identita
    ADD CONSTRAINT tipo_documento_identita_pkey PRIMARY KEY (id);


--
-- Name: tipo_intervento tipo_intervento_pkey; Type: CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.tipo_intervento
    ADD CONSTRAINT tipo_intervento_pkey PRIMARY KEY (pratica_id);


--
-- Name: tipo_procedimento tipo_procedimento_pkey; Type: CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.tipo_procedimento
    ADD CONSTRAINT tipo_procedimento_pkey PRIMARY KEY (id);


--
-- Name: tipo_ruolo_ditta tipo_ruolo_ditta_pkey; Type: CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.tipo_ruolo_ditta
    ADD CONSTRAINT tipo_ruolo_ditta_pkey PRIMARY KEY (id);


--
-- Name: tno_pptr_strumenti_urbanistici tno_pptr_strumenti_urbanistici_pk; Type: CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.tno_pptr_strumenti_urbanistici
    ADD CONSTRAINT tno_pptr_strumenti_urbanistici_pk PRIMARY KEY (id);


--
-- Name: codice_pratica_appptr_idx; Type: INDEX; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE UNIQUE INDEX codice_pratica_appptr_idx ON presentazione_istanza.pratica USING btree (codice_pratica_appptr);



--
-- Name: idx_tipo_documento_identita_nome; Type: INDEX; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE UNIQUE INDEX idx_tipo_documento_identita_nome ON presentazione_istanza.tipo_documento_identita USING btree (nome);


--
-- Name: idx_tipo_documento_identita_order; Type: INDEX; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE UNIQUE INDEX idx_tipo_documento_identita_order ON presentazione_istanza.tipo_documento_identita USING btree ("order");


--
-- Name: idx_tipo_ruolo_ditta_nome; Type: INDEX; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE UNIQUE INDEX idx_tipo_ruolo_ditta_nome ON presentazione_istanza.tipo_ruolo_ditta USING btree (nome);


--
-- Name: idx_tipo_ruolo_ditta_order; Type: INDEX; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE UNIQUE INDEX idx_tipo_ruolo_ditta_order ON presentazione_istanza.tipo_ruolo_ditta USING btree ("order");


--
-- Name: unique_numero_pratica; Type: INDEX; Schema: presentazione_istanza; Owner: presentazione_istanza
--

CREATE UNIQUE INDEX unique_numero_pratica ON presentazione_istanza.numero_pratica USING btree (numero, anno);


--
-- Name: allegati_tipo_contenuto allegati_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.allegati_tipo_contenuto
    ADD CONSTRAINT allegati_fkey FOREIGN KEY (allegati_id) REFERENCES presentazione_istanza.allegati(id);


--
-- Name: referenti_documento allegati_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.referenti_documento
    ADD CONSTRAINT allegati_fkey FOREIGN KEY (allegato_id) REFERENCES presentazione_istanza.allegati(id) ON DELETE CASCADE NOT VALID;


--
-- Name: caratterizzazione_intervento_values codice_caratterizzazione; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.caratterizzazione_intervento_values
    ADD CONSTRAINT codice_caratterizzazione FOREIGN KEY (codice) REFERENCES presentazione_istanza.tipi_e_qualificazioni(codice);


--
-- Name: qualificazione_31_2017_values codice_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.qualificazione_31_2017_values
    ADD CONSTRAINT codice_fkey FOREIGN KEY (codice) REFERENCES presentazione_istanza.tipi_e_qualificazioni(codice);


--
-- Name: qualificazione_42_2004_values codice_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.qualificazione_42_2004_values
    ADD CONSTRAINT codice_fkey FOREIGN KEY (codice) REFERENCES presentazione_istanza.tipi_e_qualificazioni(codice);


--
-- Name: disclaimer_pratica disclaimer_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.disclaimer_pratica
    ADD CONSTRAINT disclaimer_fkey FOREIGN KEY (disclaimer_id) REFERENCES presentazione_istanza.disclaimer(id);


--
-- Name: qualificazione_intervento dpcm_12_2005_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.qualificazione_intervento
    ADD CONSTRAINT dpcm_12_2005_fkey FOREIGN KEY (dpcm_12_2005) REFERENCES presentazione_istanza.tipi_e_qualificazioni(codice);


--
-- Name: destinazione_urbanistica_intervento id_strum_urban_art100_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.destinazione_urbanistica_intervento
    ADD CONSTRAINT id_strum_urban_art100_fkey FOREIGN KEY (id_strum_urban_art100) REFERENCES presentazione_istanza.tno_pptr_strumenti_urbanistici(id) NOT VALID;


--
-- Name: destinazione_urbanistica_intervento id_strum_urban_art38_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.destinazione_urbanistica_intervento
    ADD CONSTRAINT id_strum_urban_art38_fkey FOREIGN KEY (id_strum_urban_art38) REFERENCES presentazione_istanza.tno_pptr_strumenti_urbanistici(id) NOT VALID;


--
-- Name: inquadramento inq_contesto_paesag_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.inquadramento
    ADD CONSTRAINT inq_contesto_paesag_fkey FOREIGN KEY (inq_contesto_paesag) REFERENCES presentazione_istanza.tipi_e_qualificazioni(codice);


--
-- Name: inquadramento inq_dest_uso_interv_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.inquadramento
    ADD CONSTRAINT inq_dest_uso_interv_fkey FOREIGN KEY (inq_dest_uso_interv) REFERENCES presentazione_istanza.tipi_e_qualificazioni(codice);


--
-- Name: inquadramento inq_dest_uso_suolo_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.inquadramento
    ADD CONSTRAINT inq_dest_uso_suolo_fkey FOREIGN KEY (inq_dest_uso_suolo) REFERENCES presentazione_istanza.tipi_e_qualificazioni(codice);


--
-- Name: inquadramento inq_morfologia_paesag_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.inquadramento
    ADD CONSTRAINT inq_morfologia_paesag_fkey FOREIGN KEY (inq_morfologia_paesag) REFERENCES presentazione_istanza.tipi_e_qualificazioni(codice);


--
-- Name: qualificazione_intervento istanza_rinnovo_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.qualificazione_intervento
    ADD CONSTRAINT istanza_rinnovo_fkey FOREIGN KEY (istanza_rinnovo) REFERENCES presentazione_istanza.tipi_e_qualificazioni(codice);


--
-- Name: qualificazione_intervento lieve_entita_rientrano_31_2017_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.qualificazione_intervento
    ADD CONSTRAINT lieve_entita_rientrano_31_2017_fkey FOREIGN KEY (lieve_entita_rientrano_31_2017) REFERENCES presentazione_istanza.tipi_e_qualificazioni(codice);


--
-- Name: destinazione_urbanistica_intervento localizzazione_intervento_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.destinazione_urbanistica_intervento
    ADD CONSTRAINT localizzazione_intervento_fkey FOREIGN KEY (localizzazione_intervento_id) REFERENCES presentazione_istanza.localizzazione_intervento(id) NOT VALID;


--
-- Name: particelle_catastali localizzazione_intervento_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.particelle_catastali
    ADD CONSTRAINT localizzazione_intervento_fkey FOREIGN KEY (localizzazione_intervento_id) REFERENCES presentazione_istanza.localizzazione_intervento(id);


--
-- Name: qualificazione_intervento non_ricadono_31_2017_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.qualificazione_intervento
    ADD CONSTRAINT non_ricadono_31_2017_fkey FOREIGN KEY (non_ricadono_31_2017) REFERENCES presentazione_istanza.tipi_e_qualificazioni(codice);


--
-- Name: pptr_selezioni pptr_selezioni_pratica_id_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.pptr_selezioni
    ADD CONSTRAINT pptr_selezioni_pratica_id_fkey FOREIGN KEY (pratica_id) REFERENCES presentazione_istanza.pratica(id) ON DELETE CASCADE;


--
-- Name: allegati pratica_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.allegati
    ADD CONSTRAINT pratica_fkey FOREIGN KEY (pratica_id) REFERENCES presentazione_istanza.pratica(id) ON DELETE CASCADE NOT VALID;


--
-- Name: altre_dichiarazioni_rich pratica_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.altre_dichiarazioni_rich
    ADD CONSTRAINT pratica_fkey FOREIGN KEY (pratica_id) REFERENCES presentazione_istanza.pratica(id) ON DELETE CASCADE;


--
-- Name: localizzazione_intervento pratica_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.localizzazione_intervento
    ADD CONSTRAINT pratica_fkey FOREIGN KEY (pratica_id) REFERENCES presentazione_istanza.pratica(id) ON DELETE CASCADE;


--
-- Name: tipo_intervento pratica_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.tipo_intervento
    ADD CONSTRAINT pratica_fkey FOREIGN KEY (pratica_id) REFERENCES presentazione_istanza.pratica(id) ON DELETE CASCADE;


--
-- Name: caratterizzazione_intervento pratica_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.caratterizzazione_intervento
    ADD CONSTRAINT pratica_fkey FOREIGN KEY (pratica_id) REFERENCES presentazione_istanza.pratica(id) ON DELETE CASCADE;


--
-- Name: qualificazione_intervento pratica_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.qualificazione_intervento
    ADD CONSTRAINT pratica_fkey FOREIGN KEY (pratica_id) REFERENCES presentazione_istanza.pratica(id) ON DELETE CASCADE;


--
-- Name: qualificazione_31_2017_values pratica_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.qualificazione_31_2017_values
    ADD CONSTRAINT pratica_fkey FOREIGN KEY (pratica_id) REFERENCES presentazione_istanza.pratica(id) ON DELETE CASCADE;


--
-- Name: qualificazione_42_2004_values pratica_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.qualificazione_42_2004_values
    ADD CONSTRAINT pratica_fkey FOREIGN KEY (pratica_id) REFERENCES presentazione_istanza.pratica(id) ON DELETE CASCADE;


--
-- Name: assogg_procedure_edilizie pratica_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.assogg_procedure_edilizie
    ADD CONSTRAINT pratica_fkey FOREIGN KEY (pratica_id) REFERENCES presentazione_istanza.pratica(id) ON DELETE CASCADE;


--
-- Name: leggittimita pratica_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.leggittimita
    ADD CONSTRAINT pratica_fkey FOREIGN KEY (pratica_id) REFERENCES presentazione_istanza.pratica(id) ON DELETE CASCADE;


--
-- Name: leggittimita_titoli pratica_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.leggittimita_titoli
    ADD CONSTRAINT pratica_fkey FOREIGN KEY (pratica_id) REFERENCES presentazione_istanza.pratica(id) ON DELETE CASCADE;


--
-- Name: leggittimita_provvedimenti pratica_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.leggittimita_provvedimenti
    ADD CONSTRAINT pratica_fkey FOREIGN KEY (pratica_id) REFERENCES presentazione_istanza.pratica(id) ON DELETE CASCADE;


--
-- Name: inquadramento pratica_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.inquadramento
    ADD CONSTRAINT pratica_fkey FOREIGN KEY (pratica_id) REFERENCES presentazione_istanza.pratica(id) ON DELETE CASCADE;


--
-- Name: effetti_mitigazione pratica_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.effetti_mitigazione
    ADD CONSTRAINT pratica_fkey FOREIGN KEY (pratica_id) REFERENCES presentazione_istanza.pratica(id) ON DELETE CASCADE;


--
-- Name: procedimenti_contenzioso pratica_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.procedimenti_contenzioso
    ADD CONSTRAINT pratica_fkey FOREIGN KEY (pratica_id) REFERENCES presentazione_istanza.pratica(id) ON DELETE CASCADE;


--
-- Name: pareri_e_atti_assenso pratica_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.pareri_e_atti_assenso
    ADD CONSTRAINT pratica_fkey FOREIGN KEY (pratica_id) REFERENCES presentazione_istanza.pratica(id) ON DELETE CASCADE;


--
-- Name: pptr_approvato pratica_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.pptr_approvato
    ADD CONSTRAINT pratica_fkey FOREIGN KEY (pratica_id) REFERENCES presentazione_istanza.pratica(id) ON DELETE CASCADE;


--
-- Name: comuni_competenza pratica_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.comuni_competenza
    ADD CONSTRAINT pratica_fkey FOREIGN KEY (pratica_id) REFERENCES presentazione_istanza.pratica(id) ON DELETE CASCADE;


--
-- Name: referenti pratica_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.referenti
    ADD CONSTRAINT pratica_fkey FOREIGN KEY (pratica_id) REFERENCES presentazione_istanza.pratica(id) ON DELETE CASCADE NOT VALID;


--
-- Name: disclaimer_pratica pratica_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.disclaimer_pratica
    ADD CONSTRAINT pratica_fkey FOREIGN KEY (pratica_id) REFERENCES presentazione_istanza.pratica(id) ON DELETE CASCADE;


--
-- Name: caratterizzazione_intervento_values pratica_id; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.caratterizzazione_intervento_values
    ADD CONSTRAINT pratica_id FOREIGN KEY (pratica_id) REFERENCES presentazione_istanza.pratica(id) ON DELETE CASCADE;


--
-- Name: referenti_documento referenti_documento_id_tipo; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.referenti_documento
    ADD CONSTRAINT referenti_documento_id_tipo FOREIGN KEY (id_tipo) REFERENCES presentazione_istanza.tipo_documento_identita(id);


--
-- Name: referenti_documento referenti_documento_referente; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.referenti_documento
    ADD CONSTRAINT referenti_documento_referente FOREIGN KEY (referente_id) REFERENCES presentazione_istanza.referenti(id) ON DELETE CASCADE;


--
-- Name: qualificazione_intervento ricadono_31_2017_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.qualificazione_intervento
    ADD CONSTRAINT ricadono_31_2017_fkey FOREIGN KEY (ricadono_31_2017) REFERENCES presentazione_istanza.tipi_e_qualificazioni(codice);


--
-- Name: allegati_tipo_contenuto tipo_contenuto_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.allegati_tipo_contenuto
    ADD CONSTRAINT tipo_contenuto_fkey FOREIGN KEY (tipo_contenuto_id) REFERENCES presentazione_istanza.tipo_contenuto(type);


--
-- Name: tipo_intervento tipo_intervento; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.tipo_intervento
    ADD CONSTRAINT tipo_intervento FOREIGN KEY (tipo_intervento) REFERENCES presentazione_istanza.tipi_e_qualificazioni(codice) NOT VALID;


--
-- Name: pratica tipo_procedimento_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.pratica
    ADD CONSTRAINT tipo_procedimento_fkey FOREIGN KEY (tipo_procedimento) REFERENCES presentazione_istanza.tipo_procedimento(id) NOT VALID;


--
-- Name: disclaimer tipo_procedimento_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.disclaimer
    ADD CONSTRAINT tipo_procedimento_fkey FOREIGN KEY (tipo_procedimento) REFERENCES presentazione_istanza.tipo_procedimento(id) NOT VALID;


--
-- Name: referenti tipo_ruolo_ditta_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.referenti
    ADD CONSTRAINT tipo_ruolo_ditta_fkey FOREIGN KEY (ditta_qualita_di) REFERENCES presentazione_istanza.tipo_ruolo_ditta(id) NOT VALID;


--
-- Name: referenti titolarita_fkey; Type: FK CONSTRAINT; Schema: presentazione_istanza; Owner: presentazione_istanza
--

ALTER TABLE ONLY presentazione_istanza.referenti
    ADD CONSTRAINT titolarita_fkey FOREIGN KEY (titolarita_id) REFERENCES presentazione_istanza.ruolo_referente(id) NOT VALID;


--
-- PostgreSQL database dump complete
--

