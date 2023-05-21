 UPDATE tipi_e_qualificazioni SET "label"=replace("label" ,'\n','<br>')  where "label" like '%\\n%';
 UPDATE tipi_e_qualificazioni SET "label"=replace("label" ,'\"','"')  where "label" like '%\\"%';