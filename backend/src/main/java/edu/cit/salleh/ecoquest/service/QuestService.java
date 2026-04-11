package edu.cit.salleh.ecoquest.service;

import edu.cit.salleh.ecoquest.dto.CompleteQuestResponse;
import edu.cit.salleh.ecoquest.dto.QuestResponse;
import edu.cit.salleh.ecoquest.entity.Quest;
import edu.cit.salleh.ecoquest.entity.QuestCompletion;
import edu.cit.salleh.ecoquest.entity.User;
import edu.cit.salleh.ecoquest.repository.QuestCompletionRepository;
import edu.cit.salleh.ecoquest.repository.QuestRepository;
import edu.cit.salleh.ecoquest.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestService {

    private final QuestRepository questRepository;
    private final QuestCompletionRepository questCompletionRepository;
    private final UserRepository userRepository;

    public QuestService(QuestRepository questRepository,
                        QuestCompletionRepository questCompletionRepository,
                        UserRepository userRepository) {
        this.questRepository = questRepository;
        this.questCompletionRepository = questCompletionRepository;
        this.userRepository = userRepository;
    }

    public List<QuestResponse> getAllQuests(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Quest> quests = questRepository.findAll();
        return quests.stream().map(quest -> {
            boolean completed = questCompletionRepository
                    .existsByUserAndQuest(user, quest);
            return new QuestResponse(
                    quest.getId(),
                    quest.getTitle(),
                    quest.getDescription(),
                    quest.getCategory(),
                    quest.getDifficulty(),
                    quest.getPoints(),
                    quest.getIcon(),
                    completed);
        }).collect(Collectors.toList());
    }

    public CompleteQuestResponse completeQuest(String email, Long questId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Quest quest = questRepository.findById(questId)
                .orElseThrow(() -> new RuntimeException("Quest not found"));

        if (questCompletionRepository.existsByUserAndQuest(user, quest)) {
            throw new RuntimeException("Quest already completed");
        }

        QuestCompletion completion = new QuestCompletion();
        completion.setUser(user);
        completion.setQuest(quest);
        questCompletionRepository.save(completion);

        user.setPoints(user.getPoints() + quest.getPoints());
        userRepository.save(user);

        return new CompleteQuestResponse(
                quest.getId(),
                quest.getTitle(),
                quest.getPoints(),
                user.getPoints(),
                completion.getCompletedAt().toString());
    }
}