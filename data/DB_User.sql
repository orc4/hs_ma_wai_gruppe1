--Löschen der Tabelle
DROP TABLE wai_user;

--Löschen der Sequence
DROP SEQUENCE wai_user_id_seq;

--Erstellen einer Automtischen id für User
CREATE SEQUENCE wai_user_id_seq START WITH 1 INCREMENT BY 1;

--Erstellen einer Tabelle
CREATE TABLE public.wai_user
(
  id integer NOT NULL DEFAULT nextval('wai_user_id_seq'::regclass),
  username character varying(1024),
  vorname character varying(1024),
  nachname character varying(1024),
  password character varying(1024),
  salt character varying(120),
  can_mod_cam boolean NOT NULL DEFAULT false,
  can_mod_user boolean NOT NULL DEFAULT false,
  can_see_all_cam boolean NOT NULL DEFAULT false,
  can_delegate_cam boolean NOT NULL DEFAULT false,
  CONSTRAINT wai_user_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

--Einfügen von Inhalt der Tabelle
INSERT INTO public.wai_user (username, vorname, nachname, password, salt, can_mod_cam, can_mod_user, can_see_all_cam, can_delegate_cam)
VALUES ('Admin', 'Dennis', 'Schmidt', 'test1', 'salt1',  'true', 'true', 'true' ,'true');
INSERT INTO public.wai_user (username, vorname, nachname, password, salt, can_mod_cam, can_mod_user, can_see_all_cam, can_delegate_cam)
VALUES ('Schichtleiter', 'Aaron', 'Unbekannt', 'test2', 'aslt2',  'true', 'false', 'true' ,'false');

--Anzeigen der Tabelle
SELECT * FROM public.wai_user