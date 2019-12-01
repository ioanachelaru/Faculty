CREATE DATABASE Agentie_imobiliara

Use Agentie_imobiliara

CREATE TABLE Firma_de_imobiliare
(Nume_firma varchar(30) PRIMARY KEY);

CREATE TABLE Agenti_imobiliari
(Id_agent int PRIMARY KEY,
Nume varchar(30),
Prenume varchar(30),
Nume_firma varchar(30) FOREIGN KEY REFERENCES Firma_de_imobiliare(Nume_firma));

CREATE TABLE Notari
(Id_notar int PRIMARY KEY,
Nume varchar(30),
Prenume varchar(30),
Procent real);

CREATE TABLE Clienti
(Id_client int PRIMARY KEY,
Nume varchar(30),
Prenume varchar(30));

CREATE TABLE Orase
(Id_oras int PRIMARY KEY,
Nume_oras varchar(30));

CREATE TABLE Tipuri_Imobile
(Id_tip int PRIMARY KEY,
Descriere_imobil varchar(100));

CREATE TABLE Zone
(Id_zona int PRIMARY KEY,
Id_oras int FOREIGN KEY REFERENCES Orase(Id_oras),
Descriere varchar(100));

CREATE TABLE Adrese
(Id_adresa int PRIMARY KEY,
Strada varchar(30),
Numar int,
Bloc int,
Id_oras int FOREIGN KEY REFERENCES Orase(Id_oras));

CREATE TABLE Imobile
(Id_imobil int PRIMARY KEY,
Id_adresa int FOREIGN KEY REFERENCES Adrese(Id_adresa),
Id_tip int FOREIGN KEY REFERENCES Tipuri_imobile(Id_tip),
Disponibilitate varchar(30),
Suprafata real);

CREATE TABLE Tranzactii
(Id_tranzactie int PRIMARY KEY,
Id_imobil int FOREIGN KEY REFERENCES Imobile(Id_imobil),
Id_notar int FOREIGN KEY REFERENCES Notari(Id_notar),
Id_agent int FOREIGN KEY REFERENCES Agenti_Imobiliari(Id_agent),
Id_client int FOREIGN KEY REFERENCES Clienti(Id_client),
Suma real);

ALTER TABLE Zone
ADD Pret real;