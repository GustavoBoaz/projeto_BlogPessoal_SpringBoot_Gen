package com.security.App.Security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.App.Models.Usuario;
import com.security.App.Repositories.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private @Autowired UsuarioRepository repository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		Optional<Usuario> usuario = repository.findByUsuario(userName);
		
		if (usuario.isPresent()) {
			return new UserDetailsImpl(usuario.get());
		} else {
			throw new UsernameNotFoundException(userName + " not found.");
		}
	}
}
