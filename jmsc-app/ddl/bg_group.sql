-----------------------------------------------------------------
--				 Sequence 
-----------------------------------------------------------------
DROP SEQUENCE IF EXISTS jmsc.bg_group_seq;

CREATE SEQUENCE jmsc.bg_group_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
    
ALTER SEQUENCE jmsc.bg_group_seq OWNER TO jmscdev;
GRANT ALL ON SEQUENCE jmsc.bg_group_seq TO jmscdev;

-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.BG_GROUP CASCADE;

CREATE TABLE jmsc.BG_GROUP
(
    ID 					integer NOT NULL DEFAULT nextval('jmsc.bg_group_seq'::regclass),
    CLIENT_ID 			integer NOT NULL,
    GROUP_NAME 			character varying(50) NOT NULL,
    GROUP_INFO 			character varying(200) NOT NULL,
    GROUP_LIMIT 		integer,
   	CREATED_TS 			timestamp with time zone NOT NULL,
	UPDATED_TS 			timestamp with time zone NOT NULL,
    CONSTRAINT bg_group_key PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;

ALTER TABLE jmsc.BG_GROUP OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.BG_GROUP TO jmscdev;