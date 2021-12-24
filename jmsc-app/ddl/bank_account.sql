-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.bank_acccount_seq;

CREATE SEQUENCE jmsc.bank_acccount_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.bank_acccount_seq OWNER TO jmscdev;
GRANT ALL ON SEQUENCE jmsc.bank_acccount_seq TO jmscdev;

-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.BANK_ACCOUNT CASCADE;

CREATE TABLE jmsc.BANK_ACCOUNT
(
    ID 					integer NOT NULL DEFAULT nextval('jmsc.bank_acccount_seq'::regclass),
	ACCOUNT_HOLDER 		character varying(50) NOT NULL,
    ACCOUNT_NO 			character varying(50) NOT NULL,
    IFSC_CODE			character varying(50) NOT NULL,
	BANK_NAME			character varying(50),
	BRANCH_NAME			character varying(50),
	BRANCH_CODE			character varying(50),
	ADDRESS				character varying(100),
	ACC_TYPE			character varying(50),
	MOBILE				character varying(50) NOT NULL,
	ACC_TYPE			character varying(50) NOT NULL,
	STATUS				character varying(50) NOT NULL,
	CREATED_TS 			timestamp with time zone NOT NULL,
	UPDATED_TS 			timestamp with time zone NOT NULL,
    CONSTRAINT PARTY_BANK_ACCOUNT_KEY PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;

ALTER TABLE jmsc.BANK_ACCOUNT OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.BANK_ACCOUNT TO jmscdev;
