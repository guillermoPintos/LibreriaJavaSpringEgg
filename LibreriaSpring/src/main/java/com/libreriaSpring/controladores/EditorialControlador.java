
package com.libreriaSpring.controladores;

import com.libreriaSpring.entidades.Editorial;
import com.libreriaSpring.servicios.EditorialServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {
    
    @Autowired
    private EditorialServicio editorialServicio;
    
    @GetMapping("/form")
    public String form() {
        return "form-editorial.html";
    }
   
    @PostMapping("/form")
    public String crearEditorial(ModelMap modelo, @RequestParam String nombre) {
        
        try {
            editorialServicio.guardar(nombre, true);
        
        modelo.put("exito", "La editorial '" +nombre+"' se cargo exitosamente.");
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
        }
        
        return "form-editorial.html";
    }
     @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        
        List<Editorial> editoriales = editorialServicio.listarActivos();
        modelo.put("editoriales", editoriales);
        
        return "lista-editoriales.html";
        
    }
    
}