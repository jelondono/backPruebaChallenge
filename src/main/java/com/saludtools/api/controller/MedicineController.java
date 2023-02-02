package com.saludtools.api.controller;

import com.saludtools.api.entity.Medicine;
import com.saludtools.api.model.ApiResponse;
import com.saludtools.api.service.MedicineService;
import com.saludtools.api.utilities.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/medicines")
public class MedicineController {
    @Autowired
    private MedicineService medicineService;

    @GetMapping("/{patientId}")
    public ResponseEntity<ApiResponse> getAllMedicines(@PathVariable Long patientId) {
        List<Medicine> medicines = medicineService.getAllMedicines(patientId);
        if (medicines.isEmpty()) {
            throw new ResourceNotFoundException("Medicinas", "Medicina", "No existen medicinas");
        }
        return new ResponseEntity<>(new ApiResponse(200, "Medicinas encontradas", medicines), HttpStatus.OK);
    }
}
