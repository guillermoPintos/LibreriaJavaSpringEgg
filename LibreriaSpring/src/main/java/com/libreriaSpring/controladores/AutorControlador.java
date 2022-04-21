
package com.libreriaSpring.controladores;

import com.libreriaSpring.entidades.Autor;
import com.libreriaSpring.servicios.AutorServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/autor")
public class AutorControlador {
    
    @Autowired
    private AutorServicio autorServicio;
    
    @GetMapping("/form")
    public String form() {
        return "form-autor.html";
    }
   
    @PostMapping("/form")
    public String crearAutor(ModelMap modelo, @RequestParam String nombre) {
        
        try {
            autorServicio.guardar(nombre, true);
        
        modelo.put("exito", "El autor '" +nombre+"' se cargo exitosamente.");
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
        }
        
        return "form-autor.html";
    }
     @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        
        List<Autor> autores = autorServicio.listarTodos();
        modelo.put("autores", autores);
        
        return "lista-autores.html";
        
    }
     @GetMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id) {
        

        try{
            
           Autor autor = autorServicio.buscarPorId(id);
            modelo.put("autor", autor);
            
        }catch (Exception ex){
            
        modelo.put("error", ex.getMessage());
        }
        return "modificar-autor.html";
    }
    
     @PostMapping("/editar")
    public String modificarLibro(RedirectAttributes attr,@RequestParam String id, @RequestParam String nombre) {
        
        try {
            autorServicio.actualizar(id, nombre, true);
        
        attr.addFlashAttribute("exito", "El autor '" +nombre+"' se modifico exitosamente.");
        } catch (Exception ex) {
            attr.addFlashAttribute("error", ex.getMessage());
        }
        
        return "redirect:/autor/lista";
    }
    
      @GetMapping("/eliminar/{id}")
    public String eliminar(RedirectAttributes attr, @PathVariable String id) {
        
    
        try{
            
           autorServicio.baja(id);
           attr.addFlashAttribute("exito", "El autor se dio de baja correctamente");
            
        }catch (Exception ex){
            
        attr.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/autor/lista/";
    }
}