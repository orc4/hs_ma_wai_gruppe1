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


--Anzeigen der Tabelle
SELECT * FROM public.wai_cam_user

