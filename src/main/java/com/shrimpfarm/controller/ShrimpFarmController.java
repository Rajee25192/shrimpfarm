package com.shrimpfarm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shrimpfarm.exception.ShrimpFarmException;
import com.shrimpfarm.model.Farms;
import com.shrimpfarm.model.Ponds;
import com.shrimpfarm.repository.ShrimpFarmRepository;
import com.shrimpfarm.repository.ShrimpPondsRepository;

@Controller
@RequestMapping("/farms/")
public class ShrimpFarmController {

	@Autowired
	private ShrimpFarmRepository farmRepository;

	@Autowired
	private ShrimpPondsRepository pondsRepository;

	int size = 0;

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
	public String showUpdateForm(@PathVariable("id") String id, Model model) throws ShrimpFarmException {
		Farms farm1 = farmRepository.findById(id).orElseThrow(() -> new ShrimpFarmException("Farm not found :: " + id));

		model.addAttribute("farms", farm1);
		return "update-farm";
	}

	@PostMapping("updateFarm/{id}")
	public String updateFarm(@PathVariable(value = "id") String id, Farms farms, BindingResult result, Model model) {
		if (result.hasErrors()) {
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

	@GetMapping("getFarmSize/{id}")
	public String getFarmSize(@PathVariable("id") String id, Model model) throws ShrimpFarmException {
		Farms farm = farmRepository.findById(id).orElseThrow(() -> new ShrimpFarmException("Farm not found :: " + id));
		List<Ponds> farm1 = pondsRepository.findByFarmId(id);
		farm1.forEach(f -> {
			size += Integer.parseInt(f.getPondSize());
		});
		farm.setFarmSize(String.valueOf(size));
		model.addAttribute("farms", farmRepository.findAll());
		return "index";
	}
}
