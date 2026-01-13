package com.salesianostriana.dam.tribici.service;

import com.salesianostriana.dam.tribici.Model.Usuario;
import com.salesianostriana.dam.tribici.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;


    public Optional<Usuario> autenticacion(String numTarjeta, String pin) {
        return usuarioRepository.findByNumTarjetaAndPin(numTarjeta, pin);
    }

    public void actualizarSaldo(Usuario usuario, double cantidadDecrementar) {
        usuario.setSaldo(usuario.getSaldo() - cantidadDecrementar);
        usuarioRepository.save(usuario);
    }


}
