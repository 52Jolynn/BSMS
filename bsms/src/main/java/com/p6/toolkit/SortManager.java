package com.p6.toolkit;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

/**
 * 这是一个很方便的类，使用者不需要为了实现排序功能而特别制定一个TableMoel。只需要像使用插入式工具那样
 * 插入这个类，就可以方便地实现排序功能。
 * <strong>警告:</strong>
 * 由于该类的实现过程设计TableHeader的处理，如果用户的JTable实例设计TableHeader的处理，可能会出现意外。
 */

public class SortManager extends Thread implements TableModelListener {

	/**
	 * upIcon
	 * <br>
	 * <code>UpDownArrow</code>
	 *
	 * @see #UpDownArrow
	 */
	final static Icon upIcon = new UpDownArrow(0);
	/**
	 * downIcon
	 * <br>
	 * <code>UpDownArrow</code>
	 *
	 * @see #UpDownArrow
	 */
	final static Icon downIcon = new UpDownArrow(1);

	private JTable table;

	private TableModel dataModel;
	/**
	 * sortColumn
	 * <br>
	 * The current sorting column.
	 * <br>
	 * Chinese Descriptions:
	 * <br>
	 * 当前排序的列.
	 *
	 * <p>
	 */
	private int sortColumn;

	private Row rows[];

	/**
	 * ascending
	 * <br>
	 * The order of sorting.
	 * <br>
	 * Chinese Descriptions:
	 * <br>
	 * 排序的顺序。
	 */
	private boolean ascending;
	/**
	 * sortableColumns
	 * <br> It's used to point out which columns are sortable.
	 * <br> Chinese Descriptions:
	 * <br> 用来指出哪几列是需要排序功能的.
	 *
	 * <p>
	 */
	private int sortableColumns[];

	/**
	 * SortManager
	 * <br>
	 * The base way to use this class.It will sort all the columns when user
	 * click it.
	 * <br>
	 * Chinese Descriptions:
	 * <br>
	 * 该类基本用法，默认能给所有的列排序。
	 *
	 * <p>
	 *
	 * @param jtable JTable
	 */
	public SortManager(JTable jtable) {
		rows = null;
		ascending = true;
		sortableColumns = null;
		table = jtable;
		int i = 0;
		int length = jtable.getModel().getColumnCount();
		final int[] columns = new int[length];
		for (; i < length; i++) {
			columns[i] = i;
		}
		sortableColumns = columns;
		initialize();
	}

	/**
	 * SortManager
	 * <br> The base way to use this class.It will sort the specified column
	 * when user click it.
	 * <br> Chinese Descriptions:
	 * <br> 该类基本用法，默认能给指定的列排序。
	 *
	 * <p>
	 *
	 * @param jtable JTable
	 * @param i int<br>
	 *   The column user specify to sort.<br>
	 */
	public SortManager(JTable jtable, int i) {
		rows = null;
		ascending = true;
		sortableColumns = null;
		table = jtable;
		sortColumn = i;
		initialize();
	}

	/**
	 * SortManager
	 * <br> The base way to use this class.It will sort the specified columns
	 * when user click it.
	 * <br> Chinese Descriptions:
	 * <br> 该类基本用法，默认能给指定的一组列排序。
	 *
	 * <p>
	 *
	 * @param jtable JTable
	 * @param ai int[]<br> The columns user specify to sort.<br>
	 */
	public SortManager(JTable jtable, int ai[]) {
		this(jtable, ai[0]);
		sortableColumns = (int[]) ai.clone();
	}

	/**
	 * initialize
	 * <br> Initializing the JTable came in,escpecailly to add the listeners to
	 * JTable.
	 * <br>
	 * Chinese Descriptions:
	 * <br>
	 * 初始化JTable实例，并且添加相应的侦听。
	 */
	public void initialize() {
		//table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dataModel = table.getModel();
		((AbstractTableModel) dataModel).addTableModelListener(this);
		addMouseListener(table);
		JTableHeader jtableheader = table.getTableHeader();
		jtableheader.setDefaultRenderer(createHeaderRenderer());
		if (table.getRowCount() > 0) {
			reinitialize();
		}
	}

	/**
	 * createHeaderRenderer
	 * <br>
	 * Create the specify HeaderRender for the table,can response the mouse
	 * action to sort the column.
	 * <br>
	 * Chinese Description:
	 * <br>
	 * 为table创建能够相应鼠标时间排序的TableHeaderRender。
	 *
	 * <p>
	 *
	 * @return TableCellRenderer
	 */
	protected TableCellRenderer createHeaderRenderer() {
		DefaultTableCellRenderer defaultHeaderRenderer = new SortHeaderRenderer();
		defaultHeaderRenderer.setHorizontalAlignment(0);
		defaultHeaderRenderer.setHorizontalTextPosition(2);
		return defaultHeaderRenderer;
	}

	public void reinitialize() {
		rows = null;
		if (table.getRowCount() > 0) {
			rows = new Row[table.getRowCount()];
			for (int i = 0; i < rows.length; i++) {
				rows[i] = new Row();
				rows[i].index = i;
			}

			if (columnIsSortable(sortColumn)) {
				sort();
			}
		}
	}

	/**
	 * columnIsSortable
	 * <br> Check out that if the specify column is sortable. It depend on the
	 * constructor.
	 * <br>
	 * Chinese Descriptions:
	 * <br>
	 * 判断指定的列是否可排序，该方法的运行逻辑由整个类的构造函数构造时决定。
	 *
	 * <p>
	 *
	 * @param i int
	 * @return boolean
	 * @see #public SortModel(Container container1, JTable jtable, int i)
	 * @see #public SortModel(Container container1, JTable jtable, int ai[])
	 * @see #sortColumn
	 * @see #sortablrColumns
	 */
	private boolean columnIsSortable(int i) {
		if (rows != null) {
			if (sortableColumns != null) {
				for (int j = 0; j < sortableColumns.length; j++) {
					if (i == sortableColumns[j]) {
						return true;
					}
				}

			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * mouseClicked
	 * <br> The real Method to add mouselistener to JTable.Due to the
	 * SortHeaderRender is a inner class of this class.So the sortcolumn user
	 * want can share and be managed in whole class field.
	 * <br> Chinese Description:
	 * <br>
	 * 这个才是真正的处理排序的方法之一，它对JTable添加鼠标时间侦听。获得用户想排序的某行，由于SortHeaderRender是内部类，所以可以共享其数据并处理。
	 *
	 * @param table JTable
	 */
	public void addMouseListener(final JTable table) {
		table.getTableHeader().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseevent) {
				int i = table.columnAtPoint(mouseevent.getPoint());
				int j = table.convertColumnIndexToModel(i);
				//转换出用户想排序的列和底层数据的列，然后判断
				if (!columnIsSortable(j)) {
					return;
				}
				if (j == sortColumn) {
					ascending = !ascending;
				} else {
					ascending = true;
					sortColumn = j;
				}
				sort();
			}

		});
	}

	/**
	 * sort
	 * <br> The main method to sort the rows depends on the specify column to
	 * sort.
	 * <br> Chinese Descriptions:
	 * <br> 主要调用排序的方法。
	 * Fix bug:table.UpdateUI();--->
	 *             table.recalidate();
	 *             table.repaint();
	 *
	 * @see #resetData()
	 */
	public void sort() {
		if (rows == null) {
			return;
		} else {

			((AbstractTableModel) dataModel).removeTableModelListener(this);
			Arrays.sort(rows);
			resetData();
			((AbstractTableModel) dataModel).fireTableDataChanged();
			((AbstractTableModel) dataModel).addTableModelListener(this);
			table.revalidate();
			table.repaint();
			return;
		}
	}
	
	/**
	 * resetData
	 * <br>
	 * The last step to sort.We reset the sorted to  the table and show the
	 * changes.
	 * <br>
	 * Chinese Descriptions:
	 * <br>
	 * 排序的最后一步，把排好序的数据重新返回table里面。
	 *
	 * @see #sort()
	 */
	public void resetData() {
		Vector data = new Vector(dataModel.getRowCount());
		int i = 0;
		for (; i < dataModel.getRowCount(); i++) {
			int j = 0;
			final Vector vv = new Vector(dataModel.getColumnCount());
			for (; j < dataModel.getColumnCount(); j++) {
				vv.add(dataModel.getValueAt(i, j));
			}
			data.add(vv);
		}
		i = 0;
		for (; i < rows.length; i++) {
			if (rows[i].index != i) {
				int j = 0;
				final Vector vv = (Vector) data.get(rows[i].index);
				for (; j < dataModel.getColumnCount(); j++) {
					dataModel.setValueAt(vv.get(j), i, j);
				}
			}
		}
	}

	public void tableChanged(TableModelEvent tablemodelevent) {
		reinitialize();
	}

	private class SortHeaderRenderer extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable jtable,
				Object obj, boolean flag, boolean flag1, int i, int j) {
			if (jtable != null) {
				JTableHeader jtableheader = jtable.getTableHeader();
				if (jtableheader != null) {
					setForeground(jtableheader.getForeground());
					setBackground(jtableheader.getBackground());
					setFont(jtableheader.getFont());
				}
			}
			setText(obj != null ? obj.toString() : "");
			int k = jtable.convertColumnIndexToModel(j);
			if (k == sortColumn) {
				setIcon(ascending ? SortManager.upIcon : SortManager.downIcon);
			} else {
				setIcon(null);
			}
			setBorder(UIManager.getBorder("TableHeader.cellBorder"));
			return this;
		}
	}

	private class Row implements Comparable {
		private Row() {
		}

		public int compareTo(Object obj) {
			Row row = (Row) obj;
			//bug found and fixed by chenxing 05.1.24.Support Local String sorting,such as //Chinese.
			//Chinese Descriptions:
			//支持本地字符的比较排序，例如：中文排序。
			java.text.Collator cnCollator = java.text.Collator.getInstance(Locale.CHINESE);
			//////////////////////////////////////////
			Object obj1 = dataModel.getValueAt(index, sortColumn);
			Object obj2 = dataModel.getValueAt(row.index, sortColumn);
			if (ascending) {
				if (!(obj1 instanceof Comparable)) {
					return -1;
				}
				if (!(obj2 instanceof Comparable)) {
					return 1;
				} else {
					return cnCollator.compare(obj1, obj2);
				}
			}
			if (!(obj1 instanceof Comparable)) {
				return 1;
			}
			if (!(obj2 instanceof Comparable)) {
				return -1;
			} else {
				return cnCollator.compare(obj2, obj1);
			}

		}

		public int index;
	}
}