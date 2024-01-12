package ReservaManager;

import java.sql.*;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class ReservaManager {

	// Método para conectarse a la base de datos
	private Connection conexion() {
		String url = "jdbc:mysql://localhost:3306/tu_base_de_datos";
		String usuario = "tu_usuario";
		String contrasena = "tu_contraseña";
		Connection conexion = null;
		try {
			conexion = DriverManager.getConnection(url, usuario, contrasena);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conexion;
	}

	// Método para añadir una reserva a la base de datos
	public void addReserva(Reserva reserva) {
		String sql = "INSERT INTO reservas(nombre, telefono, fecha_evento, tipo_evento, n_personas, tipo_cocina, n_jornadas, n_habitaciones, tipo_mesa, n_comensales) VALUES(?,?,?,?,?,?,?,?,?,?)";
		try (Connection conexion = this.conexion(); PreparedStatement sentencia = conexion.prepareStatement(sql)) {
			sentencia.setString(1, reserva.getNombre());
			// Setear el resto de los campos
			sentencia.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void procesarJSONFile(String filePath) {
		try {
			JSONObject objeto = new JSONObject(new JSONTokener(new FileReader(filePath)));
			Reserva reserva = new Reserva(objeto.getString("nombre"), objeto.getString("telefono"),
					objeto.getString("fecha_evento"), objeto.getString("tipo_evento"), objeto.getString("n_personas"),
					objeto.getString("tipo_cocina"), objeto.getString("n_jornadas"), objeto.getString("n_habitaciones"),
					objeto.getString("tipo_mesa"), objeto.getString("n_comensales"));
			addReserva(reserva);
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}

	public void procesarXMLFile(String filePath) {
		try {
			File xmlFile = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document documento = dBuilder.parse(xmlFile);
			documento.getDocumentElement().normalize();

			NodeList nLista = documento.getElementsByTagName("reserva");
			for (int i = 0; i < nLista.getLength(); i++) {
				Node nNodo = nLista.item(i);
				if (nNodo.getNodeType() == Node.ELEMENT_NODE) {
					Element elementos = (Element) nNodo;
					Reserva reserva = new Reserva(elementos.getElementsByTagName("nombre").item(0).getTextContent(),
							elementos.getElementsByTagName("telefono").item(0).getTextContent(),
							elementos.getElementsByTagName("fecha_evento").item(0).getTextContent(),
							elementos.getElementsByTagName("tipo_evento").item(0).getTextContent(),
							elementos.getElementsByTagName("n_personas").item(0).getTextContent(),
							elementos.getElementsByTagName("tipo_cocina").item(0).getTextContent(),
							elementos.getElementsByTagName("n_jornadas").item(0).getTextContent(),
							elementos.getElementsByTagName("n_habitaciones").item(0).getTextContent(),
							elementos.getElementsByTagName("tipo_mesa").item(0).getTextContent(),
							elementos.getElementsByTagName("n_comensales").item(0).getTextContent());
					addReserva(reserva);
				}
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

	// Método para obtener reservas de la base de datos
	public List<Reserva> getReservas() {
		List<Reserva> reservas = new ArrayList<>();
		String sql = "SELECT * FROM reservas";
		try (Connection conexion = this.conexion();
				PreparedStatement sentencia = conexion.prepareStatement(sql);
				ResultSet resutado = sentencia.executeQuery()) {

			while (resutado.next()) {
				Reserva reserva = new Reserva(resutado.getString("nombre"), resutado.getString("telefono"),
						resutado.getString("fecha_evento"), resutado.getString("tipo_evento"),
						resutado.getString("n_personas"), resutado.getString("tipo_cocina"),
						resutado.getString("n_jornadas"), resutado.getString("n_habitaciones"),
						resutado.getString("tipo_mesa"), resutado.getString("n_comensales"));
				reservas.add(reserva);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return reservas;
	}

	// Método para exportar reservas a XML o JSON
	public void exportReservas(List<Reserva> reservas, String formato, String salida) throws Exception {
		if (formato.equalsIgnoreCase("XML")) {
			// Exportar a XML
			salida = "Datos.xml";
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			Document documento = documentBuilder.newDocument();
			Element raiz = documento.createElement("reservas");
			documento.appendChild(raiz);

			for (Reserva reserva : reservas) {
				Element reservaElemento = documento.createElement("reserva");
				raiz.appendChild(reservaElemento);

				// Añadir elementos para cada propiedad de Reserva
				reservaElemento.appendChild(createElement(documento, "nombre", reserva.getNombre()));
				reservaElemento.appendChild(createElement(documento, "telefono", reserva.getTelefono()));
				reservaElemento.appendChild(createElement(documento, "fecha_evento", reserva.getFechaEvento()));
				reservaElemento.appendChild(createElement(documento, "tipo_evento", reserva.getTipoEvento()));
				reservaElemento.appendChild(createElement(documento, "n_personas", reserva.getNPersonas()));
				reservaElemento.appendChild(createElement(documento, "tipo_cocina", reserva.getTipoCocina()));
				reservaElemento.appendChild(createElement(documento, "n_jornadas", reserva.getNJornadas()));
				reservaElemento.appendChild(createElement(documento, "n_habitaciones", reserva.getNHabitaciones()));
				reservaElemento.appendChild(createElement(documento, "tipo_mesa", reserva.getTipoMesa()));
				reservaElemento.appendChild(createElement(documento, "n_comensales", reserva.getNComensales()));
			}

			// Guardar documento XML
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformador = transformerFactory.newTransformer();
			DOMSource domOrigen = new DOMSource(documento);
			StreamResult streamResultado = new StreamResult(new File(salida));
			transformador.transform(domOrigen, streamResultado);

		} else if (formato.equalsIgnoreCase("JSON")) {
			// Exportar a JSON
			salida = "Datos.json";
			JSONArray jsonArray = new JSONArray();
			for (Reserva reserva : reservas) {
				JSONObject jsonObjeto = new JSONObject();
				jsonObjeto.put("nombre", reserva.getNombre());
				jsonObjeto.put("telefono", reserva.getTelefono());
				jsonObjeto.put("fecha_evento", reserva.getFechaEvento());
				jsonObjeto.put("tipo_evento", reserva.getTipoEvento());
				jsonObjeto.put("n_personas", reserva.getNPersonas());
				jsonObjeto.put("tipo_cocina", reserva.getTipoCocina());
				jsonObjeto.put("n_jornadas", reserva.getNJornadas());
				jsonObjeto.put("n_habitaciones", reserva.getNHabitaciones());
				jsonObjeto.put("tipo_mesa", reserva.getTipoMesa());
				jsonObjeto.put("n_comensales", reserva.getNComensales());

				jsonArray.put(jsonObjeto);
			}

			try (FileWriter archivo = new FileWriter(salida)) {
				archivo.write(jsonArray.toString(4)); // Indentación para mejor lectura
			}
		} else {
			throw new IllegalArgumentException("Formato no soportado: " + formato);
		}
	}

	// Método auxiliar para crear elementos XML
	private static Node createElement(Document documento, String nombre, String valor) {
		Element nodo = documento.createElement(nombre);
		nodo.appendChild(documento.createTextNode(valor));
		return nodo;
	}
}