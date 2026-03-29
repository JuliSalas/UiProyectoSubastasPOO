package cr.ac.ucenfotec.ui;

import cr.ac.ucenfotec.bl.entities.ObjetoSubastable;
import cr.ac.ucenfotec.bl.entities.Oferta;
import cr.ac.ucenfotec.bl.entities.Subasta;
import cr.ac.ucenfotec.bl.entities.Usuario;
import cr.ac.ucenfotec.bl.logic.AdminSistema;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Clase principal del sistema.
 * Se encarga de mostrar el menú de consola y comunicarse con la capa lógica.
 */
public class Main {

    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final AdminSistema sistema = AdminSistema.obtenerInstancia();

    /**
     * Método principal del programa.
     */
    public static void main(String[] args) throws IOException {
        verificarModeradorInicial();
        ejecutarSistema();
    }

    /**
     * Verifica si existe al menos un moderador registrado al iniciar el sistema.
     */
    private static void verificarModeradorInicial() throws IOException {
        if (!sistema.existeModerador()) {
            System.out.println("No existe moderador registrado. Debe registrarse uno.");

            String nombre = leerTexto("Nombre completo: ");
            String id = leerTexto("Identificacion: ");
            LocalDate fechaNacimiento = LocalDate.parse(leerTexto("Fecha nacimiento (YYYY-MM-DD): "));
            String contrasena = leerTexto("Contrasena: ");
            String correo = leerTexto("Correo electronico: ");

            System.out.println(sistema.registrarModerador(nombre, id, fechaNacimiento, contrasena, correo));
        }
    }

    /**
     * Ejecuta el menú principal.
     */
    private static void ejecutarSistema() throws IOException {
        int opcion;

        do {
            mostrarMenu();
            opcion = Integer.parseInt(leerTexto("Seleccione una opcion: "));

            switch (opcion) {
                case 1:
                    registrarUsuario();
                    break;
                case 2:
                    listarUsuarios();
                    break;
                case 3:
                    registrarObjeto();
                    break;
                case 4:
                    listarObjetos();
                    break;
                case 5:
                    crearSubasta();
                    break;
                case 6:
                    listarSubastas();
                    break;
                case 7:
                    crearOferta();
                    break;
                case 8:
                    listarOfertas();
                    break;
                case 9:
                    iniciarSesion();
                    break;
                case 10:
                    validarModerador();
                    break;
                case 11:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }

        } while (opcion != 11);
    }

    /**
     * Muestra el menú principal.
     */
    private static void mostrarMenu() {
        System.out.println("\nSISTEMA DE SUBASTAS");
        System.out.println("1. Registrar usuario");
        System.out.println("2. Listar usuarios");
        System.out.println("3. Registrar objeto");
        System.out.println("4. Listar objetos");
        System.out.println("5. Crear subasta");
        System.out.println("6. Listar subastas");
        System.out.println("7. Crear oferta");
        System.out.println("8. Listar ofertas");
        System.out.println("9. Inicio de sesion");
        System.out.println("10. Validar existencia de moderador");
        System.out.println("11. Salir");
    }

    /**
     * Registra un nuevo usuario.
     */
    private static void registrarUsuario() throws IOException {
        System.out.println("\nTipo de usuario:");
        System.out.println("1. Vendedor");
        System.out.println("2. Coleccionista");

        int tipo = Integer.parseInt(leerTexto("Seleccione una opcion: "));

        String nombre = leerTexto("Nombre completo: ");
        String id = leerTexto("Identificacion: ");
        LocalDate fechaNacimiento = LocalDate.parse(leerTexto("Fecha nacimiento (YYYY-MM-DD): "));
        String contrasena = leerTexto("Contrasena: ");
        String correo = leerTexto("Correo electronico: ");
        double puntuacion = Double.parseDouble(leerTexto("Puntuacion: "));
        String direccion = leerTexto("Direccion: ");

        if (tipo == 1) {
            System.out.println(sistema.registrarVendedor(nombre, id, fechaNacimiento, contrasena, correo, puntuacion, direccion));
        } else if (tipo == 2) {
            System.out.println(sistema.registrarColeccionista(nombre, id, fechaNacimiento, contrasena, correo, puntuacion, direccion));
        } else {
            System.out.println("Tipo de usuario invalido.");
        }
    }

    /**
     * Lista los usuarios registrados.
     */
    private static void listarUsuarios() {
        ArrayList<Usuario> usuarios = sistema.getUsuarios();

        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }

        for (int i = 0; i < usuarios.size(); i++) {
            System.out.println(i + ". " + usuarios.get(i));
        }
    }

    /**
     * Registra un nuevo objeto.
     */
    private static void registrarObjeto() throws IOException {
        String nombre = leerTexto("Nombre del objeto: ");
        String descripcion = leerTexto("Descripcion: ");
        String estado = leerTexto("Estado: ");
        LocalDate fechaCompra = LocalDate.parse(leerTexto("Fecha de compra (YYYY-MM-DD): "));

        System.out.println(sistema.registrarObjeto(nombre, descripcion, estado, fechaCompra));
    }

    /**
     * Lista los objetos registrados.
     */
    private static void listarObjetos() {
        ArrayList<ObjetoSubastable> objetos = sistema.getObjetos();

        if (objetos.isEmpty()) {
            System.out.println("No hay objetos registrados.");
            return;
        }

        for (int i = 0; i < objetos.size(); i++) {
            System.out.println(i + ". " + objetos.get(i));
        }
    }

    /**
     * Crea una nueva subasta.
     */
    private static void crearSubasta() throws IOException {
        if (!sistema.hayUsuarios() || !sistema.hayObjetos()) {
            System.out.println("Debe haber usuarios y objetos registrados.");
            return;
        }

        listarUsuarios();
        int indiceCreador = Integer.parseInt(leerTexto("Seleccione el indice del usuario creador: "));
        Usuario creador = sistema.getUsuarioPorIndice(indiceCreador);

        if (creador == null) {
            System.out.println("Indice invalido.");
            return;
        }

        LocalDateTime fechaVencimiento = LocalDateTime.parse(leerTexto("Fecha vencimiento (YYYY-MM-DDTHH:MM): "));
        double precioMinimo = Double.parseDouble(leerTexto("Precio minimo: "));

        ArrayList<ObjetoSubastable> seleccionados = new ArrayList<>();

        listarObjetos();
        int cantidad = Integer.parseInt(leerTexto("Cuantos objetos desea asociar?: "));

        for (int i = 0; i < cantidad; i++) {
            int indiceObjeto = Integer.parseInt(leerTexto("Indice del objeto #" + (i + 1) + ": "));
            ObjetoSubastable objeto = sistema.getObjetoPorIndice(indiceObjeto);

            if (objeto != null) {
                seleccionados.add(objeto);
            } else {
                System.out.println("Indice de objeto invalido.");
            }
        }

        System.out.println(sistema.crearSubasta(fechaVencimiento, creador, precioMinimo, seleccionados));
    }

    /**
     * Lista las subastas registradas.
     */
    private static void listarSubastas() {
        ArrayList<Subasta> subastas = sistema.getSubastas();

        if (subastas.isEmpty()) {
            System.out.println("No hay subastas registradas.");
            return;
        }

        for (int i = 0; i < subastas.size(); i++) {
            System.out.println(i + ". " + subastas.get(i));
        }
    }

    /**
     * Crea una nueva oferta.
     */
    private static void crearOferta() throws IOException {
        if (!sistema.hayUsuarios() || !sistema.haySubastas()) {
            System.out.println("Debe haber usuarios y subastas registradas.");
            return;
        }

        listarUsuarios();
        int indiceUsuario = Integer.parseInt(leerTexto("Seleccione el indice del oferente: "));
        Usuario usuario = sistema.getUsuarioPorIndice(indiceUsuario);

        if (usuario == null) {
            System.out.println("Indice de usuario invalido.");
            return;
        }

        listarSubastas();
        int indiceSubasta = Integer.parseInt(leerTexto("Seleccione el indice de la subasta: "));
        Subasta subasta = sistema.getSubastaPorIndice(indiceSubasta);

        if (subasta == null) {
            System.out.println("Indice de subasta invalido.");
            return;
        }

        double precio = Double.parseDouble(leerTexto("Precio ofertado: "));
        System.out.println(sistema.crearOferta(usuario, precio, subasta));
    }

    /**
     * Lista las ofertas registradas.
     */
    private static void listarOfertas() {
        ArrayList<Oferta> ofertas = sistema.getOfertas();

        if (ofertas.isEmpty()) {
            System.out.println("No hay ofertas registradas.");
            return;
        }

        for (int i = 0; i < ofertas.size(); i++) {
            System.out.println(i + ". " + ofertas.get(i));
        }
    }

    /**
     * Ejecuta el inicio de sesión.
     */
    private static void iniciarSesion() throws IOException {
        String correo = leerTexto("Correo: ");
        String contrasena = leerTexto("Contrasena: ");

        Usuario usuario = sistema.iniciarSesion(correo, contrasena);

        if (usuario != null) {
            System.out.println("Inicio de sesion exitoso. Bienvenido: " + usuario.getNombreCompleto());
        } else {
            System.out.println("Credenciales incorrectas.");
        }
    }

    /**
     * Valida si existe al menos un moderador.
     */
    private static void validarModerador() {
        if (sistema.existeModerador()) {
            System.out.println("Si existe al menos un moderador.");
        } else {
            System.out.println("No existe ningun moderador.");
        }
    }

    /**
     * Lee un texto desde consola.
     */
    private static String leerTexto(String mensaje) throws IOException {
        System.out.print(mensaje);
        return br.readLine();
    }
}

