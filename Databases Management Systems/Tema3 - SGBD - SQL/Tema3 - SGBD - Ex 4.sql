CREATE PROCEDURE procedureA (@stradaOld VARCHAR(50),@stradaNew VARCHAR(50), @descriereOld VARCHAR(50), @descriereNew VARCHAR(50))
AS
BEGIN
	UPDATE Tipuri_Imobile SET Descriere_imobil = @descriereNew WHERE Descriere_imobil = @descriereOld
	WAITFOR DELAY '00:00:02'
	DELETE FROM Adrese WHERE Strada = @stradaOld
END
GO


CREATE PROCEDURE procedureB (@stradaOld VARCHAR(50), @stradaNew VARCHAR(50), @descriereOld VARCHAR(50), @descriereNew VARCHAR(50))
AS
BEGIN
	UPDATE Adrese SET Strada = @stradaNew WHERE Strada = @stradaOld
	WAITFOR DELAY '00:00:02'
	DELETE FROM Tipuri_Imobile WHERE Descriere_imobil = @descriereOld
END
GO