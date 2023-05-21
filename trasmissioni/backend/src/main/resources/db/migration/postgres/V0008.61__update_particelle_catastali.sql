UPDATE particelle_catastali set elaborato='C' WHERE oid is not null;
UPDATE particelle_catastali set elaborato='I' WHERE oid is null;