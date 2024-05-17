-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.bid_cost_seq;

CREATE SEQUENCE jmsc.bid_cost_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.bid_cost_seq OWNER TO jmscdev;
GRANT ALL ON SEQUENCE jmsc.bid_cost_seq TO jmscdev;

-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.BID_COST_LINKAGE CASCADE;

CREATE TABLE jmsc.BID_COST
(
    ID 					integer NOT NULL DEFAULT nextval('jmsc.bid_cost_seq'::regclass),
    CLIENT_ID 			integer NOT NULL,
    BID_ID 				integer NOT NULL,
    FEE_DETAILS			bytea,
    EMD_DETAILS			bytea,
    OTHER_BIDDING_COST	bytea,
   	CREATED_TS 			timestamp with time zone NOT NULL,
	UPDATED_TS 			timestamp with time zone NOT NULL,
    CONSTRAINT bid_cost_key PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;

ALTER TABLE jmsc.BID_COST OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.BID_COST TO jmscdev;