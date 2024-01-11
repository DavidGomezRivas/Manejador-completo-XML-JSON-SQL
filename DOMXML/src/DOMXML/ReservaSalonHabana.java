package DOMXML;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

@SuppressWarnings("serial")
public class ReservaSalonHabana extends JFrame {
	private JTextField textFieldNombre;
	private JTextField textFieldTelefono;
	private JSpinner spinnerFecha;
	private JComboBox<String> comboBoxTipoEvento;
	private JTextField textFieldNumPersonas;
	private JComboBox<String> comboBoxTipoCocina;
	private JTextField textFieldNumJornadas;
	private JCheckBox checkBoxHabitaciones;
	private JRadioButton radioButtonRectangular;
	private JRadioButton radioButtonRedonda;
	private JTextField textFieldComensalesPorMesa;
	private JButton btnReservar;
	private JLabel lblNumJornadas;
	private JLabel lblComensalesPorMesa;
	private JLabel lblCuantasHabitaciones;
	private JTextField textFieldCuantasHabitaciones;

	public ReservaSalonHabana() {
		getContentPane().setBackground(new Color(255, 160, 122));
		setTitle("Reserva Salón Habana");
		setSize(300, 440);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		// Área de Información de Contacto
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(10, 10, 80, 25);
		getContentPane().add(lblNombre);

		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(100, 10, 165, 25);
		getContentPane().add(textFieldNombre);

		JLabel lblTelefono = new JLabel("Teléfono:");
		lblTelefono.setBounds(10, 40, 80, 25);
		getContentPane().add(lblTelefono);

		textFieldTelefono = new JTextField();
		textFieldTelefono.setBounds(100, 40, 165, 25);
		getContentPane().add(textFieldTelefono);

		// Detalles del Evento
		JLabel lblFecha = new JLabel("Fecha:");
		lblFecha.setBounds(10, 70, 80, 25);
		getContentPane().add(lblFecha);

		spinnerFecha = new JSpinner(new SpinnerDateModel());
		spinnerFecha.setBounds(100, 70, 165, 25);
		JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinnerFecha, "dd/MM/yyyy");
		spinnerFecha.setEditor(dateEditor);
		getContentPane().add(spinnerFecha);

		JLabel lblTipoEvento = new JLabel("Tipo de Evento:");
		lblTipoEvento.setBounds(10, 100, 100, 25);
		getContentPane().add(lblTipoEvento);

		comboBoxTipoEvento = new JComboBox<>();
		comboBoxTipoEvento.setModel(new DefaultComboBoxModel<>(new String[] { "Banquete", "Jornada", "Congreso" }));
		comboBoxTipoEvento.setBounds(100, 100, 165, 25);
		comboBoxTipoEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gestionarVisibilidadComponentes();
			}
		});
		getContentPane().add(comboBoxTipoEvento);

		JLabel lblNumPersonas = new JLabel("Nº Personas:");
		lblNumPersonas.setBounds(10, 130, 80, 25);
		getContentPane().add(lblNumPersonas);

		textFieldNumPersonas = new JTextField();
		textFieldNumPersonas.setBounds(100, 130, 165, 25);
		getContentPane().add(textFieldNumPersonas);

		JLabel lblTipoCocina = new JLabel("Tipo de Cocina:");
		lblTipoCocina.setBounds(10, 160, 100, 25);
		getContentPane().add(lblTipoCocina);

		comboBoxTipoCocina = new JComboBox<>();
		comboBoxTipoCocina
				.setModel(new DefaultComboBoxModel<>(new String[] { "Bufé", "Carta", "Cita con chef", "No precisa" }));
		comboBoxTipoCocina.setBounds(100, 160, 165, 25);
		getContentPane().add(comboBoxTipoCocina);

		// Detalles Específicos del Evento
		lblNumJornadas = new JLabel("Nº Jornadas:");
		lblNumJornadas.setBounds(10, 190, 80, 25);
		getContentPane().add(lblNumJornadas);

		textFieldNumJornadas = new JTextField();
		textFieldNumJornadas.setBounds(100, 190, 165, 25);
		getContentPane().add(textFieldNumJornadas);

		checkBoxHabitaciones = new JCheckBox("Necesita Habitaciones");
		checkBoxHabitaciones.setBounds(10, 220, 165, 25);
		getContentPane().add(checkBoxHabitaciones);

		radioButtonRectangular = new JRadioButton("Mesa Rectangular");
		radioButtonRectangular.setBounds(10, 250, 150, 25);
		getContentPane().add(radioButtonRectangular);

		radioButtonRedonda = new JRadioButton("Mesa Redonda");
		radioButtonRedonda.setBounds(170, 250, 150, 25);
		getContentPane().add(radioButtonRedonda);

		ButtonGroup groupMesas = new ButtonGroup();
		groupMesas.add(radioButtonRectangular);
		groupMesas.add(radioButtonRedonda);

		lblComensalesPorMesa = new JLabel("Comensales por Mesa:");
		lblComensalesPorMesa.setBounds(10, 280, 150, 25);
		getContentPane().add(lblComensalesPorMesa);

		textFieldComensalesPorMesa = new JTextField();
		textFieldComensalesPorMesa.setBounds(170, 280, 95, 25);
		getContentPane().add(textFieldComensalesPorMesa);

		lblCuantasHabitaciones = new JLabel("¿Cuántas habitaciones necesitas?");
		lblCuantasHabitaciones.setBounds(10, 300, 210, 25);
		lblCuantasHabitaciones.setVisible(false);
		getContentPane().add(lblCuantasHabitaciones);

		textFieldCuantasHabitaciones = new JTextField();
		textFieldCuantasHabitaciones.setBounds(230, 300, 50, 25);
		textFieldCuantasHabitaciones.setVisible(false);
		getContentPane().add(textFieldCuantasHabitaciones);

		// Botón de Reserva
		btnReservar = new JButton("Reservar");
		btnReservar.setBounds(10, 350, 255, 30);

		btnReservar.addActionListener(new ActionListener() {
			//Metodo para leer los datos de los campos correspondientes
			public void actionPerformed(ActionEvent e) {
				try {
	            DOMXML domxml = new DOMXML();
	            String nombre = textFieldNombre.getText();
	            String telefono = textFieldTelefono.getText();
	            String fecha = dateEditor.getFormat().format(spinnerFecha.getValue());
	            String tipoEvento = (String) comboBoxTipoEvento.getSelectedItem();
	            String numPersonas = textFieldNumPersonas.getText();
	            String tipoCocina = (String) comboBoxTipoCocina.getSelectedItem();
	            String numJornadas = textFieldNumJornadas.getText();
	            boolean necesitaHabitaciones = checkBoxHabitaciones.isSelected();
	            String tipoMesa = radioButtonRectangular.isSelected() ? "Rectangular" : "Redonda";
	            String comensalesPorMesa = textFieldComensalesPorMesa.getText();
	            String cuantasHabitaciones = textFieldCuantasHabitaciones.getText();
	
	            // Añadir los campos correspondientes
	            domxml.generarYAnadirReserva(nombre, telefono, fecha, tipoEvento, numPersonas, tipoCocina, 
	                                      numJornadas, tipoMesa, comensalesPorMesa);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
});
		getContentPane().add(btnReservar);

		inicializarVisibilidadComponentes();

		// Añadir Action Listener al JComboBox y checkBoxHabitaciones
		comboBoxTipoEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gestionarVisibilidadComponentes();
			}
		});

		checkBoxHabitaciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gestionarVisibilidadComponentes();
			}
		});
	}

	private void inicializarVisibilidadComponentes() {
		lblNumJornadas.setVisible(false);
		textFieldNumJornadas.setVisible(false);
		checkBoxHabitaciones.setVisible(false);
		radioButtonRectangular.setVisible(false);
		radioButtonRedonda.setVisible(false);
		lblComensalesPorMesa.setVisible(false);
		textFieldComensalesPorMesa.setVisible(false);
	}

	private void gestionarVisibilidadComponentes() {
		String tipoEvento = (String) comboBoxTipoEvento.getSelectedItem();
		boolean esCongreso = "Congreso".equals(tipoEvento);
		boolean esBanquete = "Banquete".equals(tipoEvento);
		boolean necesitaHabitaciones = checkBoxHabitaciones.isSelected();

		lblNumJornadas.setVisible(esCongreso);
		textFieldNumJornadas.setVisible(esCongreso);
		checkBoxHabitaciones.setVisible(esCongreso);

		lblComensalesPorMesa.setVisible(esBanquete);
		textFieldComensalesPorMesa.setVisible(esBanquete);
		radioButtonRectangular.setVisible(esBanquete);
		radioButtonRedonda.setVisible(esBanquete);
		lblCuantasHabitaciones.setVisible(esCongreso && necesitaHabitaciones);
		textFieldCuantasHabitaciones.setVisible(esCongreso && necesitaHabitaciones);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReservaSalonHabana frame = new ReservaSalonHabana();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
