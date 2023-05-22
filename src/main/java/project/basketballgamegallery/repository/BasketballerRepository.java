package project.basketballgamegallery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.basketballgamegallery.model.Basketballer;

import java.util.List;

public interface BasketballerRepository extends JpaRepository<Basketballer, Long> {
    List<Basketballer> findByTeamContaining(String club);
    List<Basketballer> findByLastnameContaining(String lastname);
}
