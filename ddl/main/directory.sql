-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.directory_sequence;

CREATE SEQUENCE jmsc.directory_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.directory_sequence OWNER TO jmscdev;
GRANT ALL ON SEQUENCE jmsc.directory_sequence TO jmscdev;

-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.DIRECTORY CASCADE;

CREATE TABLE jmsc.DIRECTORY
(
    ID 				integer NOT NULL DEFAULT nextval('jmsc.directory_sequence'::regclass),
    CLIENT_ID		integer NOT NULL,
    NAME 			character varying(50) NOT NULL,
    DESCRIPTION 	character varying(200) NOT NULL,
    CATEGORY 		character varying(30) NOT NULL,
    CREATED_TS 		timestamp with time zone NOT NULL,
	UPDATED_TS 		timestamp with time zone NOT NULL,
    CONSTRAINT directory_key PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;


ALTER TABLE jmsc.DIRECTORY OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.DIRECTORY TO jmscdev;