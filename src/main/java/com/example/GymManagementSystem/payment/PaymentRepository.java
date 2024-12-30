package com.example.GymManagementSystem.payment;

import com.example.GymManagementSystem.trainee.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByTrainee(Trainee trainee);
}
