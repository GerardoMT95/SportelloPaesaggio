CREATE TABLE IF NOT EXISTS "common"."sezioni_catastali"
(
    cod_catastale character varying(10) NOT NULL,
    sezione character varying(5) NOT NULL,
    descrizione character varying(80),
    PRIMARY KEY (cod_catastale, sezione)
);

ALTER TABLE "common"."sezioni_catastali"
    OWNER to aet_user;
COMMENT ON TABLE "common"."sezioni_catastali"
    IS 'tabella contenente le sezioni catastali con eventuale codifica utilizzata nel pannello localizzazione ';

INSERT INTO common.sezioni_catastali(cod_catastale, sezione, descrizione) VALUES ('A662','A','Bari') on conflict do nothing;
INSERT INTO common.sezioni_catastali(cod_catastale, sezione, descrizione) VALUES ('A662','B','Carbonara') on conflict do nothing;
INSERT INTO common.sezioni_catastali(cod_catastale, sezione, descrizione) VALUES ('A662','C','Ceglie del Campo') on conflict do nothing;
INSERT INTO common.sezioni_catastali(cod_catastale, sezione, descrizione) VALUES ('A662','D','Loseto') on conflict do nothing;
INSERT INTO common.sezioni_catastali(cod_catastale, sezione, descrizione) VALUES ('A662','E','Palese') on conflict do nothing;
INSERT INTO common.sezioni_catastali(cod_catastale, sezione, descrizione) VALUES ('A662','F','Santo Spirito') on conflict do nothing;
INSERT INTO common.sezioni_catastali(cod_catastale, sezione, descrizione) VALUES ('A662','G','Torre a Mare') on conflict do nothing;
INSERT INTO common.sezioni_catastali(cod_catastale, sezione, descrizione) VALUES ('L049','A','Taranto') on conflict do nothing;
INSERT INTO common.sezioni_catastali(cod_catastale, sezione, descrizione) VALUES ('L049','B','San Demetrio') on conflict do nothing;
INSERT INTO common.sezioni_catastali(cod_catastale, sezione, descrizione) VALUES ('L049','C','Morroni') on conflict do nothing;
INSERT INTO common.sezioni_catastali(cod_catastale, sezione, descrizione) VALUES ('M428','A','Presicce') on conflict do nothing;
INSERT INTO common.sezioni_catastali(cod_catastale, sezione, descrizione) VALUES ('M428','B','Acquarica del capo') on conflict do nothing;
