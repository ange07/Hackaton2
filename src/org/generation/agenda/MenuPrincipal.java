package org.generation.agenda;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase principal que contiene el menú de consola (Vista/Controlador).
 * Permite probar todas las funcionalidades de la Agenda.
 */
public class MenuPrincipal {

    public static void main(String[] args) {
        // Usamos try-with-resources para el Scanner (buena práctica)
        try (Scanner sc = new Scanner(System.in)) {

            Agenda agenda = inicializarAgenda(sc);
            int opcion = -1;

            do {
                mostrarMenu();
                try {
                    opcion = sc.nextInt();
                    sc.nextLine(); // Limpiar buffer (consume el \n después del número)

                    switch (opcion) {
                        case 1:
                            gestionarAnadir(sc, agenda);
                            break;
                        case 2:
                            gestionarBuscar(sc, agenda);
                            break;
                        case 3:
                            gestionarModificar(sc, agenda);
                            break;
                        case 4:
                            gestionarEliminar(sc, agenda);
                            break;
                        case 5:
                            gestionarExiste(sc, agenda);
                            break;
                        case 6:
                            agenda.listarContactos();
                            break;
                        case 7:
                            System.out.println("Espacios libres: " + agenda.espacioLibres());
                            break;
                        case 8:
                            if (agenda.agendaLlena()) {
                                System.out.println("La agenda SÍ está llena.");
                            } else {
                                System.out.println("La agenda NO está llena.");
                            }
                            break;
                        case 0:
                            System.out.println("Saliendo del sistema...");
                            break;
                        default:
                            System.out.println("Opción no válida. Intente de nuevo.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Error: Debe ingresar un número.");
                    sc.nextLine(); // Limpiar buffer de entrada incorrecta
                }
                System.out.println(); // Añadir espacio para legibilidad

            } while (opcion != 0);

        } // El Scanner se cierra automáticamente aquí
    }

    /**
     * Lógica para crear la agenda al inicio (req: dos formas de crear).
     */
    private static Agenda inicializarAgenda(Scanner sc) {
        System.out.println("--- Creación de Agenda ---");
        System.out.print("¿Desea un tamaño específico (S/N)? (Default es 10): ");
        String resp = sc.nextLine();

        if (resp.equalsIgnoreCase("S")) {
            System.out.print("Ingrese el tamaño: ");
            try {
                int tam = Integer.parseInt(sc.nextLine()); // Usar parseInt y leer línea completa
                if (tam > 0) {
                    System.out.println("Agenda creada con capacidad " + tam);
                    return new Agenda(tam);
                } else {
                    System.out.println("El tamaño debe ser positivo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. No es un número.");
            }
        }
        System.out.println("Usando capacidad por defecto (10).");
        return new Agenda(); // Constructor por defecto
    }

    private static void mostrarMenu() {
        System.out.println("======= MENÚ AGENDA =======");
        System.out.println("1. Añadir Contacto");
        System.out.println("2. Buscar Contacto (por Nombre y Apellido)");
        System.out.println("3. Modificar Teléfono");
        System.out.println("4. Eliminar Contacto");
        System.out.println("5. Verificar si Existe Contacto");
        System.out.println("6. Listar Contactos (Ordenados)");
        System.out.println("7. Ver Espacios Libres");
        System.out.println("8. Verificar si Agenda está Llena");
        System.out.println("0. Salir");
        System.out.print("Elija una opción: ");
    }

    private static void gestionarAnadir(Scanner sc, Agenda agenda) {
        System.out.print("Ingrese Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Ingrese Apellido: ");
        String apellido = sc.nextLine();
        System.out.print("Ingrese Teléfono: ");
        String telefono = sc.nextLine();

        Contacto nuevo = new Contacto(nombre, apellido, telefono);
        agenda.anadirContacto(nuevo);
    }

    private static void gestionarBuscar(Scanner sc, Agenda agenda) {
        System.out.print("Ingrese Nombre a buscar: ");
        String nombre = sc.nextLine();
        System.out.print("Ingrese Apellido a buscar: ");
        String apellido = sc.nextLine();
        agenda.buscaContacto(nombre, apellido);
    }

    private static void gestionarModificar(Scanner sc, Agenda agenda) {
        System.out.print("Ingrese Nombre del contacto a modificar: ");
        String nombre = sc.nextLine();
        System.out.print("Ingrese Apellido del contacto a modificar: ");
        String apellido = sc.nextLine();
        System.out.print("Ingrese el NUEVO Teléfono: ");
        String nuevoTelefono = sc.nextLine();
        agenda.modificarTelefono(nombre, apellido, nuevoTelefono);
    }

    private static void gestionarEliminar(Scanner sc, Agenda agenda) {
        System.out.print("Ingrese Nombre del contacto a eliminar: ");
        String nombre = sc.nextLine();
        System.out.print("Ingrese Apellido del contacto a eliminar: ");
        String apellido = sc.nextLine();

        // Creamos un "dummy" solo con nombre/apellido para la búsqueda
        Contacto dummy = new Contacto(nombre, apellido, "");
        agenda.eliminarContacto(dummy);
    }

    private static void gestionarExiste(Scanner sc, Agenda agenda) {
        System.out.print("Ingrese Nombre a verificar: ");
        String nombre = sc.nextLine();
        System.out.print("Ingrese Apellido a verificar: ");
        String apellido = sc.nextLine();

        Contacto dummy = new Contacto(nombre, apellido, "");
        if (agenda.existeContacto(dummy)) {
            System.out.println("-> El contacto SÍ existe en la agenda.");
        } else {
            System.out.println("-> El contacto NO existe en la agenda.");
        }
    }
}
