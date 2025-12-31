-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.stock_seq;

CREATE SEQUENCE jmsc.stock_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.stock_seq OWNER TO jmscdev;
GRANT ALL ON SEQUENCE jmsc.stock_seq TO jmscdev;


-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.STOCK CASCADE;

CREATE TABLE jmsc.STOCK
(
    ID 					integer NOT NULL DEFAULT nextval('jmsc.stock_seq'::regclass),
    CLIENT_ID			integer NOT NULL,
    STOCK_NAME			text NOT NULL,
    UNIT				text NOT NULL,
    BALANCE				numeric NOT NULL,
    CREATION_DATE		date NOT NULL,
    LAST_UPDATED		date NOT NULL,
    STATUS				text NOT NULL,
	CREATED_TS 			timestamp with time zone NOT NULL,
	UPDATED_TS 			timestamp with time zone NOT NULL,
    CONSTRAINT STOCK_KEY PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;

ALTER TABLE jmsc.STOCK_KEY OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.STOCK_KEY TO jmscdev;
