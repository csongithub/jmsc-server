-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.payment_draft_seq;

CREATE SEQUENCE jmsc.payment_draft_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.payment_draft_seq OWNER TO jmscdev;
GRANT ALL ON SEQUENCE jmsc.payment_draft_seq TO jmscdev;


-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.PAYMENT_DRAFT CASCADE;

CREATE TABLE jmsc.PAYMENT_DRAFT
(
    ID 					integer NOT NULL DEFAULT nextval('jmsc.payment_draft_seq'::regclass),
    CLIENT_ID			integer NOT NULL
    STATUS				character varying(50) NOT NULL,
	DRAFT 				bytea NOT NULL,
	CREATED_TS 			timestamp with time zone NOT NULL,
	UPDATED_TS 			timestamp with time zone NOT NULL,
    CONSTRAINT PAYMENT_DRAFT_KEY PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;

ALTER TABLE jmsc.PAYMENT_DRAFT OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.PAYMENT_DRAFT TO jmscdev;
