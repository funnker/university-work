USE Lab3_DBMS
GO

SET TRAN ISOLATION LEVEL SNAPSHOT
BEGIN TRAN
WAITFOR DELAY '00:00:05'
UPDATE Store SET address = 'new2' WHERE id = 99
COMMIT TRAN	