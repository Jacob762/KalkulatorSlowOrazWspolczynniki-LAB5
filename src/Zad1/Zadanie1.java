package Zad1;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
    boolean solveClicked = false;
    MyFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200,800);
        addField(aFields,400,200);
        add(new JLabel("Wspolczynniki a")).setBounds(280,210,120,40);
        addField(bFields,400,270);
        add(new JLabel("Wspolczynniki b")).setBounds(280,280,120,40);
        addField(cFields,400,340);
        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {operate();}
            @Override
            public void removeUpdate(DocumentEvent e) {operate();}
            @Override
            public void changedUpdate(DocumentEvent e) {operate();}
        };
        for(int i=0;i<aFields.size();i++){
            aFields.get(i).getDocument().addDocumentListener(documentListener);
            bFields.get(i).getDocument().addDocumentListener(documentListener);
        }
        for(JTextField element : cFields) element.setEditable(false);
        add(new JLabel("Wspolczynniki c")).setBounds(280,350,120,40);
        sign.setBounds(430,410,120,60);
        sign.getDocument().addDocumentListener(documentListener);
        add(sign);
        add(new JLabel("Znak i przycisk")).setBounds(310,420,120,40);
        solve.setBounds(560,410,120,60);
        solve.addActionListener(e -> {
            try{
                Methods.calculateProducts(cFields);
                solveClicked = true;
            } catch (NumberFormatException ex){
                System.out.println("Wrong input");
            }
        });
        add(solve);
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
    public void operate(){
        if(solveClicked){
            try{
                Methods.calculateProducts(cFields);
            } catch (NumberFormatException ex){
                System.out.println("Wrong input");
            }
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
        int factor = calculateFactor();
        Integer[] results = new Integer[4];
        for(int i=3;i>0;i--){
            results[i] = factor% (int) Math.pow(10,4-i); //dzielimy wynik na liczbe jednosci, dziesiatek i setek
            factor-=results[i];
        }
        results[0] = factor/1000; // wynik po odjeciu setek, dziesiatek i jednosci podzielony przez 1000 da nam liczbe tysiecy
        for(int i=1;i<4;i++){
            results[i] =  results[i]/ (int) Math.pow(10,3-i); // wyniki po kolei dzielimy przez liczbe towarzyszaca im w rownaniu
            list.get(i).setText(String.valueOf(results[i]));
        }
        list.get(0).setText(String.valueOf(results[0]));
    }
    public static int load(List<JTextField> list){
        int factor=0;
        for(int i=0;i<list.size();i++){
            factor+=Integer.parseInt(list.get(i).getText())*Math.pow(10,2-i);
        }
        return factor;
    }
    }