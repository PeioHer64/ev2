package examenEvalDos.controlador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import examenEvalDos.examen.modelo.Libro;

public class Metodos {
	
	public void buscarTodosLosLibros() {
		//cargarBD
		//mostrar
		Libro[] librosBD = new Libro[0];
		librosBD = cargarBDD(librosBD);
		mostrarLibros(librosBD);

	}
	
	public String buscarLibroPorID(Libro[] libros, Scanner sc) {
		//mostrar
		libros = cargarBDD(libros);
		String testString = "";
		int id = ValidarEntero(sc, 0, 50, "Introduzaca un id");
		int cont = 0;
		Libro libro = new Libro();
		do {
			libro = libros[cont];
			if(libro.getId()==id) {
				mostrarLibro(libro);
				//testString = mostrarLibro(libro);
			}else {
				cont++;
			}
		}while(cont < libros.length && libro.getId()!=id);
		return testString;
	}
	
	public String librosDeUnAutor(Libro[] libros, Scanner sc) {
		//mostrar todos los libros de un autor
		libros = cargarBDD(libros);
		String testString = "";
		String autor = ValidarString(sc, 0, 50, "Introduzaca un autor");
		int cont = 0;
		Libro libro = new Libro();
		do {
			libro = libros[cont];
			if(libro.getAutor().equals(autor)) {
				mostrarLibro(libro);
				//testString = mostrarLibro(libro);
			}else {
				cont++;
			}
		}while(cont < libros.length && !libro.getAutor().equals(autor));
		return testString;
	}
	
	public String librosAntes(Libro[] libros, Scanner sc) {
		//mostrar todos los libros de antes de ...
		libros = cargarBDD(libros);
		String testString = "";
		String fech = "2020-01-01";
		int cont = 0;
		Libro libro = new Libro();
		do {
			libro = libros[cont];
			if(libro.getFechaPublicacion().toString().equals(fech)) {
				mostrarLibro(libro);
				//testString = mostrarLibro(libro);
			}else {
				cont++;
			}
		}while(cont < libros.length && !libro.getFechaPublicacion().toString().equals(fech));
		return testString;
		
	}
	
	public Libro[] anadirLibro(Libro[] libros, Scanner sc) {
Libro libAL = new Libro();
		
		libAL.setId(ValidarEntero(sc, 0, 100, "Introduzca una id"));
		libAL.setTitulo(ValidarString(sc, 0, 20, "Introduzca un Titulo"));
		libAL.setAutor(ValidarString(sc, 0, 20, "Introduzca un Autor"));
		libAL.setEditorial(ValidarString(sc, 0, 20, "Introduzca un Editorial"));
		libAL.setFechaPublicacion(ValidarDate(sc, 0, 20, "Introduzca una fecha"));
		
		libros = reescribirArrayLibros(libros);
		libros[libros.length-1] = libAL;
		//guardarBDD(libros);
		return libros;
		//te lo mete a la bdd
	}
	
	public void guardarTXT(Libro[] libros) {
		libros = cargarBDD(libros);
		String[] stringTxT = new String[0];
		for(int i = 0;i<libros.length;i++){
		stringTxT = reescribirArrayString(stringTxT);
		stringTxT[i] = "Id:"+libros[i].getId()
				+"\n Titulo:"+libros[i].getTitulo()
				+"\n Autor:"+libros[i].getAutor()
				+"\n Editorial:"+libros[i].getEditorial()
				+"\n FechaPubli:"+libros[i].getFechaPublicacion()
				+"\n\n";
		}
		
		File file = new File("Archivos//examen.txt");
		
		BufferedWriter fichero;
		
			try {
				fichero = new BufferedWriter(new FileWriter(file));
			for(int i =0;i<stringTxT.length;i++)
			{
				fichero.write(stringTxT[i].toString());
			}
			fichero.close();
			System.out.println("todo guardado");
			
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	}
	
	public void mostrarTXT(Libro[] libros) {
		//como cargar
		
		libros = cargarTXT();
		mostrarLibros(libros);
	}
	
	public void insertarDeTXTaBD(Libro[] libros) {
		libros = cargarTXT();
		guardarBDD(libros);
		//libros
	}

	
	//metodos extras ****************+**
	
	
	public void guardarBDD(Libro[] libros) {
		//inserts
		try {
			Connection conexion = (Connection) DriverManager.getConnection(conexion1,conexion2,conexion3);
			Object insert = conexion.createStatement();
			for(int x=0;x<libros.length;x++){
			((Statement) insert).executeUpdate("INSERT INTO `t_libros`(`titulo`, `autor`, `editorial`, `fechaPublicacion`) VALUES ('"+libros[x].getTitulo()+"','"+libros[x].getAutor()+"','"+libros[x].getEditorial()+"','"+libros[x].getFechaPublicacion()+"')");
			}
			
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	
	
	public Libro[] cargarTXT() {
		Libro[] libros = new Libro[0];
		
				File file = new File("Archivos//examen.txt");
		BufferedReader fichero;
		try {
			fichero = new BufferedReader(new FileReader(file));
		int v = 0;
		String linea;
		
		int id = 0;
		String titulo = "";
		String autor = "";
		String edit = "";
		Date fecha = null;
		
		while((linea = fichero.readLine())!=null){
//			if (linea.contains("Nombre")){
//				System.out.println(linea);
//				}
			
			if(linea.split(":").length>1){
				
				if (linea.contains("Id")){
					
					id = Integer.valueOf(linea.split(":")[1]);
				}
				if (linea.contains("Titulo")){
					titulo = linea.split(":")[1];
				}
				if (linea.contains("Autor")){
					autor = linea.split(":")[1];
				}
				if (linea.contains("Editorial")){
					edit = linea.split(":")[1];
				}
				if (linea.contains("FechaPubli")){
					fecha = Date.valueOf(linea.split(":")[1]);
				}
				
		
				}else{
					Libro libT = new Libro();
					libT.setId(id);
					libT.setTitulo(titulo);
					libT.setAutor(autor);
					libT.setEditorial(edit);
					libT.setFechaPublicacion(fecha);
					
					libros = reescribirArrayLibros(libros);
					libros[v]= libT;
					v++;
					
					id = 0;
					titulo = "";
					autor = "";
					edit = "";
					fecha = null;
			
				}
				
		}
		System.out.println("todo introducido bien");
		fichero.close();
		
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//System.out.println(arrayMontes[0].getNombre());
		return libros;
	}
	
	private final String libro1 = "id";
	private final String libro2 = "titulo";
	private final String libro3 = "autor";
	private final String libro4 = "editorial";
	private final String libro5 = "fechaPublicacion";
	
	private final String tablaL = "t_libros";
	
	private final String conexion1 = "jdbc:mysql://localhost:3306/t_libros";
	private final String conexion2 = "root";
	private final String conexion3 = "";
	public Libro[] cargarBDD(Libro[] libros) {
Connection conexion;
		
		try {
			conexion=(Connection) DriverManager.getConnection(conexion1,conexion2,conexion3);
			Statement comando=(Statement) conexion.createStatement();
		
			ResultSet registroLibros = comando.executeQuery("select * from "+tablaL+"");
			int i=0;
			while (registroLibros.next()) {
				
				
				Libro lib = new Libro();
				lib.setId(registroLibros.getInt(libro1));
				lib.setTitulo(registroLibros.getString(libro2));
				lib.setAutor(registroLibros.getString(libro3));
				lib.setEditorial(registroLibros.getString(libro4));
				lib.setFechaPublicacion(registroLibros.getDate(libro5));

				libros = reescribirArrayLibros(libros);
				libros[i] = lib;
				i++;
			}
			registroLibros.close();
			
			comando.close();
			conexion.close();
		} catch(SQLException ex){
				ex.printStackTrace();

		}
		
		return libros;
	}
	
	
	
	public Libro[] reescribirArrayLibros(Libro[] arrayViejo) {
		// TODO Auto-generated method stub
		Libro[] arrayNuevo = new Libro[arrayViejo.length+1];
		for(int i =0;i<arrayViejo.length;i++)
		{
			arrayNuevo[i]=arrayViejo[i];
		}
		arrayViejo = arrayNuevo;
		return arrayNuevo;
	}
	
	public String[] reescribirArrayString(String[] arrayViejo) {
		// TODO Auto-generated method stub
		String[] arrayNuevo = new String[arrayViejo.length+1];
		for(int i =0;i<arrayViejo.length;i++)
		{
			arrayNuevo[i]=arrayViejo[i];
		}
		arrayViejo = arrayNuevo;
		return arrayNuevo;
	}
	
	public int ValidarEntero(Scanner sc, int min, int max, String texto) {
		int num = -1;
		boolean error = false;
		do {
			System.out.println(texto);
			String opcS = sc.nextLine();
			try {
				num = Integer.valueOf(opcS);
				if (num < min || num > max)
					error = true;
				else
					error = false;
			} catch (Exception ex) {
				error = true;
			}
		} while (error);
		return num;
	}
	
	public String ValidarString(Scanner sc, int min, int max, String texto) {
		String opcS = "";
		boolean error = false;
		do {
			System.out.println(texto);
			opcS = sc.nextLine();
			try {
				if (opcS.length() < min || opcS.length() > max)
					error = true;
				else
					error = false;
			} catch (Exception ex) {
				error = true;
			}
		} while (error);
		return opcS;
	}
	
	public Date ValidarDate(Scanner sc, int min, int max, String texto) {
		Date dat = null;
		boolean error = false;
		do {
			System.out.println(texto);
			String opcS = sc.nextLine();
			try {
				dat = Date.valueOf(opcS);
				if (dat==null)
					error = true;
				else
					error = false;
			} catch (Exception ex) {
				error = true;
			}
		} while (error);
		return dat;
	}
	
	//del main
	private void mostrarLibros(Libro [] libros) {
		if (null != libros) {
			for (Libro libro : libros)
				mostrarLibro(libro);
		} else {
			System.out.println("No hay libros que mostrar");
		}
	}
	
	
	private void mostrarLibro(Libro libro) {
		if (null != libro) {
			System.out.println("-------------------------------------");
			System.out.println("Id - " + libro.getId());
			System.out.println("Titulo - " + libro.getTitulo());
			System.out.println("Autor - " + libro.getAutor());
			System.out.println("Editorial - " + libro.getEditorial());
			System.out.println("Fecha Publicacion - " + dateToString(libro.getFechaPublicacion()));
			System.out.println("-------------------------------------");
		} else {
			System.out.println("No hay informacion que mostrar");
		}
	}
	
	
	
	private String dateToString(java.util.Date date) {
		String ret = null;
		String pattern = "yyyy/MM/dd";
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		ret = dateFormat.format(date);
		return ret;
	}
	
	
	
}