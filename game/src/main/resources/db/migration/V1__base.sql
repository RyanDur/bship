CREATE TABLE games (
  id   INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
  turn INT,
  over BOOLEAN              DEFAULT FALSE,
  name VARCHAR(50) NOT NULL
);

CREATE TABLE boards (
  id      INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  game_id INT NOT NULL,
  winner  BOOLEAN      DEFAULT FALSE,
  CONSTRAINT games_id_fk
  FOREIGN KEY (game_id)
  REFERENCES games (id)
    ON DELETE CASCADE
);

CREATE TABLE pieces (
  id             INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  type           VARCHAR(50),
  placement      INT          DEFAULT NULL,
  orientation    VARCHAR(50)  DEFAULT 'NONE',
  taken          BOOL         DEFAULT FALSE,
  piece_board_id INT NOT NULL,
  CONSTRAINT piece_boards_id_fk
  FOREIGN KEY (piece_board_id)
  REFERENCES boards (id)
    ON DELETE CASCADE
);

CREATE TABLE moves (
  id            INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  point         INT NOT NULL,
  status        VARCHAR(4)   DEFAULT 'MISS',
  move_board_id INT NOT NULL,
  CONSTRAINT move_boards_id_fk
  FOREIGN KEY (move_board_id)
  REFERENCES boards (id)
    ON DELETE CASCADE
)