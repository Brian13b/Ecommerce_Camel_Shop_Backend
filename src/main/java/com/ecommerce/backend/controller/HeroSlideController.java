package com.ecommerce.backend.controller;

import com.ecommerce.backend.model.HeroSlide;
import com.ecommerce.backend.service.HeroSlideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/hero-slides")
@RequiredArgsConstructor
public class HeroSlideController {

    private final HeroSlideService service;
    
    @GetMapping("/publico")
    public ResponseEntity<List<HeroSlide>> getPublicSlides() {
        return ResponseEntity.ok(service.getSlidesPublicos());
    }

    @GetMapping("/admin")
    public ResponseEntity<List<HeroSlide>> getAllSlides() {
        return ResponseEntity.ok(service.getAllSlides());
    }

    @PostMapping("/admin")
    public ResponseEntity<HeroSlide> createSlide(@RequestBody HeroSlide slide) {
        return ResponseEntity.ok(service.crearSlide(slide));
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<HeroSlide> updateSlide(@PathVariable Long id, @RequestBody HeroSlide slide) {
        return ResponseEntity.ok(service.actualizarSlide(id, slide));
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteSlide(@PathVariable Long id) {
        service.eliminarSlide(id);
        return ResponseEntity.noContent().build();
    }
}