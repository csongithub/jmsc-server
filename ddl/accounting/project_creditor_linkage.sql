-----------------------------------------------------------------
--					Table 
-----------------------------------------------------------------
DROP TABLE IF EXISTS jmsc.PROJECT_CREDITOR_LINKAGE CASCADE;

CREATE TABLE jmsc.PROJECT_CREDITOR_LINKAGE
(
   
    CLIENT_ID			integer NOT NULL,
    PROJECT_ID			integer NOT NULL,
    CREDITOR_ID			integer NOT NULL,
    LEDGER_ID			integer NOT NULL
    
)
WITH (
    OIDS = FALSE
)TABLESPACE pg_default;

ALTER TABLE jmsc.PROJECT_CREDITOR_LINKAGE OWNER to jmscdev;
GRANT ALL ON TABLE jmsc.PROJECT_CREDITOR_LINKAGE TO jmscdev;
