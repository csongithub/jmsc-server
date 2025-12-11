-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.e_invoice_files_seq;

CREATE SEQUENCE jmsc.e_invoice_files_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.e_invoice_files_seq OWNER TO jmscdev;
GRANT ALL ON SEQUENCE jmsc.e_invoice_files_seq TO jmscdev;

-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.E_INVOICE_FILES CASCADE;

CREATE TABLE jmsc.E_INVOICE_FILES
(
    ID 					integer NOT NULL DEFAULT nextval('jmsc.e_invoice_files_seq'::regclass),
    CLIENT_ID 			integer NOT NULL,
    INVOICE_ID			integer NOT NULL,
    MEMO_ATTACHED		boolean,
    MEMO				bytea,
    MEMO_NAME			text,
    MEMO_TYPE			text,
    INVOICE_ATTACHED	boolean,
    INVOICE				bytea,
    INVOICE_NAME		text,
    INVOICE_TYPE		text,
   	CREATED_TS 			timestamp with time zone NOT NULL,
	UPDATED_TS 			timestamp with time zone NOT NULL,
    CONSTRAINT E_INVOICE_FILES_KEY PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;

ALTER TABLE jmsc.E_INVOICE_FILES OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.E_INVOICE_FILES TO jmscdev;