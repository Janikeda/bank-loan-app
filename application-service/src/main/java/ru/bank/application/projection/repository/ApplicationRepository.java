package ru.bank.application.projection.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.bank.application.projection.entity.ApplicationEntity;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, String> {

    List<ApplicationEntity> findAllByLastName(String lastName);

    @Modifying
    @Query("DELETE FROM ApplicationEntity app WHERE app.createdDate < :date")
    int removeOldRecords(@Param("date") LocalDateTime date);
}
