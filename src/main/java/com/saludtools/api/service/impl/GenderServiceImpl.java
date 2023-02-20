package com.saludtools.api.service.impl;

import com.saludtools.api.entity.Gender;
import com.saludtools.api.repository.GenderRepository;
import com.saludtools.api.service.GenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenderServiceImpl implements GenderService {

    @Autowired
    private GenderRepository genderRepository;

    @Override
    public List<Gender> getAllGenders() {
        return genderRepository.findAll();
    }
}
