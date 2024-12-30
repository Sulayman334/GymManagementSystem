package com.example.GymManagementSystem.trainee;

import com.example.GymManagementSystem.exceptions.NullDetails;
import com.example.GymManagementSystem.exceptions.ObjectAlreadyExist;
import com.example.GymManagementSystem.exceptions.TraineeNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraineeService {

    private final TraineeRepository traineeRepository;

    public TraineeService(TraineeRepository traineeRepository) {
        this.traineeRepository = traineeRepository;
    }

    public Trainee addTrainee(Trainee trainee) {
        // Validate mandatory fields
        if (trainee.getFirstName() == null || trainee.getFirstName().isEmpty()) {
            throw new NullDetails("First name is required.");
        }

        if (trainee.getLastName() == null || trainee.getLastName().isEmpty()) {
            throw new NullDetails("Last name is required.");
        }

        if (trainee.getPhoneNumber() == null || trainee.getPhoneNumber().isEmpty()) {
            throw new NullDetails("Phone number is required.");
        }

        if (trainee.getEmail() == null || trainee.getEmail().isEmpty()) {
            throw new NullDetails("Email is required.");
        }

        if (trainee.getStartDate() == null) {
            throw new NullDetails("Start date is required.");
        }

        // Check for duplicate email
        if (traineeRepository.existsByEmail(trainee.getEmail())) {
            throw new ObjectAlreadyExist("A trainee with this email already exists.");
        }

        // Calculate endDate if not provided
        if (trainee.getEndDate() == null) {
            trainee.setEndDate(trainee.getStartDate().plusDays(30));
        }

        // Save and return the trainee
        return traineeRepository.save(trainee);
    }


    public void deleteTrainee(Long traineeId){
        try {
            if (!traineeRepository.existsById(traineeId)){
                throw  new TraineeNotFoundException("A trainee with "+traineeId+" does not exist");
            }
            traineeRepository.deleteById(traineeId);
        }catch (TraineeNotFoundException e) {
            throw new TraineeNotFoundException("A trainee with "+traineeId+" does not exist");
        }

    }

    public Trainee updateTrainee(Long traineeId, Trainee trainee) {
        if (trainee == null) {
            throw new NullDetails("Trainee object cannot be null");
        }

        // Fetch existing trainee or throw an exception if not found
        Trainee existingTrainee = traineeRepository.findById(traineeId)
                .orElseThrow(() -> new TraineeNotFoundException("A trainee with ID " + traineeId + " does not exist"));

        // Update the fields of the existing trainee with new details
        existingTrainee.setFirstName(trainee.getFirstName());
        existingTrainee.setEmail(trainee.getEmail());
        existingTrainee.setPhoneNumber(trainee.getPhoneNumber());
        existingTrainee.setStartDate(trainee.getStartDate());
        // Add other fields as necessary...

        // Save updated trainee
        return traineeRepository.save(existingTrainee);
    }

    public Trainee getTraineeById(Long traineeId){
        return traineeRepository.findById(traineeId)
               .orElseThrow(() -> new TraineeNotFoundException("A trainee with ID " + traineeId + " does not exist"));
    }

    public List<Trainee> getAllTrainees(){
        return traineeRepository.findAll();
    }



}
