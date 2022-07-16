-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.machine_seq;

CREATE SEQUENCE jmsc.machine_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.machine_seq OWNER TO jmscdev;
GRANT ALL ON SEQUENCE jmsc.machine_seq TO jmscdev;

-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.MACHINE CASCADE;

CREATE TABLE jmsc.MACHINE
(
    ID 					integer NOT NULL DEFAULT nextval('jmsc.machine_seq'::regclass),
    CLIENT_ID 			integer NOT NULL,
    NAME				character varying(50) NOT NULL,
    OWNER				character varying(50) NOT NULL,
    MACHINE_TYPE		character varying(50),
    REGI_NO				character varying(50),
    REGI_DATE			date,
    CHASIS_NO			character varying(50),
    ENGINE_NO			character varying(50),
    INSURANCE_EXPIRY	date,
    PERMIT_EXPIRY		date,
    TAX_EXPIRY			date,
    POLLUTION_EXPIRY	date,
    FITNESS_EXPIRY		date,
   	CREATED_TS 			timestamp with time zone NOT NULL,
	UPDATED_TS 			timestamp with time zone NOT NULL,
    CONSTRAINT machine_key PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;

ALTER TABLE jmsc.MACHINE OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.MACHINE TO jmscdev;