
package com.libreriaSpring.repositorios;

import com.libreriaSpring.entidades.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository

public interface LibroRepositorio extends JpaRepository<Libro, String>{
    
    @Query("SELECT l FROM  Libro l WHERE l.id LIKE :id ")
    public Libro buscarPoID(@Param("id")String id);

    @Query("SELECT l from Libro l WHERE l.alta = true ")
	public List<Libro> buscarActivos();
        
    @Query("SELECT l FROM Libro l WHERE l.autor.nombre LIKE :nombre")
    public List<Libro> buscarLibrosPorNombreAutor(@Param("nombre")String nombre);
}
