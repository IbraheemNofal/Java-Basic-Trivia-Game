The database is a MySQL database stored localy containing a schema named project which contains tables "player" and "questions" with the following details.

- Player Table:

Column Name:			Datatype:
UserName			Varchar(35)	- PK - Not null
Highscore			Int		- Not null


- Questions table:

Column Name:			Datatype:
QID				Int 		- PK - Not null
Question			Varchar(100)	- Not null
option1				Varchar(75)	- Not null
option2				Varchar(75)	- Not null
option3				Varchar(75)	- Not null
answer				Varchar(75)	- Not null
difficulty			Enum('Easy' , 'Medium', 'Hard')