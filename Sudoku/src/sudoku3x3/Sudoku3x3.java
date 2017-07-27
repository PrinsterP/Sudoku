package sudoku3x3;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.JFileChooser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.stage.FileChooser;

public class Sudoku3x3 extends Application {
	SudokuSolveXneqY sloveneq = new SudokuSolveXneqY();
	SudokuSolveDiff solvediff = new SudokuSolveDiff();
	final FileChooser fileChooser = new FileChooser();

  private final TableView<Sudoku> table = new TableView<>();
  private final ObservableList<Sudoku> data = FXCollections
      .observableArrayList(new Sudoku(x, y), new Sudoku(x, y), new Sudoku(x, y), 
    		  new Sudoku(x,y), new Sudoku(x,y), new Sudoku(x,y)
    		  , new Sudoku(x,y), new Sudoku(x,y), new Sudoku(x,y));
  private static final int x = 3;
  private static final int y = 3;
  private static final int sudokucells = (x*y)*(x*y);
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) {
    Callback<TableColumn<Sudoku, String>, TableCell<Sudoku, String>> cellFactory = (
        TableColumn<Sudoku, String> p) -> new EditingCell();
    int twidth = 40; 
    Scene scene = new Scene(new Group());
    stage.setWidth(520);
    stage.setMaxWidth(520);
    stage.setHeight(310);
    stage.setMaxHeight(310);
    stage.setTitle("3x3 Sudoku");
    
    //table
    table.setEditable(true);
    table.setMaxWidth(9*twidth+10);
    table.setMaxHeight(250);    

    //01
    TableColumn<Sudoku, String> firstCol = new TableColumn<>("01");
    firstCol.setCellValueFactory(new PropertyValueFactory<>("cell01"));
    firstCol.setCellFactory(cellFactory);
    firstCol.setMaxWidth(twidth);
    firstCol.setSortable(false);
    firstCol.setOnEditCommit((CellEditEvent<Sudoku, String> t) -> {
      ((Sudoku) t.getTableView().getItems().get(t.getTablePosition().getRow()))
          .setCell(t.getNewValue(),0);
    });

    //02
    TableColumn<Sudoku, String> secondCol = new TableColumn<>("02");
    secondCol.setCellValueFactory(new PropertyValueFactory<>("cell02"));
    secondCol.setCellFactory(cellFactory);
    secondCol.setMaxWidth(twidth);
    secondCol.setSortable(false);
    secondCol.setOnEditCommit((CellEditEvent<Sudoku, String> t) -> {
        ((Sudoku) t.getTableView().getItems().get(t.getTablePosition().getRow()))
        .setCell(t.getNewValue(),1);
      });
    
    //03
    TableColumn<Sudoku, String> thirdCol = new TableColumn<>("03");
    thirdCol.setCellFactory(cellFactory);
    thirdCol.setMaxWidth(twidth);
    thirdCol.setCellValueFactory(new PropertyValueFactory<>("cell03"));
    thirdCol.setSortable(false);
    thirdCol.setOnEditCommit((CellEditEvent<Sudoku, String> t) -> {
        ((Sudoku) t.getTableView().getItems().get(t.getTablePosition().getRow()))
        .setCell(t.getNewValue(),2);
      });
    
    //04
    TableColumn<Sudoku, String> fourthCol = new TableColumn<>("04");
    fourthCol.setMaxWidth(twidth);
    fourthCol.setCellFactory(cellFactory);
    fourthCol.setCellValueFactory(new PropertyValueFactory<>("cell04"));
    fourthCol.setSortable(false);
    fourthCol.setOnEditCommit((CellEditEvent<Sudoku, String> t) -> {
        ((Sudoku) t.getTableView().getItems().get(t.getTablePosition().getRow()))
        .setCell(t.getNewValue(),3);
      });
    
    
    //05
    TableColumn<Sudoku, String> fifthCol = new TableColumn<>("05");
    fifthCol.setCellValueFactory(new PropertyValueFactory<>("cell05"));
    fifthCol.setCellFactory(cellFactory);
    fifthCol.setMaxWidth(twidth);
    fifthCol.setSortable(false);
    fifthCol.setOnEditCommit((CellEditEvent<Sudoku, String> t) -> {
      ((Sudoku) t.getTableView().getItems().get(t.getTablePosition().getRow()))
          .setCell(t.getNewValue(),4);
    });

    //06
    TableColumn<Sudoku, String> sixthCol = new TableColumn<>("06");
    sixthCol.setCellValueFactory(new PropertyValueFactory<>("cell06"));
    sixthCol.setCellFactory(cellFactory);
    sixthCol.setMaxWidth(twidth);
    sixthCol.setSortable(false);
    sixthCol.setOnEditCommit((CellEditEvent<Sudoku, String> t) -> {
        ((Sudoku) t.getTableView().getItems().get(t.getTablePosition().getRow()))
        .setCell(t.getNewValue(),5);
      });
    
    //07
    TableColumn<Sudoku, String> seventhCol = new TableColumn<>("07");
    seventhCol.setCellFactory(cellFactory);
    seventhCol.setMaxWidth(twidth);
    seventhCol.setCellValueFactory(new PropertyValueFactory<>("cell07"));
    seventhCol.setSortable(false);
    seventhCol.setOnEditCommit((CellEditEvent<Sudoku, String> t) -> {
        ((Sudoku) t.getTableView().getItems().get(t.getTablePosition().getRow()))
        .setCell(t.getNewValue(),6);
      });
    
    //08
    TableColumn<Sudoku, String> eigthCol = new TableColumn<>("08");
    eigthCol.setMaxWidth(twidth);
    eigthCol.setCellFactory(cellFactory);
    eigthCol.setCellValueFactory(new PropertyValueFactory<>("cell08"));
    eigthCol.setSortable(false);
    eigthCol.setOnEditCommit((CellEditEvent<Sudoku, String> t) -> {
        ((Sudoku) t.getTableView().getItems().get(t.getTablePosition().getRow()))
        .setCell(t.getNewValue(),7);
      });    
    
    //09
    TableColumn<Sudoku, String> ninthCol = new TableColumn<>("09");
    ninthCol.setMaxWidth(twidth);
    ninthCol.setCellFactory(cellFactory);
    ninthCol.setCellValueFactory(new PropertyValueFactory<>("cell09"));
    ninthCol.setSortable(false);
    ninthCol.setOnEditCommit((CellEditEvent<Sudoku, String> t) -> {
        ((Sudoku) t.getTableView().getItems().get(t.getTablePosition().getRow()))
        .setCell(t.getNewValue(),8);
      });    
    
    table.setItems(data);
    table.getColumns().addAll(firstCol, secondCol, thirdCol, fourthCol, fifthCol, sixthCol, seventhCol, eigthCol, ninthCol);
    
    //label
    Label lbstatus = new Label();
    
    //Load Button
    Button btnload = new Button("Load");
    btnload.setMinWidth(50);
    btnload.setMaxWidth(50);
    btnload.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    configureFileChooser(fileChooser);
                    File file = fileChooser.showOpenDialog(stage);
                    if (file != null) {
                        JSONParser parser = new JSONParser();
                    	System.out.println(file.getAbsolutePath());
                        try {

                            Object obj = parser.parse(new FileReader(file.getAbsolutePath()));

                            JSONObject jsonObject = (JSONObject) obj;
                            System.out.println(jsonObject);
                            
                            String sx = (String) jsonObject.get("sudokux");
                            String sy = (String) jsonObject.get("sudokuy");
                            
                            if ((sx == (Integer.toString(x))) && (sy == (Integer.toString(y))));
                            {
                                // loop array
                                JSONArray jsudoku = (JSONArray) jsonObject.get("sudoku");
                                Iterator<String> iterator = jsudoku.iterator();
                                String[] tempSudoku = new String[(x*y)*(x*y)];
                                int temppos =0;
                            	while (iterator.hasNext()) 
                            	{
                            		tempSudoku[temppos] =iterator.next();
                            		temppos++;

                            	}
                            	temppos= 0;
                                for (Sudoku tab: data){
                                	for (int i = 0; i<(x*y); i++)
                                	{
                                		tab.setCell(tempSudoku[temppos], i);
                                		temppos++;
                                	}
                                }
                                lbstatus.setText("Sudoku loaded");
                                table.refresh();
                            }


                        } catch (FileNotFoundException e1) {
                            e1.printStackTrace();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }

                        
                    }
                }

				private void configureFileChooser(FileChooser fileChooser) {
			         fileChooser.setTitle("Load Sudoku");
			            fileChooser.setInitialDirectory(
			                new File(System.getProperty("user.home"))
			            );                 
			            fileChooser.getExtensionFilters().addAll(
			                new FileChooser.ExtensionFilter("JSON", "*.json")
	                
			            );
				}
            });
    
    //Save Button
    Button btnsave = new Button("Save");
    btnsave.setMinWidth(50);
    btnsave.setMaxWidth(50);
    btnsave.setOnAction(new EventHandler<ActionEvent>(){
    	@Override
    	public void handle(final ActionEvent e){
            //Set extension filter
    		configureFileChooser(fileChooser);
            
            //Show save file dialog
            File file = fileChooser.showSaveDialog(stage);
            
            if(file != null){
                JSONObject obj = new JSONObject();
                obj.put("sudokux", Integer.toString(x));
                obj.put("sudokuy", Integer.toString(y));
                
                int i = 0;
                JSONArray sudoku = new JSONArray();
            		for (Sudoku tab : data)
            		{
            			for (int j = 0; j<(x*y); j++)
            			{
            				sudoku.add(tab.getCell(j));
            				i++;
            			}

            		}

                obj.put("sudoku", sudoku);

                try (FileWriter fileb = new FileWriter(file.getAbsoluteFile())) {

                    fileb.write(obj.toJSONString());
                    fileb.flush();

                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                System.out.print(obj);
                lbstatus.setText("Sudoku saved");
            }
    	}
    	
		private void configureFileChooser(FileChooser fileChooser) {
	         fileChooser.setTitle("Save Sudoku");
	            fileChooser.setInitialDirectory(
	                new File(System.getProperty("user.home"))
	            );                 
	            fileChooser.getExtensionFilters().addAll(
	                new FileChooser.ExtensionFilter("JSON", "*.json")
           
	            );
		}
    });
    

    
	//Radiobuttons
	ToggleGroup tgsolver = new ToggleGroup();
	
    //RadioButton xneqy
	RadioButton rbxneqy = new RadioButton("x not equal Y");
	rbxneqy.setToggleGroup(tgsolver);
	rbxneqy.setSelected(true);
    

    //RadioButton diff
	RadioButton rbdiff = new RadioButton("Different");
	rbdiff.setToggleGroup(tgsolver);
	
	//RadioButton sat4j
	RadioButton sat4j = new RadioButton("Sat4J");
	sat4j.setToggleGroup(tgsolver);
	
    //Button Solve
    Button btnSolve = new Button("Solve");
    btnSolve.setMinWidth(50);
    btnSolve.setMaxWidth(50);
    btnSolve.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
        	//fill an array
        	int[] sudokudata = new int[sudokucells];
        	int position = 0;
        	for (Sudoku tab : data){
        		for (int i = 0; i<(x*y);i++)
        		{
        			sudokudata[position]= Integer.valueOf(tab.getCell(i));
        			position++;
        		}
        	}
        	int [] solution;
        	if(rbxneqy.isSelected())
        	{
        			solution = sloveneq.start(sudokudata, x, y);
        	}
        	else if(sat4j.isSelected())
        	{
        		solution = SudokuSolverTres.start(sudokudata);
        	}
        	else
        	{
    				solution = solvediff.start(sudokudata, x, y);
        	}
        	if (solution==sudokudata)
        	{
        		lbstatus.setText("Solver can't solve!");
        	}
        	else
        	{
        		lbstatus.setText("");
        		position=0;
        		for (Sudoku tab : data)
        		{
        			for (int i = 0; i<(x*y); i++)
        			{
        				tab.setCell(Integer.toString(solution[position]), i);
        				position++;
        			}
        		}

            table.refresh();
        	}
			
        }
    });
    
    //Button Clear
    Button btnClear = new Button("Clear");
    btnClear.setMinWidth(50);
    btnClear.setMaxWidth(50);
    btnClear.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
        	for (Sudoku tab : data){
        	    tab.clear();
        	}
            table.refresh();
            lbstatus.setText("");
        }
        
    });
    


    
    final HBox hboxa = new HBox();
    hboxa.setPadding(new Insets(0, 0, 10, 0));
    hboxa.getChildren().addAll(btnload, btnsave);
    final HBox hboxb = new HBox();
    hboxb.setPadding(new Insets(10, 10, 0, 0)); // 1 = oben 2=links 3 = unten 4 = rechts
    hboxb.getChildren().addAll(btnSolve, btnClear);
    
    //VBox
    final VBox vbox = new VBox();
    vbox.setPadding(new Insets(10, 10, 10, 10));
    vbox.getChildren().addAll(rbxneqy, rbdiff, sat4j, hboxb, hboxa, lbstatus); 
    
    //HBox
    final HBox hbox = new HBox();    
    hbox.setSpacing(5);
    hbox.setPadding(new Insets(10, 0, 0, 10));
    hbox.getChildren().addAll(table, vbox);

    ((Group) scene.getRoot()).getChildren().addAll(hbox);

    stage.setScene(scene);
    stage.show();
  
    
  }
  
  class EditingCell extends TableCell<Sudoku, String> {

    private TextField textField;

    public EditingCell() {
    }

    @Override
    public void startEdit() {
      if (!isEmpty()) {
        super.startEdit();
        createTextField();
        setText(null);
        setGraphic(textField);
        textField.selectAll();
      }
    }

    @Override
    public void cancelEdit() {
      super.cancelEdit();

      setText((String) getItem());
      setGraphic(null);
    }

    @Override
    public void updateItem(String item, boolean empty) {
      super.updateItem(item, empty);

      if (empty) {
        setText(null);
        setGraphic(null);
      } else {
        if (isEditing()) {
          if (textField != null) {
            textField.setText(getString());
          }
          setText(null);
          setGraphic(textField);
        } else {
          setText(getString());
          setGraphic(null);
        }
      }
    }

    private void createTextField() {
      textField = new TextField(getString());
      textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
      textField.focusedProperty()
          .addListener(
              (ObservableValue<? extends Boolean> arg0, Boolean arg1,
                  Boolean arg2) -> {
                if (!arg2) {
                  commitEdit(textField.getText());
                }
              });
    }

    private String getString() {
      return getItem() == null ? "" : getItem().toString();
    }
    
    
  }

  //CSudoku Class
  public static class Sudoku 
  {
	private String[] srow;

    private Sudoku(int x, int y) 
    {
    	srow = new String[x*y];
    	for (int i = 0; i<srow.length; i++)
    	{
    		srow[i]="0";
    	}
    }
    //check if valid input
    private String checkString(String zahl)
    {
    	String zahl2 ="0";
    	switch (zahl)
    	{
    	case "1": zahl2 = "1";
    		break;
    	case "2": 
    		zahl2="2";
    		break;
    	case "3":
    		zahl2="3";
    		break;
    	case "4":
    		zahl2="4";
    		break;
    	case "5": 
    		zahl2 = "5";
    		break;
    	case "6": 
    		zahl2="6";
    		break;
    	case "7":
    		zahl2="7";
    		break;
    	case "8":
    		zahl2="8";
    		break;
    	case "9":
    		zahl2="9";
    		break;
    	default:
    		zahl2 = "0";
    	}
    	return zahl2;
    }
    
    //set value at position
    public void setCell(String zahl, int pos)
    {
    	this.srow[pos]=checkString(zahl);
    }
    //returns selected position
    public String getCell(int pos)
    {
    	String zahl;
    	if (pos < srow.length)
    	{
    		zahl = srow[pos];
    	}
    	else zahl = "0";
    	
    	return zahl;
    }
    //returns position 1 of array
    public String getCell01() {
      return this.srow[0];
    }
    
    //returns position 2 of array
    public String getCell02() {
      return this.srow[1];
    }
    
    //returns position 3 of array
    public String getCell03()
    {
    	return this.srow[2];
    }
    
    //returns position 4 of array
    public String getCell04()
    {
    	return this.srow[3];
    }
    //returns position 5 of array
    public String getCell05() {
      return this.srow[4];
    }
    
    //returns position 6 of array
    public String getCell06() {
      return this.srow[5];
    }
    
    //returns position 7 of array
    public String getCell07()
    {
    	return this.srow[6];
    }
    
    //returns position 8 of array
    public String getCell08()
    {
    	return this.srow[7];
    }  
    
    //returns position 9 of array
    public String getCell09()
    {
    	return this.srow[8];
    }  
    //clear entire row
    public void clear()
    {
    	for (int i = 0; i<srow.length; i++)
    	{
    		srow[i]="0";
    	}
    }
  }
}