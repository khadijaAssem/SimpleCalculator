package eg.edu.alexu.csd.oop.calculator.cs27;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import static javafx.scene.input.KeyCode.ENTER;

public class Controller {
    @FXML
    private TextField TfOperation;
    @FXML
    private TextArea History;
    @FXML
    private Label Ans;

    private String Answer = "";
    private boolean New = true;
    Calculator Calculator = new Calc();
    //Read from keyboard ; press Enter to evaluate
    @FXML
    public void handle(KeyEvent event) {
        if (New) {
            TfOperation.setText("");
            Ans.setText("");
            New = false;
            return;
        }
        if(event.getCode() == ENTER) {
            Evaluate();
            New = true;
        }
    }
    //Read from buttons
    @FXML
    public void ClickedNoOrOp(ActionEvent event){
        if (New) {
            TfOperation.setText("");
            Ans.setText("");
            New = false;
        }
        String input = ((Button)event.getSource()).getText();
        TfOperation.setText(TfOperation.getText().concat(input));
    }
    //On clicking Enter or equal
    @FXML
    public void Evaluate(){
        Calculator.input(TfOperation.getText());
        String temp = Calculator.getResult();
        if (!temp.equals("Invalid")){
            Ans.setText(temp);
            Answer=temp;
            History.setText(History.getText().concat(TfOperation.getText().concat(" = ").concat(Ans.getText()).concat("\n")));
            New = true;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Invalid input,Please enter a valid input");
            alert.showAndWait();
        }
    }
    @FXML
    public void AC(){
        TfOperation.setText("");
    }
    @FXML
    public void Delete(){
        try {
            TfOperation.setText(TfOperation.getText().substring(0, TfOperation.getText().length() - 1));
        }
        catch (Exception e){}
    }
    @FXML
    public void Ans() {
        TfOperation.setText(Answer);
        Ans.setText("");
        New=false;
    }
    @FXML
    public void Prev(){
        String s = Calculator.prev();
        if (s.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("No more previous operations");
            alert.showAndWait();
        }
        else
            TfOperation.setText(s);
        Ans.setText("");
    }
    @FXML
    public void next(){
        String s = Calculator.next();
        if (s.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("No more next operations");
            alert.showAndWait();
        }
        else
            TfOperation.setText(s);
        Ans.setText("");
    }
    @FXML
    public void Save(){
        Calculator.save();
    }
    public void Load(){
        Calculator.load();
    }
    @FXML
    public void Current(){
        String s = Calculator.current();
        TfOperation.setText(s);
        Ans.setText("");
    }
    @FXML
    public void Clear(){
        History.setText("");
    }
}