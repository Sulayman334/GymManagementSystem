package com.example.GymManagementSystem.trainee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee,Long> {

    boolean existsByEmail(String email);

    void deleteByEmail(String email);
}
