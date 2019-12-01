------------------------ Exercitiul 3 -----------------------------------

---- Consideram tabela Adrese

-------------- Dirty reads -------------

SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED -- unresolved
-- SET TRANSACTION ISOLATION LEVEL READ COMMITTED -- resolved

--- T2 ----
BEGIN TRAN
SELECT * FROM Adrese
WAITFOR DELAY '00:00:8'
SELECT * FROM Adrese
COMMIT TRAN


----------- Non-repeatable reads ---------

--- T2 ----
SET TRANSACTION ISOLATION LEVEL READ COMMITTED -- unresolved
-- SET TRANSACTION ISOLATION LEVEL REPEATABLE READ -- resolved
BEGIN TRAN
SELECT * FROM Adrese
WAITFOR DELAY '00:00:05'
SELECT * FROM Adrese
COMMIT TRAN


---------- Phantom reads ----------------

--- T2 ----
SET TRANSACTION ISOLATION LEVEL REPEATABLE READ -- unresolved
-- SET TRANSACTION ISOLATION LEVEL SERIALIZABLE -- resolved
BEGIN TRAN
SELECT * FROM Adrese
WAITFOR DELAY '00:00:05'
SELECT * FROM Adrese
COMMIT TRAN


---------- Deadlock -------------------

--- T2 ----
-- SET DEADLOCK_PRIORITY HIGH -- solved
SET DEADLOCK_PRIORITY LOW -- unresolved
BEGIN TRAN
UPDATE Tipuri_Imobile SET Descriere_imobil = 'fainnnn 2' WHERE Id_tip = 1
-- this transaction has exclusively lock on table Tipuri_Imobile
WAITFOR DELAY '00:00:10'
UPDATE Adrese SET Strada = 'Neptun 2' WHERE Id_adresa = 2
-- this transaction will be blocked because transaction 1 has already blocked our lock on table Adrese, so, both of the transactions are blocked
COMMIT TRAN
-- after some seconds transaction 2 will be chosen as a deadlock victim and terminates with an error
-- in tables Adrese and Tipuri_Imobile will be the values from transaction 1SELECT * FROM Adrese
SELECT * FROM Tipuri_Imobile-- SOLUTION ---- this transaction has the higher priority level from here (set to HIGH)
-- transaction 1 finish with an error, and ans results are the ones from this transaction 