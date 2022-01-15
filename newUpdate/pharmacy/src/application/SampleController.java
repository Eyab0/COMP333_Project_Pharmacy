package application;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class SampleController {

	private ArrayList<Employeedata> data;
	private ObservableList<Employeedata> dataList;

	@FXML
	private TableColumn<Employeedata, String> DateBirthColumn;

	@FXML
	private Button Delete;

	@FXML
	private TextField ID;

	@FXML
	private TableColumn<Employeedata, Integer> IDColumn;

	@FXML
	private TableColumn<Employeedata, String> NameColumn;
	@FXML
	private TableColumn<Employeedata, String> PassColumn;

	@FXML
	private TableColumn<Employeedata, Double> W_HourColoumn;

	@FXML
	private TableColumn<Employeedata, Double> H_PriceColumn;

	@FXML
	private TextField Newdateofemployment;

	@FXML
	private TextField SearchFiled;

	@FXML
	private TableView<Employeedata> TableData;

	@FXML
	private Button Update;

	@FXML
	private Button add;

	@FXML
	private TextField addDateOfBirth;

	@FXML
	private TextField addName;

	@FXML
	private TextField adddateofemployment;

	@FXML
	private TextField addPassWord;

	@FXML
	private TableColumn<Employeedata, String> dateofemploymentColumn;

	@FXML
	private TextField newDateBirth;

	@FXML
	private TextField newName;

	@FXML
	private TextField oldID;
	@FXML
	private Button btnback;
	@FXML
	private ToggleButton Hourly_Emp;

	@FXML
	private ToggleGroup EmpType;

	@FXML
	private ToggleButton Contrect_Emp;
	@FXML
	private TextField addWHours;

	@FXML
	private TextField addHPrice;

	@FXML
	private TextField newPass;

	@FXML
	private TextField newWHour;

	@FXML
	private TextField newHPrice;

	int statmentID;
	boolean Contract = false;
	boolean Hourly = true;

	@FXML
	void btnContrect_Emp(ActionEvent event) {
		Contract = true;
		Hourly = false;
		H_PriceColumn.setText("Amount Paid");
		W_HourColoumn.setVisible(false);
		addWHours.setVisible(false);
		addHPrice.setPromptText("Amount Paid");
		TableData.setPrefWidth(919);
		TableData.setLayoutX(64);
		newWHour.setVisible(false);
		newHPrice.setPromptText("New Amount Paid");
		Update.setLayoutX(746);
		initialize();
	}

	@FXML
	void btnHourly_Emp(ActionEvent event) {
		Contract = false;
		Hourly = true;
		H_PriceColumn.setText("Hour Price");
		W_HourColoumn.setVisible(true);
		addWHours.setVisible(true);
		addHPrice.setPromptText("Hour Price");
		TableData.setPrefWidth(1037);
		TableData.setLayoutX(9);
		newWHour.setVisible(true);
		newHPrice.setPromptText("New Hour Price");
		Update.setLayoutX(883);
		initialize();
	}

	@FXML
	public void initialize() {
		data = new ArrayList<>();
		dataList = FXCollections.observableArrayList(data);
		TableData.setEditable(true);

		// IDColumn.setCellFactory(TextFieldTableCell.<Employeedata,
		// Integer>forTableColumn(new IntegerStringConverter()));
		IDColumn.setCellValueFactory(new PropertyValueFactory<Employeedata, Integer>("id"));

		NameColumn.setCellValueFactory(new PropertyValueFactory<Employeedata, String>("employee_name"));
		NameColumn.setCellFactory(TextFieldTableCell.<Employeedata>forTableColumn());
		NameColumn.setOnEditCommit((CellEditEvent<Employeedata, String> t) -> {
			((Employeedata) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setEmployee_name(t.getNewValue()); // display
			// only

			updateName(t.getRowValue().getId(), t.getNewValue());
		});

		DateBirthColumn.setCellValueFactory(new PropertyValueFactory<Employeedata, String>("birthday"));
		DateBirthColumn.setCellFactory(TextFieldTableCell.<Employeedata>forTableColumn());
		DateBirthColumn.setOnEditCommit((CellEditEvent<Employeedata, String> t) -> {
			((Employeedata) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setBirthday(t.getNewValue()); // display
													// only

			updateBDate(t.getRowValue().getId(), t.getNewValue());
		});

		dateofemploymentColumn
				.setCellValueFactory(new PropertyValueFactory<Employeedata, String>("date_of_employment"));
		dateofemploymentColumn.setCellFactory(TextFieldTableCell.<Employeedata>forTableColumn());
		dateofemploymentColumn.setOnEditCommit((CellEditEvent<Employeedata, String> t) -> {
			((Employeedata) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setDate_of_employment(t.getNewValue()); // display
			// only

			updateDateE(t.getRowValue().getId(), t.getNewValue());
		});
		PassColumn.setCellValueFactory(new PropertyValueFactory<Employeedata, String>("emp_password"));
		PassColumn.setCellFactory(TextFieldTableCell.<Employeedata>forTableColumn());
		PassColumn.setOnEditCommit((CellEditEvent<Employeedata, String> t) -> {
			((Employeedata) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setEmp_password(t.getNewValue()); // display
			// only

			updatePass(t.getRowValue().getId(), t.getNewValue());
		});
		if (Hourly) {
			W_HourColoumn.setCellValueFactory(new PropertyValueFactory<Employeedata, Double>("work_hours"));
			W_HourColoumn.setCellFactory(
					TextFieldTableCell.<Employeedata, Double>forTableColumn(new DoubleStringConverter()));
			W_HourColoumn.setOnEditCommit((CellEditEvent<Employeedata, Double> t) -> {
				((Employeedata) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setWork_hours(t.getNewValue()); // display
				// only

				updateHourWork(t.getRowValue().getId(), t.getNewValue());
			});
			H_PriceColumn.setCellValueFactory(new PropertyValueFactory<Employeedata, Double>("hour_price"));
			H_PriceColumn.setCellFactory(
					TextFieldTableCell.<Employeedata, Double>forTableColumn(new DoubleStringConverter()));
			H_PriceColumn.setOnEditCommit((CellEditEvent<Employeedata, Double> t) -> {
				((Employeedata) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setHour_price(t.getNewValue()); // display
				// only

				updateHourPrice(t.getRowValue().getId(), t.getNewValue());
			});
		} else if (Contract) {
			H_PriceColumn.setCellValueFactory(new PropertyValueFactory<Employeedata, Double>("amount_paid"));
			H_PriceColumn.setCellFactory(
					TextFieldTableCell.<Employeedata, Double>forTableColumn(new DoubleStringConverter()));
			H_PriceColumn.setOnEditCommit((CellEditEvent<Employeedata, Double> t) -> {
				((Employeedata) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setAmount_paid(t.getNewValue()); // display
				// only

				updateAmountPaid(t.getRowValue().getId(), t.getNewValue());
			});
		}

		getData();
		TableData.setItems(dataList);
		searchRentalEmployee();
	}

	private void updateAmountPaid(int id2, Double newValue) {
		try {
//			System.out.println("update  employee set employee_name = '" + name + "' where id = " + ID_num);
			Connector.a.connectDB();
			Connector.a.ExecuteStatement(
					"update  contrect_employee set amount_paid = " + newValue + " where id = " + id2 + ";");
			Connector.a.connectDB().close();
//			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void updateHourPrice(int id2, Double newValue) {
		try {
//			System.out.println("update  employee set employee_name = '" + name + "' where id = " + ID_num);
			Connector.a.connectDB();
			Connector.a.ExecuteStatement(
					"update  hourly_employee set hour_price = " + newValue + " where id = " + id2 + ";");
			Connector.a.connectDB().close();
//			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void updateHourWork(int id2, double newValue) {
		try {
//			System.out.println("update  employee set employee_name = '" + name + "' where id = " + ID_num);
			Connector.a.connectDB();
			Connector.a.ExecuteStatement(
					"update  hourly_employee set work_hours = " + newValue + " where id = " + id2 + ";");
			Connector.a.connectDB().close();
//			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void updatePass(int id2, String newValue) {
		try {
//			System.out.println("update  employee set employee_name = '" + name + "' where id = " + ID_num);
			Connector.a.connectDB();
			Connector.a
					.ExecuteStatement("update  employee set emp_password = '" + newValue + "' where id = " + id2 + ";");
			Connector.a.connectDB().close();
//			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void searchRentalEmployee() {
		// TODO Auto-generated method stub
		FilteredList<Employeedata> filteredData = new FilteredList<>(dataList, b -> true);
		SearchFiled.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(employeedata -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (employeedata.getEmployee_name().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches car Id
				} else if (String.valueOf(employeedata.getId()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} // Filter matches password
				else
					return false; // Does not match.
			});
		});
		SortedList<Employeedata> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(TableData.comparatorProperty());
		TableData.setItems(sortedData);
	}

	public void getData() {
		if (Hourly) {
			String SQL = "select * from employee e,hourly_employee h  where e.id=h.id";
			try {
				Connector.a.connectDB();
				java.sql.Statement state = Connector.a.connectDB().createStatement();
				ResultSet rs = state.executeQuery(SQL);
				while (rs.next()) {
					Employeedata em = new Employeedata(rs.getString(2).toString(), rs.getDate(3).toString(),
							rs.getDate(4).toString(), rs.getString(5).toString(), rs.getDouble(7), rs.getDouble(8));
					em.setId(rs.getInt(1));
					dataList.add(em);
				}
				rs.close();
				state.close();
				Connector.a.connectDB().close();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (Contract) {
			String SQL = "select * from employee e,contrect_employee c  where e.id=c.id";
			try {
				Connector.a.connectDB();
				java.sql.Statement state = Connector.a.connectDB().createStatement();
				ResultSet rs = state.executeQuery(SQL);
				while (rs.next()) {
					Employeedata em = new Employeedata(rs.getString(2).toString(), rs.getDate(3).toString(),
							rs.getDate(4).toString(), rs.getString(5).toString(), rs.getDouble(7));
					em.setId(rs.getInt(1));
					dataList.add(em);
				}
				rs.close();
				state.close();
				Connector.a.connectDB().close();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void updateName(int ID_num, String name) {

		try {
//			System.out.println("update  employee set employee_name = '" + name + "' where id = " + ID_num);
			Connector.a.connectDB();
			Connector.a
					.ExecuteStatement("update  employee set employee_name = '" + name + "' where id = " + ID_num + ";");
			Connector.a.connectDB().close();
//			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void updateBDate(int ID_num, String date) {
		try {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			java.util.Date myDate = null;
			java.sql.Date sqlDate;
			try {
				myDate = formatter.parse(date);
				System.out.println(myDate);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sqlDate = new java.sql.Date(myDate.getTime());
			System.out.println(sqlDate);

			// System.out.println("update employee set birthday = '" + sqlDate + "' where id
			// = " + ID_num);
			Connector.a.connectDB();
			Connector.a
					.ExecuteStatement("update  employee set birthday = '" + sqlDate + "' where id = " + ID_num + ";");
			Connector.a.connectDB().close();
			// System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void updateDateE(int ID_num, String date) {
		try {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			java.util.Date myDate = null;
			java.sql.Date sqlDate;
			try {
				myDate = formatter.parse(date);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sqlDate = new java.sql.Date(myDate.getTime());
			// System.out.println("update employee set date_of_employment = " + sqlDate + "
			// where id = " + ID_num);
			Connector.a.connectDB();
			Connector.a.ExecuteStatement(
					"update  employee set date_of_employment = '" + sqlDate + "' where id = " + ID_num + ";");
			Connector.a.connectDB().close();
			// System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	@FXML
	void addOnAction(ActionEvent event) {

		try {

			Employeedata rc;
			if (Hourly) {
				rc = new Employeedata(addName.getText(), addDateOfBirth.getText(), adddateofemployment.getText(),
						addPassWord.getText(), Double.parseDouble(addWHours.getText()),
						Double.parseDouble(addHPrice.getText()));
				dataList.add(rc);
				insertData(rc);
				addName.clear();
				addDateOfBirth.clear();
				adddateofemployment.clear();
				addPassWord.clear();
				addWHours.clear();
				addHPrice.clear();
				initialize();
			} else if (Contract) {
				rc = new Employeedata(addName.getText(), addDateOfBirth.getText(), adddateofemployment.getText(),
						addPassWord.getText(), Double.parseDouble(addHPrice.getText()));
				dataList.add(rc);
				insertData(rc);
				addName.clear();
				addDateOfBirth.clear();
				adddateofemployment.clear();
				addPassWord.clear();
				addWHours.clear();
				addHPrice.clear();
				initialize();
			}
		} catch (Exception e) {

		}

	}

	public void refresh() {
		TableData.getItems().clear();
		getData();
		TableData.setItems(dataList);

	}

	private void insertData(Employeedata rc) {

		try {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			java.util.Date myDate = null;
			java.sql.Date sqlDate;
			try {
				myDate = formatter.parse(rc.getBirthday());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			sqlDate = new java.sql.Date(myDate.getTime());

			java.util.Date myDate2 = null;
			java.sql.Date sqlDate2;
			try {
				myDate2 = formatter.parse(rc.getDate_of_employment());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sqlDate2 = new java.sql.Date(myDate.getTime());

//			System.out.println("Insert into employee (id,employee_name,birthday,date_of_employment) values("
//					+ rc.getId() + ",'" + rc.getEmployee_name() + "','" + sqlDate + "','" + sqlDate2 + "')");

			Connector.a.connectDB();
			String sql = "Insert into employee (employee_name,birthday,date_of_employment,emp_password) values(?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) Connector.a.connectDB().prepareStatement(sql);
			ps.setString(4, rc.getEmp_password());
			ps.setString(1, rc.getEmployee_name());
			ps.setTimestamp(2, new java.sql.Timestamp(myDate.getTime()));
			ps.setTimestamp(3, new java.sql.Timestamp(myDate2.getTime()));
			ps.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (Hourly) {
			String SQL = "select id from employee where employee_name='" + rc.getEmployee_name()
					+ "' and emp_password='" + rc.getEmp_password() + "';";
			try {
				Connector.a.connectDB();
				java.sql.Statement state = Connector.a.connectDB().createStatement();
				ResultSet rs = state.executeQuery(SQL);
				while (rs.next()) {
					statmentID = rs.getInt(1);
				}
				rs.close();
				state.close();
				Connector.a.connectDB().close();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				Connector.a.connectDB();
				String sql = "Insert into hourly_employee (id,work_hours,hour_price) values(?,?,?)";
				PreparedStatement ps = (PreparedStatement) Connector.a.connectDB().prepareStatement(sql);
				ps.setInt(1, statmentID);
				ps.setDouble(2, rc.getWork_hours());
				ps.setDouble(3, rc.getHour_price());
				ps.execute();

			} catch (SQLException e) {

			} catch (ClassNotFoundException e) {

			}
		} else if (Contract) {
			String SQL = "select id from employee where employee_name='" + rc.getEmployee_name()
					+ "' and emp_password='" + rc.getEmp_password() + "';";
			try {
				Connector.a.connectDB();
				java.sql.Statement state = Connector.a.connectDB().createStatement();
				ResultSet rs = state.executeQuery(SQL);
				while (rs.next()) {
					statmentID = rs.getInt(1);
				}
				rs.close();
				state.close();
				Connector.a.connectDB().close();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				Connector.a.connectDB();
				String sql = "Insert into contrect_employee (id,amount_paid) values(?,?)";
				PreparedStatement ps = (PreparedStatement) Connector.a.connectDB().prepareStatement(sql);
				ps.setInt(1, statmentID);
				ps.setDouble(2, rc.getAmount_paid());
				ps.execute();

			} catch (SQLException e) {

			} catch (ClassNotFoundException e) {

			}

		}
	}

	@FXML
	void deleteOnAction(ActionEvent event) {

		try {
			if (ID.getText() != null) {
				int id = Integer.parseInt(ID.getText());
				deleteRow(id);
			}

			ID.clear();
		} catch (Exception e) {

		}
		initialize();

	}

	private void deleteRow(int id) {
		try {
//			System.out.println("delete from  employee where id =" + id + ";");
			Connector.a.connectDB();
			Connector.a.ExecuteStatement("delete from  employee where id =" + id + ";");
			Connector.a.connectDB().close();
//			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void updateOnAction(ActionEvent event) {

		try {
			if (oldID.getText() != null) {
				int id = Integer.parseInt(oldID.getText());
				if (newName.getText().length() > 0) {
					updateName(id, newName.getText());
				}
				if (newDateBirth.getText().length() > 0) {
					updateBDate(id, newDateBirth.getText());
				}
				if (Newdateofemployment.getText().length() > 0) {
					updateDateE(id, Newdateofemployment.getText());
				}
				if (newPass.getText().length() > 0) {
					updatePass(id, newPass.getText());
				}
				if (Hourly) {
					if (newHPrice.getText().length() > 0) {
						updateHourPrice(id, Double.parseDouble(newHPrice.getText()));
					}
					if (newWHour.getText().length() > 0) {
						updateHourWork(id, Double.parseDouble(newWHour.getText()));
					}
				} else if (Contract) {
					if (newHPrice.getText().length() > 0) {
						updateAmountPaid(id, Double.parseDouble(newHPrice.getText()));
					}
				}
				oldID.clear();
				newName.clear();
				newDateBirth.clear();
				Newdateofemployment.clear();
				newPass.clear();
				newHPrice.clear();
				newWHour.clear();
				initialize();

			}
		} catch (Exception e) {

		}
		// refresh();

	}

	@FXML
	void back(ActionEvent event) {
		try { // open new stage
			Stage stage;
			Parent root;
			stage = (Stage) btnback.getScene().getWindow();
			stage.close();
			root = FXMLLoader.load(getClass().getResource("Start.fxml"));
			Scene scene = new Scene(root, 741, 484);
			stage.setScene(scene);
			stage.setTitle("Chose One");
			stage.show();

		} catch (IOException e1) {

		}
	}

}
