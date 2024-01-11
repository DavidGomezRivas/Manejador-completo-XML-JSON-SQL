package DOMXML;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class MostrarSalonHabana extends JFrame {
	private JTable table;
	private JButton btnBuscarArchivo;
	private JButton btnCargarArchivo;
	private JFileChooser fileChooser;
	private JTextField txtFilePath;

	public MostrarSalonHabana() {
		setBackground(new Color(160, 82, 45));
		getContentPane().setBackground(new Color(255, 160, 122));
		setResizable(false);
		setTitle("Sistema de Reservas");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 916, 400);
		getContentPane().setLayout(null);

		txtFilePath = new JTextField();
		txtFilePath.setEditable(false);
		txtFilePath.setBounds(10, 11, 580, 25);
		getContentPane().add(txtFilePath);

		// Botón para buscar archivo
		btnBuscarArchivo = new JButton("Buscar Archivo");
		btnBuscarArchivo.setBounds(600, 11, 140, 25);
		getContentPane().add(btnBuscarArchivo);

		// Botón para cargar archivo
		btnCargarArchivo = new JButton("Cargar Archivo");
		btnCargarArchivo.setBounds(750, 11, 140, 25);
		getContentPane().add(btnCargarArchivo);
		btnCargarArchivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarDatosDesdeXML(txtFilePath.getText());
			}

		});

		// JFileChooser
		fileChooser = new JFileChooser();

		// Evento para el botón de buscar archivo
		btnBuscarArchivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					// Actualiza el campo de texto con la ruta del archivo seleccionado
					txtFilePath.setText(fileChooser.getSelectedFile().getPath());
				}
			}
		});

		// Evento para el botón de cargar archivo
		btnCargarArchivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adjustColumnWidths(table);
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 47, 880, 303); // Ajusta el tamaño del JScrollPane
		getContentPane().add(scrollPane);

		table = new JTable();
		table.setBackground(new Color(255, 255, 255));
		table.setModel(new DefaultTableModel(new Object[][] { {} },
				new String[] { "Nombre", "Fecha", "Telefono", "Tipo Evento", "NPersonas", "Tipo Cocina", "NJornadas", "NecesitaHab", "TipoMesa", "ComensalesMesa", "HabNecesarias" }));

		scrollPane.setViewportView(table);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}

	private void adjustColumnWidths(JTable table) {
		JTableHeader header = table.getTableHeader();

		for (int column = 0; column < table.getColumnCount(); column++) {
			TableColumn tableColumn = table.getColumnModel().getColumn(column);
			int preferredWidth = tableColumn.getMinWidth();
			int maxWidth = tableColumn.getMaxWidth();

			// ver el ancho del encabezado de la columna
			TableCellRenderer headerRenderer = tableColumn.getHeaderRenderer();
			if (headerRenderer == null) {
				headerRenderer = header.getDefaultRenderer();
			}
			Component headerComp = headerRenderer.getTableCellRendererComponent(table, tableColumn.getHeaderValue(),
					false, false, 0, column);
			preferredWidth = Math.max(preferredWidth, headerComp.getPreferredSize().width);

			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
				Component c = table.prepareRenderer(cellRenderer, row, column);

				int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
				preferredWidth = Math.max(preferredWidth, width);

				// Verifica si está dentro de los límites
				if (preferredWidth >= maxWidth) {
					preferredWidth = maxWidth;
					break;
				}
			}

			tableColumn.setPreferredWidth(preferredWidth);
		}
	}

	//Cardar los datos del archivo
    private void cargarDatosDesdeXML(String filePath) {
        try {
            DOMXML domxml = new DOMXML();
            List<String[]> reservas = domxml.leerReservas(); // Utiliza el nuevo método de DOMXML
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Limpiar la tabla antes de cargar nuevos datos

            for (String[] reserva : reservas) {
                model.addRow(reserva); // Añade cada reserva al modelo de la tabla
            }
            adjustColumnWidths(table);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public static void main(String[] args) {
		try {
			MostrarSalonHabana frame = new MostrarSalonHabana();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
