-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.creditor_pl_seq;

CREATE SEQUENCE jmsc.creditor_pl_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.creditor_pl_seq OWNER TO jmscdev;
GRANT ALL ON SEQUENCE jmsc.creditor_pl_seq TO jmscdev;


-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.CREDITOR_PAYMENT_LINKAGE CASCADE;

CREATE TABLE jmsc.CREDITOR_PAYMENT_LINKAGE
(
    ID 					integer NOT NULL DEFAULT nextval('jmsc.creditor_pl_seq'::regclass),
    CLIENT_ID			integer NOT NULL,
   	PARTY_ID			integer NOT NULL,
   	CREDITOR_ID			integer NOT NULL,
   	PAYMENT_ID			integer NOT NULL,
   	STATUS				text NOT NULL,
	CREATED_TS 			timestamp with time zone NOT NULL,
	UPDATED_TS 			timestamp with time zone NOT NULL,
    CONSTRAINT CREDITOR_PL_KEY PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;

ALTER TABLE jmsc.CREDITOR_PAYMENT_LINKAGE OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.CREDITOR_PAYMENT_LINKAGE TO jmscdev;
