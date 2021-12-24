-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.user_sequence;

CREATE SEQUENCE jmsc.user_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.user_sequence OWNER TO jmscdev;
GRANT ALL ON SEQUENCE jmsc.user_sequence TO jmscdev;

-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.USERS CASCADE;

CREATE TABLE jmsc.USERS
(
    USER_ID 		integer NOT NULL DEFAULT nextval('jmsc.user_sequence'::regclass),
    FIRST_NAME 		character varying(200) NOT NULL,
    LAST_NAME 		character varying(200) NOT NULL,
    MOBILE			character varying(200) NOT NULL,
    EMAIL_ID		character varying(200) NOT NULL,
    LOGON_ID		character varying(200) NOT NULL,
    PASSWORD		character varying(200) NOT NULL,
    FIRST_LOGIN		BOOLEAN NOT NULL,
	CREATED_TS 		timestamp with time zone NOT NULL,
	UPDATED_TS 		timestamp with time zone NOT NULL,
	
    CONSTRAINT user_key PRIMARY KEY (USER_ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;


ALTER TABLE jmsc.USERS OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.USERS TO jmscdev;