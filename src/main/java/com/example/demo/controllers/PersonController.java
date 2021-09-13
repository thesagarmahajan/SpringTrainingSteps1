package com.example.demo.controllers;

import com.example.demo.models.Person;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("person")
public class PersonController {

    private HashMap<String, Object> commonResp;
    private static ArrayList<Person> db = new ArrayList<>();

    @PostMapping("add")
    public HashMap<String, Object> addPerson(@RequestBody Person person){
        this.commonResp = new HashMap<>();
        this.commonResp = new HashMap<>();
        Person p = new Person(UUID.randomUUID(), person.getName());
        this.db.add(p);
        this.commonResp.put("SUCCESS", "Person Added");
        this.commonResp.put("ADDED_PERSON", p);
        return this.commonResp;
    }

    @GetMapping("get/all")
    public ArrayList<Person> getAllPerson(){
        return this.db;
    }

    @GetMapping("get/one/{id}")
    public Optional<Person> getOnePerson(@PathVariable("id") UUID id){
        this.commonResp = new HashMap<>();
        Optional<Person> foundPerson = this.db.stream().filter(person -> person.getId().equals(id)).findFirst();
        return foundPerson;
    }

    @DeleteMapping("delete/{id}")
    public int deletePerson(@PathVariable("id") UUID id){
        Optional<Person> foundPerson = this.getOnePerson(id);
        if(foundPerson.isEmpty()){
            return 0;
        }
        else{
            this.db.remove(foundPerson.get());
            return 1;
        }
    }

    @PutMapping("update/{id}")
    public int updatePerson(@PathVariable("id") UUID id, @RequestBody Person personToUpdate){
        return this.getOnePerson(id).map(person -> {
            int indexOfPersonToUpdate = this.db.indexOf(person);
            this.db.set(indexOfPersonToUpdate, new Person(id, personToUpdate.getName()));
            return 1;
        }).orElse(0);
    }


}
