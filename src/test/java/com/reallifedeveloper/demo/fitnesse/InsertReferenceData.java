package com.reallifedeveloper.demo.fitnesse;

import java.io.InputStream;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.datatype.IDataTypeFactory;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

import com.reallifedeveloper.tools.test.fitnesse.AbstractFitNesseFixture;

public class InsertReferenceData extends AbstractFitNesseFixture {

	private String filename;

	public InsertReferenceData() throws Exception {
		super("META-INF/spring-context-rld-fitnesse-demo-func-test.xml");
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void execute() throws Exception {
		DatabaseOperation.CLEAN_INSERT.execute(getConnection(), getDataSet(filename));
	}

	private IDatabaseConnection getConnection() throws DatabaseUnitException, SQLException {
		DataSource ds = getBean(DataSource.class);
		IDatabaseConnection connection = new DatabaseConnection(ds.getConnection());
		IDataTypeFactory dataTypeFactory = getBean(IDataTypeFactory.class);
		connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, dataTypeFactory);
		return connection;
	}

	private IDataSet getDataSet(String filename) throws DataSetException {
		logger().debug("filename=" + filename);
		InputStream is = InsertReferenceData.class.getResourceAsStream(filename);
		logger().debug("is=" + is);
		IDataSet dataSet = new FlatXmlDataSetBuilder().build(is);
		return dataSet;
	}
}
