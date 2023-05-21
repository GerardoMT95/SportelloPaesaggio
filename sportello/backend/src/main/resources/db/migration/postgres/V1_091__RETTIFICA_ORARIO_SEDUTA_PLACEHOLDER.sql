-- Auto-generated SQL script #202011171013
UPDATE presentazione_istanza.template_email
	SET testo='<div>
				<p>
					Con la presente si comunica la convocazione per la seduta di commissione ''{NOME_SEDUTA}'' che si terrà in data {DATA_SEDUTA}
					alle ore {ORARIO_SEDUTA}.
				</p>
				<p>
					Il Responsabile del Procedimento <br />
					(arch./ing./ geom. {RUP})
				</p>
		 </div>'
	WHERE codice='CONV_SED_COM';
	
UPDATE presentazione_istanza.template_email
	SET testo='<div>
				<p>
					Con la presente si comunica l''aggiornamento della data per la seduta di commissione ''{NOME_SEDUTA}'' al  {DATA_SEDUTA}
					alle ore {ORARIO_SEDUTA}.
				</p>
				<p>
					Il Responsabile del Procedimento <br />
					(arch./ing./ geom. {RUP})
				</p>
		 </div>'
	WHERE codice='AGG_SED_COM';	
