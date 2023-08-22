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
	(33, '2020-03-02', 'life party', 670),
	(22, '2019-04-09', 'nature party', 1000);

-- d1
select pname, nofvotes
from city natural join votes
where cname='ryde end' and edate='2020-03-02'

-- d2
select pname, region, sum(nofvotes) as region_votes
from city natural join votes natural join running natural join election
where kno = 3
group by pname, region;

-- d3
select cname, region
from city
where cid not in (select cid
				 from votes
				 where pname='life party')

-- d4
with running_count(edate, nofparties) as (select edate, count(pname) from running group by edate)
select edate, kno
from running_count natural join election
where nofparties >= all (select nofparties from running_count)

-- d5
with available_parties(pname, totalvotes) as (
	select pname, sum(totalvotes) as totalvotes
	from running
	where pname not in (
			select pname
			from votes natural join city
			where region='hills')
		and pname in (
			select pname
			from running natural join election
			where kno=3 
		)
	group by pname)
select pname
from available_parties
where totalvotes <= all (select totalvotes from available_parties)

-- d6
with maxvotes(votes) as (select max(totalvotes) from running natural join election where kno=3)
select pname
from running natural join election, maxvotes
where kno=3 and totalvotes < maxvotes.votes and 
	totalvotes >= all (select totalvotes from running natural join election where kno=3 and totalvotes < maxvotes.votes)

-- d7
select r1.pname, r2.pname
from running as r1, running as r2
where r1.edate = r2.edate and r1.pname < r2.pname
group by r1.pname, r2.pname
having count(r1.edate) = (select count(*) from running where pname=r1.pname)
	and count(r1.edate) = (select count(*) from running where pname=r2.pname)