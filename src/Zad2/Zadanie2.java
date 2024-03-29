package Zad2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Zadanie2 {
    public static void main(String[] args) {
        new MyFrame();
    }}

class MyFrame extends JFrame implements MouseListener,ActionListener {
    JPanel panel = new JPanel();
    private JButton abc = new JButton("abc");
    private JButton def = new JButton("def");
    private JButton ghi = new JButton("ghi");
    private JButton jkl = new JButton("jkl");
    private JButton mno = new JButton("mno");
    private JButton pqr = new JButton("pqr");
    private JButton stuv = new JButton("stuv");
    private JButton wxyz = new JButton("wxyz");
    private JButton dmSwitch = new JButton("D/M");
    private JButton clearSign = new JButton("CE");
    private JButton clear = new JButton("C");
    private JButton dodaj = new JButton("+");
    private JButton usun = new JButton("-");
    private JButton podziel = new JButton("/");
    private JButton resultButton = new JButton("=");
    private JTextField result = new JTextField();
    String resultFieldText = "";
    private boolean DMactivated = false;
    private int operators = 0;
    private boolean nextResult = false;
    MyFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,850);
        panel.setBounds(45,120,500,600);
        abc.addMouseListener(this);
        def.addMouseListener(this);
        ghi.addMouseListener(this);
        jkl.addMouseListener(this);
        pqr.addMouseListener(this);
        stuv.addMouseListener(this);
        wxyz.addMouseListener(this);
        mno.addMouseListener(this);
        clear.addActionListener(e -> {
            resultFieldText = "";
            result.setText(resultFieldText);
        });
        clearSign.addActionListener(e -> {
            try {
                resultFieldText = resultFieldText.substring(0,resultFieldText.length()-1);;
            } catch (StringIndexOutOfBoundsException ex){
                System.out.println("Blad");
            }
            result.setText(resultFieldText);
        });
        dmSwitch.addActionListener(e -> {
            if(DMactivated) DMactivated = false;
            else DMactivated = true;
            System.out.println(DMactivated + "DUZE?");
        });
        resultButton.addActionListener(e -> {
            try{
                resultFieldText += "=" + Methods.calculations(resultFieldText,operators);
                operators=0;
            } catch (Exception exception){
                System.out.println("Blad rezultatu");
            }
            result.setText(resultFieldText);
            nextResult = true;
        });
        panel.add(abc);
        panel.add(def);
        panel.add(ghi);
        panel.add(jkl);
        panel.add(dmSwitch);
        panel.add(pqr);
        panel.add(stuv);
        panel.add(wxyz);
        panel.add(mno);
        panel.add(clearSign);
        panel.add(clear);
        panel.add(dodaj);
        dodaj.addActionListener(this);
        panel.add(usun);
        usun.addActionListener(this);
        panel.add(podziel);
        podziel.addActionListener(this);
        panel.setLayout(new GridLayout(3,5));
        panel.add(resultButton);
        add(panel);
        result.setBounds(90,10,420,80);
        result.setEditable(false);
        result.setFont(new Font("Serif", Font.PLAIN, 18));
        add(result);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) { // kod przy mouseClicked nie dzialal poprawnie, przycisk nacisniety powodowal reakcje w kolejnym nacisnietym
        if(nextResult){                      // co skutkowalo niepoprawnym dodawaniu znakow. mousePressed dziala przy zwyklym przycisnieciu przycisku myszki
            resultFieldText = "";            // dzieki czemu "klikniecia" nie przechodza do kolejnych przyciskow
            nextResult = false;
        }
        JButton button = (JButton) e.getSource();
        String buttonText = button.getText();
        if(e.getButton()==MouseEvent.BUTTON1){ //zabezpieczenie przed klikaniem prawym przyciskiem
            int clickCounter = e.getClickCount()-1;
            System.out.println(clickCounter);
            switch (clickCounter%buttonText.length()){
                case (0):
                    if(clickCounter>2){
                        StringBuilder stringBuilder;
                        stringBuilder = new StringBuilder(resultFieldText);
                        stringBuilder.deleteCharAt(resultFieldText.length()-1);
                        resultFieldText = stringBuilder.toString();
                    }
                    if(DMactivated){
                        resultFieldText+=buttonText.toUpperCase().charAt(0);
                    }else{
                        resultFieldText+=buttonText.charAt(0);
                    }
                    break;
                case (1):
                    stringBuilder(1,buttonText);
                    break;
                case (2):
                    stringBuilder(2,buttonText);
                    break;
                case (3):
                    stringBuilder(3,buttonText);
                    break;
                default:
                    resultFieldText+=buttonText.charAt(0);
            }
        }
        result.setText(resultFieldText);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(nextResult){
            resultFieldText = "";
            nextResult = false;
        }
        if(resultFieldText.length()!=0){
            if(e.getSource().equals(dodaj)){
                resultFieldText += "+";
            } else if(e.getSource().equals(usun)){
                resultFieldText += "-";
            }  else if(e.getSource().equals(podziel)){
                resultFieldText += '/';
            }
            operators++;
            result.setText(resultFieldText);
        } else System.out.println("Blad znaku");
    }
    void stringBuilder(int charAt, String text){
        if(DMactivated){
            resultFieldText+=text.toUpperCase().charAt(charAt);
        }
        else {
            resultFieldText+=text.charAt(charAt);
        }
        StringBuilder sb1 = new StringBuilder(resultFieldText);
        sb1.deleteCharAt(resultFieldText.length()-2);
        resultFieldText= sb1.toString();
    }
}

class Methods{
    public static String calculations(String resultString, int operators){
        String finalResult;
        ArrayList<Character> signs = new ArrayList<>();
        ArrayList<String> list = new ArrayList<>();
        int k=operators;
        int i=0;
        while(k!=0){
            if(resultString.charAt(i)=='+'){
                list.add(resultString.substring(0,i));
                resultString = resultString.substring(i+1);
                k--;
                signs.add('+');
                i=0;
            } else if (resultString.charAt(i)=='-'){
                list.add(resultString.substring(0,i));
                resultString = resultString.substring(i+1);
                k--;
                signs.add('-');
                i=0;
            } else if (resultString.charAt(i)=='/'){
                list.add(resultString.substring(0,i));
                resultString = resultString.substring(i+1);
                k--;
                signs.add('/');
                i=0;
            }
            i++;
        }
        list.add(resultString);
        for(Character element: signs){
            if(element=='+'){
                finalResult = list.get(0)+list.get(1);
                list.remove(1);
                list.set(0,finalResult);
            } else if (element=='-'){
                finalResult = list.get(0).replace(list.get(1),"" );
                list.remove(1);
                list.set(0,finalResult);
            } else if (element=='/'){
                finalResult =podziel(list.get(0), list.get(1));
                list.remove(1);
                list.set(0,finalResult);
            }
        }
        return list.get(0);
    }
    public static String podziel(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        int[][] Table = new int[m + 1][n + 1];
        int maxLength = 0;
        int maxRow = 0;
        // Tabela do porownywania liter stringow
        // Kazdy rowny znak uzyskuje wynik LiczbaPoPrzekatnejPoLewej + 1 dzieki czemu znajdujemy dlugosci najdluzszych wspolnych ciagow znakow (LCS problem)
        // Jednoczesnie zapisujemy numer wiersza ktory reprezentuje najdluzszy znaleziony ciag
        for (int i = 0; i < m; i++) {
            Table[i][0] = 0;
        }
        for (int j = 0; j < n; j++) {
            Table[0][j] = 0;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    Table[i][j] = 1 + Table[i - 1][j - 1];
                    if (maxLength < Table[i][j]) {
                        maxLength = Table[i][j];
                        maxRow = i;
                    }
                } else {
                    Table[i][j] = 0;
                }
            }
        }
        StringBuilder LCS = new StringBuilder(maxLength); //Longest Common String
        while (maxLength > 0) {
            LCS.append(word1.charAt(maxRow - 1));
            maxRow--;
            maxLength--;
        }
        return LCS.reverse().toString();
    }
}