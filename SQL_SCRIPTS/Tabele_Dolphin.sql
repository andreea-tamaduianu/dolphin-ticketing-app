create user DOLPHIN  identified by DOLPHIN

GRANT ALL PRIVILEGES TO DOLPHIN;
GRANT  ALTER tables TO DOLPHIN;
grant create view, create procedure, create sequence to DOLPHIN;


drop table DOLPHIN.UTILIZATORI;
create table DOLPHIN.UTILIZATORI (
  ID_UTILIZ number(10) not null, 
  COD_UTILIZ varchar2(10) not null ,
  NUME_UTILIZ varchar2(40) not null,
  ID_ROL_UTILIZ number(10) not null, -- Se va completa cu valori din LISTE_DEF pentru codul TIP_LISTA = ROLURI
  PAROLA varchar2(20) not null,
  ID_STARE number(10) not null, -- Se va completa cu valori din LISTE_DEF pentru codul TIP_LISTA = STARI_U
  PRENUME_UTILIZ varchar2(40) not null,
  ID_ECHIPA number(10) not null -- Se va completa cu valori din LISTE_DEF pentru codul TIP_LISTA = ECHIPE
);


ALTER TABLE DOLPHIN.UTILIZATORI add email varchar2(40);


ALTER TABLE DOLPHIN.UTILIZATORI ADD CONSTRAINT
PK_UTILIZATORI PRIMARY KEY (ID_UTILIZ);

ALTER TABLE DOLPHIN.UTILIZATORI ADD CONSTRAINT
UK_UTILIZATORI UNIQUE (COD_UTILIZ); 

select * from DOLPHIN.utilizatori

select  * from  DOLPHIN.UTILIZATORI where  COD_UTILIZ = 'ADMIN' 

35	8 	DBA
36	9 	ADMIN
37	10	ACTIV

INSERT INTO DOLPHIN.UTILIZATORI (id_utiliz, cod_utiliz, nume_utiliz, id_rol_utiliz, parola, id_stare, prenume_utiliz, id_echipa)
VALUES(1,'ADMIN','ADMINISTRATOR',26,'1234', 37, 'ADMIN', 35);

INSERT INTO DOLPHIN.UTILIZATORI (id_utiliz, cod_utiliz, nume_utiliz, id_rol_utiliz, parola, id_stare, prenume_utiliz, id_echipa)
VALUES(2,'OPERATOR','OPERATOR CALL CENTER',26,'1234', 37, 'Operator', 35); -- OPER = LEVEL1

INSERT INTO DOLPHIN.UTILIZATORI (id_utiliz, cod_utiliz, nume_utiliz, id_rol_utiliz, parola, id_stare)
VALUES(3,'USER1','USER 1','LEVEL2','1234', 0); --
INSERT INTO DOLPHIN.UTILIZATORI 
VALUES(4,'USER2','USER 2','LEVEL2','1234', 0);
INSERT INTO DOLPHIN.UTILIZATORI 
VALUES(5,'USER3','USER 3','LEVEL2','1234', 0);

-- commit

/*
-- Am scos tabela Echipe 

drop table DOLPHIN.ECHIPE;
create table DOLPHIN.ECHIPE (
  ID_ECHIPA number(10) not null, 
  COD_ECHIPA varchar2(10) not null, -- Se va completa cu valori din   LISTE_DEF pentru codul TIP_LISTA = ECHIPE
  ID_UTILIZATOR number(10) not null    
);

ALTER TABLE DOLPHIN.ECHIPE ADD CONSTRAINT
PK_ECHIPE PRIMARY KEY (ID_ECHIPA)  ;

ALTER TABLE DOLPHIN.ECHIPE ADD CONSTRAINT
FK_ECHIPE_UTILIZ foreign KEY (ID_UTILIZATOR) 
REFERENCES DOLPHIN.UTILIZATORI (ID_UTILIZ) ENABLE;

*/


/*
drop table DOLPHIN.TICHETE_LOG;
drop table DOLPHIN.TICHETE_DET;
drop table DOLPHIN.TICHETE;

drop table DOLPHIN.LISTE_DEF;
drop table DOLPHIN.LISTE;
*/

create table DOLPHIN.LISTE (
  ID_LISTA number(10) not null, 
  TIP_LISTA varchar2(10) not null , 
  DEN_TIP_LISTA varchar2(100) not null    
);

ALTER TABLE DOLPHIN.LISTE ADD CONSTRAINT
PK_LISTE PRIMARY KEY (ID_LISTA)  ;

create table DOLPHIN.LISTE_DEF (
  ID_LISTA_DEF number(10) not null,
  ID_LISTA number(10) not null, 
  COD_LISTA varchar2(10) not null , 
  DEN_LISTA varchar2(100) not null    
);

ALTER TABLE DOLPHIN.LISTE_DEF ADD ORDER_NO number(5);


ALTER TABLE DOLPHIN.LISTE_DEF ADD CONSTRAINT
PK_LISTE_DEF PRIMARY KEY (ID_LISTA_DEF)  ;

ALTER TABLE DOLPHIN.LISTE_DEF ADD CONSTRAINT
UK_LISTE_DEF UNIQUE (ID_LISTA, COD_LISTA)  ;


ALTER TABLE DOLPHIN.LISTE_DEF ADD CONSTRAINT
FK_LISTE_DEF foreign KEY (ID_LISTA) 
 REFERENCES DOLPHIN.LISTE (ID_LISTA) ENABLE;
 
-- drop  sequence DOLPHIN.SEQ_TICHET 
create sequence DOLPHIN.SEQ_TICHET start with 10000;

create sequence DOLPHIN.SEQ_UTILIZ start with 100;

CREATE SEQUENCE SEQ_LISTE_DEF INCREMENT BY 1 START WITH 1 MAXVALUE 9999 MINVALUE 1;



select DOLPHIN.SEQ_TICHET.nextval PK_VAL from dual

 select * from DOLPHIN.TICHETE 
 
create table DOLPHIN.TICHETE (
  ID_TICHET number(10) not null, 
  NR_TICHET number(10) not null,
  NUME_APARMA varchar2(20) not null,
  ID_PRIORITATE  number(10)  not null, -- P1, P2, sau P3, P4
  
  ID_ENTITATE_ACTIVA number(10) ,
  DESCRIERE varchar2(400),
  
  ID_SERVICIU number(10) ,
  ID_COMPONENTA number(10) ,
  
  ID_STARE number(10) 
  
);

ALTER TABLE DOLPHIN.TICHETE 
RENAME COLUMN NUME_APARMA TO NUME_ALARMA
/

select * from DOLPHIN.TICHETE

ALTER TABLE DOLPHIN.TICHETE ADD CONSTRAINT
PK_TICHETE PRIMARY KEY (ID_TICHET)  ;

ALTER TABLE DOLPHIN.TICHETE ADD CONSTRAINT
UK_TICHETE UNIQUE (NR_TICHET)  ;

-- TIP_LISTA  = STARI
ALTER TABLE DOLPHIN.TICHETE ADD CONSTRAINT
FK_TICHETE_STARE foreign KEY (ID_STARE) 
REFERENCES DOLPHIN.LISTE_DEF (ID_LISTA_DEF) ENABLE;

-- TIP_LISTA  = PRIORITATI
ALTER TABLE DOLPHIN.TICHETE ADD CONSTRAINT
FK_TICHETE_PRIORITATE foreign KEY (ID_PRIORITATE) 
REFERENCES DOLPHIN.LISTE_DEF (ID_LISTA_DEF) ENABLE;

-- TIP_LISTA  = ENTITATI
ALTER TABLE DOLPHIN.TICHETE ADD CONSTRAINT
FK_TICHETE_ENTITATE foreign KEY (ID_ENTITATE_ACTIVA) 
REFERENCES DOLPHIN.LISTE_DEF (ID_LISTA_DEF) ENABLE;

-- TIP_LISTA  = SERVICII
ALTER TABLE DOLPHIN.TICHETE ADD CONSTRAINT
FK_TICHETE_SERVICIU foreign KEY (ID_SERVICIU) 
REFERENCES DOLPHIN.LISTE_DEF (ID_LISTA_DEF) ENABLE;

-- TIP_LISTA  = COMPONENTE
ALTER TABLE DOLPHIN.TICHETE ADD CONSTRAINT
FK_TICHETE_COMPONENTA foreign KEY (ID_COMPONENTA) 
REFERENCES DOLPHIN.LISTE_DEF (ID_LISTA_DEF) ENABLE;

drop table DOLPHIN.TICHETE_DET;
create table DOLPHIN.TICHETE_DET (
  ID_TICHET_DET number(10) not null,
  ID_TICHET number(10) not null, 
  ID_TIP_DET number(10), 
  DESCRIERE varchar2(400),
  ID_UTILIZ number(10),
  DATA_ACT date
);

ALTER TABLE DOLPHIN.TICHETE_DET ADD CONSTRAINT
PK_TICHETE_DET PRIMARY KEY (ID_TIP_DET);


ALTER TABLE DOLPHIN.TICHETE_DET ADD CONSTRAINT
FK_TICHETE_DET_TICHET foreign KEY (ID_TICHET) 
REFERENCES DOLPHIN.TICHETE (ID_TICHET) ENABLE;

-- TIP_LISTA  = TIPDET
ALTER TABLE DOLPHIN.TICHETE_DET ADD CONSTRAINT
FK_TICHETE_DET_TIPDET foreign KEY (ID_TIP_DET) 
REFERENCES DOLPHIN.LISTE_DEF (ID_LISTA_DEF) ENABLE;

drop  table DOLPHIN.TICHETE_LOG 
select * from DOLPHIN.TICHETE_LOG
create table DOLPHIN.TICHETE_LOG (
  ID_TICHET_LOG number(10) not null,
  ID_TICHET number(10) not null,
  ID_UTILIZ number(10) not null,
   
  ID_TIP_OPER number(10), 
  ID_STARE number(10),
  DATA_START date,
  DATA_END date,
  OVERTIME number(2),
  DESCRIERE varchar2(400)    
);

create sequence DOLPHIN.SEQ_LOG_TICHETE;

select DOLPHIN.SEQ_LOG_TICHETE.NEXTVAL from dual 

ALTER TABLE DOLPHIN.TICHETE_LOG ADD CONSTRAINT
PK_TICHETE_LOG PRIMARY KEY (ID_TICHET_LOG)  ;

ALTER TABLE DOLPHIN.TICHETE_LOG ADD CONSTRAINT
FK_TICHETE_LOG_UTILIZ foreign KEY (ID_UTILIZ) 
REFERENCES DOLPHIN.UTILIZATORI (ID_UTILIZ) ENABLE;

ALTER TABLE DOLPHIN.TICHETE_LOG ADD CONSTRAINT
FK_TICHETE_LOG_TICHET foreign KEY (ID_TICHET) 
REFERENCES DOLPHIN.TICHETE (ID_TICHET) ENABLE;

-- TIP_LISTA  = TIPOPER
ALTER TABLE DOLPHIN.TICHETE_LOG ADD CONSTRAINT
FK_TICHETE_DET_TIPOPER foreign KEY (ID_TIP_OPER) 
REFERENCES DOLPHIN.LISTE_DEF (ID_LISTA_DEF) ENABLE;

-- TIP_LISTA  = STARI
ALTER TABLE DOLPHIN.TICHETE_LOG ADD CONSTRAINT
FK_TICHETE_DET_STARE foreign KEY (ID_STARE) 
REFERENCES DOLPHIN.LISTE_DEF (ID_LISTA_DEF) ENABLE;


 utilizatori, 
 echipe, 
 servicii (ex: oferte tarifare, vânzare, rezervare)  
 componente ( ex: baza de date, sistem de operare, network ?i aplica?ie).
 
 ALARMA 
 incident - Minor
 incident - Major
 
 Când incidentul nu apare în timpul unei opera?iuni, 
 se creeaza un tichet folosind informa?iile primite odata cu activarea 
 alarmei( prioritate, nume alarma ?i entitate activata).
 Daca prioritatea incidentului este P1 sau P2, se activeaza telefonic ?i prin email 
 echipele de la Level 2 ( DBA, SysAdmin, Application ?i Networking) pentru a rezolva problema.
  
 De men?ionat este ca pe un incident poate lucra o echipa sau pot lucra mai multe. 
 Ace?tia vor folosi aplica?ia pentru a oferi în timp real informa?ii despre pa?ii parcur?i pentru a rezolva un incident
 
 La final, dupa ce un incident a fost rezolvat, se vor completa informa?iile legate de cauza problemei, 
 metodele prin care acesta s-a rezolvat 
 ?i eventualele ac?iuni care se pot lua pentru a preîntâmpina incidentul. 
 Tichetul ?i alarma se închid.
 
 Daca prioritatea incidentului este alta (P3 sau P4) ?i exista o procedura de rezolvare, se aplica aceasta procedura.
 
 Daca problema se rezolva aplicând procedura, se închid alarma ?i tichetul. 
 Altfel, se contacteaza echipele de la Level 2 care vor rezolva problema, 
 vor completa datele aferente rezolvarii incidentului ?i vor închide tichetul
 
 Al doilea, când incidentul apare în timpul unei opera?iuni care a fost planificata, 
 se a?teapta pâna ce opera?iunea respectiva se finalizeaza. 
 Daca dupa ce se termina opera?iunea apar incidente, 
 se va crea un tichet ?i se vor executa opera?iile descrise mai sus în func?ie de situa?ie
 
 
 Cerin?ele de baza pe care trebuie sa le realizeze sistemul informatic sunt:
 -	Logarea în cadrul aplica?iei
 -	Posibilitatea crearii unui nou tichet 

 -	Echipele de la Level 2 pot prelua un tichet, îl pot completa cu informa?iile necesare rezolvarii incidentului aferent acestuia ?i îl pot închide
 -	Echipele de la Level 2 dispun de op?iunea de a completa toate ac?iunile care au fost facute pentru rezolvarea tichetului(prin comentarii)

 -	Administratorul de aplica?ie poate introduce utilizatori, echipe, servicii ?i componente

 -	În cazul în care incidentul apare în afara orelor normale de lucru, inginerul care a fost activat are posibilitatea sa introduca timpul petrecut pentru rezolvarea tichetului( overtime )

 -	Daca avem mai multe echipe ?i fiecare echipa are mai mul?i membri, exista op?iunea  pentru stabilirea perioadei în care inginerii vor fi de serviciu


 
modifice starea tichetului din “Open” in “Acknowledged”


, el completeaza in sectiunea de comentarii pasii prin care trece in acest proces. 
Dupa, va completa in sectiunea de “Detalii tichet” numele alarmei, 
denumirea serviciului( ex: vanzari bilete, rezolvare bilete) 
unde a aparut problema, 
serverul pe care a aparut aceasta, 
componenta pe care a aparut ( baza de date, aplicatie, sistem de operare, retea), 
va stabili prioritatea tichetului, 
va face o scurta descriere a problemei, 
descriere orientata catre client si o descriere in mare a pasilor de rezolvare. 
Dupa aceasta, va apasa butonul “Save”. 
Daca campurile serviciul, serverul, componenta, prioritatea si descrierea pasilor de 
rezolvare sunt completate, inginerul va putea modifica starea 
tichetului in “Resolved”, altfel, va primi un mesaj de eroare si 
va trebui sa completeze campurile lipsa pentru a putea trece mai departe.

Daca inginerul a rezolvat tichetul in afara programului de lucru, 
are posibilitatea de a-si adauga orele suplimentare. 

Pe urma, angajatul de la departamentul de Front Office verifica daca dupa rezolvarea incidentului 
mai apar alte alarme referitoare la componentele tratate in tichet. 
Daca da, tehnicianul va adauga in sectiunea de comentarii acest lucru, 
va modifica starea tichetului in “Open” si va retrimite tichetul inapoi in coada. 

In caz contrar, tehnicianul va schimba starea incidentului in “Closed”.


Crearea unui tichet
In momentul in care apare o alarma tehnicianul va fi nevoit sa creeze un tichet. Creearea unui tichet se face, in primul rand, accesand pagina de creare tichete din meniul aplicatiei. Dupa accea, acesta, folosindu-se de detaliile din alarma, va trebui sa completeze urmatoarele campuri: prioritate, denumire alarma, descriere si entitate activata. Pe urma, va apasa pe butonul “Save”. Daca vreunul din campurile prioritate, denumire alarma sau entitate activata nu au fost completate, utilizatorul va primi un mesaj de eroare care il va anunta ce campuri mai are de completat. 

Apoi, sistemul ii va aloca tichetului un id unic, ii va seta starea pe modul “Open”, va salva informatia astfel creata in baza de date si va introduce tichetul in coada de asteptare. 


 
 

 
  
<tnt:NavigationListItem text="{i18n>adaugareTichet" icon="sap-icon://home" expanded="false" key="tichetPage" enabled="{editingState>/allnoop}"/> 
<tnt:NavigationListItem text="{i18n>ticheteDeschise" icon="sap-icon://car-rental" expanded="false" key="ticheteDeschisePage" enabled="{editingState>/allnoop}"/>          
<tnt:NavigationListItem text="{i18n>ticheteDeschise" icon="sap-icon://shipping-status" expanded="false" key="ticheteDeschisePage" enabled="{editingState>/allnoop}"/> 
 
 
  
 


select * from dolphin.liste

-- TIP_LISTA  = STARI
-- TIP_LISTA  = PRIORITATI
-- TIP_LISTA  = ENTITATI
-- TIP_LISTA  = SERVICII
-- TIP_LISTA  = COMPONENTE
-- TIP_LISTA  = TIPDET
-- TIP_LISTA  = TIPOPER
-- TIP_LISTA  = STARI

INSERT INTO DOLPHIN.LISTE 
VALUES(1,'STARI','LIsta de valori posibile pentru Stari');
INSERT INTO DOLPHIN.LISTE 
VALUES(2,'PRIORITATI','LIsta de valori posibile pentru Prioritati');
INSERT INTO DOLPHIN.LISTE 
VALUES(3,'ENTITATI','LIsta de valori posibile pentru Entitati');
INSERT INTO DOLPHIN.LISTE 
VALUES(4,'SERVICII','LIsta de valori posibile pentru Servicii');
INSERT INTO DOLPHIN.LISTE 
VALUES(5,'COMPONENTE','LIsta de valori posibile pentru Componente');
INSERT INTO DOLPHIN.LISTE 
VALUES(6,'TIPDET','LIsta de valori posibile pentru Tip Detaliete Tichet');
INSERT INTO DOLPHIN.LISTE 
VALUES(7,'TIPOPER','LIsta de valori posibile pentru Tip Operatie');



INSERT INTO DOLPHIN.LISTE 
VALUES(8,'ECHIPE','Lista de valori posibile pentru Echipe');
INSERT INTO DOLPHIN.LISTE 
VALUES(9,'ROLURI','Lista de valori posibile pentru Roluri');
INSERT INTO DOLPHIN.LISTE 
VALUES(10,'STARI_U','Lista de valori posibile pentru Stari Utilizatori');

select * from dolphin.liste

select * from dolphin.liste_def

1 	STARI     	LIsta de valori posibile pentru Stari
2 	PRIORITATI	LIsta de valori posibile pentru Prioritati
3 	ENTITATI  	LIsta de valori posibile pentru Entitati
4 	SERVICII  	LIsta de valori posibile pentru Servicii
5 	COMPONENTE	LIsta de valori posibile pentru Componente
6 	TIPDET    	LIsta de valori posibile pentru Tip Detaliete Tichet
7 	TIPOPER   	LIsta de valori posibile pentru Tip Operatie
8 	ECHIPE    	Lista de valori posibile pentru Echipe
9 	ROLURI    	Lista de valori posibile pentru Roluri
10	STARI_U   	Lista de valori posibile pentru Stari Utilizatori


INSERT INTO DOLPHIN.LISTE_DEF 
VALUES(1,1,'DESCHIS','Tichet Deschis');
INSERT INTO DOLPHIN.LISTE_DEF 
VALUES(2,1,'IN_LUCRU','Tichet In Lucru');
INSERT INTO DOLPHIN.LISTE_DEF 
VALUES(3,1,'REZOLVAT','Tichet In Rezolvat');
INSERT INTO DOLPHIN.LISTE_DEF 
VALUES(4,1,'INCHIS','Tichet Inchis');
INSERT INTO DOLPHIN.LISTE_DEF 
VALUES(5,2,'P1','Nivel prioritate P1');
INSERT INTO DOLPHIN.LISTE_DEF 
VALUES(6,2,'P2','Nivel prioritate P2');
INSERT INTO DOLPHIN.LISTE_DEF 
VALUES(7,2,'P3','Nivel prioritate P3');
INSERT INTO DOLPHIN.LISTE_DEF 
VALUES(8,2,'P4','Nivel prioritate P4');
INSERT INTO DOLPHIN.LISTE_DEF 
VALUES(9,3,'E1','Entitatea 1');
INSERT INTO DOLPHIN.LISTE_DEF 
VALUES(10,3,'E2','Entitatea 2');
INSERT INTO DOLPHIN.LISTE_DEF 
VALUES(11,3,'E3','Entitatea 3');
INSERT INTO DOLPHIN.LISTE_DEF 
VALUES(12,3,'E4','Entitatea 4');
INSERT INTO DOLPHIN.LISTE_DEF 
VALUES(14,4,'S1','Serviciul 1');
INSERT INTO DOLPHIN.LISTE_DEF 
VALUES(15,4,'S2','Entitatea 2');
INSERT INTO DOLPHIN.LISTE_DEF 
VALUES(16,4,'S3','Entitatea 3');
INSERT INTO DOLPHIN.LISTE_DEF 
VALUES(17,4,'S4','Entitatea 4');
INSERT INTO DOLPHIN.LISTE_DEF 
VALUES(18,5,'C1','Componenta 1');
INSERT INTO DOLPHIN.LISTE_DEF 
VALUES(19,5,'C2','Componenta 2');
INSERT INTO DOLPHIN.LISTE_DEF 
VALUES(22,6,'TIPDET1','Tip Detaliete Tichet 1');
INSERT INTO DOLPHIN.LISTE_DEF 
VALUES(23,6,'TIPDET2','Tip Detaliete Tichet 2');
INSERT INTO DOLPHIN.LISTE_DEF 
VALUES(33,7,'TIPOPER1','Tip Operatie 1');
INSERT INTO DOLPHIN.LISTE_DEF 
VALUES(34,7,'TIPOPER2','Tip Operatie 2');




INSERT INTO DOLPHIN.LISTE_DEF 
VALUES(35,8,'DBA','DBA');
INSERT INTO DOLPHIN.LISTE_DEF 
VALUES(36,9,'ADMIN','Administrator');
INSERT INTO DOLPHIN.LISTE_DEF 
VALUES(37,10,'ACTIV','Activ');
INSERT INTO DOLPHIN.LISTE_DEF 
VALUES(38,10,'BLOCAT','Blocat');
INSERT INTO DOLPHIN.LISTE_DEF 
VALUES(39,8,'SYSADMIN','SysAdmin');
INSERT INTO DOLPHIN.LISTE_DEF 
VALUES(40,8,'APP','Aplicatie');
INSERT INTO DOLPHIN.LISTE_DEF 
VALUES(41,9,'L1','Level 1');
INSERT INTO DOLPHIN.LISTE_DEF 
VALUES(42,9,'L2','Level 2');




-- commit 

select
 LISTE.id_lista, 
 LISTE.tip_lista, 
 LISTE_DEF.id_lista_def,
 LISTE_DEF.cod_lista,
 LISTE_DEF.den_lista
from 
 DOLPHIN.LISTE,
 DOLPHIN.LISTE_DEF
where 
 LISTE.id_lista =  LISTE_DEF.id_lista
 and LISTE.tip_lista in ('PRIORITATI', 'ENTITATI', 'SERVICII', 'COMPONENTE', 'STARI' )
order by 
 LISTE.tip_lista, 
 LISTE_DEF.cod_lista
 
 insert into DOLPHIN.TICHETE (
  ID_TICHET , 
  NR_TICHET ,
  NUME_APARMA ,
  ID_PRIORITATE, 
  ID_ENTITATE_ACTIVA,
  DESCRIERE,  
  ID_SERVICIU,
  ID_COMPONENTA,  
  ID_STARE 
 ) values (?, ?, ?, ?, ?, ?, ?, ?, ?)
 
 
 select * from 

select 
liste.id_lista,
liste.tip_lista,
liste_def.* 
from 
dolphin.liste_def,
dolphin.liste
where
liste_def.id_lista = dolphin.liste.id_lista 
order by 
liste.id_lista,
liste_def.cod_lista 

select * from
dolphin.liste_def


1 	STARI     	1 	1 	DESCHIS 	Tichet Deschis
1 	STARI     	4 	1 	INCHIS  	Tichet Inchis
1 	STARI     	2 	1 	IN_LUCRU	Tichet In Lucru
1 	STARI     	3 	1 	REZOLVAT	Tichet In Rezolvat
2 	PRIORITATI	5 	2 	P1      	Nivel prioritate P1
2 	PRIORITATI	6 	2 	P2      	Nivel prioritate P2
2 	PRIORITATI	7 	2 	P3      	Nivel prioritate P3
2 	PRIORITATI	8 	2 	P4      	Nivel prioritate P4
3 	ENTITATI  	9 	3 	E1      	Entitatea 1
3 	ENTITATI  	10	3 	E2      	Entitatea 2
3 	ENTITATI  	11	3 	E3      	Entitatea 3
3 	ENTITATI  	12	3 	E4      	Entitatea 4
4 	SERVICII  	14	4 	S1      	Serviciul 1
4 	SERVICII  	15	4 	S2      	Serviciul 2
4 	SERVICII  	16	4 	S3      	Serviciul 3
4 	SERVICII  	17	4 	S4      	Serviciul 4
5 	COMPONENTE	18	5 	C1      	Componenta 1
5 	COMPONENTE	19	5 	C2      	Componenta 2
6 	TIPDET    	22	6 	TIPDET1 	Tip Detaliete Tichet 1
6 	TIPDET    	23	6 	TIPDET2 	Tip Detaliete Tichet 2

7 	TIPOPER   	33	7 	TIPOPER1	Tip Operatie 1
7 	TIPOPER   	34	7 	TIPOPER2	Tip Operatie 2

8 	ECHIPE    	35	8 	DBA     	DBA
8 	ECHIPE    	35	8 	DBA     	SYSADMIN
8 	ECHIPE    	35	8 	DBA     	APLICATIE

9 	ROLURI    	36	9 	ADMIN   	ADMIN
9 	ROLURI    	36	9 	ADMIN   	LEVEL1
9 	ROLURI    	36	9 	ADMIN   	LEVEL2

10	STARI_U   	37	10	ACTIV   	Activ
10	STARI_U   	38	10	BLOCAT  	Blocat


select  
 UTILIZATORI.ID_UTILIZ , 
 UTILIZATORI.COD_UTILIZ , 
 UTILIZATORI.NUME_UTILIZ , 
 UTILIZATORI.PRENUME_UTILIZ ,
 UTILIZATORI.id_rol_utiliz,
 ROL.cod_lista ROL_utiliz, 
 ROL.den_lista den_rol,
  
 UTILIZATORI.PAROLA, 
 UTILIZATORI.id_stare,
 STARE.cod_lista stare, 
 STARE.den_lista den_stare,
 
 UTILIZATORI.id_echipa,
 ECHIPA.cod_lista ECHIPA, 
 ECHIPA.den_lista den_echipa 
  
from  
 DOLPHIN.UTILIZATORI,
 DOLPHIN.liste_def STARE,
 DOLPHIN.liste_def ROL,
 DOLPHIN.liste_def ECHIPA
 
where  
 UTILIZATORI.COD_UTILIZ = 'ADMIN'
 and STARE.id_lista_def = UTILIZATORI.id_stare 
 and ROL.id_lista_def = UTILIZATORI.id_rol_utiliz
 and ECHIPA.id_lista_def = UTILIZATORI.id_echipa
 
 
 
 
 --expdp DOLPHIN/tama1234 DIRECTORY=DATA_FILE_DIR schemas=DOLPHIN DUMPFILE=dolphin.dmp log=dolphin.log
 
 
 
 
 
 
 
 
 
 
 
 create table DOLPHIN.CHANGEURI (
  ID_CHANGE number(10) not null, 
   DESCRIERE varchar2(400),
   COMENTARII varchar2(400),
   ID_STARE_CHANGE number(10), --initiat, in_derulare, inchis
   DATA_START date,
   DATA_END date
  
);


create table DOLPHIN.CHANGEURI_DET (
  create table DOLPHIN.CHANGEURI (
  ID_CHANGE number(10) not null, 
   DESCRIERE varchar2(400),
   COMENTARII varchar2(400),
   ID_STARE_CHANGE number(10), --initiat, in_derulare, inchis
   DATA_START date,
   DATA_END date
  
);


create table DOLPHIN.CHANGEURI_DET (
  ID_CHANGE_DET number(10) not null,
  ID_CHANGE number(10) not null, 

   ID_UTILIZ number(10),
   OVERTIME NUMBER(2,0)
  
);

select * from DOLPHIN.CHANGEURI;

ALTER TABLE DOLPHIN.CHANGEURI ADD CONSTRAINT
PK_CHANGEURI PRIMARY KEY (ID_CHANGE);

CREATE SEQUENCE SEQ_CHANGEURI INCREMENT BY 1 START WITH 1 MAXVALUE 9999 MINVALUE 1;
CREATE SEQUENCE SEQ_CHANGEURI_DET INCREMENT BY 1 START WITH 1 MAXVALUE 9999 MINVALUE 1;


ALTER TABLE DOLPHIN.CHANGEURI_DET ADD CONSTRAINT
PK_CHANGEURI_DET PRIMARY KEY (ID_CHANGE_DET);

-- TIP_LISTA  = STARI
ALTER TABLE DOLPHIN.CHANGEURI ADD CONSTRAINT
FK_CHANGEURI_STARE foreign KEY (ID_STARE_CHANGE) 
REFERENCES DOLPHIN.LISTE_DEF (ID_LISTA_DEF) ENABLE;


select * from DOLPHIN.CHANGEURI;

 ALTER TABLE DOLPHIN.CHANGEURI_DET ADD CONSTRAINT
FK_CHANGEURI_DET foreign KEY (ID_CHANGE) 
REFERENCES DOLPHIN.CHANGEURI (ID_CHANGE) ENABLE;
 
 
 
 
 
 
 
 
 
 
 
 
 
 

  