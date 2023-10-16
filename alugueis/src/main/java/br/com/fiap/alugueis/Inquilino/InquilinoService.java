package br.com.fiap.alugueis.Inquilino;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InquilinoService {

    @Autowired
    InquilinoRepository repository;

    public List<Inquilino> findAll(){
        return repository.findAll();
    }

    public boolean delete(Long id) {
        var task = repository.findById(id);
        if(task.isEmpty()) return false;

        repository.deleteById(id);
        return true;
    }

    public void save(Inquilino inquilino) {
        repository.save(inquilino);
    }
    
}
