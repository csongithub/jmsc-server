alter table jmsc.BANK_ACCOUNT add column CLIENT_ID integer NOT NULL DEFAULT 1;
alter table jmsc.PARTY_BANK_ACCOUNT add column CLIENT_ID integer NOT NULL DEFAULT 1;
alter table jmsc.PAYMENT_DRAFT add column CLIENT_ID integer NOT NULL DEFAULT 1;