package pageobjects;

import base.BasePage;
import base.TranslationHelper;
import data.InsuredPerson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TravelPolice extends BasePage {
    public TravelPolice(TranslationHelper translationHelper){
        super(translationHelper);
    }
    public void setCustomDestination(String destination){
        assertTrue($(".country-button-add").isDisplayed());
        $(".country-button-add").shouldBe(visible).click();
        $("#regionalSelectorCountry-typedValue").shouldBe(visible).setValue(destination);
        $("[data-value=\""+destination+"\"]").shouldBe(visible).click();
        $("#regionalSelectorCountry-removeCountry-0").shouldBe(visible);
        $("#regionalSelectorCountry-applyButton").click();
        $(".popups").shouldNotBe(visible);
    }

    public void destinationCheck(String destination){
        $("#regionalSelectorRegion-open .text").shouldHave(text(destination));
    }

    public void setActivity(String activity){
        $("#travelActivities-open").shouldBe(visible).click();
        $(byText(activity)).shouldBe(visible).click();
    }

    public void checkActivity(String activity){
        $("#travelActivities-open .text").shouldHave(text(activity));
    }

    public void goToStep2(){
        $("[data-type=\"travelSubmit\"]").click();
        currentStepCorrect(translationHelper.getTranslation("police.step2"));
    }

    public void currentStepCorrect(String step){
        $(".application-step.current").shouldHave(text(step));
    }

    public String selectPlanSavePrice(String plan){
        String price = $("[data-type=\"policyItemPlan"+plan+"\"]").getText();
        $("[data-type=\"selectPolicyPlan"+plan+"\"]").click();
        currentStepCorrect(translationHelper.getTranslation("police.step3"));
        return price;
    }

    public void extraOptionsDisplayed(Map<String, String> expectedPlans){
        //logic to check if all selected insurance plans applied
        $("#form-travel-additional").shouldBe(visible);
        Map<String, String> actualPlans = new HashMap<>();
        for (Map.Entry<String, String> entry : expectedPlans.entrySet()) {
            String planName = entry.getKey();
            String planPrice = entry.getValue();
            boolean planNameDisplayed= $$(".item-name").findBy(text(planName)).isDisplayed();
            boolean planPriceDisplayed= $$(".item-value").findBy(text(planPrice)).isDisplayed();
            if (planNameDisplayed && planPriceDisplayed) {
                actualPlans.put(planName, planPrice);
            }
        }
        if (!actualPlans.equals(expectedPlans)) {
            throw new AssertionError("Displayed plans do not match the expected plans.");
        }

    }

    public void editRentalCarInsurance(){
        $(".edit-details").click();
        $(".rental-car-security").shouldBe(visible);
    }

    public void setInsuranceSum(String sum){
        $("#deductible-open").click();
        $$(".dropdown .item").findBy(text(sum)).click();
        assertEquals($("#deductible-open .text").getText(), sum);
    }

    public String saveRentalCarInsurance(String sum){
        $(".confirm-details").click();
        $(".rental-car-security").shouldNotBe(visible);
        $$(".option-detail").findBy(text(translationHelper.getTranslation("police.car.ownrisk")+sum)).shouldBe(visible);
        return convertAmountToPrice($(".amount").getText());
    }

    public String convertAmountToPrice(String amount){
        return amount.replace("+", "").trim()+" €";
    }

    public void goToStep4(){
        $(".discount-info ~ button").click();
        currentStepCorrect(translationHelper.getTranslation("police.step4"));
    }

    public void travellerFieldsInitialState(List<InsuredPerson> travellers){
        for(int i=0; i<travellers.size(); i++){
            if(travellers.get(i).getIsResident()){
                $("travelerFirstName0").shouldBe(visible).shouldHave(attribute("data-store-value",""));
                $("travelerLastName0").shouldBe(visible).shouldHave(attribute("data-store-value",""));
                $("travelerIdentityNumber0").shouldBe(visible).shouldHave(attribute("data-store-value",""));
            }else{
                //check for more fields, out of scope
            }
        }
    }


}
