-- Table Creation Queries!

CREATE TABLE flight (
                  fid int IDENTITY,
                  airline_code VARCHAR(5) NOT NULL,
                  flight_num VARCHAR(5) NOT NULL,
                  flight_date VARCHAR(10) NOT NULL,
                  origin VARCHAR(5) NOT NULL,
                  dest VARCHAR(5) NOT NULL,
                  dep_time VARCHAR(10) NOT NULL,
                  arr_time VARCHAR(10) NOT NULL,
                  airplane_model VARCHAR(10),
                  PRIMARY KEY (fid));

CREATE TABLE seat_class (
            sid int IDENTITY,
            class_name char NOT NULL,
            origin VARCHAR(3) NOT NULL,
            dest VARCHAR(3) NOT NULL,
            airline_code VARCHAR(5) NOT NULL,
            adult_price int NOT NULL,
            child_price int NOT NULL,
            infant_price int NOT NULL,
            PRIMARY KEY (sid));

CREATE TABLE flight_seat_class (
			fid int,
			sid int,
			PRIMARY KEY (fid, sid),
			FOREIGN KEY (fid) REFERENCES flight ON DELETE CASCADE,
			FOREIGN KEY (sid) REFERENCES seat_class ON DELETE CASCADE);


CREATE TABLE reserve (
      		token VARCHAR(50),
      		fid int,
      		sid int,
          adult_count int NOT NULL,
          child_count int NOT NULL,
          infant_count int NOT NULL,
            PRIMARY KEY (token),
            FOREIGN KEY (sid) REFERENCES seat_class ON DELETE CASCADE,
            FOREIGN KEY (fid) REFERENCES flight ON DELETE CASCADE);


CREATE TABLE person (
      		pid VARCHAR(50),
      		first_name VARCHAR(50) NOT NULL,
      		sur_name VARCHAR(50) NOT NULL,
      		gender VARCHAR(4) NOT NULL,
      		type VARCHAR(7) NOT NULL,
      		nationalId VARCHAR(20) NOT NULL,
      		  PRIMARY KEY (pid));


CREATE TABLE ticket (
			tid VARCHAR(50), -- reference code
			rid VARCHAR(50), -- Reserve -> token
			pid VARCHAR(50), -- Person -> ticket number
			  PRIMARY KEY (tid, pid),
			  FOREIGN KEY (rid) REFERENCES reserve ON DELETE CASCADE,
			  FOREIGN KEY (pid) REFERENCES person ON DELETE CASCADE);

CREATE TABLE behx (
  sid int,
	time timestamp default current_timestamp)

  DROP TABLE ticket
  DROP TABLE reserve
  DROP TABLE person
  DROP TABLE flight_seat_class
  DROP TABLE seat_class
  DROP TABLE flight