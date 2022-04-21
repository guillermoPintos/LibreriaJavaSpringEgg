package com.libreriaSpring.servicios;

import com.libreriaSpring.entidades.Cliente;
import com.libreriaSpring.entidades.Libro;
import com.libreriaSpring.entidades.Prestamo;
import com.libreriaSpring.errores.ErrorServicio;
import com.libreriaSpring.repositorios.PrestamoRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrestamoServicio {

    @Autowired
    private PrestamoRepositorio prestamoRepositorio;

    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private LibroServicio libroServicio;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Prestamo guardar( String idLibro, String idCliente) throws ErrorServicio {

        Prestamo prestamo = new Prestamo();

        prestamo.setFechaPrestamo(new Date());
        prestamo.setAlta(true);

        Libro libro = libroServicio.buscarPorId(idLibro);
        prestamo.setLibro(libro);

        Cliente cliente = clienteServicio.buscarPorId(idCliente);
        prestamo.setCliente(cliente);

        return prestamoRepositorio.save(prestamo);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Prestamo actualizar(String id, Boolean alta, String idLibro, String idCliente) throws ErrorServicio {

        Optional<Prestamo> respuesta = prestamoRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Prestamo prestamo = respuesta.get();

            prestamo.setFechaPrestamo(new Date());
            prestamo.setAlta(alta);

            Libro libro = libroServicio.buscarPorId(idLibro);
            prestamo.setLibro(libro);

            Cliente cliente = clienteServicio.buscarPorId(idCliente);
            prestamo.setCliente(cliente);

            return prestamoRepositorio.save(prestamo);
        } else {
            throw new ErrorServicio("No se encontró el prestamo solicitado");
        }
    }

    @Transactional(readOnly = true)
    public Prestamo alta(String id) {

        Prestamo entidad = prestamoRepositorio.getOne(id);

        entidad.setAlta(true);
        return prestamoRepositorio.save(entidad);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Prestamo buscarPorId(String id) throws ErrorServicio {
        Optional<Prestamo> respuesta = prestamoRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Prestamo prestamo = respuesta.get();
            return prestamo;
        } else {
            throw new ErrorServicio("No existe este Prestamo");
        }

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Prestamo baja(String id) {

        Prestamo entidad = prestamoRepositorio.getOne(id);

        entidad.setAlta(false);
        entidad.setFechaDevolucion(new Date());
        return prestamoRepositorio.save(entidad);
    }

    @Transactional(readOnly = true)
    public List<Prestamo> listarActivos() {
        return prestamoRepositorio.buscarActivos();
    }

    @Transactional(readOnly = true)
    public List<Prestamo> listarTodos() {
        return prestamoRepositorio.findAll();
    }

}
