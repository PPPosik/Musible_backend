package com.team_musible.musible.Controller;

import com.team_musible.musible.Model.Entity.TestTable;
import com.team_musible.musible.Model.Repository.TestTableRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/testTable")
public class TestTableController {

    private final TestTableRepository testTableRepository;

    public TestTableController(TestTableRepository testTableRepository) {
        this.testTableRepository = testTableRepository;
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public TestTable createTestTable(@RequestBody TestTable testTable) {
        return testTableRepository.save(testTable);
    }

    @GetMapping("/findAll")
    public Iterable<TestTable> getTestTable() {
        return testTableRepository.findAll();
    }
}