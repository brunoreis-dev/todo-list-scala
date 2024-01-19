CREATE TABLE "todos" (
  id SERIAL PRIMARY KEY,
  description VARCHAR(255),
  completed BOOLEAN
);

drop table "todos" if exists;
