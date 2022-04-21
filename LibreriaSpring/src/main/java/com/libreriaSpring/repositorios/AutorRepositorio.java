
package com.libreriaSpring.repositorios;

import com.libreriaSpring.entidades.Autor;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



    
    
    @Repository

public interface AutorRepositorio extends JpaRepository<Autor, String>{
    
    @Query("SELECT a FROM  Autor a WHERE a.id LIKE :id ")
    public Autor buscarPodID(@Param("id")String id);
    
    @Query("SELECT a from Autor a WHERE a.alta = true ")
	public List<Autor> buscarActivos();
    
    
    }
    

    

