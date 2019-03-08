package co.kurapka.springbootexample.persons;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonResource {

	@GetMapping("v1/person")
	public PersonV1 getPersonV1() {
		return new PersonV1("Bob Sinclair");
	}
	
	@GetMapping("v2/person")
	public PersonV2 getPersonV2() {
		return new PersonV2(new Name("Bob",  "Sinclair"));
	}
	
	@GetMapping(value="person/param", params="version=1")
	public PersonV1 getPersonParams1() {
		return new PersonV1("Bob Sinclair");
	}
	
	@GetMapping(value="person/param", params="version=2")
	public PersonV2 getPersonParams2() {
		return new PersonV2(new Name("Bob",  "Sinclair"));
	}
	
	@GetMapping(value="person/header", headers="X-API-VERSION=1")
	public PersonV1 getPersonHeaders1() {
		return new PersonV1("Bob Sinclair");
	}
	
	@GetMapping(value="person/header", headers="X-API-VERSION=2")
	public PersonV2 getPersonHeaders2() {
		return new PersonV2(new Name("Bob",  "Sinclair"));
	}
	
	@GetMapping(value="person/produces", produces="application/vnd.company.app-v1+json")
	public PersonV1 getPersonProduces1() {
		return new PersonV1("Bob Sinclair");
	}
	
	@GetMapping(value="person/produces", produces="application/vnd.company.app-v2+json")
	public PersonV2 getPersonProduces2() {
		return new PersonV2(new Name("Bob",  "Sinclair"));
	}
	
}
