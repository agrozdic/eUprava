DROP SCHEMA IF EXISTS EUprava15;
CREATE SCHEMA  EUprava15 DEFAULT CHARACTER SET utf8;
USE EUprava15;

CREATE TABLE Korisnici (
	Id bigint  auto_increment PRIMARY KEY,
	Email VARCHAR(50) NOT NULL UNIQUE,
	Lozinka VARCHAR(50) NOT NULL,
    Ime VARCHAR(50) NOT NULL,
    Prezime VARCHAR(50) NOT NULL,
    DatumRodjenja DATE NOT NULL,
    JMBG VARCHAR(13) NOT NULL UNIQUE,
    Adresa VARCHAR(50) NOT NULL,
    BrojTelefona VARCHAR(50) NOT NULL,
    DatumIVremeRegistracije DATETIME NOT NULL DEFAULT NOW(),
    EUloga ENUM('MEDICINSKOOSOBLJE', 'PACIJENT', 'ADMINISTRATOR') DEFAULT 'PACIJENT'
);

CREATE TABLE ProizvodjaciVakcine(
	Id BIGINT auto_increment PRIMARY KEY,
	Proizvodjac VARCHAR(50) NOT NULL,
    DrzavaProizvodnje VARCHAR(50) NOT NULL
);

CREATE TABLE Vakcine (
	Id BIGINT  auto_increment PRIMARY KEY,
	Ime VARCHAR(50) NOT NULL,
    DostupnaKolicina INT default 0,
    ProizvodjacId BIGINT,
	FOREIGN KEY (ProizvodjacId) REFERENCES ProizvodjaciVakcine (Id) 	
    ON DELETE CASCADE
);


CREATE TABLE Vesti (
	Id BIGINT auto_increment PRIMARY KEY,
	NazivVesti VARCHAR(50) NOT NULL,
    SadrzajVesti VARCHAR(50) NOT NULL,
    DatumIVremeObjavljivanja datetime
    
    );
    
CREATE TABLE VestiOObolelima (
	Id BIGINT auto_increment PRIMARY KEY,
	OboleliUPoslednja24h INT,
    TestiraniUPoslednja24h INT,
    Hospitalizovani INT,
    NaRespiratoru INT,
    DatumIVremeObjavljivanja datetime
    
    );
    CREATE TABLE PrimljeneVakcine(
	Id bigint  auto_increment PRIMARY KEY,
	KorisnikId BIGINT,
	VakcinaId BIGINT,
     EDoza ENUM('PRVA', 'DRUGA', 'TRECA', 'CETVRTA') ,
     DatumIVremeVakcinacije DATETIME NOT NULL DEFAULT NOW(),
	FOREIGN KEY (KorisnikId) REFERENCES Korisnici (Id) 	
    ON DELETE CASCADE,
	FOREIGN KEY (VakcinaId) REFERENCES Vakcine (Id) 	
    ON DELETE CASCADE
); 
CREATE TABLE PrijaveZaVakcinaciju(
	Id bigint  auto_increment PRIMARY KEY,
	KorisnikId BIGINT,
	VakcinaId BIGINT,
     EDoza ENUM('PRVA', 'DRUGA', 'TRECA', 'CETVRTA'),
	DatumIVremePrijave DATETIME NOT NULL DEFAULT NOW(),
	FOREIGN KEY (KorisnikId) REFERENCES Korisnici (Id) 	
    ON DELETE CASCADE,
	FOREIGN KEY (VakcinaId) REFERENCES Vakcine (Id) 	
    ON DELETE CASCADE
);

CREATE TABLE Nabavke(
Id bigint  auto_increment PRIMARY KEY,
VakcinaId BIGINT,
Kolicina INT default 0,
DatumIVremeNabavke DATETIME NOT NULL DEFAULT NOW(),
 Komentar VARCHAR(50) ,
EStatus ENUM('POSLAT', 'PRIHVACEN', 'POSLAT_NA_REVIZIJU', 'ODBIJEN') DEFAULT 'POSLAT',
 FOREIGN KEY (VakcinaId) REFERENCES Vakcine (Id) 	
    ON DELETE CASCADE
);


insert into korisnici(email, lozinka, ime, prezime, datumRodjenja, jmbg, adresa, brojTelefona) values 
('ana@gmail.com', 'ana123', 'Ana', 'Anic', '2000-01-01', 1234567890002, 'Adresa 1, Grad', "+381612345678");
insert into korisnici(email, lozinka, ime, prezime, datumRodjenja, jmbg, adresa, brojTelefona) values 
('bojan@gmail.com', 'bojan123', 'Bojan', 'Bojanic', '2000-03-01', 1234567890003, 'Adresa 3, Grad', "+381642345678");
insert into korisnici(email, lozinka, ime, prezime, datumRodjenja, jmbg, adresa, brojTelefona, eUloga) values 
('admin@gmail.com', 'admin123', 'Admin', 'Adminic', '2000-02-01', 1234567890001, 'Adresa 2, Grad', "+381622345678", 'ADMINISTRATOR');
insert into korisnici(email, lozinka, ime, prezime, datumRodjenja, jmbg, adresa, brojTelefona, eUloga) values 
('sanja@gmail.com', 'sanja123', 'Sanja', 'Sanjic', '2000-05-01', 1234567890004, 'Adresa 4, Grad', "+381632345678", 'MEDICINSKOOSOBLJE');
insert into korisnici(email, lozinka, ime, prezime, datumRodjenja, jmbg, adresa, brojTelefona, eUloga) values 
('tihomir@gmail.com', 'tihomir123', 'Tihomir', 'Tihomirovic', '2000-06-01', 1234567890005, 'Adresa 5, Grad', "+381652345678", 'MEDICINSKOOSOBLJE');

insert into ProizvodjaciVakcine( proizvodjac, drzavaproizvodnje) values ('Proizvodjac', 'SAD');
insert into ProizvodjaciVakcine( proizvodjac, drzavaproizvodnje) values ('Proizvodjac', 'UK');
insert into ProizvodjaciVakcine( proizvodjac, drzavaproizvodnje) values ('Proizvodjac', 'Rusija');

insert into vakcine(ime,  ProizvodjacId) values ('Pfizer',  1);
insert into vakcine(ime, ProizvodjacId) values ('Modena', 2);
insert into vakcine(ime, ProizvodjacId) values ('AstraZeneca', 3);

insert into vesti(nazivVesti, sadrzajVesti, DatumIVremeObjavljivanja ) values ('COVID-19 vise nije globalna pandemija', 'Sadrzaj vesti', '2023-01-01 12:00:00' );

insert into vestiOOBolelima(OboleliUPoslednja24h,TestiraniUPoslednja24h , Hospitalizovani,
NaRespiratoru, DatumIVremeObjavljivanja) values (10, 200,  1, 0, '2023-08-31 12:00:00' );

insert into vestiOOBolelima(OboleliUPoslednja24h,TestiraniUPoslednja24h , Hospitalizovani,
NaRespiratoru, DatumIVremeObjavljivanja) values (5, 100,  2, 0, '2023-08-31 12:00:00' );

insert into primljeneVakcine(korisnikid, vakcinaid, edoza) values (1, 2, "PRVA");

insert into prijaveZaVakcinaciju(korisnikid, vakcinaid, edoza) values (1, 2, "DRUGA");

insert into nabavke(vakcinaId, kolicina, komentar) values (2, 3, " ");

SELECT * FROM KORISNICI;
select * from ProizvodjaciVakcine;
select * from vakcine;
select * from vesti;
select * from vestiOObolelima;
select * from primljeneVakcine;
select * from prijaveZaVakcinaciju;
select * from nabavke;