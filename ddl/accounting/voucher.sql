-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.voucher_seq;

CREATE SEQUENCE jmsc.voucher_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.voucher_seq OWNER TO jmscprod;
GRANT ALL ON SEQUENCE jmsc.voucher_seq TO voucher_seq;

-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.VOUCHER CASCADE;

CREATE TABLE jmsc.VOUCHER
(
    ID 							integer NOT NULL DEFAULT nextval('jmsc.voucher_seq'::regclass),
    CLIENT_ID 					integer NOT NULL,
    
    VOUCHER_NO					text NOT NULL,
    DATE						date NOT NULL,
    ITEMS						json NOT NULL,
    AMOUNT						numeric NOT NULL,
    CAP_ACC_ID					integer NOT NULL,
    PROJECT_ID					integer NOT NULL,
   	CREATED_TS 					timestamp with time zone NOT NULL,
	UPDATED_TS 					timestamp with time zone NOT NULL,
    CONSTRAINT VOUCHER_KEY PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;

ALTER TABLE jmsc.VOUCHER OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.VOUCHER TO jmscdev;