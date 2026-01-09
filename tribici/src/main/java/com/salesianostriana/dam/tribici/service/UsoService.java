package com.salesianostriana.dam.tribici.service;

import com.salesianostriana.dam.tribici.Model.Bicicleta;
import com.salesianostriana.dam.tribici.Model.Estacion;
import com.salesianostriana.dam.tribici.Model.Uso;
import com.salesianostriana.dam.tribici.Model.Usuario;
import com.salesianostriana.dam.tribici.Repository.BicicletaRepository;
import com.salesianostriana.dam.tribici.Repository.EstacionRepository;
import com.salesianostriana.dam.tribici.Repository.UsoRepository;
import com.salesianostriana.dam.tribici.Repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsoService {

    private final UsoRepository usoRepository;
    private  final UsuarioRepository usuarioRepository;
    private  final UsuarioService usuarioService;
    private final EstacionRepository estacionRepository;
    private final BicicletaRepository bicicletaRepository;

    // Vamos a necesitar el usuario
    // Estación
    // bici

//    public Uso inicializarUso (String numTarjeta,
//                               String pin,
//                               Long idEstacion,
//                               Long idBicicleta) {
//
//        Usuario usuario = usuarioService.autenticacion(numTarjeta,pin).orElseThrow(
//                ()-> new EntityNotFoundException("No se ha encontrado el usuario")
//        );
//
//        Estacion inicio = estacionRepository.findById(idEstacion).
//                orElseThrow(()-> new BadRequestException("Estacion no encotrada"));
//
//        Bicicleta bicicleta = bicicletaRepository.findById(idBicicleta).
//                orElseThrow(()-> new BadRequestException("Bicicleta no encontrada"));
//
//
//    }
}
