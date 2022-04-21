package com.libreriaSpring.servicios;

import com.libreriaSpring.entidades.Editorial;
import com.libreriaSpring.errores.ErrorServicio;
import com.libreriaSpring.repositorios.EditorialRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Editorial guardar(String nombre, Boolean alta) throws ErrorServicio {

        validar(nombre);
        Editorial editorial = new Editorial();

        editorial.setNombre(nombre);
        editorial.setAlta(true);
        editorial.setFechaAlta(new Date());

        return editorialRepositorio.save(editorial);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Editorial alta(String id) {

        Editorial entidad = editorialRepositorio.getOne(id);

        entidad.setAlta(true);
        return editorialRepositorio.save(entidad);
    }

    @Transactional(readOnly = true)

    public Editorial buscarPorId(String id) throws ErrorServicio {
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            return editorial;
        } else {
            throw new ErrorServicio("No existe este libro");
        }

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Editorial actualizar(String id, String nombre, Boolean alta) throws ErrorServicio {

        validar(nombre);

        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();

            editorial.setNombre(nombre);
            editorial.setAlta(true);
            editorial.setFechaEditado(new Date());

            return editorialRepositorio.save(editorial);
        } else {
            throw new ErrorServicio("No se encontró la editorial solicitada");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Editorial baja(String id) {

        Editorial entidad = editorialRepositorio.getOne(id);

        entidad.setAlta(false);
        return editorialRepositorio.save(entidad);
    }

    @Transactional(readOnly = true)
    public List<Editorial> listarActivos() {
        return editorialRepositorio.buscarActivos();
    }

    @Transactional(readOnly = true)
    public List<Editorial> listarTodos() {
        return editorialRepositorio.findAll();
    }

    public void validar(String nombre) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty() || nombre.contains("  ")) {
            throw new ErrorServicio("El nombre de la editorial no puede ser nulo o estar vacio.");
        }

    }

  
}
