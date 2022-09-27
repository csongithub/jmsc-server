-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.party_acc_lnkg_seq;

CREATE SEQUENCE jmsc.party_acc_lnkg_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.party_acc_lnkg_seq OWNER TO jmscdev;
GRANT ALL ON SEQUENCE jmsc.party_acc_lnkg_seq TO jmscdev;

-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.PARTY_ACCOUNT_LINKAGE CASCADE;

CREATE TABLE jmsc.PARTY_ACCOUNT_LINKAGE
(
    ID 					integer NOT NULL DEFAULT nextval('jmsc.party_acc_lnkg_seq'::regclass),
    CLIENT_ID			integer NOT NULL,
    PARTY_ID			integer NOT NULL,
    ACCOUNT_ID			integer NOT NULL,
	CREATED_TS 			timestamp with time zone NOT NULL,
	UPDATED_TS 			timestamp with time zone NOT NULL,
    CONSTRAINT PARTY_ACCOUNT_LINKAGE_KEY PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;

ALTER TABLE jmsc.PARTY_ACCOUNT_LINKAGE OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.PARTY_ACCOUNT_LINKAGE TO jmscdev;
