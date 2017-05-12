-- Table Creation Queries!

CREATE TABLE flight (
                  fid int IDENTITY,
                  airline_code VARCHAR(5) NOT NULL,
                  flight_num VARCHAR(5) NOT NULL,
                  flight_date VARCHAR(10) NOT NULL,
                  origin VARCHAR(3) NOT NULL,
                  dest VARCHAR(3) NOT NULL,
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


DROP TABLE flight
DROP TABLE seat_class
DROP TABLE flight_seat_class