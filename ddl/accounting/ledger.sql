-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.ledger_seq;

CREATE SEQUENCE jmsc.ledger_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.ledger_seq OWNER TO jmscdev;
GRANT ALL ON SEQUENCE jmsc.ledger_seq TO jmscdev;

-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.LEDGER CASCADE;

CREATE TABLE jmsc.LEDGER
(
    ID 							integer NOT NULL DEFAULT nextval('jmsc.ledger_seq'::regclass),
    CLIENT_ID 					integer NOT NULL,
    CREDITOR_ID					integer NOT NULL,						
    CODE						text NOT NULL,
    NAME						text NOT NULL,
    START_DATE					date NOT NULL,
    OPENING_BALALCE				numeric NOT NULL,
    REMARK						text,
   	CREATED_TS 					timestamp with time zone NOT NULL,
	UPDATED_TS 					timestamp with time zone NOT NULL,
    CONSTRAINT eledger_key PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;

ALTER TABLE jmsc.LEDGER OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.LEDGER TO jmscdev;