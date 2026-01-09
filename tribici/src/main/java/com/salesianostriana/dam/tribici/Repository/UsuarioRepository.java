package com.salesianostriana.dam.tribici.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesianostriana.dam.tribici.Model.Usuario;

import java.util.Optional;

public interface UsuarioRepository  extends JpaRepository <Usuario, Long>{

    public Optional<Usuario>findByNumTarjetaAndPin (String numTarjeta, String pin);
}
