
package com.libreriaSpring.repositorios;

import com.libreriaSpring.entidades.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

@Repository

public interface ClienteRepositorio extends JpaRepository<Cliente, String> {
   
   @Query("SELECT c FROM  Cliente c WHERE c.id LIKE :id ")
    public Cliente buscarPoID(@Param("id")String id);

    @Query("SELECT c from Cliente c WHERE c.alta = true ")
	public List<Cliente> buscarActivos();
        
    @Query("SELECT c FROM Cliente c WHERE c.nombre LIKE :nombre")
    public List<Cliente> buscarClientesPorNombre(@Param("nombre")String nombre);
    
    @Query("SELECT c FROM Cliente c WHERE c.nombre LIKE :apellido")
    public List<Cliente> buscarClientesPorApellido(@Param("apellido")String apellido);
}
