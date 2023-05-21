UPDATE presentazione_istanza.tariffa SET percentuale_finale=100;


UPDATE presentazione_istanza.tariffa
	SET delete_eccedente=true
	WHERE soglia_minima IN (200000, 5000000, 20000000);
	
UPDATE presentazione_istanza.tariffa
	SET cifra_da_aggiungere=100
	WHERE soglia_minima=200000;	