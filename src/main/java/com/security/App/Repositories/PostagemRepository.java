package com.security.App.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.App.Models.Postagem;

@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long> {

}
