-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.purchage_ledger_seq;

CREATE SEQUENCE jmsc.purchage_ledger_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.purchage_ledger_seq OWNER TO jmscdev;
GRANT ALL ON SEQUENCE jmsc.purchage_ledger_seq TO jmscdev;

-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.PURCHAGE_LEDGER CASCADE;

CREATE TABLE jmsc.PURCHAGE_LEDGER
(
    ID 							integer NOT NULL DEFAULT nextval('jmsc.purchage_ledger_seq'::regclass),
    CLIENT_ID 					integer NOT NULL,
    SUPPLIER_ID					integer NOT NULL,
    DATE						date,
    ITEM						text,
    QTY							numeric,
    RATE						numeric,
    VEHICLE						text,
    CREDIT_AMOUNT				numeric,
    DEBIT_AMOUNT				numeric,
    REMARK						text,
   	CREATED_TS 					timestamp with time zone NOT NULL,
	UPDATED_TS 					timestamp with time zone NOT NULL,
    CONSTRAINT purchage_ledger_key PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;

ALTER TABLE jmsc.PURCHAGE_LEDGER OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.PURCHAGE_LEDGER TO jmscdev;