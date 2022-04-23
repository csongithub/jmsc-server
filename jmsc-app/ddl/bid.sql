-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.bid_seq;

CREATE SEQUENCE jmsc.bid_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.bid_seq OWNER TO jmscdev;
GRANT ALL ON SEQUENCE jmsc.bid_seq TO jmscdev;

-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.BID CASCADE;

CREATE TABLE jmsc.BID
(
    ID 					integer NOT NULL DEFAULT nextval('jmsc.bid_seq'::regclass),
    CLIENT_ID 			integer NOT NULL,
    DISPLAY_NAME		character varying(200) NOT NULL,
    SOURCE_SITE			character varying(100) NOT NULL,
    AUTHORITY			character varying(200) NOT NULL,
    NIT					character varying(200) NOT NULL,
    NIT_DATE			date NOT NULL,
    TENDER_REF_NUMBER	character varying(50) NOT NULL,
    TENDER_ID			character varying(50) NOT NULL,
    TITLE				character varying(500) NOT NULL,
    WORK_VALUE			integer NOT NULL,
    BIDDING_COST		integer NOT NULL,
    BC_IN_FAVOUR		character varying(100) NOT NULL,
    BC_PAYBLE_AT		character varying(50) NOT NULL,
    EMD_AMOUNT			integer NOT NULL,
    EMD_IN_FAVOUR		character varying(100) NOT NULL,
    EMD_PAYBLE_AT		character varying(50) NOT NULL,
    BANK_CERTIFICATE	integer,
    WORK_PERIOD			integer NOT NULL,
    BID_VALIDITY		integer NOT NULL,
    BID_START_DATE		date NOT NULL,
    BID_END_DATE		date NOT NULL,
    BID_OPENING_DATE	date NOT NULL,
    BIDDING_RATE		float(2),		
   	STATUS				character varying(25) NOT NULL,
   	REASON				character varying(100),
   	BID_SUBMITTED_DATE	date,
   	BID_ID				character varying(50),
   	CREATED_TS 			timestamp with time zone NOT NULL,
	UPDATED_TS 			timestamp with time zone NOT NULL,
    CONSTRAINT bid_key PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;

ALTER TABLE jmsc.BID OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.BID TO jmscdev;