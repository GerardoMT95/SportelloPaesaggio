CREATE OR REPLACE VIEW presentazione_istanza.v_cds_modalita
AS SELECT modalita.codice AS value,
    modalita.descrizione AS label
   FROM cdst.modalita;
   
CREATE OR REPLACE VIEW presentazione_istanza.v_cds
AS SELECT c.id_conferenza AS id,
    c.riferimento_istanza,
        CASE
            WHEN s.descrizione::text = 'stato.compilazione'::text THEN 'Compilazione'::text
            WHEN s.descrizione::text = 'stato.bozza'::text THEN 'Bozza'::text
            WHEN s.descrizione::text = 'stato.valutazione'::text THEN 'Valutazione'::text
            WHEN s.descrizione::text = 'stato.chiusa'::text THEN 'Chiusa'::text
            WHEN s.descrizione::text = 'stato.archiviata'::text THEN 'Archiviata'::text
            ELSE '-'::text
        END AS stato,
    c.audit_crt_time AS data_creazione
   FROM cdst.conferenza c
     JOIN cdst.stato s ON c.fk_stato::text = s.codice::text;
     


create table pratica_documentazione_cds
(id               varchar(36) not null
,id_pratica   uuid not null
,id_tipo          integer not null
,id_documento_cds bigint not null
,user_create      varchar(100) not null
,date_create      timestamp not null default current_timestamp
);

comment on table pratica_documentazione_cds is 'Documenti migrati da CDS a CDP';
comment on column pratica_documentazione_cds.id                is 'PK';
comment on column pratica_documentazione_cds.id_pratica    is 'FK su pratica';
comment on column pratica_documentazione_cds.id_tipo           is 'FK su tipo';
comment on column pratica_documentazione_cds.id_documento_cds  is 'Id documento cds';
comment on column pratica_documentazione_cds.user_create       is 'Utente creazione';
comment on column pratica_documentazione_cds.date_create       is 'Data Creazione';


ALTER TABLE pratica_documentazione_cds ADD CONSTRAINT pratica_documentazione_cds_fk_1 FOREIGN KEY (id_pratica) REFERENCES pratica(id);
ALTER TABLE pratica_documentazione_cds ADD CONSTRAINT pratica_documentazione_cds_fk_2 FOREIGN KEY (id_tipo) REFERENCES tipo_contenuto(id);

CREATE INDEX pratica_documentazione_cds_idx_1 on pratica_documentazione_cds(id_documento_cds);
CREATE INDEX pratica_documentazione_cds_idx_2 on pratica_documentazione_cds(id_pratica);
CREATE INDEX pratica_documentazione_cds_idx_3 on pratica_documentazione_cds(id_tipo);

CREATE OR REPLACE VIEW presentazione_istanza.v_cds_documentazione
AS SELECT d.fk_conferenza AS id_conferenza,
    c.riferimento_istanza AS riferimento_conferenza,
        CASE
            WHEN s.descrizione::text = 'stato.compilazione'::text THEN 'Compilazione'::text
            WHEN s.descrizione::text = 'stato.bozza'::text THEN 'Bozza'::text
            WHEN s.descrizione::text = 'stato.valutazione'::text THEN 'Valutazione'::text
            WHEN s.descrizione::text = 'stato.chiusa'::text THEN 'Chiusa'::text
            WHEN s.descrizione::text = 'stato.archiviata'::text THEN 'Archiviata'::text
            ELSE '-'::text
        END AS stato_conferenza,
    rd.id,
    d.nome,
        CASE
            WHEN td.descrizione::text = 'tipologiaDocumento.documentazioneAllegata'::text THEN 'Documentazione allegata'::text
            WHEN td.descrizione::text = 'tipologiaDocumento.documentazioneAggiuntiva'::text THEN 'Documentazione aggiuntiva'::text
            WHEN td.descrizione::text = 'tipologiaDocumento.interazioni'::text THEN 'Documentazione interazioni'::text
            WHEN td.descrizione::text = 'tipologiaDocumento.interazioni.richiestaIntegrazioni'::text THEN 'Documentazione interazioni fase richiesta integrazioni'::text
            WHEN td.descrizione::text = 'tipologiaDocumento.interazioni.espressioniParere'::text THEN 'Documentazione interazioni fase espressioni parere'::text
            WHEN td.descrizione::text = 'tipologiaDocumento.interazioni.determinaFinale'::text THEN 'Documentazione interazioni fase determina finale'::text
            WHEN td.descrizione::text = 'tipologiaDocumento.documentoIndizione'::text THEN 'Documento di indizione'::text
            WHEN td.descrizione::text = 'tipologiaDocumento.documentoAccreditamento'::text THEN 'Documento di accreditamento'::text
            WHEN td.descrizione::text = 'tipologiaDocumento.comunicazioneGenerica'::text THEN 'Comunicazione Generica'::text
            WHEN td.descrizione::text = 'tipologiaDocumento.verbaleRiunione'::text THEN 'Verbale della Riunione'::text
            WHEN td.descrizione::text = 'tipologiaDocumento.determina'::text THEN 'Determinazione finale'::text
            WHEN td.descrizione::text = 'tipologiaDocumento.video'::text THEN 'Video'::text
            WHEN td.descrizione::text = 'tipologiaDocumento.documentiCondivisi'::text THEN 'Documento Condiviso'::text
            WHEN td.descrizione::text = 'tipologiaDocumento.documentoFirmato'::text THEN 'Documento Firmato'::text
            ELSE '---'::text
        END AS tipo,
        CASE
            WHEN cd.descrizione::text = 'categoriaDocumento.documentazioneCorredoIstanza'::text THEN '---'::text
            WHEN cd.descrizione::text = 'categoriaDocumento.istanza'::text THEN 'Istanza'::text
            WHEN cd.descrizione::text = 'categoriaDocumento.documentoIntegrativo'::text THEN 'Documento Integrativo'::text
            WHEN cd.descrizione::text = 'categoriaDocumento.nota'::text THEN 'Nota'::text
            WHEN cd.descrizione::text = 'categoriaDocumento.altro'::text THEN 'Altro'::text
            WHEN cd.descrizione::text = 'categoriaDocumento.comune'::text THEN 'Documentazione per Comune'::text
            WHEN cd.descrizione::text = 'categoriaDocumento.provincia'::text THEN 'Documentazione per Provincia'::text
            WHEN cd.descrizione::text = 'categoriaDocumento.richiestaIntegrazioni'::text THEN 'Richiesta integrazioni'::text
            WHEN cd.descrizione::text = 'categoriaDocumento.parere'::text THEN 'Parere'::text
            WHEN cd.descrizione::text = 'categoriaDocumento.richiestaUnicaIntegrazioni'::text THEN 'Richiesta unica integrazioni'::text
            WHEN cd.descrizione::text = 'categoriaDocumento.documentazioneIntegrativa'::text THEN 'Documentazione integrativa'::text
            WHEN cd.descrizione::text = 'categoriaDocumento.verbaleConferenza'::text THEN 'Verbale conferenza'::text
            WHEN cd.descrizione::text = 'categoriaDocumento.determinazioneFinale'::text THEN 'Determinazione finale'::text
            WHEN cd.descrizione::text = 'categoriaDocumento.documentoIndizione'::text THEN 'Documento di Indizione'::text
            WHEN cd.descrizione::text = 'categoriaDocumento.documentoCondiviso'::text THEN 'Documento Condiviso'::text
            WHEN cd.descrizione::text = 'categoriaDocumento.videoRiunione'::text THEN 'Video della riunione'::text
            WHEN cd.descrizione::text = 'categoriaDocumento.documentoFirmato'::text THEN 'Documento Firmato'::text
            WHEN cd.descrizione::text = 'categoriaDocumento.appunti'::text THEN 'Appunti'::text
            ELSE '---'::text
        END AS categoria,
    rd.rif_esterno,
    p.numero_protocollo AS protocollo,
    rd.data AS date_create
   FROM cdst.documento d
     JOIN cdst.registro_documento rd ON d.id_documento = rd.fk_documento
     LEFT JOIN cdst.tipologia_documento td ON td.codice::text = d.fk_tipologia_documento::text
     LEFT JOIN cdst.categoria_documento cd ON cd.codice::text = d.fk_categoria_documento::text
     JOIN cdst.conferenza c ON c.id_conferenza = d.fk_conferenza
     JOIN cdst.stato s ON c.fk_stato::text = s.codice::text
     LEFT JOIN cdst.protocollo p ON p.id_documento = d.id_documento
  WHERE (rd.tipo::text = ANY (ARRAY['FS'::character varying, 'ALFRESCO'::character varying]::text[])) AND rd.id = (( SELECT max(rd_inner.id) AS max
           FROM cdst.registro_documento rd_inner
          WHERE rd_inner.fk_documento = d.id_documento AND (rd_inner.tipo::text = ANY (ARRAY['FS'::character varying, 'ALFRESCO'::character varying]::text[])))) AND d.esterno = false AND (s.descrizione::text <> ALL (ARRAY['stato.compilazione'::character varying, 'stato.bozza'::character varying]::text[]));
  