package one.digitalinnovation.personapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import one.digitalinnovation.personapi.dto.request.PersonDTO;
import one.digitalinnovation.personapi.dto.response.MessageResponseDTO;
import one.digitalinnovation.personapi.entity.Person;
import one.digitalinnovation.personapi.exception.PersonNotFoundException;
import one.digitalinnovation.personapi.mapper.PersonMapper;
import one.digitalinnovation.personapi.repository.PersonRepository;

@Service
public class PersonService {

	private PersonRepository repository;

	private PersonMapper personMapper;

	public PersonService(PersonRepository personRepository, PersonMapper personMapper) {
		this.repository = personRepository;
		this.personMapper = personMapper;
	}

	@Transactional
	public MessageResponseDTO createPerson(PersonDTO personDTO) {

		Person personToSave = personMapper.toModel(personDTO);
		Person savedPerson = repository.save(personToSave);

		return createMessageResponse(savedPerson.getId(), "Created person with ID ");
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

	@Transactional(readOnly = true)
	public PersonDTO findById(Long id) throws PersonNotFoundException {
//		ANTES
//		Optional<Person> optPerson = repository.findById(id);
//		if (optPerson.isEmpty()) {
//			throw new PersonNotFoundException(id);
//		}
// 		REFATORADO
		Person person = verifyIfExists(id);

		return personMapper.toDTO(person);
	}

	@Transactional
	public void delete(Long id) throws PersonNotFoundException {
		Person person = verifyIfExists(id);
		repository.delete(person);
	}

	@Transactional
	public MessageResponseDTO replace(PersonDTO personDTO, Long id) throws PersonNotFoundException {
		verifyIfExists(id);

		Person personTUpdate = personMapper.toModel(personDTO);
		Person UpdatePerson = repository.save(personTUpdate);

		return createMessageResponse(UpdatePerson.getId(), "Update person with ID ");

	}

	private Person verifyIfExists(Long id) throws PersonNotFoundException {
		return repository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
	}

	private MessageResponseDTO createMessageResponse(Long id, String message) {
		return MessageResponseDTO.builder().message(message + id).build();
	}

}
