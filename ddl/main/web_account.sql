-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.web_account_seq;

CREATE SEQUENCE jmsc.web_account_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.web_account_seq OWNER TO jmscdev;
GRANT ALL ON SEQUENCE jmsc.web_account_seq TO jmscdev;

-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.ACCOUNT CASCADE;

CREATE TABLE jmsc.WEB_ACCOUNT
(
    ID 					integer NOT NULL DEFAULT nextval('jmsc.web_account_seq'::regclass),
	CLIENT_ID			integer NOT NULL,
    ACCOUNT_NAME 		character varying(50) NOT NULL,
    URL 				character varying(200) NOT NULL,
    ATTRIBUTES			bytea NOT NULL,
	CREATED_TS 			timestamp with time zone NOT NULL,
	UPDATED_TS 			timestamp with time zone NOT NULL,
    CONSTRAINT WEB_ACCOUNT_KEY PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;

ALTER TABLE jmsc.WEB_ACCOUNT OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.WEB_ACCOUNT TO jmscdev;
