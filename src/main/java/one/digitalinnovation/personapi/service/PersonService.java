package one.digitalinnovation.personapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import one.digitalinnovation.personapi.dto.response.MessageResponseDTO;
import one.digitalinnovation.personapi.entity.Person;
import one.digitalinnovation.personapi.repository.PersonRepository;

@Service
public class PersonService {

	private PersonRepository repository;

	@Autowired
	public PersonService(PersonRepository personRepository) {
		this.repository = personRepository;
	}

	public MessageResponseDTO createPerson(@RequestBody Person person) {
		Person savePerson = repository.save(person);
		return MessageResponseDTO.builder().message("Created person with ID " + savePerson.getId()).build();
	}

}
