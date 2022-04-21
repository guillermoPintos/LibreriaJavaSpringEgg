package com.libreriaSpring.servicios;

import com.libreriaSpring.entidades.Cliente;
import com.libreriaSpring.errores.ErrorServicio;
import com.libreriaSpring.repositorios.ClienteRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteServicio {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Cliente guardar(Long documento, String nombre, String apellido, String telefono) throws Exception {

        validar(documento, nombre, apellido, telefono);

        Cliente cliente = new Cliente();

        cliente.setDocumento(documento);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setTelefono(telefono);
        cliente.setAlta(true);
        cliente.setFechaAlta(new Date());

        return clienteRepositorio.save(cliente);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Cliente actualizar(String id,Long documento, String nombre, String apellido, String telefono) throws ErrorServicio {

        validar(documento, nombre, apellido, telefono);

        Optional<Cliente> respuesta = clienteRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Cliente cliente = respuesta.get();

            cliente.setDocumento(documento);
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setTelefono(telefono);
            cliente.setAlta(true);
            cliente.setFechaEditado(new Date());

          

            return clienteRepositorio.save(cliente);
        } else {
            throw new ErrorServicio("No se encontró el cliente solicitado");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})

    public Cliente buscarPorId(String id) throws ErrorServicio {
        Optional<Cliente> respuesta = clienteRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Cliente cliente = respuesta.get();
            return cliente;
        } else {
            throw new ErrorServicio("No existe este cliente");
        }

    }

    @Transactional(readOnly = true)
    public Cliente alta(String id) {

        Cliente entidad = clienteRepositorio.getOne(id);

        entidad.setAlta(true);
        return clienteRepositorio.save(entidad);
    }

    @Transactional(readOnly = true)
    public List<Cliente> buscarPorNombre(String nombre) {
        return clienteRepositorio.buscarClientesPorNombre(nombre);
    }

    @Transactional(readOnly = true)
    public List<Cliente> buscarPorApellido(String apellido) {
        return clienteRepositorio.buscarClientesPorApellido(apellido);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Cliente baja(String id) {

        Cliente entidad = clienteRepositorio.getOne(id);

        entidad.setAlta(false);
        return clienteRepositorio.save(entidad);
    }

    @Transactional(readOnly = true)
    public List<Cliente> listarActivos() {
        return clienteRepositorio.buscarActivos();
    }

    @Transactional(readOnly = true)
    public List<Cliente> listarTodos() {
        return clienteRepositorio.findAll();
    }

    public void validar(Long documento, String nombre, String apellido, String telefono) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty() || nombre.contains("  ")) {
            throw new ErrorServicio("El nombre no puede ser nulo o estar vacio.");
        }

        if (documento <= 0) {
            throw new ErrorServicio("El valor del año no puede ser menos a 0.");
        }

        if (apellido == null || apellido.isEmpty() || apellido.contains("  ")) {
            throw new ErrorServicio("El apellido no puede ser nulo o esta vacio.");
        }

        if (telefono == null || telefono.isEmpty() || telefono.contains("  ")) {
            throw new ErrorServicio("El telefono no puede ser nulo o esta vacio.");
        }

    }
}
