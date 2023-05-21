-- tolto invio mail a SBAP in caso di pareri ETI6-71
UPDATE tipo_procedimento
SET invia_email=false
WHERE codice='PARERE_COMP_96D';
