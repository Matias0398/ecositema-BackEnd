package com.semillero.ecosistema.servicio;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.semillero.ecosistema.entidad.Publicacion;
import com.semillero.ecosistema.repositorio.IPublicacionRepositorio;

import io.micrometer.common.util.StringUtils;

@Service
@Transactional
public class PublicacionServicioImpl {

	@Autowired
	private IPublicacionRepositorio publicacionRepositorio;
	
	public Publicacion crearPublicacion(Publicacion publicacion) {
		publicacion.setFechaDeCreacion(new Date());
		return publicacionRepositorio.save(publicacion);
	}
	
	public List<Publicacion> obtenerPublicaciones() {
		return publicacionRepositorio.findAll();
	}
	
	public List<Publicacion> obtenerPublicacionesActivas() {
		return publicacionRepositorio.findAllByDeletedFalse();
	}
	
	public Optional<Publicacion> buscarPublicacionPorId(Long id) {
		return publicacionRepositorio.findById(id);
	}
	
	public void incrementarVisualizaciones(Publicacion publicacion) {
			publicacion.setCantidadDeVisualizaciones(publicacion.getCantidadDeVisualizaciones() + 1);
			
			publicacionRepositorio.save(publicacion);
	}
	
	public boolean cambiarEstado(Long id) {
		Optional <Publicacion> opcPublicacion = buscarPublicacionPorId(id);
		
		if (opcPublicacion.isPresent()) {
			Publicacion publicacion = opcPublicacion.get();
			publicacion.setDeleted(!publicacion.isDeleted());
			publicacionRepositorio.save(publicacion);
			
			return true;
		} 
		return false;
	}
	
	public boolean editarPublicacion(Long id, Publicacion publicacion) {
		Optional <Publicacion> opcPublicacion = buscarPublicacionPorId(id);
		
		if(opcPublicacion.isPresent()) {
			Publicacion publicacionBD = opcPublicacion.get();
			
			
			String nuevasImagenes = StringUtils.isNotBlank(publicacion.getImagenes())
					? publicacion.getImagenes()
					: publicacionBD.getImagenes();
			
			int nuevaCantVisualizaciones = !Objects.isNull(publicacion.getCantidadDeVisualizaciones())
					? publicacion.getCantidadDeVisualizaciones()
					: publicacionBD.getCantidadDeVisualizaciones();
			
			publicacionBD.setTitulo(publicacion.getTitulo());
			publicacionBD.setDescripcion(publicacion.getDescripcion());
			publicacionBD.setDeleted(publicacion.isDeleted());	
			publicacionBD.setImagenes(nuevasImagenes);
			publicacionBD.setCantidadDeVisualizaciones(nuevaCantVisualizaciones);
			
			publicacionRepositorio.save(publicacionBD);
			
			return true;
		}
		return false;
	}
	
	
	public boolean borrarPublicacion(Long id) {
		Optional <Publicacion> opcPublicacion = buscarPublicacionPorId(id);
		
		if (opcPublicacion.isPresent()) {
			Publicacion borrarPublicacion = opcPublicacion.get();
			borrarPublicacion.setDeleted(true);
			publicacionRepositorio.save(borrarPublicacion);
			
			return true;
		}else {
			return false;
		}	
		
	}

	
	
	
	
}
