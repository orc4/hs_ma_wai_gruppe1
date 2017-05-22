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
  vorname character varying,
  nachname character varying,
  can_mod_cam boolean NOT NULL DEFAULT false,
  can_mod_user boolean NOT NULL DEFAULT false,
  can_delegate_cam boolean NOT NULL DEFAULT false,
  can_see_all boolean NOT NULL DEFAULT false,
  salt character varying(120),
  password character varying(1024),
  CONSTRAINT wai_user_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

--Einfügen von Inhalt der Tabelle
INSERT INTO public.wai_user (vorname, nachname, can_mod_cam, can_mod_user, can_delegate_cam, can_see_all, salt, username)
VALUES ('Dennis', 'Schmidt', 'false', 'true', 'true' ,'true', 'Test', 'Damm');
INSERT INTO public.wai_user (vorname, nachname, can_mod_cam, can_mod_user, can_delegate_cam, can_see_all, salt, username)
VALUES ('Aaron', 'Unbekannt', 'true', 'false', 'true' ,'false', 'Test2', 'Hs');

--Anzeigen der Tabelle
SELECT * FROM public.wai_user