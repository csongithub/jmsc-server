-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.stock_trans_seq;

CREATE SEQUENCE jmsc.stock_trans_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.stock_trans_seq OWNER TO jmscdev;
GRANT ALL ON SEQUENCE jmsc.stock_trans_seq TO jmscdev;


-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.STOCK_TRANSACTION CASCADE;

CREATE TABLE jmsc.STOCK_TRANSACTION
(
    ID 					integer NOT NULL DEFAULT nextval('jmsc.stock_trans_seq'::regclass),
    CLIENT_ID			integer NOT NULL,
    STOCK_ID			integer NOT NULL,
    DATE				date NOT NULL,
    NOTE				tet NOT NULL,
    DEBIT				numeric NOT NULL,
    CREDIT				numeric NOT NULL,
    ENTRY_TYPE			text NOT NULL,
    TRANS_REF_NO		text,
	CREATED_TS 			timestamp with time zone NOT NULL,
	UPDATED_TS 			timestamp with time zone NOT NULL,
    CONSTRAINT STOCK_TRANSACTION_KEY PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;

ALTER TABLE jmsc.STOCK_TRANSACTION OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.STOCK_TRANSACTION TO jmscdev;

--INDEXES
CREATE INDEX INDEX_STOCK_TRANSACTION_1 ON jmsc.STOCK_TRANSACTION(ID, CLIENT_ID, DATE);
CREATE INDEX INDEX_STOCK_TRANSACTION_2 ON jmsc.STOCK_TRANSACTION(CLIENT_ID, STOCK_ID, DATE);
