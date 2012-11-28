package com.rapidftr.activity.pages;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import com.jayway.android.robotium.solo.Solo;
import junit.framework.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegisterChildPage {

    public Solo solo;
    int formPosition ;
    List automationFormData = Arrays.asList(new String[]{"Automation TextField value", "Automation TextArea value", "Check 3", "Select 1", "Radio 3", "1", "20", "10", "2012"});

    public RegisterChildPage(Solo solo) {
        this.solo = solo;
    }

    public void navigateToRegisterPage() {
        solo.clickOnText("Register Child");
        solo.waitForText("Basic Identity");
    }

    public List<String> getDropDownFormSections() {

      solo.clickOnText("Basic Identity",0);
       solo.waitForText("Automation Form");
        ListAdapter adapter = solo.getCurrentListViews().get(0).getAdapter();
        int totalCount = adapter.getCount();
        ArrayList formSections = new ArrayList();
       for(int i=0;i<totalCount;i++){
           formSections.add(adapter.getItem(i).toString());
        }
//        solo.getCurrentListViews().get(0).getAdapter().getItem(2);
//        Spinner spinner= (Spinner)solo.getCurrentSpinners().get(0).getSelectedItem();
        return formSections;
    }


    public int getIndexFromElement(ArrayAdapter adapter, String element) {
        for(int i = 0; i < adapter.getCount(); i++) {
            if(adapter.getItem(i).equals(element)) {
                return i;
            }
        }
        return 0;
    }


    public void selectFormSection(String formSectionName) {
        solo.waitForText("Save");
        solo.clickOnView(solo.getCurrentSpinners().get(0));
        solo.waitForText(formSectionName);
        ListAdapter adapter= solo.getCurrentListViews().get(0).getAdapter();
        for(int i=0;i<adapter.getCount();i++){
            if(adapter.getItem(i).toString().equalsIgnoreCase(formSectionName)){
                 formPosition=i;
                break;
            }
        }
        solo.clickOnText(adapter.getItem(formPosition).toString());
        solo.waitForText(formSectionName);


    }

    public boolean verifyFields(List fieldNames) {
        boolean result = true;
        for(int i=0; i<fieldNames.size();i++){
            if(solo.searchText(fieldNames.get(i).toString(),true)==false){
                result=false;
                Log.e(fieldNames.get(i).toString()," Field Does not exists in the Form ");
                break;
            }
        }
        return result;
    }
    public void registerChild(){
        enterAutomationFormDetails(automationFormData);
        save();
    }

    public void save() {
        solo.clickOnButton("Save");
        Assert.assertTrue(solo.waitForText("Saved child record successfully"));
        solo.waitForText("Edit");

    }

    public void enterAutomationFormDetails(List automationFormData) {
        solo.enterText(0, automationFormData.get(0).toString());
        solo.enterText(1, automationFormData.get(1).toString());
        int checkBoxCount=solo.getCurrentCheckBoxes().size();
        for(int i=0;i<checkBoxCount;i++){
        if (solo.getCurrentCheckBoxes().get(i).getText().toString().equals(automationFormData.get(2).toString())){
            solo.clickOnCheckBox(i);
        }
        }
        int selectBoxCount=solo.getCurrentSpinners().get(1).getCount();

        for(int i=0;i<selectBoxCount;i++){
            if(solo.getCurrentSpinners().get(1).getAdapter().getItem(i).toString().equals(automationFormData.get(3).toString())){
                solo.pressSpinnerItem(1,i);
                solo.clickOnText(automationFormData.get(3).toString());
            }
        }

        solo.clickOnText(automationFormData.get(2).toString());
        solo.clickOnText(automationFormData.get(4).toString());
//        solo.enterText(2,automationFormData.get(5).toString());




    }

    public void verifyRegisterChildDetail(List automationFormData,String formName) {
        solo.searchButton("Edit", true);
        selectFormSection(formName);
        Assert.assertTrue(solo.searchEditText(automationFormData.get(0).toString()));
        Assert.assertTrue(solo.searchEditText(automationFormData.get(1).toString()));
//        Assert.assertTrue(solo.isCheckBoxChecked(automationFormData.get(2).toString()));
//        Assert.assertTrue(solo.isRadioButtonChecked(automationFormData.get(3).toString()));
//        solo.getCurrentNumberPickers().get(0) ;
//        solo.getCurrentDatePickers().get(0);

    }



    public void selectEditChild(){
        solo.waitForText("Edit");
        solo.clickOnText("Edit");

    }

    public void enterChildName(String name){
        solo.waitForText("Save");
        solo.enterText(3,name);
//        solo.enterText(1,"RCID123");
    }

    public boolean getChildName(String name){
        System.out.print(solo.getCurrentEditTexts().get(0).getText());
       return solo.getCurrentEditTexts().get(5).getText().toString().equals(name);

    }

}