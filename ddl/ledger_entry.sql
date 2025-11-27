-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.ledger_entry_seq;

CREATE SEQUENCE jmsc.ledger_entry_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.ledger_entry_seq OWNER TO jmscdev;
GRANT ALL ON SEQUENCE jmsc.ledger_entry_seq TO jmscdev;

-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.LEDGER_ENTRY CASCADE;

CREATE TABLE jmsc.LEDGER_ENTRY
(
    ID 							integer NOT NULL DEFAULT nextval('jmsc.ledger_seq'::regclass),
    CLIENT_ID 					integer NOT NULL,
    CREDITOR_ID					integer NOT NULL,	
    LEDGER_ID					integer NOT NULL,	
    PROJECT_ID					integer NOT NULL,
    RECEIPT_NO					text,
    DATE						date NOT NULL,
    ITEM						text,
    RATE						float,
    QTY							float,
    CREDIT						numeric,
    UNIT						text,
    VEHICLE						text,
    REMARK						text,
    ENTRY_TYPE 					text NOT NULL,
    DEBIT						numeric,
    NARRATION					text,
   	CREATED_TS 					timestamp with time zone NOT NULL,
	UPDATED_TS 					timestamp with time zone NOT NULL,
    CONSTRAINT eledger_entry_key PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;

ALTER TABLE jmsc.LEDGER_ENTRY OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.LEDGER_ENTRY TO jmscdev;