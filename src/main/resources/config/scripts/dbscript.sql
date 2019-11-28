
CREATE TABLE ride(
id serial NOT NULL PRIMARY KEY,
ride_id VARCHAR(50),
offer_code BIGINT,

start_location TEXT,
end_location TEXT,

total_seats integer,
available_seats integer,
ride_date_time timestamp, 
start_coordinate VARCHAR(100),
end_coordinate VARCHAR(100),
encoded_path TEXT,
user_id integer,
departure_date_time timestamp,
charges integer,
vehicle VARCHAR(50),
is_deleted boolean,
CREATED_DATE timestamp, 
UPDATED_DATE timestamp
);

CREATE TABLE ride_user(
id serial NOT NULL PRIMARY KEY,
username VARCHAR(10),
user_email VARCHAR(100),
name VARCHAR(100),
mobile_number VARCHAR(100),
gender VARCHAR(50),
first_name VARCHAR(100),
last_name VARCHAR(100),
CREATED_DATE timestamp, 
UPDATED_DATE timestamp
);

CREATE TABLE ride_request(
id serial NOT NULL PRIMARY KEY,
start_Location VARCHAR(500),
end_Location VARCHAR(500),
ride_Id integer ,
user_Id integer,
booking_id VARCHAR(50),
booking_code integer,
status VARCHAR(50),
ride_charges integer,
is_deleted boolean,
CREATED_DATE timestamp, 
UPDATED_DATE timestamp
);

CREATE TABLE feedback(
id serial NOT NULL PRIMARY KEY,
user_email VARCHAR(100),
rating integer,
comment VARCHAR(100),
operation VARCHAR(50),
CREATED_DATE timestamp, 
UPDATED_DATE timestamp
);


