package com.frandev.dragonball_api_spring_boot.controllers;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.frandev.dragonball_api_spring_boot.models.DragonBallCharacter;
import com.frandev.dragonball_api_spring_boot.services.CharacterService;

@Controller
public class WebController {

    @Autowired
    CharacterService characterService;

    @GetMapping("/")
    public String greet(){
        return "greet";
    }


    @GetMapping("dragonball/character/random")
    public String getRandomCharacter(Model model){
        DragonBallCharacter characterDB = characterService.getRandomCharacterFromAPI();
        model.addAttribute("characterDB", characterDB);
        return "index";
    }

    @RequestMapping("dragonball/allcharacters")
    public String getAlCharactersDB(Model model){
        ArrayList<DragonBallCharacter> charactersDB = characterService.getAllDBCharacters();
        model.addAttribute("charactersDB", charactersDB);
        return "dbcharacters";
    }

    @PostMapping("/dragonball/getCharacter")
    public String getCharacter(@RequestParam Long id, Model model){
        DragonBallCharacter characterDB  = characterService.getButton(id);
        model.addAttribute("characterDB", characterDB);
        return "index";
    }

    @RequestMapping("dragonball/game")
    public String getTeamsGame(Model model){
        ArrayList<DragonBallCharacter> team1 = characterService.getTeam();
        ArrayList<DragonBallCharacter> team2 = characterService.getTeam();
        model.addAttribute("team1", team1);
        model.addAttribute("team2", team2);
        return "game";
    }


    @PostMapping("/dragonball/combat")
    public String combat(@RequestParam ArrayList<String> team1,@RequestParam ArrayList<String> team2 ,Model model){
        String [] team = characterService.letsFight(team1,team2);
        model.addAttribute("team", team);
        return "winner";
    }
}
   
