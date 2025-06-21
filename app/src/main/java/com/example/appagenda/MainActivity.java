package com.example.appagenda;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements InputFragment.OnInputListener {
    private List<Compromisso> compromissos;
    private CompromissosDB compromissosDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        compromissos = new ArrayList<>();
        compromissosDB = new CompromissosDB(this);

        if (savedInstanceState == null) {
            InputFragment inputFragment = new InputFragment();
            DisplayFragment displayFragment = new DisplayFragment();

        }
    }

    @Override
    public void onInput(String data, String hora, String descricao) {
        Compromisso novoCompromisso = new Compromisso(data, hora, descricao);

        compromissosDB.adicionarCompromisso(novoCompromisso);

        DisplayFragment displayFragment = (DisplayFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_display);
        if (displayFragment != null) {
            displayFragment.atualizarCompromissos(compromissosDB.buscarCompromissosPorData(data));
        }
    }
}