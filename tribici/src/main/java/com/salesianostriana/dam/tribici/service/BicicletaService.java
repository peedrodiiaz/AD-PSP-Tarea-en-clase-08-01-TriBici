package com.salesianostriana.dam.tribici.service;

import ch.qos.logback.core.util.StringUtil;
import com.salesianostriana.dam.tribici.Model.Bicicleta;
import com.salesianostriana.dam.tribici.Repository.BicicletaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BicicletaService {

    private final BicicletaRepository bicicletaRepository;

    public List<Bicicleta> getAll (){
        List <Bicicleta>lista = bicicletaRepository.findAll();

        if (lista.isEmpty()){
            throw  new EntityNotFoundException("La lista esta vacía");
        }
        return lista;
    }

    public Bicicleta findById (Long id){
        return  bicicletaRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("No se ha encontrado la bicicleta con id %d".formatted(id))
        );

    }

    public Bicicleta create (Bicicleta b){
        if (!StringUtils.hasText(b.getModelo())){
            throw new IllegalArgumentException("Falta el modelo");
        }

       getAll().forEach( bicicleta -> {
           if (b.getModelo().equalsIgnoreCase(bicicleta.getModelo()))
               throw new IllegalArgumentException("La bicicleta ya esta registrada");
       });

        return   bicicletaRepository.save(b);

    }

    public Bicicleta edit ( Bicicleta b){
        if (!StringUtils.hasText(b.getModelo())){
            throw new IllegalArgumentException("Falta el modelo");
        }

        getAll().forEach( bicicleta -> {
            if (b.getModelo().equalsIgnoreCase(bicicleta.getModelo()))
                throw new IllegalArgumentException("La bicicleta ya esta registrada");
        });

        return  bicicletaRepository.findById(b.getId()).map(bicicleta -> {
            bicicleta.setEstacion(b.getEstacion());
            bicicleta.setUsos(b.getUsos());
            bicicleta.setEstado(b.getEstado());
            bicicleta.setModelo(b.getModelo());
            bicicleta.setMarca(b.getMarca());
           return bicicletaRepository.save(bicicleta);
        }).orElseThrow(
                ()->new EntityNotFoundException("No se ha encontrado la bicicleta con id %d".formatted(b.getId())));

    }

    public void delete (Long id){
        bicicletaRepository.deleteById(id);
    }



}
