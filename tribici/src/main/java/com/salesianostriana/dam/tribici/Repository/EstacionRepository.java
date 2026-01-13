package com.salesianostriana.dam.tribici.Repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.salesianostriana.dam.tribici.Model.Estacion;

import java.util.List;

public interface EstacionRepository extends JpaRepository <Estacion, Long> {


    @EntityGraph(attributePaths = {"bicicletas"})
    List<Estacion> findAll();

}

