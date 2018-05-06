package pt.ulisboa.tecnico.softeng.activity.presentation;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.activity.exception.ActivityException;
import pt.ulisboa.tecnico.softeng.activity.services.local.ActivityInterface;
import pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityOfferData;
import pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityProviderData;
import pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects.ActivityReservationData;

import org.joda.time.LocalDate;

@Controller
@RequestMapping(value = "/providers/{codeProvider}/activities/{codeActivity}/offers/{begin}/{end}/reservations")
public class ActivityReservationController {
    private static Logger logger = LoggerFactory.getLogger(ActivityReservationController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String reservationForm(Model model, @PathVariable String codeProvider, @PathVariable String codeActivity, @PathVariable String begin, @PathVariable String end) {
        logger.info("reservationForm codeProvider:{}, codeActivity:{}, begin:{}, end:{}", codeProvider, codeActivity, begin, end);

        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
        LocalDate beg = dtf.parseLocalDate(begin);
        LocalDate en = dtf.parseLocalDate(end);

        ActivityOfferData offerData = ActivityInterface.getOfferDataByBeginEndDate(codeProvider, codeActivity, beg, en);

        if (offerData == null) {
            model.addAttribute("error", "Error: it does not exist an offer with begin date " + beg.toString() + " and end date " + en.toString()
                    + " in activity with code " + codeActivity + " in provider with code " + codeProvider);
            model.addAttribute("provider", new ActivityProviderData());
            model.addAttribute("providers", ActivityInterface.getProviders());
            return "providers";
        } else {
            model.addAttribute("reservation", new ActivityReservationData());
            model.addAttribute("offer", offerData);
            return "reservations";
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public String reservationSubmit(Model model, @PathVariable String codeProvider, @PathVariable String codeActivity, @PathVariable String begin, @PathVariable String end, @ModelAttribute ActivityReservationData reservation) {
        logger.info("reservationSubmit codeProvider:{}, codeActivity:{}, offerBegin:{}, offerEnd:{}, nif:{}, iban:{}",
                codeProvider, codeActivity, begin, end, reservation.getBuyerNif(), reservation.getBuyerIban());

        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
        LocalDate beg = dtf.parseLocalDate(begin);
        LocalDate en = dtf.parseLocalDate(end);

        try {
            ActivityInterface.createReservation(codeProvider, codeActivity, beg, en, reservation);
        } catch (ActivityException e) {
            model.addAttribute("error", "Error: it was not possible to create the reservation");
            model.addAttribute("reservation", reservation);
            model.addAttribute("offer", ActivityInterface.getOfferDataByBeginEndDate(codeProvider, codeActivity, beg, en));
            return "reservations";
        }

        return "redirect:/providers/" + codeProvider + "/activities/" + codeActivity + "/offers/" + beg.toString() + "/" + en.toString() + "/reservations";
    }

}
