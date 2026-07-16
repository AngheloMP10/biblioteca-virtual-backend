package com.biblio.virtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.biblio.virtual.model.Genero;

public interface IGeneroRepository extends JpaRepository<Genero, Long> {

}