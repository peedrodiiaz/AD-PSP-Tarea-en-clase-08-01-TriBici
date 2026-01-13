package com.salesianostriana.dam.tribici.service;

import com.salesianostriana.dam.tribici.Model.*;
import com.salesianostriana.dam.tribici.Repository.BicicletaRepository;
import com.salesianostriana.dam.tribici.Repository.EstacionRepository;
import com.salesianostriana.dam.tribici.Repository.UsoRepository;
import com.salesianostriana.dam.tribici.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UsoService {

    private final UsoRepository usoRepository;
    private final EstacionRepository estacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final BicicletaRepository bicicletaRepository;
    private final BicicletaService bicicletaService;
    private final UsuarioService usuarioService;

    private static final double UMBRAL_SALDO_MINIMO = 0.10;

    public Uso inicializarUso(String numTarjeta,
                              String pin,
                              Long idEstacion,
                              Long idBicicleta) throws BadRequestException {

        Usuario usuario = usuarioService.autenticacion(
                        numTarjeta, pin)
                .orElseThrow(() -> new BadRequestException("La tarjeta o el pin no son válidos"));


        if (usuario.getSaldo() < UMBRAL_SALDO_MINIMO)
            throw new BadRequestException("El usuario no tiene saldo suficiente");

        Estacion inicio = estacionRepository.findById(idEstacion)
                .orElseThrow(() -> new BadRequestException("Estación no encontrada"));

        Bicicleta bicicleta = bicicletaRepository.findById(idBicicleta)
                .orElseThrow(() -> new BadRequestException("Bicicleta no encontrada"));

        Uso uso = Uso.builder()
                // Completar con los datos necesarios
                .inicio(inicio)
                .fechaInicio(LocalDate.from(LocalDateTime.now()))
                .usuario(usuario)
                .bicicleta(bicicleta)
                .build();

        bicicleta.eliminarEstacion();
        bicicletaService.update(bicicleta);

        return usoRepository.save(uso);

    }




    public Uso finalizarUso(String numTarjeta,
                            String pin,
                            Long idBicicleta,
                            Long idEstacion) throws BadRequestException {

        Usuario usuario = usuarioService.autenticacion(
                        numTarjeta, pin)
                .orElseThrow(() -> new BadRequestException("La tarjeta o el pin no son válidos"));


        Uso uso = usoRepository.findFirstByUsuarioOrderByFechaInicioDesc(usuario)
                .orElseThrow(() -> new BadRequestException("Error: el usuario no tiene ninguna bicicleta en uso actualmente"));

        Estacion fin = estacionRepository.findById(idEstacion)
                .orElseThrow(() -> new BadRequestException("Estación no encontrada"));


        if (!Objects.equals(idBicicleta, uso.getBicicleta().getId()))
            throw new BadRequestException("La bicicleta no coincide con la que se tomó al inicio");

        LocalDateTime fechaFin = LocalDateTime.now();

        double precio = uso.calcularPrecio(fechaFin);

        if (usuario.getSaldo() < precio)
            throw new BadRequestException("El usuario no tiene saldo suficiente");

        uso.setFechaFin(LocalDate.from(fechaFin));
        uso.setFin(fin);
        uso.setCoste(precio);

        // Actualizar saldo del usuario
        usuarioService.actualizarSaldo(usuario, precio);

        // Actualizar estación de la bicicleta
        fin.addBicicleta(uso.getBicicleta());
        bicicletaService.update(uso.getBicicleta());

        return usoRepository.save(uso);
    }





    public Uso addUso(CreateUsoRequest createUsoRequest) throws BadRequestException {

        // Obtener datos necesarios;
        Estacion inicio = estacionRepository.findById(createUsoRequest.idEstacionOrigen())
                .orElseThrow(() -> new BadRequestException("Estación no encontrada"));

        Usuario usuario = usuarioRepository.findById(createUsoRequest.idUsuario())
                .orElseThrow(() -> new BadRequestException("Usuario no encontrado"));

        Bicicleta bicicleta = bicicletaRepository.findById(createUsoRequest.idBicicleta())
                .orElseThrow(() -> new BadRequestException("Bicicleta no encontrada"));

        Uso uso = Uso.builder()
                // Completar con los datos necesarios
                .inicio(inicio)
                .fechaInicio(LocalDate.from(LocalDateTime.now()))
                .usuario(usuario)
                .bicicleta(bicicleta)
                .build();


        return usoRepository.save(uso);
    }

}
