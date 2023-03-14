package com.example.RESTful.Library.controller;

import com.example.RESTful.Library.dao.dao.AbstractDaoImpl;
import com.example.RESTful.Library.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public abstract class AbstractController<
        T,
        D extends AbstractDaoImpl<T>,
        S extends AbstractService<T, D>
    >  {
    protected final S service;

    @Autowired
    protected AbstractController(S service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<T>> allElements() {
        return ResponseEntity.ok(service.findAll());
    }
    @PostMapping
    public ResponseEntity<T> saveElement(@RequestBody T element) {
        if (element != null) {
            service.save(element);
        }
        return ResponseEntity.ok(element);
    }
    @GetMapping("/{id}")
    public ResponseEntity<T> selectedElement(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<T> updateElement(@PathVariable Long id, @RequestBody T element) {
        if (element != null && service.findById(id) != null) {
            service.update(element);
        }
        return ResponseEntity.ok(element);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<List<T>> deleteElement(@PathVariable Long id) {
        T element = service.findById(id);
        if (element != null) {
            service.delete(element);
        }
        return ResponseEntity.ok(service.findAll());
    }
}
