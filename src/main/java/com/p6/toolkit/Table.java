/**
 * @author Laud
 * @version 1.0.0
 * copyright
 */
package com.p6.toolkit;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.*;

public class Table extends DefaultTableModel {

	public Table() {
		super();
	}
	
	public Table(Object[][] data, Object[] columnName) {
		super(data, columnName);
	}
	
	public Table(Vector<Vector<String>> data, Vector<String> columnName) {
		super(data, columnName);
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	public static void FitTableColumns(JTable myTable){
		JTableHeader header = myTable.getTableHeader();
		int rowCount = myTable.getRowCount();
		Enumeration<TableColumn> columns = myTable.getColumnModel().getColumns();
		while(columns.hasMoreElements()){
			TableColumn column = (TableColumn)columns.nextElement();
			int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
			int width = (int)myTable.getTableHeader().getDefaultRenderer()
			.getTableCellRendererComponent(myTable, column.getIdentifier()
					, false, false, -1, col).getPreferredSize().getWidth();
			for(int row = 0; row<rowCount; row++){
				int preferedWidth = (int)myTable.getCellRenderer(row, col).getTableCellRendererComponent(myTable,
						myTable.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
				width = Math.max(width, preferedWidth);
			}
			header.setResizingColumn(column); // 森俴竭笭猁
			column.setWidth(width+myTable.getIntercellSpacing().width);
			myTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		}
	}
}
