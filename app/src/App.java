
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import accountsBank.AccountsBank;
import clients.Clients;
import mysql.DBConnection;
import mysql.Query;
import createNumberFormat.CreateNumberFormat;
import creditsCard.CreditsCard;
import founds.Founds;
import investments.Investments;

public class App {
    static CreateNumberFormat format;

    static final String COLOR_WHITE = "\033[1;37m";

    public static void main(String[] args) {
        new DBConnection();

        format = new CreateNumberFormat();

        initializeAnswers();
    }

    public static void initializeAnswers() {
        String[] options;
        int selected;
        String title;
        Scanner scanner = new Scanner(System.in);
        String scannerValue;
        ResultSet result;
        
        
        options = new String[] {
            "Cuentas bancarias",
            "Registrar cliente", 
            "Ver todos los clientes",
            "Eliminar todos los registros que existen",
            "Eliminar todas las tablas de la base de datos",
            "Crear todas las tablas de la base de datos"
        };

        selected = createAnswer(COLOR_WHITE + "BIENVENIDO AL BANCO, POR FAVOR SELECCIONA UNA OPCIÓN", options, "Salir");

        title = options[selected - 1].toUpperCase();

        createLineStart();

        switch(selected) {
            case 1: {
                options = new String[] {
                    "Crear una cuenta bancaria",
                    "Ingresar a mi cuenta bancaria",
                    "Ver todas las cuentas bancarias",
                    "Buscar una cuenta"
                };

                selected = createAnswer(title, options, "Volver al menú principal");

                System.out.println(title);     
                System.out.println();
                
                switch(selected) {
                    case 1: {
                        System.out.println("* CREAR UNA CUENTA BANCARIA");
                        System.out.println("Ingresa tu DNI:\n\n0. Volver al menú principal");
                        scannerValue = scanner.nextLine();

                        if (scannerValue.equals("0")) {
                            break;
                        }
                        
                        try {
                            result = new Clients().getClient(scannerValue);

                            if (result.next()) {
                                int idClient = result.getInt("cl.id");
                                int idClientWithBank = result.getInt("acc.idClient");
                                
                                if (idClient == idClientWithBank) {
                                    System.out.println("Este usuario ya posee una cuenta bancaria");
                                    generateExitOption(scanner);
                                    break;
                                }
                                
                                System.out.println("¿Tipo de cuenta de ahorro? Y / N:\n\n0. Volver al menú principal");
                                scannerValue = scanner.nextLine();
                                int accountType;

                                if (scannerValue.equals("0")) {
                                    break;
                                }
                                switch(scannerValue.toLowerCase()) {
                                    case "y": {
                                        accountType = 1;
                                        break;
                                    }
                                    default: {
                                        accountType = 0;
                                        break;
                                    }
                                }

                                AccountsBank accountsBank = new AccountsBank(
                                    idClient,
                                    accountType
                                );

                                accountsBank.createAccount();
                                // System.out.println(idClient);
                                generateExitOption(scanner);
                            }
                            else {
                                System.out.println("No existe ningún cliente asociado a este DNI");
                                generateExitOption(scanner);
                            }

                            result.close();
                        }
                        catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }

                        break;
                    }
                    case 2: {
                        System.out.println("Ingresa tu DNI:\n\n0. Volver al menú principal");
                        scannerValue = scanner.nextLine();
                        
                        if (scannerValue.equals("0")) {
                            break;
                        }

                        try {
                            result = new Clients().getClient(scannerValue);
                            
                            if (result.next()) {
                                String dni = result.getString("cl.dni");
                                int idClient = result.getInt("cl.id");
                                int idClientWithAccountBank = result.getInt("acc.idClient");

                                if (scannerValue.equals(dni) && idClient == idClientWithAccountBank) {
                                    int lastDigit;

                                    String name = result.getString("cl.name");
                                    String lastName = result.getString("cl.lastName");
                                    int money = result.getInt("acc.money");
                                    String date = result.getString("acc.dateStarted");
                                    int creditCardVinculation = result.getInt("cr.idClient");
                                    String accountNumber = result.getString("acc.accountNumber");
                                    String cbu = result.getString("acc.cbu");
                                    int moneyIncoming = result.getInt("acc.moneyIncoming");
                                    int moneyRetired = result.getInt("acc.moneyRetired");
                                    lastDigit = result.getInt("acc.lastDigit");
                                    boolean haveCreditCard = false;
                                    String creditCardInformation = "";

                                    if (creditCardVinculation == idClient) {
                                        haveCreditCard = true;
                                        
                                        creditCardInformation = String.format("- Generado: %s\n- Clave: %s\n- Tipo de tarjeta: %s\n- Apertura: %s\n- Vencimiento: %s", 
                                            result.getString("cr.creditCardNumber"),
                                            result.getString("cr.lastDigit"),
                                            (result.getInt("cr.useVisa") == 1) ? "Visa" : "MasterCard",
                                            result.getString("cr.dateApperture"),
                                            result.getString("cr.expire")
                                        );
                                    }

                                    options = new String[] {
                                        "Retirar dinero",
                                        "Ingresar dinero",
                                        "Fondos de inversión",
                                        "Cartera de valores",
                                        (haveCreditCard) ? "Desvincular" : "Vincular" + " tarjeta de crédito",
                                        "Darme de baja"
                                    };

                                    title = String.format("Bienvenido %s %s a tu cuenta bancaria\n\n- Dinero: $%s\n- Apertura: %s\n- Generado: %s\n- Clave generada: %d\n- CBU: %s\n- Dinero ingresado: $%s\n- Dinero retirado: $%s\n\nTARJETAS:\n\n%s", 
                                        name, 
                                        lastName, 
                                        format.setFormat(money), 
                                        date,
                                        accountNumber,
                                        lastDigit,
                                        cbu,
                                        format.setFormat(moneyIncoming),
                                        format.setFormat(moneyRetired),
                                        (haveCreditCard) ? creditCardInformation : "- No hay tarjeta vinculadas"
                                    );

                                    System.out.println();
                                    selected = createAnswer(title, options, "Volver al menú principal");


                                    switch(selected) {
                                        case 1, 2: {
                                            try {
                                                System.out.println();
                                                System.out.printf("Escribe el monto de dinero que deseas %s:\n\n0. Volver al menú principal\n", 
                                                (selected == 1) ? "retirar" : "ingresar");

                                                scannerValue = scanner.nextLine();
                                                
                                                if (scannerValue.equals("0")) {
                                                    break;
                                                }

                                                int value = Integer.parseInt(scannerValue);
                                                int lastMoney = money;

                                                if (selected == 1) {
                                                    money -= value;
                                                    moneyRetired += value; 
                                                }
                                                else {
                                                    money += value;
                                                    moneyIncoming += value;
                                                }
                                                
                                                Integer update = new AccountsBank().setAccountBankUpdateData(
                                                    selected,
                                                    money,
                                                    moneyIncoming,
                                                    moneyRetired,
                                                    value,
                                                    idClient
                                                ); 

                                                if (update != null) {
                                                    System.out.println();
                                                    System.out.println("* OPERACIÓN EXITOSA: ");
                                                    System.out.printf("* Has %s $%s de dinero %s tu cuenta bancaria, ahora de $%s pasaste a tener $%s", 
                                                        (selected == 1) ? "retirado" : "ingresado",
                                                        format.setFormat(value),
                                                        (selected == 1) ? "de" : "en",
                                                        format.setFormat(lastMoney), 
                                                        format.setFormat(money)
                                                    );
                                                }
                                                
                                                generateExitOption(scanner);
                                            }
                                            catch (NumberFormatException e) {
                                                System.out.println();
                                                System.out.println("* HAN SUCECIDO ALGUNOS INCONVENIENTES:");

                                                System.out.println();
                                                System.out.println("- Al ingresa dinero, el valor introducido es muy grande");
                                                System.out.println("- Al retirar dinero, el valor introducido supera tu dinero actual");
                                                generateExitOption(scanner);
                                            }

                                            break;
                                        }
                                        case 3: {
                                            options = new String[] {
                                                "Inversión de renta fija",
                                                "Inversión de renta variable (NO DISPONIBLE)",
                                                "Inversión en bolsa de valores (NO DISPONIBLE)",
                                                "Inversión en divisas (NO DISPONIBLE)",
                                                "Inversión en materias primas (NO DISPONIBLE)",
                                                "Inversión en bienes inmobiliarios (NO DISPONIBLE)\n\n",
                                                "Mis inversiones"
                                            };

                                            selected = createAnswer("INVERSIONES", options, "Volver al menú principal");
                                            
                                            switch(selected) {
                                                case 1: {
                                                    System.out.println();

                                                    title = String.format("Escribe el monto a invertir, no puede superar los $%s\n\n0. Volver al menú principal", format.setFormat(money));
                                                    System.out.println(title);
                                                    scannerValue = scanner.nextLine();

                                                    if (scannerValue.equals("0")) {
                                                        break;
                                                    }

                                                    if (Integer.parseInt(scannerValue) > money) {
                                                        System.out.println("* La inversión supera tu dinero actual");
                                                        generateExitOption(scanner);
                                                        break;
                                                    }

                                                    int value = Integer.parseInt(scannerValue);
                                                        

                                                    String founds = new Founds(options[selected - 1], idClient).createInvertion(value);

                                                    System.out.println();
                                                    System.out.println(founds);

                                                    generateExitOption(scanner);
                                                    break;
                                                }
                                                // case 2..6 { }
                                                case 2, 3, 4, 5, 6: {
                                                    System.out.println("Esta inversión aún no está disponible");
                                                    generateExitOption(scanner);     
                                                    break;
                                                }
                                                case 7: {
                                                    System.out.println("* MIS INVERSIONES");
                                                    dni = result.getString("dni");

                                                    result.close();
                                                    result = new Clients().getClient(dni);

                                                    int num = 0;

                                                    while (result.next()) {
                                                        num++;

                                                        String invertions = String.format("INVERSIÓN N° %d\n- ID: #%d\n- Monto: $%s\n- Monto por día: $%s\n- Renta: %d%%\n- Probabilidad: %d%%\n- Ganancias: $%s (+%d%%)\n- Días de inversión: %d\n- Apertura: %s\n- Expira: %s",
                                                            num,
                                                            result.getInt("fo.id"),
                                                            format.setFormat(result.getInt("fo.invertion")),
                                                            format.setFormat(result.getInt("fo.amount")),
                                                            result.getInt("fo.rent"),
                                                            result.getInt("fo.chanceSuccefully"),
                                                            format.setFormat(result.getInt("fo.gainWithSuccefully")), result.getInt("fo.percentSuccefully"),
                                                            result.getInt("fo.days"),
                                                            result.getString("fo.dateApperture"),
                                                            result.getString("fo.dateExpire")
                                                        );

                                                        System.out.println();
                                                        System.out.println(invertions);
                                                    }

                                                    result.close();
                                                    generateExitOption(scanner);
                                                    break;

                                                }
                                            }

                                            break;
                                        }
                                        case 4: {
                                            result.close();
                                            result = new Investments().getInvertion(idClient);

                                            System.out.println("* MI CARTERA DE VALORES");

                                            while (result.next()) {
                                                System.out.println();
                                                System.out.printf("\nCARTERA #%d\n- Nombre del valor: %s\n- Inversión: $%s\n- Número de títulos: %d\n- Precio de cotización: $%s\n- Porcentaje de la cotización: %d%%",
                                                    result.getInt("inv.id"),
                                                    result.getString("inv.nameValue"),
                                                    format.setFormat(result.getInt("fo.invertion")),
                                                    result.getInt("inv.numberTitles"),
                                                    format.setFormat(result.getInt("inv.priceCotization")),
                                                    result.getInt("inv.percentCotization")
                                                );
                                            }
                                            
                                            result.close();
                                            generateExitOption(scanner);
                                            break;
                                        }
                                        case 5: {
                                            options = new String[] {
                                                "Sí",
                                                "No"
                                            };

                                            boolean vinculation = (creditCardVinculation == idClient) ? true : false;
                                             

                                            title = String.format("¿%s TARJETA DE CRÉDITO?", (vinculation) ? "DESVINCULAR" : "VINCULAR");

                                            selected = createAnswer(title, options, "Volver al menú principal");
                                            
                                            switch(selected) {
                                                case 1: {
                                                    if (vinculation) {
                                                        new CreditsCard(idClient).deleteCreditCard();
                                                        generateExitOption(scanner);
                                                        break;
                                                    } 

                                                    int idBank = result.getInt("acc.id");

                                                    CreditsCard creditsCard = new CreditsCard(
                                                        idClient,
                                                        idBank
                                                    );

                                                    creditsCard.createCreditCard();
                                                    generateExitOption(scanner);
                                                    break;
                                                }
                                            }

                                            break;
                                        }
                                        case 6: {
                                            options = new String[] {
                                                "Sí",
                                                "No"
                                            };

                                            selected = createAnswer("¿ESTÁS SEGURO DE DAR DE BAJA TU CUENTA BANCARIA?\n\nAl dar de baja " +
                                            "tu cuenta bancaria, también tu usuario se dará de baja", 
                                            options, "Volver al menú principal");

                                            switch(selected) {
                                                case 1: {
                                                    new AccountsBank().deleteAccount(idClient);

                                                    System.out.println("TE HAS DADO DE BAJA");
                                                    System.out.printf("Tu usuario %s %s junto a tu cuenta bancaria, fueron dados de baja", name, lastName);

                                                    generateExitOption(scanner);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                                else {
                                    System.out.println();            
                                    System.out.println("Los datos introducidos son incorrectos");
                                    generateExitOption(scanner);

                                }
                            }
                            else {
                                System.out.println();            
                                System.out.println("La credencial del DNI no coincide");
                                generateExitOption(scanner);

                            }

                            result.close();
                        }
                        catch (SQLException e) {
                            
                        }
                        
                        break;
                    }
                    case 3: {
                        try {
                            result = new AccountsBank().getAllAccounts();

                            while (result.next()) {
                                System.out.println();
                                System.out.printf("* #%d %s (%d) (%s) - $%s - %s %s - %s", 
                                    result.getInt("acc.id"),
                                    result.getString("acc.accountNumber"),
                                    result.getInt("acc.lastDigit"),
                                    (result.getBoolean("acc.accountTypeSaving")) ? "CAJA DE AHORRO" : "CUENTA CORRIENTE",
                                    format.setFormat(result.getInt("acc.money")),
                                    result.getString("cl.name"),
                                    result.getString("cl.lastName"),
                                    result.getString("acc.dateStarted")
                                );
                            }

                            result.close();
                            generateExitOption(scanner);
                        }
                        catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }

                        break;
                    }
                    case 4: {
                        System.out.println("Introduce el nombre del cliente al que pertenece una cuenta bancaria");

                        scannerValue = scanner.nextLine();
                        
                        try {
                            result = new Clients().getClient(scannerValue);

                            if (result.next()) {
                                System.out.println();
                                System.out.printf("* #%d %s (%d) (%s) - $%s - %s %s -  %s", 
                                    result.getInt("acc.id"),
                                    result.getString("acc.accountNumber"),
                                    result.getInt("acc.lastDigit"),
                                    (result.getBoolean("acc.accountTypeSaving")) ? "CAJA DE AHORRO" : "CUENTA CORRIENTE",
                                    format.setFormat(result.getInt("acc.money")),
                                    result.getString("cl.name"),
                                    result.getString("cl.lastName"),
                                    result.getString("acc.dateStarted")
                                );
                            }
                            else {
                                System.out.println();    
                                System.out.println("* No se encontraron resultados");

                            }
                            
                            result.close();
                            generateExitOption(scanner);
                        }
                        catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }

                        break;
                    }
                }

                break;
            }
            case 2: {
                String name;
                String lastName;
                String dni;
                String phone;
                String direction;
                int accountType;
                int idClient;
                String exit;
                exit = "\n\n0. Volver al menú principal";

                System.out.println(title);

                System.out.println("Introduce tu nombre:" + exit);
                scannerValue = scanner.nextLine();

                if (scannerValue.equals("0")) {
                    break;
                }

                name = scannerValue;

                System.out.println("Introduce tu apellido:" + exit);
                scannerValue = scanner.nextLine();

               if (scannerValue.equals("0")) {
                    break;
                }

                lastName = scannerValue;

                System.out.println("Introduce tu DNI:" + exit);
                scannerValue = scanner.nextLine(); 

                if (scannerValue.equals("0")) {
                    break;
                }

                dni = scannerValue;

                System.out.println("Introduce tu teléfono:" + exit);
                scannerValue = scanner.nextLine();

                if (scannerValue.equals("0")) {
                    break;
                }

                phone = scannerValue;

                System.out.println("Introduce tu dirección:" + exit);
                scannerValue = scanner.nextLine();

                direction = scannerValue;


                Clients clients = new Clients(
                    name,
                    lastName,
                    dni,
                    phone,
                    direction
                );

                if (clients.getClientExist(dni)) {
                    System.out.println("Este DNI le pertenece a un cliente ya registrado");
                    generateExitOption(scanner);
                    break;
                }

                clients.createClient();

                System.out.println("\n\n¿Crear una cuenta bancaria? Y / N\nPodrás crearla luego en la opción 'Cuentas bancarias'");
                scannerValue = scanner.nextLine();

                if (!scannerValue.toLowerCase().equals("y")) {
                    // generateExitOption(scanner);
                    break;
                }

                System.out.println("¿Tipo de cuenta de ahorro? Y / N:" + exit);
                scannerValue = scanner.nextLine();

                switch(scannerValue.toLowerCase()) {
                    case "y": {
                        accountType = 1;
                        break;
                    }
                    default: {
                        accountType = 0;
                        break;
                    }
                }

                idClient = clients.getLastClientID();

                AccountsBank accountsBank = new AccountsBank(
                    idClient,
                    accountType
                );

                accountsBank.createAccount();
                generateExitOption(scanner);
                break;
            }
            case 3: {
                System.out.println(title);
                
                try {
                    result = new Clients().getAllClients();  

                    while (result.next()) {
                        System.out.println();
                        System.out.printf("* #%d %s %s - %s - %s - %s - %s", 
                            result.getInt("id"),
                            result.getString("name"), 
                            result.getString("lastName"),     
                            format.setFormat(Integer.parseInt(result.getString("dni"))),
                            result.getString("phone"),
                            result.getString("direction"),
                            result.getString("dateRegister")
                        );
                    }

                    result.close();
                    generateExitOption(scanner);
                }
                catch (SQLException e) {
                }
                
                break;
            }
            case 4: {
                new Query(null).clearTables();
                break;
            }
            case 5: {
                new Query(null).deleteTables();
                break;
            }
            case 6: {
                new Query(null).createAllTables();
                break;
            }
        }

        createLineEnd();
        
        initializeAnswers();

        scanner.close();
    }

    public static int createAnswer(String title, String[] options, String optionExit) {

        createLineStart(); 

        System.out.println("*" + " " + title);   
        System.out.println();

        
        for (int i = 0; i < options.length; i++) {
            System.out.println();
            System.out.printf("%d. %s", (i + 1), options[i]);
        }

        System.out.println();      
        System.out.println();
        
        System.out.printf("0. %s", optionExit);

        createLineEnd();

        int value = checkScanner();

        return value;
    }

    public static int checkScanner() {
        Scanner scanner = new Scanner(System.in);
        int value = scanner.nextInt();
        // scanner.close();

        return value;
    }

    public static void createLineStart() {
        System.out.println("=======================================================");   
        System.out.println();   
    }

    public static void createLineEnd() {
        System.out.println(); 
        System.out.println("=======================================================");   
    }

    public static void generateExitOption(Scanner scanner) {
        System.out.println();        
        System.out.println(); 

        System.out.println("0. Volver al menú principal");
        scanner.nextLine();
    }
}
