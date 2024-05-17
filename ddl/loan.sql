-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.loan_seq;

CREATE SEQUENCE jmsc.loan_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.loan_seq OWNER TO jmscdev;
GRANT ALL ON SEQUENCE jmsc.loan_seq TO jmscdev;

-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.LOAN CASCADE;

CREATE TABLE jmsc.LOAN
(
    ID 					integer NOT NULL DEFAULT nextval('jmsc.loan_seq'::regclass),
	CLIENT_ID			integer NOT NULL,
	ACCOUNT_NO 			character varying(100) NOT NULL,
    DISPLAY_NAME 		character varying(50) NOT NULL,
    SANC_AMOUNT			integer NOT NULL,
    DISB_AMOUNT			integer NOT NULL,
    EMI_AMOUNT			integer NOT NULL,
    I_RATE				float NOT NULL,
    OPENING_DATE		date NOT NULL,
    BANK 				character varying(100) NOT NULL,
    BRANCH 				character varying(50) NOT NULL,
    BORROWER 			character varying(50) NOT NULL,
	STATUS				character varying(50) NOT NULL,
	CREATED_TS 			timestamp with time zone NOT NULL,
	UPDATED_TS 			timestamp with time zone NOT NULL,
    CONSTRAINT LOAN_KEY PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;

ALTER TABLE jmsc.LOAN OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.LOAN TO jmscdev;
