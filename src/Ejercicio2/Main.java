package Ejercicio2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map.Entry;

public class Main {	
	static Map<String, Map<String, Integer>> usuarios = new TreeMap<String, Map<String, Integer>>(); 
	private static String ruta = null;
	private static int duracion;
	
	public static void leerFichero() throws IOException {
		try {
			FileReader fl = new FileReader(ruta);
			BufferedReader b = new BufferedReader(fl);
			String linea;
			while((linea = b.readLine()) != null) {
				String[] partes = linea.split(" ");
				String parte1 = partes[0];
				String parte2 = partes[1];
				int parte3 = Integer.parseInt(partes[2]);
				if (usuarios.containsKey(parte2)) {
					usuarios.get(parte2).put(parte1, parte3);
					if (usuarios.get(parte2).containsKey(parte1)) {
						usuarios.get(parte2).put(parte1, parte3);
					}
				}
				else {
					usuarios.put(parte2, new TreeMap<>());
					usuarios.get(parte2).put(parte1, parte3);
				}
			}
			b.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		Scanner teclado = new Scanner(System.in);
		System.out.print("Introduce la ruta: ");
		try {
			ruta = teclado.skip("[a-zA-Z]:/(\\w+/)*\\w*\\.*\\w+").match().group();
			try {
			leerFichero();
		}catch(FileNotFoundException e) {
			System.out.println("No se ha podido leer el archivo");
		}
		}catch(NoSuchElementException e) {
			System.out.println("La ruta no es valida");
		}		

		Iterator<Map.Entry<String, Map<String, Integer>>> mapa = usuarios.entrySet().iterator();
		while (mapa.hasNext()) {			
			Map.Entry<String, Map<String, Integer>> entrada1 = mapa.next();		
			String nombreUsuario = entrada1.getKey();
			System.out.print(nombreUsuario + ": ");
			Iterator<Entry<String, Integer>> mapa2 = usuarios.get(nombreUsuario).entrySet().iterator();
			while (mapa2.hasNext()) {
				Map.Entry<String, Integer> ips = mapa2.next();
				if(ips.getKey() == ips.getKey())
					duracion = ips.getValue() + ips.getValue();
				if (mapa2.hasNext()) {
					System.out.print(duracion + " " + ips.getKey() + ", ");
				}
				else {
					System.out.print(duracion + " " + ips.getKey());
					System.out.println();
				}
			}
		}
		
	}

}
