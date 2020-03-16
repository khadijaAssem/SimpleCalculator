package eg.edu.alexu.csd.oop.calculator.cs27;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calc implements Calculator {

    private ArrayList<String> history = new ArrayList();
    Calc(){
        for (int i=0;i<5;i++){
            history.add(null);
        }
    }
    private String operation = "";//Maim Operation
    private String ans = "";//Answer
    private int Count = 0;//Elements in history
    private int current = -1;
    private boolean first = false;
    private int Counter =0;

    @Override
    public void input(String s) {
        Counter++;
        if (Counter==6)
            Counter=1;
        operation="";
        operation = operation.concat(s);
        first = true;
        getResult();
        operation=operation.concat(s);
    }

    @Override
    public String getResult() {
        try {
            Double result;
            ArrayList<Double> temp = new ArrayList<Double>();
            List<String> op = split();
            if (operation.equals("")) {
                return ans;
            }
            if (op.size()!=3) {
                operation="";
                Counter--;
                return "Invalid";
            }
            try {
                for (int i = 0; i < op.size(); i += 2) {
                    temp.add(Double.parseDouble(op.get(i)));
                }
            }
            catch (Exception e){
                operation="";
                Counter--;
                return "Invalid";
            }
            String Operation = op.get(1);
            if (Operation.equals("+")) {
                result = temp.get(0) + temp.get(1);
            } else if (Operation.equals("-")) {
                result = temp.get(0) - temp.get(1);
            } else if (Operation.equals("÷")||Operation.equals("/")||Operation.equals("\\")) {
                if (temp.get(1) != 0)
                    result = temp.get(0) / temp.get(1);
                else{
                    operation="";
                    Counter--;
                    return "Invalid";
                }
            } else if (Operation.equals("x")||Operation.equals("*")) {
                result = temp.get(0) * temp.get(1);
            } else {
                operation="";
                Counter--;
                return "Invalid";
            }
            if (first) {
                if (Count==5) {
                    for (int i=0;i<4;i++){
                        history.set(i,history.get(i+1));
                    }
                    Count = 4;
                }
                history.set(Count, operation);
                Count++;
                current=Count-1;
                first=false;
            }
            operation = "";
            return String.valueOf(result);
        }
        catch (Exception e){
            operation = "";
            Counter--;
            return "Invalid";
        }
    }

    @Override
    public String current() {
        try {
            return history.get(current);
        }
        catch (Exception e){
            System.out.println("Failed to find Current");
        }
        return null;
    }

    @Override
    public String prev() {
        try {
            if (current!=0) {
                current -= 1;
                String p = history.get(current);
                operation = "";
                operation = p;
                return p;
            }
        } catch (Exception e){
            System.out.println("Failed to find previous");
        }
        return null;
    }

    @Override
    public String next() {
        try {
            int next = -1;
            if (!(current>Counter-1)) {
                next = current + 1;
                current++;
            }
            operation =history.get(next);
            if(operation.equals(null))
                current--;
            return operation;
        }
        catch (Exception e){
            System.out.println("Failed to find next");
            current--;
            return null;
        }
    }

    @Override
    public void save() {
        try {
            try (BufferedWriter bw = new BufferedWriter(new PrintWriter("Saved.txt"))) {
                if(Count>current)
                    for (int i = 0; i < Count; i++)
                        bw.write(history.get(i).concat("\n"));
                else
                    for (int i = current; i >= 0; i--)
                        bw.write(history.get(i).concat("\n"));
                bw.write(String.valueOf(current));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (Exception e){
            System.out.println("Failed to save");
        }
    }

    @Override
    public void load() {
        input("Load");
        File file = new File("Saved.txt");
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String st;
            Count=0;
            history.clear();
            for (int i=0;i<5;i++){
                history.add(null);
            }
            Counter=0;
            current=0;
            while ((st = br.readLine()) != null){
                if (!st.equals("\n")&& st.length()!=1) {
                    Counter++;
                    first=true;
                    operation = st;
                    getResult();
                    System.out.println(current);
                }
                else if (st.length()==1)
                    current = Integer.parseInt(st);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private List<String> split(){
        List<String> Out = new ArrayList<String>();
        try {
            String[] expressions = new String[1];
            if (operation.contains("/"))
                operation.replace("/","÷");
            expressions[0] = operation;
            Pattern pattern = Pattern.compile("((?:^\\-?\\.?(\\d+\\.)?\\d+)|(?:(?<=[-+x/*÷])(?:\\-?\\.?(\\d+\\.)?\\d+)))+|(\\+|-|x|÷|-|\\*|/){1}+|(-?[0-9]\\d*(\\.\\d+)?)");
            List<String> elements = new ArrayList<String>();
            for (String expression : expressions) {
                Matcher matcher = pattern.matcher(expression);
                while (matcher.find()) {
                    elements.add(matcher.group());
                }
            }
            if (elements.size() == 3)
                return elements;
            return Out;
        }
        catch (Exception e){
            System.out.println("Failed To split");
        }
        return Out;
    }
}