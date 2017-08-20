package com.bship.games.endpoints.cabinet.repositories;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public interface SQL {
    String INSERT_INTO = "INSERT INTO";
    String SELECT_ALL = "SELECT *";
    String DELETE = "DELETE";
    String WHERE = "WHERE";
    String SET = "SET";

    String ID = "id = :id";
    String WINNER = "winner = :winner";
    String TURN = "turn = :turn";
    String TAKEN = "taken = :taken";
    String PLACEMENT = "placement = :placement";
    String OVER = "over = :over";
    String ORIENTATION = "orientation = :orientation";
    String GAME_ID = "game_id = :game_id";
    String FROM_GAMES = "FROM games";
    String FROM_BOARDS = "FROM boards";

    String UPDATE_GAMES = "UPDATE games";
    String UPDATE_PIECES = "UPDATE pieces";
    String UPDATE_BOARDS = "UPDATE boards";

    String SELECT_OPPONENTS_TAKEN_PIECES = "SELECT p.* FROM pieces p JOIN boards b ON p.piece_board_id = b.id WHERE b.game_id = :game_id AND p.piece_board_id <> :piece_board_id AND p.taken IS TRUE;";
    String SELECT_All_OPPONENTS_MOVES = "SELECT m.* FROM moves m JOIN boards b ON m.move_board_id = b.id WHERE b.game_id = :game_id AND m.move_board_id <> :board_id;";

    String PIECES = "pieces(id, type, piece_board_id) VALUES (default, :type, :board_id)";
    String GAMES = "games(id, name) VALUES (default, :name)";
    String MOVES = "moves(id, move_board_id, point, status) VALUES (default, :board_id, :point, :status)";

    String BOARDS_FOR_GAME = "boards(id, game_id) VALUES (default, :game_id)";
    String MOVES_FOR_BOARD = "FROM moves WHERE move_board_id = :board_id";
    String PIECES_FOR_BOARD = "FROM pieces WHERE piece_board_id = :piece_board_id";

    String SEP = " ";
    String COMMA = ", ";

    default String join(String separator, String... sqls) {
        return Stream.of(sqls).collect(Collectors.joining(separator));
    }

    default <T> Function<Stream<T>, SqlParameterSource[]> createBatch(Function<T, HashMap<String, Object>> mapper) {
        return list -> list
                .map(mapper)
                .map(MapSqlParameterSource::new)
                .collect(toList())
                .toArray(new MapSqlParameterSource[0]);
    }
}
