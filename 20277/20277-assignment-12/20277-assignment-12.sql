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

-- b
create function trigf1() returns trigger as $$
declare
	votes_to_add integer;
begin
	votes_to_add := new.nofvotes;
	if TG_OP='UPDATE' then 
		votes_to_add := votes_to_add - old.nofvotes;
	end if;
	-- add votes to existing running relation
	update running
		set totalvotes = totalvotes + votes_to_add
		where edate = new.edate and pname = new.pname;
	return new;
end;
$$ language plpgsql

create trigger T1
after insert or update
on votes
for each row
execute procedure trigf1();

-- c
insert into election (edate, kno)
values ('2019-04-09', 1),
	('2019-09-17', 2),
	('2020-03-02', 3),
	('2021-03-23', 4),
	('2022-11-01', 5);

insert into party (pname, symbol)
values ('nature party', 'np'),
	('science group', 'sg'),
	('life party', 'lp'),
	('art group', 'ag'),
	('lost group', 'lg');

insert into running (edate, pname, chid)
values ('2019-04-09', 'nature party', 12345),
	('2019-04-09', 'life party', 54321),
	('2019-04-09', 'lost group', 34567),
	('2019-09-17', 'lost group', 76543),
	('2019-09-17', 'art group', 67890),
	('2020-03-02', 'science group', 90876),
	('2020-03-02', 'nature party', 55555),
	('2020-03-02', 'life party', 54321);

insert into city(cid, cname, region)
values (22, 'ryde end', 'north'),
	(77, 'east strat', 'south'),
	(33, 'grandetu', 'center'),
	(88, 'royalpre', 'hills'),
	(11, 'carlpa', 'hills'),
	(44, 'lommont', 'north'),
	(66, 'grand sen', 'south'),
	(99, 'kingo heaven', 'hills'),
	(55, 'el munds', 'south');

insert into votes (cid, edate, pname, nofvotes)
values (22, '2020-03-02', 'nature party', 100),
	(22, '2020-03-02', 'science group', 30),
	(22, '2020-03-02', 'life party', 500),
	(77, '2020-03-02', 'nature party', 300),
	(77, '2020-03-02', 'science group', 150),
	(77, '2020-03-02', 'life party', 25),
	(33, '2020-03-02', 'nature party', 13),
	(33, '2020-03-02', 'science group', 740),
	(33, '2020-03-02', 'life party', 670);

-- d1
