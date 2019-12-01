------------------------ Exercitiul 3 -----------------------------------

---- Consideram tabela Adrese

-------------- Dirty reads -------------

--- T1 ----
BEGIN TRANSACTION
UPDATE Adrese SET Strada = 'Papadiilor'
WHERE Id_adresa = 1
WAITFOR DELAY '00:00:6'
ROLLBACK TRANSACTION
GO

----------- Non-repeatable reads ---------

--- T1 ----
INSERT INTO Adrese VALUES ((SELECT MAX(Id_adresa) FROM Adrese) + 1,'Hasdeu',45,2,1)
BEGIN TRAN
WAITFOR DELAY '00:00:05'
UPDATE Adrese SET Strada='Papadiilor' WHERE Numar = 5
COMMIT TRAN

---------- Phantom reads ----------------

--- T1 ----
BEGIN TRAN
WAITFOR DELAY '00:00:04'
INSERT INTO Adrese VALUES ((SELECT MAX(Id_adresa) FROM Adrese) + 1,'Pastorului',45,2,1)
COMMIT TRAN
GO

---------- Deadlock -------------------

--- T1 ----
BEGIN TRAN
UPDATE Adrese SET Strada = 'Neptun 1' WHERE Id_adresa = 2
-- this transaction has exclusively lock on table Adrese
WAITFOR DELAY '00:00:10'
UPDATE Tipuri_Imobile SET Descriere_imobil = 'fainnnn 1' WHERE Id_tip = 1
-- this transaction will be blocked because transaction 2 has already blocked our lock on table Tipuri_Imobile
-- so, transaction 1 is blocked on an exclusively block on table Tipuri_Imobile
COMMIT TRAN

SELECT * FROM Adrese
SELECT * FROM Tipuri_Imobile

-- SOLUTION --
-- this transaction is chose as a deadlock, because it has the lowest priority level here (normal)