
package com.libreriaSpring.repositorios;

import com.libreriaSpring.entidades.Editorial;
import com.libreriaSpring.entidades.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



    @Repository

public interface EditorialRepositorio extends JpaRepository<Editorial, String>{
    
    @Query("SELECT e FROM  Editorial e WHERE e.id LIKE :id ")
    public Libro buscarPodID(@Param("id")String id);
    
     @Query("SELECT e from Editorial e WHERE e.alta = true ")
	public List<Editorial> buscarActivos();
    
    
    
    }
    

