-- liquibase formatted sql

-- changeset mikky999:1
CREATE INDEX student_name_ind ON student (name)

-- changeset mikky999:2
CREATE INDEX faculty_name_and_color_ind ON faculty (name, color) --поправила

-- changeset mikky999:3
SELECT name FROM Student