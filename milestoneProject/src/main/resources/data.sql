TRUNCATE TABLE dbo.shows;
BULK INSERT dbo.shows
FROM 'C:\Users\shewa\Downloads\netflix_data.csv'
WITH(FORMAT='CSV')