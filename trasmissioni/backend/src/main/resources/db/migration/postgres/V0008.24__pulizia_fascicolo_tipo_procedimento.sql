DELETE FROM fascicolo_tipi_procedimento where 
id_fascicolo in (
    SELECT id from fascicolo where data_creazione>fine_validita or data_creazione<inizio_validita);
 