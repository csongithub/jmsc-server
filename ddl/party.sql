-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.party_seq;

CREATE SEQUENCE jmsc.party_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.party_seq OWNER TO jmscdev;
GRANT ALL ON SEQUENCE jmsc.party_seq TO jmscdev;

-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.PARTY CASCADE;

CREATE TABLE jmsc.PARTY
(
    ID 					integer NOT NULL DEFAULT nextval('jmsc.party_seq'::regclass),
    CLIENT_ID 			integer NOT NULL,
    NAME				character varying(100) NOT NULL,
    NICK_NAME			character varying(100) NOT NULL,
    PARTY_TYPE			character varying(50) NOT NULL,
    MOBILE				character varying(50),
    ADDRESS				character varying(100),
   	CREATED_TS 			timestamp with time zone NOT NULL,
	UPDATED_TS 			timestamp with time zone NOT NULL,
    CONSTRAINT party_key PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;

ALTER TABLE jmsc.PARTY OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.PARTY TO jmscdev;