package br.com.fiap.alugueis.contrato;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContratoService {

    @Autowired
    ContratoRepository repository;

    public List<Contrato> findAll(){
        return repository.findAll();
    }

    public boolean delete(Long id) {
        var task = repository.findById(id);
        if(task.isEmpty()) return false;

        repository.deleteById(id);
        return true;
    }

    public void save(Contrato contrato) {
        repository.save(contrato);
    }
    
}
