alter table jmsc.BANK_ACCOUNT add column CLIENT_ID integer NOT NULL DEFAULT 1;
alter table jmsc.PARTY_BANK_ACCOUNT add column CLIENT_ID integer NOT NULL DEFAULT 1;
alter table jmsc.PAYMENT_DRAFT add column CLIENT_ID integer NOT NULL DEFAULT 1;

--bid-
alter table jmsc.CREDIT_FACILITY add column BID_ID integer;

--bg-group, loan-group
alter table jmsc.CREDIT_FACILITY add column BG_GROUP_ID integer;
alter table jmsc.CREDIT_FACILITY add column LOAN_ID integer;
alter table jmsc.CREDIT_FACILITY add column	IS_LIEN boolean  NOT NULL default false;
alter table jmsc.CREDIT_FACILITY add column	STATUS character varying(50) NOT NULL default 'ALIVE';

--admin password, sites
alter table jmsc.CLIENT add column ADMIN_PASSWORD character varying(200);
alter table jmsc.BID add column SITE_ID integer;