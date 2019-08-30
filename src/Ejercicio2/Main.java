package Ejercicio2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main {	
	static Map<String, Main> usuarios = new TreeMap<String, Main>();
	private static String ruta = null;
	private static Scanner teclado = new Scanner(System.in);
	private int duracion;
    private Set<String> ips;
	
    public Main(String ip, int duracion) {
        this.duracion = duracion;
        this.ips = new TreeSet<String>();

        this.ips.add(ip);
    }

    public void nuevaEntrada(String ip, int duracion) {
        this.duracion += duracion;
        if (!this.ips.contains(ip)) {
            this.ips.add(ip);
        }
    }

    public void imprimir() {
        System.out.print(this.duracion);
        System.out.print(" ");
        System.out.print(ips);
    }
	
	
	public static void leerFichero() throws IOException {
		try {
			FileReader fl = new FileReader(ruta);
			BufferedReader b = new BufferedReader(fl);
			String linea = null, ip, usuario;
			int duracionSesion, numLineas = 0;
			while((linea = b.readLine()) != null) {
				numLineas++;
				String[] partes = linea.split(" ");
				ip = partes[0];
				usuario = partes[1];
				duracionSesion = Integer.parseInt(partes[2]);
				try {								
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
								if (usuarios.containsKey(usuario)) {
			                        usuarios.get(usuario).nuevaEntrada(ip, duracionSesion);
			                    } else {
			                        usuarios.put(usuario, new Main(ip, duracionSesion));
			                    }
								estado = 2;
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

		Iterator<Map.Entry<String, Main>> mapa = usuarios.entrySet().iterator();
		while (mapa.hasNext()) {			
			Map.Entry<String, Main> entrada1 = mapa.next();		
			String nombreUsuario = entrada1.getKey();
			System.out.print(nombreUsuario + ": ");
			entrada1.getValue().imprimir();
			System.out.println();
		}	
	}
}