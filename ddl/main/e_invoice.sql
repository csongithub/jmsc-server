-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.einvoice_seq;

CREATE SEQUENCE jmsc.einvoice_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.einvoice_seq OWNER TO jmscdev;
GRANT ALL ON SEQUENCE jmsc.einvoice_seq TO jmscdev;

-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.E_INVOICE CASCADE;

CREATE TABLE jmsc.E_INVOICE
(
    ID 							integer NOT NULL DEFAULT nextval('jmsc.einvoice_seq'::regclass),
    CLIENT_ID 					integer NOT NULL,
    GST_STATE					text NOT NULL,
    FY							text NOT NULL,
    MONTH						text NOT NULL,
    BILL_DATE					date NOT NULL,
    PAYMENT_DATE				date NOT NULL,
    CHEQUE_AMOUNT				numeric NOT NULL,
    GROSS_AMOUNT				numeric NOT NULL,
    TAXABLE_AMOUNT				numeric NOT NULL,
    GST_RATE					integer NOT NULL,
    CGST						numeric NOT NULL,
    SGST						numeric NOT NULL,
    TOTAL_GST_TO_PAY			numeric NOT NULL,
    GST_DEDUCTED_AT_SOURCE		numeric NOT NULL,
    FINAL_GST_TO_PAY			numeric NOT NULL,
    SOURCE_DIVISION_NAME		text NOT NULL,
    PROJECT_NAME				text NOT NULL,
    DESCRIPTION					text,
   	CREATED_TS 					timestamp with time zone NOT NULL,
	UPDATED_TS 					timestamp with time zone NOT NULL,
    CONSTRAINT einvoice_key PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;

ALTER TABLE jmsc.E_INVOICE OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.E_INVOICE TO jmscdev;