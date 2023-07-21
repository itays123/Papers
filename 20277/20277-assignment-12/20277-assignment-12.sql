-- a

CREATE TABLE election (
	edate date,
	kno int,
	primary key (edate)
);

CREATE TABLE party (
	pname char(20),
	symbol char(5),
	primary key (pname)
);

CREATE TABLE running (
	edate date,
	pname char(20),
	chid numeric(5,0),
	totalvotes int default 0,
	primary key (edate, pname),
	foreign key (edate) references election,
	foreign key (pname) references party
);

CREATE TABLE city (
	cid numeric(5,0),
	cname varchar(20),
	region varchar(20),
	primary key (cid)
);

CREATE TABLE votes (
	cid numeric(5,0),
	pname char(20),
	edate date,
	nofvotes int check (nofvotes > 0),
	primary key (cid, pname, edate),
	foreign key (cid) references city,
	foreign key (pname) references party,
	foreign key (edate) references election
);