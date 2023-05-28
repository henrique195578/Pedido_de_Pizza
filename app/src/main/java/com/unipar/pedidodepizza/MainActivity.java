package com.unipar.pedidodepizza;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RadioGroup rg_Tamanho;
    private Spinner spn_Sabores;
    private ListView list_Sabores;
    private CheckBox ck_com_Borda;
    private CheckBox ck_com_Refrigerante;
    private String[] vetorPizzasSabores;
    private ArrayList<String> ListaSabores;
    private int SelecaoSpinner;
    private int qntSabores;
    private double total_Pedido;
    private TextView tv_Result;
    private String tamSelecionado;
    private String cmBordaeRefri = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        rg_Tamanho = findViewById(R.id.rg_Tamanho);
        ck_com_Borda = findViewById(R.id.ck_com_Borda);
        ck_com_Refrigerante = findViewById(R.id.ck_com_Refrigerante);
        tv_Result = findViewById(R.id.tv_Result);
        spn_Sabores = findViewById(R.id.spn_Sabores);

        vetorPizzasSabores = new String[]{
                "", "Calabresa", "Strogonoff", "Mexicana", "Abacaxi Nevada",
                "Dois Amores", "Marguerita", "Palmito", "Filé Mignon",
                "Strogonoff", "4 Queijo", "Bacon", "Atum"};

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, vetorPizzasSabores);
        spn_Sabores.setAdapter(adapter);

        list_Sabores = findViewById(R.id.list_Sabores);
        ListaSabores = new ArrayList<>();
        tamSelecionado = "";

        spn_Sabores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int meusabor, long l) {
                if (meusabor == 0) {
                    SelecaoSpinner = -1;
                } else {
                    SelecaoSpinner = meusabor;
                    adicionarSabor(meusabor);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void adicionarSabor(int meusabor) {

        if (tamSelecionado.isEmpty()) {
            //Toast.makeText(this, "Por favor, selecione o tamanho da pizza primeiro!", Toast.LENGTH_LONG).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Por favor, selecione o tamanho da pizza primeiro!")
                    .setTitle("Atenção!")
                    .setPositiveButton("Irei adicionar", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        } else if (ListaSabores.size() < qntSabores) {
            ListaSabores.add(vetorPizzasSabores[meusabor]);
            atualizaLista();
        } else {
            Toast.makeText(this, "Ja selecionou a quantidade máximo do tamanho escolhido!", Toast.LENGTH_LONG).show();
        }
    }

    private void atualizaLista() {
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ListaSabores);
        list_Sabores.setAdapter(adapter);
        atualizaResultado();

    }

    private void atualizaResultado() {

        tv_Result.setText("PEDIDO REALIZADO!\n\n" + "Tamanho: " +
                tamSelecionado + "\nSabores escolhidos:" +
                ListaSabores + "\n" +
                cmBordaeRefri + "\n" + "Total do Pedido: R$" +
                total_Pedido);
    }

    public void SelecioneOpcao(View view) {
        boolean checkado = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.rb_Pequena:
                if (checkado) {
                    if (ListaSabores.size() == 0 || ListaSabores.size() <= qntSabores) {
                        qntSabores = 1;
                        total_Pedido = 20.00;
                        tamSelecionado = "Pequena";
                        atualizaLista();
                        spn_Sabores.setSelection(0);
                        resetarListaSabores();
                    } /*else {
                        Toast.makeText(this, "Limite de sabores atingido! Máximo " + qntSabores + " sabor(es)!", Toast.LENGTH_LONG).show();
                        ((RadioButton) view).setChecked(false);
                    }*/
                }
                break;
            case R.id.rb_Media:
                if (checkado) {
                    if (ListaSabores.size() == 0 || ListaSabores.size() <= qntSabores) {
                        qntSabores = 2;
                        total_Pedido = 30.00;
                        tamSelecionado = "Média";
                        atualizaLista();
                        spn_Sabores.setSelection(0);
                    } /*else {
                        Toast.makeText(this, "Limite de sabores atingido! Máximo " + qntSabores + " sabor(es)!", Toast.LENGTH_LONG).show();
                        ((RadioButton) view).setChecked(false);
                    }*/
                }
                break;
            case R.id.rb_Grande:
                if (checkado) {
                    if (ListaSabores.size() == 0 || ListaSabores.size() <= qntSabores) {
                        qntSabores = 4;
                        total_Pedido = 40.00;
                        tamSelecionado = "Grande";
                        atualizaLista();
                        spn_Sabores.setSelection(0); //redefinindo o spinner
                    } /*else {
                        Toast.makeText(this, "Limite de sabores atingido! Máximo " + qntSabores + " sabor(es)!", Toast.LENGTH_LONG).show();
                        ((RadioButton) view).setChecked(false);
                    }*/
                }
                break;
        }
    }
    private void resetarListaSabores() {
        ListaSabores.clear();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ListaSabores);
        list_Sabores.setAdapter(adapter);
    }

    private void atualizaListaSabores(){
        ListaSabores.clear();
        int saborSelecionado = spn_Sabores.getSelectedItemPosition();
        if (saborSelecionado > 0) {
            adicionarSabor(saborSelecionado);
        }
    }

    public void zerarCheckado() {
        ck_com_Borda.setChecked(false);
        ck_com_Refrigerante.setChecked(false);
    }

    public void retornaCheckBox(View view) {
        boolean checkado = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.ck_com_Borda:
                if (checkado) {
                    if (cmBordaeRefri.contains("Borda")) {
                        ((CheckBox) view).setChecked(false);
                        return;
                    }
                    total_Pedido += 10.00;
                    if (cmBordaeRefri.isEmpty()) {
                        cmBordaeRefri += "Borda";
                    } else {
                        cmBordaeRefri += " e Borda";
                    }
                    atualizaLista();
                } else {
                    if (cmBordaeRefri.contains("Borda")) {
                        if (cmBordaeRefri.contains(" e Borda")) {
                            cmBordaeRefri = cmBordaeRefri.replace(" e Borda", "");
                        } else {
                            cmBordaeRefri = cmBordaeRefri.replace("Borda", "");
                        }
                        total_Pedido -= 10.00;
                        atualizaLista();
                    }
                }
                break;
            case R.id.ck_com_Refrigerante:
                if (checkado) {
                    if (cmBordaeRefri.contains("Refrigerante")) {
                        ((CheckBox) view).setChecked(false);
                        return;
                    }
                    total_Pedido += 5.00;
                    if (cmBordaeRefri.isEmpty()) {
                        cmBordaeRefri += "Refrigerante";
                    } else {
                        cmBordaeRefri += " e Refrigerante";
                    }
                    atualizaLista();
                } else {
                    if (cmBordaeRefri.contains("Refrigerante")) {
                        if (cmBordaeRefri.contains(" e Refrigerante")) {
                            cmBordaeRefri = cmBordaeRefri.replace(" e Refrigerante", "");
                        } else {
                            cmBordaeRefri = cmBordaeRefri.replace("Refrigerante", "");
                        }
                        total_Pedido -= 5.00;
                        atualizaLista();
                    }
                }
                break;
        }
    }

    public void btn_ConcluirPedido(View view) {
        if (tamSelecionado.isEmpty() || ListaSabores.isEmpty()) {

            //Toast.makeText(this, "Não é possível finalizar o pedido. \n Selecione um TAMANHO e um SABOR.", Toast.LENGTH_LONG).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Não é possível finalizar o pedido. \nSelecione um TAMANHO e um SABOR.")
                    .setTitle("Atenção!")
                    .setPositiveButton("OK", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            Toast.makeText(this, "Pedido realizado com sucesso!", Toast.LENGTH_LONG).show();
        }
    }


    public void limparLista() {
        ListaSabores.clear();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ListaSabores);
        list_Sabores.setAdapter(adapter);
    }

    public void btn_LimparPedido(View view) {
        tv_Result.setText("");
        total_Pedido = 0.00;
        tamSelecionado = "";
        cmBordaeRefri = "";
        limparLista();
        rg_Tamanho.clearCheck();
        ck_com_Borda.setChecked(false);
        ck_com_Refrigerante.setChecked(false);
    }

    public void btn_RemoverSabores(View view) {
        removeSabor();

    }

    private void removeSabor() {
        if (!ListaSabores.isEmpty()) {
            int ultimoId = ListaSabores.size() - 1;
            ListaSabores.remove(ultimoId);
            atualizaLista();
        } else {
            //Snackbar.make(findViewById(android.R.id.content), "Não há sabor selecionado. \n Por favor adicione um sabor!", Snackbar.LENGTH_SHORT).show();
            //Toast.makeText(this, "Não há sabor selecionado. \n Por favor adicione um sabor!", Toast.LENGTH_LONG).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Não há sabor selecionado. \n Por favor adicione um sabor!")
                    .setTitle("Atenção")
                    .setPositiveButton("Adicionar", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }

    }

}