-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.refresh_token_seq;

CREATE SEQUENCE jmsc.loan_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.refresh_token_seq OWNER TO jmscdev;
GRANT ALL ON SEQUENCE jmsc.refresh_token_seq TO jmscdev;

-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.REFRESH_TOKEN CASCADE;

CREATE TABLE jmsc.REFRESH_TOKEN
(
    ID 					integer NOT NULL DEFAULT nextval('jmsc.refresh_token_seq'::regclass),
	CLIENT_ID			integer,
	TOKEN				text NOT NULL,
	USERNAME			text NOT NULL,
	EXPIRY				TIMESTAMPTZ NOT NULL,
	CREATED_TS 			timestamp with time zone NOT NULL,
	UPDATED_TS 			timestamp with time zone NOT NULL,
    CONSTRAINT REFRESH_TOKEN_KEY PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;

ALTER TABLE jmsc.REFRESH_TOKEN OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.REFRESH_TOKEN TO jmscdev;
