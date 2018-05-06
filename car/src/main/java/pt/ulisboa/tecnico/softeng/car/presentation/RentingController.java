package pt.ulisboa.tecnico.softeng.car.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import pt.ulisboa.tecnico.softeng.car.services.local.CarInterface;
import pt.ulisboa.tecnico.softeng.car.services.local.dataobjects.RentACarData;
import pt.ulisboa.tecnico.softeng.car.services.local.dataobjects.RentingData;
import pt.ulisboa.tecnico.softeng.car.services.local.dataobjects.VehicleData;

@Controller
@RequestMapping(value = "/rentacars/{code}/vehicles/{plate}/rentings")
public class RentingController {
    private static Logger logger = LoggerFactory.getLogger(RentingController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String rentingForm(Model model, @PathVariable String code, @PathVariable String plate) {
        logger.info("rentingForm rentACarCode:{}, vehiclePlate:{}", code, plate);

        VehicleData vehicleData = CarInterface.getVehicleDataByPlate(code, plate);

        if (vehicleData == null) {
            model.addAttribute("error",
                    "Error: it does not exist a vehicle with plate " + plate + " in rentACar with code " + code);
            model.addAttribute("rentacar", new RentACarData());
            model.addAttribute("rentacars", CarInterface.getRentACars());
            return "rentacars";
        } else {
            model.addAttribute("renting", new RentingData());
            model.addAttribute("vehicle", vehicleData);
            return "rentings";
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public String rentingSubmit(Model model, @PathVariable String code, @PathVariable String plate,
                                @ModelAttribute RentingData renting) {
        logger.info("rentingSubmit rentACarCode:{}, vehiclePlate:{}, drivingLicense:{}, begin:{}, end:{}, buyerNif:{}, buyerIban:{}",
                code, plate, renting.getDrivingLicense(), renting.getBegin(), renting.getEnd(), renting.getBuyerNif(), renting.getBuyerIban());

        try {
            CarInterface.createRenting(code, plate, renting);
        } catch (CarException ce) {
            model.addAttribute("error", "Error: it was not possible to rent the vehicle");
            model.addAttribute("renting", renting);
            model.addAttribute("vehicle", CarInterface.getVehicleDataByPlate(code, plate));
            return "rentings";
        }

        return "redirect:/rentacars/" + code + "/vehicles/" + plate + "/rentings";
    }

    @RequestMapping(value = "/{reference}/checkout", method = RequestMethod.POST)
    public String rentingCheckout(Model model, @PathVariable String code, @PathVariable String plate,
                                  @PathVariable String reference, @RequestParam("kilometers") int kilometers) {

        logger.info("rentingCheckout rentACarCode:{}, vehiclePlate:{}, rentingReference{}",
                code, plate, reference);

        try {
            CarInterface.checkoutRenting(code, plate, reference, kilometers);
            logger.info("checkout sucessful");
        } catch (CarException ce) {
            model.addAttribute("error", "Error: it was not possible to checkout the renting");
            model.addAttribute("vehicle", CarInterface.getVehicleDataByPlate(code, plate));
            logger.info("checkout unsucessful");
            return "rentings";
        }

        return "redirect:/rentacars/" + code + "/vehicles/" + plate + "/rentings";
    }

}
