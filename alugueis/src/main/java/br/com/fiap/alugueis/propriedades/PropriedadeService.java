package br.com.fiap.alugueis.propriedades;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropriedadeService {

    @Autowired
    PropriedadeRepository repository;

    public List<Propriedade> findAll(){
        return repository.findAll();
    }

    public boolean delete(Long id) {
        var task = repository.findById(id);
        if(task.isEmpty()) return false;

        repository.deleteById(id);
        return true;
    }

    public void save(Propriedade propriedade) {
        repository.save(propriedade);
    }
    
}
