package com.example;

import java.util.ArrayList;
import java.util.List;

public class Cine {

    private final int NUMERO_FILAS = 10;  // número de fila fixa
    private final int NUMERO_COLUNAS = 10;  // número de coluna fixa
    private Character[][] sala; // uma array
    private List<String> historicoCompras;
    private List<String> clientes;

    public int getNUMERO_FILAS(){
        return NUMERO_FILAS;
    }

    public int getNUMERO_COLUNAS(){
        return NUMERO_COLUNAS;
    }

    public Cine() {
        this.sala = new Character[NUMERO_FILAS + 1][NUMERO_COLUNAS + 1];
        this.historicoCompras = new ArrayList<>();
        this.clientes = new ArrayList<>();
        preencherSala();

    }
    
    public void preencherSala() {
        for (int i = 0; i < sala.length; i++) {
            for (int j = 0; j < sala[0].length; j++) {
                if (i == 0 && j == 0) sala[i][j] = ' ';
                else if (i == 0) sala[i][j] = Character.forDigit(j, 10);
                else if (j == 0) sala[i][j] = Character.forDigit(i, 10);
                else sala[i][j] = 'D';
            }
        }
    }

    public String imprimirSala() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < sala.length; i++) {
            for (int j = 1; j < sala[0].length; j++) {
                if (sala[i][j] == 'D') {
                    sb.append("D ");  // Assento disponível
                } else {
                    sb.append("O ");  // Assento ocupado
                }
            }
            sb.append("\n");
        }
        return sb.toString();  // Retorna a representação da sala como string
    }

    // Método para comprar ingresso
    public boolean verificarPoltrona(int fila, int coluna) {
        if (fila > NUMERO_FILAS || coluna > NUMERO_COLUNAS || fila == 0 || coluna == 0) {
            return false; // Poltrona inválida
        }
        return sala[fila][coluna] == 'D'; // Verifica se a poltrona está disponível
    }

    public void comprarIngresso(int fila, int coluna, String nomeCliente) {
        sala[fila][coluna] = 'O'; // Marca como ocupado
        historicoCompras.add("Assento na fila e coluna respectivamente [" + fila + "," + coluna + "] comprado por " + nomeCliente);
        clientes.add(nomeCliente); // Adiciona o nome ao histórico de clientes
    }

    // Método para retornar o histórico de compras
    public String getHistoricoCompras() {
        StringBuilder historico = new StringBuilder();
        for (String compra : historicoCompras) {
            historico.append(compra).append("\n");
        }
        return historico.toString();

    }
}