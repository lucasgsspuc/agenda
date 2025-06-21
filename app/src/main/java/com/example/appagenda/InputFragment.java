package com.example.appagenda;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class InputFragment extends Fragment {

    private EditText editTextDescricao;
    private Button buttonData;
    private Button buttonHora;
    private String dataSelecionada;
    private String horaSelecionada;
    private OnInputListener onInputListener;

    public interface OnInputListener {
        void onInput(String data, String hora, String descricao);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input, container, false);

        buttonData = view.findViewById(R.id.button_data);
        buttonHora = view.findViewById(R.id.button_hora);
        editTextDescricao = view.findViewById(R.id.editText_descricao);
        Button buttonOk = view.findViewById(R.id.button_ok);

        buttonData.setOnClickListener(v -> showDatePickerDialog());
        buttonHora.setOnClickListener(v -> showTimePickerDialog());
        buttonOk.setOnClickListener(v -> saveCompromisso());

        return view;
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year1, month1, dayOfMonth) -> {
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(year1, month1, dayOfMonth);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            dataSelecionada = sdf.format(selectedDate.getTime());

            buttonData.setText(dataSelecionada);
        }, year, month, day);

        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), (view, hourOfDay, minute1) -> {
            horaSelecionada = String.format("%02d:%02d", hourOfDay, minute1);
            buttonHora.setText(horaSelecionada);
        }, hour, minute, true);

        timePickerDialog.show();
    }

    private void saveCompromisso() {
        String descricao = editTextDescricao.getText().toString();

        if (dataSelecionada == null || horaSelecionada == null || descricao.isEmpty()) {
            Toast.makeText(getActivity(), "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (onInputListener != null) {
            onInputListener.onInput(dataSelecionada, horaSelecionada, descricao);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            onInputListener = (OnInputListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " deve implementar OnInputListener");
        }
    }
}