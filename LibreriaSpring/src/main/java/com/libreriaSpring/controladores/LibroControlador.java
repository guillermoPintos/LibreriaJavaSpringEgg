
package com.libreriaSpring.controladores;


import com.libreriaSpring.entidades.Autor;
import com.libreriaSpring.entidades.Editorial;
import com.libreriaSpring.entidades.Libro;
import com.libreriaSpring.servicios.AutorServicio;
import com.libreriaSpring.servicios.EditorialServicio;
import com.libreriaSpring.servicios.LibroServicio;
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
@RequestMapping("/libro")
public class LibroControlador {
    
    @Autowired
    private LibroServicio libroServicio;
    
    @Autowired
    private AutorServicio autorServicio;
    
      @Autowired
    private EditorialServicio editorialServicio;
    
    @GetMapping("/form")
    public String form(ModelMap modelo) {
        
        List<Autor> autores = autorServicio.listarActivos();
        modelo.put("autores", autores);
        
         List<Editorial> editoriales = editorialServicio.listarActivos();
        modelo.put("editoriales", editoriales);
        
        
        return "form-libro.html";
    }
   
    @PostMapping("/form")
    public String crearLibro(RedirectAttributes attr, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam Integer ejemplaresPrestados, @RequestParam Integer ejemplaresRestantes, @RequestParam String idAutor, @RequestParam String idEditorial) {
        
        try {
            libroServicio.guardar(titulo, true, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, idAutor, idEditorial);
        
        attr.addFlashAttribute("exito", "El libro '" +titulo+"' se cargo exitosamente.");
        } catch (Exception ex) {
            attr.addFlashAttribute("error", ex.getMessage());
        }
        
        return "redirect:/libro/form";
    }
    
     @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        
        List<Libro> libros = libroServicio.listarTodos();
        modelo.put("libros", libros);
        
        return "lista-libro.html";
        
    }
    
      @GetMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id) {
        
     List<Autor> autores = autorServicio.listarTodos();
        modelo.put("autores", autores);
        
         List<Editorial> editoriales = editorialServicio.listarTodos();
        modelo.put("editoriales", editoriales);
        try{
            
           Libro libro = libroServicio.buscarPorId(id);
            modelo.put("libro", libro);
            
        }catch (Exception ex){
            
        modelo.put("error", ex.getMessage());
        }
        return "modificar-libro.html";
    }
    
     @PostMapping("/editar")
    public String modificarLibro(RedirectAttributes attr,@RequestParam String id, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam Integer ejemplaresPrestados, @RequestParam Integer ejemplaresRestantes, @RequestParam String idAutor, @RequestParam String idEditorial) {
        
        try {
            libroServicio.actualizar(id, titulo, true, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, idAutor, idEditorial);
        
        attr.addFlashAttribute("exito", "El libro '" +titulo+"' se modifico exitosamente.");
        } catch (Exception ex) {
            attr.addFlashAttribute("error", ex.getMessage());
        }
        
        return "redirect:/libro/lista";
    }
    
      @GetMapping("/eliminar/{id}")
    public String eliminar(RedirectAttributes attr, @PathVariable String id) {
        
    
        try{
            
           libroServicio.baja(id);
           attr.addFlashAttribute("exito", "El autor se dio de baja correctamente");
            
        }catch (Exception ex){
            
        attr.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/libro/lista/";
    }
    
}
