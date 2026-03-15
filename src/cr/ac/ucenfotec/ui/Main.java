package cr.ac.ucenfotec.ui;

import cr.ac.ucenfotec.bl.logic.AdminSistema;
import cr.ac.ucenfotec.dl.Data;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {

        AdminSistema sistema = new AdminSistema();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int opcion = 0;

        try {

            while(opcion != 7){

                System.out.println("\nSISTEMA DE SUBASTAS");
                System.out.println("1. Registrar usuario");
                System.out.println("2. Listar usuarios");
                System.out.println("3. Crear subasta");
                System.out.println("4. Listar subastas");
                System.out.println("5. Crear oferta");
                System.out.println("6. Listar ofertas");
                System.out.println("7. Salir");

                System.out.print("Seleccione una opcion: ");
                opcion = Integer.parseInt(br.readLine());

                switch(opcion){

                    case 1:

                        System.out.print("Nombre: ");
                        String nombre = br.readLine();

                        System.out.print("Identificacion: ");
                        String id = br.readLine();

                        System.out.print("Edad: ");
                        int edad = Integer.parseInt(br.readLine());

                        System.out.print("Correo: ");
                        String correo = br.readLine();

                        sistema.registrarUsuario(nombre,id,edad,correo);

                        System.out.println("Usuario registrado correctamente.");
                        break;

                    case 2:

                        System.out.println("\nLista de usuarios:");
                        sistema.listarUsuarios();
                        break;

                    case 3:

                        if (Data.usuarios.isEmpty()) {
                            System.out.println("No hay usuarios registrados.");
                            break;
                        }

                        System.out.print("Fecha de vencimiento: ");
                        String fecha = br.readLine();

                        System.out.print("Precio minimo: ");
                        double precioMin = Double.parseDouble(br.readLine());

                        System.out.println("\nUsuarios disponibles:");
                        for (int i = 0; i < Data.usuarios.size(); i++) {
                            System.out.println(i + ". " + Data.usuarios.get(i));
                        }

                        System.out.print("Seleccione el indice del usuario creador: ");
                        int indiceCreador = Integer.parseInt(br.readLine());

                        if (indiceCreador >= 0 && indiceCreador < Data.usuarios.size()) {
                            sistema.crearSubasta(fecha, Data.usuarios.get(indiceCreador), precioMin);
                            System.out.println("Subasta creada correctamente.");
                        } else {
                            System.out.println("Indice de usuario invalido.");
                        }
                        break;

                    case 4:

                        System.out.println("\nLista de subastas:");
                        sistema.listarSubastas();
                        break;

                    case 5:

                        if (Data.usuarios.isEmpty() || Data.subastas.isEmpty()) {
                            System.out.println("Debe haber usuarios y subastas registradas.");
                            break;
                        }

                        System.out.println("\nUsuarios disponibles:");
                        for (int i = 0; i < Data.usuarios.size(); i++) {
                            System.out.println(i + ". " + Data.usuarios.get(i));
                        }

                        System.out.print("Seleccione el indice del usuario oferente: ");
                        int indiceOferente = Integer.parseInt(br.readLine());

                        if (indiceOferente < 0 || indiceOferente >= Data.usuarios.size()) {
                            System.out.println("Indice de usuario invalido.");
                            break;
                        }

                        System.out.println("\nSubastas disponibles:");
                        for (int i = 0; i < Data.subastas.size(); i++) {
                            System.out.println(i + ". " + Data.subastas.get(i));
                        }

                        System.out.print("Seleccione el indice de la subasta: ");
                        int indiceSubasta = Integer.parseInt(br.readLine());

                        if (indiceSubasta < 0 || indiceSubasta >= Data.subastas.size()) {
                            System.out.println("Indice de subasta invalido.");
                            break;
                        }

                        System.out.print("Precio de la oferta: ");
                        double precioOferta = Double.parseDouble(br.readLine());

                        sistema.crearOferta(
                                Data.usuarios.get(indiceOferente),
                                precioOferta,
                                Data.subastas.get(indiceSubasta)
                        );

                        System.out.println("Oferta registrada correctamente.");
                        break;

                    case 6:

                        System.out.println("\nLista de ofertas:");
                        sistema.listarOfertas();
                        break;

                    case 7:
                        System.out.println("Saliendo del sistema...");
                        break;

                    default:
                        System.out.println("Opcion invalida.");

                }

            }

        } catch (Exception e){
            System.out.println("Error en el sistema.");
        }

    }
}

