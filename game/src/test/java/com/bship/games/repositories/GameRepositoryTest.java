package com.bship.games.repositories;

import com.bship.DBHelper;
import com.bship.games.domains.Board;
import com.bship.games.domains.Game;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GameRepositoryTest {

    private GameRepository repository;
    private BoardRepository boardRepository;

    @Before
    public void setup() {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(DBHelper.reset());
        boardRepository = mock(BoardRepository.class);
        repository = new GameRepository(template, boardRepository);
    }

    @Test
    public void create_shouldCreateAGame() {
        Game game = repository.create();

        assertThat(game, is(instanceOf(Game.class)));
    }

    @Test
    public void create_shouldHaveTwoBoards() {
        Game game = repository.create();
        when(boardRepository.create(anyLong())).thenReturn(Board.builder().build());
        assertThat(game.getBoards().size(), is(2));
    }

    @Test
    public void create_shouldPersistGames() {
        when(boardRepository.getAll(anyLong())).thenReturn(asList(null, null));
        Game game1 = repository.create();
        Game game2 = repository.create();

        List<Game> games = repository.getAll();

        assertThat(games, is(not(empty())));
        assertThat(games.size(), is(2));
        assertThat(game1.getId(), is(equalTo(1L)));
        assertThat(game2.getId(), is(equalTo(2L)));
    }

    @Test
    public void getAll_shouldGetAllTheGames() {
        when(boardRepository.getAll(anyLong())).thenReturn(asList(null, null));
        Game game1 = repository.create();
        Game game2 = repository.create();

        List<Game> games = repository.getAll();

        assertThat(games, containsInAnyOrder(game1, game2));
    }

    @Test
    public void getAll_shouldBeEmptyIfNoGames() {
        List<Game> games = repository.getAll();

        assertThat(games, is(empty()));
    }

    @Test
    public void get_shouldRetrieveABoard() {
        when(boardRepository.getAll(anyLong())).thenReturn(asList(null, null));
        Game createdGame = repository.create();
        Optional<Game> game = repository.get(createdGame.getId());

        assertThat(createdGame, is(equalTo(game.get())));
    }

    @Test
    public void get_shouldOnlyRetrieveTheBoardForTheGivenId() {
        when(boardRepository.getAll(anyLong())).thenReturn(asList(null, null));
        repository.create();
        Game createdGame = repository.create();
        repository.create();
        Optional<Game> game = repository.get(createdGame.getId());

        assertThat(createdGame, is(equalTo(game.get())));
    }

    @Test
    public void get_shouldReturnEmptyIfTheBoardDoesNotExit() {
        when(boardRepository.getAll(anyLong())).thenReturn(asList(null, null));
        repository.create();
        repository.create();
        repository.create();
        Optional<Game> game = repository.get(100L);

        assertThat(game, is(Optional.empty()));
    }

    @Test
    public void save_shouldSaveTheGame() {
        when(boardRepository.getAll(anyLong())).thenReturn(asList(null, null));
        Game game = repository.create().copy().withTurn(2L).build();

        repository.save(game);
        Optional<Game> savedGame = repository.get(game.getId());

        assertThat(savedGame.get(), is(equalTo(game)));
    }

    @Test
    public void save_shouldReturnTheSavedGame() {
        when(boardRepository.getAll(anyLong())).thenReturn(asList(null, null));
        Game game = repository.create().copy().withTurn(2L).build();

        Optional<Game> savedGame = repository.save(game);

        assertThat(savedGame.get(), is(equalTo(game)));
    }

    @Test
    public void save_shouldSaveTheBoards() {
        when(boardRepository.getAll(anyLong())).thenReturn(asList(null, null));
        Game game = repository.create();

        repository.save(game);

        verify(boardRepository, times(2)).save(any(Board.class));
    }

    @Test
    public void delete_shouldDeleteTheGameFromTheRepository() {
        Game game = repository.create();
        Optional<Game> gotGame = repository.get(game.getId());
        assertThat(gotGame.isPresent(), is(true));
        repository.delete(game);
        Optional<Game> gotDeletedGame = repository.get(game.getId());
        assertThat(gotDeletedGame.isPresent(), is(false));
    }
    @Test
    public void delete_shouldReturnTheDeletedGame() {
        Game game = repository.create();

        Optional<Game> gotDeletedGame =  repository.delete(game);

        assertThat(gotDeletedGame.isPresent(), is(true));
    }
}