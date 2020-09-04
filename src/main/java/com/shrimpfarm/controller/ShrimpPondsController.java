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
import com.shrimpfarm.model.Ponds;
import com.shrimpfarm.repository.ShrimpFarmRepository;
import com.shrimpfarm.repository.ShrimpPondsRepository;

@Controller
@RequestMapping("/farms/{farmId}/")
public class ShrimpPondsController {

	
	@Autowired
    private ShrimpPondsRepository pondsRepository;

    @Autowired
    private ShrimpFarmRepository farmRepository;

	
	@GetMapping("showForm")
	public String showZipcodeForm(Ponds ponds,Model model) {
		return "add-pond";
	}
	
    @GetMapping("listponds")
    public String getPondsByFarmId(@PathVariable(value = "farmId") String farmId, Model model) {
        model.addAttribute("ponds", pondsRepository.findByFarmId(farmId));
        model.addAttribute("farmId",farmId);
		return "view-ponds";
    }

    @PostMapping("addponds")
    public String createPond(@PathVariable(value = "farmId") String farmId,
       Ponds ponds, BindingResult result, Model model) throws ShrimpFarmException {
    	if (result.hasErrors()) {
			return "add-pond";
		}
        farmRepository.findById(farmId).map(farm -> {
            ponds.setFarm(farm);
           return pondsRepository.save(ponds);
        }).orElseThrow(() -> new ShrimpFarmException("Farm not found"));
        
        return "redirect:listponds";
    }
    
    @GetMapping("editponds/{id}")
	public String showUpdatePond(@PathVariable(value = "farmId") String farmId,@PathVariable ("id") String id, Model model) throws ShrimpFarmException {
		Ponds ponds = pondsRepository.findById(id).orElseThrow(() -> new ShrimpFarmException("Pond not found :: " + id));
		
		model.addAttribute("ponds", ponds);
		return "update-pond";
	}

    @PostMapping("editponds/updateponds/{id}")
    public String updatePond(@PathVariable(value = "farmId") String farmId,
        @PathVariable(value = "id") String id, Ponds ponds,BindingResult result, Model model)
    throws ShrimpFarmException {
    	
    	if(result.hasErrors()) {
			ponds.setId(id);
			return "update-pond";
		}
        if (!farmRepository.existsById(farmId)) {
            throw new ShrimpFarmException("FarmId is not found");
        }
        Ponds pond =  pondsRepository.findById(id).orElseThrow(() -> new ShrimpFarmException("Pond not found :: " + id));
        pond.setPondName(ponds.getPondName());
        pond.setPondSize(ponds.getPondSize());
        pondsRepository.save(pond);
        model.addAttribute("ponds", pondsRepository.findByFarmId(farmId));
        model.addAttribute("farmId",farmId);
        return "view-ponds";
       
    }

    @GetMapping("deleteponds/{id}")
    public String deletePond(@PathVariable(value = "farmId") String farmId,
        @PathVariable(value = "id") String id, Model model) throws ShrimpFarmException {
    Ponds pond=  pondsRepository.findByIdAndFarmId(id, farmId).orElseThrow(() -> new ShrimpFarmException(
            "Pond not found with id " + id + " and farmId " + farmId));
      pondsRepository.delete(pond);
      
        model.addAttribute("ponds", pondsRepository.findByFarmId(farmId));
        model.addAttribute("farmId",farmId);
        return "view-ponds";
    }
}
