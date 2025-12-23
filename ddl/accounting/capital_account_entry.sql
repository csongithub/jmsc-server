-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.capital_account_entry_seq;

CREATE SEQUENCE jmsc.capital_account_entry_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.capital_account_entry_seq OWNER TO jmscdev;
GRANT ALL ON SEQUENCE jmsc.capital_account_entry_seq TO jmscdev;


-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.CAPITAL_ACCOUNT_ENTRY CASCADE;

CREATE TABLE jmsc.CAPITAL_ACCOUNT_ENTRY
(
    ID 					integer NOT NULL DEFAULT nextval('jmsc.capital_account_entry_seq'::regclass),
    CLIENT_ID			integer NOT NULL,
    ACCOUNT_ID			integer NOT NULL,
    DATE				date NOT NULL,
    NOTE				tet NOT NULL,
    DEBIT				numeric NOT NULL,
    CREDIT				numeric NOT NULL,
    ENTRY_TYPE			text NOT NULL,
    TRANS_REF_NO		integer,
	CREATED_TS 			timestamp with time zone NOT NULL,
	UPDATED_TS 			timestamp with time zone NOT NULL,
    CONSTRAINT CAPITAL_ACCOUNT_ENTRY_KEY PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;

ALTER TABLE jmsc.CAPITAL_ACCOUNT_ENTRY OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.CAPITAL_ACCOUNT_ENTRY TO jmscdev;


CREATE INDEX index_cae_id_clientid_date
ON jmsc.capital_account_entry(id, client_id,date);
