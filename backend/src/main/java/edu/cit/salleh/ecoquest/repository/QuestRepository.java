package edu.cit.salleh.ecoquest.repository;

import edu.cit.salleh.ecoquest.entity.Quest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestRepository extends JpaRepository<Quest, Long> {
}