UPDATE presentazione_istanza.template_email
	SET oggetto='Fascicolo {CODICE_FASCICOLO} sollecito trasmissione parere'
	WHERE id_organizzazione=0 AND codice='14';
UPDATE presentazione_istanza.template_email
	SET oggetto='Fascicolo {CODICE_FASCICOLO} richiesta di chiarimenti alla Soprintendenza'
	WHERE id_organizzazione=0 AND codice='15';
UPDATE presentazione_istanza.template_email
	SET oggetto='Fascicolo {CODICE_FASCICOLO} richiesta di chiarimenti all''Ente territoriale'
	WHERE id_organizzazione=0 AND codice='16';
UPDATE presentazione_istanza.template_email
	SET oggetto='Fascicolo {CODICE_FASCICOLO} richiesta parere soprintendenza Tipo 1'
	WHERE id_organizzazione=0 AND codice='17';
UPDATE presentazione_istanza.template_email
	SET oggetto='Fascicolo {CODICE_FASCICOLO} richiesta parere soprintendenza Tipo 2'
	WHERE id_organizzazione=0 AND codice='18';
UPDATE presentazione_istanza.template_email
	SET oggetto='Fascicolo {CODICE_FASCICOLO} comunicazione generica soprintendenza'
	WHERE id_organizzazione=0 AND codice='GEN_SOP';
UPDATE presentazione_istanza.template_email
	SET oggetto='Fascicolo {CODICE_FASCICOLO} comunicazione generica ente delegato'
	WHERE id_organizzazione=0 AND codice='GEN_ED';
UPDATE presentazione_istanza.template_email
	SET oggetto='Fascicolo {CODICE_FASCICOLO} Comunicazione'
	WHERE id_organizzazione=0 AND codice='GENERIC_TER';
UPDATE presentazione_istanza.template_email
	SET oggetto='Fascicolo {CODICE_FASCICOLO} richiesta di integrazioni'
	WHERE id_organizzazione=0 AND codice='13';
UPDATE presentazione_istanza.template_email
	SET oggetto='Fascicolo {CODICE_FASCICOLO} in attesa '
	WHERE id_organizzazione=0 AND codice='RICH_PAR_SOP_31';
UPDATE presentazione_istanza.template_email
	SET oggetto='Fascicolo {CODICE_FASCICOLO} in attesa '
	WHERE id_organizzazione=0 AND codice='RICH_PAR_SOP_32';