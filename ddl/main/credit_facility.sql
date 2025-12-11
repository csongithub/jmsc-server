-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.cf_seq;

CREATE SEQUENCE jmsc.cf_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.cf_seq OWNER TO jmscdev;
GRANT ALL ON SEQUENCE jmsc.cf_seq TO jmscdev;

-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.CREDIT_FACILITY CASCADE;

CREATE TABLE jmsc.CREDIT_FACILITY
(
    ID 					integer NOT NULL DEFAULT nextval('jmsc.cf_seq'::regclass),
	CLIENT_ID			integer NOT NULL,
    ACCOUNT_NO 			character varying(25) NOT NULL UNIQUE,
    AMOUNT 				numeric NOT NULL,
    OPEN_DATE 			date NOT NULL,
    MATURITY_DATE 		date NOT NULL,
    ISSUER_TYPE			character varying(25),
    ISSUER_NAME			character varying(25) NOT NULL,
    ISSUER_BRANCH		character varying(25) NOT NULL,
    FACILITY_TYPE		character varying(25) NOT NULL,
    IS_PLEDGED			boolean  NOT NULL,
    PLEDGED_ID			integer,
    PLEDGED_TYPE		character varying(25),
	CREATED_TS 			timestamp with time zone NOT NULL,
	UPDATED_TS 			timestamp with time zone NOT NULL,
	
    CONSTRAINT CREDIT_FACILITY_KEY PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;

ALTER TABLE jmsc.CREDIT_FACILITY OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.CREDIT_FACILITY TO jmscdev;
