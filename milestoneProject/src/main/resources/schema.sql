IF (NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE  TABLE_NAME = 'shows'))
BEGIN
CREATE TABLE dbo.shows(
	show_id varchar(10) NOT NULL,
	type varchar(max) NULL,
	title varchar(max) NULL,
	director varchar(max) NULL,
	cast varchar(max) NULL,
	country varchar(max) NULL,
	date_added date NULL,
	release_year int NULL,
	rating varchar(max) NULL,
	duration varchar(max) NULL,
	listed_in varchar(max) NULL,
	description varchar(max) NULL,
	PRIMARY KEY (show_id)
)
END;