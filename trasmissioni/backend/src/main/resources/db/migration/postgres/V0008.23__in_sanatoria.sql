-- in sanatoria va a questi..
UPDATE tipo_procedimento
	SET sanatoria=false
	WHERE codice='ACCERT_COMPAT_PAES_DLGS_42_2004';
UPDATE tipo_procedimento
	SET sanatoria=true
	WHERE codice='ACCERT_COMPAT_PAES_DPR_31_2017';
