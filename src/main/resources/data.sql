INSERT INTO roles (role_name, description) VALUES ('USER', 'User role'),
                                                 ('CRITIC', 'Critic role'),
                                                 ('MODERATOR', 'Moderator role');
INSERT INTO genres (description, name) VALUES ("Action", "Action movies."),
                                              ("Drama", "Drama movies.");

CREATE PROCEDURE checkPassword(IN username VARCHAR(255), IN password VARCHAR(255), OUT is_valid BOOLEAN)
BEGIN
    SELECT EXISTS(SELECT * FROM users WHERE username = username AND password = password) INTO is_valid;
END;

CREATE PROCEDURE checkUsername(IN username VARCHAR(255), OUT is_valid BOOLEAN)
BEGIN
    SELECT EXISTS(SELECT * FROM users WHERE username = username) INTO is_valid;
END;