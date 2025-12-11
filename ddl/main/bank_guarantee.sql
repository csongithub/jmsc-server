-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.bg_seq;

CREATE SEQUENCE jmsc.bg_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.bg_seq OWNER TO jmscdev;
GRANT ALL ON SEQUENCE jmsc.bg_seq TO jmscdev;

-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.BANK_GUARANTEE CASCADE;

CREATE TABLE jmsc.BANK_GUARANTEE
(
    ID 					integer NOT NULL DEFAULT nextval('jmsc.bg_seq'::regclass),
    CLIENT_ID 			integer NOT NULL,
    CREATION_TYPE		text NOT NULL,
    BG_TYPE				text NOT NULL,
    BG_NUMBER			text NOT NULL,
    BG_AMOUNT			integer NOT NULL,
    VALID_FROM_DATE		date NOT NULL,
    VALID_TO_DATE		date NOT NULL,
    IN_FAVOUR_OF		text NOT NULL,
    WORK_NAME			text NOT NULL,
    BANK				text NOT NULL,
    SECURITY_TYPE		text NOT NULL,
    STATUS				text NOT NULL,
    BG_CHARGE			integer,
    CHARGE_DATE			date,
    CHARGE_FROM_ACCOUNT	text,
    FILE_ATTACHED		boolean,
    FILE				bytea,
    FILE_NAME			text,
    CONTENT_TYPE		text,
   	CREATED_TS 			timestamp with time zone NOT NULL,
	UPDATED_TS 			timestamp with time zone NOT NULL,
    CONSTRAINT bg_key PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;

ALTER TABLE jmsc.BANK_GUARANTEE OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.BANK_GUARANTEE TO jmscdev;