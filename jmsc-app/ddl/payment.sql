-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.payment_seq;

CREATE SEQUENCE jmsc.payment_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.payment_seq OWNER TO jmscdev;
GRANT ALL ON SEQUENCE jmsc.payment_seq TO jmscdev;


-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.PAYMENT CASCADE;

CREATE TABLE jmsc.PAYMENT
(
    ID 					integer NOT NULL DEFAULT nextval('jmsc.payment_seq'::regclass),
    CLIENT_ID			integer NOT NULL,
    PAYMENT				json,		
    PAYMENT_SUMMARY		json,
    PAYMENT_DATE		date,
    STATUS				character varying(50) NOT NULL,
	CREATED_TS 			timestamp with time zone NOT NULL,
	UPDATED_TS 			timestamp with time zone NOT NULL,
    CONSTRAINT PAYMENT_KEY PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;

ALTER TABLE jmsc.PAYMENT OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.PAYMENT TO jmscdev;
