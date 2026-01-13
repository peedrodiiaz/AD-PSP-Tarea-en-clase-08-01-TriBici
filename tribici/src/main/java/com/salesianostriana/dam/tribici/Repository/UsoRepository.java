package com.salesianostriana.dam.tribici.Repository;

import com.salesianostriana.dam.tribici.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import com.salesianostriana.dam.tribici.Model.Uso;

import java.util.Optional;

public interface UsoRepository extends JpaRepository <Uso, Long> {
    Optional <Uso> findFirstByUsuarioOrderByFechaInicioDesc(Usuario usuario);
}
