package com.shrimpfarm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shrimpfarm.exception.ShrimpFarmException;
import com.shrimpfarm.model.Farms;
import com.shrimpfarm.repository.ShrimpFarmRepository;

@Controller
@RequestMapping("/farms/")
public class ShrimpFarmController {

	@Autowired
	private ShrimpFarmRepository farmRepository;

	@GetMapping("showForm")
	public String showZipcodeForm(Farms farms) {
		return "add-farm";
	}

	@GetMapping("listAllFarms")
	public String getAllFarms(Model model) {
		model.addAttribute("farms", farmRepository.findAll());
		return "index";
	}

	@PostMapping("createFarm")
	public String createNewFarm(Farms farms, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-farm";
		}
		this.farmRepository.save(farms);
		return "redirect:listAllFarms";
	}
	
	@GetMapping("edit/{id}")
	public String showUpdateForm(@PathVariable ("id") String id, Model model) throws ShrimpFarmException {
		Farms farm1 = farmRepository.findById(id).orElseThrow(() -> new ShrimpFarmException("Farm not found :: " + id));
		
		model.addAttribute("farms", farm1);
		return "update-farm";
	}

	@PostMapping("updateFarm/{id}")
	public String updateFarm(@PathVariable(value = "id") String id, Farms farms, BindingResult result, Model model) {
		if(result.hasErrors()) {
			farms.setId(id);
			return "update-farm";
		}
		farmRepository.save(farms);
		model.addAttribute("farms", this.farmRepository.findAll());
		return "index";
	}

	@GetMapping("deleteFarms/{id}")
	public String deleteFarms(@PathVariable(value = "id") String id, Model model) throws ShrimpFarmException {
		Farms farms = farmRepository.findById(id).orElseThrow(() -> new ShrimpFarmException("Farm not found :: " + id));

		farmRepository.delete(farms);

		model.addAttribute("farms", this.farmRepository.findAll());
		return "index";
	}
}
