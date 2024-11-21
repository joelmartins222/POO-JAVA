package com.example; // pacote utilizado com.example

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    private Cine cine = new Cine();
    private TextArea salaTextArea = new TextArea();

    @Override
    //ponto de entrada da aplicação javafx
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sistema de Cinema"); //título da janela principal
        
        VBox mainLayout = new VBox(10); // utilizado um espaçamento de 10pixels entre os botões

        // botão para verificar poltronas disponíveis
        Button btnVerificar = new Button("Verificar poltronas disponíveis");
        btnVerificar.setOnAction(e -> verificarPoltronas());

        // botão para comprar ingressos
        Button btnComprar = new Button("Comprar Ingressos");
        btnComprar.setOnAction(e -> comprarIngresso());

        // botão para verificar histórico de compras
        Button btnHistorico = new Button("Visualizar Compra");
        btnHistorico.setOnAction(e -> exibirHistoricoCompras());

        mainLayout.getChildren().addAll(btnVerificar, btnComprar, btnHistorico, salaTextArea);  // adiciona o espaço de texto e os botões no layout um abaixo do outro

        // tamanho da janela do projeto
        Scene scene = new Scene(mainLayout, 700, 700); //700px de largura e 700px de altura
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void verificarPoltronas() {
        salaTextArea.setText(cine.imprimirSala());
        for (int i = 1; i <= cine.getNUMERO_FILAS(); i++) {
            for (int j = 1; j <= cine.getNUMERO_COLUNAS(); j++) {
                Button seatButton = new Button("D");
                seatButton.setStyle("-fx-background-color: #00CED1;"); // Cor para disponível
                int fila = i, coluna = j;
            }

        }
    }

    // metódo para comprar ingresso
    private void comprarIngresso() {
        Stage compraStage = new Stage();
        compraStage.setTitle("Comprar Ingresso");

        // layout para a janela de compra
        VBox compraLayout = new VBox(10);

        // usamos uma caixa de texto para inserir o nome do cliente
        TextField nomeClienteField = new TextField();
        nomeClienteField.setPromptText("Digite seu nome");

        // layout para seleção de assentos
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);

        // adiciona os números nas colunas
        for (int j = 1; j <= cine.getNUMERO_COLUNAS(); j++) {
            Label colunaLabel = new Label(Integer.toString(j));
            grid.add(colunaLabel, j, 0);
        }

        // adiciona a letra da fila no início de cada linha
        for (int i = 1; i <= cine.getNUMERO_FILAS(); i++) {
            Label filaLabel = new Label(Character.toString((char) ('A' + i - 1)));
            grid.add(filaLabel, 0, i);
    
            // Adiciona os botões dos assentos
            for (int j = 1; j <= cine.getNUMERO_COLUNAS(); j++) {
                Button seatButton = new Button("D");
                seatButton.setStyle("-fx-background-color: #00CED1;"); // Cor para disponível
                int fila = i, coluna = j;
                seatButton.setOnAction(e -> {
                    String nomeCliente = nomeClienteField.getText().trim();
                    if (nomeCliente.isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Por favor, insira o nome do cliente antes de selecionar o assento.");
                        alert.show();
                    } else {
                        boolean disponivel = cine.verificarPoltrona(fila, coluna);
                        if (disponivel) {
                            cine.comprarIngresso(fila, coluna, nomeCliente);
                            seatButton.setStyle("-fx-background-color: #FFD700;"); // Cor para ocupado
                        } else {
                            //mensagem de alerta para caso a poltrona já esteja ocupada
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Assento já ocupado!");
                            alert.show();
                        }
                    }
                });
                grid.add(seatButton, j, i); // Adiciona o botão na posição correspondente
            }
        }

        // Finalizar Compra
        Button btnFinalizar = new Button("Finalizar Compra");
        btnFinalizar.setOnAction(e -> compraStage.close());

        // Adiciona o campo de nome
        compraLayout.getChildren().addAll(new Label("Nome do Cliente:"), nomeClienteField, grid, btnFinalizar);

        // layout para compra de ingressos
        Scene scene = new Scene(compraLayout, 400, 450);
        compraStage.setScene(scene);
        compraStage.show();
    }

    // método para exibir o historico de compras
    private void exibirHistoricoCompras() {
        Stage historicoStage = new Stage();
        historicoStage.setTitle("Histórico de Compras");

        //área do histórico de compras
        VBox historicoLayout = new VBox(10);
        TextArea historicoArea = new TextArea();
        historicoArea.setEditable(false); // não permite fazer edições
        historicoArea.setText(cine.getHistoricoCompras());

        //botão de fechar a janela do histórico
        Button btnFechar = new Button("Fechar");
        btnFechar.setOnAction(e -> historicoStage.close());
        historicoLayout.getChildren().addAll(new Label("Histórico de Compras:"), historicoArea, btnFechar);

        // layout da janela do histórico de compras
        Scene scene = new Scene(historicoLayout, 400, 300);
        historicoStage.setScene(scene);
        historicoStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}