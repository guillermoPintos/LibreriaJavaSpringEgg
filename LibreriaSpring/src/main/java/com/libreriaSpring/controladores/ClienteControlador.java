
package com.libreriaSpring.controladores;



import com.libreriaSpring.entidades.Cliente;
import com.libreriaSpring.servicios.ClienteServicio;
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
@RequestMapping("/cliente")
public class ClienteControlador {
    
    @Autowired
    private ClienteServicio clienteServicio;
    
    
    @GetMapping("/form")
    public String form(ModelMap modelo) {
        
        
        
        return "form-cliente.html";
    }
   
    @PostMapping("/form")
    public String crearCliente(RedirectAttributes attr, @RequestParam Long documento, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String telefono ) {
        
        try {
            clienteServicio.guardar(documento, nombre, apellido, telefono);
        
        attr.addFlashAttribute("exito", "El cliente '" +apellido+"' se cargo exitosamente.");
        } catch (Exception ex) {
            attr.addFlashAttribute("error", ex.getMessage());
        }
        
        return "redirect:/cliente/form";
    }
    
     @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        
        List<Cliente> clientes = clienteServicio.listarTodos();
        modelo.put("clientes", clientes);
        
        return "lista-cliente.html";
        
    }
    
      @GetMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id) {
        
    
        try{
            
           Cliente cliente = clienteServicio.buscarPorId(id);
            modelo.put("cliente", cliente);
            
        }catch (Exception ex){
            
        modelo.put("error", ex.getMessage());
        }
        return "modificar-cliente.html";
    }
    
     @PostMapping("/editar")
    public String modificarLibro(RedirectAttributes attr,@RequestParam String id, @RequestParam Long documento, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String telefono) {
        
        try {
            clienteServicio.actualizar(id, documento, nombre, apellido, telefono);
        
        attr.addFlashAttribute("exito", "El cliente '" +apellido+"' se modifico exitosamente.");
        } catch (Exception ex) {
            attr.addFlashAttribute("error", ex.getMessage());
        }
        
        return "redirect:/cliente/lista";
    }
    
      @GetMapping("/eliminar/{id}")
    public String eliminar(RedirectAttributes attr, @PathVariable String id) {
        
    
        try{
            
           clienteServicio.baja(id);
           attr.addFlashAttribute("exito", "El cliente se dio de baja correctamente");
            
        }catch (Exception ex){
            
        attr.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/cliente/lista/";
    }
    
}
