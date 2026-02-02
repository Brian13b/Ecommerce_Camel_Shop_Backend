package com.ecommerce.backend.repository;

import com.ecommerce.backend.model.HeroSlide;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HeroSlideRepository extends JpaRepository<HeroSlide, Long> {
    List<HeroSlide> findByActivoTrueOrderByOrdenAsc();
    List<HeroSlide> findAllByOrderByOrdenAsc();
}