INSERT INTO roles (role_name, description) VALUES ('USER', 'User role'),
                                                 ('CRITIC', 'Critic role'),
                                                 ('MODERATOR', 'Moderator role');
INSERT INTO genres (description, name) VALUES ('Action', 'Action movies.'),
                                              ('Drama', 'Drama movies.');

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
