--Löschen der Tabelle
DROP TABLE wai_cam;

--Löschen der Sequence
DROP SEQUENCE wai_cam_id_seq;

--Erstellen einer Automtischen id für User
CREATE SEQUENCE wai_cam_id_seq START WITH 1 INCREMENT BY 1;

--Erstellen einer Tabelle
CREATE TABLE public.wai_cam
(
  id integer NOT NULL DEFAULT nextval('wai_cam_id_seq'::regclass),
  name character varying(1024),
  url character varying(1024),
  CONSTRAINT wai_cam_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

--Einfügen von Inhalt der Tabelle
INSERT INTO public.wai_cam (name, url) VALUES ('WER', 'https://www.hs-mannheim.de/');
INSERT INTO public.wai_cam (name, url) VALUES ('warum', 'https://www.hs-mannheim.de/');

--Anzeigen der Tabelle
SELECT * FROM public.wai_cam