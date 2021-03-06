package br.example.jonat.a05_controle_abastecimento;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class lista extends AppCompatActivity {
    public static final int ADICIONAR_ABASTECIMENTO = 1312;
    private listaAdapter adapater;
    private boolean permissao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        RecyclerView rvListaAbastecimento = findViewById(R.id.rvLista);

        this.permissao = this.getIntent().getBooleanExtra("permissao", false);
        this.adapater = new listaAdapter();
        this.adapater.lista = ListaDAO.getLista(this.getApplicationContext());

        rvListaAbastecimento.setAdapter(this.adapater);
        rvListaAbastecimento.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
    }

    public void onClickFAB(View view) {
        Intent intent =  new Intent(this.getApplicationContext(), AdicionarAbastecimento.class);
        if(this.adapater.lista.size()>0) {
            intent.putExtra("kmAntigo", this.adapater.lista.get(this.adapater.lista.size() - 1).getKilometros());
        }
        intent.putExtra("permissao", this.permissao);
        startActivityForResult(intent, ADICIONAR_ABASTECIMENTO);
    }

    public void atualizaDatasetLista(){
        this.adapater.lista = ListaDAO.getLista(this.getApplicationContext());
        this.adapater.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADICIONAR_ABASTECIMENTO){
            if(resultCode == 1){
                //Toast.makeText(this.getApplicationContext(), "AÇÃO 1!!!!!", Toast.LENGTH_LONG).show();
                atualizaDatasetLista();
            }else{
                Toast.makeText(this.getApplicationContext(), "nao sei de onde veio", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this.getApplicationContext(), "deu ruim", Toast.LENGTH_SHORT).show();
        }
    }

    public void backFAB(View view) {
        Intent intent =  new Intent(this.getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void GoToMap(View view) {
        Intent intent = new Intent(this.getApplicationContext(), MapaActivity.class);
        startActivity(intent);
    }
}