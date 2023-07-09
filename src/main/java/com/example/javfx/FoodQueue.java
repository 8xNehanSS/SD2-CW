package com.example.javfx;

public class FoodQueue {
    private Customer[] queue;
    private int size;

    public FoodQueue (int size) {
        this.size = size;
        this.queue = new Customer[size];
    }

    public void setCustomer (Customer customer) {
        for(int i=0; i<queue.length; i++) {
            if(queue[i]==null){
                queue[i] = customer;
                return;
            }
        }
    }

    public void RemoveCustomer (int Cashier, int CashierSlot) {
        int Slot = 0;
        if(Cashier==1) {Slot = 2;}
        if(Cashier==2) {Slot = 3;}
        if(Cashier==3) {Slot = 5;}
        if(Slot==0) {
            System.out.println("Invalid Cashier");
            return;
        }
        if(CashierSlot<=Slot && CashierSlot>0){
            if(this.queue[CashierSlot-1]!=null){
                String Name = this.queue[CashierSlot-1].getName();
                this.queue[CashierSlot-1] = null;
                System.out.println("Customer removed from slot - "+Name);

                for (int i =0; i < (this.queue.length - 1); i++) {
                    if (this.queue[i]==null) {
                        Customer temp = this.queue[i + 1];
                        this.queue[i + 1] = this.queue[i];
                        this.queue[i] = temp;
                    }
                }

            } else {
                System.out.println("Slot is already empty");
            }
        } else {
            System.out.println("Invalid Slot number");
        }
    }

    public int ServeCustomer (int BurgerStock) {
        if(this.queue[0]!=null){
            int Burgers = this.queue[0].getRequiredBurgers();
            if(BurgerStock<Burgers) {
                System.out.println("Not enough burgers to serve this customer");
                return 0;
            }
            System.out.println(this.queue[0].getName()+" Customer served with "+this.queue[0].getRequiredBurgers()+" Burgers");
            this.queue[0] = null;
            for (int i =0; i < (this.queue.length - 1); i++) {
                if (this.queue[i]==null) {
                    Customer temp = this.queue[i + 1];
                    this.queue[i + 1] = this.queue[i];
                    this.queue[i] = temp;
                }
            }
            return Burgers;
        } else {
            System.out.println("No customers in this cashier");
            return 0;
        }
    }

    public Customer[] getQueue() {
        return queue;
    }

}


