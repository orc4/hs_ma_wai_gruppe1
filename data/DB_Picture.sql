--Löschen der Tabelle
DROP TABLE wai_picture;

--Löschen der Sequence
DROP SEQUENCE wai_picture_id_seq;

--Erstellen einer Automtischen id für User
CREATE SEQUENCE wai_picture_id_seq START WITH 1 INCREMENT BY 1;

--Erstellen einer Tabelle
CREATE TABLE public.wai_picture
(
  id integer NOT NULL DEFAULT nextval('wai_picture_id_seq'::regclass),
  cam_id bigint,
  date timestamp,
  path character varying(1024),
  CONSTRAINT wai_picture_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);


--Einfügen von Inhalt der Tabelle
INSERT INTO public.wai_picture (cam_id, date, path) VALUES (2, '2017-05-15', 'Test');
INSERT INTO public.wai_picture (cam_id, date, path) VALUES (5, '2017-05-16', 'Test2');
INSERT INTO public.wai_picture (cam_id, date, path) VALUES (9, '2017-04-15', 'Test3');
INSERT INTO public.wai_picture (cam_id, date, path) VALUES (3, '2017-04-16', 'Test4');

--Anzeigen der Tabelle
SELECT * FROM public.wai_picture