UPDATE procedimento_qualificazioni set escluso_eti=true 
where 
id_tipi_qualificazioni in (212,213,214,216,217,218)
and codice_tipo_procedimento in ('PUTT_X','PUTT_DLGS_42_2004');