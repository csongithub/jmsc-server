-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.client_sequence;

CREATE SEQUENCE jmsc.client_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.client_sequence OWNER TO jmscdev;
GRANT ALL ON SEQUENCE jmsc.client_sequence TO jmscdev;

-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.CLIENT CASCADE;

CREATE TABLE jmsc.CLIENT
(
    ID 				integer NOT NULL DEFAULT nextval('jmsc.client_sequence'::regclass),
    NAME 			character varying(200) NOT NULL,
    LOGON_ID 		character varying(200) NOT NULL,
    PASSWORD		character varying(200) NOT NULL,
    CONSTRAINT client_key PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;


ALTER TABLE jmsc.CLIENT OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.CLIENT TO jmscdev;