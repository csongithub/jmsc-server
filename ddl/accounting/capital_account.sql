-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.capital_account_seq;

CREATE SEQUENCE jmsc.capital_account_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.capital_account_seq OWNER TO jmscdev;
GRANT ALL ON SEQUENCE jmsc.capital_account_seq TO jmscdev;


-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.CAPITAL_ACCOUNT CASCADE;

CREATE TABLE jmsc.CAPITAL_ACCOUNT
(
    ID 					integer NOT NULL DEFAULT nextval('jmsc.capital_account_seq'::regclass),
    CLIENT_ID			integer NOT NULL,
    ACCOUNT_NAME		text NOT NULL,
    ACCOUNT_TYPE		text NOT NULL,
    BALANCE				numeric NOT NULL,
    ACC_OPEN_DATE		date NOT NULL,
    LAST_UPDATED		date NOT NULL,
    STATUS				text NOT NULL,
	CREATED_TS 			timestamp with time zone NOT NULL,
	UPDATED_TS 			timestamp with time zone NOT NULL,
    CONSTRAINT CAPITAL_ACCOUNT_KEY PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;

ALTER TABLE jmsc.CAPITAL_ACCOUNT OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.CAPITAL_ACCOUNT TO jmscdev;
