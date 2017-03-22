CREATE TABLE games(
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  turn INT,
  over BOOLEAN DEFAULT FALSE
);

CREATE TABLE boards(
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  game_id INT NOT NULL,
  winner BOOLEAN DEFAULT FALSE,
  CONSTRAINT games_id_fk
  FOREIGN KEY (game_id)
  REFERENCES games(id)
);

CREATE TABLE ships(
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  type VARCHAR(50),
  placement INT DEFAULT NULL,
  orientation VARCHAR(50) DEFAULT 'NONE',
  sunk BOOL DEFAULT FALSE,
  size INT,
  ship_board_id INT NOT NULL,
  CONSTRAINT ship_boards_id_fk
  FOREIGN KEY (ship_board_id)
  REFERENCES boards(id)
);

CREATE TABLE moves(
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  point INT NOT NULL,
  status VARCHAR(4) DEFAULT 'MISS',
  move_board_id INT NOT NULL,
  CONSTRAINT move_boards_id_fk
  FOREIGN KEY (move_board_id)
  REFERENCES boards(id)
)