/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Natalia
 */

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Scanner;


class Estado_1 {
    
    private byte EActual;
    private String name;
    private String stack;
    private HashMap <Character, HashMap <Character, Estado_1>> tran;

    public Estado_1(String nombre) {
        EActual = 0;
        this.name = nombre;
        tran = new HashMap <Character, HashMap <Character, Estado_1>>();
        stack = "";
    }

    public String getPilacion() {
        return stack;
    }

    public void setPilacion(String pila) {
        stack = pila;
    }

    public String getName() {
        return name;
    }
    
    public void setEstadoInicio() {
        EActual = 1;
    }
    
    public void setEstadoFin() {
        EActual = 2;
    }

    public void setEstadoInicioFin() {
        EActual = 3;
    }

    public byte getEstadoActual() {
        return EActual;
    }

    public void agregarTransicion(char AlfabetoS, char PilaS, Estado_1 Ea, LinkedList < Character > alfabeto) {
        if (tran.get(AlfabetoS) == null) 
        {
          tran.put(AlfabetoS, new HashMap < Character, Estado_1 > ());
          tran.get(AlfabetoS).put(PilaS, Ea);

        if (AlfabetoS == '#') 
        {
          for (char x: alfabeto) {
                    if (tran.get(x) != null && tran.get(x).get(PilaS) != null)
                    {
                        System.out.println("Error encontrado en 7");
                        System.exit(0);
                    }
                }
            } else {
                if (tran.get('#') != null && tran.get('#').get(PilaS) != null) {
                    System.out.println("Error encontrado en 7");
                    System.exit(0);
                }
            }

        } else
            if (tran.get(AlfabetoS).get(PilaS) == null) {
            tran.get(AlfabetoS).put(PilaS, Ea);


        } else
            {
            System.out.println("Error encontrado en 7");
            System.exit(0);
        }
    }

    public Estado_1 getTran(char Alf, char Stack) {
        if (tran.get(Alf) == null){
            return null;
        }
        else if (tran.get(Alf).get(Stack) == null){ 
            return null;
        }
        else{ 
            return tran.get(Alf).get(Stack);
        }
    }
}
class AutomataPdeterminista{
    private LinkedList <String> EIng;
    private LinkedList <Character> CintaAL;
        private LinkedList <Character> simbolosPila;
                   private LinkedList <String> EFinal;
    private LinkedList < String > Trans;
    private String EIn;
    private char PilaSimb;
    public AutomataPdeterminista (LinkedList <String> EIng,LinkedList <Character> CintaAL ,LinkedList <Character> simbolosPila,String EIn,LinkedList <String> EFinal,char PilaSimb,LinkedList < String > Trans){
        this.EIng = EIng;
        this.CintaAL = CintaAL;
        this.simbolosPila = simbolosPila;
        this.EIn = EIn;
        this.EFinal = EFinal;
        this.PilaSimb = PilaSimb;
        this.Trans = Trans;   
    }
        private Stack <Character> Pila; //Stack:Una pila es una lista ordenada o estructura de datos que permite almacenar y recuperar datos

    private LinkedList < Estado_1 > estados;
    private HashMap <Integer, String> pilaMap;

    private boolean read;
    public AutomataPdeterminista()
    {
       this.Pila = new Stack < Character > ();
        this.estados = new LinkedList < > ();
        this.pilaMap = new HashMap < Integer, String > ();
        this.read = false;  
    }
    public void Automata() {
		for (String nombre : EIng) {
			estados.add(new Estado_1(nombre));
			if (nombre.equals(EIn)) {
                            estados.peekLast().setEstadoInicio();
			}
			
			if (EFinal.contains(nombre)) {
				if (estados.peekLast().getEstadoActual() == 1) {
					estados.peekLast().setEstadoInicioFin();
				} else {
					estados.peekLast().setEstadoFin();

				}
			}

		}
		this.ADDtrans();
	}
	
	public void ADDtrans() { 
		Scanner N;
		String name1;
		char alfa;
		char pilaSimbol;
		String namef;
		String aux;
		for (int i=0;i<Trans.size();i++) {
			N = new Scanner(Trans.get(i));
			N.useDelimiter(",|\r\n");
			aux = N.next();
			name1 = aux.substring(1);
			alfa = N.next().charAt(0);
			aux = N.next();
			pilaSimbol = aux.charAt(0);
			namef = aux.substring(2, aux.length());
			aux = N.next();
			aux = aux.substring(0, aux.length()-1);
		
			if (this.getPosicionEstado(name1) == -1 || this.getPosicionEstado(namef) == -1 || (!CintaAL.contains(alfa) && alfa != '#') || !simbolosPila.contains(pilaSimbol)) {
				System.out.println("Error encontrado en 7");
				System.exit(0);
			}
			
			this.estados.get(this.getPosicionEstado(name1)).agregarTransicion(alfa, pilaSimbol, this.estados.get(this.getPosicionEstado(namef)), CintaAL);
			pilaMap.put(namef.length() + pilaSimbol + alfa, aux);
			
			
			
		}
	}
	
	public int getPosicionEstado(String nameresearch) {
		for (int i=0;i<estados.size();i++) {
			if (estados.get(i).getName().equals(nameresearch)) {
				return i;
			}
		}
		return -1;
	}
        public void computar(String palabra) {
		LinkedList<Estado_1> estadosComputados = new LinkedList<Estado_1>();
		Estado_1 estadoTransicion = this.estados.get(this.getPosicionEstado(EIn));
		Pila.push(PilaSimb);
		String porApilar = "";
		System.out.println("." + palabra + " " + estadoTransicion.getName() + " " + Pila.peek());
		estadosComputados.add(estadoTransicion);
		
		while (estadosComputados.peekLast().getTran('#', Pila.peek()) != null) {
			estadoTransicion = estadosComputados.peekLast().getTran('#', Pila.peek());
			porApilar = this.pilaMap.get(estadoTransicion.getName().length() + '#' +Pila.peek());
			Pila.pop();
			if (!porApilar.equals("#")) {
				for (int k=porApilar.length()-1;k>=0;k--) {
					Pila.push(porApilar.charAt(k));
				}
			}
			
			System.out.print("." + palabra + " " + estadoTransicion.getName() + " ");
			for (int k=Pila.size()-1;k>=0;k--) {
				System.out.print(Pila.get(k));
			}
			System.out.println();
			estadosComputados.add(estadoTransicion);
		}
		
		for (int i=0;i<palabra.length();i++) {	
			
			if (estadosComputados.peekLast().getTran(palabra.charAt(i), Pila.peek()) != null) {
				estadoTransicion = estadosComputados.peekLast().getTran(palabra.charAt(i), Pila.peek());
				porApilar = this.pilaMap.get(estadoTransicion.getName().length() + palabra.charAt(i) + Pila.peek());
				Pila.pop();
				if (!porApilar.equals("#")) {
					for (int k=porApilar.length()-1;k>=0;k--) {
						Pila.push(porApilar.charAt(k));
					}
				}
				
				System.out.print(palabra.substring(0, i+1) + "." + palabra.substring(i+1, palabra.length()) + " " + estadoTransicion.getName() + " ");
				for (int k=Pila.size()-1;k>=0;k--) {
					System.out.print(Pila.get(k));
				}
				System.out.println();
				estadosComputados.add(estadoTransicion);
			} else if (estadosComputados.peekLast().getTran('#', Pila.peek()) != null) {
				while (estadosComputados.peekLast().getTran('#', Pila.peek()) != null) {
					estadoTransicion = estadosComputados.peekLast().getTran('#', Pila.peek());
					porApilar = this.pilaMap.get(estadoTransicion.getName().length() + '#' + Pila.peek());
					Pila.pop();
					if (!porApilar.equals("#")) {
						for (int k=porApilar.length()-1;k>=0;k--) {
							Pila.push(porApilar.charAt(k));
						}
					}
					
					System.out.print(palabra.substring(0, i) + "." + palabra.substring(i, palabra.length()) + " " + estadoTransicion.getName() + " ");
					for (int k=Pila.size()-1;k>=0;k--) {
						System.out.print(Pila.get(k));
					}
					System.out.println();
					estadosComputados.add(estadoTransicion);
					
				}
				i--;
			} else {
				System.out.println("Rechazado");
				System.exit(0);
			}
			
			if (i==palabra.length()-1) {
				read = true;
			}
		}
		
		while (estadosComputados.peekLast().getTran('#', Pila.peek()) != null) {
			estadoTransicion = estadosComputados.peekLast().getTran('#', Pila.peek());
			porApilar = this.pilaMap.get(estadosComputados.peekLast().getTran('#', Pila.peek()).getName().length() + '#' + Pila.peek());
			Pila.pop();
			if (!porApilar.equals("#")) {
				for (int k=porApilar.length()-1;k>=0;k--) {
					Pila.push(porApilar.charAt(k));
				}
			}
			
			System.out.print(palabra + ". " + estadoTransicion.getName() + " ");
			for (int k=Pila.size()-1;k>=0;k--) {
				System.out.print(Pila.get(k));
			}
			System.out.println();
			estadosComputados.add(estadoTransicion);
		}
		
		
		if (read && Pila.peek() == PilaSimb && (estadoTransicion.getEstadoActual() == 2 || estadoTransicion.getEstadoActual() == 3 )) {
			System.out.println("Aceptado");
		} else {
			System.out.println("Rechazado");
		}
	}
}


public class NewMain {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        Scanner aux;
        
        LinkedList < Character > caracterNoPermitido = new LinkedList <Character> ();
        caracterNoPermitido.add('#');
        caracterNoPermitido.add('"');
        caracterNoPermitido.add('\'');
        caracterNoPermitido.add(',');
        caracterNoPermitido.add('.');
 LinkedList < String > estados = new LinkedList <String> ();
        String lineaEstados = teclado.nextLine();
        aux = new Scanner(lineaEstados);
 String unCaracter = "";
        while (aux.hasNext()) {
            unCaracter = aux.next();
            if ((!Character.isLetterOrDigit(unCaracter.charAt(0)))) {
                System.out.println("Error encontrado en 1");
                System.exit(0);
            } else {
                estados.add(unCaracter);
            }}
        for (String palabraEstado: estados) {
            for (int i = 0; i < palabraEstado.length(); i++) {
                if (caracterNoPermitido.contains(palabraEstado.charAt(i))) {
                    System.out.println("Error encontrado en 1");
                    System.exit(0);
                }}}
        LinkedList <Character> alfabeto = new LinkedList <Character> ();
        String lineaAlfabeto = teclado.nextLine();
        aux = new Scanner(lineaAlfabeto);
        unCaracter = "";
        while (aux.hasNext()) {
            unCaracter = aux.next();
            if (unCaracter.length() != 1 || (!Character.isLetterOrDigit(unCaracter.charAt(0)))) {
                System.out.println("Error encontrado en 2");
                System.exit(0);
            } else {
                alfabeto.add(unCaracter.charAt(0));
            }
        }
 LinkedList <Character> simbolosPila = new LinkedList <Character> ();
        String lineaPila = teclado.nextLine();
        aux = new Scanner(lineaPila);
 unCaracter = "";
        while (aux.hasNext()) {
            unCaracter = aux.next();
            if (unCaracter.length() != 1) {
                System.out.println("Error encontrado en 3");
                System.exit(0);
            } else {
                simbolosPila.add(unCaracter.charAt(0));
            }
        }
        for (char caracterPila: simbolosPila) {
            if (caracterNoPermitido.contains(caracterPila)) {
                System.out.println("Error encontrado en 3");
                System.exit(0);
            }
        }
        String estadoInicial = teclado.nextLine();
        if (!estados.contains(estadoInicial)) {
            System.out.println("Error encontrado en 4");
            System.exit(0);
        }

        LinkedList <String> estadosFinales = new LinkedList <String> ();
        String lineaEstadosFinales = teclado.nextLine();
        aux = new Scanner(lineaEstadosFinales);
        unCaracter = "";
        while (aux.hasNext()) {
            unCaracter = aux.next();
            estadosFinales.add(unCaracter);
        }
        for (String palabraEstadosFinales: estadosFinales) {
            if (!estados.contains(palabraEstadosFinales)) {
                System.out.println("Error encontrado en 5");
                System.exit(0);
            }
        }
        unCaracter = teclado.nextLine();
        if (unCaracter.length() != 1) {
            System.out.println("Error encontrado en 6");
            System.exit(0);
        }
        char simboloInicialPila = unCaracter.charAt(0);
        if (!simbolosPila.contains(simboloInicialPila)) {
            System.out.println("Error encontrado en 6");
            System.exit(0);
        }
        LinkedList <String> transiciones = new LinkedList <String> ();
        String lineaTransiciones = teclado.nextLine();
        aux = new Scanner(lineaTransiciones);
        while (aux.hasNext()) {
            unCaracter = aux.next();
            transiciones.add(unCaracter);
        }
        String palabra = teclado.nextLine();
        for (int i = 0; i < palabra.length(); i++) {
            if (!alfabeto.contains(palabra.charAt(i))) {
                System.out.println("Error encontrado en 8");
                System.exit(0);
            }
        }
        AutomataPdeterminista beepBoop = new AutomataPdeterminista(estados, alfabeto, simbolosPila, estadoInicial, estadosFinales, simboloInicialPila, transiciones);
        beepBoop.Automata();
        System.out.println("\n");
        beepBoop.computar(palabra);

    }
        
    }
    

