package com.bship.games.repositories;

import com.bship.games.domains.Direction;
import com.bship.games.domains.Harbor;
import com.bship.games.domains.Piece;
import com.bship.games.domains.Point;
import com.bship.games.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

import static com.bship.games.util.Util.toIndex;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

@Repository
public class PieceRepository implements SQL {

    private final NamedParameterJdbcTemplate template;

    @Autowired
    public PieceRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public List<Piece> createAll(Long boardId) {
        String NEW_PIECES = join(SEP, INSERT_INTO, PIECES);
        of(Harbor.getShips()).map(batchNewPieces(boardId))
                .map(batch -> template.batchUpdate(NEW_PIECES, batch));
        return getAll(boardId);
    }

    public void save(List<Piece> pieces) {
        String SAVE_PIECES = join(SEP, UPDATE_PIECES, SET, join(COMMA, TAKEN, PLACEMENT, ORIENTATION), WHERE, ID);
        of(pieces).map(batchUpdatePieces)
                .map(batch -> template.batchUpdate(SAVE_PIECES, batch));
    }

    public List<Piece> getAll(Long boardId) {
        return template.query(join(SEP, SELECT_ALL, PIECES_FOR_BOARD),
                new MapSqlParameterSource("piece_board_id", boardId),
                buildPiece);
    }

    public List<Piece> getAllOpponents(Long gameId, Long boardId) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("game_id", gameId);
        source.addValue("piece_board_id", boardId);
        return template.query(SELECT_OPPONENTS_TAKEN_PIECES, source, buildPiece);
    }

    public void save(Piece piece) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("id", piece.getId());
        source.addValue("placement", toIndex(piece.getPlacement()));
        source.addValue("orientation", piece.getOrientation().name());

        template.update(join(SEP, UPDATE_PIECES, SET, join(COMMA, PLACEMENT, ORIENTATION), WHERE, ID), source);
    }

    private RowMapper<Piece> buildPiece = (rs, rowNum) -> Piece.builder()
            .withId(rs.getLong("id"))
            .withType(Harbor.valueOf(rs.getString("type")))
            .withPlacement(getPoint(rs.getString("placement")))
            .withOrientation(Direction.valueOf(rs.getString("orientation")))
            .withTaken(rs.getBoolean("taken"))
            .withSize(rs.getInt("size"))
            .withBoardId(rs.getLong("piece_board_id")).build();

    private Point getPoint(String point) throws SQLException {
        return ofNullable(point)
                .map(Integer::valueOf)
                .map(Util::toPoint)
                .orElse(new Point());
    }

    private Function<List<Harbor>, SqlParameterSource[]> batchNewPieces(final Long boardId) {
        return createBatch(piece ->
                new HashMap<String, Object>() {{
                    put("type", piece.name());
                    put("size", piece.getSize());
                    put("piece_board_id", boardId);
                }});
    }

    private Function<List<Piece>, SqlParameterSource[]> batchUpdatePieces = createBatch(piece ->
            new HashMap<String, Object>() {{
                put("id", piece.getId());
                put("taken", piece.isTaken());
                put("placement", toIndex(piece.getPlacement()));
                put("orientation", piece.getOrientation().name());
            }});
}
