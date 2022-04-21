
package com.libreriaSpring.controladores;


import com.libreriaSpring.entidades.Cliente;

import com.libreriaSpring.entidades.Libro;
import com.libreriaSpring.entidades.Prestamo;
import com.libreriaSpring.servicios.ClienteServicio;
import com.libreriaSpring.servicios.LibroServicio;
import com.libreriaSpring.servicios.PrestamoServicio;
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
@RequestMapping("/prestamo")
public class PrestamoControlador {
    
    @Autowired
    private LibroServicio libroServicio;
    
    @Autowired
    private ClienteServicio clienteServicio;
    
      @Autowired
    private PrestamoServicio prestamoServicio;
    
    @GetMapping("/form")
    public String form(ModelMap modelo) {
        
        List<Cliente> clientes = clienteServicio.listarActivos();
        modelo.put("clientes", clientes);
        
         List<Libro> libros = libroServicio.listarActivos();
        modelo.put("libros", libros);
        
        
        return "form-prestamo.html";
    }
   
    @PostMapping("/form")
    public String crearLibro(RedirectAttributes attr , @RequestParam String idLibro, @RequestParam String idCliente) {
        
        try {
            prestamoServicio.guardar(idLibro, idCliente);
        
        attr.addFlashAttribute("exito", "El préstamo se cargo exitosamente.");
        } catch (Exception ex) {
            attr.addFlashAttribute("error", ex.getMessage());
        }
        
        return "redirect:/prestamo/form";
    }
    
     @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        
        List<Prestamo> prestamos = prestamoServicio.listarTodos();
        modelo.put("prestamos", prestamos);
        
        return "lista-prestamo.html";
        
    }
    
      @GetMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id) {
        
     List<Libro> libros = libroServicio.listarTodos();
        modelo.put("libros", libros);
        
         List<Cliente> clientes = clienteServicio.listarTodos();
        modelo.put("clientes", clientes);
        try{
            
           Prestamo prestamos = prestamoServicio.buscarPorId(id);
            modelo.put("prestamos", prestamos);
            
        }catch (Exception ex){
            
        modelo.put("error", ex.getMessage());
        }
        return "modificar-prestamo.html";
    }
    
     @PostMapping("/editar")
    public String modificarPrestamo(RedirectAttributes attr,@RequestParam String id, @RequestParam String idLibro, @RequestParam String idCliente) {
        
        try {
            prestamoServicio.actualizar(id, true, idLibro, idCliente);
        
        attr.addFlashAttribute("exito", "El prestamo se modifico exitosamente.");
        } catch (Exception ex) {
            attr.addFlashAttribute("error", ex.getMessage());
        }
        
        return "redirect:/prestamo/lista";
    }
    
      @GetMapping("/eliminar/{id}")
    public String eliminar(RedirectAttributes attr, @PathVariable String id) {
        
    
        try{
            
           prestamoServicio.baja(id);
           attr.addFlashAttribute("exito", "El prestamo se dio de baja correctamente");
            
        }catch (Exception ex){
            
        attr.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/prestamo/lista/";
    }
    
}
