package br.com.atividade.barbearia.security.service;

import br.com.atividade.barbearia.Model.User;
import br.com.atividade.barbearia.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{

        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("User not found with the email: "+email);
        }

        return user.get();
    }

}
