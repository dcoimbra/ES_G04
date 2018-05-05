package pt.ulisboa.tecnico.softeng.activity.presentation;

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
@RequestMapping(value = "/providers/{codeProvider}/activities/{codeActivity}/offers/{begin}/reservations")
public class ActivityReservationController {
    private static Logger logger = LoggerFactory.getLogger(ActivityReservationController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String reservationForm(Model model, @PathVariable String codeProvider, @PathVariable String codeActivity, @PathVariable LocalDate begin) {
        logger.info("reservationForm codeProvider:{}, codeActivity:{}, begin:{}", codeProvider, codeActivity, begin);

        ActivityOfferData offerData = ActivityInterface.getOfferDataByBeginEndDate(codeProvider, codeActivity, begin);

        if (offerData == null) {
            model.addAttribute("error", "Error: it does not exist an offer with begin date " + begin + " and end date "
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
    public String reservationSubmit(Model model, @PathVariable String codeProvider, @PathVariable String codeActivity, @PathVariable LocalDate begin, @ModelAttribute ActivityReservationData reservation) {
        logger.info("reservationSubmit codeProvider:{}, codeActivity:{}, offerBegin:{}, reference:{}, paymentReference:{}, invoiceReference:{}",
                codeProvider, codeActivity, begin, reservation.getReference(), reservation.getPaymentReference(), reservation.getInvoiceReference());

        try {
            ActivityInterface.createReservation(codeProvider, codeActivity, begin, reservation);
        } catch (ActivityException e) {
            model.addAttribute("error", "Error: it was not possible to create the reservation");
            model.addAttribute("reservation", reservation);
            model.addAttribute("offer", ActivityInterface.getOfferDataByBeginEndDate(codeProvider, codeActivity, begin));
            return "reservations";
        }

        return "redirect:/providers/" + codeProvider + "/activities/" + codeActivity + "/offers/" + "" + begin.toString() + "" + "/reservations";
    }

}
