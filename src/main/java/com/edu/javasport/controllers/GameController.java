package com.edu.javasport.controllers;

import com.edu.javasport.bll.constants.GameConstants;
import com.edu.javasport.bll.errors.GameErrors;
import com.edu.javasport.bll.errors.GeneralErrors;
import com.edu.javasport.bll.service.GameService;
import com.edu.javasport.dal.entity.Game;
import com.edu.javasport.dto.game.CreateGameDto;
import com.edu.javasport.dto.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class GameController {

    @Autowired
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }


    @PostMapping("/game/create")
    public ResponseEntity<?> createGame (@RequestBody CreateGameDto createGameDto) {

        final Response response = new Response();
        String result = gameService.createGame(createGameDto);

        if (result == GameConstants.GAME_CREATED) {
            response.message = result;
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            response.error = GeneralErrors.UNKNOWN_ERROR;
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/game/getall")
    public ResponseEntity<List<Game>> getAll () {

        final List<Game> countries = gameService.getAll();

        return countries != null && !countries.isEmpty()
                ? new ResponseEntity<>(countries, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/game/get/{id}")
    public ResponseEntity<Response> getOneById (@PathVariable Long id) {

        final Optional<Game> game = gameService.getOneById(id);
        final Response response = new Response();
        response.message = game;

        if (game != null && !game.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.error = GameErrors.GAME_DOES_NOT_EXIST;
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/game/delete/{id}")
    public ResponseEntity<?> deleteOneById (@PathVariable Long id) {
        final Response response = new Response();
        response.message = gameService.deleteById(id);

        if (response.message == GameConstants.GAME_DELETED) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.error = GameErrors.GAME_DOES_NOT_EXIST;
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}

