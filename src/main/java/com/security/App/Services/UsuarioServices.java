package com.security.App.Services;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.App.Models.Usuario;
import com.security.App.Models.UsuarioLogin;
import com.security.App.Repositories.UsuarioRepository;

@Service
public class UsuarioServices {

	private @Autowired UsuarioRepository repository;
	
	public Optional<Usuario> cadastrarUsuario(Usuario usuario) {
		
		Optional<Usuario> usuarioExistente = repository.findByUsuario(usuario.getUsuario());
		if (usuarioExistente.isPresent()) {
			return Optional.empty();
		} else {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String senhaCriptografada = encoder.encode(usuario.getSenha());
			usuario.setSenha(senhaCriptografada);
			return Optional.ofNullable(repository.save(usuario));
		}
	}
	
	public Optional<UsuarioLogin> logarUsuario(Optional<UsuarioLogin> usuarioLogin){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> usuarioPresente = repository.findByUsuario(usuarioLogin.get().getUsuario());

		if(usuarioPresente.isPresent()) {
			if(encoder.matches(usuarioLogin.get().getSenha(), usuarioPresente.get().getSenha())) {
				String auth = usuarioLogin.get().getUsuario() + ":" + usuarioLogin.get().getSenha();
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);
				
				usuarioLogin.get().setToken(authHeader);				
				usuarioLogin.get().setNome(usuarioPresente.get().getNome());
				usuarioLogin.get().setSenha(usuarioPresente.get().getSenha());

				return usuarioLogin;
			}
		}
		return null;
	}
}
