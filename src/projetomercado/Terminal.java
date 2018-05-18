/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetomercado;

import java.util.InputMismatchException;
import projetomercado.Product;
import projetomercado.Payment;
import java.util.Scanner;

/**
 *
 * @author wesleyfreitas
 */
public class Terminal {
    
    private Payment payment = new Payment();

    public void startTerminal() {

        System.out.println("Bem-vindo(a) ao terminal de vendas do SuperMercado");
        System.out.println("A qualquer momento tecle 'OPT' no código do produto ir às opções da compra");
        startSale();
    }

    private void startSale() {

        try {
            // Declare scanner
            Scanner scanner = new Scanner(System.in);

            // Product code
            System.out.print("\nInsira o código do produto: ");
            String code = scanner.nextLine();
            
            if (code.toUpperCase().trim().matches("OPT")) {
                this.settings();
                return;
            }

            Product product = new Product(code).findProduct();
            if (product == null) {
                System.out.println("Produto não encontrado"); 
                this.startSale();
            }
            
            // Product unit
            System.out.print("Insira a quantidade: ");
            int units = scanner.nextInt();

            // Check if product or unit is null
            if (code.isEmpty() || code == null || units == 0) {
                System.out.println("Insira o código e/ou quantidade do produto");
                return;
            }
            
            this.payment.setCart(product.code, units);
            this.startSale();
        } catch (InputMismatchException e) {
            System.out.println("Insira apenas números na quantidade de produtos!");
            this.startSale();
        }
    }

    private void settings() {
        System.out.println("\nOpções da compra");
        System.out.println("1 - Finalizar");
        System.out.println("2 - Detalhar");
        System.out.println("3 - Remover item");
        System.out.println("4 - Cancelar");
        System.out.println("5 - Sair");
        System.out.print("Digite o número da opção: ");
        
        try {
            int opt = new Scanner(System.in).nextInt();
            
            if (opt == 1) {
                this.finishCart();
            } else if (opt == 2) {
                this.cartDetail();
            } else if (opt == 3) {
                this.removeItem();
            } else if (opt == 4) {
                this.cancelSale();
            } else if (opt == 5) {
                this.startSale();
            } else {
                System.out.println("Opção inválida. Tente novamente!");
                this.settings();
            }
            
        } catch (Exception e) {
            System.out.println("Insira apenas números para escolher a opção!");
            this.settings();
        }
    }
    
    private void finishCart() {
        if (!this.payment.finishCart()) 
            this.settings();
        else {
            System.out.println("\n***** Compra finalizada! Obrigado e volte sempre! *****\n\n");
            this.startTerminal();
        }
    }
    
    private void cartDetail() {
        this.payment.cartDetail();
        this.startSale();
    }
    
    private void removeItem() {
        System.out.print("\nDigite o código do produto: ");
        String code = new Scanner(System.in).nextLine();
        boolean remove = this.payment.removeItem(code);
        
        if (remove) {
            System.out.println("Item removido com sucesso!");
        } else {
            System.out.println("Item não encontrado");
        }
        
        this.startSale();
    }
    
    private void cancelSale() {
        this.payment = new Payment();
        System.out.println("\nCompra cancelada com sucesso!\n");
        this.startTerminal();
    }
    

}
