package one.digitalinnovation.personapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public MessageResponseDTO createPerson(PersonDTO personDTO) {

		Person personToSave = personMapper.toModel(personDTO);

		Person savedPerson = repository.save(personToSave);

		return MessageResponseDTO.builder().message("Created person with ID " + savedPerson.getId()).build();
	}

}
