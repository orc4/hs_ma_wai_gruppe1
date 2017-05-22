--Löschen der Tabelle
DROP TABLE wai_cam_user;

--Erstellen einer Tabelle
CREATE TABLE public.wai_cam_user
(
  cam_id bigint NOT NULL,
  user_id bigint NOT NULL,
  CONSTRAINT wai_cam_user_pkey PRIMARY KEY (cam_id, user_id)
)
WITH (
  OIDS=FALSE
);

--Einfügen von Inhalt der Tabelle
INSERT INTO public.wai_cam_user (cam_id, user_id) VALUES (10, 1);
INSERT INTO public.wai_cam_user (cam_id, user_id) VALUES (21, 2);

--Anzeigen der Tabelle
SELECT * FROM public.wai_cam_user