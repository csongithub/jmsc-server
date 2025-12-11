-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.noti_seq;

CREATE SEQUENCE jmsc.noti_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.noti_seq OWNER TO jmscdev;
GRANT ALL ON SEQUENCE jmsc.noti_seq TO jmscdev;

-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.NOTIFICATION CASCADE;

CREATE TABLE jmsc.NOTIFICATION
(
    ID 					integer NOT NULL DEFAULT nextval('jmsc.noti_seq'::regclass),
	CLIENT_ID			integer NOT NULL,
    TYPE				character varying(25),
	CREATED_TS 			timestamp with time zone NOT NULL,
	UPDATED_TS 			timestamp with time zone NOT NULL,
	
    CONSTRAINT NOTIFICATION_KEY PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;

ALTER TABLE jmsc.NOTIFICATION OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.NOTIFICATION TO jmscdev;
