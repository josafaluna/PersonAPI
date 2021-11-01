package one.digitalinnovation.personapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import one.digitalinnovation.personapi.dto.request.PersonDTO;
import one.digitalinnovation.personapi.dto.response.MessageResponseDTO;
import one.digitalinnovation.personapi.entity.Person;
import one.digitalinnovation.personapi.mapper.PersonMapper;
import one.digitalinnovation.personapi.repository.PersonRepository;

@Service
public class PersonService {

	private PersonRepository repository;

	@Autowired
	PersonMapper personMapper;

	@Autowired
	public PersonService(PersonRepository personRepository) {
		this.repository = personRepository;
	}

	@Transactional
	public MessageResponseDTO createPerson(PersonDTO personDTO) {

		Person personToSave = personMapper.toModel(personDTO);

		Person savedPerson = repository.save(personToSave);

		return MessageResponseDTO.builder().message("Created person with ID " + savedPerson.getId()).build();
	}

	@Transactional(readOnly = true)
	public List<PersonDTO> findAll() {
		List<Person> allPerson = repository.findAll();
		// eu criei assim
		// return allPerson.stream().map(p ->
		// personMapper.toDTO(p)).collect(Collectors.toList());

		// o professor criou assim
		return allPerson.stream().map(personMapper::toDTO).collect(Collectors.toList());
	}

}
