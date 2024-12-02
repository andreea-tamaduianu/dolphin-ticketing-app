
-- select * from dolphin.liste_def 
select 
 utilizatori.id_utiliz,
 utilizatori.cod_utiliz,
 utilizatori.nume_utiliz,
 utilizatori.prenume_utiliz,
 utilizatori.id_rol_utiliz,
 
 rol.cod_lista rol_utiliz,
 rol.den_lista den_rol_utiliz,
 
 utilizatori.id_stare,
 stare.cod_lista stare,
 stare.den_lista den_stare,
 
 utilizatori.id_echipa,
 stare.cod_lista echipa,
 stare.den_lista den_echipa,
 
 utilizatori.email
from 
 dolphin.utilizatori,
 dolphin.liste_def stare,
 dolphin.liste_def echipa,
 dolphin.liste_def rol
where 
 stare.id_lista_def = utilizatori.id_stare   
 and echipa.id_lista_def = utilizatori.id_echipa
 and rol.id_lista_def = utilizatori.id_rol_utiliz
  
