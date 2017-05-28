DROP DATABASE IF EXISTS moviedb;
CREATE DATABASE moviedb;
USE moviedb;

CREATE TABLE movies (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    year INT NOT NULL,
    director VARCHAR(100) NOT NULL,
    banner_url VARCHAR(200),
    trailer_url VARCHAR(200)
);

CREATE TABLE stars (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    dob DATE,
    photo_url VARCHAR(200)
);

CREATE TABLE stars_in_movies (
    star_id INT NOT NULL,
    movie_id INT NOT NULL,
    FOREIGN KEY (star_id) REFERENCES stars(id),
    FOREIGN KEY (movie_id) REFERENCES movies(id)
);

CREATE TABLE genres (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL
);

CREATE TABLE genres_in_movies (
    genre_id INT NOT NULL,
    movie_id INT NOT NULL,
    FOREIGN KEY (genre_id) REFERENCES genres(id),
    FOREIGN KEY (movie_id) REFERENCES movies(id)
);

CREATE TABLE creditcards (
    id VARCHAR(20) NOT NULL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    expiration DATE NOT NULL
);

CREATE TABLE customers (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    cc_id VARCHAR(20) NOT NULL,
    address VARCHAR(200) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(20) NOT NULL,
    FOREIGN KEY (cc_id) REFERENCES creditcards(id)
);

CREATE TABLE sales (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    customer_id INT NOT NULL,
    movie_id INT NOT NULL,
    sale_date DATE NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (movie_id) REFERENCES movies(id)
);

CREATE TABLE employees (
	email varchar(50) primary key,
	password varchar(20) not null,
	fullname varchar(100)
);
DROP TABLE IF EXISTS ft;
CREATE TABLE ft (
    entryID INT AUTO_INCREMENT,
    entry text,
    PRIMARY KEY (entryID),
    FULLTEXT (entry)) ENGINE=MyISAM;
    
DROP PROCEDURE IF EXISTS add_movie;
DELIMITER $$
CREATE PROCEDURE add_movie(
    new_title VARCHAR(100),
    new_year INT,
    new_director VARCHAR(100),
    new_banner_url VARCHAR(200),
    new_trailer_url VARCHAR(200),
    star_firstname VARCHAR(50),
    star_lastname VARCHAR(50),
    genre_name VARCHAR(32))
BEGIN
    DECLARE orig_movie_id INT;
    DECLARE orig_star_id INT;
    DECLARE linkMovieStar INT;
    DECLARE linkMovieGenre INT;
    DECLARE orig_genre_id INT;
    
    SET orig_movie_id = (SELECT id FROM movies WHERE movies.title = new_title LIMIT 1);
    SET orig_star_id = (SELECT id FROM stars WHERE stars.first_name = star_firstname AND stars.last_name = star_lastname LIMIT 1);
    SET orig_genre_id = (SELECT genres.id FROM genres WHERE genres.name = genre_name LIMIT 1);
    -- Add movie if it doesn't exist
    IF (orig_movie_id IS NULL) THEN
	SET orig_movie_id = (SELECT MAX(id) + 1 FROM movies); 
	INSERT INTO movies(id,title,year,director,banner_url,trailer_url)
	VALUES (orig_movie_id,new_title,new_year,new_director,new_banner_url,new_trailer_url);
	END IF;
    -- Add star if it doesn't exist
    IF (orig_star_id IS NULL) THEN
	SET orig_star_id = (SELECT MAX(id) + 1 FROM stars);
	INSERT INTO stars (id,first_name,last_name)
	VALUES (orig_star_id,star_firstname,star_lastname);
	END IF;
    -- Add genre if it doesn't exist
    IF (orig_genre_id IS NULL) THEN
	SET orig_genre_id = (SELECT MAX(id) + 1 FROM genres);
	INSERT INTO genres (id,name)
	VALUES (orig_genre_id,genre_name);
    END IF;
    -- Link the star to the movie
    SET linkMovieStar = (SELECT SM.star_id from stars_in_movies SM WHERE SM.star_id = orig_star_id and SM.movie_id = orig_movie_id LIMIT 1);
    IF (linkMovieStar IS NULL and orig_movie_id is not null and orig_star_id is not null) THEN 
	INSERT INTO stars_in_movies(star_id,movie_id)
	VALUES (orig_star_id,orig_movie_id);
    END IF;
    -- Link Genre to the movie
    SET linkMovieGenre = (SELECT genre_id FROM genres_in_movies GM WHERE GM.genre_id = orig_genre_id and GM.movie_id = orig_movie_id LIMIT 1); 
    IF (linkMovieGenre IS NULL and orig_movie_id is not null and orig_genre_id is not null) THEN
	INSERT INTO genres_in_movies(genre_id,movie_id)
        VALUES (orig_genre_id,orig_movie_id);
    END IF;
END
$$
DELIMITER ;

DROP PROCEDURE IF EXISTS add_star_movie_map;
DELIMITER $$
CREATE PROCEDURE add_star_movie_map(
    new_title VARCHAR(100),
    star_firstname VARCHAR(50),
    star_lastname VARCHAR(50))
BEGIN
    DECLARE orig_movie_id INT;
    DECLARE orig_star_id INT;
    DECLARE linkMovieStar INT;
    DECLARE linkMovieGenre INT;
    
    SET orig_movie_id = (SELECT id FROM movies WHERE movies.title = new_title LIMIT 1);
    SET orig_star_id = (SELECT id FROM stars WHERE stars.first_name = star_firstname AND stars.last_name = star_lastname LIMIT 1);

   IF orig_star_id is not NULL THEN
	-- Link the star to the movie
	SET linkMovieStar = (SELECT SM.star_id from stars_in_movies SM WHERE SM.star_id = orig_star_id and SM.movie_id = orig_movie_id LIMIT 1);
	IF (linkMovieStar IS NULL and orig_movie_id is not null) THEN 
		INSERT INTO stars_in_movies(star_id,movie_id)
		VALUES (orig_star_id,orig_movie_id);
	END IF;
    END IF;
END
$$
DELIMITER ;

DROP PROCEDURE IF EXISTS add_genre_movie_map;
DELIMITER $$
CREATE PROCEDURE add_genre_movie_map(
    new_title VARCHAR(100),
    genre_name VARCHAR(32))
BEGIN
    DECLARE orig_movie_id INT;
    DECLARE linkMovieGenre INT;
    DECLARE orig_genre_id INT;
    
    SET orig_movie_id = (SELECT id FROM movies WHERE movies.title = new_title LIMIT 1);
    SET orig_genre_id = (SELECT genres.id FROM genres WHERE genres.name = genre_name LIMIT 1);
  
    SET linkMovieGenre = (SELECT genre_id FROM genres_in_movies GM WHERE GM.genre_id = orig_genre_id and GM.movie_id = orig_movie_id LIMIT 1); 
    IF (linkMovieGenre IS NULL and orig_movie_id is not null and orig_genre_id is not null) THEN
	INSERT INTO genres_in_movies(genre_id,movie_id)
        VALUES (orig_genre_id,orig_movie_id);
    END IF;
END
$$
DELIMITER ;


