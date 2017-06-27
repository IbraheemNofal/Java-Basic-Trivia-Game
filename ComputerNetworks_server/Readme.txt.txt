This is the client part of the server, which must be run first. The server connects to a
local database at 127.0.0.1 and then waits for connection from client. The server randomly
queries the database, returns questions and answers of appropriate difficulty to the client,
and then waits for reply. At the end, the server updates the database with the final score of
the player if it's a new highscore.