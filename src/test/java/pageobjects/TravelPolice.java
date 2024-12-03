package pageobjects;

import base.BasePage;
import base.TranslationHelper;
import data.InsuredPerson;
import java.util.List;
import java.util.Map;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TravelPolice extends BasePage {
    public TravelPolice(TranslationHelper translationHelper) {
        super(translationHelper);
    }

    public void clickMenuOption(String itemText) {
        $(".quick-navigation").should(visible).$(byText(itemText)).click();
        $(".page-one-travel").should(exist);
    }

    public void setCustomDestination(String destination) {
        $("#regionalSelectorRegion-open").click();
        $("#regionalSelectorCountry-showListSearch").shouldBe(visible).click();
        $(".add-text").shouldBe(visible).click();
        $("#regionalSelectorCountry-typedValue").shouldBe(visible).setValue(destination);
        $("[data-value=\"" + destination + "\"]").shouldBe(visible).click();
        $("#regionalSelectorCountry-removeCountry-0").shouldBe(visible);
        $("#regionalSelectorCountry-applyButton").click();
        $(".popups").shouldNotBe(visible);
    }

    public void destinationCheck(String destination) {
        $("#regionalSelectorRegion-open .text").shouldHave(text(destination));
    }

    public void setActivity(String activity) {
        $("#travelActivities-open").scrollTo().shouldBe(visible).click();
        $(byText(activity)).shouldBe(visible).click();
    }

    public void checkActivity(String activity) {
        $("#travelActivities-open .text").shouldHave(text(activity));
    }

    public void goToStep2() {
        $("[data-type=\"travelSubmit\"]").scrollTo().click();
        currentStepCorrect(translationHelper.getTranslation("police.step2"));
    }

    public void currentStepCorrect(String step) {
        $(".application-step.current").shouldHave(text(step));
    }

    public String selectPlanSavePrice(String plan) {
        String price = $("[data-type=\"policyItemPrice" + plan + "\"]").getText();
        $("[datatype=\"selectPolicyPlan" + plan + "\"]").scrollTo().shouldBe(visible);
        runJS("arguments[0].click();", $("[datatype=\"selectPolicyPlan" + plan + "\"]"));
        currentStepCorrect(translationHelper.getTranslation("police.step3"));
        return price;
    }

    public void extraOptionsDisplayed(Map<String, String> expectedPlans) {
        //logic to check if all selected insurance plans applied
        $("#insurance-plan-widget").shouldBe(visible);
        // Iterate through expected plans and check their presence and counts
        for (Map.Entry<String, String> entry : expectedPlans.entrySet()) {
            String planName = entry.getKey();
            String planPrice = entry.getValue();
            // Check that each plan name and price are displayed exactly once
            int planNameCount = $$(".item-name").filter(text(planName)).size();
            int planPriceCount = $$(".item-value").filter(text(planPrice)).size();
            if (planNameCount!=1) {
                throw new AssertionError("Plan name '" + planName + "' is displayed " + planNameCount + " times.");
            }
            if (planPriceCount!=1) {
                throw new AssertionError("Plan price '" + planPrice + "' is displayed " + planPriceCount + " times.");
            }
        } // Additional check to ensure no extra elements are present
        int totalPlanNamesCount = $$(".item-name").size();
        int totalPlanPricesCount = $$(".item-value").size();

        if (totalPlanNamesCount!=expectedPlans.size() || totalPlanPricesCount!=expectedPlans.size()) {
            throw new AssertionError("Number of displayed plans does not match the expected count. Expected: "
                    + expectedPlans.size() + ", but got names: " + totalPlanNamesCount
                    + " and prices: " + totalPlanPricesCount);
        }

    }

    public void editRentalCarInsurance() {
        $(".edit-details").click();
        $(".rental-car-security").shouldBe(visible);
    }

    public void setInsuranceSum(String sum) {
        $("#deductible-open").click();
        $$(".dropdown .item").findBy(text(sum)).click();
        assertEquals($("#deductible-open .text").getText(), sum);
    }

    public String saveRentalCarInsurance(String sum) {
        $(".confirm-details").click();
        $(".rental-car-security").shouldNotBe(visible);
        $$(".option-detail").findBy(text(translationHelper.getTranslation("police.car.deductible") + " " + sum)).shouldBe(visible);
        return convertAmountToPrice($(".amount").getText());
    }

    public String convertAmountToPrice(String amount) {
        return amount.replace("+", "").trim() + " €";
    }

    public void goToStep4() {
        $(".discount-info ~ button").click();
        currentStepCorrect(translationHelper.getTranslation("police.step4"));
    }

    public void travellerFieldsInitialState(List<InsuredPerson> travellers) {
        for (int i = 0; i < travellers.size(); i++) {
            if (travellers.get(i).getIsResident()) {
                $("#travelerFirstName0").shouldBe(visible).shouldHave(attribute("data-store-value", ""));
                $("#travelerLastName0").shouldBe(visible).shouldHave(attribute("data-store-value", ""));
                $("#travelerIdentityNumber0").shouldBe(visible).shouldHave(attribute("data-store-value", ""));
            } else {
                //check for more fields, out of scope
            }
        }
    }


}
