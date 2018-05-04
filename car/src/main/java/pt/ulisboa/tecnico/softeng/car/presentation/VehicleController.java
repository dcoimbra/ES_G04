package pt.ulisboa.tecnico.softeng.car.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import pt.ulisboa.tecnico.softeng.car.services.local.CarInterface;
import pt.ulisboa.tecnico.softeng.car.services.local.dataobjects.RentACarData;
import pt.ulisboa.tecnico.softeng.car.services.local.dataobjects.VehicleData;

@Controller
@RequestMapping(value = "/rentacars/{code}/vehicles")
public class VehicleController {
    private static Logger logger = LoggerFactory.getLogger(VehicleController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String vehicleForm(Model model, @PathVariable String code) {
        logger.info("vehicleForm rentACarCode:{}", code);

        RentACarData rentACarData = CarInterface.getRentACarDataByCode(code);

        if (rentACarData == null) {
            model.addAttribute("error", "Error: it does not exist a rentACar with the code " + code);
            model.addAttribute("rentacar", new RentACarData());
            model.addAttribute("rentacars", CarInterface.getRentACars());
            return "rentacars";
        } else {
            model.addAttribute("vehicle", new VehicleData());
            model.addAttribute("rentacar", rentACarData);
            return "vehicles";
        }
    }

    @RequestMapping(value="/cars", method = RequestMethod.POST)
    public String carSubmit(Model model, @PathVariable String code, @ModelAttribute VehicleData vehicleData) {
        logger.info("vehicleSubmit rentACarCode:{}, plate:{}, kilometers:{}, price:{}",
                code, vehicleData.getPlate(), vehicleData.getKilometers(), vehicleData.getPrice());

        try {
            CarInterface.createCar(code, vehicleData);
        } catch (CarException ce) {
            model.addAttribute("carError", "Error: it was not possible to create the car");
            model.addAttribute("vehicle", vehicleData);
            model.addAttribute("rentacar", CarInterface.getRentACarDataByCode(code));
            return "vehicles";
        }

        return "redirect:/rentacars/" + code + "/vehicles";
    }

    @RequestMapping(value="/motorcycles", method = RequestMethod.POST)
    public String motorcycleSubmit(Model model, @PathVariable String code, @ModelAttribute VehicleData vehicleData) {
        logger.info("vehicleSubmit rentACarCode:{}, plate:{}, kilometers:{}, price:{}",
                code, vehicleData.getPlate(), vehicleData.getKilometers(), vehicleData.getPrice());

        try {
            CarInterface.createMotorcycle(code, vehicleData);
        } catch (CarException ce) {
            model.addAttribute("motorcycleError", "Error: it was not possible to create the motorcycle");
            model.addAttribute("vehicle", vehicleData);
            model.addAttribute("rentacar", CarInterface.getRentACarDataByCode(code));
            return "vehicles";
        }

        return "redirect:/rentacars/" + code + "/vehicles";
    }
}
