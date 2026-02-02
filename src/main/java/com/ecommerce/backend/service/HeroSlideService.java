package com.ecommerce.backend.service;

import com.ecommerce.backend.model.HeroSlide;
import com.ecommerce.backend.repository.HeroSlideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HeroSlideService {

    private final HeroSlideRepository repository;

    public List<HeroSlide> getSlidesPublicos() {
        return repository.findByActivoTrueOrderByOrdenAsc();
    }

    public List<HeroSlide> getAllSlides() {
        return repository.findAllByOrderByOrdenAsc();
    }

    public HeroSlide crearSlide(HeroSlide slide) {
        return repository.save(slide);
    }

    public HeroSlide actualizarSlide(Long id, HeroSlide datos) {
        HeroSlide slide = repository.findById(id).orElseThrow();
        slide.setTitulo(datos.getTitulo());
        slide.setSubtitulo(datos.getSubtitulo());
        slide.setDescripcion(datos.getDescripcion());
        slide.setImagenUrl(datos.getImagenUrl());
        slide.setBotonTexto(datos.getBotonTexto());
        slide.setBotonLink(datos.getBotonLink());
        slide.setAlineacion(datos.getAlineacion());
        slide.setOrden(datos.getOrden());
        slide.setActivo(datos.getActivo());
        return repository.save(slide);
    }

    public void eliminarSlide(Long id) {
        repository.deleteById(id);
    }
}