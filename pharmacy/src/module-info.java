module pharmacy {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.graphics;
	requires mysql.connector.java;
	requires javafx.base;
//	requires mysql.connector.java;

	opens application to javafx.graphics, javafx.fxml, javafx.base;

}
