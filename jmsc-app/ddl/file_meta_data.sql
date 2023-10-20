-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.file_meta_sequence;

CREATE SEQUENCE jmsc.file_meta_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.file_meta_sequence OWNER TO jmscdev;
GRANT ALL ON SEQUENCE jmsc.file_meta_sequence TO jmscdev;

-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.FILE_META_DATA CASCADE;

CREATE TABLE jmsc.FILE_META_DATA
(
    ID 				integer NOT NULL DEFAULT nextval('jmsc.file_meta_sequence'::regclass),
    CLIENT_ID		integer NOT NULL,
    DIR_ID			integer NOT NULL,
    SYS_PATH		character varying(200) NOT NULL,
    FILE_NAME 		character varying(50) NOT NULL,
    FILE_SIZE 		character varying(50),
    FILE_TYPE 		character varying(30) NOT NULL,
    FILE_PATH		character varying(200),
    CREATED_BY		character varying(30) NOT NULL,
    UPDATED_BY		character varying(30) NOT NULL,
    STATUS			character varying(30) NOT NULL,
    DESCRIPTION		character varying(200) NOT NULL,
    CREATED_TS 		timestamp with time zone NOT NULL,
	UPDATED_TS 		timestamp with time zone NOT NULL,
    CONSTRAINT FILE_META_DATA_key PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;


ALTER TABLE jmsc.FILE_META_DATA OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.FILE_META_DATA TO jmscdev;