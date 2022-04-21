package com.libreriaSpring.controladores;

import com.libreriaSpring.entidades.Autor;
import com.libreriaSpring.errores.ErrorServicio;
import com.libreriaSpring.servicios.AutorServicio;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors")
public class AutorControllerRest {
    
    private HashMap<Object, Object> data;
    
    @Autowired
    public AutorServicio autorService;
    
    @GetMapping
    public ResponseEntity<List<Autor>> getAll() {
        List<Autor> authors = autorService.listarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(authors);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<HashMap<Object, Object>> getById(@PathVariable String id) {
        data = new HashMap();
        try {
            Autor author = autorService.buscarPorId(id);
            data.put("author", author);
            return ResponseEntity.status(HttpStatus.OK).body(data);
        } catch (ErrorServicio ex) {
            data.put("message", "Author with id: " + id + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(data);
        }
        
    }
    
}
