package pt.ulisboa.tecnico.softeng.car.services.remote;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pt.ulisboa.tecnico.softeng.car.domain.Vehicle;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import pt.ulisboa.tecnico.softeng.car.services.local.CarInterface;
import pt.ulisboa.tecnico.softeng.car.services.local.dataobjects.RentingData;

@RestController
@RequestMapping(value = "/rest/car")
public class CarRestController {
    private static Logger logger = LoggerFactory.getLogger(CarRestController.class);


    @RequestMapping(value = "/rent", method = RequestMethod.POST)
    public ResponseEntity<String> rentCar(@RequestParam CarInterface.Type type,
                                          @RequestParam String drivingLicense,
                                          @RequestParam String nif, @RequestParam String iban,
                                          @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                          @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
                                          ) {
        logger.info("rent type:{}, drivingLicense:{}, nif:{}, iban:{}, begin:{}, end:{}", type, drivingLicense, nif, iban, begin, end);
        try {
            return new ResponseEntity<>(CarInterface.rentCar(type, drivingLicense, nif, iban, begin, end), HttpStatus.OK);
        } catch (CarException be) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public ResponseEntity<String> cancel(@RequestParam String reference) {
        logger.info("cancel reference:{}", reference);
        try {
            return new ResponseEntity<>(CarInterface.cancelRenting(reference), HttpStatus.OK);
        } catch (CarException be) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/operation", method = RequestMethod.GET)
    public ResponseEntity<RentingData> renting(@RequestParam String reference) {
        logger.info("renting reference:{}", reference);
        try {
            return new ResponseEntity<>(CarInterface.getRentingData(reference), HttpStatus.OK);
        } catch (CarException be) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}