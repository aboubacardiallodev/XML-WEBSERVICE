import proxy.Account;
import proxy.BankWS;
import proxy.BanqueAccountServiceService;

import java.util.List;
import java.util.Scanner;

public class ClientWS {

    private static BankWS bankService;
    private static Scanner scanner;

    public static void main(String[] args) {
        bankService = new BanqueAccountServiceService().getBankWSPort();

        scanner = new Scanner(System.in);

        // Initialiser le service proxy
        BanqueAccountServiceService service = new BanqueAccountServiceService();
        bankService = service.getBankWSPort();

        boolean continuer = true;

        while (continuer) {
            afficherMenu();
            int choix = lireChoix();

            switch (choix) {
                case 1:
                    afficherCompte();
                    break;
                case 2:
                    afficherListeComptes();
                    break;
                case 3:
                    effectuerConversion();
                    break;
                case 4:
                    System.out.println("Merci d'avoir utilisé ce service. Au revoir !");
                    continuer = false;
                    break;
                default:
                    System.out.println("Choix invalide ! Veuillez réessayer.");
            }

            if (continuer) {
                System.out.println("\nAppuyez sur Entrée pour continuer...");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    private static void afficherMenu() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║       MENU - SERVICE BANCAIRE          ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ 1. Récupérer un compte par code        ║");
        System.out.println("║ 2. Afficher la liste de tous les comptes║");
        System.out.println("║ 3. Convertir Euro en Dirham            ║");
        System.out.println("║ 4. Quitter                             ║");
        System.out.println("╚════════════════════════════════════════╝");
    }

    private static int lireChoix() {
        System.out.print("Choisissez une option (1-4) : ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void afficherCompte() {
        System.out.print("Entrez le code du compte : ");
        try {
            int code = Integer.parseInt(scanner.nextLine());
            Account account = bankService.getAccount(code);

            if (account != null) {
                System.out.println("\n✓ Compte trouvé :");
                System.out.println("  Code : " + account.getCode());
                System.out.println("  Solde : " + account.getBalance() + " DH");
                System.out.println("  Dae creation : " + account.getCreatedAt());
            } else {
                System.out.println("✗ Aucun compte trouvé avec ce code.");
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ Erreur : Le code doit être un nombre entier.");
        } catch (Exception e) {
            System.out.println("✗ Erreur lors de la récupération du compte : " + e.getMessage());
        }
    }

    private static void afficherListeComptes() {
        try {
            List<Account> comptes = bankService.getListAccounts();

            if (comptes != null && !comptes.isEmpty()) {
                System.out.println("\n✓ Liste des comptes :");
                System.out.println("┌───────┬────────────────────────────────┐");
                System.out.println("│ Code  │ Solde (DH)  │   Date creation  │");
                System.out.println("├───────┼────────────────────────────────┤");

                for (Account account : comptes) {
                    String dateFormatee = String.format("%02d/%02d/%04d",
                            account.getCreatedAt().getDay(),
                            account.getCreatedAt().getMonth(),
                            account.getCreatedAt().getYear());
                    System.out.printf("│ %-5d │ %-12.2f │ %-15s │%n",
                            account.getCode(),
                            account.getBalance(),
                            dateFormatee);
                }

                System.out.println("└───────┴────────────────────────────────┘");
            } else {
                System.out.println("✗ Aucun compte disponible.");
            }
        } catch (Exception e) {
            System.out.println("✗ Erreur lors de la récupération des comptes : " + e.getMessage());
        }
    }

    private static void effectuerConversion() {
        System.out.print("Entrez le montant en euros : ");
        try {
            double montant = Double.parseDouble(scanner.nextLine());
            double resultat = bankService.conversionEuroToDh(montant);

            System.out.println("\n✓ Résultat de la conversion :");
            System.out.println("  " + montant + " € = " + String.format("%.2f", resultat) + " DH");
        } catch (NumberFormatException e) {
            System.out.println("✗ Erreur : Le montant doit être un nombre décimal.");
        } catch (Exception e) {
            System.out.println("✗ Erreur lors de la conversion : " + e.getMessage());
        }
    }
}
