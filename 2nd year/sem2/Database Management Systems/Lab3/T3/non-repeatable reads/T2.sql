USE Lab3_DBMS
GO

SET TRAN ISOLATION LEVEL READ COMMITTED
BEGIN TRAN
SELECT * FROM Store
WAITFOR DELAY '00:00:05'
SELECT * FROM Store
COMMIT TRAN

--DELETE FROM Store WHERE ID = 2