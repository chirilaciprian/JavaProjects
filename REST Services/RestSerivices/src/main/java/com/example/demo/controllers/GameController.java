package com.example.demo.controllers;

import com.example.demo.repositorys.GameRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/games")
public class GameController {
    private GameRepository gameRepository;
    public GameController(GameRepository gameRepository){
        this.gameRepository = gameRepository;
    }

    @GetMapping
    public ResponseEntity GetAllGames(){
        return ResponseEntity.ok(this.gameRepository.findAll());
    }
}
