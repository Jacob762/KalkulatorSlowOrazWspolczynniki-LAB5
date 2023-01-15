package Zad1;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class Zadanie1 {
    public static void main(String[] args) {
        new MyFrame();
    }
}

class MyFrame extends JFrame {
    public static List<JTextField> aFields = Methods.init_Fields('a',3);
    public static List<JTextField> bFields = Methods.init_Fields('b',3);;
    public static List<JTextField> cFields = Methods.init_Fields('c',4);;
    public static JTextField sign = new JTextField();
    public JButton solve = new JButton("Rozwiaz");
    public JTextField result = new JTextField();
    MyFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200,800);
        addField(aFields,400,200);
        add(new JLabel("Wspolczynniki a")).setBounds(280,210,120,40);
        addField(bFields,400,270);
        add(new JLabel("Wspolczynniki b")).setBounds(280,280,120,40);
        addField(cFields,400,340);
        add(new JLabel("Wspolczynniki c")).setBounds(280,350,120,40);
        sign.setBounds(430,410,120,60);
        add(sign);
        add(new JLabel("Znak i przycisk")).setBounds(310,420,120,40);
        solve.setBounds(560,410,120,60);
        solve.addActionListener(e -> {
            try{
                Methods.calculateProducts(cFields);
            } catch (NumberFormatException ex){
                System.out.println("Wrong input");
            }
        });
        add(solve);
        add(new JLabel("Wynik")).setBounds(400,490,120,40);
        result.setBounds(450,480,200,60);
        result.setEditable(false);
        add(result);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        setVisible(true);
    }
    void addField(List<JTextField> list, int startLocx, int startLocy){
        int i=0;
        for(JTextField element : list){
            element.setBounds(startLocx+i,startLocy,90,60);
            add(element);
            i+=100;
        }
    }


}

class Methods{
    public static List<JTextField> init_Fields(char character, int size){
        List<JTextField> list = new ArrayList<>();
        for(int i=0;i<size;i++){
            list.add(new JTextField(character + String.valueOf(i+1)));
        }
        return list;
    }
    public static int calculateFactor(){
       int a = load(MyFrame.aFields);
       int b = load(MyFrame.bFields);
       int result;
       switch (MyFrame.sign.getText()){
           case "+":
               result=a+b;
               System.out.println(result);
               break;
           case "-":
               result=a-b;
               System.out.println(result);
               break;
           case "*":
               result=a*b;
               System.out.println(result);
               break;
           default:
               throw new NumberFormatException();
       }
       return result;
    }
    public static void calculateProducts(List<JTextField> list){
        List<Integer> products = new ArrayList<>();
        List<Boolean> isNumber = new ArrayList<>();
        List<String> notNumbers = new ArrayList<>();
        List<Integer> finalAns = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            try {
                products.add((int) (Integer.parseInt(list.get(i).getText())*Math.pow(10,3-i)));
                isNumber.add(true);
                notNumbers.add("");
                System.out.println(products.get(i));
            } catch (NumberFormatException e){
                products.add(0);
                isNumber.add(false);
                notNumbers.add(list.get(i).getText());
                System.out.println(notNumbers.get(i));
            }
        }
        int factor = calculateFactor();
        for(int i=0;i<products.size();i++){
            if(isNumber.get(i)) factor-=products.get(i);
            else{

            }
        }
        System.out.println(factor);
    }
    public static int load(List<JTextField> list){
        int factor=0;
        for(int i=0;i<list.size();i++){
            factor+=Integer.parseInt(list.get(i).getText())*Math.pow(10,2-i);
        }
        return factor;
    }
    }