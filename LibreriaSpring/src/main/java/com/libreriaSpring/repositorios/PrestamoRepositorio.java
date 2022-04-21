
package com.libreriaSpring.repositorios;

import com.libreriaSpring.entidades.Prestamo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamoRepositorio extends JpaRepository<Prestamo, String> {
    
    @Query("SELECT p FROM  Prestamo p WHERE p.id LIKE :id ")
    public Prestamo buscarPoID(@Param("id")String id);

    @Query("SELECT p from Libro p WHERE p.alta = true ")
	public List<Prestamo> buscarActivos();
        
    @Query("SELECT p FROM Prestamo p WHERE p.cliente.nombre LIKE :nombre")
    public List<Prestamo> buscarPrestamoPorNombre(@Param("nombre")String nombre);
    
     @Query("SELECT p FROM Prestamo p WHERE p.cliente.apellido LIKE :apellido")
    public List<Prestamo> buscarPrestamoPorApellido(@Param("apellido")String apellido);
}
