package com.edu.javasport.bll.service;

import com.edu.javasport.bll.constants.GameConstants;
import com.edu.javasport.bll.errors.GameErrors;
import com.edu.javasport.dal.entity.Game;
import com.edu.javasport.dal.entity.Team;
import com.edu.javasport.dal.repository.GameRepository;
import com.edu.javasport.dto.game.CreateGameDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;


    public String createGame(CreateGameDto createGameDto) {

        Game game = new Game(createGameDto);
        gameRepository.save(game);

        return GameConstants.GAME_CREATED;
    }

    public List<Game> getAll() {
        return gameRepository.findAll();
    }

    public Optional<Game> getOneById(Long id) {
        return gameRepository.findById(id);
    }

    public String deleteById(Long id) {

        Game gameId = new Game();
        gameId.setId(id);
        Example<Game> gameNameExample = Example.of(gameId);
        Optional<Game> gameNameOptional = this.gameRepository.findOne(gameNameExample);

        if (!gameNameOptional.isEmpty()) {
            gameRepository.deleteById(id);
            return GameConstants.GAME_DELETED;
        } else {
            return GameErrors.GAME_DOES_NOT_EXIST;
        }
    }

    public ArrayList<Game> getAllByTeam(Long id) {

        Team teamId = new Team();
        teamId.setId(id);

        ArrayList<Game> response = new ArrayList<>();

        Game gameHomeTeam = new Game();
        gameHomeTeam.setHomeTeam(teamId);
        Example<Game> gameHomeTeamExample = Example.of(gameHomeTeam);
        response.addAll(gameRepository.findAll(gameHomeTeamExample));

        Game gameGuestTeam = new Game();
        gameGuestTeam.setGuestTeam(teamId);
        Example<Game> gameGuestTeamExample = Example.of(gameGuestTeam);
        response.addAll(gameRepository.findAll(gameGuestTeamExample));

        return response;
    }
}
