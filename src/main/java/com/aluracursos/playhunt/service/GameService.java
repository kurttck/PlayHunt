package com.aluracursos.playhunt.service;

import com.aluracursos.playhunt.model.DataGame;
import com.aluracursos.playhunt.model.Game;
import com.aluracursos.playhunt.model.Plataform;
import com.aluracursos.playhunt.repository.GameRepository;
import com.aluracursos.playhunt.repository.PlatformRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlatformRepository platformRepository;

    public void saveGameWithPlatforms(Game game, List<String> platFormNames){
        for(String platFormName: platFormNames){
            Plataform platform = new Plataform();

            platformRepository.save(platform);
            game.getPlataforms().add(platform);

        }

        gameRepository.save(game);
    }




}
