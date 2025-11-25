package com.example.tabelogpage.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tabelogpage.entity.PasswordResetToken;
import com.example.tabelogpage.entity.User;
import com.example.tabelogpage.form.PasswordEditForm;
import com.example.tabelogpage.repository.PasswordResetTokenRepository;
import com.example.tabelogpage.repository.UserRepository;

@Service
public class PasswordResetService {
	private final UserRepository userRepository;
	private final PasswordResetTokenRepository passwordResetTokenRepository;
	
	public PasswordResetService(UserRepository userRepository, PasswordResetTokenRepository passwordResetTokenRepository) {
		this.userRepository = userRepository;
		this.passwordResetTokenRepository = passwordResetTokenRepository;
	}
	
        public User getUserByPasswordResetToken(String token) {
                PasswordResetToken passToken = passwordResetTokenRepository.findByToken(token);
                if (passToken == null) {
                        return null;
                }
                return passToken.getUser();
        }

        @Transactional
        public void updatePassword(PasswordEditForm passwordEditForm, PasswordEncoder passwordEncoder,  User user) {
                String newPassword = passwordEditForm.getPassword();
                String encodedPassword = passwordEncoder.encode(newPassword);
                user.setPassword(encodedPassword);

                userRepository.save(user);
                passwordResetTokenRepository.deleteByUser(user);
        }

}
