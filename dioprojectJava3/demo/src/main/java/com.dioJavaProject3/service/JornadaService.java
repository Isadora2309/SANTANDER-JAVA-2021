package com.dioJavaProject3;
import org.springframework.stereotype.Service;
@Service
public class JornadaService{
    JornadaRepository jornadaRepository;
    @Autowired
    public JornadaService(JornadRepository jornadaRepository){
        this.jornadaRepository = jornadaRepository;
    }
    public jornadaTrabalho saveJornada(jornadaTrabalho jornadaTrabalho){
        return jornadaRepository.save(jornadaTrabalho);
    }
    public List<jornadaTrabalho> findAll(){
        return jornadaRepository.findAll();
    }
    public Optional<jornadaTrabalho> getById(Long idJornada){
        return jornadaRepository.findById(idJornada);
    }
    public jornadaTrabalho updatedJornada(jornadaTrabalho jornadaTrabalho){
        return jornadaRepository.save(jornadaTrabalho);
    }
    public jornadaTrabalho deleteJornada(jornadaTrabalho jornadaTrabalho){
        return jornadaRepository.deleteById(jornadaTrabalho);
    }
}