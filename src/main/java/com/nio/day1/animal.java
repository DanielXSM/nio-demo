package com.nio.day1;

/**
 * @author daniel
 * @date 2018-12-25 21:34
 */
public class animal {
    public String noise(){
        return "namal";
    }


    public static void main(String[] args) {

        cat cat=null;
        animal animal=new cat();
        if(animal instanceof cat){
            cat=(cat)animal;
            System.out.println(cat.noise());
        }else{
            System.out.println("1111111111111");
        }


    }





}
 class dog extends animal{

     public String noise(){
         return "dog";
     }
 }


class cat extends animal{
    public String noise(){
        return "cat";
    }
}




