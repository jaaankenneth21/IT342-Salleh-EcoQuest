package edu.cit.salleh.ecoquest.repository;

import edu.cit.salleh.ecoquest.entity.QuestCompletion;
import edu.cit.salleh.ecoquest.entity.User;
import edu.cit.salleh.ecoquest.entity.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestCompletionRepository
        extends JpaRepository<QuestCompletion, Long> {

    boolean existsByUserAndQuest(User user, Quest quest);
    List<QuestCompletion> findByUser(User user);
}