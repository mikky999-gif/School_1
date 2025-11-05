CREATE TABLE students (
id  SERIAL,
name CHAR(30) UNIQUE NOT NULL,
age SMALLINT DEFAULT 20 CHECK (age > 16)
)

CREATE TABLE faculties (
id SERIAL,
name CHAR(30),
color CHAR(10)
)

ALTER TABLE faculties
ADD CONSTRAINT name_color_uniqe UNIQUE (name, color)