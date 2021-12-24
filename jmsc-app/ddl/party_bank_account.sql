-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.party_bnk_acc_seq;

CREATE SEQUENCE jmsc.party_bnk_acc_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.party_bnk_acc_seq OWNER TO jmscdev;
GRANT ALL ON SEQUENCE jmsc.party_bnk_acc_seq TO jmscdev;

-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.PARTY_BANK_ACCOUNT CASCADE;

CREATE TABLE jmsc.PARTY_BANK_ACCOUNT
(
    ID 					integer NOT NULL DEFAULT nextval('jmsc.party_bnk_acc_seq'::regclass),
    PARTY_NAME			character varying(200) NOT NULL,
	ACCOUNT_HOLDER 		character varying(200) NOT NULL,
    ACCOUNT_NO 			character varying(200) NOT NULL,
    IFSC_CODE			character varying(200) NOT NULL,
	BANK_NAME			character varying(200),
	BRANCH_NAME			character varying(200),
	BRANCH_CODE			character varying(200),
	ADDRESS				character varying(200),
	CREATED_TS 			timestamp with time zone NOT NULL,
	UPDATED_TS 			timestamp with time zone NOT NULL,
    CONSTRAINT PARTY_BANK_ACCOUNT_KEY PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;

ALTER TABLE jmsc.PARTY_BANK_ACCOUNT OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.PARTY_BANK_ACCOUNT TO jmscdev;
