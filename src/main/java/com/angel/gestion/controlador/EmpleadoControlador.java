package com.angel.gestion.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.angel.gestion.excepciones.ResourceNoteFoundException;
import com.angel.gestion.modelo.Empleado;
import com.angel.gestion.repositorio.EmpleadoRepositorio;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "http://localhost:4200/")
public class EmpleadoControlador {
	
	@Autowired
	private EmpleadoRepositorio repositorio;
	
	@GetMapping("/empleados")
	public List<Empleado> listarEmpleados() {
		
		return repositorio.findAll();
		
	}
	
	@PostMapping("/empleados")
	public Empleado registrarEmpleado(@RequestBody Empleado empleado) {
		
		return repositorio.save(empleado);
		
	}
	
	// Metodo para buscar usuario por id
	@GetMapping("/empleados/{id}")
	public ResponseEntity<Empleado> obtenerPorId(@PathVariable Long id) {
		
		// Creamos un objeto del tipo empleado el cuál guardará el usuario encontrado y si no lo encentra lanzara una excepcion con un mensaje
		Empleado empleado = repositorio.findById(id).orElseThrow(() -> new ResourceNoteFoundException("Empleado inexistente"));
	
		return ResponseEntity.ok(empleado);
		
	}
	
	@PutMapping("/empleados/{id}")
	public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable Long id ,@RequestBody Empleado detallesEmpleado) {
		
		Empleado empleado = repositorio.findById(id).orElseThrow(() -> new ResourceNoteFoundException("Empleado inexistente"));
		
		empleado.setNombre(detallesEmpleado.getNombre());
		empleado.setApellido(detallesEmpleado.getApellido());
		empleado.setEmail(detallesEmpleado.getEmail());
		
		Empleado empleadoRepo = repositorio.save(empleado);
		
		return ResponseEntity.ok(empleadoRepo);
		
	}
	
	@DeleteMapping("/empleados/{id}")
	public void delete(@PathVariable("id") Long id) {
		
		repositorio.deleteById(id);
		
	}
	
	
	
	
	

}
