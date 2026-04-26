package com.codearena.backend.service;

import com.codearena.backend.entity.PasswordResetOtp;
import com.codearena.backend.repository.PasswordResetOtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class PasswordResetService {

    @Autowired
    private PasswordResetOtpRepository otpRepository;

    @Autowired
    private EmailService emailService;

    public String generateOtp(){
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public void sendOtp(String email){

        String otp = generateOtp();

        PasswordResetOtp resetOtp = new PasswordResetOtp(
                email,
                otp,
                LocalDateTime.now().plusMinutes(5)
        );

        otpRepository.save(resetOtp);

        emailService.sendOtp(email, otp);
    }

    public boolean verifyOtp(String email, String otp){

        Optional<PasswordResetOtp> record = otpRepository.findTopByEmailOrderByIdDesc(email);

        if(record.isEmpty())
            return false;

        PasswordResetOtp data = record.get();

        if(!data.getOtp().equals(otp))
            return false;

        if(data.getExpiryTime().isBefore(LocalDateTime.now()))
            return false;

        return true;
    }

}