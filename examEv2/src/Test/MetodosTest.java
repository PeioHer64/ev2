package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import examenEvalDos.controlador.Metodos;
import examenEvalDos.examen.modelo.Libro;

class MetodosTest {
	Metodos mts = new Metodos();
	//
	
	@Test
	void test_validacion() {
		String nombre = "4\n";
		InputStream in = new ByteArrayInputStream(nombre.getBytes());
		System.setIn(in);
		Scanner sc = new Scanner(System.in);
		
		mts.ValidarEntero(sc, 0, 100, "Introduzca una id");
	}
	
	@Test
	void test_buscarTodosLosLibros() {
		mts.buscarTodosLosLibros();
	}

	@Test
	void test_buscarLibroporID() {
		Libro[] libros = new Libro[0];
		String nombre = "2\n";
		InputStream in = new ByteArrayInputStream(nombre.getBytes());
		System.setIn(in);
		Scanner sc = new Scanner(System.in);
		
		mts.buscarLibroPorID(libros, sc);
	}
	
	@Test
	void test_anadirLibro() {
		//
	}
	
	@Test
	void test_GuardarTXT() {
		Libro[] libros = new Libro[0];
		mts.guardarTXT(libros);
	}
	
	@Test
	void test_mostarTXT() {
		Libro[] libros = new Libro[0];
		libros = mts.cargarBDD(libros);
		mts.mostrarTXT(libros);
	}
	
	
	
	
}
