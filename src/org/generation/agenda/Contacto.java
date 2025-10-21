package org.generation.agenda;

import java.util.Objects;

/**
 * Representa un Contacto.
 * Implementa Comparable para permitir la ordenación alfabética (req: listarContactos).
 * Sobrescribe equals() y hashCode() para la regla de negocio (req: duplicados).
 */
public class Contacto implements Comparable<Contacto> {

    private String nombre;
    private String apellido;
    private String telefono;

    public Contacto(String nombre, String apellido, String telefono) {
        // Se prefiere validar los nulos/vacíos en la clase Agenda (capa de servicio)
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
    }

    // --- Getters y Setters ---
    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    // --- Métodos de Negocio Clave ---

    /**
     * Define la igualdad basada en nombre y apellido (ignorando mayúsculas).
     * Esto es polimorfismo, sobrescribiendo el método de la clase Object.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contacto contacto = (Contacto) o;

        // Compara nombre y apellido ignorando mayúsculas
        boolean nombreIgual = (nombre != null && nombre.equalsIgnoreCase(contacto.nombre));
        boolean apellidoIgual = (apellido != null && apellido.equalsIgnoreCase(contacto.apellido));

        return nombreIgual && apellidoIgual;
    }

    /**
     * Requerido si se sobrescribe equals().
     * Debe generar el mismo hash para objetos "iguales".
     * Usamos los valores en minúsculas para ser consistentes con equalsIgnoreCase().
     */
    @Override
    public int hashCode() {
        String hashNombre = (nombre != null) ? nombre.toLowerCase() : null;
        String hashApellido = (apellido != null) ? apellido.toLowerCase() : null;
        return Objects.hash(hashNombre, hashApellido);
    }

    /**
     * Define el formato de impresión (req: listarContactos).
     */
    @Override
    public String toString() {
        // Formato: Nombre Apellido - Teléfono
        return String.format("%s %s - %s", this.nombre, this.apellido, this.telefono);
    }

    /**
     * Define el orden natural (req: listarContactos alfabéticamente).
     */
    @Override
    public int compareTo(Contacto o) {
        // Compara primero por nombre (ignorando mayúsculas)
        int compNombre = this.nombre.compareToIgnoreCase(o.nombre);
        if (compNombre != 0) {
            return compNombre;
        }
        // Si los nombres son iguales, compara por apellido (ignorando mayúsculas)
        return this.apellido.compareToIgnoreCase(o.apellido);
    }
}

