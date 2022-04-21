package com.libreriaSpring.servicios;

import com.libreriaSpring.entidades.Autor;
import com.libreriaSpring.entidades.Editorial;
import com.libreriaSpring.entidades.Libro;
import com.libreriaSpring.errores.ErrorServicio;
import com.libreriaSpring.repositorios.LibroRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private EditorialServicio editorialServicio;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Libro guardar(String titulo, Boolean alta, Integer anio, Integer ejemplares, Integer ejemplaresPrestados,
            Integer ejemplaresRestantes, String idAutor, String idEditorial) throws ErrorServicio {

        validar(titulo, alta, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes);

        Libro libro = new Libro();

        libro.setTitulo(titulo);
        libro.setAlta(alta);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setEjemplaresRestantes(ejemplaresRestantes);
        libro.setFechaAlta(new Date());

        Autor autor = autorServicio.buscarPorId(idAutor);
        libro.setAutor(autor);

        Editorial editorial = editorialServicio.buscarPorId(idEditorial);
        libro.setEditoriales(editorial);

        return libroRepositorio.save(libro);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Libro actualizar(String id, String titulo, Boolean alta, Integer anio, Integer ejemplares, Integer ejemplaresPrestados,
            Integer ejemplaresRestantes, String idAutor, String idEditorial) throws ErrorServicio {

        validar(titulo, alta, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes);

        Optional<Libro> respuesta = libroRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();

            libro.setTitulo(titulo);
            libro.setAlta(true);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            libro.setEjemplaresPrestados(ejemplaresPrestados);
            libro.setEjemplaresRestantes(ejemplaresRestantes);
            libro.setFechaEditado(new Date());

            Autor autor = autorServicio.buscarPorId(idAutor);
            libro.setAutor(autor);

            Editorial editorial = editorialServicio.buscarPorId(idEditorial);
            libro.setEditoriales(editorial);

            return libroRepositorio.save(libro);
        } else {
            throw new ErrorServicio("No se encontró el libro solicitado");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})

    public Libro buscarPorId(String id) throws ErrorServicio {
        Optional<Libro> respuesta = libroRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            return libro;
        } else {
            throw new ErrorServicio("No existe este libro");
        }

    }

    @Transactional(readOnly = true)
    public Libro alta(String id) {

        Libro entidad = libroRepositorio.getOne(id);

        entidad.setAlta(true);
        return libroRepositorio.save(entidad);
    }

    @Transactional(readOnly = true)
    public List<Libro> buscarPorAutor(String nombre) {
        return libroRepositorio.buscarLibrosPorNombreAutor(nombre);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Libro baja(String id) {

        Libro entidad = libroRepositorio.getOne(id);

        entidad.setAlta(false);
        return libroRepositorio.save(entidad);
    }

    @Transactional(readOnly = true)
    public List<Libro> listarActivos() {
        return libroRepositorio.buscarActivos();
    }

    @Transactional(readOnly = true)
    public List<Libro> listarTodos() {
        return libroRepositorio.findAll();
    }

    public void validar(String titulo, Boolean alta, Integer anio, Integer ejemplares, Integer ejemplaresPrestados,
            Integer ejemplaresRestantes) throws ErrorServicio {

        if (titulo == null || titulo.isEmpty() || titulo.contains("  ")) {
            throw new ErrorServicio("El titulo no puede ser nulo o esta vacio.");
        }

        if (anio <= 0) {
            throw new ErrorServicio("El valor del año no puede ser menos a 0.");
        }

        if (ejemplares < 0) {
            throw new ErrorServicio("La cantidad de ejemplares no puede ser menor a 0.");
        }

        if (ejemplaresPrestados <= 0) {
            throw new ErrorServicio("LA cantidad de los ejemplares prestador no puede ser menor o igual a 0.");
        }

        if (ejemplaresRestantes <= 0) {
            throw new ErrorServicio("La cantidad de ejemplares restantes no puede ser menor o igual a 0.");
        }
    }

}
