DROP TABLE IF EXISTS book_to_book_store;
DROP TABLE IF EXISTS book_store;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS author;
DROP TABLE IF EXISTS language;
DROP SEQUENCE IF EXISTS s_author_id;
DROP SEQUENCE IF EXISTS s_book_id;

CREATE SEQUENCE s_author_id START WITH 1;
CREATE SEQUENCE s_book_id START WITH 1;

CREATE TABLE language (
    id char(2)  NOT NULL,
    description varchar(50)  NULL,

    PRIMARY KEY (id)
);

CREATE TABLE author (
    id INT NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50) NOT NULL,
    date_of_birth DATE,
    
    PRIMARY KEY (id)
);

CREATE TABLE book (
    id INT NOT NULL,
    author_id INT NOT NULL,
    title VARCHAR(400) NOT NULL,
    published_in INT,
    language_id char(2) NOT NULL,
    
    PRIMARY KEY (id),
    FOREIGN KEY (author_id) REFERENCES author(id),
    FOREIGN KEY (language_id) REFERENCES language(id)
);

CREATE TABLE book_store (
    name VARCHAR(400) NOT NULL,

    PRIMARY KEY(name)
);

CREATE TABLE book_to_book_store (
    book_store_name VARCHAR(400) NOT NULL,
    book_id INTEGER NOT NULL,
    stock INTEGER,

    PRIMARY KEY(book_store_name, book_id),
    FOREIGN KEY (book_store_name) REFERENCES book_store (name) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES book (id) ON DELETE CASCADE
);

INSERT INTO author VALUES (next value for s_author_id, 'George', 'Orwell', '1903-06-25');
INSERT INTO author VALUES (next value for s_author_id, 'Paulo', 'Coelho', '1947-08-24');

INSERT INTO language VALUES ('EN', 'English');
INSERT INTO language VALUES ('DE', 'German');
INSERT INTO language VALUES ('FR', 'French');
INSERT INTO language VALUES ('PT', 'Portuguese');

INSERT INTO book VALUES (next value for s_book_id, 1, '1984'        , 1948, 'EN');
INSERT INTO book VALUES (next value for s_book_id, 1, 'Animal Farm' , 1945, 'EN');
INSERT INTO book VALUES (next value for s_book_id, 2, 'O Alquimista', 1988, 'PT');
INSERT INTO book VALUES (next value for s_book_id, 2, 'Brida'       , 1990, 'DE');

INSERT INTO book_store (name) VALUES
	('Amazon'),
	('Barnes and Noble'),
	('Payot');

INSERT INTO book_to_book_store VALUES
	('Amazon', 1, 10),
	('Amazon', 2, 10),
	('Amazon', 3, 10),
	('Barnes and Noble', 1, 1),
	('Barnes and Noble', 3, 2),
	('Payot', 3, 1);
