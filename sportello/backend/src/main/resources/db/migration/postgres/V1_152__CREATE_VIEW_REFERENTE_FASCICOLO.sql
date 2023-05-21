CREATE OR REPLACE VIEW v_referente_fascicolo as
SELECT
	p.id,
	r.nome ,
	r.cognome ,
	r.pec ,
	r.mail ,
	r.codice_fiscale,
	r.tipo_referente
FROM
	presentazione_istanza.pratica p ,
	presentazione_istanza.referenti r
WHERE
	r.pratica_id = p.id
UNION 
SELECT
	p.id,
	pd.nome ,
	pd.cognome ,
	pd.pec ,
	pd.mail ,
	pd.codice_fiscale,
	'DE'
FROM
	presentazione_istanza.pratica_delegato pd,
	presentazione_istanza.pratica p
WHERE
	pd.id = p.id
	AND pd.delgato_corrente = true;

comment ON VIEW v_referente_fascicolo IS 'View per visualizzare i referenti per fascicolo';