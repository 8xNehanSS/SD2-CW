package com.example.javfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private Label c1r1,c1r2,c2r1,c2r2,c2r3,c3r1,c3r2,c3r3,c3r4,c3r5;
    @FXML
    private Circle C1,C2,C3,C4,C5,C6,C7,C8,C9,C10;
    @FXML
    private Label search1,search2,search3,search4;
    @FXML
    private TextField textboxname;
    @FXML
    public void onUpdateButtonClick() {
        Customer[] CashierONE = HelloApplication.Queue[0].getQueue();
        Customer[] CashierTWO = HelloApplication.Queue[1].getQueue();
        Customer[] CashierTHREE = HelloApplication.Queue[2].getQueue();
        Customer[][] AllCashiers = {CashierONE,CashierTWO,CashierTHREE};

        Label[] LabelsC1 = {c1r1,c1r2};
        Label[] LabelsC2 = {c2r1,c2r2,c2r3};
        Label[] LabelsC3 = {c3r1,c3r2,c3r3,c3r4,c3r5};
        Label[][] Labels = {LabelsC1, LabelsC2, LabelsC3};

        Circle[] Circles1 = {C1,C2};
        Circle[] Circles2 = {C3,C4,C5};
        Circle[] Circles3 = {C6,C7,C8,C9,C10};
        Circle[][] Circles = {Circles1,Circles2,Circles3};

        int j = 0;
        for(Customer[] Cashier:AllCashiers){
            int i=0;
            for(Customer element:Cashier){
                if(element!=null){
                    Labels[j][i].setText("0");
                    Circles[j][i].setFill(Color.RED);
                } else {
                    Labels[j][i].setText("X");
                    Circles[j][i].setFill(Color.web("#51ea51"));
                }
                i++;
            }
            j++;
        }
    }

    @FXML
    protected void onSearchButtonClick() {
        String text = textboxname.getText().toLowerCase().trim().replaceAll("\\s+", "");
        Customer[] CashierONE = HelloApplication.Queue[0].getQueue();
        Customer[] CashierTWO = HelloApplication.Queue[1].getQueue();
        Customer[] CashierTHREE = HelloApplication.Queue[2].getQueue();
        Customer[] WaitingLIST = HelloApplication.WaitingLIST.getQueue();
        Customer[][] AllCashiers = {CashierONE,CashierTWO,CashierTHREE,WaitingLIST};

        String[] customers = new String[20];
        int count = 0;
        for(Customer[] Cashier:AllCashiers) {
            for(Customer element:Cashier) {
                if(element!=null){ customers[count] = element.getName().trim(); }
                else { customers[count] = "-"; }
                count++;
            }
        }

        Label[] SearchLabels = {search1,search2,search3,search4};

        for(int i=0;i<4;i++) {
            SearchLabels[i].setText(i+1+".");
        }

        int j =0;
        for(int i=0;i<20;i++){
            if(customers[i].toLowerCase().replaceAll("\\s+", "").contains(text)){
                String Place = "unknown";
                if(i<2){
                    Place = "Cashier One";
                }
                if(1<i && i<5){
                    Place = "Cashier Two";
                }
                if(4<i && i<10){
                    Place = "Cashier Three";
                }
                if(i>9){
                    Place = "Waiting list";
                }
                if(j==4) {
                    break;
                }
                SearchLabels[j].setText(j+1+". "+customers[i]+" in "+Place);
                j++;
            }
        }
    }
}