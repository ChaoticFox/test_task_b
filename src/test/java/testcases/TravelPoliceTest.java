package testcases;

import base.BaseTest;
import data.InsuredPerson;
import org.junit.jupiter.api.Test;
import pageobjects.TravelPolice;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TravelPoliceTest extends BaseTest {

    @Test
    public void travelPolicePositive() {
        //Pārvietoties uz ceļojuma polišu iegādes formu
        TravelPolice travelPolice = new TravelPolice(translationHelper);
        travelPolice.clickMenuOption(translationHelper.getTranslation("police.option.travel"));
        List<InsuredPerson> insuredPeople = new ArrayList<>();
        insuredPeople.add(new InsuredPerson(true));
        //Nomainīt ceļojuma galamērķi uz Indija izmantojot “Izvēlies valstis” -> “Pievienot valsti” -> “Meklēt” norādi “Indiju” un nospied “Apstiprināt”
        travelPolice.setCustomDestination(translationHelper.getTranslation("countries.India"));
        //Pārbaudīt vai ceļojuma galamērķis ir nomainīts uz “Visa pasaule”
        travelPolice.destinationCheck(translationHelper.getTranslation("travel.destination.world"));
        //Nomainīt aktivitātes uz “Ar paaugstināta riska aktivitātēm”
        travelPolice.setActivity(translationHelper.getTranslation("police.activity.risky"));
        //Pārbaudīt vai aktivitāte nomainīta uz “Ar paaugstināta riska aktivitātēm”
        travelPolice.checkActivity(translationHelper.getTranslation("police.activity.risky"));
        //Pārvietoties uz otro soli nospiežot uz “Saņemt piedāvājumu”
        travelPolice.goToStep2();
        //Izvēlēties “Optimālā” -> “Turpināt”
        String policePrice = travelPolice.selectPlanSavePrice("OPTIMAL");
        Map<String, String> expectedPlans = new HashMap<>();
        expectedPlans.put(translationHelper.getTranslation("police.planOptimal"), policePrice);
        //Pārbaudi vai ir redzama forma “Vēlies pievienot papildu aizsardzību?” un lauki ”Mana polise” ar norādītām vērtībām
        travelPolice.extraOptionsDisplayed(expectedPlans);
        //Nospiest “Labot datus” -> noradīt citu “Apdrošinājuma summa” -> “Apstiprināt”
        //Pārliecināties, ka ievadītā “Apdrošinājuma summa” ir pamainījusies
        travelPolice.editRentalCarInsurance();
        String sum = "2000 €";
        travelPolice.setInsuranceSum(sum);
        String priceRentalCar = travelPolice.saveRentalCarInsurance(sum);
        expectedPlans.put(translationHelper.getTranslation("police.planRentalCar"), priceRentalCar);
        //Pārvietoties uz nākamo soli -> “Turpināt”
        travelPolice.goToStep4();
        //Pārbaudīt vai ceļotāja lauki ir redzami un tie ir tukši
        travelPolice.travellerFieldsInitialState(insuredPeople);
    }
}
