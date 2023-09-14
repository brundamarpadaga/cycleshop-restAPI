package com.talentsprint.cycleshop.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.talentsprint.cycleshop.entity.Cycle;
import com.talentsprint.cycleshop.repository.CycleRepository;
import com.talentsprint.cycleshop.service.CycleService;


@Controller
@RequestMapping("/cycle")
public class CycleController {

    @Autowired
    private CycleService cycleService;
    
    @Autowired
    private CycleRepository cycleRepository;
    
    @GetMapping("/delete/{id}")
    @ResponseBody
    public String delCycle(@PathVariable("id") long id) {
    	cycleRepository.deleteById(id);
		return "succesfully deleted";
    }
    
    @PostMapping("/add")
    @ResponseBody
    public String addCycle(@RequestBody Cycle cycle) {
    	cycleRepository.save(cycle);
    	return "Cycle succesfully added";
    }
    
    
    @GetMapping("/{id}/borrow")
    @ResponseBody
    public String borrowCycle(@PathVariable long id, @RequestParam(required=false, defaultValue="1") int count) {
        cycleService.borrowCycle(id, count);
        //just a comment
        return " Cycle borrowed"; //TODO: redirect to List handler
    }

    @GetMapping("/{id}/return")
    @ResponseBody
    public String returnCycle(@PathVariable long id, @RequestParam(required=false, defaultValue="1") int count) {
        cycleService.returnCycle(id, count);
        return "Cycle returned "; //TODO: redirect to list handler
    }

    @PostMapping("/{id}/restock")
    @ResponseBody
    public String restockCycle(@PathVariable long id, @RequestParam(required=false, defaultValue="1") int count) {
        cycleService.restockBy(id, count);
        return "Restocked succesfully";
    }

    @GetMapping("/list")
    @ResponseBody
    public Iterable<Cycle> listAvailableCycles(Model model) {
		/*
		 * var allCycles = cycleService.listAvailableCycles();
		 * model.addAttribute("allCycles", allCycles);
		 */
        return cycleRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Optional<Cycle> cycleDetail(@PathVariable long id, Model model) {
		/*
		 * var cycle = cycleService.findByIdOrThrow404(id); model.addAttribute("cycle",
		 * cycle);
		 */
        return cycleRepository.findById(id);
    }

}
