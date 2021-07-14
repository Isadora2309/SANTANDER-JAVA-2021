package com.dioJavaProject3.controller;
import org.springframeqork.web.bind.annotation.RestController;
@RestController @RequestMapping("/jornada")
public class JornadaTrabalhoController{
    @Autowired
    JornadaService jornadaService;
    @PostMapping
    public jornadaTrabalho createJornada(@RequestBody jornadaTrabalho jornadaTrabalho){
        return jornadaService.saveJornada(jornadaTrabalho);
    }
    @GetMapping("/list")
    public List<jornadaTrabalho> getJornadaList(){
        return jornadaService.findAll();
    }
    @GetMapping("/{idJornada}")
    public ResponseEntity<jornadaTrabalho> jornadaTrabalho getJornadaById(@PathVariable("idJornada") Long idJornada) throws  Exception{
        return ResponseEntity.ok(jornadaService.getById(idJornada).orElseThrow(()->new NoSuchElementException("Jornada não encontrda!")));
    }
    @PutMapping
    public jornadaTrabalho updateJornada(@RequestBody jornadaTrabalho jornadaTrabalho){
        return jornadaService.saveJornada(jornadaTrabalho);
    }
    @DeleteMapping("/{idJornada}")
    public ResponseEntity<jornadaTrabalho> jornadaTrabalho deleteJornadaById(@PathVariable("idJornada") Long idJornada) throws  Exception{
        try{jornadaService.deleteJornada(idJornada);} catch(Exception e){
            System.out.println(e.getMessage());
        }
        return (ResponseEntity<jornadaTrabalho>) ResponseEntity.ok();

    }
}