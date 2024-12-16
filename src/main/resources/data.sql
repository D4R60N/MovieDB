INSERT INTO roles (role_name, description) VALUES ('USER', 'User role'),
                                                 ('CRITIC', 'Critic role'),
                                                 ('MODERATOR', 'Moderator role');
INSERT INTO genres (description, name) VALUES ('Action', 'Action movies.'),
                                              ('Drama', 'Drama movies.'),
                                              ('Comedy', 'Comedy movies.'),
                                              ('Horror', 'Horror movies.'),
                                              ('Thriller', 'Thriller movies.'),
                                              ('Science Fiction', 'Science Fiction movies.'),
                                              ('Fantasy', 'Fantasy movies.'),
                                              ('Mystery', 'Mystery movies.'),
                                              ('Documentary', 'Documentary movies.'),
                                              ('Romance', 'Romance movies.');

INSERT Into profiles (id) VALUES (1), (2), (3);

INSERT INTO users (username, password, email, role_id, profile_id) VALUES ('user', '$2a$12$byqBMBHJ1m20mYaOfPi6u.0TnaY1akAOM.5TDHCJmAB6AkNhNAVRa', 'user@user.com', 1, 1),
                                                            ('critic', '$2a$12$byqBMBHJ1m20mYaOfPi6u.0TnaY1akAOM.5TDHCJmAB6AkNhNAVRa', 'critic@critic.com', 2, 2),
                                                            ('moderator', '$2a$12$byqBMBHJ1m20mYaOfPi6u.0TnaY1akAOM.5TDHCJmAB6AkNhNAVRa', 'moderator@moderator.com', 3, 3);

CREATE PROCEDURE checkPassword(IN username VARCHAR(255), IN password VARCHAR(255), OUT is_valid BOOLEAN)
BEGIN
    SELECT EXISTS(SELECT * FROM users WHERE username = username AND password = password) INTO is_valid;
END;

CREATE PROCEDURE checkUsername(IN username VARCHAR(255), OUT is_valid BOOLEAN)
BEGIN
    SELECT EXISTS(SELECT * FROM users WHERE username = username) INTO is_valid;
END;

INSERT INTO movies (title, description, genre_id, release_date, actors, director, duration, trailer)
VALUES ('The Lord of the Rings: The Fellowship of the Ring', 'A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.', 7, '2001-12-19', 'Elijah Wood, Ian McKellen, Orlando Bloom', 'Peter Jackson', 178, 'V75dMMIW2B4'),
       ('The Lord of the Rings: The Two Towers', 'While Frodo and Sam edge closer to Mordor with the help of the shifty Gollum, the divided fellowship makes a stand against Sauron''s new ally, Saruman, and his hordes of Isengard.', 7, '2002-12-18', 'Elijah Wood, Ian McKellen, Orlando Bloom', 'Peter Jackson', 179, 'LbfMDwc4azU'),
       ('The Lord of the Rings: The Return of the King', 'The former Fellowship of the Ring prepare for the final battle. While Frodo and Sam approach Mount Doom to destroy the One Ring, Aragorn leads the forces of good against Sauron''s evil army at the stone city of Minas Tirith.', 7, '2003-12-17', 'Elijah Wood, Ian McKellen, Orlando Bloom', 'Peter Jackson', 201, 'r5X-hFf6Bwo'),
       ('Harry Potter and the Philosopher\'s Stone', 'A school of witchcraft and wizardry, where he learns the truth about himself, his family and the terrible evil that haunts the magical world.', 7, '2001-11-16', 'Daniel Radcliffe, Rupert Grint, Emma Watson', 'Chris Columbus', 152, 'VyHV0BRtdxo'),
       ('Harry Potter and the Chamber of Secrets', 'An ancient prophecy seems to be coming true when a mysterious presence begins stalking the corridors of a school of magic and leaving its victims paralyzed.', 7, '2002-11-15', 'Daniel Radcliffe, Rupert Grint, Emma Watson', 'Chris Columbus', 161, '1bq0qff4iF8'),
       ('Airplane!', 'Leslie Nielsen and Robert Hays star in this classic comedy. An airplane crew takes ill. Surely the only person capable of landing the plane is an ex-pilot afraid to fly. But don''t call him Shirley.', 3, '1980-07-02', 'Robert Hays, Julie Hagerty, Leslie Nielsen', 'Jim Abrahams, David Zucker, Jerry Zucker', 88, '07pPmCfKi3U'),
       ('The Shining', 'A family heads to an isolated hotel for the winter where a sinister presence influences the father into violence, while his psychic son sees horrific forebodings from both past and future.', 4, '1980-06-13', 'Jack Nicholson, Shelley Duvall, Danny Lloyd', 'Stanley Kubrick', 146, '5Cb3ik6zP2I'),
       ('The Exorcist' , 'When the young daughter of Regan MacNeil starts acting odd and strange occurrences happen around the house, her mother seeks the help of a priest who is convinced that Regan is possessed by a demonic entity.', 4, '1973-12-26', 'Ellen Burstyn, Max von Sydow, Linda Blair', 'William Friedkin', 122, 'YDGw1MTEe9k'),
       ('Puss in Boots', 'An outlaw cat, his childhood egg-friend and a seductive thief kitty set out in search for the eggs of the fabled Golden Goose to clear his name, restore his lost honor and regain the trust of his mother and town.', 3, '2011-10-28', 'Antonio Banderas, Salma Hayek, Zach Galifianakis', 'Chris Miller', 90, '55gmAtakjJ4'),
       ('The Dark Knight', 'When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.', 1, '2008-07-18', 'Christian Bale, Heath Ledger, Aaron Eckhart', 'Christopher Nolan', 152, 'EXeTwQWrcwY'),
       ('The Dark Knight Rises', 'Eight years after the Joker''s reign of anarchy, Batman, with the help of the enigmatic Catwoman, is forced from his exile to save Gotham City from the brutal guerrilla.' , 1, '2012-07-20', 'Christian Bale, Tom Hardy, Anne Hathaway', 'Christopher Nolan', 164, 'g8evyE9TuYk'),
       ('Inception', 'A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.', 8, '2010-07-16', 'Leonardo DiCaprio, Joseph Gordon-Levitt, Ellen Page', 'Christopher Nolan', 148, 'YoHD9XEInc0'),
       ('Interstellar', 'A team of explorers travel through a wormhole in space in an attempt to ensure humanity''s survival.', 6, '2014-11-07', 'Matthew McConaughey, Anne Hath', 'Christopher Nolan', 169, 'zSWdZVtXT7E'),
       ('The Prestige', 'After a tragic accident, two stage magicians engage in a battle to create the ultimate illusion while sacrificing everything they have to outwit each other.', 8, '2006-10-20', 'Christian Bale, Hugh Jackman, Scarlett Johansson', 'Christopher Nolan', 130, 'o4gHCmTQDVI'),
       ('The Departed', 'An undercover cop and a mole in the police attempt to identify each other while infiltrating an Irish gang in South Boston.', 2, '2006-10-06', 'Leonardo DiCaprio, Matt Damon, Jack Nicholson', 'Martin Scorsese', 151, 'auYbpnEwBBg');