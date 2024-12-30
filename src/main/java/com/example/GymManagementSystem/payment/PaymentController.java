package com.example.GymManagementSystem.payment;

import com.example.GymManagementSystem.exceptions.NullDetails;
import com.example.GymManagementSystem.exceptions.PaymentNotFound;
import com.example.GymManagementSystem.exceptions.TraineeNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/add/{traineeId}")
    public ResponseEntity<?> createPayment(@PathVariable Long traineeId, @RequestBody Payment payment) {
        try {
            Payment savedPayment = paymentService.addPayment(traineeId, payment);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPayment);
        }catch (NullDetails e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (TraineeNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while saving the payment.");
        }
    }


    @DeleteMapping("delete/{paymentId}")
    public ResponseEntity<?> deletePayment(@PathVariable Long paymentId) {
        try {
            paymentService.deletePayment(paymentId);
            return ResponseEntity.ok("Payment deleted successfully.");
        } catch (PaymentNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while deleting the payment.");
        }
    }

    @PutMapping("update/{paymentId}")
    public ResponseEntity<?> updatePayment(@PathVariable Long paymentId, @RequestBody Payment paymentDetails) {
        try {
            Payment updatedPayment = paymentService.updatePayment(paymentId, paymentDetails);
            return ResponseEntity.ok(updatedPayment);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while updating the payment.");
        }
    }

    @GetMapping("/getPayment/{traineeId}")
    public ResponseEntity<?> getPaymentsByTrainee(@PathVariable Long traineeId) {
        try {
            List<Payment> payments = paymentService.getPaymentsByTrainee(traineeId);
            return ResponseEntity.ok(payments);
        } catch (TraineeNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while retrieving payments.");
        }
    }

    @GetMapping("/get/{paymentId}")
    public ResponseEntity<?> getPaymentsById(@PathVariable Long paymentId) {
        try {
            Payment payment = paymentService.getPaymentById(paymentId);
            return ResponseEntity.ok(payment);
        } catch (PaymentNotFound e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




}
