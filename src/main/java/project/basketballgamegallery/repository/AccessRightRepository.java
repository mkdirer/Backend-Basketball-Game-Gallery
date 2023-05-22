package project.basketballgamegallery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.basketballgamegallery.model.EnumAccessRight;
import project.basketballgamegallery.model.AccessRight;

import java.util.Optional;
@Repository
public interface AccessRightRepository extends JpaRepository<AccessRight, Long> {
    Optional<AccessRight> findByName(EnumAccessRight name);
}
