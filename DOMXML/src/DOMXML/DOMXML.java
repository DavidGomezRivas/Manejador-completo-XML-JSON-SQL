package DOMXML;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DOMXML {

    // Constructor
    public DOMXML() {
    }

    // Método para cargar o crear el documento XML 'Datos.xml'
    private Document cargarOCrearDocumento() throws ParserConfigurationException, IOException, SAXException {
        File xmlFile = new File("Datos.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        if (xmlFile.exists()) {
            return builder.parse(xmlFile);
        } else {
            Document doc = builder.newDocument();
            Element raiz = doc.createElement("reservas");
            doc.appendChild(raiz);
            return doc;
        }
    }

    // Método para generar y añadir una nueva reserva a 'Datos.xml'
    public void generarYAnadirReserva(String nombre, String fecha, String tipoEvento,
                                      String numPersonas, String tipoCocina, String numJornadas,
                                      String habitaciones, String tipoMesa, String comensalesMesa)
            throws ParserConfigurationException, IOException, SAXException, TransformerException {

        Document doc = cargarOCrearDocumento();

        // Obtener el elemento raíz
        Element raiz = doc.getDocumentElement();

        // Crear el elemento reserva y añadirlo al raíz
        Element reserva = doc.createElement("reserva");
        raiz.appendChild(reserva);

        // Añadir elementos a la reserva
        reserva.appendChild(crearElemento(doc, "nombre", nombre));
        reserva.appendChild(crearElemento(doc, "fechaEvento", fecha));
        reserva.appendChild(crearElemento(doc, "tipo", tipoEvento));
        reserva.appendChild(crearElemento(doc, "asistentes", numPersonas));
        reserva.appendChild(crearElemento(doc, "tipoCocina", tipoCocina));
        reserva.appendChild(crearElemento(doc, "numeroJornadas", numJornadas));
        reserva.appendChild(crearElemento(doc, "habitaciones", habitaciones));
        reserva.appendChild(crearElemento(doc, "tipoMesa", tipoMesa));
        reserva.appendChild(crearElemento(doc, "comensalesMesa", comensalesMesa));

        // Guardar el documento XML en 'Datos.xml'
        crearArchivoXML(doc, "Datos.xml");
    }

    // Método auxiliar para crear elementos del documento
    private Element crearElemento(Document doc, String nombre, String valor) {
        Element elemento = doc.createElement(nombre);
        elemento.appendChild(doc.createTextNode(valor));
        return elemento;
    }

    // Método para guardar el documento XML
    public void crearArchivoXML(Document doc, String nombreArchivo) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource origen = new DOMSource(doc);
        StreamResult resultado = new StreamResult(new File(nombreArchivo));
        transformer.transform(origen, resultado);
    }
    // Método para leer el documento XML
    public List<String[]> leerReservas() throws Exception {
        File xmlFile = new File("Datos.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getElementsByTagName("reserva");

        List<String[]> reservas = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            String nombre = obtenerTextoDeElemento(element, "nombre");
            String fechaEvento = obtenerTextoDeElemento(element, "fechaEvento");
            String tipo = obtenerTextoDeElemento(element, "tipo");
            String asistentes = obtenerTextoDeElemento(element, "asistentes");
            String tipoCocina = obtenerTextoDeElemento(element, "tipoCocina");
            String numeroJornadas = obtenerTextoDeElemento(element, "numeroJornadas");
            String habitaciones = obtenerTextoDeElemento(element, "habitaciones");
            String tipoMesa = obtenerTextoDeElemento(element, "tipoMesa");
            String comensalesMesa = obtenerTextoDeElemento(element, "comensalesMesa");
            reservas.add(new String[] { nombre, fechaEvento, tipo, asistentes, tipoCocina, numeroJornadas, habitaciones, tipoMesa, comensalesMesa });
        }
        return reservas;
    }

    private String obtenerTextoDeElemento(Element elemento, String nombreTag) {
        NodeList nl = elemento.getElementsByTagName(nombreTag);
        if (nl != null && nl.getLength() > 0) {
            Node nodo = nl.item(0);
            if (nodo != null) {
                return nodo.getTextContent();
            }
        }
        return "";
    }
}