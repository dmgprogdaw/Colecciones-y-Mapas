package Ejercicio2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TreeMap;


public class Main {	
	static Map<String, Map<Integer, List<String>>> usuarios = new TreeMap<String, Map<Integer, List<String>>>();
	static List<String> ips = new ArrayList<>();
	private static String ruta = null;
	private static Scanner teclado = new Scanner(System.in);

	public static void leerFichero() throws IOException {
		try {
			FileReader fl = new FileReader(ruta);
			BufferedReader b = new BufferedReader(fl);
			String linea = null;
			int duracionSesion, numLineas = 0, totalDuracion = 0;
			while((linea = b.readLine()) != null) {
				try {
					numLineas++;
					String[] partes = linea.split(" ");
					duracionSesion = Integer.parseInt(partes[2]);
					Scanner comprobar = new Scanner(linea);
					int estado = 0;	
					while (estado != 2) {	
						switch(estado) {
							case 0:
								try {
									linea = comprobar.skip("(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\s*\\p{L}+\\s*\\d+").match().group();
									estado = 1;
								}catch(NoSuchElementException e) {
									System.out.println("Hay un error en la linea " + numLineas);
									estado = 2;
								}
								break;
							case 1:		
								ips.add(partes[0]);
								usuarios.put(partes[1], new TreeMap<>());
									if (usuarios.containsKey(partes[1])) {
									totalDuracion = totalDuracion + duracionSesion;
									usuarios.get(partes[1]).put(totalDuracion, ips);
								}
								else {
									totalDuracion = totalDuracion + duracionSesion;
									usuarios.get(partes[1]).put(totalDuracion, ips);
								}
								estado = 2;
								break;
						}
					}
					comprobar.close();
				}catch(NumberFormatException e) {
					System.out.println("Hay un error en la linea " + numLineas);
				}
			}
			b.close();
		} catch (FileNotFoundException e) {
			System.out.println("La ruta introducida no es valida.");
		}
	}

	public static void main(String[] args) throws IOException {	
		System.out.print("Introduce la ruta: ");
		ruta = teclado.skip("[a-zA-Z]:/(\\w+/)*\\w*\\.*\\w+").match().group();				
		leerFichero();

		Iterator<Map.Entry<String, Map<Integer, List<String>>>> mapa = usuarios.entrySet().iterator();
		while (mapa.hasNext()) {			
			Map.Entry<String, Map<Integer, List<String>>> entrada1 = mapa.next();		
			String nombreUsuario = entrada1.getKey();
			System.out.print(nombreUsuario + ": ");

			Iterator<Entry<Integer, List<String>>> mapa2 = usuarios.get(nombreUsuario).entrySet().iterator();
			while (mapa2.hasNext()) {
				Map.Entry<Integer, List<String>> entrada2 = mapa2.next();

				System.out.print(entrada2.getKey() + " " + entrada2.getValue());
			}
			System.out.println();
		}	
	}
}