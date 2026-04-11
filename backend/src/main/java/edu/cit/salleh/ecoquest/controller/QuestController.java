package edu.cit.salleh.ecoquest.controller;

import edu.cit.salleh.ecoquest.service.QuestService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/quests")
@CrossOrigin(origins = "http://localhost:5173")
public class QuestController {

    private final QuestService questService;

    public QuestController(QuestService questService) {
        this.questService = questService;
    }

    @GetMapping
    public ResponseEntity<?> getAllQuests(Authentication authentication) {
        try {
            return ResponseEntity.ok(
                    questService.getAllQuests(authentication.getName()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<?> completeQuest(
            @PathVariable Long id,
            Authentication authentication) {
        try {
            return ResponseEntity.ok(
                    questService.completeQuest(
                            authentication.getName(), id));
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Quest already completed")) {
                return ResponseEntity.status(409).body(e.getMessage());
            }
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}