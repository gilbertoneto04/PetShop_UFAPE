package util;

import java.time.LocalDateTime;
import java.util.Scanner;

public class InputHelper {

    public static int lerInteiro(Scanner sc) {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. Digite um número inteiro: ");
            }
        }
    }

    public static double lerDouble(Scanner sc) {
        while (true) {
            try {
                return Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. Digite um número decimal (ex: 10.5): ");
            }
        }
    }

    public static LocalDateTime lerDataHora(Scanner sc) {
        while (true) {
            try {
                String input = sc.nextLine();
                return LocalDateTime.parse(input);
            } catch (Exception e) {
                System.out.print("Data/hora inválida. Use o formato AAAA-MM-DDTHH:MM: ");
            }
        }
    }
}
