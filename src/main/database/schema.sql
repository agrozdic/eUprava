DROP SCHEMA IF EXISTS eUprava;

CREATE SCHEMA eUprava DEFAULT CHARACTER SET utf8;

USE eUprava;

BEGIN TRANSACTION;

CREATE TABLE Korisnik(
    KorisnikID INT AUTO_INCREMENT PRIMARY KEY,
    Email VARCHAR(50) NOT NULL UNIQUE,
    Lozinka VARCHAR(50) NOT NULL,
    Ime VARCHAR(50) NOT NULL,
    Prezime VARCHAR(50) NOT NULL,
    DatumRodjenja DATE NOT NULL,
    JMBG VARCHAR(13) NOT NULL UNIQUE,
    Adresa VARCHAR(50) NOT NULL,
    BrojTelefona VARCHAR(50) NOT NULL,
    DatumVremeRegistracije DATETIME NOT NULL DEFAULT NOW(),
    Uloga ENUM('PACIJENT', 'MEDICINSKO_OSOBLJE', 'ADMINISTRATOR') DEFAULT 'PACIJENT'
);

CREATE TABLE Proizvodjac(
    ProizvodjacID INT AUTO_INCREMENT PRIMARY KEY,
    Naziv VARCHAR(50) NOT NULL,
    Drzava VARCHAR(50) NOT NULL
);

CREATE TABLE Vakcina (
    VakcinaID INT AUTO_INCREMENT PRIMARY KEY,
    Naziv VARCHAR(50) NOT NULL,
    DostupnaKolicina INT default 0,
    ProizvodjacID INT,
    FOREIGN KEY (ProizvodjacID) REFERENCES Proizvodjac (ProizvodjacID) ON DELETE CASCADE
);

CREATE TABLE Vest (
    VestID INT AUTO_INCREMENT PRIMARY KEY,
    Naslov VARCHAR(50) NOT NULL,
    Sadrzaj VARCHAR(1000) NOT NULL,
    DatumVreme DATETIME
);

CREATE TABLE OboleliVest (
    OboleliVestID INT AUTO_INCREMENT PRIMARY KEY,
    BrObolelih INT,
    BrTestiranih INT,
    BrHospitalizovanih INT,
    BrNaRespiratoru INT,
    DatumVreme DATETIME
);

CREATE TABLE Vakcinacija(
    VakcinacijaID INT AUTO_INCREMENT PRIMARY KEY,
    KorisnikID INT,
    VakcinaID INT,
    DozaVakcine ENUM('PRVA', 'DRUGA', 'TRECA', 'CETVRTA'),
    DatumVreme DATETIME NOT NULL DEFAULT NOW(),
    FOREIGN KEY (KorisnikID) REFERENCES Korisnik (KorisnikID) ON DELETE CASCADE,
    FOREIGN KEY (VakcinaId) REFERENCES Vakcina (VakcinaID) ON DELETE CASCADE
);

CREATE TABLE PrijavaZaVakcinu(
    PrijavaID INT AUTO_INCREMENT PRIMARY KEY,
    KorisnikID INT,
    VakcinaID INT,
    DozaVakcine ENUM('PRVA', 'DRUGA', 'TRECA', 'CETVRTA'),
    DatumVreme DATETIME NOT NULL DEFAULT NOW(),
    FOREIGN KEY (KorisnikID) REFERENCES Korisnik (KorisnikID) ON DELETE CASCADE,
    FOREIGN KEY (VakcinaID) REFERENCES Vakcina (VakcinaID) ON DELETE CASCADE
);

CREATE TABLE Narudzbenica(
    NarudzbenicaID INT AUTO_INCREMENT PRIMARY KEY,
    VakcinaID BINT,
    Kolicina INT DEFAULT 0,
    DatumVreme DATETIME NOT NULL DEFAULT NOW(),
    Komentar VARCHAR(100),
    StatusZahteva ENUM('POSLAT', 'PRIHVACEN', 'POSLAT_NA_REVIZIJU', 'ODBIJEN') DEFAULT 'POSLAT',
    FOREIGN KEY (VakcinaID) REFERENCES Vakcina (VakcinaID) ON DELETE CASCADE
);

INSERT INTO Korisnik(Email, Lozinka, Ime, Prezime, DatumRodjenja, JMBG, Adresa, BrojTelefona)
VALUES ('anaanic@mail.com', 'ana123', 'Ana', 'Anic', '2000-01-01', '0101000865123', 'Adresa 1, Grad', '0612345678')

INSERT INTO Korisnik(Email, Lozinka, Ime, Prezime, DatumRodjenja, JMBG, Adresa, BrojTelefona, Uloga)
VALUES ('bojanbojanic@mail.com', 'bojan123', 'Bojan', 'Bojanic', '2001-02-02', '0202001860123', 'Adresa 2, Grad', '0622345678', 'ADMINISTRATOR')

INSERT INTO Korisnik(Email, Lozinka, Ime, Prezime, DatumRodjenja, JMBG, Adresa, BrojTelefona, Uloga)
VALUES ('canacanic@mail.com', 'cana123', 'Cana', 'Canic', '2002-03-03', '0303002865123', 'Adresa 3, Grad', '0632345678', 'MEDICINSKO_OSOBLJE')

INSERT INTO Korisnik(Email, Lozinka, Ime, Prezime, DatumRodjenja, JMBG, Adresa, BrojTelefona)
VALUES ('dejandejanic@mail.com', 'dejan123', 'Dejan', 'Dejanic', '2003-04-04', '0404003860123', 'Adresa 4, Grad', '0632345678')

INSERT INTO Proizvodjac(Naziv, Drzava) VALUES ('Pfizer', 'SAD');
INSERT INTO Proizvodjac(Naziv, Drzava) VALUES ('Moderna', 'UK');
INSERT INTO Proizvodjac(Naziv, Drzava) VALUES ('AstraZeneca', 'Svedska');

INSERT INTO Vakcina(Naziv, DostupnaKolicina, ProizvodjacID) VALUES ('Pfizer C-001',  1000, 1);
INSERT INTO Vakcina(Naziv, ProizvodjacID) VALUES ('Moderna SZF744', 2);
INSERT INTO Vakcina(Naziv, ProizvodjacID) VALUES ('AZ CVD19', 3);

INSERT INTO Vest(Naslov, Sadrzaj, DatumVreme ) VALUES (
    'WHO Declares End to COVID-19 as Global Health Emergency',
    'The World Health Organization (WHO) Director-General has determined that COVID-19 “is now an established and ongoing health issue which no longer constitutes a public health emergency of international concern (PHEIC).” The announcement comes more than three years after WHO declared the outbreak of the novel coronavirus a global health emergency.',
    '2023-05-10 12:00:00');

INSERT INTO OboleliVest(BrObolelih, BrTestiranih, BrHospitalizovanih, BrNaRespiratoru, DatumVreme) VALUES (5, 200,  0, 0, '2023-08-25 17:00:00');
INSERT INTO OboleliVest(BrObolelih, BrTestiranih, BrHospitalizovanih, BrNaRespiratoru, DatumVreme) VALUES (5, 300,  0, 0, '2023-08-26 17:00:00');
INSERT INTO OboleliVest(BrObolelih, BrTestiranih, BrHospitalizovanih, BrNaRespiratoru, DatumVreme) VALUES (10, 250,  0, 0, '2023-08-27 17:00:00');
INSERT INTO OboleliVest(BrObolelih, BrTestiranih, BrHospitalizovanih, BrNaRespiratoru, DatumVreme) VALUES (10, 500,  0, 0, '2023-08-28 17:00:00');
INSERT INTO OboleliVest(BrObolelih, BrTestiranih, BrHospitalizovanih, BrNaRespiratoru, DatumVreme) VALUES (5, 1000,  0, 0, '2023-08-29 17:00:00');

INSERT INTO Vakcinacija(KorisnikID, VakcinaID, DozaVakcine) VALUES (1, 1, "PRVA");
INSERT INTO Vakcinacija(KorisnikID, VakcinaID, DozaVakcine) VALUES (4, 2, "DRUGA");

INSERT INTO PrijavaZaVakcinu(KorisnikID, VakcinaID, DozaVakcine) VALUES (1, 2, "DRUGA");

INSERT INTO Narudzbenica(VakcinaID, Kolicina, Komentar) VALUES (3, 50, "Hitno!");

SELECT * FROM Korisnik;
SELECT * FROM Proizvodjac;
SELECT * FROM Vakcina;
SELECT * FROM Vest;
SELECT * FROM OboleliVest;
SELECT * FROM Vakcinacija;
SELECT * FROM PrijavaZaVakcinu;
SELECT * FROM Narudzbenica;

COMMIT TRANSACTION;