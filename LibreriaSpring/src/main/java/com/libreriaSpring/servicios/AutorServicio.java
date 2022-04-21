package com.libreriaSpring.servicios;

import com.libreriaSpring.entidades.Autor;
import com.libreriaSpring.errores.ErrorServicio;
import com.libreriaSpring.repositorios.AutorRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Autor guardar(String nombre, Boolean alta) throws ErrorServicio {

        validar(nombre);

        Autor autor = new Autor();

        autor.setNombre(nombre);
        autor.setAlta(true);
        autor.setFechaAlta(new Date());

        return autorRepositorio.save(autor);
    }

    @Transactional(readOnly = true)

    public Autor buscarPorId(String id) throws ErrorServicio {
        Optional<Autor> respuesta = autorRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            return autor;
        } else {
            throw new ErrorServicio("No existe este autor");
        }

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Autor actualizar(String id, String nombre, Boolean alta) throws ErrorServicio {

        validar(nombre);

        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();

            autor.setNombre(nombre);
            autor.setAlta(alta);
            autor.setFechaEditado(new Date());
            return autorRepositorio.save(autor);
        } else {
            throw new ErrorServicio("No se encontró el autor solicitado");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Autor alta(String id) {

        Autor entidad = autorRepositorio.getOne(id);

        entidad.setAlta(true);
        return autorRepositorio.save(entidad);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Autor baja(String id) {

        Autor entidad = autorRepositorio.getOne(id);

        entidad.setAlta(false);
        return autorRepositorio.save(entidad);
    }

    @Transactional(readOnly = true)
    public List<Autor> listarActivos() {
        return autorRepositorio.buscarActivos();
    }

    @Transactional(readOnly = true)
    public List<Autor> listarTodos() {
        return autorRepositorio.findAll();
    }

    public void validar(String nombre) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty() || nombre.contains("  ")) {
            throw new ErrorServicio("El nombre del autor no puede ser nulo o estar vacio.");
        }

    }
}
