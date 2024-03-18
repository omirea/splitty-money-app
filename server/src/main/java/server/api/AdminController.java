package server.api;

import commons.Admin;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import server.database.AdminRepository;

public class AdminController {
    private AdminRepository repo;

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getById(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.findById(id).get());
    }

    @PostMapping(path = { "", "/" })
    public ResponseEntity<Admin> add(@RequestBody Admin admin) {

        if (isNullOrEmpty(admin.getName()) || isNullOrEmpty(admin.getPassword())) {
            return ResponseEntity.badRequest().build();
        }

        Admin saved = repo.save(admin);
        return ResponseEntity.ok(saved);
    }

    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

}
