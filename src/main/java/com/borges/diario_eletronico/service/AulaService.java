package com.borges.diario_eletronico.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.borges.diario_eletronico.domain.Aula;
import com.borges.diario_eletronico.domain.ProfessorTurmaDisciplina;
import com.borges.diario_eletronico.domain.dtos.AulaDTO;
import com.borges.diario_eletronico.repository.AulaRepository;
import com.borges.diario_eletronico.service.execeptions.ObjectNotFoundException;

@Service
public class AulaService {

	@Autowired
	private AulaRepository repository;
	@Autowired
	private ProfessorTurmaDisciplinaService professorService;

	public Aula findById(Integer id) {
		Optional<Aula> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
	}

	public List<Aula> findAll() {
		return repository.findAll();
	}

	public Aula create(AulaDTO objDTO) {
		return repository.save(newAula(objDTO));
	}

	public Aula update(Integer id, @Valid AulaDTO objDTO) {
		
		objDTO.setId(id);
		Aula oldObj = findById(id);
		
		/* if (oldObj.getProfessorAula().size() > 0) {

			throw new DataIntegratyViolationException("Aula possui professor, não pode ser Atulaizada!");

		} */
		

		oldObj = newAula(objDTO);
		
		return repository.save(oldObj);
	}

	private Aula newAula(AulaDTO objDTO) {
		
		ProfessorTurmaDisciplina professor = professorService.findById(objDTO.getProfessor());
		Aula aula = new Aula();
		
		if (objDTO.getId() != null) {
			aula.setId(objDTO.getId());
		}

		aula.setData(objDTO.getData());
		aula.setHoraInicio(objDTO.getHoraInicio());
		aula.setHoraFim(objDTO.getHoraFim());
		aula.setConteudo(objDTO.getConteudo());
		aula.setProfessorTurmaDisciplina(professor);

		return aula;
	}

	public void delete(Integer id) {

		Aula obj = findById(id);

		/* if (obj.getProfessorTurmaDisciplina().size() > 0) {

			throw new DataIntegratyViolationException("Aula possui professor, não pode ser deletad!");

		} */
		repository.deleteById(id);
	}

}
