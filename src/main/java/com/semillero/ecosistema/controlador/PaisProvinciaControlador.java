package com.semillero.ecosistema.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.semillero.ecosistema.dto.PaisDto;
import com.semillero.ecosistema.entidad.Provincia;
import com.semillero.ecosistema.servicio.PaisProvinciaServiceImpl;

@RestController
@RequestMapping("/ubicacion")
@Validated
public class PaisProvinciaControlador {

	@Autowired
	private PaisProvinciaServiceImpl paisProvinciaServiceImpl;
	
	@GetMapping("/paises")
    public List<PaisDto> getAllPaises() {
        return paisProvinciaServiceImpl.mostrarTodo();
    }
	
	@GetMapping("/paises/{paisId}/provincias")
	public ResponseEntity<?>mostrarProvinciasPorId(@PathVariable Long paisId){
		try{
			List<Provincia>provincias=paisProvinciaServiceImpl.mostrarProvinciasPorPaisId(paisId);
			if(provincias.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El pais no tiene provincias");
			}
			return ResponseEntity.ok(provincias);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
}
