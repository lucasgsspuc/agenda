package com.example.appagenda;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DisplayFragment extends Fragment {

    private TextView textViewCompromissos;
    private CompromissosDB compromissosDB;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display, container, false);

        compromissosDB = new CompromissosDB(getContext());

        Button buttonHoje = view.findViewById(R.id.button_hoje);
        Button buttonOutraData = view.findViewById(R.id.button_outra_data);
        textViewCompromissos = view.findViewById(R.id.textView_compromissos);

        mostrarCompromissosHoje();

        buttonHoje.setOnClickListener(v -> mostrarCompromissosHoje());
        buttonOutraData.setOnClickListener(v -> mostrarDialogoOutraData());

        return view;
    }

    public void atualizarCompromissos(List<Compromisso> novosCompromissos) {
        StringBuilder sb = new StringBuilder();
        for (Compromisso compromisso : novosCompromissos) {
            sb.append(compromisso.toString()).append("\n");
        }
        if (sb.length() == 0) {
            sb.append("Nenhum compromisso para a data selecionada.");
        }
        textViewCompromissos.setText(sb.toString());
    }

    private void mostrarCompromissosHoje() {
        String dataAtual = new java.text.SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new java.util.Date());
        mostrarCompromissosPorData(dataAtual);
    }

    private void mostrarDialogoOutraData() {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
            String dataEscolhida = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);
            mostrarCompromissosPorData(dataEscolhida);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void mostrarCompromissosPorData(String data) {
        List<Compromisso> compromissos = compromissosDB.buscarCompromissosPorData(data);
        StringBuilder sb = new StringBuilder();

        for (Compromisso compromisso : compromissos) {
            sb.append(compromisso.toString()).append("\n");
        }

        if (sb.length() == 0) {
            sb.append("Nenhum compromisso para ").append(data).append(".");
        }

        textViewCompromissos.setText(sb.toString());
    }
}