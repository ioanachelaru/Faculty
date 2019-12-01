
-------------------------- Exercitiul 1 -----------------------------


------------ Validarea datelor din cele 3 tablele --------------------
CREATE FUNCTION ValidareDate (@strada VARCHAR(30), @nr int, @bloc int, @disponibilitatea VARCHAR(30), @suprafata real, @descriere VARCHAR(30),@idOras int) RETURNS INT AS
BEGIN
	DECLARE @return INT
	SET @return=1

	IF(@strada IS null OR @strada = '')
	SET @return=0
	IF(NOT(@disponibilitatea IN ('disponibil','indisponibil')))
	SET @return=0
	IF(@descriere IS null OR @descriere = '')
	SET @return=0
	IF @nr < 1
	SET @return=0
	IF @bloc < 1
	SET @return=0
	IF @suprafata < 10
	SET @return=0
	IF(NOT (EXISTS(SELECT * FROM Orase WHERE Id_oras = @idOras)))
	SET @return=0

	RETURN @return
END
GO

------------ Procedura adaugare -----------------
GO
CREATE PROCEDURE AdaugareCuRollback (@strada VARCHAR(30), @nr int, @bloc int, @disponibilitatea VARCHAR(30), @suprafata real, @descriere VARCHAR(30),@idOras int)
AS
BEGIN
	BEGIN TRAN
		BEGIN TRY
			IF(dbo.ValidareDate(@strada, @nr, @bloc, @disponibilitatea, @suprafata, @descriere,@idOras) = 0)
			BEGIN
				RAISERROR('DATE INVALIDE!',15,1)
			END
			DECLARE @idAdresa int,@idTip int, @idImobil int
			
			SET @idAdresa= (SELECT MAX(Id_adresa) FROM Adrese)+1
			SET @idTip = (SELECT MAX(Id_tip) FROM Tipuri_Imobile)+1
			SET @idImobil = (SELECT MAX(Id_imobil) FROM Imobile)+1
			INSERT INTO Adrese VALUES(@idAdresa,@strada,@nr,@bloc,@idOras)
			INSERT INTO Tipuri_Imobile VALUES (@idTip,@descriere)
			INSERT INTO Imobile VALUES(@idImobil,@idAdresa,@idTip,@disponibilitatea,@suprafata)
			COMMIT TRAN
			SELECT 'TRANSACTION SUCCESSFUL'
		END TRY
		BEGIN CATCH
			ROLLBACK TRAN
			SELECT 'ROLLBACK'
		END CATCH
END
GO

------- Succes -----
SELECT * FROM Imobile
SELECT * FROM Tipuri_Imobile
SELECT * FROM Adrese
EXEC AdaugareCuRollback @strada = 'Neptun',@nr = 17,@bloc = 4,@disponibilitatea = 'disponibil',@suprafata = 56,@descriere = 'uau',@idOras = 3
SELECT * FROM Imobile
SELECT * FROM Tipuri_Imobile
SELECT * FROM Adrese

------- Fail -------
SELECT * FROM Imobile
SELECT * FROM Tipuri_Imobile
SELECT * FROM Adrese
EXEC AdaugareCuRollback @strada = 'Meteor',@nr = 7,@bloc = 2,@disponibilitatea = 'indisponibil',@suprafata = 70,@descriere = 'mare',@idOras = 17
SELECT * FROM Imobile
SELECT * FROM Tipuri_Imobile
SELECT * FROM Adrese
GO


---------------------- Exercitiul 2 ----------------------------------

------------ Validare Tipuri_imobile -------------------
GO
CREATE FUNCTION ValidareTipuriImobile (@descriere VARCHAR(30)) RETURNS INT
AS
BEGIN
	DECLARE @return INT
	SET @return = 1

	IF(@descriere IS null OR @descriere = '')
		SET @return = 0

	RETURN @return
END
GO

------------ Validare Adrese -------------------
GO
CREATE FUNCTION ValidareAdrese (@strada VARCHAR(30),@nr int,@bloc int,@idOras int) RETURNS INT
AS
BEGIN
	DECLARE @return INT
	SET @return = 1

	IF(@strada IS null OR @strada = '')
	SET @return=0
	IF @nr < 1
	SET @return=0
	IF @bloc < 1
	SET @return=0
	IF(NOT (EXISTS(SELECT * FROM Orase WHERE Id_oras = @idOras)))
	SET @return=0

	RETURN @return
END
GO

------------ Validare Imobile -------------------
GO
CREATE FUNCTION ValidareImobile (@disponibilitatea VARCHAR(30),@suprafata real) RETURNS INT
AS
BEGIN
	DECLARE @return INT
	SET @return = 1

	SET @return=0
	IF(NOT(@disponibilitatea IN ('disponibil','indisponibil')))
	SET @return=0
	IF @suprafata < 10
	SET @return=0

	RETURN @return
END
GO

--------------------- Procedura adaugare -------------------------------
GO
CREATE PROCEDURE AdaugareSeparata (@strada VARCHAR(30), @nr int, @bloc int, @disponibilitatea VARCHAR(30), @suprafata real, @descriere VARCHAR(30),@idOras int)
AS
BEGIN
	
	DECLARE @idAdresa int,@idTip int, @idImobil int
	---- Tipuri imobile -----
		BEGIN TRAN
		BEGIN TRY
			IF(dbo.ValidareTipuriImobile(@descriere) = 0)
			BEGIN
				RAISERROR('Descrierea imobilului incorecta!',15,1)
			END

			SET @idTip = (SELECT MAX(Id_tip) FROM Tipuri_Imobile)+1
			INSERT INTO Tipuri_Imobile VALUES(@idTip,@descriere)

			COMMIT TRAN
			SELECT 'Tranzactie completa pentru Tipuri_Imobile'
		END TRY
		BEGIN CATCH
			ROLLBACK TRAN
			SELECT 'ROLLBACK Tipuri_Imobile'
		END CATCH

	------- Adrese ---------
		BEGIN TRAN
		BEGIN TRY
			IF(dbo.ValidareAdrese(@strada,@nr,@bloc,@idOras) = 0)
			BEGIN
				RAISERROR('Date Adresa incorecte!',15,1)
			END

			SET @idAdresa = (SELECT MAX(Id_adresa) FROM Adrese)+1
			INSERT INTO Adrese VALUES (@idAdresa,@strada,@nr,@bloc,@idOras)

			COMMIT TRAN
			SELECT 'Tranzactie completa pentru Adrese'
		END TRY
		BEGIN CATCH
			ROLLBACK TRAN
			SELECT 'ROLLBACK Adrese'
		END CATCH

	------ Imobile --------
		BEGIN TRAN
		BEGIN TRY
			IF(dbo.ValidareImobile(@disponibilitatea,@suprafata) = 0)
			BEGIN
				RAISERROR('Date Imobile incorecte!',15,1)
			END

			SET @idImobil = (SELECT MAX(Id_imobil) FROM Imobile)+1
			INSERT INTO Imobile VALUES(@idImobil,@idAdresa,@idTip,@disponibilitatea,@suprafata)
			COMMIT TRAN
		END TRY
		BEGIN CATCH
			ROLLBACK TRAN
			SELECT 'ROLLBACK Imobile' 
		END CATCH
END
GO

------- Corect -----------
SELECT * FROM Imobile
SELECT * FROM Tipuri_Imobile
SELECT * FROM Adrese
EXEC AdaugareSeparata @strada = 'Mercur',@nr = 23,@bloc = 4,@disponibilitatea = 'disponibil',@suprafata = 55,@descriere = 'minunat',@idOras = 2
SELECT * FROM Imobile
SELECT * FROM Tipuri_Imobile
SELECT * FROM Adrese

------- Fails at Adrese -------
SELECT * FROM Imobile
SELECT * FROM Tipuri_Imobile
SELECT * FROM Adrese
EXEC AdaugareSeparata @strada = '',@nr = 17,@bloc = 4,@disponibilitatea = 'disponibil',@suprafata = 56,@descriere = 'fain',@idOras = 3
SELECT * FROM Imobile
SELECT * FROM Tipuri_Imobile
SELECT * FROM Adrese

------- Fails at Tipuri_Imobile ---------
SELECT * FROM Imobile
SELECT * FROM Tipuri_Imobile
SELECT * FROM Adrese
EXEC AdaugareSeparata @strada = 'Neptun',@nr = 17,@bloc = 4,@disponibilitatea = 'disponibil',@suprafata = 56,@descriere = '',@idOras = 3
SELECT * FROM Imobile
SELECT * FROM Tipuri_Imobile
SELECT * FROM Adrese

--------- Fails at Imobile ----------
SELECT * FROM Imobile
SELECT * FROM Tipuri_Imobile
SELECT * FROM Adrese
EXEC AdaugareSeparata @strada = 'Margaretelor',@nr = 12,@bloc = 2,@disponibilitatea = '',@suprafata = 43,@descriere = 'mic',@idOras = 1
SELECT * FROM Imobile
SELECT * FROM Tipuri_Imobile
SELECT * FROM Adrese
GO