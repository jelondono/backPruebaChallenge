package com.saludtools.api.controller;

import com.saludtools.api.entity.Gender;
import com.saludtools.api.model.ApiResponse;
import com.saludtools.api.service.GenderService;
import com.saludtools.api.utilities.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/genders")
public class GenderController {

    @Autowired
    private GenderService genderService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllGenders() {
        List<Gender> genders = genderService.getAllGenders();
        if (genders.isEmpty()) {
            throw new ResourceNotFoundException("Géneros", "género", "No existen géneros");
        }
        return new ResponseEntity<>(new ApiResponse<>(200, "Géneros encontrados", genders), HttpStatus.OK);
    }


}