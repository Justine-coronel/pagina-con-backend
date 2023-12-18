package com.example.reportes.Controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.reportes.Models.Estudiantes;
import org.springframework.http.ResponseEntity;
import com.example.reportes.Services.EstudiantesDb;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class EstudiantesController {

    @GetMapping("/estudiantes")
    public List<Estudiantes> ObtenerEstudiantes() {

        return new EstudiantesDb().TodoLosEstudiantes();
    }

    @PostMapping("/Busqueda")
    public ResponseEntity<List<Estudiantes>> Busqueda(@RequestBody Estudiantes es) {
        System.out.println(es.getAño() + "" + es.getCarrera() + "" + es.getEtapa());
        return ResponseEntity.ok().body(new EstudiantesDb().ObtenerEstudiantes(es));
    }
}
