-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.user_permissions_sequence;

CREATE SEQUENCE jmsc.user_permissions_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.user_permissions_sequence OWNER TO jmscdev;
GRANT ALL ON SEQUENCE jmsc.user_permissions_sequence TO jmscdev;

-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.USER_PERMISSIONS CASCADE;

CREATE TABLE jmsc.USER_PERMISSIONS
(
    ID 				integer NOT NULL DEFAULT nextval('jmsc.user_permissions_sequence'::regclass),
    CLIENT_ID		integer NOT NULL,
    USER_ID			integer NOT NULL,
  	PERMISSIONS		json,
    CREATED_TS 		timestamp with time zone NOT NULL,
	UPDATED_TS 		timestamp with time zone NOT NULL,
    CONSTRAINT user_permissions_key PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;


ALTER TABLE jmsc.USER_PERMISSIONS OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.USER_PERMISSIONS TO jmscdev;