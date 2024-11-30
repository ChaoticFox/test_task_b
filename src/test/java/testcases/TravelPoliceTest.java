package testcases;

import base.BaseTest;
import base.TranslationHelper;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import data.InsuredPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pageobjects.MainPage;
import pageobjects.TravelPolice;

import java.util.*;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TravelPoliceTest extends BaseTest {
    //private TranslationHelper translationHelper;

    @BeforeEach
    public void setup() {
        //super.setUp();
    }

    @Test
    public void travelPolicePositive(){
        //Pārvietoties uz ceļojuma polišu iegādes formu
        MainPage mainPage = new MainPage();
        mainPage.clickMenuOption(translationHelper.getTranslation("police.option.travel"));
        assertTrue(isPageOpened("https://www.bta.lv/privatpersonam/celojuma-apdrosinasana"));
        TravelPolice travelPolice = new TravelPolice(translationHelper);
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
