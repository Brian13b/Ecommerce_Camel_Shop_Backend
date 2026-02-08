package com.ecommerce.backend.controller;

import com.ecommerce.backend.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/uploads")
@CrossOrigin(origins = "*") // Permite peticiones desde Vercel
public class FileController {
    
    @Autowired
    private CloudinaryService cloudinaryService;
    
    @PostMapping
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // 1. Subimos a Cloudinary y obtenemos la URL segura (https://...)
            String fileUrl = cloudinaryService.uploadFile(file);
            
            // 2. Preparamos la respuesta para que el Frontend la entienda
            Map<String, String> response = new HashMap<>();
            
            // TRUCO: Tu frontend espera 'filename', así que le damos la URL completa ahí.
            // De esta forma, React guarda la URL de Cloudinary directamente en la base de datos.
            response.put("filename", fileUrl); 
            
            response.put("url", fileUrl);
            response.put("message", "Imagen subida exitosamente a la nube");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al subir imagen: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}