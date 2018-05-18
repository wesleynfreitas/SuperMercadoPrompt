/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetomercado;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Scanner;
import projetomercado.Product;
import projetomercado.Terminal;

/**
 *
 * @author wesleyfreitas
 */
public class Payment {

    protected String code;
    protected int units;
    protected Double amount;

    public ArrayList<Payment> shoppingCart = new ArrayList();

    Payment() {

    }

    Payment(String code, int units) {
        this.code = code;
        this.units = units;
        this.amount = new Product(code).findProduct().price * units;
    }

    public ArrayList<Payment> getCart() {
        return this.shoppingCart;
    }

    public boolean setCart(String code, int units) {
        
        boolean added = false;
        
        for (int i = 0; i < this.shoppingCart.size(); i++) {
            if (this.shoppingCart.get(i).code.matches(code.toUpperCase().trim())) {
                Payment item = this.shoppingCart.get(i);
                item.units += units;
                item.amount = new Product(code).findProduct().price * item.units;
                added = true;
            }
        }
        
        if (!added) {
            added = this.shoppingCart.add(new Payment(code, units));                    
        }
        
        return added;
    }

    public boolean removeItem(String code) {
        boolean remove = false;

        for (int i = 0; i < this.shoppingCart.size(); i++) {
            if (this.shoppingCart.get(i).code.matches(code.toUpperCase().trim())) {
                this.shoppingCart.remove(i);
                remove = true;
            }
        }

        return remove;
    }

    public void cartDetail() {

        System.out.println("\n*****  RESUMO DA COMPRA  *****");
        System.out.println("COD  |  DESC  |  QTD  |  UN  |  QTD * UN");

        this.shoppingCart.forEach(item -> {
            Product product = new Product(item.code).findProduct();
            System.out.println(MessageFormat.format("{0}  {1}  {2}  {3}  {4}",
                    product.code, product.name, item.units, product.price, item.amount));
        });
        this.calculeCart();
    }

    public boolean finishCart() {

        if (this.shoppingCart.size() == 0) {
            System.out.println("\nO carrinho deve conter itens de compra");
            return false;
        }

        Double total = this.calculeCart();

        System.out.println("\nEscolha a forma de pagamento");
        System.out.println("1 - Dinheiro");
        System.out.println("2 - Cheque");
        System.out.println("3 - Voltar");
        System.out.print("Digite o número da opção: ");

        try {
            int opt = new Scanner(System.in).nextInt();

            if (opt == 1) {
                return this.cashPayment(total);
            } else if (opt == 2) {
                return this.bankCheckPayment(total);
            } else if (opt == 3) {
                return false;
            } else {
                System.out.println("Opção inválida. Tente novamente!");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Insira apenas números para escolher a opção!");
            return false;
        }
    }

    private Double calculeCart() {

        Double total = 0.00;

        for (int i = 0; i < this.shoppingCart.size(); i++) {
            total += this.shoppingCart.get(i).amount;
        }

        System.out.println("\nTOTAL R$" + total);
        return total;
    }

    private boolean cashPayment(Double total) {

        try {
            System.out.print("\nDigite a quantia recebida: ");
            Double value = new Scanner(System.in).nextDouble();
            
            if (value < total) {
                System.out.println("\nO valor recebido do cliente deve ser maior que o total da compra");
                return false;
            }
            
            this.cartDetail();
            System.out.println("\nTroco: " + (value - total));
            return true;

        } catch (Exception e) {
            System.out.println("Insira apenas números para escolher a opção!");
            this.finishCart();
            return true;
        }
    }

    private boolean bankCheckPayment(Double total) {

        System.out.println("\nInsira os dados do cheque ");

        Scanner scanner = new Scanner(System.in);

        System.out.print("\nDigite o nome do banco: ");
        String bank = scanner.nextLine();

        System.out.print("Digite o número do cheque: ");
        String number = scanner.nextLine();

        System.out.print("Digite o nome do comprador: ");
        String name = scanner.nextLine();

        System.out.println("\nRecibo de pagamento com cheque");
        System.out.println(MessageFormat.format("Nome do comprador: {0}", name));
        System.out.println(MessageFormat.format("Nome do banco: {0} | No. do cheque: {1}", bank, number));

        return this.bankCheckConfirmation();
    }

    private boolean bankCheckConfirmation() {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("\nVocê confirma o depósito do cheque? (1 - SIM, 2 - NÃO): ");
            int confirmation = scanner.nextInt();
            
            if (confirmation == 1) {
                System.out.println("Depósito confirmado!");
                this.cartDetail();
                return true;
            } else if (confirmation == 2) {
                System.out.println("Depósito NÃO confirmado!");
                return false;
            } else {
                System.out.println("Opção inválida. Tente novamente!");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Insira apenas números para escolher a opção!");
            return false;
        }
    }
}
