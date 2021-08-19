package br.com.everis.api.service;

import br.com.everis.api.model.UserEntity;
import br.com.everis.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    public UserEntity create(UserEntity user) {
        if (repository.existsByEmail(user.getEmail())) {
            throw new RuntimeException(String.format("Já existe um usuario para o email %s", user.getEmail()));
        }
        return repository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
        UserEntity userEntity = Optional.ofNullable(repository.findByEmail(user)).orElseThrow(() -> new UsernameNotFoundException(user));
        userEntity.setDataHoraUltimoAcesso(LocalDateTime.now());
        return repository.save(userEntity);
    }
}

