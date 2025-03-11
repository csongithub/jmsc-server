-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.supplier_seq;

CREATE SEQUENCE jmsc.supplier_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.supplier_seq OWNER TO jmscdev;
GRANT ALL ON SEQUENCE jmsc.supplier_seq TO jmscdev;

-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.SUPPLIER CASCADE;

CREATE TABLE jmsc.SUPPLIER
(
    ID 							integer NOT NULL DEFAULT nextval('jmsc.supplier_seq'::regclass),
    CLIENT_ID 					integer NOT NULL,
    PARTY_ID					integer NOT NULL,
    SUPPLY_CONFIG				json,
   	CREATED_TS 					timestamp with time zone NOT NULL,
	UPDATED_TS 					timestamp with time zone NOT NULL,
    CONSTRAINT supplier_key PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;

ALTER TABLE jmsc.SUPPLIER OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.SUPPLIER TO jmscdev;