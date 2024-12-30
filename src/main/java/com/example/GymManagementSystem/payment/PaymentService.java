package com.example.GymManagementSystem.payment;

import com.example.GymManagementSystem.exceptions.NullDetails;
import com.example.GymManagementSystem.exceptions.PaymentNotFound;
import com.example.GymManagementSystem.exceptions.TraineeNotFoundException;
import com.example.GymManagementSystem.trainee.Trainee;
import com.example.GymManagementSystem.trainee.TraineeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final TraineeRepository traineeRepository;


    public PaymentService(PaymentRepository paymentRepository, TraineeRepository traineeRepository) {
        this.paymentRepository = paymentRepository;
        this.traineeRepository = traineeRepository;
    }

    public Payment addPayment(Long traineeId, Payment payment){
        if (payment == null){
            throw new NullDetails("Payment object cannot be null");
        }

        Trainee trainee = traineeRepository.findById(traineeId)
                .orElseThrow(() -> new TraineeNotFoundException("Trainee with ID " + traineeId + " not found"));

        payment.setTrainee(trainee);

        return paymentRepository.save(payment);
    }


    // Retrieve a payment by ID
    public Payment getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFound("Payment with ID " + paymentId + " not found"));
    }

    // Retrieve all payments for a trainee
    public List<Payment> getPaymentsByTrainee(Long traineeId) {
        Trainee trainee = traineeRepository.findById(traineeId)
                .orElseThrow(() -> new TraineeNotFoundException("Trainee with ID " + traineeId + " not found"));

        return paymentRepository.findByTrainee(trainee);
    }

    // Update a payment
    public Payment updatePayment(Long paymentId, Payment paymentDetails) {
        if (paymentDetails == null) {
            throw new NullDetails("Payment details cannot be null");
        }

        Payment existingPayment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFound("Payment with ID " + paymentId + " not found"));

        // Update payment fields
        existingPayment.setAmount(paymentDetails.getAmount());
        existingPayment.setPaymentDate(paymentDetails.getPaymentDate());

        return paymentRepository.save(existingPayment);
    }

    // Delete a payment
    public void deletePayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFound("Payment with ID " + paymentId + " not found"));

        paymentRepository.delete(payment);
    }

    // TODO add the find all method
}
