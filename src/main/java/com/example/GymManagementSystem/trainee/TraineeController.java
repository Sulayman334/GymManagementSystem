package com.example.GymManagementSystem.trainee;

import com.example.GymManagementSystem.exceptions.NullDetails;
import com.example.GymManagementSystem.exceptions.ObjectAlreadyExist;

import com.example.GymManagementSystem.exceptions.TraineeNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trainee")
public class TraineeController {
    private final TraineeService traineeService;

    public TraineeController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addTrainee(@RequestBody Trainee trainee) {
        try {
            return ResponseEntity.ok(traineeService.addTrainee(trainee));
        }catch (NullDetails e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (ObjectAlreadyExist e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.internalServerError().body("An error occurred while adding trainee.");
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTrainee(@PathVariable Long id) {
        try {
            traineeService.deleteTrainee(id);
            return ResponseEntity.ok("Trainee deleted successfully.");
        } catch (TraineeNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{traineeId}")
    public ResponseEntity<?> updateTrainee(@PathVariable Long traineeId, @RequestBody Trainee trainee) {
        try {
            // Update the trainee and get the updated trainee object
            Trainee updatedTrainee = traineeService.updateTrainee(traineeId, trainee );

            // Return a success response with the updated trainee
            return ResponseEntity.ok(updatedTrainee);
        } catch (TraineeNotFoundException e) {
            // Handle specific exception when the trainee ID does not exist
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Handle any unexpected errors
            return ResponseEntity.internalServerError().body("An error occurred while updating trainee.");
        }
    }
    @GetMapping("/get/{traineeId}")
    public ResponseEntity<?>getTraineeById(@PathVariable Long traineeId){
        try {
            // Get the trainee by ID and return it
            return ResponseEntity.ok(traineeService.getTraineeById(traineeId));
        } catch (TraineeNotFoundException e) {
            // Handle specific exception when the trainee ID does not exist
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Handle any unexpected errors
            return ResponseEntity.internalServerError().body("An error occurred while retrieving trainee.");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllTrainees(){
        // Get all trainees and return them
        return ResponseEntity.ok(traineeService.getAllTrainees());
    }

}

