-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.site_seq;

CREATE SEQUENCE jmsc.site_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.site_seq OWNER TO jmscdev;
GRANT ALL ON SEQUENCE jmsc.site_seq TO jmscdev;

-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.SITE CASCADE;

CREATE TABLE jmsc.SITE
(
    ID 					integer NOT NULL DEFAULT nextval('jmsc.site_seq'::regclass),
    CLIENT_ID 			integer NOT NULL,
    SITE_NAME			character varying(200) NOT NULL,
    DISPLAY_NAME		character varying(200) NOT NULL,
    BID_LINKAGE_ID		integer,
    AGREEMENT_NO		character varying(200),
    AGREEMENT_DATE		date,
    AGREEMENT_VALUE		integer,
   	CREATED_TS 			timestamp with time zone NOT NULL,
	UPDATED_TS 			timestamp with time zone NOT NULL,
    CONSTRAINT site_key PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;

ALTER TABLE jmsc.SITE OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.SITE TO jmscdev;