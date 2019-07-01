package Ejercicio2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TreeMap;


public class Main {
	static Map<String, Map<Integer, List>> usuarios = new TreeMap<String, Map<Integer, List>>();
	static List ips = new ArrayList();
	private static String ruta = null;
	static Scanner teclado = new Scanner(System.in);
	
	public static void leerFichero() throws IOException {
		try {
			FileReader fl = new FileReader(ruta);
			BufferedReader b = new BufferedReader(fl);
			Scanner comprobar = new Scanner(b);
			String parte1, parte2;
			int parte3;
			String linea;
			int numLineas = 0;
			while((linea = b.readLine()) != null) {
				int duracion = 0;
				String[] partes = linea.split(" ");				
				parte1 = partes[0];
				parte2 = partes[1];
				parte3 = Integer.parseInt(partes[2]);
				usuarios.put(parte2, new TreeMap<>());
				if (usuarios.containsKey(parte2)) {
					ips.add(parte1);
					duracion = duracion + parte3;
					usuarios.get(parte2).put(duracion, ips);
				}		
			}
			b.close();
		} catch (FileNotFoundException e) {
			System.out.println("La ruta no es valida");
		}
	}
	
	public static void main(String[] args) throws IOException {
		System.out.print("Introduce la ruta: ");
		ruta = teclado.skip("[a-zA-Z]:/(\\w+/)*\\w*\\.*\\w+").match().group();
		
		leerFichero();		
	

		Iterator<Map.Entry<String, Map<Integer, List>>> mapa = usuarios.entrySet().iterator();
		while (mapa.hasNext()) {			
			Map.Entry<String, Map<Integer, List>> entrada1 = mapa.next();		
			String nombreUsuario = entrada1.getKey();
			System.out.print(nombreUsuario + ": ");
			
			Iterator<Entry<Integer, List>> mapa2 = usuarios.get(nombreUsuario).entrySet().iterator();
			while (mapa2.hasNext()) {
				Map.Entry<Integer, List> entrada2 = mapa2.next();
				
				System.out.print(entrada2.getKey());
				System.out.print(entrada2.getValue());
			}
			System.out.println();
		}
		
	}

}
