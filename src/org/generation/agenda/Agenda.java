package org.generation.agenda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Gestiona la lógica de negocio de la agenda.
 * Utiliza un HashSet para eficiencia y manejo automático de duplicados.
 */
public class Agenda {

    private Set<Contacto> contactos;
    private int capacidadMaxima;

    // Tamaño por defecto si no se especifica
    private static final int CAPACIDAD_DEFAULT = 10;

    /**
     * Constructor por defecto (tamaño 10).
     */
    public Agenda() {
        this(CAPACIDAD_DEFAULT); // Llama al otro constructor
    }

    /**
     * Constructor con capacidad específica.
     */
    public Agenda(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
        this.contactos = new HashSet<>(capacidadMaxima);
    }

    // --- Métodos de Funcionalidad ---

    /**
     * Añade un contacto validando espacio, nulos y duplicados.
     */
    public void anadirContacto(Contacto c) {
        // Validación 1: Nombre o apellido vacíos
        if (c.getNombre() == null || c.getNombre().trim().isEmpty() ||
            c.getApellido() == null || c.getApellido().trim().isEmpty()) {
            System.out.println("Error: No se puede añadir contacto con nombre o apellido vacíos.");
            return;
        }

        // Validación 2: Agenda llena
        if (agendaLlena()) {
            System.out.println("Error: La agenda está llena. No se pudo añadir a " + c.getNombre() + " " + c.getApellido());
            return;
        }

        // Validación 3: Duplicados (HashSet lo hace automático)
        // .add() devuelve 'false' si el elemento ya existía (según equals/hashCode)
        boolean anadido = this.contactos.add(c);

        if (anadido) {
            System.out.println("Contacto '" + c.getNombre() + " " + c.getApellido() + "' añadido.");
        } else {
            System.out.println("Error: El contacto '" + c.getNombre() + " " + c.getApellido() + "' ya existe.");
        }
    }

    /**
     * Verifica si un contacto existe (basado en nombre y apellido).
     * Complejidad O(1) gracias a HashSet.
     */
    public boolean existeContacto(Contacto c) {
        // .contains() usa internamente equals() y hashCode()
        return this.contactos.contains(c);
    }

    /**
     * Muestra todos los contactos, ordenados alfabéticamente.
     */
    public void listarContactos() {
        if (contactos.isEmpty()) {
            System.out.println("La agenda está vacía.");
            return;
        }

        System.out.println("--- Lista de Contactos (" + contactos.size() + "/" + capacidadMaxima + ") ---");

        // Convertimos el Set a ArrayList para poder ordenarlo
        ArrayList<Contacto> listaOrdenada = new ArrayList<>(contactos);
        Collections.sort(listaOrdenada); // Usa el compareTo() de Contacto

        // Imprimimos cada contacto
        for (Contacto c : listaOrdenada) {
            System.out.println(c);
        }
    }

    /**
     * Busca un contacto por nombre y apellido y muestra su teléfono.
     */
    public void buscaContacto(String nombre, String apellido) {
        // Creamos un "dummy" para buscar, solo necesitamos nombre y apellido
        Contacto aBuscar = new Contacto(nombre, apellido, "");

        // Buscamos con un bucle for tradicional
        for (Contacto c : contactos) {
            if (c.equals(aBuscar)) {
                System.out.println("Teléfono de " + nombre + " " + apellido + ": " + c.getTelefono());
                return;
            }
        }

        System.out.println("Contacto '" + nombre + " " + apellido + "' no encontrado.");
    }

    /**
     * Elimina un contacto (buscado por nombre y apellido).
     * Complejidad O(1) gracias a HashSet.
     */
    public void eliminarContacto(Contacto c) {
        // .remove() usa equals() y hashCode() para encontrar el objeto
        boolean eliminado = this.contactos.remove(c);

        if (eliminado) {
            System.out.println("Contacto '" + c.getNombre() + " " + c.getApellido() + "' eliminado.");
        } else {
            System.out.println("Contacto '" + c.getNombre() + " " + c.getApellido() + "' no se encontró.");
        }
    }

    /**
     * Modifica el teléfono de un contacto existente.
     */
    public void modificarTelefono(String nombre, String apellido, String nuevoTelefono) {
        Contacto aBuscar = new Contacto(nombre, apellido, "");

        // Buscamos con un bucle for tradicional
        for (Contacto c : contactos) {
            if (c.equals(aBuscar)) {
                c.setTelefono(nuevoTelefono);
                System.out.println("Teléfono de '" + nombre + " " + apellido + "' actualizado.");
                return;
            }
        }

        System.out.println("Contacto '" + nombre + " " + apellido + "' no encontrado, no se pudo modificar.");
    }

    /**
     * Devuelve true si la agenda está llena, false en caso contrario.
     */
    public boolean agendaLlena() {
        return this.contactos.size() >= this.capacidadMaxima;
    }

    /**
     * Devuelve el número de espacios libres.
     */
    public int espacioLibres() {
        return this.capacidadMaxima - this.contactos.size();
    }
}
