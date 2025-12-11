-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.project_seq;

CREATE SEQUENCE jmsc.project_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.project_seq OWNER TO jmscdev;
GRANT ALL ON SEQUENCE jmsc.project_seq TO jmscdev;

-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.PROJECT CASCADE;

CREATE TABLE jmsc.PROJECT
(
    ID 							integer NOT NULL DEFAULT nextval('jmsc.project_seq'::regclass),
    CLIENT_ID 					integer NOT NULL,
    
    NICK_NAME					text NOT NULL,
    FULL_NAME					text NOT NULL,
    PACKAGE_NO					text NOT NULL,
    AGREEMENT_NO				text NOT NULL,
    AGREEMENT_DATE				date NOT NULL,
    AGREEMENT_AMOUNT			numeric NOT NULL,
    CONST_AMOUNT				numeric NOT NULL,
    MAINT_AMOUNT				numeric NOT NULL,
    OTHER_AMOUNT				numeric NOT NULL,
    SECURITY_AMOUNT				numeric NOT NULL,
   	CREATED_TS 					timestamp with time zone NOT NULL,
	UPDATED_TS 					timestamp with time zone NOT NULL,
    CONSTRAINT project_key PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;

ALTER TABLE jmsc.PROJECT OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.PROJECT TO jmscdev;