-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.creditor_seq;

CREATE SEQUENCE jmsc.creditor_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.creditor_seq OWNER TO jmscdev;
GRANT ALL ON SEQUENCE jmsc.creditor_seq TO jmscdev;


-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.CREDITOR CASCADE;

CREATE TABLE jmsc.CREDITOR
(
    ID 					integer NOT NULL DEFAULT nextval('jmsc.creditor_seq'::regclass),
    CLIENT_ID			integer NOT NULL,
    NAME				text NOT NULL,
    ADDRESS				text NOT NULL,
    PARTY_ID			integer NOT NULL,
    ITEMS				json,
	CREATED_TS 			timestamp with time zone NOT NULL,
	UPDATED_TS 			timestamp with time zone NOT NULL,
    CONSTRAINT CREDITOR_KEY PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;

ALTER TABLE jmsc.CREDITOR OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.CREDITOR TO jmscdev;
