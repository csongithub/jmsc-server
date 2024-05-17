-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.users_sequence;

CREATE SEQUENCE jmsc.users_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.users_sequence OWNER TO jmscdev;
GRANT ALL ON SEQUENCE jmsc.users_sequence TO jmscdev;

-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.USERS CASCADE;

CREATE TABLE jmsc.USERS
(
    ID 				integer NOT NULL DEFAULT nextval('jmsc.users_sequence'::regclass),
    CLIENT_ID		integer NOT NULL,
    NAME 			character varying(200) NOT NULL,
    DISP_NAME 		character varying(200) NOT NULL,
    STATUS 			character varying(200) NOT NULL,
    LOGON_ID 		character varying(200) NOT NULL UNIQUE,
    PASSWORD		character varying(200) NOT NULL,
    CREATED_TS 		timestamp with time zone NOT NULL,
	UPDATED_TS 		timestamp with time zone NOT NULL,
    CONSTRAINT users_key PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;


ALTER TABLE jmsc.USERS OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.USERS TO jmscdev;